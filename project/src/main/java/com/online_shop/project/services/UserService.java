package com.online_shop.project.services;

import com.online_shop.project.models.Person;
import com.online_shop.project.dto.updateUserForm;
import com.online_shop.project.dto.changePasswordForm;
import com.online_shop.project.models.SystemUser;

import java.util.List;


public interface UserService {
    List<SystemUser> viewModerators();
    void insertModerator(updateUserForm form);
    SystemUser showAdminProfile(Long id);
    void editAdminProfile(Long id,updateUserForm form);
    void removeModerator(Long id);

    void editModeratorProfile(Long id,updateUserForm form);
}
