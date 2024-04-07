package com.logistics.snowapi.config;

import com.logistics.snowapi.service.NWSDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // register endpoint at '/ws', allowing client to establish connection with
        // websocket
        registry.addEndpoint("/ws").setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // specifies messages sent by the client to '/app' will be routed to backend
        // with handler
        // this is currently not necessary as we are not receiving data from the client
        registry.setApplicationDestinationPrefixes("/app");

        // configure message broker, which is responsible for routing messages from
        // server to clients that are subscribed to 'topic'
        registry.enableSimpleBroker("/topic");
    }

    // @Override
    // public void configureWebSocketTransport(WebSocketTransportRegistration
    // registration) {
    // registration.setMessageSizeLimit(8192); // Set the maximum message size
    // registration.setSendTimeLimit(10 * 10000); // Set the time limit for sending
    // messages
    // registration.setSendBufferSizeLimit(1024 * 1024); // Set the send buffer size
    // limit
    // }
}
