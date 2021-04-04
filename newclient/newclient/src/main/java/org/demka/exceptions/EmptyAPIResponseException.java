package org.demka.exceptions;


import javafx.application.Platform;
import org.demka.App;
import org.demka.utils.MyLogger;

/**
 * Ошибка, возникающая при пустом ответе со стороны сервера (или, например, если нет интернета)
 */
public class EmptyAPIResponseException extends Exception {
    public EmptyAPIResponseException(App mainApp,String errorMessage) {
        super(errorMessage);
        MyLogger.logger.error(errorMessage);

        //Это синхронизация с main JavaFX потоком, чтоб там отрисовать эту сцену
        Platform.runLater(mainApp::ConnectionError);
    }
}