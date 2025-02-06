package com.online_shop.project.dto;

import com.online_shop.project.enums.RequestStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {
    private Long id;
    private RequestStatus status;
    private String date;
    private String candidateName;
    private String courseName;
    private String moderatorName;

    // Constructor
    public RequestDto(Long id,
                      RequestStatus status,
                      String date,
                      String candidateName,
                      String courseName,
                      String moderatorName) {
        this.id = id;
        this.status = status;
        this.date = date;
        this.candidateName = candidateName;
        this.courseName = courseName;
        this.moderatorName = moderatorName;
    }

}
