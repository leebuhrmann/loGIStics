package com.logistics.snowapi.geojsonresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.logistics.snowapi.model.Alert;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Feature {
    @JsonProperty("id")
    private String id;

    @JsonProperty("properties")
    private FeatureProperties properties;

    public String toString() {
        return String.format("id: %s" +
                            "\nevent: %s" +
                            "\nheadline: %s" +
                            "\nonset: %s" +
                            "\nexpires: %s" +
                            "\naffected-zones: %s" +
                            "\ndescription: %s",
                this.id, properties.getEvent(), properties.getHeadline(), properties.getOnset(), properties.getExpires(), properties.getUgcCodeAddress(), properties.getDescription());
    }

    public Alert getFeatureAsAlert() {
        Alert alert = new Alert();
        alert.setEvent(properties.getEvent());
        alert.setOnset(properties.getOnset());
        alert.setExpires(properties.getExpires());
        alert.setHeadline(properties.getHeadline());
        alert.setDescription(properties.getDescription());
        alert.setNwsID(this.id);

        return alert;
    }
}