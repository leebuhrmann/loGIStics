package com.logistics.snowapi.events;

import com.logistics.snowapi.model.Alert;
import org.springframework.context.ApplicationEvent;

public class AlertCreatedEvent extends ApplicationEvent {
    private final Alert alert;

    public AlertCreatedEvent(Object source, Alert alert) {
        super(source);
        this.alert = alert;
    }

    public Alert getAlert() {
        return alert;
    }
}
