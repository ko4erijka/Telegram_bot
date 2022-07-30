package ru.ko4erijka.weather.bot.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class User {
    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private Long chatId;
    private LocalDateTime localDateTime;
    private Location location;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User users = (User) o;
        return Objects.equals(userId, users.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Location {
        private Double lat;
        private Double lon;
    }
}
