package org.demka.utils;

import com.google.gson.Gson;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AuthUtil {

    private final FileProcessing fileProcessing;

    public AuthUtil(){

        Path paths = null;
        try {
            paths = Paths.get(this.getClass().getResource("/org/demka/data.json").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.fileProcessing = new FileProcessing(paths);
    }

    public String readKey(){
        return fileProcessing.DataRead();
    }

    public void writeKey(String key){
        fileProcessing.DataWrite(key);
    }


}
