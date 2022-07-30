package ru.ko4erijka.weather.bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:yandex.properties")
public class YandexApiConfig {

    private final String yandexApiKey;

    public YandexApiConfig( @Value("${yandex.api-key}") String yandexApiKey) {
        this.yandexApiKey = yandexApiKey;
    }
    @Bean
    public String yandexApiKey() {
        return yandexApiKey;
    }
}
