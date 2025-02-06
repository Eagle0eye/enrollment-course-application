package com.online_shop.project.repositories;


import com.online_shop.project.models.Person;
import com.online_shop.project.models.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {


}
