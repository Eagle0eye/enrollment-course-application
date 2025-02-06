package com.online_shop.project.services;


import com.online_shop.project.dto.updateUserForm;
import com.online_shop.project.enums.Role;
import com.online_shop.project.models.SystemUser;
import com.online_shop.project.repositories.SystemUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private SystemUserRepository userRepository;


    @Override
    public List<SystemUser> viewModerators() {
        List<SystemUser> moderators = userRepository.findSystemUsersByRole(Role.MODERATOR);
        return (moderators.size()==0)?new ArrayList<>():moderators;
    }

    @Override
    public void insertModerator(updateUserForm form) {
        SystemUser user = new SystemUser();
        user.setFirst_name(form.getFirst_name());
        user.setLast_name(form.getLast_name());
        user.setEmail(form.getEmail());
        user.setPhone(form.getPhone());
        user.setUsername(form.getUsername());
        user.setPassword("0000");
        user.setRole(Role.MODERATOR);
        userRepository.save(user);
    }

    @Override
    public SystemUser showAdminProfile(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void editAdminProfile(Long id, updateUserForm form) {
        Optional<SystemUser> search_user = userRepository.findById(id);
        if (search_user.isPresent())
        {
            userRepository.save(assignFormToUser(search_user.get(),form,Role.ADMIN));
        }
        else
            throw  new RuntimeException("Not Found");

    }

    @Override
    public void removeModerator(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void editModeratorProfile(Long id, updateUserForm form) {
        Optional<SystemUser> search_user = userRepository.findById(id);
        if (search_user.isPresent())
        {
            userRepository.save(assignFormToUser(search_user.get(),form,Role.MODERATOR));
        }
        else
            throw  new RuntimeException("Not Found");

    }




    private SystemUser assignFormToUser(SystemUser user,updateUserForm form,Role role)
    {
        SystemUser found_user;
        found_user = user;
        found_user.setFirst_name(form.getFirst_name());
        found_user.setLast_name(form.getLast_name());
        found_user.setEmail(form.getEmail());
        found_user.setPhone(form.getPhone());
        found_user.setRole(role);
        return  found_user;
    }
}
