package sample.exceptions;

import sample.utils.MyLogger;

/**
 * Ошибка инициализации longpoll
 */
public class LongpollListenerException extends Exception {
    public LongpollListenerException(String errorMessage) {
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
