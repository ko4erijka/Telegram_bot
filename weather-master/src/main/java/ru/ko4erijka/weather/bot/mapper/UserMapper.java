package ru.ko4erijka.weather.bot.mapper;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ko4erijka.weather.bot.api.model.User;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public User toUsers(Message message) {
        try {

            return User.builder()
                .chatId(message.getChatId())
                .firstName(message.getFrom().getFirstName())
                .lastName(message.getFrom().getLastName())
                .localDateTime(LocalDateTime.now())
                .userId(message.getFrom().getId())
                .username(message.getFrom().getUserName())
                .location(User.Location.builder()
                    .lat(message.getLocation().getLatitude())
                    .lon(message.getLocation().getLongitude())
                    .build())
                .build();
        } catch (NullPointerException e) {
            return User.builder()
                .chatId(message.getChatId())
                .firstName(message.getFrom().getFirstName())
                .lastName(message.getFrom().getLastName())
                .localDateTime(LocalDateTime.now())
                .userId(message.getFrom().getId())
                .username(message.getFrom().getUserName())
                .location(User.Location.builder()
                    .lat(0D)
                    .lon(0D)
                    .build())
                .build();
        }
    }
}
