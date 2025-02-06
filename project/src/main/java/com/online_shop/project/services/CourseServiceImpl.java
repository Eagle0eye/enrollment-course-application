package com.online_shop.project.services;

import com.online_shop.project.dto.UpdateCourseForm;
import com.online_shop.project.enums.Category;
import com.online_shop.project.models.Candidate;
import com.online_shop.project.models.Course;
import com.online_shop.project.repositories.CandidateRepository;
import com.online_shop.project.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CandidateRepository candidateRepository;

    private final CourseRepository courseRepository;
    public CourseServiceImpl(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    // Get All Courses
    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // find course by id
    @Override
    public Course getCourseById(Long id) {
        return this.courseRepository.findById(id).orElseThrow(()-> new RuntimeException("Not Found"));
    }

    // Get all candidates per course
    @Override
    public List<Candidate> getAllCandidatesPerCourse(Long id) {
        Course course = courseRepository.findById(id).get();
        List<Candidate> candidates = course.getCandidates().stream().toList();
        return (candidates.isEmpty())?new ArrayList<>():candidates;
    }

    // update course by id
    @Override
    public void updateCourseById(Long id, UpdateCourseForm form)
    {
        Course updated_course = courseRepository.findById(id).get();
        updated_course.setName(form.getName());
        updated_course.setDescription(form.getDescription());
        updated_course.setCategory(Category.valueOf(form.getCategory().toUpperCase()));
        updated_course.setHours(form.getHours());
        courseRepository.save(updated_course);
    }

    // delete course by id
    @Override
    public void deleteCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(()->new RuntimeException("COURSE NOT FOUND"));
        List<Candidate> candidates = course.getCandidates().stream().toList();
        for (Candidate candidate: candidates)
        {
            candidate.removeCourse(course);
            candidateRepository.save(candidate);
        }
        courseRepository.delete(course);
    }

    // create course
    @Override
    public void CreateCourse(UpdateCourseForm form) {
        Course new_course = new Course();
        new_course.setName(form.getName());
        new_course.setDescription(form.getDescription());
        new_course.setHours(form.getHours());
        new_course.setCategory(Category.valueOf(form.getCategory().toUpperCase()));
        courseRepository.save(new_course);
    }

}
