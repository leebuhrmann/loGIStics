import React, { useRef, useEffect, useState } from "react";
import "ol/ol.css";
import Map from "ol/Map";
import View from "ol/View";
import TileLayer from "ol/layer/Tile";
import TileWMS from "ol/source/TileWMS.js";
import OSM from "ol/source/OSM";
import Draw from "ol/interaction/Draw";
import VectorSource from "ol/source/Vector";
import VectorLayer from "ol/layer/Vector";
import { PlusIcon } from "@radix-ui/react-icons";
import { Button } from "@/components/ui/button";




const MapComponent = ({ onPolygonComplete }) => {
  const mapRef = useRef<HTMLDivElement | null>(null);
  const [map, setMap] = useState<Map>();
  const [source] = useState(new VectorSource());


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
        layers: [baseLayer, wmsLayer,
          new VectorLayer({
            source: source,
          })],
        view: new View({
          center: [0, 0],
          zoom: 2,
        }),
      });
      // Set map to the map reference
      map.setTarget(mapRef.current || "");
      // Set current map
      setMap(map);
      return () => map.setTarget("");
    }
  }, []);

  const startPolygonDrawing = () => {
    if (!map) return;

    // Clean up if in the middle of polygon drawing
    const existingInteraction = map
      .getInteractions()
      .getArray()
      .find((interaction) => interaction instanceof Draw);
    if (existingInteraction) {
      map.removeInteraction(existingInteraction);
    }

    // Create new polygon
    const draw = new Draw({
      source: source,
      type: "Polygon",
    });
    map.addInteraction(draw);

    // Remove the draw interaction once polygon has been created
    draw.on("drawend", () => {
      map.removeInteraction(draw);
      onPolygonComplete();
    });
  };

  return (
    <>
      <div ref={mapRef} style={{ width: "100%", height: "100%" }} />
      <Button
        onClick={startPolygonDrawing}
        variant="outline"
        size="icon"
        className="absolute top-0 right-0 m-4 rounded-full"
      >
        <PlusIcon className="h-4 w-4" />
      </Button>
    </>
  );
};
export default MapComponent;
