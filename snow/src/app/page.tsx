"use client";
import MapComponent from "../components/map/MapComponent";
import { useState } from "react";
//import Hello from "../components/map/fetchData";
import {
  ResizableHandle,
  ResizablePanel,
  ResizablePanelGroup,
} from "@/components/ui/resizable";
import {
  NavigationMenu,
  NavigationMenuItem,
  NavigationMenuList,
} from "@/components/ui/navigation-menu";
import SideMenu from "@/components/side-menu/side-menu";
import SideInfoView from "@/components/side-menu/side-info-view/side-info-view";
import SideCreationView from "@/components/side-menu/side-form-view/side-creation-view";

export default function Home() {
  const [viewState, setViewState] = useState("info");

  const toggleViewToInfo = () => {
    setViewState("info");
  };

  const handlePolygonComplete = () => {
    setViewState("create");
  };

  return (
    <main className="w-screen h-screen overflow-hidden flex flex-col">
      <div className="h-5/100">
        <NavigationMenu>
          <NavigationMenuList>
            <NavigationMenuItem>User Account</NavigationMenuItem>
          </NavigationMenuList>
        </NavigationMenu>
      </div>
      <ResizablePanelGroup
        id="resizablePanelGroup"
        direction="horizontal"
        className="w-screen rounded-lg border"
      >
        <ResizablePanel defaultSize={20} minSize={15}>
          <div className="flex h-full p-2 overflow-hidden">
            <SideMenu>
              {viewState === "create" ? (
                <SideCreationView onClose={toggleViewToInfo} />
              ) : (
                <SideInfoView />
              )}
            </SideMenu>
          </div>
        </ResizablePanel>
        <ResizableHandle />
        <ResizablePanel defaultSize={80}>
          <div className="flex flex-col h-full items-center justify-center relative">
            <MapComponent onPolygonComplete={handlePolygonComplete} />
          </div>
        </ResizablePanel>
        <ResizableHandle />
      </ResizablePanelGroup>
    </main>
  );
}
