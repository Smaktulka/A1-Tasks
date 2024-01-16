package com.example.dbtask.postings.repository;

import com.example.dbtask.postings.entity.PostingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface PostingsRepository extends JpaRepository<PostingsEntity, Long> {

}
