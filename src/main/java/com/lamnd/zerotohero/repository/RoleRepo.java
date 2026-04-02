package com.lamnd.zerotohero.repository;

import com.lamnd.zerotohero.entity.Permission;
import com.lamnd.zerotohero.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, String> {
}
