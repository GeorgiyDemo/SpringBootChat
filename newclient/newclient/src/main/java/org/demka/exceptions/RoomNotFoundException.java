package org.demka.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ошибка, если комната не найдена
 */
public class RoomNotFoundException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(RoomNotFoundException.class);

    /**
     * Ошибка, если комната не найдена
     *
     * @param errorMessage - описание ошибки
     */
    public RoomNotFoundException(String errorMessage) {
        super(errorMessage);
        logger.error(errorMessage);
    }
}