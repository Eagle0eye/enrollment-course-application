package com.online_shop.project.services;

import com.online_shop.project.dto.CandidateDto;
import com.online_shop.project.dto.changePasswordForm;
import com.online_shop.project.models.Candidate;
import com.online_shop.project.models.Course;
import com.online_shop.project.models.Request;
import com.online_shop.project.repositories.CandidateRepository;
import com.online_shop.project.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class CandidateServiceImpl implements CandidateService {


    private final CandidateRepository candidateRepository;
    private final CourseRepository courseRepository;

    public CandidateServiceImpl(CandidateRepository candidateRepository,
                                CourseRepository courseRepository){
        this.candidateRepository = candidateRepository;
        this.courseRepository =courseRepository;


    }

    @Override
    public Optional<Candidate> getCandidateById(Long id) {
        return candidateRepository.findById(id);
    }

    @Override
    public String updateCandidateById(Long id, CandidateDto form) {
        try {
            Candidate candidate = candidateRepository.findById(id).get();
            candidate.setFirst_name(form.getFirst_name());
            candidate.setLast_name(form.getLast_name());
            candidate.setPhone(form.getPhone());
            candidate.setEmail(form.getEmail());
            candidate.setUsername(form.getUsername());
            candidate.setUniversity(form.getUniversity());
            candidateRepository.save(candidate);
            return "Candidate has been updated";
        }
        catch (Exception exception)
        {
            return "";
        }
    }

    @Override
    public boolean changePassword(Long id, changePasswordForm form) {
        try {
            Candidate candidate = candidateRepository.findById(id).get();
            if(candidate.getPassword().equals(form.getOld_password()))
                    if(Objects.equals(form.getNew_password(),form.getRetry_password())) {
                candidate.setPassword(form.getNew_password());
                candidateRepository.save(candidate);
                return true;
            }
                return false;

        }
        catch (Exception exception)
        {
            return false;
        }
    }
    @Override
    public List<Course> getEnrolledCourses(Long id) {
        Candidate candidate = candidateRepository.findById(id).get();
        List<Course> enrolled_courses = candidate.getCourses().stream().toList();
        return (enrolled_courses.isEmpty())?new ArrayList<>(): enrolled_courses;
    }

    // DELETE: user by id
    @Override
    public void deleteCandidateById(Long id) {
        Candidate candidate = candidateRepository.findById(id).orElseThrow(()->new RuntimeException("CANDIDATE NOT FOUND"));
        List<Course> courses = candidate.getCourses().stream().toList();
        for (Course course : courses){
            course.removeCandidate(candidate);
            courseRepository.save(course);
        }
        candidateRepository.delete(candidate);
    }

    @Override
    public List<Candidate> viewCandidates() {
        List<Candidate> candidates = candidateRepository.findAll();
        return (candidates.isEmpty())?new ArrayList<>():candidates;

    }

    @Override
    public void addCandidate(CandidateDto form) {
        Candidate candidate = new Candidate();
        candidate.setFirst_name(form.getFirst_name());
        candidate.setLast_name(form.getLast_name());
        candidate.setUniversity(form.getUniversity());
        candidate.setPhone(form.getPhone());
        candidate.setEmail(form.getEmail());
        candidate.setUsername(form.getUsername());
        candidate.setPassword("0000");
        candidateRepository.save(candidate);
    }

    @Override
    public List<Request> viewCandidateRequests(Long id) {
        return candidateRepository.findById(id).get().getRequests();
    }






}
