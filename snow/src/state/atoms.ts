import { AlertMessage } from "@/services/WebSocketService";
import { atom } from "recoil";

// Define Recoil atoms
export const viewStateAtom = atom({
  key: "viewState",
  default: "info",
});

export const clearPolygonAtom = atom({
  key: "clearPolygon",
  default: false,
});

export const alertsAtom = atom<AlertMessage[]>({
  key: "alerts",
  default: [],
});

export const subCheckValueAtom = atom({
  key: "subCheckValueAtom",
  default: true,
});

export const boundaryNameAtom = atom({
  key: "boundaryNameAtom",
  default: "",
});

export const boundaryDescriptionAtom = atom({
  key: "boundaryDescriptionAtom",
  default: "",
});

export const polygonCoordinatesAtom = atom({
  key: "polygonCoordinatesAtom",
  default: [],
});

export const subscribedAlertsAtom = atom<AlertMessage[]>({
  key: "subscribedAlertsAtom",
  default: [],
});
