package ru.ko4erijka.weather.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.ko4erijka.weather.bot.api.model.CacheUsers;
import ru.ko4erijka.weather.bot.api.model.User;
import ru.ko4erijka.weather.bot.config.BotState;
import ru.ko4erijka.weather.bot.mapper.UserMapper;
import ru.ko4erijka.weather.feign.WeatherFeignClient;
import ru.ko4erijka.weather.model.Condition;
import ru.ko4erijka.weather.model.Fact;
import ru.ko4erijka.weather.model.Weather;
import ru.ko4erijka.weather.service.WeatherYourCityService;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class WeatherYourCityServiceImpl implements WeatherYourCityService {
    private final WeatherFeignClient weatherFeignClient;
    private final InlineKeyboardMarkup inlineMessageButtons;
    private final String yandexApiKey;
    private final CacheUsers cacheUsers;
    private final UserMapper userMapper;

    @Override
    public SendMessage handle(Message message) {
        if (cacheUsers.getUsers().containsKey(message.getChat().getId())) {
            return getSendMessage(message);
        } else {
            SendMessage sendMessage = new SendMessage(String.valueOf(message.getChatId()), "Вы не отправили свои " +
                "координаты, поэтому узнать погоду " +
                "невозможно.\nПожалуйста, пришли свою геолокацию.\nДля этого нажми на \"булавку\" и выбери раздел " +
                "\"геопозиция\"");
            sendMessage.setReplyMarkup(inlineMessageButtons);
            return sendMessage;
        }
        // return getSendMessage(message);
        //}
       /* Weather weather = weatherFeignClient.getWeather(yandexApiKey,String.valueOf(message.getLocation()
       .getLatitude()),
            String.valueOf( message.getLocation().getLongitude()),true);
        String meaasageWeather = getWeatherSaintPersburgNowFromYandexApiMessage(weather.getFact(),weather);
        SendMessage sendMessage =new SendMessage(String.valueOf(message.getChatId()),meaasageWeather);
        sendMessage.setReplyMarkup(inlineMessageButtons);
        return sendMessage;*/
    }

    @Override
    public SendMessage handle(final long chatId, Message message) {
        return getSendMessage(message);
    }

    private SendMessage getSendMessage(Message message) {
        if (cacheUsers.getUsers().containsKey(message.getChat().getId())) {
            User users = cacheUsers.getUsers().get(message.getChat().getId());
            return getSendMessage(message, users);
        }
        User users = userMapper.toUsers(message);
        return getSendMessage(message, users);
    }

    private SendMessage getSendMessage(Message message, User users) {
        Weather weather = weatherFeignClient.getWeather(yandexApiKey, String.valueOf(users.getLocation().getLat()),
            String.valueOf(users.getLocation().getLon()), true);
        String meaasageWeather = getWeatherSaintPersburgNowFromYandexApiMessage(weather.getFact(), weather);
        SendMessage sendMessage = new SendMessage(String.valueOf(message.getChatId()), meaasageWeather);
        sendMessage.setReplyMarkup(inlineMessageButtons);
        return sendMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.YOUR_CITY;
    }

    private String getWeatherSaintPersburgNowFromYandexApiMessage(Fact fact, Weather weather) {
        StringBuilder weatherStringBuilder = new StringBuilder();
        weatherStringBuilder.append("Погода в вашем городе сейчас:\n");
        weatherStringBuilder.append("Температура: ").append(fact.getTemp()).append("°C\n");
        weatherStringBuilder.append("Ощущаемая температура: ").append(fact.getFeelsLike()).append("°C\n");
        Condition condition = Condition.valueOf(weather.getFact().getCondition().toUpperCase(Locale.ROOT));
        weatherStringBuilder.append("Погодное описание: ").append(condition.getCondition()).append("\n");
        weatherStringBuilder.append("Давление (в мм рт. ст.): ").append(fact.getPressureMm()).append("\n");
        weatherStringBuilder.append("Влажность воздуха: ").append(fact.getHumidity()).append("%\n");
        weatherStringBuilder.append("Фазы Луны: ").append(weather.getForecastsList().get(0).getMoonCodeInText()).append("\n");
        weatherStringBuilder.append("Восход Солнца: ").append(weather.getForecastsList().get(0).getSunrise()).append(
            "\n");
        weatherStringBuilder.append("Закат Солнца: ").append(weather.getForecastsList().get(0).getSunset()).append("\n\n");
        //weatherStringBuilder.append("Если захотите поменять погоду в вашем городе, отправьте, пожалуйста, новую геопозицию");
        return weatherStringBuilder.toString();
    }
}
