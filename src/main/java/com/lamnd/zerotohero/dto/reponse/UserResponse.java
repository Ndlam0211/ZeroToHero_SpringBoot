package com.lamnd.zerotohero.dto.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lamnd.zerotohero.entity.Role;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String username;
    String firstName;
    String lastName;
    LocalDate dob;
    Set<Role> roles;
    String id;
}
