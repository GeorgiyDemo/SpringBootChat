package org.demka.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ошибка прослушки longPoll
 */
public class LongPollListenerException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(LongPollListenerException.class);

    /**
     * Ошибка прослушки лонгпула
     *
     * @param errorMessage - описание ошибки
     */
    public LongPollListenerException(String errorMessage) {
        super(errorMessage);
        logger.info(errorMessage);
    }
}
