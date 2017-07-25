package com.idscorporation.wade.domain;

import aero.aixm.schema.x51.AbstractAIXMFeatureDocument;
import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AixmFeature.
 */
@Document(indexName = "aixmfeature", type = "aixmfeature", shards = 6, replicas = 1, refreshInterval = "30s")
@Mapping(mappingPath = "/config/elasticsearch/AixmFeature.json")
public class AixmFeature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    private String identifier;

    @NotNull
    private String type;

    @NotNull
    private AbstractAIXMFeatureDocument timeSlice;

    private Geometry geometry;

    private Integer lowerLimit;

    private Integer upperLimit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public AixmFeature id(Long id) {
        this.id = id;
        return this;
    }

    public AixmFeature identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getType() {
        return type;
    }

    public AixmFeature type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AbstractAIXMFeatureDocument getTimeSlice() {
        return timeSlice;
    }

    public AixmFeature timeSlice(AbstractAIXMFeatureDocument timeSlice) {
        this.timeSlice = timeSlice;
        return this;
    }

    public void setTimeSlice(AbstractAIXMFeatureDocument timeSlice) {
        this.timeSlice = timeSlice;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public AixmFeature geometry(Geometry geometry) {
        this.geometry = geometry;
        return this;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Integer getLowerLimit() {
        return lowerLimit;
    }

    public AixmFeature lowerLimit(Integer lowerLimit) {
        this.lowerLimit = lowerLimit;
        return this;
    }

    public void setLowerLimit(Integer lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Integer getUpperLimit() {
        return upperLimit;
    }

    public AixmFeature upperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
        return this;
    }

    public void setUpperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AixmFeature aixmFeature = (AixmFeature) o;
        if (aixmFeature.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aixmFeature.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AixmFeature{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", type='" + getType() + "'" +
            ", timeSlice='" + getTimeSlice() + "'" +
            ", geometry='" + getGeometry() + "'" +
            ", lowerLimit='" + getLowerLimit() + "'" +
            ", upperLimit='" + getUpperLimit() + "'" +
            "}";
    }
}
