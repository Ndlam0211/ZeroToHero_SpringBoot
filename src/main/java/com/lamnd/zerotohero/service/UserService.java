package com.lamnd.zerotohero.service;

import com.lamnd.zerotohero.dto.reponse.UserResponse;
import com.lamnd.zerotohero.dto.request.UserCreationRequest;
import com.lamnd.zerotohero.dto.request.UserUpdateRequest;
import com.lamnd.zerotohero.entity.User;
import com.lamnd.zerotohero.exception.AppException;
import com.lamnd.zerotohero.exception.ErrorCode;
import com.lamnd.zerotohero.mapper.UserMapper;
import com.lamnd.zerotohero.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    private final UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request){

        if(userRepo.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);

        return userMapper.toDTO(userRepo.save(user));
    }

    public List<UserResponse> getAllUser() {
        return userMapper.toListDTO(userRepo.findAll());
    }

    public UserResponse getUserById(String userId) {
        return userMapper.toDTO(findUserById(userId));
    }

    public User updateUserById(String userId, UserUpdateRequest request) {
        User user = findUserById(userId);

        userMapper.updateUser(user, request);

        return userRepo.save(user);
    }

    public void deleteUserById(String userId) {
        userRepo.deleteById(userId);
    }

    private User findUserById(String userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found")
        );
    }
}
