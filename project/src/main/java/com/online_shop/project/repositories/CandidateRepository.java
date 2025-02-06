package com.online_shop.project.repositories;

import com.online_shop.project.models.Candidate;
import com.online_shop.project.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CandidateRepository extends JpaRepository<Candidate,Long> {
}
