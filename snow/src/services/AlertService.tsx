import { Client, CompatClient, Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";

let stompClient: CompatClient | undefined;

const BASE_URL = "0.0.0.0:8080";

const connectWebSocket = () => {
  let socket = new SockJS("/wsAlerts/alertToFE");
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    console.log("Connected: ", +frame);
    stompClient!.subscribe("/topic/toFE", function (message) {
      showMessage(JSON.parse(message.body).content);
    });
  });
};

function showMessage(message) {
  console.log("show alerts", message);
}

export default connectWebSocket;
