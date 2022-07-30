package ru.ko4erijka.weather.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface WeatherYourCityService extends WeatherService {
     SendMessage handle(final long chatId,Message message);
}
