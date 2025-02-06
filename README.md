# **Enrollment Course Application Documentation**

## **Overview**

The **Enrollment Course Application** is a system designed to handle course enrollment requests. It allows candidates to apply for courses, and moderators can approve or refuse requests. It manages relationships between candidates, courses, and enrollment statuses.

## **Features**
- **Candidate registration** and course application.
- **Course approval** and refusal processes by moderators.
- **Relationship management** between candidates and courses.
- **Request status tracking** (Pending, Accepted, Refused).

## **Technologies Used**
- **Java 17** for backend development.
- **Spring Boot** for the application framework.
- **JPA/Hibernate** for ORM and database interactions.
- **MySQL** for database management.
- **REST APIs** for communication.

---

## **Key Components**

### 1. **Entities**

#### Request
Represents an enrollment request made by a candidate.
- **Attributes**:
  - `id`: Unique identifier.
  - `status`: Status of the request (`Pending`, `Accepted`, `Refused`).
  - `course`: The course being applied for.
  - `candidate`: The candidate applying for the course.

#### Candidate
Represents an individual who applies for courses.
- **Attributes**:
  - `id`: Unique identifier.
  - `name`: Candidate's name.
  - `courses`: List of courses the candidate has applied to or is enrolled in.

#### Course
Represents a course available for enrollment.
- **Attributes**:
  - `id`: Unique identifier.
  - `title`: Course title.
  - `candidates`: List of candidates enrolled in the course.

---

### 2. **Request Management**

#### Approving a Request
When a request is approved:
1. The candidate is added to the course’s list of enrolled students.
2. The course is added to the candidate's list of enrolled courses.
3. The request status is updated to **ACCEPTED**.

#### Refusing a Request
When a request is refused:
1. The request is marked as **REFUSED**.
2. If previously accepted, the candidate is removed from the course.

---

### 3. **Endpoints (REST API)**

#### Request Endpoints
- `POST /requests` - Submit a new enrollment request.
- `GET /requests/{id}` - Retrieve a specific request.
- `PUT /requests/{id}/approve` - Approve an enrollment request.
- `PUT /requests/{id}/refuse` - Refuse an enrollment request.

#### Candidate Endpoints
- `GET /candidates` - List all candidates.
- `GET /candidates/{id}` - Retrieve a specific candidate.

#### Course Endpoints
- `GET /courses` - List all available courses.
- `GET /courses/{id}` - Retrieve a specific course.
- PostMan Documentaion: https://documenter.getpostman.com/view/26334403/2sAYX6qMxc 
---

## **Handling Infinite Recursion in One-to-Many and Many-to-Many Relationships**

### **Problem Description**
In JPA/Hibernate, bidirectional relationships between entities (like `@OneToMany` and `@ManyToMany`) can lead to infinite recursion when serializing entities to JSON, as the serialization includes references back to the parent entity.

### **Example Scenario**
Consider the `Candidate` and `Course` entities with a many-to-many relationship:
```java
@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "enrolled_courses",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    @JsonManagedReference
    private Set<Course> courses;
}

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToMany(mappedBy = "courses")
    @JsonBackReference
    private Set<Candidate> candidates;
}
```

### **Solutions to Prevent Infinite Recursion**

#### 1. **Use `@JsonIgnore`**
This solution prevents Jackson from serializing the `courses` field of the `Candidate` entity, avoiding infinite recursion.
```java
@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "candidates")
    @JsonIgnore
    private List<Course> courses;
}
```

#### 2. **Use `@JsonManagedReference` and `@JsonBackReference`**
These annotations prevent infinite recursion by managing the bidirectional relationship during serialization. Use `@JsonManagedReference` on the parent side and `@JsonBackReference` on the child side.
```java
@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "candidates")
    @JsonManagedReference
    private List<Course> courses;
}

@Entity
public class Course {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private int hours;

    @ManyToMany(mappedBy = "courses")
    @JsonBackReference
    private Set<Candidate> candidates;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Request> requests;
}
```

#### 3. **Use DTOs (Recommended for Large Applications)**
DTOs (Data Transfer Objects) provide more control over the data being exposed and avoid infinite recursion by not exposing full entities directly.
```java
package com.online_shop.project.dto;

import com.online_shop.project.enums.RequestStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {
    private Long id;
    private RequestStatus status;
    private String date;
    private String candidateName;
    private String courseName;
    private String moderatorName;

    public RequestDto(Long id,
                      RequestStatus status,
                      String date,
                      String candidateName,
                      String courseName,
                      String moderatorName) {
        this.id = id;
        this.status = status;
        this.date = date;
        this.candidateName = candidateName;
        this.courseName = courseName;
        this.moderatorName = moderatorName;
    }

}

```
Map class to RequestRepository:
```java
@Repository
public interface RequestRepository extends JpaRepository<Request,Long> {
@Query("SELECT new com.online_shop.project.dto.RequestDto(r.id,r.status , r.dateTime, " +
            "concat(c.first_name, ' ', c.last_name), co.name, concat(m.first_name, ' ', m.last_name)) " +
            "FROM Request r " +
            "JOIN r.candidate c " +
            "JOIN r.course co " +
            "JOIN r.user m")
    List<RequestDto> findAllRequests();
}
```
Mapped to ServiceImpl
```java
 @Override
    public List<RequestDto> viewRequests() {
        return repository.findAllRequests();
    }
```

---

## **Use of `@Inheritance(strategy = InheritanceType.JOINED)` vs `@MappedSuperclass`**

### **`@Inheritance(strategy = InheritanceType.JOINED)`**
This strategy uses multiple tables to store related entities, optimizing space by storing common attributes in the base class table and subclass-specific attributes in separate tables.
```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String first_name;
    private String last_name;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
}

@Entity
public class Candidate extends Person {
    @Column(nullable = false)
    private String University;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "enrolled_courses",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    @JsonManagedReference
    private Set<Course> courses;

    @OneToMany(mappedBy = "candidate",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Request> requests;

}
```

### **`@MappedSuperclass`**
This approach does not create its own table but allows other entities to inherit its fields. It helps in reducing space as there’s no additional table for the base class.
```java
@MappedSuperclass
public class BaseEntity {
    @Id
    private Long id;
}

@Entity
public class Student extends BaseEntity {
    private String course;
}
```

---

## **Enums for Request Status**

Instead of using string literals for statuses like `Pending`, `Accepted`, and `Refused`, use an enum to maintain consistency.
```java
public enum RequestStatus {
    PENDING,
    ACCEPTED,
    REFUSED
}

@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;


}
```

---

## **Custom Queries with `@Query`**

You can use custom queries to retrieve only the data you need, optimizing performance and reducing redundancy.

Example custom query to find courses by title:
```java
@Repository
public interface RequestRepository extends JpaRepository<Request,Long> {
    @Query("SELECT new com.online_shop.project.dto.RequestDto(r.id,r.status , r.dateTime, " +
            "concat(c.first_name, ' ', c.last_name), co.name, concat(m.first_name, ' ', m.last_name)) " +
            "FROM Request r " +
            "JOIN r.candidate c " +
            "JOIN r.course co " +
            "JOIN r.user m")
    List<RequestDto> findAllRequests();
}
```

---

## **Conclusion**

The **Enrollment Course Application** efficiently handles course enrollments and the relationships between candidates and courses. It prevents infinite recursion issues in bidirectional relationships, reduces redundancy by using enums, custom queries, and DTOs, and leverages `@Inheritance(strategy = InheritanceType.JOINED)` and `@MappedSuperclass` for optimized space usage.

Future enhancements could include:
- Authentication and authorization mechanisms for different user roles.
--- 
