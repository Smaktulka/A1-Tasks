package com.example.dbtask.postings.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "postings")
public class PostingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long matDoc;
    private Integer item;
    private String docDate;
    private String postingDate;
    private String description;
    private Integer quantity;
    private String bun;
    private Double amountLC;
    private String currency;
    private String userName;
    private Boolean isDeliveryAuthorized;
}
