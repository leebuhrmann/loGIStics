"use client"
import { Button } from "@/components/ui/button";
import MapComponent from "../components/map/MapComponent";
import Hello from "../components/map/fetchData";

export default function Home() {
  return (
    <main>
      <div className="p-1">
        <Button>Button</Button>
        <h1>Open Layers Map</h1>
        <Hello />
      </div>
      <MapComponent/> 
    </main>
  );
}
