package com.online_shop.project.controllers;
import com.online_shop.project.dto.RequestDto;
import com.online_shop.project.dto.updateUserForm;

import com.online_shop.project.dto.CandidateDto;
import com.online_shop.project.models.Candidate;
import com.online_shop.project.models.Request;
import com.online_shop.project.models.SystemUser;
import com.online_shop.project.services.CandidateServiceImpl;
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
@RequestMapping("/api/manager")
public class ManagementController {

    @Autowired
    private RequestServiceImpl requestService;

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CandidateServiceImpl candidateService;

    @GetMapping("/moderator/{id}")
    public ResponseEntity<SystemUser> viewModeratorDetails(@PathVariable(name = "id") Long id){
        try {
            SystemUser user =userService.showAdminProfile(id);
            return new ResponseEntity<>(user,HttpStatus.FOUND);
        }
        catch (NoSuchElementException exception)
        {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }




    // candidates per course
    @GetMapping("/course/{id}/candidates")
    public ResponseEntity<List<Candidate>> getCandidatesPerCourse(@PathVariable Long id)
    {
        try {
            List<Candidate> candidates = courseService.getAllCandidatesPerCourse(id);
            return new ResponseEntity<>(candidates,HttpStatus.OK);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }







    // update moderator Details
    @PutMapping("/moderator/{id}")
    public ResponseEntity<String> editModeratorProfile(@PathVariable Long id, @RequestBody updateUserForm form)
    {
        try {
            userService.editModeratorProfile(id,form);
            return  new ResponseEntity<>("Done",HttpStatus.OK);
        }
        catch (NoSuchElementException e) {
            return  new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    // view candidates
    @GetMapping("/candidates")
    public ResponseEntity<List<Candidate>> getCandidates(){
        return new ResponseEntity<>(candidateService.viewCandidates(),HttpStatus.OK);
    }

    // add candidate
    @PostMapping("/candidate")
    public ResponseEntity<String> addCandidate(@RequestBody CandidateDto form){
        try {
            candidateService.addCandidate(form);
            return new ResponseEntity<>(HttpStatus.CREATED);}
        catch (Exception exception) {return new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);}
    }



    // delete candidate
    @DeleteMapping("/candidates/{id}")
    public void removeCandidate(@PathVariable Long id){
        candidateService.deleteCandidateById(id);
    }


    // view requests
    @GetMapping("/requests")
    public ResponseEntity<List<RequestDto>> getAllRequests() {
        List<RequestDto> requests = requestService.viewRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<Request> viewRequest(@PathVariable Long id) {
        Request request = requestService.viewRequest(id);
        return ResponseEntity.ok(request);
    }





}
