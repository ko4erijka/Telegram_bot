package ru.ko4erijka.weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ko4erijka.weather.bot.config.BotState;

@Service
@RequiredArgsConstructor
public class WeatherHelloServiceImpl implements WeatherHelloService {
    @Override
    public SendMessage handle(long chatId, Message message) {
        return new SendMessage(String.valueOf(chatId),"Привет! Хочешь узнать погоду? Нажми на одну из этих кнопок:\n" +
            "Ярославль;\nМосква;\nСанкт-Петербург.\nХочешь добавить свою погоду?\nПришли мне свою геопозицию.\n" +
            "Для этого нажми на \"булавку\" и выбери раздел \"геопозиция\"");
    }

    @Override
    public SendMessage handle(Message message) {
        return new SendMessage(String.valueOf(message.getChatId()),"Привет! Хочешь узнать погоду? Нажми на одну из этих кнопок:\n" +
            "Ярославль;\nМосква;\nСанкт-Петербург.\nХочешь добавить свою погоду?\nПришли мне свою геопозицию.\n" +
            "Для этого нажми на \"булавку\" и выбери раздел \"геопозиция\"");
    }

    @Override
    public BotState getHandlerName() {
        return BotState.HELLO;
    }
}
