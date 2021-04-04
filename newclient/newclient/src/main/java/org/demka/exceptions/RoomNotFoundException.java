package org.demka.exceptions;

import org.demka.utils.MyLogger;

/**
 * Ошибка, если комната не найдена
 */
public class RoomNotFoundException extends Exception {
    public RoomNotFoundException(String errorMessage) {
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}