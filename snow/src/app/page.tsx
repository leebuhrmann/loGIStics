"use client"
import { Button } from "@/components/ui/button";
import MapComponent from "../components/map/MapComponent";
import Hello from "../components/map/fetchData";
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


export default function Home() {
  return (
    <main className={"w-screen h-screen overflow-hidden"}>
        <NavigationMenu>
            <NavigationMenuList>
                <NavigationMenuItem>
                    User Account
                </NavigationMenuItem>
            </NavigationMenuList>
        </NavigationMenu>
        <ResizablePanelGroup
            direction="horizontal"
            className="w-screen min-h-screen rounded-lg border"
        >
            <ResizablePanel defaultSize={15}>
                <div className="flex h-full items-center justify-center p-6">
                    <Button>Button</Button>
                </div>
            </ResizablePanel>
            <ResizableHandle />
            <ResizablePanel defaultSize={75}>
                <div className="flex h-full items-center justify-center p-6">
                    <Hello/>
                    <MapComponent/>
                </div>
            </ResizablePanel>
            <ResizableHandle />
        </ResizablePanelGroup>
    </main>
  );
}
