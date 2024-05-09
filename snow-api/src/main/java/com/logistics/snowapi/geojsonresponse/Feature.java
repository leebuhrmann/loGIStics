package com.logistics.snowapi.geojsonresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.logistics.snowapi.model.Alert;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a GeoJSON feature object, specifically designed to handle the parsing of alert data
 * from external sources such as the National Weather Service (NWS). This class facilitates the extraction
 * of alert-related data encapsulated within a GeoJSON structure.
 * <p>
 * This class is annotated with {@link JsonIgnoreProperties} to ignore any unknown JSON properties,
 * ensuring compatibility and resilience against changes in the JSON data structure from external APIs.
 * <p>
 * Attributes:
 * <ul>
 *     <li>{@code id} - The unique identifier of the alert, typically provided by the external data source.</li>
 *     <li>{@code properties} - An instance of {@link FeatureProperties}, which holds detailed information about the alert.</li>
 * </ul>
 * <p>
 * Methods:
 * <ul>
 *     <li>{@link #toString()} - Provides a string representation of the feature, useful for logging and debugging.</li>
 *     <li>{@link #getFeatureAsAlert()} - Converts the feature into an {@link Alert} entity, mapping relevant properties to the corresponding fields.</li>
 * </ul>
 * <p>
 * Usage:
 * Instances of this class are typically created during the deserialization process of GeoJSON data.
 * After creation, they can be transformed into {@link Alert} entities for further processing, storage, or display within the application.
 *
 * @see FeatureProperties
 * @see Alert
 */
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