package com.online_shop.project.dto;

import lombok.Data;
import lombok.Getter;


@Getter
public class changePasswordForm {
    private String old_password;
    private String new_password;
    private String retry_password;

}
