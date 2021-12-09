package com.proyecto.account.service;

import com.proyecto.account.dto.UserRegistrationDto;
import com.proyecto.account.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


public interface IUserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
    User findByUsername(String username);
}
