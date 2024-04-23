import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { mockBoundaries } from "@/mock-data/mock-data";
import SideInfoCommon from "./side-info-common";
import WebSocketService, { AlertMessage } from "@/services/AlertService";
import { alertsAtom } from "@/state/atoms";
import { useEffect } from "react";
import { useRecoilState } from "recoil";

export default function SideInfoView() {
  const [alerts, setAlerts] = useRecoilState<AlertMessage[]>(alertsAtom);

  useEffect(() => {
    const webSocketService = new WebSocketService((newAlert) => {
      setAlerts((prevAlerts) => [newAlert, ...prevAlerts]);
    });

    return () => {
      webSocketService.client.deactivate();
    };
  }, []);

  return (
    <div id="side-info-view" className="w-full">
      <Tabs id="tabs" defaultValue="alerts" className="w-full h-full">
        <TabsList>
          <TabsTrigger value="alerts">Alerts</TabsTrigger>
          <TabsTrigger value="boundaries">Boundaries</TabsTrigger>
        </TabsList>
        <TabsContent id="tabsAlertContent" value="alerts" className="h-full">
          <SideInfoCommon data={alerts}></SideInfoCommon>
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
