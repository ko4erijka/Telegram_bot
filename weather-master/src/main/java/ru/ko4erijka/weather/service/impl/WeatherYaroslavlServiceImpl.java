package ru.ko4erijka.weather.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.ko4erijka.weather.bot.api.model.CacheUsers;
import ru.ko4erijka.weather.bot.config.BotState;
import ru.ko4erijka.weather.bot.mapper.UserMapper;
import ru.ko4erijka.weather.feign.WeatherFeignClient;
import ru.ko4erijka.weather.model.Condition;
import ru.ko4erijka.weather.model.Fact;
import ru.ko4erijka.weather.model.Weather;
import ru.ko4erijka.weather.service.WeatherYaroslavlService;

import java.util.Locale;
@Service
@RequiredArgsConstructor
public class WeatherYaroslavlServiceImpl implements WeatherYaroslavlService {
    private final WeatherFeignClient weatherFeignClient;
    private final InlineKeyboardMarkup inlineMessageButtons;
    private final String yandexApiKey;
    private final CacheUsers cacheUsers;
    private final UserMapper userMapper;

    @Override
    public SendMessage handle(Message message) {
        final long chatId = message.getChatId();
        cacheUsers.addHistoryUser(userMapper.toUsers(message));
        Weather weather = weatherFeignClient.getWeather(yandexApiKey,"57.6298700","39.8736800",true);
        String meaasageWeather = getWeatherYaroslavlNowFromYandexApiMessage(weather.getFact(),weather);
        SendMessage replyToUser = new SendMessage(String.valueOf(chatId),meaasageWeather);
        replyToUser.setReplyMarkup(inlineMessageButtons);
        return replyToUser;
    }
    @Override
    public SendMessage handle(final long chatId, Message message) {
        cacheUsers.addHistoryUser(userMapper.toUsers(message));
        Weather weather = weatherFeignClient.getWeather(yandexApiKey,"57.6298700","39.8736800",true);
        String meaasageWeather = getWeatherYaroslavlNowFromYandexApiMessage(weather.getFact(),weather);
        SendMessage sendMessage =new SendMessage(String.valueOf(chatId),meaasageWeather);
        sendMessage.setReplyMarkup(inlineMessageButtons);
        return sendMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.YAROSLAVL;
    }
    private String getWeatherYaroslavlNowFromYandexApiMessage(Fact fact, Weather weather) {
        StringBuilder weatherStringBuilder = new StringBuilder();
        weatherStringBuilder.append("Погода в Ярославле сейчас:\n");
        weatherStringBuilder.append("Температура: ").append(fact.getTemp()).append("°C\n");
        weatherStringBuilder.append("Ощущаемая температура: ").append(fact.getFeelsLike()).append("°C\n");
        Condition condition = Condition.valueOf(weather.getFact().getCondition().toUpperCase(Locale.ROOT));
        weatherStringBuilder.append("Погодное описание: ").append(condition.getCondition()).append("\n");
        weatherStringBuilder.append("Давление (в мм рт. ст.): ").append(fact.getPressureMm()).append("\n");
        weatherStringBuilder.append("Влажность воздуха: ").append(fact.getHumidity()).append("%\n");
        weatherStringBuilder.append("Фазы Луны: ").append(weather.getForecastsList().get(0).getMoonCodeInText()).append("\n");
        weatherStringBuilder.append("Восход Солнца: ").append(weather.getForecastsList().get(0).getSunrise()).append("\n");
        weatherStringBuilder.append("Закат Солнца: ").append(weather.getForecastsList().get(0).getSunset());
        return  weatherStringBuilder.toString();
    }
}
