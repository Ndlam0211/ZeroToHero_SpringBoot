package com.lamnd.zerotohero.repository;

import com.lamnd.zerotohero.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepo extends JpaRepository<Permission, String> {
}
