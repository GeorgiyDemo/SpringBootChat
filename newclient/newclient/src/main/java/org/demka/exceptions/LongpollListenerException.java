package org.demka.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ошибка инициализации longpoll
 */
public class LongpollListenerException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(LongpollListenerException.class);
    public LongpollListenerException(String errorMessage) {
        super(errorMessage);
        logger.error(errorMessage);
    }
}
