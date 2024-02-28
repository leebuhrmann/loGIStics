import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import SideInfoCommon from "./side-info-common";
import { mockAlerts, mockBoundaries } from "@/mock-data/mock-data";

export default function SideInfoView() {
  return (
    <div id="side-info-view" className="w-full">
      <Tabs id="tabs" defaultValue="alerts" className="w-full h-full">
        <TabsList>
          <TabsTrigger value="alerts">Alerts</TabsTrigger>
          <TabsTrigger value="boundaries">Boundaries</TabsTrigger>
        </TabsList>
        <TabsContent id="tabsAlertContent" value="alerts" className="h-full">
          <SideInfoCommon data={mockAlerts}></SideInfoCommon>
        </TabsContent>
        <TabsContent
          id="tabsBoundaryContent"
          value="boundaries"
          className="h-full"
        >
          <SideInfoCommon data={mockBoundaries}></SideInfoCommon>
        </TabsContent>
      </Tabs>
    </div>
  );
}
