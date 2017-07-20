package com.idscorporation.wade.service;

import com.idscorporation.wade.domain.AixmFeature;
import com.idscorporation.wade.repository.search.AixmFeatureSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Random;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AixmFeature.
 */
@Service
@Transactional
public class AixmFeatureService {

    private final Logger log = LoggerFactory.getLogger(AixmFeatureService.class);

    private final AixmFeatureSearchRepository aixmFeatureSearchRepository;

    public AixmFeatureService(AixmFeatureSearchRepository aixmFeatureSearchRepository) {
        this.aixmFeatureSearchRepository = aixmFeatureSearchRepository;
    }

    /**
     * Save a aixmFeature.
     *
     * @param aixmFeature the entity to save
     * @return the persisted entity
     */
    public AixmFeature save(AixmFeature aixmFeature) {
        log.debug("Request to save AixmFeature : {}", aixmFeature);
        if (aixmFeature.getId()==null)
            aixmFeature.setId(System.currentTimeMillis());
        AixmFeature result = aixmFeatureSearchRepository.save(aixmFeature);
        return result;
    }

    /**
     *  Get all the aixmFeatures.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AixmFeature> findAll(Pageable pageable) {
        log.debug("Request to get all AixmFeatures");
        return aixmFeatureSearchRepository.findAll(pageable);
    }

    /**
     *  Get one aixmFeature by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AixmFeature findOne(Long id) {
        log.debug("Request to get AixmFeature : {}", id);
        return aixmFeatureSearchRepository.findOne(id);
    }

    /**
     *  Delete the  aixmFeature by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AixmFeature : {}", id);
        aixmFeatureSearchRepository.delete(id);
    }

    /**
     * Search for the aixmFeature corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AixmFeature> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AixmFeatures for query {}", query);
        Page<AixmFeature> result = aixmFeatureSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
