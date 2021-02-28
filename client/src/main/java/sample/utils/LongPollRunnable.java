package sample.utils;

import javafx.collections.ObservableList;
import sample.models.Message;
import sample.models.Room;

import java.util.ArrayList;
import java.util.List;

public class LongPollRunnable implements Runnable{

    ObservableList<Room> roomData;
    MyAPI apiSession;

    public LongPollRunnable(ObservableList<Room> roomData, MyAPI apiSession) {
        this.roomData = roomData;
        this.apiSession = apiSession;
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
        for (int i = 0; i < 100; i++) {
            try {
                //Пример добавления комнат
                Thread.sleep(1000);
                MyLogger.logger.info("LongPollRunnable.run - работает!");
                List<String> users = new ArrayList<>();
                Room bufferRoom = new Room("45436","LongPollRunnable"+i,343285,users,"85g677h8h6787g686g78"+i);
                roomData.add(bufferRoom);

                //Пример добавления сообщения в комнату
                Room thisRoom =roomData.get(0);
                for (int j = 0; j < 4; j++) {
                    Message bufMessage = new Message("1","тестовое сообщение от LongPollRunnable"+j,thisRoom.getId(),1234,"85g677h8h6g686g78"+j);
                    thisRoom.addMessage(bufMessage);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
