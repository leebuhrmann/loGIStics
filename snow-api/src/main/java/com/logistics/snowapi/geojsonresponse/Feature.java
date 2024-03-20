package com.logistics.snowapi.geojsonresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Feature {
    @JsonProperty("properties")
    private FeatureProperties properties;

    public FeatureProperties getProperties() {
        return properties;
    }

    public void setProperties(FeatureProperties properties) {
        this.properties = properties;
    }

    public String toString() {
        return String.format("event: %s" +
                            "\nheadline: %s" +
                            "\nonset: %s" +
                            "\nexpires: %s" +
                            "\nUGC: %s" +
                            "\ndescription: %s",
                properties.getEvent(), properties.getHeadline(), properties.getOnset(), properties.getExpires(), properties.getUGC(), properties.getDescription());
    }
}