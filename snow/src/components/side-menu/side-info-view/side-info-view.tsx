import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { mockAlerts, mockBoundaries } from "@/mock-data/mock-data";
import { SideInfoCommon } from "./side-info-common";

export default function SideInfoView({ subCheckValue, setSubCheckValue }) {
  return (
    <div id="side-info-view" className="w-full">
      <Tabs id="tabs" defaultValue="alerts" className="w-full h-full">
        <TabsList>
          <TabsTrigger value="alerts">Alerts</TabsTrigger>
          <TabsTrigger value="boundaries">Boundaries</TabsTrigger>
        </TabsList>
        <TabsContent id="tabsAlertContent" value="alerts" className="h-full">
          <SideInfoCommon
            data={mockAlerts}
            subCheckValue={subCheckValue}
            setSubCheckValue={setSubCheckValue}
          ></SideInfoCommon>
        </TabsContent>
        <TabsContent
          id="tabsBoundaryContent"
          value="boundaries"
          className="h-full"
        >
          <SideInfoCommon
            data={mockBoundaries}
            subCheckValue={subCheckValue}
            setSubCheckValue={setSubCheckValue}
          ></SideInfoCommon>
        </TabsContent>
      </Tabs>
    </div>
  );
}
