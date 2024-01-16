package com.example.dbtask.logins.controller;

import com.example.dbtask.logins.service.LoginsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/logins")
public class LoginsController {

    private final LoginsService loginsService;

    public LoginsController (LoginsService loginsService) {
        this.loginsService = loginsService;
    }
    @GetMapping(value = "/local/file")
    public ResponseEntity<String> readLoginsCsvFile() {
        loginsService.readLoginsCsvFile();
        return ResponseEntity.ok("logins.csv is read successfully");
    }
}
