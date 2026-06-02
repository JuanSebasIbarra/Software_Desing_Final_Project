package com.ezyvet.service.notification.channel;

import org.springframework.stereotype.Component;

import com.ezyvet.domain.enums.NotificationChannelType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SmsNotificationChannel implements NotificationChannel {

    @Override
    public NotificationChannelType getType() {
        return NotificationChannelType.SMS;
    }

    @Override
    public void send(String recipient, String message) {
        log.info("SMS to {} -> {}", recipient, message);
    }
}
