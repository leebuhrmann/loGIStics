import React, { useRef, useEffect, useState } from "react";
import "ol/ol.css";
import Map from "ol/Map";
import View from "ol/View";
import TileLayer from "ol/layer/Tile";
import TileWMS from "ol/source/TileWMS.js";
import OSM from "ol/source/OSM";
import { Modify, Select, Snap, Draw, Interaction } from "ol/interaction.js";
import VectorSource from "ol/source/Vector";
import VectorLayer from "ol/layer/Vector";
import { Pencil1Icon, PlusIcon } from "@radix-ui/react-icons";
import { Button } from "@/components/ui/button";
import Collection from "ol/Collection";

interface MapComponentProps {
  onPolygonComplete: () => void;
  onPolygonSelect: () => void;
  clearPolygon: boolean;
  onClearComplete: () => void;
}

const MapComponent: React.FC<MapComponentProps> = ({
  onPolygonComplete,
  onPolygonSelect,
  clearPolygon,
  onClearComplete,
}) => {
  const mapRef = useRef<HTMLDivElement | null>(null);
  const [map, setMap] = useState<Map>();
  const [source] = useState(new VectorSource());
  const [shouldClearPolygon, setShouldClearPolygon] = useState<boolean>(false);
  const modify = useRef<Modify>();

  const [isDrawing, setIsDrawing] = useState(false);
  const [isModifying, setIsModifying] = useState(false);

  const startDrawing = () => {
    setIsDrawing(true);
  };

  const stopDrawing = () => {
    setIsDrawing(false);
  };

  const startModifying = () => {
    setIsModifying(true);
  };

  const stopModifying = () => {
    setIsModifying(false);
  };

  useEffect(() => {
    // Ensure the mapRef.current is not null when initializing the map
    if (mapRef.current) {
      const baseLayer = new TileLayer({
        source: new OSM(),
      });

      const wmsLayer = new TileLayer({
        source: new TileWMS({
          url: "http://0.0.0.0:8080/geoserver/wms?",
          params: { LAYERS: "topp:states", TILED: true },
          serverType: "geoserver",
        }),
      });

      // Initialize the map with a non-null assertion for mapRef.current
      const map = new Map({
        target: mapRef.current!,
        layers: [
          baseLayer,
          wmsLayer,
          new VectorLayer({
            source: source,
          }),
        ],
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

  useEffect(() => {
    if (clearPolygon && map) {
      setShouldClearPolygon(true);
    }
  }, [clearPolygon, map]);

  useEffect(() => {
    if (shouldClearPolygon && source) {
      source.clear();
      setShouldClearPolygon(false);
      if (onClearComplete) {
        onClearComplete();
      }
    }
  }, [shouldClearPolygon, source, onClearComplete]);

  const startPolygonDrawing = () => {
    if (!map) return;

    modify.current?.setActive(false);

    if (isDrawing) {
      resetInteractions(map);
      stopDrawing();
      return;
    }

    resetInteractions(map);

    // Create new polygon
    const draw = new Draw({
      source: source,
      type: "Polygon",
    });
    map.addInteraction(draw);
    startDrawing();

    // Remove the draw interaction once polygon has been created
    draw.on("drawend", (event) => {
      map.removeInteraction(draw);
      stopDrawing()
      // Outputs long lat, not lat long.
      const polygonCoordinates = event.feature?.getGeometry()?.getCoordinates();
      console.log("Polygon Coordinates", polygonCoordinates);
      onPolygonComplete();
    });
  };

  /**
   * Toggles modifying mode.
   * If already in modifying mode, modifying mode is exited.
   */
  function modifyPolygon(): void {
    if (!map) return;

    if (isModifying) {
      resetInteractions(map);
      stopModifying();
      return;
    }

    resetInteractions(map);

    const select = new Select({
      multi: false,
    });
    map.addInteraction(select);

    select.on("select", (event) => {
      if (event.selected.length > 0) {
        console.log("Boundary selected", event.selected[0]);
        onPolygonSelect();
      }
    });

    modify.current = new Modify({
      features: select.getFeatures(),
    });
    map.addInteraction(modify.current);
    modify.current.setActive(true);

    const snap = new Snap({ source: source });
    map.addInteraction(snap);

    startModifying();
  }

  /**
   * Removes all temporary interactions used for creating and modifying boundaries.
   *
   * @param map The OpenLayers {@link Map} component.
   */
  function resetInteractions(map: Map) {
    let interactions: Collection<Interaction> = map.getInteractions();
    interactions.forEach(function (interaction: Interaction) {
      if (
        interaction instanceof Draw ||
        interaction instanceof Select ||
        interaction instanceof Modify ||
        interaction instanceof Select
      ) {
        map.removeInteraction(interaction);
      }
    });
    stopDrawing();
    stopModifying();
  }


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
      <Button
        onClick={modifyPolygon}
        variant="outline"
        size="icon"
        className="absolute top-12 right-0 m-4 rounded-full"
      >
        <Pencil1Icon className="h-4 w-4" />
      </Button>
    </>
  );
};
export default MapComponent;
