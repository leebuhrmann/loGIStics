//package com.logistics.snowapi.ugczoneresponse;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Getter;
//import lombok.Setter;
//
//
//import java.util.List;
//
//@Getter
//@Setter
//@JsonIgnoreProperties(ignoreUnknown = true)
//public abstract class Geometry {
//    /**
//     * Returns geospatial coordinates deserialized from a GeoJSON.
//     * <p>
//     * It's important to check the {@code type} of geometry prior to utilizing the returned coordinates
//     * to ensure the appropriate handling of the nested list structure. Failure to do so may result in
//     * {@link ClassCastException} or improper data interpretation.
//     * </p>
//     * <p>
//     * The exact return type depends on the geometry type:
//     * <ul>
//     *     <li>Polygon - {@code List<List<List<Double>>>}</li>
//     *     <li>MultiPolygon - {@code List<List<List<List<Double>>>>}</li>
//     * </ul>
//     * </p>
//     * <p><strong>Warning:</strong> Always verify the geometry type before processing the coordinates to
//     * avoid runtime errors. Use conditional logic to differentiate between {@code Polygon} and
//     * {@code MultiPolygon} types, as demonstrated in the examples below.</p>
//     * <pre>
//     * if (geometryType.equals("Polygon")) {
//     *     // Handle Polygon
//     * } else if (geometryType.equals("MultiPolygon")) {
//     *     // Handle MultiPolygon
//     * }
//     * </pre>
//     *
//     * @return Nested list of coordinates. The structure varies based on the geometry type.
//     */
//    public abstract Object getCoordinates();
//}
