package com.online_shop.project.services;

import com.online_shop.project.dto.UpdateCourseForm;
import com.online_shop.project.models.Candidate;
import com.online_shop.project.models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    public List<Course> getAllCourses();
    Course getCourseById(Long id);

    List<Candidate>  getAllCandidatesPerCourse(Long id);
    void updateCourseById(Long id, UpdateCourseForm form);
    void deleteCourseById(Long id);
    void CreateCourse(UpdateCourseForm form);

}
