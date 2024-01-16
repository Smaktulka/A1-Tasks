package com.example.dbtask.postings.controller;

import com.example.dbtask.postings.dto.DateSearchDto;
import com.example.dbtask.postings.entity.PostingsEntity;
import com.example.dbtask.postings.service.PostingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/postings")
public class PostingsController {

    private final PostingsService postingsService;

    public PostingsController(PostingsService postingsService) {
        this.postingsService = postingsService;
    }

    @GetMapping(value = "/local/file")
    public ResponseEntity<String> readPostingsCsvFile() {
        this.postingsService.readPostingsCsvFile();
        return ResponseEntity.ok("posting.csv file is read successfully");
    }


    @GetMapping("/date-search")
    public ResponseEntity<ArrayList<PostingsEntity>> getEntitiesByFilter(
            @RequestParam(value = "docDate", required = false) String docDate,
            @RequestParam(value = "postingDate", required = false) String postingDate,
            @RequestParam(value = "isDeliveryAuthorized") Boolean isDeliveryAuthorized) {
        var dto = new DateSearchDto(docDate, postingDate, isDeliveryAuthorized);
        var result = this.postingsService.getByDateFilters(dto);
        return ResponseEntity.ok(result);
    }
}
