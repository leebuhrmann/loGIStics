/** Interface for mock alert data */
export interface MockAlertData {
  title: string;
  header: string[];
  body: string[];
}

/** Interface for mock boundary data */
export interface MockBoundaryData {
  title: string;
  sub: boolean;
  header: string[];
  body: string[];
}

/** Mock data for alert messages. */
export const mockAlerts: MockAlertData[] = [
  {
    title: "Event: Winter Weather Advisory",
    header: [
      "Boundary: Boundary 1",
      "Issued: Februrary 09 at 2:05PM CST",
      "Expiring: Feburary 11 at 6:00AM CST",
    ],
    body: [
      "Alert:",
      "...WINTER WEATHER ADVISORY IN EFFECT FROM 6 AM SATURDAY TO 6 AM",
      "MST SUNDAY...",
      "* WHAT...Snow expected. Total snow accumulations of 1 to 4 inches with locally higher amounts possible.",
      "* WHERE...Portions of north central, panhandle and west central Nebraska.",
      "* WHEN...From 6 AM Saturday to 6 AM MST Sunday.",
      "* IMPACTS...Plan on slippery road conditions.* ADDITIONAL DETAILS...Winds of less than 20 mph are expected through the duration of the event, limiting the threat for blowing and drifting snow.",
    ],
  },
  {
    title: "Event: Winter Weather Advisory",
    header: [
      "Boundary: Boundary 1",
      "Issued: Februrary 09 at 2:05PM CST",
      "Expiring: Feburary 11 at 6:00AM CST",
    ],
    body: [
      "Alert:",
      "...WINTER WEATHER ADVISORY IN EFFECT FROM 6 AM SATURDAY TO 6 AM",
      "MST SUNDAY...",
      "* WHAT...Snow expected. Total snow accumulations of 1 to 4 inches with locally higher amounts possible.",
      "* WHERE...Portions of north central, panhandle and west central Nebraska.",
      "* WHEN...From 6 AM Saturday to 6 AM MST Sunday.",
      "* IMPACTS...Plan on slippery road conditions.* ADDITIONAL DETAILS...Winds of less than 20 mph are expected through the duration of the event, limiting the threat for blowing and drifting snow.",
    ],
  },
];

/** Mock data for boundary data. */
export const mockBoundaries: MockBoundaryData[] = [
  {
    title: "Boundary: Boundary 1",
    sub: false,
    header: ["Description:"],
    body: [
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Adipiscing elit ut aliquam purus sit amet.",
    ],
  },
  {
    title: "Boundary: Boundary 2",
    sub: true,
    header: ["Description:"],
    body: [
      "Tellus cras adipiscing enim eu turpis egestas pretium. Vitae et leo duis ut diam quam nulla porttitor. Tortor vitae purus faucibus ornare suspendisse sed. Vitae sapien pellentesque habitant morbi tristique.",
    ],
  },
];
