package com.online_shop.project.dto;

import com.online_shop.project.enums.RequestStatus;

public class updateRequest {
    private String status;

    public RequestStatus getStatus() {
        return RequestStatus.valueOf(status.toLowerCase());
    }
}
