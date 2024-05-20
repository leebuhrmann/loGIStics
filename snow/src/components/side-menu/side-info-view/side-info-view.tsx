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

/**
 * Component that displays the common elements within SideInfoView.
 * @returns html elements for the SideInfoView
 */
export default function SideInfoView() {
  const [alerts, setAlerts] = useRecoilState<AlertMessage[]>(alertsAtom);
  const [subAlerts, setSubAlerts] =
    useRecoilState<SubscribedAlertMessage[]>(subscribedAlertsAtom);
  const subCheckValue = useRecoilValue(subCheckValueAtom);
  const [boundaries, setBoundaries] = useRecoilState(boundaryDataAtom);

  useEffect(() => {
    /** Fetches all alerts from the alert service and updates the alerts state */
    async function fetchAllAlerts() {
      try {
        const alertsData = await AlertService.getAllAlerts();
        setAlerts(alertsData);
      } catch (error) {
        // Handle error
        console.error("Error fetching alerts:", error);
      }
    }

    /** Fetches all of the subscribed alerts from the alerts services and updates the subAlerts state */
    async function fetchSubAlerts() {
      try {
        const subAlertsData = await AlertService.getSubAlerts();
        setSubAlerts(subAlertsData);
      } catch (error) {
        // Handle error
        console.error("Error fetching alerts:", error);
      }
    }

    /** Fetches all of the boundaries from the boundary service and updates the boundary state. */
    const fetchBoundaries = async () => {
      const fetchedBoundaries = await BoundaryService.getAllBoundaries();
      setBoundaries(fetchedBoundaries);
    };

    fetchAllAlerts();
    fetchSubAlerts();
    fetchBoundaries();

    /**Sets up the WebSocket to retrieve alerts and update the alerts state. */
    const webSocketService = new WebSocketService((newAlert) => {
      setAlerts((prevAlerts) => [newAlert, ...prevAlerts]);
    });

    /** Clean-up function to deactivate the WebSocket. */
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
