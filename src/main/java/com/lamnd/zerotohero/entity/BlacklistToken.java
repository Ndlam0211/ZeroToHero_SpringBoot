package com.lamnd.zerotohero.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BlacklistToken {
    @Id
    private String id;
    private Date expiryTime;
}
