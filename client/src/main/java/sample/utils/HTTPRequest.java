package sample.utils;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HTTPRequest {

    public static String sendGET(String urlString) {

        try {
            URL obj = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();

            //Если все хорошо
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();

            } else {
                System.out.println("GET получил код " + con.getResponseCode());
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static String sendPOST(String urlString, Map<String, String> paramsMap) {

        try {
            URL obj = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

            con.setDoOutput(true);
            OutputStream stream = con.getOutputStream();

            //Конвертируем параметры
            String params = StringMapToString.convert(paramsMap);
            stream.write(params.getBytes());
            stream.flush();
            stream.close();

            int responseCode = con.getResponseCode();

            //Успешно отработал
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();

            } else {
                System.out.println("POST получил код "+con.getResponseCode());
                return null;
            }
        }
        catch (Exception e){
            return null;
        }
    }

}


