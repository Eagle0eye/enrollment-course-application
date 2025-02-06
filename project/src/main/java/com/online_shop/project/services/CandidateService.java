package com.online_shop.project.services;
import com.online_shop.project.dto.CandidateDto;
import com.online_shop.project.dto.UpdateCourseForm;
import com.online_shop.project.dto.changePasswordForm;
import com.online_shop.project.dto.updateUserForm;
import com.online_shop.project.models.Candidate;
import com.online_shop.project.models.Course;
import com.online_shop.project.models.Request;


import java.util.List;
import java.util.Optional;

public interface CandidateService {
    Optional<Candidate> getCandidateById(Long id);
    String updateCandidateById(Long id, CandidateDto form);

    boolean changePassword(Long id, changePasswordForm from);
    List<Course> getEnrolledCourses(Long id);

    void deleteCandidateById(Long id);

    List<Candidate> viewCandidates();

    void addCandidate(CandidateDto form);

    List<Request> viewCandidateRequests(Long id);

}
