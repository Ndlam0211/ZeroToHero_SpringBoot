package com.lamnd.zerotohero.dto.request;

import jakarta.validation.constraints.NotBlank;
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
     LocalDate dob;
}
