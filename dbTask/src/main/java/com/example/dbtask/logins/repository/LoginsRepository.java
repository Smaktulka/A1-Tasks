package com.example.dbtask.logins.repository;

import com.example.dbtask.logins.entity.LoginsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginsRepository extends JpaRepository<LoginsEntity, Long> {
    LoginsEntity getByAppAccountName(String appAccountName);
}
