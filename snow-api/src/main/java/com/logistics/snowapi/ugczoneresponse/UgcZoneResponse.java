package com.logistics.snowapi.ugczoneresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Map;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UgcZoneResponse {
    private String type;
    private Geometry geometry;

    @JsonProperty("geometry")
    private void geoMapper(Map<String,Object> geometryMap) {
        ObjectMapper mapper = new ObjectMapper();
        type = (String) geometryMap.get("type");
        Object coordinates = geometryMap.get("coordinates");

        try {
            if ("Polygon".equals(type)) {
                List<List<List<Double>>> coords = mapper.convertValue(coordinates, new TypeReference<List<List<List<Double>>>>() {});
                this.geometry = new Polygon(coords);
            } else if ("MultiPolygon".equals(type)) {
                List<List<List<List<Double>>>> coords = mapper.convertValue(coordinates, new TypeReference<List<List<List<List<Double>>>>>() {});
                this.geometry = new MultiPolygon(coords);
            } else {
                System.out.println("Unsupported geometry type: " + type);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error casting coordinates: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
