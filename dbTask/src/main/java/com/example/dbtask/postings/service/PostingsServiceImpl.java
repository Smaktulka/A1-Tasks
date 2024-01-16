package com.example.dbtask.postings.service;

import com.example.dbtask.cache.InMemoryCache;
import com.example.dbtask.logins.service.LoginsService;
import com.example.dbtask.postings.dto.DateSearchDto;
import com.example.dbtask.postings.entity.PostingsEntity;
import com.example.dbtask.postings.repository.PostingsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

@Service
public class PostingsServiceImpl implements PostingsService {

    private final Environment env;

    private final EntityManager entityManager;

    private final LoginsService loginsService;

    private final InMemoryCache<String, Boolean> cache;

    private final PostingsRepository postingsRepository;

    private final CriteriaBuilder criteriaBuilder;


    public PostingsServiceImpl(
            Environment env,
            EntityManager entityManager, LoginsService loginsService,
            InMemoryCache<String, Boolean> cache,
            PostingsRepository postingsRepository
    ) {
        this.env = env;
        this.entityManager = entityManager;
        this.loginsService = loginsService;
        this.cache = cache;
        this.postingsRepository = postingsRepository;
        this.criteriaBuilder = this.entityManager.getCriteriaBuilder();
    }

    private void storeEntry(String[] values, Boolean isDeliveryAuthorized) throws ParseException {
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        var postingEntity = new PostingsEntity().builder()
                .matDoc(Long.parseLong(values[0]))
                .item(Integer.parseInt(values[1]))
                .docDate(values[2])
                .postingDate(values[3])
                .description(values[4])
                .quantity(Integer.parseInt(values[5]))
                .bun(values[6])
                .amountLC(format.parse(values[7]).doubleValue())
                .currency(values[8])
                .userName(values[9])
                .isDeliveryAuthorized(isDeliveryAuthorized)
                .build();

        this.postingsRepository.save(postingEntity);
    }

    private void storeEntryFromFile(String entry) throws ParseException {
        if (entry.isEmpty()) {
            return;
        }

        String[] values = entry.split(";\t"); // in posting.csv ;\t is delimiter
        String userName = values[9];                // get USER_NAME from entry
        var isDeliveryAuthorized = cache.get(userName);
        if (isDeliveryAuthorized != null) {
            storeEntry(values, isDeliveryAuthorized);
            return;
        }

        var loginsEntity = this.loginsService.getByAppAccountName(userName);
        isDeliveryAuthorized = loginsEntity == null ? false : true;
        storeEntry(values, isDeliveryAuthorized);

        cache.save(userName, isDeliveryAuthorized);
    }


    @Override
    public void readPostingsCsvFile() {
        if (loginsService.isLoginsRead() == false) {
            throw new RuntimeException("logins.csv has not yet been read.\n");
        }

        String postingsPath = this.env.getProperty("postings.path");
        try (var buffer = new BufferedReader(new FileReader(postingsPath))) {
            buffer.readLine(); // skip line with column's names
            while (true) {
                String line = buffer.readLine();
                if (line == null) {
                    break;
                }

                storeEntryFromFile(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("postings.csv not found.");
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private Predicate[] getDatePredicate(Root<PostingsEntity> entity, DateSearchDto dto) {
        var predicates = new ArrayList<Predicate>();
        String postingDate = dto.getPostingDate();
        String docDate = dto.getDocDate();
        Boolean isDeliveryAuthorized = dto.getIsDeliveryAuthorized();
        // query clause for postingDate equality check
        if (postingDate != null && !postingDate.isEmpty()) {
            Predicate postingDatePredicate = criteriaBuilder.equal(entity.get("postingDate"), postingDate);
            predicates.add(postingDatePredicate);
        }

        // query clause for docDate equality check
        if (docDate != null && !docDate.isEmpty()) {
            Predicate docDatePredicate = criteriaBuilder.equal(entity.get("docDate"), docDate);
            predicates.add(docDatePredicate);
        }

        // query clause for isDeliveryAuthorized check
        Predicate deliveryAuthPredicate = criteriaBuilder.equal(entity.get("isDeliveryAuthorized"), isDeliveryAuthorized);
        predicates.add(deliveryAuthPredicate);

        return predicates.toArray(Predicate[]::new);
    }
    @Override
    public ArrayList<PostingsEntity> getByDateFilters(DateSearchDto dto) {
        CriteriaQuery<PostingsEntity> criteriaQuery = this.criteriaBuilder.createQuery(PostingsEntity.class);

        Root<PostingsEntity> entity = criteriaQuery.from(PostingsEntity.class);

        Predicate[] predicates = getDatePredicate(entity, dto);
        // creating where clause with AND logic
        criteriaQuery.where(predicates);
        // query format for db
        TypedQuery<PostingsEntity> query = entityManager.createQuery(criteriaQuery);

        return new ArrayList<>(query.getResultList());
    }

}
