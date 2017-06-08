package com.malikov.ticketsystem.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author Yurii Malikov
 */
public class MessageUtil {

    public static String getMessage(MessageSource messageSource, String messageIdentifier) {
        return messageSource.getMessage(messageIdentifier, null, LocaleContextHolder.getLocale());
    }
}
