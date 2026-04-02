package com.lamnd.zerotohero.service;

import com.lamnd.zerotohero.dto.reponse.UserResponse;
import com.lamnd.zerotohero.dto.request.UserCreationRequest;
import com.lamnd.zerotohero.dto.request.UserUpdateRequest;
import com.lamnd.zerotohero.entity.User;
import com.lamnd.zerotohero.enums.Role;
import com.lamnd.zerotohero.exception.AppException;
import com.lamnd.zerotohero.exception.ErrorCode;
import com.lamnd.zerotohero.mapper.UserMapper;
import com.lamnd.zerotohero.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request){

        if(userRepo.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        User user = userMapper.toUser(request);

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

//        user.setRoles(roles);

        return userMapper.toDTO(userRepo.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')") // only admin able to access
    public List<UserResponse> getAllUser() {
        return userMapper.toListDTO(userRepo.findAll());
    }

    @PostAuthorize("returnObject.username == authentication.name") // users can only get their own information, can't get other users information
    public UserResponse getUserById(String userId) {
        return userMapper.toDTO(findUserById(userId));
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();

        String username = context.getAuthentication().getName();

        User user = findUserByUsername(username);

        return userMapper.toDTO(user);
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

    private User findUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User Not Found")
        );
    }
}
