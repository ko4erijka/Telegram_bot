package ru.ko4erijka.weather.bot;

import lombok.Setter;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ko4erijka.weather.bot.api.facade.TelegramFacade;

import java.util.Optional;

@Setter
public class WeatherTelegramBot extends TelegramWebhookBot {

    private String webHookPath;
    private String botUserName;
    private String botToken;

    public  WeatherTelegramBot(DefaultBotOptions botOptions, TelegramFacade telegramFacade) {
        super(botOptions);
        this.telegramFacade = telegramFacade;
    }

     private TelegramFacade telegramFacade;
    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        Optional<BotApiMethod<?>> replyMessageToUser = telegramFacade.handleUpdate(update);
        return replyMessageToUser.orElse(new SendMessage());
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }
}
