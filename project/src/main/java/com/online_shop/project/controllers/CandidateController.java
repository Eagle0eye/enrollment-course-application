package com.online_shop.project.controllers;

import com.online_shop.project.dto.RequestGenDTO;
import com.online_shop.project.services.RequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/candidate")
public class CandidateController {

    @Autowired
    private RequestServiceImpl requestService;


    @PostMapping("/request")
    public ResponseEntity<String> makeRequest(@RequestBody RequestGenDTO request){

        requestService.makeRequest(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
