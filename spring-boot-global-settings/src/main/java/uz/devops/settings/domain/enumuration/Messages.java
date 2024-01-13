package uz.devops.settings.domain.enumuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import uz.devops.settings.service.configuration.ConfigurationServiceUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Nurislom
 * @see uz.devops.settings.domain.enumuration
 * @since 11/28/2023 10:54 AM
 */
@Getter
public enum Messages {

    NOT_FOUND(new Message("en", "%s not found!"), new Message("ru", "%s не найдено!"), new Message("uz", "%s topilmadi!")),
    NOT_NULL(new Message("en", "%s must not be null!"), new Message("ru", "%s не должно быть нулевым!"), new Message("uz", "%s null bo'lishi mumkin emas!"));

    private final Message[] messages;

    Messages(Message... messages) {
        this.messages = messages;
    }

    public String getMessageToCurrentRequest(Messages messages, Object... args) {
        return String.format(getMessageToCurrentRequest(messages), args);
    }

    public String getMessageToCurrentRequest(Messages messages) {
        return Arrays.stream(values())
                .filter(message -> {
                    return Objects.equals(message, messages);
                }).map(message -> Arrays.stream(message.getMessages())
                        .filter(text -> Objects.equals(text.language,
                                ConfigurationServiceUtils.getCurrentLanguage()))
                        .map(Message::getMessage).findFirst()).map(optionalMessage -> {
                    return optionalMessage.orElseGet(() -> messages.name());
                }).findFirst().orElse(messages.name());
    }

    @Data
    @AllArgsConstructor
    public static class Message {
        private String language;
        private String message;
    }

}
