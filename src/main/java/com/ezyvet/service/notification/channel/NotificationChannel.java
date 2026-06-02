package com.ezyvet.service.notification.channel;

import com.ezyvet.domain.enums.NotificationChannelType;

public interface NotificationChannel {

    NotificationChannelType getType();

    void send(String recipient, String message);
}
