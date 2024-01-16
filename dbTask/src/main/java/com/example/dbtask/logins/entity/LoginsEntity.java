package com.example.dbtask.logins.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "logins")
public class LoginsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String application;
    private String appAccountName;
    private String isActive;
    private String jobTitle;
    private String department;
}
