package com.idscorporation.wade.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idscorporation.wade.domain.AixmFeature;
import com.idscorporation.wade.service.AixmFeatureService;
import com.idscorporation.wade.web.rest.util.HeaderUtil;
import com.idscorporation.wade.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AixmFeature.
 */
@RestController
@RequestMapping("/api")
public class AixmFeatureResource {

    private final Logger log = LoggerFactory.getLogger(AixmFeatureResource.class);

    private static final String ENTITY_NAME = "aixmFeature";

    private final AixmFeatureService aixmFeatureService;

    public AixmFeatureResource(AixmFeatureService aixmFeatureService) {
        this.aixmFeatureService = aixmFeatureService;
    }

    /**
     * POST  /aixm-features : Create a new aixmFeature.
     *
     * @param aixmFeature the aixmFeature to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aixmFeature, or with status 400 (Bad Request) if the aixmFeature has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aixm-features")
    @Timed
    public ResponseEntity<AixmFeature> createAixmFeature(@Valid @RequestBody AixmFeature aixmFeature) throws URISyntaxException {
        log.debug("REST request to save AixmFeature : {}", aixmFeature);
        if (aixmFeature.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new aixmFeature cannot already have an ID")).body(null);
        }
        AixmFeature result = aixmFeatureService.save(aixmFeature);
        return ResponseEntity.created(new URI("/api/aixm-features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aixm-features : Updates an existing aixmFeature.
     *
     * @param aixmFeature the aixmFeature to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aixmFeature,
     * or with status 400 (Bad Request) if the aixmFeature is not valid,
     * or with status 500 (Internal Server Error) if the aixmFeature couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aixm-features")
    @Timed
    public ResponseEntity<AixmFeature> updateAixmFeature(@Valid @RequestBody AixmFeature aixmFeature) throws URISyntaxException {
        log.debug("REST request to update AixmFeature : {}", aixmFeature);
        if (aixmFeature.getId() == null) {
            return createAixmFeature(aixmFeature);
        }
        AixmFeature result = aixmFeatureService.save(aixmFeature);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aixmFeature.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aixm-features : get all the aixmFeatures.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of aixmFeatures in body
     */
    @GetMapping("/aixm-features")
    @Timed
    public ResponseEntity<List<AixmFeature>> getAllAixmFeatures(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AixmFeatures");
        Page<AixmFeature> page = aixmFeatureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/aixm-features");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /aixm-features/:id : get the "id" aixmFeature.
     *
     * @param id the id of the aixmFeature to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aixmFeature, or with status 404 (Not Found)
     */
    @GetMapping("/aixm-features/{id}")
    @Timed
    public ResponseEntity<AixmFeature> getAixmFeature(@PathVariable Long id) {
        log.debug("REST request to get AixmFeature : {}", id);
        AixmFeature aixmFeature = aixmFeatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(aixmFeature));
    }

    /**
     * DELETE  /aixm-features/:id : delete the "id" aixmFeature.
     *
     * @param id the id of the aixmFeature to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aixm-features/{id}")
    @Timed
    public ResponseEntity<Void> deleteAixmFeature(@PathVariable Long id) {
        log.debug("REST request to delete AixmFeature : {}", id);
        aixmFeatureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/aixm-features?query=:query : search for the aixmFeature corresponding
     * to the query.
     *
     * @param query the query of the aixmFeature search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/aixm-features")
    @Timed
    public ResponseEntity<List<AixmFeature>> searchAixmFeatures(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of AixmFeatures for query {}", query);
        Page<AixmFeature> page = aixmFeatureService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/aixm-features");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
