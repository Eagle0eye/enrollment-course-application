package com.online_shop.project.services;

import com.online_shop.project.utilities.CustomPair;
import com.online_shop.project.dto.RequestDto;
import com.online_shop.project.dto.RequestGenDTO;
import com.online_shop.project.enums.RequestStatus;
import com.online_shop.project.models.Candidate;
import com.online_shop.project.models.Course;
import com.online_shop.project.models.Request;
import com.online_shop.project.models.SystemUser;
import com.online_shop.project.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService{
    private final RequestRepository repository;
    private final CandidateRepository candidateRepository;
    private final CourseRepository courseRepository;

    private final SystemUserRepository sysRepository;

    RequestServiceImpl(RequestRepository requestRepository,
                       CandidateRepository candidateRepository,
                       CourseRepository courseRepository,
                       SystemUserRepository userRepository)
    {
        this.repository =requestRepository;
        this.candidateRepository = candidateRepository;
        this.courseRepository =courseRepository;
        this.sysRepository = userRepository;
    }




    @Override
    public void approveRequest(Long id) {
        try {
            Request request = this.getRequest(id);
            if (request == null) {
                throw new IllegalArgumentException("Request not found with id: " + id);
            }

            CustomPair<Candidate, Course> customPair = this.findBothByRequest(request);
            if (customPair == null || customPair.getKey() == null || customPair.getValue() == null) {
                throw new IllegalStateException("Candidate or Course not found for the request.");
            }

            Candidate candidate = customPair.getKey();
            Course course = customPair.getValue();

            candidate.getCourses().add(course);
            course.getCandidates().add(candidate);
            request.setStatus(RequestStatus.ACCEPTED);
            candidateRepository.save(candidate);
            courseRepository.save(course);
            repository.save(request);
        } catch (Exception e) {
          throw new RuntimeException("Error approving request with id: " + id);
        }
    }

    @Override
    public void refuseRequest(Long id) {
        Request request = this.getRequest(id);
        if (request == null) {
            throw new RuntimeException("Request not found.");
        }
        if(request.getStatus()==RequestStatus.PENDING) {
                request.setStatus(RequestStatus.REFUSED);
                this.repository.save(request);

            } else if (
                request.getStatus()==RequestStatus.ACCEPTED) {
                CustomPair<Candidate,Course> info = this.removeInformationByRequest(this.findBothByRequest(request));
                Candidate candidate = info.getKey();
                Course course = info.getValue();
                candidate.removeCourse(course);
                course.removeCandidate(candidate);
                candidateRepository.save(candidate);
                courseRepository.save(course);
                request.setStatus(RequestStatus.REFUSED);
                this.repository.save(request);
            }
        else
            throw new RuntimeException("Cannot refuse a request that is already refused.");


    }



    @Override
    public Request viewRequest(Long id) {
        return this.getRequest(id);
    }

    @Override
    public List<RequestDto> viewRequests() {
        return repository.findAllRequests();
    }

    @Override
    public void removeRequest(Long id) {
        Optional<Request> search_request = repository.findById(id);
        if(search_request.isPresent()) {
            CustomPair<Candidate,Course> pair= this.removeInformationByRequest(findBothByRequest(search_request.get()));
            Candidate candidate = pair.getKey();
            Course course = pair.getValue();
            candidateRepository.save(candidate);
            courseRepository.save(course);
        }
        repository.deleteById(id);
    }


    @Override
    public void makeRequest(RequestGenDTO requestDto) {
        Optional<Candidate> candidateOpt = candidateRepository.findById(requestDto.getCandidate());
        Optional<Course> courseOpt = courseRepository.findById(requestDto.getCourse());
        Optional<SystemUser> userOpt = sysRepository.findById(requestDto.getUser());

        if (candidateOpt.isEmpty() || courseOpt.isEmpty() || userOpt.isEmpty()) {
            throw new IllegalArgumentException("Candidate, Course, or User not found.");
        }
        if (candidateOpt.get().getCourses().contains(courseOpt.get())) {
            throw new RuntimeException("Candidate is already enrolled in this course.");
        }
        Optional<Request> requestOpt = repository.findRequestByCandidateAndCourse(candidateOpt.get(), courseOpt.get());

        if (requestOpt.isEmpty() || requestOpt.get().getStatus().equals(RequestStatus.REFUSED)) {
            Request request = new Request();
            request.setDate();
            request.setCandidate(candidateOpt.get());
            request.setCourse(courseOpt.get());
            request.setUser(userOpt.get());
            request.setStatus(RequestStatus.PENDING);
            repository.save(request);
        } else {
            throw new RuntimeException("Request already exists and is not refused.");
        }
    }


    @Override
    public List<Request> filterRequestsByModerator(String email) {
        SystemUser moderator = sysRepository.findSystemUserByEmail(email);
        List<Request> requests = repository.getRequestsByUser(moderator);
        if (requests.isEmpty())
            return new ArrayList<>();
        return requests;
    }




    private CustomPair<Candidate,Course> findBothByRequest(Request request){
        Long course_id =request.getCourse().getId();
        Long candidate_id = request.getCandidate().getId();

        Course course = courseRepository.findById(course_id)
                .orElseThrow(() -> new RuntimeException("Course not found."));
        Candidate candidate = candidateRepository.findById(candidate_id)
                .orElseThrow(() -> new RuntimeException("Candidate not found."));

        return new CustomPair<>(candidate,course);
    }

    private CustomPair<Candidate,Course> removeInformationByRequest(CustomPair<Candidate,Course> request){
        Candidate candidate= request.getKey();
        Course course= request.getValue();
        candidate.getCourses().removeIf(c -> c.getId().equals(course.getId()));
        course.getCandidates().removeIf(c -> c.getId().equals(candidate.getId()));
        return new CustomPair<>(candidate,course);
    }
    private Request getRequest(Long id){
        Optional<Request> request = repository.findById(id);
        return request.orElse(null);
    }
}
