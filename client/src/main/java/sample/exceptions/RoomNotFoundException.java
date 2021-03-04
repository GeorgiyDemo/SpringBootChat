package sample.exceptions;

import sample.utils.MyLogger;

/**
 * Ошибка, если комната не найдена
 */
public class RoomNotFoundException extends Exception {
    public RoomNotFoundException(String errorMessage) {
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}