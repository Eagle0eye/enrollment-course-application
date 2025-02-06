package com.online_shop.project.repositories;


import com.online_shop.project.models.Candidate;
import com.online_shop.project.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

}
