package org.demka.runnable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для менеджмента потоков LongPollRunnable и CheckInternetRunnable
 */
public class RunnableManager {
    private static final Logger logger = LoggerFactory.getLogger(RunnableManager.class);
    /**
     * The constant threadsList.
     */
    public static List<Thread> threadsList = new ArrayList<>();

    /**
     * Прерывание всех потоков, которые есть в списке threadsList
     */
    public static void interruptAll() {

        for (Thread t : threadsList) {
            t.interrupt();
            logger.info("Отправили прерывание для потока с id " + t.getId() + " " + t.getName());
        }
    }
}
