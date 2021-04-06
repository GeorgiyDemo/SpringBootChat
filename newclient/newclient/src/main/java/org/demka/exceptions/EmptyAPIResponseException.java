package org.demka.exceptions;


import javafx.application.Platform;
import org.demka.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ошибка, возникающая при пустом ответе со стороны сервера (или, например, если нет интернета)
 */
public class EmptyAPIResponseException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(EmptyAPIResponseException.class);

    public EmptyAPIResponseException(App mainApp,String errorMessage) {
        super(errorMessage);
        logger.error(errorMessage);

        //Это синхронизация с main JavaFX потоком, чтоб там отрисовать эту сцену
        Platform.runLater(mainApp::ConnectionError);
    }


}