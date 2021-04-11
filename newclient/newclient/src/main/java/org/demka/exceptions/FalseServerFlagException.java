package org.demka.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Обработка отрицательного ответа от сервера (response.result == false)
 */
public class FalseServerFlagException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(FalseServerFlagException.class);

    public FalseServerFlagException(String url, String serverResponse, String errorMessage) {
        super(errorMessage);
        logger.error(errorMessage + "\n" + "URL: " + url + "\n" + "Ответ: " + serverResponse);
    }
}
