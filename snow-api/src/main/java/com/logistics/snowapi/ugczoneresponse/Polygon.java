package com.logistics.snowapi.ugczoneresponse;

import java.util.List;

public class Polygon extends Geometry{

    List<List<List<Double>>> coordinates;

    public Polygon(List<List<List<Double>>> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public List<List<List<Double>>> getCoordinates() {
        return coordinates;
    }
}
