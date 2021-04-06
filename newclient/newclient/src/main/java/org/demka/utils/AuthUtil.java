package org.demka.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AuthUtil {

    private final FileProcessing fileProcessing;

    public AuthUtil(){

        //Получаем путь файла, где хранится авторизация
        String pathStr = null;
        try {
            pathStr = new File(".tempdata").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.fileProcessing = new FileProcessing(pathStr);
    }

    public String readKey(){
        return fileProcessing.DataRead();
    }

    public void writeKey(String key){
        fileProcessing.DataWrite(key);
    }


}
