package com.idscorporation.wade.repository.search;

import com.idscorporation.wade.domain.AixmFeature;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AixmFeature entity.
 */
public interface AixmFeatureSearchRepository extends ElasticsearchRepository<AixmFeature, Long> {
}
