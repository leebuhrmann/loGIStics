"use client";
import MapComponent from "../components/map/MapComponent";
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
import { useEffect, useRef, useState } from "react";
import SideCreationView from "@/components/side-menu/side-form-view/side-creation-view";
import SideInfoView from "@/components/side-menu/side-info-view/side-info-view";
import connectWebSocket from "@/services/AlertService";
import { CompatClient } from "@stomp/stompjs";

export default function Home() {
  const [viewState, setViewState] = useState("info");
  const [clearPolygon, setClearPolygon] = useState(false);
  // State for global subscription filter
  const [subCheckValue, setSubCheckValue] = useState(true);

  const toggleViewToInfo = () => {
    setViewState("info");
  };

  const handlePolygonComplete = () => {
    setViewState("create");
  };

  const handleClearPolygon = () => {
    console.log("Clear polygon function called");
    setClearPolygon(true);
  };

  const handleClearComplete = () => {
    setClearPolygon(false);
  };

  const stompClientRef = useRef<CompatClient | null>(null);

  useEffect(() => {
    stompClientRef.current = connectWebSocket();

    return () => {
      console.log('disconnect?', stompClientRef.current);
      if (stompClientRef.current && stompClientRef.current.connected) {
        console.log('unsubscribe')
        stompClientRef.current.unsubscribe("/topic");  // Unsubscribe from the topic
        
        stompClientRef.current.disconnect(() => {
          console.log("Disconnected");
        });
      }
    };
  }, []);
  
  return (
    <main className="w-screen h-screen overflow-hidden flex flex-col">
      <div className="h-5/100">
        <NavigationMenu>
          <NavigationMenuList>
            <NavigationMenuItem>User Account</NavigationMenuItem>
            <p></p>
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
                <SideCreationView
                  onClose={toggleViewToInfo}
                  onClearPolygon={handleClearPolygon}
                />
              ) : (
                <SideInfoView
                  subCheckValue={subCheckValue}
                  setSubCheckValue={setSubCheckValue}
                />
              )}
            </SideMenu>
          </div>
        </ResizablePanel>
        <ResizableHandle />
        <ResizablePanel defaultSize={80}>
          <div className="flex flex-col h-full items-center justify-center relative">
            <MapComponent
              onPolygonComplete={handlePolygonComplete}
              clearPolygon={clearPolygon}
              onClearComplete={handleClearComplete}
            />
          </div>
        </ResizablePanel>
        <ResizableHandle />
      </ResizablePanelGroup>
    </main>
  );
}
