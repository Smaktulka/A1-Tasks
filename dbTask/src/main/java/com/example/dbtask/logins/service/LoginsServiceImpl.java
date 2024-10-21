package com.example.dbtask.logins.service;

import com.example.dbtask.logins.entity.LoginsEntity;
import com.example.dbtask.logins.repository.LoginsRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class LoginsServiceImpl implements LoginsService{

    private final Environment env;

    private final LoginsRepository loginsRepository;


    public LoginsServiceImpl(
            Environment env,
            LoginsRepository loginsRepository
    ) {
        this.env = env;
        this.loginsRepository = loginsRepository;
    }

    private void storeEntryFromFile(String entry) {
        if (entry.isEmpty()) {
            return;
        }
        String[] values = entry.split(",\t"); // in posting.csv ,\t is delimiter
        var loginsEntity = LoginsEntity.builder()
                .application(values[0])
                .appAccountName(values[1])
                .isActive(values[2])
                .jobTitle(values[3])
                .department(values[4])
                .build();

        this.loginsRepository.save(loginsEntity);
    }

    @Override
    public void readLoginsCsvFile() {
        String loginsPath = env.getProperty("logins.path");
        try (var buffer = new BufferedReader(new FileReader(loginsPath))) {
            buffer.readLine(); // skip line with column's names
            while (true) {
                String line = buffer.readLine();
                if (line == null) {
                    break;
                }

                storeEntryFromFile(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("logins.csv not found.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public LoginsEntity getByAppAccountName(String appAccountName) {
        return loginsRepository. getByAppAccountName(appAccountName);
    }

    @Override
    public Boolean isLoginsRead() {
        if (loginsRepository.count() > 0) {
            return true;
        }

        return false;
    }
}
