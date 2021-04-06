package org.demka.api;

import javafx.collections.ObservableList;
import org.demka.exceptions.EmptyAPIResponseException;
import org.demka.exceptions.FalseServerFlagException;
import org.demka.exceptions.LongpollListenerException;
import org.demka.exceptions.RoomNotFoundException;
import org.demka.models.Message;
import org.demka.models.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LongPollRunnable implements Runnable{

    ObservableList<Room> roomData;
    ObservableList<Message> messageData;
    MyAPI apiSession;
    private static final Logger logger = LoggerFactory.getLogger(LongPollRunnable.class);

    public LongPollRunnable(ObservableList<Room> roomData, ObservableList<Message> messageData, MyAPI apiSession) {
        this.roomData = roomData;
        this.apiSession = apiSession;
        this.messageData = messageData;
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

    /***
     * Проверка на существование комнаты по её id
     * @param roomId
     * @return
     */
    public Integer isRoomExist(String roomId){
        for (int i = 0; i < roomData.size(); i++) {
            if (roomData.get(i).getId().equals(roomId)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void run() {

        while (true) {
            logger.info("LongPollRunnable - Работаем");
            //Пытаемся получить новые данные
            try {
                List<Message> newMessages = apiSession.longpollListener();

                for (Message msg: newMessages) {
                    //Проверка на существование комнаты
                    String messageRoomId = msg.getRoomId();
                    Integer existInt = isRoomExist(messageRoomId);

                    //Существует
                    if (existInt != -1){
                        //Добавляем сообщение
                        roomData.get(existInt).addMessage(msg);
                        logger.info("Добавили сообщение '"+msg.getText()+"' для комнаты "+msg.getRoomId());
                        //Если открыт уже диалог с текущей конференцией
                        if (messageRoomId.equals(apiSession.getCurrentRoomId())){
                            messageData.add(msg);
                        }
                    }

                    //Значит это сообщение с новой комнаты
                    else{
                        try {
                            //Создаем объект комнаты
                            Room newRoom = apiSession.getRoomInfo(messageRoomId);
                            //Добавляем полученное сообщение
                            newRoom.addMessage(msg);
                            //Добавляем саму комнату
                            roomData.add(newRoom);
                            logger.info("Получили новую комнату "+newRoom.getName()+" ["+newRoom.getId()+"]");
                        }
                        catch (RoomNotFoundException e){
                            logger.error("Случилось странное: бек отдал сообщение с комнаты "+messageRoomId+", но её не существует");
                        } catch (EmptyAPIResponseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            //Если необходимо заново пройти авторизацию - проходим
            catch (LongpollListenerException e) {
                System.out.println(e);
                try {
                    apiSession.getLongpollServer();
                } catch (EmptyAPIResponseException | FalseServerFlagException newE) {
                    newE.printStackTrace();
                }
            }

            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error("LongPollRunnable - Прекратил работу");
                return;
            }
        }
    }
}