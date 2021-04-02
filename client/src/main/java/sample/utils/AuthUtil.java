package sample.utils;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;

public class AuthUtil {

    private FileProcessing fileProcessing;

    public AuthUtil(){
        URL fileName = getClass().getResource("/storage/data.json");
        this.fileProcessing = new FileProcessing(fileName.getPath());
    }

    public String readKey(){
        return fileProcessing.DataRead();
    }

    public void writeKey(String key){
        fileProcessing.DataWrite(key);
    }


}
