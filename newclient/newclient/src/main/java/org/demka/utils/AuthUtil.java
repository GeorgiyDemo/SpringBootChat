package org.demka.utils;

import java.io.File;
import java.io.IOException;

public class AuthUtil {

    private final FileProcessingUtil fileProcessingUtil;

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

    public String readKey() {
        return fileProcessingUtil.DataRead();
    }

    public void writeKey(String key) {
        fileProcessingUtil.DataWrite(key);
    }


}
