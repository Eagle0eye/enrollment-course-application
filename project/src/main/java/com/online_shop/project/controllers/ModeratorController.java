package com.online_shop.project.controllers;

import com.online_shop.project.services.RequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/moderator")
public class ModeratorController {



    // Requests
    @Autowired
    private RequestServiceImpl requestService;

    @GetMapping("/request/{status}/{id}")
    public ResponseEntity<String> changeRequest(@PathVariable String status,@PathVariable Long id)
    {
        String update = "in Correct Status";
        if ("approved".equals(status)) {
            requestService.approveRequest(id);
            update = "approved";
            } else if ("refused".equals(status)) {
            requestService.refuseRequest(id);
            update = "cancelled";
        }
        return new ResponseEntity<>("Request #"+id+" "+update,HttpStatus.OK);
    }



}
