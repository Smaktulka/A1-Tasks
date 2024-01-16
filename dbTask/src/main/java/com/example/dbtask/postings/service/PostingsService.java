package com.example.dbtask.postings.service;

import com.example.dbtask.postings.dto.DateSearchDto;
import com.example.dbtask.postings.entity.PostingsEntity;

import java.util.ArrayList;
import java.util.Date;

public interface PostingsService {
    void readPostingsCsvFile();

    ArrayList<PostingsEntity> getByDateFilters(DateSearchDto dto);
}
