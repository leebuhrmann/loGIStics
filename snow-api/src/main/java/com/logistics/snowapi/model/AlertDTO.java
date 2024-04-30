package com.logistics.snowapi.model;

import java.time.OffsetDateTime;

public class AlertDTO {
    private Integer id;
    private String event;
    private OffsetDateTime onset;
    private OffsetDateTime expires;
    private String headline;
    private String description;


    public AlertDTO(Alert alert) {
        this.id = alert.getId();
        this.event = alert.getEvent();
        this.onset = alert.getOnset();
        this.expires = alert.getExpires();
        this.headline = alert.getHeadline();
        this.description = alert.getDescription();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public OffsetDateTime getOnset() {
        return onset;
    }

    public void setOnset(OffsetDateTime onset) {
        this.onset = onset;
    }

    public OffsetDateTime getExpires() {
        return expires;
    }

    public void setExpires(OffsetDateTime expires) {
        this.expires = expires;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }
    
}
