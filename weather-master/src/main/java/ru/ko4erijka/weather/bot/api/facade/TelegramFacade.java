package ru.ko4erijka.weather.bot.api.facade;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ko4erijka.weather.bot.api.model.CacheUsers;
import ru.ko4erijka.weather.bot.api.model.User;
import ru.ko4erijka.weather.bot.config.BotState;
import ru.ko4erijka.weather.bot.config.context.BotStateContext;
import ru.ko4erijka.weather.bot.mapper.UserMapper;


import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class TelegramFacade {
    private final BotStateContext botStateContext;
    private final CacheUsers cacheUsers;
    private final UserMapper userMapper;


    public Optional<BotApiMethod<?>> handleUpdate(Update update) {
        Message message = update.getMessage();
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data1: {}",
                update.getCallbackQuery().getFrom().getUserName(),
                callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            return processCallbackQuery(callbackQuery);
        } else if (message != null && message.hasText()) {
            log.info("New message from User:{}, chatId: {},  with text: {}",
                message.getFrom().getUserName(), message.getChatId(), message.getText());
            Optional<SendMessage> optionalSendMessage = handleInputMessage(message);
            if (optionalSendMessage.isPresent()) {
                return Optional.of(optionalSendMessage.get());
            }
        } else if (update.getMessage().getLocation() != null) {
            log.info("Отправка коррдинатов");
            if (!cacheUsers.getUsers().containsKey(userMapper.toUsers(message).getUserId())) {
                cacheUsers.addUser(userMapper.toUsers(message).getUserId(), userMapper.toUsers(message));
                log.info("Сохранение пользователя");
                cacheUsers.addHistoryUser(userMapper.toUsers(message));
                return Optional.ofNullable(botStateContext.processInputMessage(BotState.YOUR_CITY,
                    update.getMessage()));
            } else {
                User users = cacheUsers.getUsers().get(userMapper.toUsers(message).getUserId());
                users.setLocation(userMapper.toUsers(message).getLocation());
                users.setUsername(userMapper.toUsers(message).getUsername());
                users.setFirstName(userMapper.toUsers(message).getFirstName());
                users.setLastName(userMapper.toUsers(message).getLastName());
                users.setChatId(users.getChatId());
                users.setUserId(userMapper.toUsers(message).getUserId());
                cacheUsers.addUser(userMapper.toUsers(message).getUserId(), users);
                cacheUsers.addHistoryUser(userMapper.toUsers(message));
                log.info("Сохранение полтзователя");
                log.info("Сохранение новых коррдинат");
                return Optional.ofNullable(botStateContext.processInputMessage(BotState.YOUR_CITY,
                    update.getMessage()));
            }
        }

        return Optional.empty();
    }

    private Optional<SendMessage> handleInputMessage(Message message) {
        String inputMsg = message.getText();
        BotState botState;
        SendMessage replyMessage;

        switch (inputMsg) {
            case "/start":
                botState = BotState.HELLO;
                break;
            case "/Moscow":
            case "/moscow":
            case "/moscow@over_weather_bot":
                botState = BotState.MOSCOW;
                break;
            case "/Saint_Petersburg":
            case "/saint_petersburg":
            case "/saint_petersburg@over_weather_bot":
                botState = BotState.SAINT_PETERSBURG;
                break;
            case "/Yaroslavl":
            case "/yaroslavl":
            case "/yaroslavl@over_weather_bot":
                botState = BotState.YAROSLAVL;
                break;
            case "/your_city":
            case "/Your_city":
                botState = BotState.YOUR_CITY;
                break;
            case"/your_city@over_weather_bot":
                replyMessage = new SendMessage(String.valueOf(message.getChatId()),
                    "Данная функция не работает в групповых чатах");
                return Optional.of(replyMessage);
            case "/how_change_weather@over_weather_bot":
            case "/how_change_weather":
            case "/How_Change_Weather":
                replyMessage = new SendMessage(String.valueOf(message.getChatId()),
                    "Если захочешь поменять погоду в своем городе, пожалуйста, отправь новую геопозицию.\n" +
                        "Для этого нажми на \"булавку\" и выбери раздел \"геопозиция\"");
                return Optional.of(replyMessage);
            default:
                return Optional.empty();

        }

        replyMessage = botStateContext.processInputMessage(botState, message);

        return Optional.of(replyMessage);
    }

    private Optional<BotApiMethod<?>> processCallbackQuery(CallbackQuery buttonQuery) {
        final long chatId = buttonQuery.getMessage().getChatId();
        Message message = buttonQuery.getMessage();
        message.getFrom().setId(message.getChatId());
        message.getFrom().setFirstName(message.getChat().getFirstName());
        message.getFrom().setLastName(message.getChat().getLastName());
        message.getFrom().setUserName(message.getChat().getUserName());
        SendMessage replyMessage;
        switch (buttonQuery.getData()) {
            case "buttonMoscow":
                //  userDataCache.getUsersCurrentBotState(userId);
                replyMessage = botStateContext.processButton(BotState.MOSCOW, chatId, message);
                return Optional.of(replyMessage);
            case "buttonPetersburg":
                replyMessage = botStateContext.processButton(BotState.SAINT_PETERSBURG, chatId,
                    message);
                return Optional.of(replyMessage);
            case "buttonYaroslavl":
                replyMessage = botStateContext.processButton(BotState.YAROSLAVL, chatId, message);
                return Optional.of(replyMessage);
            case "buttonYourCity":
                replyMessage = botStateContext.processButton(BotState.YOUR_CITY, chatId, message);
                return Optional.of(replyMessage);
            case "buttonHowChangeWeather":
                   replyMessage = new SendMessage(String.valueOf(message.getChatId()),
                       "Если захотите поменять погоду в вашем городе, отправьте, пожалуйста, новую геопозицию");
                   return  Optional.of(replyMessage);
        }

        return Optional.empty();
    }


}