package com.lamnd.zerotohero.dto.request;

import com.lamnd.zerotohero.annotation.DobConstraint;
import com.lamnd.zerotohero.config.AppConstants;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserUpdateRequest {
    private String password;
    private String firstName;
    private String lastName;

    @DobConstraint(minAge = AppConstants.MINAGE, message = "User must be at least " + AppConstants.MINAGE +" years old")
    private LocalDate dob;

    private List<String> roles;
}
