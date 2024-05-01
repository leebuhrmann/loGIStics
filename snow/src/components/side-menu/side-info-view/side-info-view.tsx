import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import SideInfoCommon from "./side-info-common";
import WebSocketService, { AlertMessage } from "@/services/AlertService";
import { alertsAtom, boundaryDataAtom } from "@/state/atoms";
import { useEffect } from "react";
import { useRecoilState } from "recoil";
import BoundaryService from "@/services/BoundaryService";

export default function SideInfoView() {
  const [alerts, setAlerts] = useRecoilState<AlertMessage[]>(alertsAtom);
  const [boundaries, setBoundaries] = useRecoilState(boundaryDataAtom);

  useEffect(() => {
    const fetchBoundaries = async () => {
      try {
        const fetchedBoundaries = await BoundaryService.getAllBoundaries();
        setBoundaries(fetchedBoundaries);
      } catch (error) {
        console.error('Failed to fetch boundary data:', error);
      }
    };

    fetchBoundaries();
  }, []);

  // WebSocket setup for alerts
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
          <SideInfoCommon data={boundaries}></SideInfoCommon>
        </TabsContent>
      </Tabs>
    </div>
  );
}
