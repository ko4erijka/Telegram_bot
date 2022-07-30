package ru.ko4erijka.weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ko4erijka.weather.bot.api.model.CacheUsers;
import ru.ko4erijka.weather.bot.api.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CacheUsersService {
    private final CacheUsers cacheUsers;

    public List<User> findAllHistoryUsers() {
        List<User> historyUsersList = new ArrayList<>();
        cacheUsers.getUsers().forEach((k,v) ->
            historyUsersList.add(v));
        return historyUsersList;
    }
    public List<User> findAllUsers() {
        return cacheUsers.getHistoryUsers();
    }
    public List<User> findAllByUsersHistoryId(Long userId) {
        return cacheUsers.getHistoryUsers()
            .stream()
            .filter(id -> id.getUserId().equals(userId))
            .collect(Collectors.toList());
    }
    public User findByUserId(Long userId) {
        return cacheUsers.getUsers().get(userId);
    }

}
