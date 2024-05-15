import {
  AlertMessage,
  SubscribedAlertMessage,
} from "@/services/WebSocketService";
import { atom } from "recoil";

// Define Recoil atoms
/** Recoil atom for managing the current view state, including "info", "create", and "edit". */
export const viewStateAtom = atom({
  key: "viewState",
  default: "info",
});

/** Recoil atom for tracking if a polygon should be cleared. */
export const clearPolygonAtom = atom({
  key: "clearPolygon",
  default: false,
});

/** Recoil atom for storing all alerts. */
export const alertsAtom = atom<AlertMessage[]>({
  key: "alerts",
  default: [],
});

/** Recoil atom for storing the state of the subscription checkbox. */
export const subCheckValueAtom = atom({
  key: "subCheckValueAtom",
  default: false,
});

/** Recoil atom for storing the name of a boundary while creating. */
export const boundaryNameAtom = atom({
  key: "boundaryNameAtom",
  default: "",
});

/** Recoil atom for storing the description of a boundary while creating. */
export const boundaryDescriptionAtom = atom({
  key: "boundaryDescriptionAtom",
  default: "",
});

/** Recoil atom for storing the coordinates of a boundary while creating. */
export const polygonCoordinatesAtom = atom({
  key: "polygonCoordinatesAtom",
  default: [],
});

/** Recoil atom for storing all the alerts that fall within a subscribed boundary. */
export const subscribedAlertsAtom = atom<SubscribedAlertMessage[]>({
  key: "subscribedAlertsAtom",
  default: [],
});

/** Recoil atom for storing all boundary data. */
export const boundaryDataAtom = atom({
  key: "boundaryDataAtom",
  default: [],
});

/** Recoil atom for managing the create checkbox. */
export const createCheckboxAtom = atom({
  key: "createCheckboxAtom",
  default: false,
});
