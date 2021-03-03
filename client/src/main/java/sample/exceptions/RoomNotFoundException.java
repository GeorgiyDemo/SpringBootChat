package sample.exceptions;

/**
 * Ошибка, если комната не найдена
 */
public class RoomNotFoundException extends Exception {
    public RoomNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}