package ru.ko4erijka.weather.bot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ko4erijka.weather.bot.api.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ListUsersConfig {
    @Bean
    public Map<Long, User> users() {
        return new HashMap<>();
    }

    @Bean
    public List<User> listUsers() {
        return new ArrayList<>();
    }
}
