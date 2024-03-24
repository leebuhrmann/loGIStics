import { Client, CompatClient, Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";

let stompClient: CompatClient | undefined;

const BASE_URL = "http://localhost:8080";

const connectWebSocket = () => {
  if (stompClient) {
    return stompClient;
  }

  let socket = new SockJS(`${BASE_URL}/ws`);
  stompClient = Stomp.over(socket);

  stompClient.debug = (str) => {
    console.log("debug", str);
  };

  stompClient.connect({}, function (frame) {
    console.log("Connected: ", frame);
    if (stompClient) {
      stompClient.subscribe("/topic", function (message) {
        showMessage(JSON.parse(message.body).headline);
      });
    }
  }, function (error) {
    console.log("STOMP error:", error);
  }, function() {
    console.log("Disconnected");
  });

  return stompClient;
};

function showMessage(message) {
  console.log("show alerts", message);
}

export default connectWebSocket;
