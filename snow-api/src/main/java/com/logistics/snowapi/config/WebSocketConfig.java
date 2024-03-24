package com.logistics.snowapi.config;

import com.logistics.snowapi.service.NWSDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // register endpoint at '/ws', allowing client to establish connection with
        // websocket

        // registry.addEndpoint("/wsAlerts/alertToFE").withSockJS();
        // registry.addEndpoint("/ws").withSockJS();
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // specifies messages sent by the client to '/app' will be routed to backend
        // with handler
        // this is currently not necessary as we are not receiving data from the client
        registry.setApplicationDestinationPrefixes("/app");

        // configure message broker, which is responsible for routing messages from
        // server to clients that are
        // subscribed to 'wsAlerts'
        registry.enableSimpleBroker("/topic");
    }
}
