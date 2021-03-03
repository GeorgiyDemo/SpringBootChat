package sample.exceptions;

/**
 * Ошибка инициализации longpoll
 */
public class LongpollListenerException extends Exception {
    public LongpollListenerException(String errorMessage) {
        super(errorMessage);
    }
}
