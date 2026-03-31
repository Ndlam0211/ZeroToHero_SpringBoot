package com.lamnd.zerotohero.controller;

import com.lamnd.zerotohero.dto.reponse.APIResponse;
import com.lamnd.zerotohero.dto.reponse.AuthResponse;
import com.lamnd.zerotohero.dto.reponse.IntrospectResponse;
import com.lamnd.zerotohero.dto.request.AuthRequest;
import com.lamnd.zerotohero.dto.request.IntrospectRequest;
import com.lamnd.zerotohero.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public APIResponse<AuthResponse> login(@RequestBody AuthRequest authRequest){
        AuthResponse authResponse = authService.authenticate(authRequest);

        return APIResponse.<AuthResponse>builder()
                .data(authResponse)
                .build();
    }

    @PostMapping("/introspect")
    public APIResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest introspectRequest){
        IntrospectResponse introspectResponse = authService.introspect(introspectRequest);

        return APIResponse.<IntrospectResponse>builder()
                .code(200)
                .data(introspectResponse)
                .build();
    }
}
