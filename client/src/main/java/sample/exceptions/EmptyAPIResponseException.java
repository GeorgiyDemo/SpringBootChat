package sample.exceptions;


import javafx.application.Platform;
import sample.Main;
import sample.utils.MyLogger;

/**
 * Ошибка, возникающая при пустом ответе со стороны сервера (или, например, если нет интернета)
 */
public class EmptyAPIResponseException extends Exception {
    public EmptyAPIResponseException(Main mainApp,String errorMessage) {
        super(errorMessage);
        MyLogger.logger.error(errorMessage);

        //Это синхронизация с main JavaFX потоком, чтоб там отрисовать эту сцену
        Platform.runLater(mainApp::ConnectionError);
    }
}