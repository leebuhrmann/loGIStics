package com.logistics.snowapi.geojsonresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * Class representing the response structure for GeoJSON data, particularly used for handling
 * data that includes geographic features related to alerts or other geographic information.
 * This class captures the array of features as defined in standard GeoJSON format.
 * <p>
 * Attributes:
 * <ul>
 *     <li>{@code features} - A list of {@link Feature} objects, each representing a distinct geographic feature
 *     along with its properties and geometry, as detailed in the received GeoJSON data.</li>
 * </ul>
 * <p>
 * Usage:
 * Instances of this class are primarily used for JSON deserialization when GeoJSON data is received from external
 * sources, such as web APIs providing weather alerts or geographic mapping data. The class facilitates the extraction
 * and processing of the 'features' part of the GeoJSON, making it accessible for further processing within the application,
 * such as converting them into domain-specific entities or performing geographic data analysis.
 * <p>
 * This class is annotated with {@link JsonIgnoreProperties} to ensure that it can handle variations in GeoJSON
 * structures gracefully by ignoring unknown properties, thus enhancing compatibility and robustness when interfacing
 * with various external data sources.
 *
 * @see Feature
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoJsonResponse {
    @JsonProperty("features")
    private List<Feature> features;
}
