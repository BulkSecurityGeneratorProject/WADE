package com.idscorporation.wade.web.rest;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.idscorporation.wade.WadeApp;

import com.idscorporation.wade.domain.AixmFeature;
import com.idscorporation.wade.service.AixmFeatureService;
import com.idscorporation.wade.repository.search.AixmFeatureSearchRepository;
import com.idscorporation.wade.web.rest.errors.ExceptionTranslator;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AixmFeatureResource REST controller.
 *
 * @see AixmFeatureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WadeApp.class)
public class AixmFeatureResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TIME_SLICE = "AAAAAAAAAA";
    private static final String UPDATED_TIME_SLICE = "BBBBBBBBBB";

    private static Geometry DEFAULT_GEOMETRY = null;
    private static Geometry UPDATED_GEOMETRY = null;

    private static final Integer DEFAULT_LOWER_LIMIT = 1;
    private static final Integer UPDATED_LOWER_LIMIT = 2;

    private static final Integer DEFAULT_UPPER_LIMIT = 1;
    private static final Integer UPDATED_UPPER_LIMIT = 2;

    @Autowired
    private AixmFeatureService aixmFeatureService;

    @Autowired
    private AixmFeatureSearchRepository aixmFeatureSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAixmFeatureMockMvc;

    private AixmFeature aixmFeature;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AixmFeatureResource aixmFeatureResource = new AixmFeatureResource(aixmFeatureService);
        this.restAixmFeatureMockMvc = MockMvcBuilders.standaloneSetup(aixmFeatureResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
        GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4326);
        DEFAULT_GEOMETRY = gf.createPoint(new Coordinate(12.2345678, 42.3456789));
        UPDATED_GEOMETRY = gf.createPoint(new Coordinate(12, 42));
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AixmFeature createEntity(EntityManager em) {
        GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4326);
        DEFAULT_GEOMETRY = gf.createPoint(new Coordinate(12.2345678, 42.3456789));

        AixmFeature aixmFeature = new AixmFeature()
            .identifier(DEFAULT_IDENTIFIER)
            .type(DEFAULT_TYPE)
            .timeSlice(DEFAULT_TIME_SLICE)
            .geometry(DEFAULT_GEOMETRY)
            .lowerLimit(DEFAULT_LOWER_LIMIT)
            .upperLimit(DEFAULT_UPPER_LIMIT);
        return aixmFeature;
    }

    @Before
    public void initTest() {
        aixmFeatureSearchRepository.deleteAll();
        aixmFeature = createEntity(em);
    }

    @Test
    @Transactional
    public void createAixmFeature() throws Exception {
        int databaseSizeBeforeCreate = Iterables.size(aixmFeatureSearchRepository.findAll());

        // Create the AixmFeature
        restAixmFeatureMockMvc.perform(post("/api/aixm-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aixmFeature)))
            .andExpect(status().isCreated());

        // Validate the AixmFeature in the database
        List<AixmFeature> aixmFeatureList = Lists.newArrayList(aixmFeatureSearchRepository.findAll());
        assertThat(aixmFeatureList).hasSize(databaseSizeBeforeCreate + 1);
        AixmFeature testAixmFeature = aixmFeatureList.get(aixmFeatureList.size() - 1);
        assertThat(testAixmFeature.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testAixmFeature.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAixmFeature.getTimeSlice()).isEqualTo(DEFAULT_TIME_SLICE);
        assertThat(testAixmFeature.getGeometry()).isEqualTo(DEFAULT_GEOMETRY);
        assertThat(testAixmFeature.getLowerLimit()).isEqualTo(DEFAULT_LOWER_LIMIT);
        assertThat(testAixmFeature.getUpperLimit()).isEqualTo(DEFAULT_UPPER_LIMIT);

        // Validate the AixmFeature in Elasticsearch
        AixmFeature aixmFeatureEs = aixmFeatureSearchRepository.findOne(testAixmFeature.getId());
        assertThat(aixmFeatureEs).isEqualToComparingFieldByField(testAixmFeature);
    }

    @Test
    @Transactional
    public void createAixmFeatureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = Iterables.size(aixmFeatureSearchRepository.findAll());

        // Create the AixmFeature with an existing ID
        aixmFeature.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAixmFeatureMockMvc.perform(post("/api/aixm-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aixmFeature)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AixmFeature> aixmFeatureList = Lists.newArrayList(aixmFeatureSearchRepository.findAll());
        assertThat(aixmFeatureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = Iterables.size(aixmFeatureSearchRepository.findAll());
        // set the field null
        aixmFeature.setIdentifier(null);

        // Create the AixmFeature, which fails.

        restAixmFeatureMockMvc.perform(post("/api/aixm-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aixmFeature)))
            .andExpect(status().isBadRequest());

        List<AixmFeature> aixmFeatureList = Lists.newArrayList(aixmFeatureSearchRepository.findAll());
        assertThat(aixmFeatureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = Iterables.size(aixmFeatureSearchRepository.findAll());
        // set the field null
        aixmFeature.setType(null);

        // Create the AixmFeature, which fails.

        restAixmFeatureMockMvc.perform(post("/api/aixm-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aixmFeature)))
            .andExpect(status().isBadRequest());

        List<AixmFeature> aixmFeatureList = Lists.newArrayList(aixmFeatureSearchRepository.findAll());
        assertThat(aixmFeatureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeSliceIsRequired() throws Exception {
        int databaseSizeBeforeTest = Iterables.size(aixmFeatureSearchRepository.findAll());
        // set the field null
        aixmFeature.setTimeSlice(null);

        // Create the AixmFeature, which fails.

        restAixmFeatureMockMvc.perform(post("/api/aixm-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aixmFeature)))
            .andExpect(status().isBadRequest());

        List<AixmFeature> aixmFeatureList = Lists.newArrayList(aixmFeatureSearchRepository.findAll());
        assertThat(aixmFeatureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAixmFeatures() throws Exception {
        // Initialize the database
        aixmFeature.setId(System.currentTimeMillis());
        aixmFeature = aixmFeatureSearchRepository.save(aixmFeature);

        // Get all the aixmFeatureList
        restAixmFeatureMockMvc.perform(get("/api/aixm-features?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aixmFeature.getId().longValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].timeSlice").value(hasItem(DEFAULT_TIME_SLICE.toString())))
            .andExpect(jsonPath("$.[*].geometry.type").value(hasItem(DEFAULT_GEOMETRY.getGeometryType())))
            .andExpect(jsonPath("$.[*].lowerLimit").value(hasItem(DEFAULT_LOWER_LIMIT)))
            .andExpect(jsonPath("$.[*].upperLimit").value(hasItem(DEFAULT_UPPER_LIMIT)));
    }

    @Test
    @Transactional
    public void getAixmFeature() throws Exception {
        // Initialize the database
        aixmFeature.setId(System.currentTimeMillis());
        aixmFeature = aixmFeatureSearchRepository.save(aixmFeature);

        // Get the aixmFeature
        restAixmFeatureMockMvc.perform(get("/api/aixm-features/{id}", aixmFeature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aixmFeature.getId().longValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.timeSlice").value(DEFAULT_TIME_SLICE.toString()))
            .andExpect(jsonPath("$.geometry.type").value(DEFAULT_GEOMETRY.getGeometryType()))
            .andExpect(jsonPath("$.lowerLimit").value(DEFAULT_LOWER_LIMIT))
            .andExpect(jsonPath("$.upperLimit").value(DEFAULT_UPPER_LIMIT));
    }

    @Test
    @Transactional
    public void getNonExistingAixmFeature() throws Exception {
        // Get the aixmFeature
        restAixmFeatureMockMvc.perform(get("/api/aixm-features/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAixmFeature() throws Exception {
        // Initialize the database
        aixmFeature = aixmFeatureService.save(aixmFeature);

        int databaseSizeBeforeUpdate = Iterables.size(aixmFeatureSearchRepository.findAll());

        // Update the aixmFeature
        AixmFeature updatedAixmFeature = aixmFeatureSearchRepository.findOne(aixmFeature.getId());
        updatedAixmFeature
            .identifier(UPDATED_IDENTIFIER)
            .type(UPDATED_TYPE)
            .timeSlice(UPDATED_TIME_SLICE)
            .geometry(UPDATED_GEOMETRY)
            .lowerLimit(UPDATED_LOWER_LIMIT)
            .upperLimit(UPDATED_UPPER_LIMIT);

        restAixmFeatureMockMvc.perform(put("/api/aixm-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAixmFeature)))
            .andExpect(status().isOk());

        // Validate the AixmFeature in the database
        List<AixmFeature> aixmFeatureList = Lists.newArrayList(aixmFeatureSearchRepository.findAll());
        assertThat(aixmFeatureList).hasSize(databaseSizeBeforeUpdate);
        AixmFeature testAixmFeature = aixmFeatureList.get(aixmFeatureList.size() - 1);
        assertThat(testAixmFeature.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testAixmFeature.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAixmFeature.getTimeSlice()).isEqualTo(UPDATED_TIME_SLICE);
        assertThat(testAixmFeature.getGeometry()).isEqualTo(UPDATED_GEOMETRY);
        assertThat(testAixmFeature.getLowerLimit()).isEqualTo(UPDATED_LOWER_LIMIT);
        assertThat(testAixmFeature.getUpperLimit()).isEqualTo(UPDATED_UPPER_LIMIT);

        // Validate the AixmFeature in Elasticsearch
        AixmFeature aixmFeatureEs = aixmFeatureSearchRepository.findOne(testAixmFeature.getId());
        assertThat(aixmFeatureEs).isEqualToComparingFieldByField(testAixmFeature);
    }

    @Test
    @Transactional
    public void updateNonExistingAixmFeature() throws Exception {
        int databaseSizeBeforeUpdate = Iterables.size(aixmFeatureSearchRepository.findAll());

        // Create the AixmFeature

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAixmFeatureMockMvc.perform(put("/api/aixm-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aixmFeature)))
            .andExpect(status().isCreated());

        // Validate the AixmFeature in the database
        List<AixmFeature> aixmFeatureList = Lists.newArrayList(aixmFeatureSearchRepository.findAll());
        assertThat(aixmFeatureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAixmFeature() throws Exception {
        // Initialize the database
        aixmFeatureService.save(aixmFeature);

        int databaseSizeBeforeDelete = Iterables.size(aixmFeatureSearchRepository.findAll());

        // Get the aixmFeature
        restAixmFeatureMockMvc.perform(delete("/api/aixm-features/{id}", aixmFeature.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean aixmFeatureExistsInEs = aixmFeatureSearchRepository.exists(aixmFeature.getId());
        assertThat(aixmFeatureExistsInEs).isFalse();

        // Validate the database is empty
        List<AixmFeature> aixmFeatureList = Lists.newArrayList(aixmFeatureSearchRepository.findAll());
        assertThat(aixmFeatureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAixmFeature() throws Exception {
        // Initialize the database
        aixmFeature = aixmFeatureService.save(aixmFeature);

        // Search the aixmFeature
        restAixmFeatureMockMvc.perform(get("/api/_search/aixm-features?query=id:" + aixmFeature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aixmFeature.getId().longValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].timeSlice").value(hasItem(DEFAULT_TIME_SLICE.toString())))
            .andExpect(jsonPath("$.[*].geometry.type").value(hasItem(DEFAULT_GEOMETRY.getGeometryType())))
            .andExpect(jsonPath("$.[*].lowerLimit").value(hasItem(DEFAULT_LOWER_LIMIT)))
            .andExpect(jsonPath("$.[*].upperLimit").value(hasItem(DEFAULT_UPPER_LIMIT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AixmFeature.class);
        AixmFeature aixmFeature1 = new AixmFeature();
        aixmFeature1.setId(1L);
        AixmFeature aixmFeature2 = new AixmFeature();
        aixmFeature2.setId(aixmFeature1.getId());
        assertThat(aixmFeature1).isEqualTo(aixmFeature2);
        aixmFeature2.setId(2L);
        assertThat(aixmFeature1).isNotEqualTo(aixmFeature2);
        aixmFeature1.setId(null);
        assertThat(aixmFeature1).isNotEqualTo(aixmFeature2);
    }
}
