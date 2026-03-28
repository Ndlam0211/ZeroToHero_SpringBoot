package com.lamnd.zerotohero.service;

import com.lamnd.zerotohero.dto.request.UserCreationRequest;
import com.lamnd.zerotohero.entity.User;
import com.lamnd.zerotohero.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User createRequest(UserCreationRequest request){

    }
}
