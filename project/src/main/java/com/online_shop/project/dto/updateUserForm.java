package com.online_shop.project.dto;


import jakarta.persistence.Column;
import lombok.Data;

@Data
public class updateUserForm {
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String username;
}

