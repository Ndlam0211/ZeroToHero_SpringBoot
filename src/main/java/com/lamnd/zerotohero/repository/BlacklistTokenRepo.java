package com.lamnd.zerotohero.repository;

import com.lamnd.zerotohero.entity.BlacklistToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistTokenRepo extends JpaRepository<BlacklistToken, String> {
}
