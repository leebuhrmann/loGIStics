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

/**
 * Configuration class for WebSocket and message brokering in the Spring Boot application.
 * This class implements {@link WebSocketMessageBrokerConfigurer} to enable WebSocket message
 * brokering through STOMP protocol.
 * <p>
 * The configuration enables WebSocket connections and configures message routing strategies,
 * allowing real-time communication between the server and clients. It defines STOMP endpoint
 * registrations and message broker options to handle the flow of messages efficiently.
 * <p>
 * Methods:
 * <ul>
 *     <li>{@link #registerStompEndpoints(StompEndpointRegistry)} - Registers STOMP endpoints for WebSocket connections.</li>
 *     <li>{@link #configureMessageBroker(MessageBrokerRegistry)} - Configures the message broker for routing STOMP messages.</li>
 * </ul>
 *
 * @see WebSocketMessageBrokerConfigurer
 * @see StompEndpointRegistry
 * @see MessageBrokerRegistry
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Registers STOMP over WebSocket endpoints that clients will use to connect to our WebSocket server.
     * <p>
     * Security configurations such as allowed origins are also specified here to prevent unauthorized cross-origin requests.
     *
     * @param registry the {@link StompEndpointRegistry} to configure.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // register endpoint at '/ws', allowing client to establish connection with
        // websocket
        registry.addEndpoint("/ws").setAllowedOrigins("*");
    }

    /**
     * Configures message brokering options to support application-specific message handling and broker-backed messaging.
     * <p>
     * This includes setting application destination prefixes for simplifying mapping of incoming messages to message handling methods,
     * and configuring a simple message broker for broadcasting messages to subscribed clients.
     *
     * @param registry the {@link MessageBrokerRegistry} to configure.
     */
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
}
