package com.logistics.snowapi.geojsonresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Feature {
    @JsonProperty("id")
    private String id;

    @JsonProperty("properties")
    private FeatureProperties properties;

    public FeatureProperties getProperties() {
        return properties;
    }

    public void setProperties(FeatureProperties properties) {
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        return String.format("id: %s" +
                            "\nevent: %s" +
                            "\nheadline: %s" +
                            "\nonset: %s" +
                            "\nexpires: %s" +
                            "\nUGC: %s" +
                            "\ndescription: %s",
                this.id, properties.getEvent(), properties.getHeadline(), properties.getOnset(), properties.getExpires(), properties.getUGC(), properties.getDescription());
    }
}