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
import { useRecoilState, useSetRecoilState } from "recoil";
import { clearPolygonAtom, viewStateAtom, polygonCoordinatesAtom, subCheckValueAtom } from "@/state/atoms";
import { MultiPolygon, SimpleGeometry } from "ol/geom";
import { useRecoilValue } from "recoil";
import { boundaryDataAtom } from "@/state/atoms";
import Feature from "ol/Feature";

interface Boundary {
  id: number;
  description: string;
  name: string;
  subscribed: boolean;
  the_geom: {
    type: string;
    coordinates: number[][][][];
  };
}


function MapComponent() {
  const filterSubscriptions = useRecoilValue(subCheckValueAtom);
  const setViewState = useSetRecoilState(viewStateAtom);
  const [clearPolygon, setClearPolygon] = useRecoilState(clearPolygonAtom);
  const mapRef = useRef<HTMLDivElement | null>(null);
  const [map, setMap] = useState<Map>();
  const [source] = useState(new VectorSource());
  const [shouldClearPolygon, setShouldClearPolygon] = useState<boolean>(false);
  const modify = useRef<Modify>();
  const setPolygonCoordinates = useSetRecoilState(polygonCoordinatesAtom);
  const [isDrawing, setIsDrawing] = useState(false);
  const [isModifying, setIsModifying] = useState(false);
  const boundaryData: Boundary[] = useRecoilValue(boundaryDataAtom);
  const vectorSource = useRef(new VectorSource());

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

  

      // Initialize the map with a non-null assertion for mapRef.current
      const map = new Map({
        target: mapRef.current!,
        layers: [
          baseLayer,
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
    console.log("Filter subscriptions state:", filterSubscriptions);
    if (map && boundaryData.length) {
      console.log("Loading boundaries...");
      const filteredBoundaries = !filterSubscriptions ? 
      boundaryData.filter(boundary => boundary.subscribed) : 
      boundaryData;

      const features = filteredBoundaries.map((boundary) => {
        const multiPolygon = new MultiPolygon(boundary.the_geom.coordinates);
        return new Feature({
          geometry: multiPolygon,
          name: boundary.name,
          description: boundary.description
        });
      });
      source.clear();
      source.addFeatures(features);
    }
  }, [boundaryData, map, filterSubscriptions]);

  useEffect(() => {
    if (clearPolygon && map) {
      setShouldClearPolygon(true);
    }
  }, [clearPolygon, map]);

  useEffect(() => {
    if (map && source) {
      // Just for debugging, log out the layers to confirm they're present
      console.log("Current map layers:", map.getLayers().getArray());
      console.log("Vector source features:", source.getFeatures());
    }
  }, [map, source]);

  useEffect(() => {
    if (shouldClearPolygon && source) {
      source.clear();
      setShouldClearPolygon(false);
      if (clearPolygon) {
        setClearPolygon(false);
      }
    }
  }, [shouldClearPolygon, source, setClearPolygon]);

  const startPolygonDrawing = () => {
    console.log("Starting to draw polygon...");
    if (!map) {
      console.log("Map not available, cannot start drawing.");
      return;
    }
    console.log("Deactivating any active modification.");
    modify.current?.setActive(false);

    if (isDrawing) {
      console.log("Drawing already in progress - resetting interactions");
      resetInteractions(map);
      stopDrawing();
      return;
    }

    console.log("Resetting interactions and initializing new drawing.");
    resetInteractions(map);

    // Create new polygon
    const draw = new Draw({
      source: source,
      type: "Polygon",
    });
    console.log("Vector source used for drawing:", vectorSource.current);
    map.addInteraction(draw);
    startDrawing();

    // Remove the draw interaction once polygon has been created
    draw.on("drawend", (event) => {
      console.log("Drawing ended - removing interaction");
      map.removeInteraction(draw);
      stopDrawing();
      if (event.feature) {
        const geometry = event.feature.getGeometry()
        if (geometry instanceof SimpleGeometry) {
          const coords = geometry.getCoordinates();
          // Outputs long lat, not lat long.
          if (coords) {
            setPolygonCoordinates(coords[0]);
            console.log("Polygon coordinates set:", coords[0]);

          }
        }

      }
      setViewState("create");
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
        setViewState("edit");
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
}

export default React.memo(MapComponent);

