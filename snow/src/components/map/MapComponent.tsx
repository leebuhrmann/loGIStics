import React, { useRef, useEffect } from 'react';
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import TileWMS from 'ol/source/TileWMS.js';
import OSM from 'ol/source/OSM';

const MapComponent2 = () => {
  const mapRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    // Ensure the mapRef.current is not null when initializing the map
    if (mapRef.current) {
      const baseLayer = new TileLayer({
        source: new OSM(),
      });

      const wmsLayer = new TileLayer({
        source: new TileWMS({
          url: 'http://0.0.0.0:8080/geoserver/wms?',
          params: { 'LAYERS': 'topp:states', 'TILED': true },
          serverType: 'geoserver',
        })
      })

      // Initialize the map with a non-null assertion for mapRef.current
      const map = new Map({
        target: mapRef.current!,
        layers: [baseLayer, wmsLayer],
        view: new View({
          center: [0, 0],
          zoom: 2,
        }),
      });

      return () => map.setTarget(undefined); // Cleanup
    }
  }, []);

  return <div ref={mapRef} style={{ width: '100%', height: '100%' }} />;
};

export default MapComponent2;
