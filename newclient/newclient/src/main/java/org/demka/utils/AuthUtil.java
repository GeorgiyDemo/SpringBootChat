package org.demka.utils;

import java.io.File;
import java.io.IOException;

/**
 * Класс для работы с автоавторизацией
 */
public class AuthUtil {

    private final FileProcessingUtil fileProcessingUtil;

    /**
     * Констркутор класса AuthUtil.
     * Пытается найти файл .tempdata, если не существует - создает его
     */
    public AuthUtil() {

        //Получаем путь файла, где хранится авторизация
        String pathStr = null;
        try {
            pathStr = new File(".tempdata").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.fileProcessingUtil = new FileProcessingUtil(pathStr);
    }

    /**
     * Чтение данных из файла
     *
     * @return string
     */
    public String readKey() {
        return fileProcessingUtil.DataRead();
    }

    /**
     * Запись данных в файл
     *
     * @param data - данные для записи
     */
    public void writeKey(String data) {
        fileProcessingUtil.DataWrite(data);
    }


}
