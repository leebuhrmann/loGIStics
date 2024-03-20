package com.logistics.snowapi.geojsonresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureProperties {

    @JsonProperty("event")
    private String event;

    @JsonProperty("onset")
    private String onset;

    @JsonProperty("expires")
    private String expires;

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

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getOnset() {
        return onset;
    }

    public void setOnset(String onset) {
        this.onset = onset;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getUGC() {
        return UGC;
    }

    public void setUGC(ArrayList<String> UGC) {
        this.UGC = UGC;
    }
}