package com.lamnd.zerotohero.service;

import com.lamnd.zerotohero.dto.reponse.AuthResponse;
import com.lamnd.zerotohero.dto.reponse.IntrospectResponse;
import com.lamnd.zerotohero.dto.request.AuthRequest;
import com.lamnd.zerotohero.dto.request.IntrospectRequest;
import com.lamnd.zerotohero.dto.request.LogoutRequest;
import com.lamnd.zerotohero.entity.BlacklistToken;
import com.lamnd.zerotohero.exception.AppException;
import com.lamnd.zerotohero.exception.ErrorCode;
import com.lamnd.zerotohero.repository.BlacklistTokenRepo;
import com.lamnd.zerotohero.repository.UserRepo;
import com.lamnd.zerotohero.security.JwtUtil;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final BlacklistTokenRepo blacklistTokenRepo;
    private final JwtUtil jwtUtil;

    public AuthResponse authenticate(AuthRequest authRequest){
        var user = userRepo.findByUsername(authRequest.getUsername())
                .orElseThrow(
                        () -> new AppException(ErrorCode.BAD_CREDENTIALS)
                );

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        if(!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())){
            throw new AppException(ErrorCode.BAD_CREDENTIALS);
        }

        var token = jwtUtil.generateToken(user);

        return new AuthResponse(token);
    }

    public void logout(LogoutRequest logoutRequest) {
        try {
            SignedJWT signedToken = jwtUtil.verifyToken(logoutRequest.getToken());

            String jwtId = signedToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signedToken.getJWTClaimsSet().getExpirationTime();

            BlacklistToken blacklistToken = BlacklistToken.builder()
                    .id(jwtId)
                    .expiryTime(expiryTime)
                    .build();

            blacklistTokenRepo.save(blacklistToken);
        } catch (JOSEException | ParseException e) {
            log.error("Cannot verify token: ", e);

            throw new RuntimeException(e);
        }
    }

    public IntrospectResponse introspect(IntrospectRequest introspectRequest){
        var token = introspectRequest.getToken();
        boolean isValid = true;

        try {
            jwtUtil.verifyToken(token);
        } catch (JOSEException | ParseException e) {
            log.error("Cannot verify token: ", e);

            throw new RuntimeException(e);
        } catch (AppException e){
            isValid = false;
        }

        return IntrospectResponse.builder()
                .isValid(isValid)
                .build();
    }


}
