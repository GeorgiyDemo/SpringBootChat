package sample.exceptions;

import sample.utils.MyLogger;

/**
 * Обработка отрицательного ответа от сервера (response.result == false)
 */
public class FalseServerFlagException extends  Exception{
    public FalseServerFlagException(String URL, String serverResponse, String errorMessage) {
        super(errorMessage);
        MyLogger.logger.error(errorMessage+"\n"+"URL: "+URL+"\n"+"Ответ: "+serverResponse);
    }
}
