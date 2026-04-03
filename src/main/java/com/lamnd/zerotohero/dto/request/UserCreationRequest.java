package com.lamnd.zerotohero.dto.request;

import com.lamnd.zerotohero.annotation.DobConstraint;
import com.lamnd.zerotohero.config.AppConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @NotBlank
    String username;
    @Size(min = 8, message = "password must be at least 8 characters")
    String password;
    String firstName;
    String lastName;

    @DobConstraint(minAge = AppConstants.MINAGE, message = "User must be at least " + AppConstants.MINAGE +" years old")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date of birth must be in the format YYYY-MM-DD")
    LocalDate dob;
}
