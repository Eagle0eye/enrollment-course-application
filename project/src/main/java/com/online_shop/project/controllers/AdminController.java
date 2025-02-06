package com.online_shop.project.controllers;
import com.online_shop.project.dto.updateUserForm;
import com.online_shop.project.dto.UpdateCourseForm;
import com.online_shop.project.models.Request;
import com.online_shop.project.models.SystemUser;
import com.online_shop.project.services.CourseServiceImpl;
import com.online_shop.project.services.RequestServiceImpl;
import com.online_shop.project.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    // Admin Controller
    @Autowired
    private UserServiceImpl userService;

    // show admin profile
    @GetMapping("/{id}")
    public ResponseEntity<SystemUser> showAdminProfile(@PathVariable Long id){
        try {
            SystemUser user =userService.showAdminProfile(id);
           return new ResponseEntity<>(user,HttpStatus.FOUND);
        }
        catch (NoSuchElementException exception)
        {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    // edit profile
    @PutMapping("/{id}")
    public ResponseEntity<String> editAdminProfile(@PathVariable  Long id, @RequestBody updateUserForm form){
       try {
           userService.editAdminProfile(id,form);
           return new ResponseEntity<>("Information updated",HttpStatus.ACCEPTED);

       }
       catch (Exception e)
       {
           return new ResponseEntity<>("Something Wrong happen",HttpStatus.NOT_MODIFIED);
       }
    }




    @Autowired
    private CourseServiceImpl courseService;

    // add new course
    @PostMapping("/course")
    public void createCourse(@RequestBody UpdateCourseForm form)
    {
        try {
            courseService.CreateCourse(form);
        }catch (Exception e)
        {
            throw new RuntimeException("Something wrong happen\n"+e.getMessage());
        }
    }

    // update Course Details
    @PutMapping("/course/{id}")
    public ResponseEntity<String> updateCourseById(@PathVariable Long id, @RequestBody UpdateCourseForm form)
    {
        try {
            courseService.updateCourseById(id,form);
            return  new ResponseEntity<>("Course Updated",HttpStatus.ACCEPTED);
        }
        catch (NoSuchElementException e) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // delete course
    @DeleteMapping("/course/{id}")
    public void deleteCourse(@PathVariable Long id)
    {
        try {
            courseService.deleteCourseById(id);
        }catch (Exception e)
        {
            throw new RuntimeException("Something wrong happen");
        }
    }


    // Requests
    @Autowired
    private RequestServiceImpl requestService;

    @GetMapping("/requests?moderator={email}")
    private ResponseEntity<List<Request>> showModeratorRequests(@PathVariable String email){
        return new ResponseEntity<>(requestService.filterRequestsByModerator(email),HttpStatus.OK);
    }

    @DeleteMapping("/requests/{id}")
    public void deleteRequest(@PathVariable Long id)
    {
        try {
            requestService.removeRequest(id);
        }catch (Exception e)
        {
            throw new RuntimeException("Something wrong happen");
        }
    }



    // Moderators

    @PostMapping("/moderator")
    public void generateModerator(@RequestBody updateUserForm form){    userService.insertModerator(form);  }

    // get all moderators
    @GetMapping("/moderators")
    public ResponseEntity<List<SystemUser>> getModerators(){
        List<SystemUser> moderators = userService.viewModerators();
        return new ResponseEntity<>(moderators,HttpStatus.OK);
    }

    @DeleteMapping("/moderator/{id}")
    public void deleteModerator(@PathVariable Long id)
    {
        try {
            userService.removeModerator(id);
        }catch (Exception e)
        {
            throw new RuntimeException("Something wrong happen");
        }
    }


}
