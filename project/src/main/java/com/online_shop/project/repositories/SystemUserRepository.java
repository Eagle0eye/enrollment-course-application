package com.online_shop.project.repositories;


import com.online_shop.project.enums.Role;
import com.online_shop.project.models.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser,Long> {
    SystemUser findSystemUserByEmail(String email);
    List<SystemUser> findSystemUsersByRole(Role role);
}
