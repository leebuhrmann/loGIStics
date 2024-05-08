package com.logistics.snowapi.geojsonresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Class representing the properties of a GeoJSON feature specific to alerts.
 * This class captures details about weather or emergency alerts as they are defined in
 * GeoJSON data structures retrieved from external sources. It includes attributes
 * like the event type, onset and expiration times, headline, description, and affected zones.
 * <p>
 * Attributes:
 * <ul>
 *     <li>{@code event} - The type of event the alert pertains to, e.g., "Flood Warning".</li>
 *     <li>{@code onset} - The datetime when the alert becomes effective.</li>
 *     <li>{@code expires} - The datetime when the alert expires and is no longer in effect.</li>
 *     <li>{@code headline} - A brief headline summarizing the alert.</li>
 *     <li>{@code description} - A detailed description of the alert, providing comprehensive information about the event.</li>
 *     <li>{@code UgcCodeAddress} - A list of UGC (Universal Geographic Code) addresses, identifying specific areas affected by the alert.</li>
 * </ul>
 * <p>
 * Usage:
 * Instances of this class are typically populated via JSON deserialization when processing GeoJSON data
 * received from external sources. The class is designed to facilitate the easy extraction of alert properties,
 * which can then be mapped to application-specific data structures or database entities.
 *
 * @see com.fasterxml.jackson.annotation.JsonIgnoreProperties
 * @see com.fasterxml.jackson.annotation.JsonProperty
 */
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

    @JsonProperty("affectedZones")
    private ArrayList<String> UgcCodeAddress;

//    @JsonProperty("geocode")
//    private void unpackUGC(Map<String,Object> geocode) {
//        this.UGC = (ArrayList<String>) geocode.get("UGC");
//    }

}