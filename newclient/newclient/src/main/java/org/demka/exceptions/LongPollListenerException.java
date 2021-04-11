package org.demka.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ошибка инициализации longPoll
 */
public class LongPollListenerException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(LongPollListenerException.class);

    public LongPollListenerException(String errorMessage) {
        super(errorMessage);
        logger.error(errorMessage);
    }
}
