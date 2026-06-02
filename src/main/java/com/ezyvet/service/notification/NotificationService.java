package com.ezyvet.service.notification;

import java.util.Arrays;
import java.util.List;

import com.ezyvet.domain.entity.PetOwner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ezyvet.domain.enums.NotificationChannelType;
import com.ezyvet.service.notification.channel.NotificationChannel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationChannelFactory channelFactory;

    @Value("${notification.channels:EMAIL}")
    private String channelsProperty;

    public void notifyOwner(PetOwner owner, String message) {
        List<NotificationChannelType> channels = parseChannels();
        channels.stream()
            .map(channelFactory::get)
            .forEach(channel -> channel.send(resolveRecipient(owner, channel.getType()), message));
    }

    private String resolveRecipient(PetOwner owner, NotificationChannelType type) {
        return switch (type) {
            case EMAIL -> owner.getEmail();
            case SMS, WHATSAPP -> owner.getPhone();
        };
    }

    private List<NotificationChannelType> parseChannels() {
        return Arrays.stream(channelsProperty.split(","))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(String::toUpperCase)
            .map(NotificationChannelType::valueOf)
            .toList();
    }
}
