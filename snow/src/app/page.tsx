"use client"
import { Button } from "@/components/ui/button";
import MapComponent from "../components/map/MapComponent";
import Hello from "../components/map/fetchData";

export default function Home() {
  return (
    <main>
      <Button>Button</Button>
      <h1>Open Layers Map</h1>
      <Hello />
      <MapComponent /> 
    </main>
  );
}
