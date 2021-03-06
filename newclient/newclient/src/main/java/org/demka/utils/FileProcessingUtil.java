package org.demka.utils;

import java.io.*;
import java.util.stream.Collectors;

/**
 * Класс для работы с файлами
 */
public class FileProcessingUtil {

    /**
     * The File name.
     */
    String fileName;

    /**
     * Конструктор  FileProcessingUtil
     *
     * @param fileName - имя файла для работы
     */
    public FileProcessingUtil(String fileName) {
        this.fileName = fileName;

        File tempFile = new File(fileName);
        //Если файла не существует - создаем его
        if (!tempFile.exists()) {
            try {
                //Создаем новый файл, если его не существует
                tempFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Чтение данных
     *
     * @return string
     */
    public String DataRead() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.fileName));
            String lines = reader.lines().collect(Collectors.joining());
            reader.close();
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Запись данных
     *
     * @param data - данные для записи
     */
    public void DataWrite(String data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileName));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
