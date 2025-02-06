package com.online_shop.project.repositories;

import com.online_shop.project.dto.RequestDto;
import com.online_shop.project.dto.RequestDto;
import com.online_shop.project.models.Candidate;
import com.online_shop.project.models.Course;
import com.online_shop.project.models.Request;
import com.online_shop.project.models.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RequestRepository extends JpaRepository<Request,Long> {

    List<Request> findRequestByCandidate(Candidate candidate);
    List<Request> findRequestByCourse(Course course);

    List<Request> getRequestsByUser(SystemUser user);

    @Query("SELECT new com.online_shop.project.dto.RequestDto(r.id,r.status , r.dateTime, " +
            "concat(c.first_name, ' ', c.last_name), co.name, concat(m.first_name, ' ', m.last_name)) " +
            "FROM Request r " +
            "JOIN r.candidate c " +
            "JOIN r.course co " +
            "JOIN r.user m")
    List<RequestDto> findAllRequests();

    Optional<Request> findRequestByCandidateAndCourse(Candidate candidate, Course course);



}
