package org.demka.runnable;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import org.demka.App;
import org.demka.api.MyAPI;
import org.demka.controllers.ConnectionErrorController;
import org.demka.controllers.MainChatController;
import org.demka.exceptions.EmptyAPIResponseException;
import org.demka.exceptions.FalseServerFlagException;
import org.demka.exceptions.LongPollListenerException;
import org.demka.exceptions.RoomNotFoundException;
import org.demka.models.Message;
import org.demka.models.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Отдельный поток для получения сообщений через логику лонгпулинга
 */
public class LongPollRunnable implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(LongPollRunnable.class);
    private final ObservableList<Room> roomData;
    private final ObservableList<Message> messageData;
    private final MyAPI myAPI;
    private final App app;
    private final MainChatController mainChatController;

    /**
     * Конструктор LongPollRunnable
     *
     * @param roomData    - ObservableList данных комнат, который синхронизирован с таблицей roomTable в JavaFX
     * @param messageData - ObservableList данных сообщений, который синхронизирован с таблицей messageTable в JavaFX
     * @param myAPI       - объект текущей сессии API для сапросов с бека
     * @param app         - объект app
     */
    public LongPollRunnable(ObservableList<Room> roomData, ObservableList<Message> messageData, MyAPI myAPI, App app, MainChatController mainChatController) {
        this.roomData = roomData;
        this.myAPI = myAPI;
        this.messageData = messageData;
        this.app = app;
        this.mainChatController = mainChatController;
    }

    /**
     * TODO: ВСЕ ПРОБЛЕМЫ ОТ ЭТОГО. Перемещение комнаты на первую позицию списка в основном потоке JavaFX
     *
     * @param roomData - список ObservableList
     * @param roomIndex - индекс комнаты, которую необходимо поднять
     */
    public void updateRoomData(ObservableList<Room> roomData, int roomIndex) {
        Room buffRoom = roomData.get(roomIndex);
        roomData.add(0, buffRoom);
        Platform.runLater(() -> roomData.remove(roomIndex + 1));

    }

    /**
     * Проверка на существование комнаты по её id
     *
     * @param roomId - идентификатор комнаты
     * @return
     */
    public Integer isRoomExist(String roomId) {
        for (int i = 0; i < roomData.size(); i++) {
            if (roomData.get(i).getId().equals(roomId)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            logger.info("LongPollRunnable - Работаем");
            //Пытаемся получить новые данные

            boolean exceptionFlag = false;
            try {
                List<Message> newMessages = myAPI.longPollListener();

                for (Message msg : newMessages) {
                    //Проверка на существование комнаты
                    String messageRoomId = msg.getRoomId();
                    Integer existInt = isRoomExist(messageRoomId);

                    //Существует
                    if (existInt != -1) {
                        //Добавляем сообщение
                        Room currentRoom = roomData.get(existInt);
                        currentRoom.addMessage(msg);
                        logger.info("Добавили сообщение '" + msg.getText() + "' для комнаты " + msg.getRoomId());
                        updateRoomData(roomData, existInt);

                        //Если открыт уже диалог с текущей конференцией
                        if (messageRoomId.equals(myAPI.getCurrentRoomId())) {
                            messageData.add(msg);
                            Platform.runLater(mainChatController::selectFirstSelectionModel);
                        }
                        //Иначе выставляем флаг нового сообщения для комнаты
                        else {
                            currentRoom.setNewMessageFlag(true);
                        }
                    }

                    //Значит это сообщение с новой комнаты
                    else {
                        try {
                            //Создаем объект комнаты
                            Room newRoom = myAPI.getRoomInfo(messageRoomId);
                            newRoom.setNewMessageFlag(true);
                            //Добавляем полученное сообщение
                            newRoom.addMessage(msg);
                            //Добавляем саму комнату
                            roomData.add(0, newRoom);
                            logger.info("Получили новую комнату " + newRoom.getName() + " [" + newRoom.getId() + "]");
                        } catch (RoomNotFoundException e) {
                            logger.error("Случилось странное: бек отдал сообщение с комнаты " + messageRoomId + ", но её не существует");
                        } catch (EmptyAPIResponseException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            //Если необходимо заново пройти авторизацию - проходим
            catch (LongPollListenerException e) {
                e.printStackTrace();
                try {
                    myAPI.getLongPollServer();
                } catch (EmptyAPIResponseException | FalseServerFlagException newE) {
                    exceptionFlag = true;
                    newE.printStackTrace();
                }
            }

            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Поток '" + Thread.currentThread().getName() + "' с id " + Thread.currentThread().getId() + " убит");
                return;
            }

            //Если находились в ConnectionErrorController, но при этом не получили ошибки, то обратно переходим в чат
            if ((!exceptionFlag) && (ConnectionErrorController.isActive)) {
                ConnectionErrorController.isActive = false;
                Platform.runLater(() -> app.myStart(app.getPrimaryStage()));
                RunnableManager.interruptAll();
                break;
            }
        }

        //Переходим отбратно в чат, а данный поток завершает свою работу
        logger.error("Поток '" + Thread.currentThread().getName() + "' с id " + Thread.currentThread().getId() + " завершил работу");
    }
}
