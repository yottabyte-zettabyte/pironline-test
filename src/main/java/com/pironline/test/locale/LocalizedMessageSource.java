package com.pironline.test.locale;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class LocalizedMessageSource {

    private final Locale defaultLocale;
    private final MessageSource messageSource;

    @Autowired
    public LocalizedMessageSource(MessageSource messageSource, @Value("${application.locale}") String locale) {
        this.defaultLocale = new Locale(locale);
        this.messageSource = messageSource;
    }

    public String getMessage(String code) {
        return messageSource.getMessage(code, null, defaultLocale);
    }

    public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, defaultLocale);
    }

    public String getMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }

    public String getMessage(String code, Locale locale, Object[] args) {
        return messageSource.getMessage(code, args, locale);
    }

    public Locale getLocale() {
        return defaultLocale;
    }
}
