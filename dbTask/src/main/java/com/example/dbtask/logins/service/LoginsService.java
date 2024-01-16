package com.example.dbtask.logins.service;

import com.example.dbtask.logins.entity.LoginsEntity;

public interface LoginsService {

    void readLoginsCsvFile();

    LoginsEntity getByAppAccountName(String appAccountName);

    Boolean isLoginsRead();
}
