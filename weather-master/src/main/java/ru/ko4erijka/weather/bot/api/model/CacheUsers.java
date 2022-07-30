package ru.ko4erijka.weather.bot.api.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Data
@RequiredArgsConstructor
public class CacheUsers {
    private final Map<Long, User> users;
    private final List<User> historyUsers;

    public void addUser(Long userId, User users) {
        this.users.put(userId,users);
    }
    public void addHistoryUser(User users) {
        historyUsers.add(users);
    }
}
