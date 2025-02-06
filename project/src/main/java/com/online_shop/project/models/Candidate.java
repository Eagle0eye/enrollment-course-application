package com.online_shop.project.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "candidates")
public class Candidate extends Person{

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

    public void setUniversity(String university) {
        University = university;
    }

    public void setCourses(Course course) {
        this.courses.add(course);
    }
    public void removeCourse(Course course) {
        this.courses.remove(course);
    }
}
