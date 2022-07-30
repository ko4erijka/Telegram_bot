package ru.ko4erijka.weather.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ko4erijka.weather.bot.WeatherTelegramBot;
import ru.ko4erijka.weather.bot.api.model.User;
import ru.ko4erijka.weather.service.CacheUsersService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BotController {
    private final WeatherTelegramBot telegramBot;
    private final CacheUsersService cacheUsersService;
    @PostMapping("/")
    @SneakyThrows
    public BotApiMethod<?> getWeather(@RequestBody Update update) {
     return telegramBot.onWebhookUpdateReceived(update);
    }
    @GetMapping("/all-history-users/{userId}")
    public List<User> findAllHistoryUsers(@RequestParam(required = false) Long userId) {
        if(userId==null) {
            return cacheUsersService.findAllHistoryUsers();
        } else {
            return cacheUsersService.findAllByUsersHistoryId(userId);
        }
    }
    @GetMapping("/all-users")
    public List<User> findAllUsers() {
        return cacheUsersService.findAllUsers();
    }
    @GetMapping("/user/{id}")
    public User findByUserId(Long userId) {
        return cacheUsersService.findByUserId(userId);
    }
}
