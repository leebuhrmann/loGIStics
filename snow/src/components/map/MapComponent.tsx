import React, { useRef, useEffect, useState } from "react";
import "ol/ol.css";
import Map from "ol/Map";
import View from "ol/View";
import TileLayer from "ol/layer/Tile";
import OSM from "ol/source/OSM";

import {ImageWMS} from "ol/source";

const MapComponent = () => {
  const mapRef = useRef(null);
  const [map, setMap] = useState<Map>()

  useEffect(() => {
    if (mapRef.current) {
      const map = new Map({
        target: mapRef.current as HTMLElement,
        layers: [
          new TileLayer({
            source: new OSM(),
          }),
        ],
        view: new View({
          center: [0, 0],
          zoom: 2,
        }),
      });
      // Set map to the map reference
      map.setTarget(mapRef.current || "")
      // Set current map
      setMap(map)
      return () => map.setTarget("")
    }
  }, []);

  return <div ref={mapRef} style={{ width: "100%", height: "100%" }} />;
};
export default MapComponent;