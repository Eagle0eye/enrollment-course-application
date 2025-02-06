package com.online_shop.project.services;
import com.online_shop.project.dto.RequestDto;
import com.online_shop.project.dto.RequestGenDTO;
import com.online_shop.project.models.Request;
import java.util.List;

public interface RequestService {


    Request viewRequest(Long id);
    List<RequestDto> viewRequests();
    void approveRequest(Long id);

    void refuseRequest(Long id);


    void removeRequest(Long id);

    void makeRequest(RequestGenDTO form);

    List<Request> filterRequestsByModerator(String email);
}
