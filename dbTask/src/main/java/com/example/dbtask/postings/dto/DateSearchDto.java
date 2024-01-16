package com.example.dbtask.postings.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DateSearchDto {
    @Nullable
    private String docDate;

    @Nullable
    private String postingDate;

    private Boolean isDeliveryAuthorized;
}
