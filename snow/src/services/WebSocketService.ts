import { Client } from "@stomp/stompjs";

/** Interface representing an alert message. */
export interface AlertMessage {
  description: string;
  boundary?: [];
  event: string;
  expires: string;
  headline: string;
  onset: string;
}

/** Interface representing a subscribed alert message. */
export interface SubscribedAlertMessage {
  alert: AlertMessage;
  boundaryIds: [];
  boundaryNames: [];
}

type AlertCallback = (alert: AlertMessage) => void;

/**
 * Service to handle the connection to the websocket.
 */
class WebSocketService {
  public client: Client;
  private alertCallback: AlertCallback | null = null;

  /**
   * Creates a WebSocketService
   * @param alertCallback - A callback function to be called when an alert is received.
   */
  constructor(alertCallback: AlertCallback) {
    this.alertCallback = alertCallback;

    this.client = new Client({
      brokerURL: "ws://localhost:8081/ws",
      debug: function (str) {
        // add console with str for debugging
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    this.client.onConnect = this.onConnect;
    this.client.onStompError = this.onStompError;
    this.client.onWebSocketError = this.onWebSocketError;

    this.client.activate();
  }

  /**
   * Called every time there is a successful connection
   * @param frame The frame object representing the connection details.
   */
  onConnect = (frame: any) => {
    this.client.subscribe("/topic", this.onAlertRecieved);
  };

  /**
   * Called when an alert message is received. Adds alert to state.
   * @param alertMessage The alert body being received
   */
  onAlertRecieved = (alertMessage: { body: string }) => {
    if (alertMessage.body) {
      const data = JSON.parse(alertMessage.body)["properties"];

      let alert: AlertMessage = {
        description: data.description,
        event: data.event,
        expires: data.expires,
        headline: data.headline,
        onset: data.onset,
      };

      if (this.alertCallback) {
        this.alertCallback(alert);
      }
    }
  };

  /**
   * Called when there is an error in the STOMP connection.
   * @param error The STOMP error
   */
  onStompError = (error: any) => {
    console.error("STOMP error:", error);
  };

  /**
   * Called when there is an error in the WebSocket connection.
   * @param event The WebSocket error
   */
  onWebSocketError = (event: any) => {
    console.error("WebSocket error:", event);
  };
}

export default WebSocketService;
