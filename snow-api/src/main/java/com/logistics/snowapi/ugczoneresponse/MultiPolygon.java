package com.logistics.snowapi.ugczoneresponse;

import java.util.List;

public class MultiPolygon extends Geometry{

    List<List<List<List<Double>>>> coordinates;

    public MultiPolygon(List<List<List<List<Double>>>> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public List<List<List<List<Double>>>> getCoordinates() {
        return coordinates;
    }
}
