package sample.exceptions;


import sample.Main;
import sample.utils.MyLogger;

/**
 * Ошибка, возникающая при пустом ответе со стороны сервера (или, например, если нет интернета)
 */
public class EmptyAPIResponseException extends Exception {
    public EmptyAPIResponseException(Main mainApp,String errorMessage) {
        super(errorMessage);
        MyLogger.logger.error(errorMessage);


        synchronized(mainApp)
        {
            mainApp.ConnectionError();
            // Access shared variables and other
            // shared resources
        }
    }
}