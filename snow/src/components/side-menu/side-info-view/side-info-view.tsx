import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import SideInfoCommon from "./side-info-common";
import WebSocketService, {
  AlertMessage,
  SubscribedAlertMessage,
} from "@/services/WebSocketService";
import {
  alertsAtom,
  subCheckValueAtom,
  subscribedAlertsAtom,
  boundaryDataAtom,
} from "@/state/atoms";
import { useEffect } from "react";
import { useRecoilState, useRecoilValue } from "recoil";
import AlertService from "@/services/AlertService";
import BoundaryService from "@/services/BoundaryService";

export default function SideInfoView() {
  const [alerts, setAlerts] = useRecoilState<AlertMessage[]>(alertsAtom);
  const [subAlerts, setSubAlerts] =
    useRecoilState<SubscribedAlertMessage[]>(subscribedAlertsAtom);
  const subCheckValue = useRecoilValue(subCheckValueAtom);
  const [boundaries, setBoundaries] = useRecoilState(boundaryDataAtom);

  useEffect(() => {
    async function fetchAllAlerts() {
      try {
        const alertsData = await AlertService.getAllAlerts();
        setAlerts(alertsData);
      } catch (error) {
        // Handle error
        console.error("Error fetching alerts:", error);
      }
    }

    async function fetchSubAlerts() {
      try {
        const subAlertsData = await AlertService.getSubAlerts();
        setSubAlerts(subAlertsData);
      } catch (error) {
        // Handle error
        console.error("Error fetching alerts:", error);
      }
    }

    const fetchBoundaries = async () => {
      const fetchedBoundaries = await BoundaryService.getAllBoundaries();
      setBoundaries(fetchedBoundaries);
    };

    fetchAllAlerts();
    fetchSubAlerts();
    fetchBoundaries();

    // WebSocket setup for alerts
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
          <SideInfoCommon
            data={subCheckValue ? subAlerts : alerts}
          ></SideInfoCommon>
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
