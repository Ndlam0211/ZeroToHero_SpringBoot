package com.lamnd.zerotohero.service;

import com.lamnd.zerotohero.dto.reponse.AuthResponse;
import com.lamnd.zerotohero.dto.reponse.IntrospectResponse;
import com.lamnd.zerotohero.dto.request.AuthRequest;
import com.lamnd.zerotohero.dto.request.IntrospectRequest;
import com.lamnd.zerotohero.entity.User;
import com.lamnd.zerotohero.exception.AppException;
import com.lamnd.zerotohero.exception.ErrorCode;
import com.lamnd.zerotohero.repository.UserRepo;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;

    @Value("${jwt.signer_key}")
    protected String SIGNER_KEY;

    public AuthResponse authenticate(AuthRequest authRequest){
        var user = userRepo.findByUsername(authRequest.getUsername())
                .orElseThrow(
                        () -> new AppException(ErrorCode.BAD_CREDENTIALS)
                );

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        if(!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())){
            throw new AppException(ErrorCode.BAD_CREDENTIALS);
        }

        var token = generateToken(user);

        return new AuthResponse(token);
    }

    public IntrospectResponse introspect(IntrospectRequest introspectRequest){
        var token = introspectRequest.getToken();

        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

            SignedJWT signedJWT = SignedJWT.parse(token);

            var verified = signedJWT.verify(verifier);

            Date expiryTime =  signedJWT.getJWTClaimsSet().getExpirationTime();

            return IntrospectResponse.builder()
                    .isValid(verified && expiryTime.after(new Date()))
                    .build();

        } catch (JOSEException | ParseException e) {
            log.error("Cannot verify token: ", e);
            System.out.println("Cannot verify token: "+e.getMessage());

            throw new RuntimeException(e);
        }
    }

    private String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("lamnd.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));

            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot generate token: ", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner scope = new StringJoiner(" ");

//        if (!CollectionUtils.isEmpty(user.getRoles())) {
//            user.getRoles().forEach(scope::add);
//        }

        return scope.toString();
    }
}
