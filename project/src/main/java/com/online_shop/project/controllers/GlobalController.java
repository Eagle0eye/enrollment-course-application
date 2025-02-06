package com.online_shop.project.controllers;

import com.online_shop.project.dto.CandidateDto;
import com.online_shop.project.models.Candidate;
import com.online_shop.project.models.Course;
import com.online_shop.project.dto.updateUserForm;
import com.online_shop.project.dto.changePasswordForm;

import com.online_shop.project.models.Request;
import com.online_shop.project.services.CandidateServiceImpl;
import com.online_shop.project.services.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class GlobalController {
    private final CourseServiceImpl courseService;


    @Autowired
    private CandidateServiceImpl candidateService;


    public GlobalController(CourseServiceImpl course_Service){
        this.courseService =  course_Service;
    }

    // get current Profile
    @GetMapping("/candidate/{id}")
    public ResponseEntity<Candidate> viewCandidate(@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(candidateService.getCandidateById(id).orElse(null),HttpStatus.OK);
    }


    // change password for candidate
    @PutMapping("/candidate/{id}/change-password")
    public ResponseEntity<String> changeCandidatePassword(@PathVariable(name = "id") Long id, @RequestBody changePasswordForm form){

        return (candidateService.changePassword(id,form))?
                new ResponseEntity<>("Candidate Changed Password", HttpStatus.ACCEPTED):
                new ResponseEntity<>("Please enter correct password :(",HttpStatus.CONFLICT);
    }

    // Get all Courses
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> viewCourses(){
        List<Course> courses = courseService.getAllCourses();
        return (courses.isEmpty())?
                new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK)
                :new ResponseEntity<>(courses,HttpStatus.OK);
    }

    // get Course by id
    @GetMapping("/course/{id}")
    public ResponseEntity<Course> viewCourseDetails(@PathVariable(name = "id") Long id){
    try {
        return new ResponseEntity<>(courseService.getCourseById(id),HttpStatus.FOUND);
    }catch (Exception exception){
        return new ResponseEntity<>(new Course(),HttpStatus.NOT_FOUND);
    }
    }


    @GetMapping("/candidate/{id}/requests")
    public ResponseEntity<List<Request>> candidateRequests(@PathVariable Long id){

    List<Request> requests = candidateService.viewCandidateRequests(id);
    if (requests.isEmpty())
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
    return new ResponseEntity<>(requests,HttpStatus.OK);
    }


    @GetMapping("/candidate/{id}/courses")
    public List<Course> viewCoursesPerCandidate(@PathVariable Long id) {
        return candidateService.getEnrolledCourses(id);
    }
    @PutMapping("/candidate/{id}")
    public void editCandidateProfile(@PathVariable Long id, @RequestBody CandidateDto form){
        candidateService.updateCandidateById(id,form);
    }


}
