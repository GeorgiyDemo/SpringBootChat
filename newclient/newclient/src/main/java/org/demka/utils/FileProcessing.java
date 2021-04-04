package org.demka.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

// Класс для работы с файлами
public class FileProcessing {

    Path filePath;

    public FileProcessing(Path filePath) {
        this.filePath = filePath;
    }

    //Чтение данных
    public String DataRead(){
        try {
            return new String(Files.readAllBytes(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Запись данных
    public void DataWrite(String data){
        byte[] strToBytes = data.getBytes();
        try {
            Files.write(filePath, strToBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
