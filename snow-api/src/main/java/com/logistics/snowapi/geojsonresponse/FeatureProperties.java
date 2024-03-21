package com.logistics.snowapi.geojsonresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Map;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureProperties {

    @JsonProperty("event")
    private String event;

    @JsonProperty("onset")
    private OffsetDateTime onset;

    @JsonProperty("expires")
    private OffsetDateTime expires;

    @JsonProperty("headline")
    private String headline;

    @JsonProperty("description")
    private String description;

    private ArrayList<String> UGC;

    @SuppressWarnings("unchecked")
    @JsonProperty("geocode")
    private void unpackUGC(Map<String,Object> geocode) {
        this.UGC = (ArrayList<String>) geocode.get("UGC");
    }

}