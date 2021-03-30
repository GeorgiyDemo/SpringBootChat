package sample.utils;

import java.util.Map;

public class StringMapToString {
    public static String convert(Map<String, String> inputMap){
        StringBuilder resultSb = new StringBuilder();
        for (String value: inputMap.values()) {

            resultSb.append(value);
            resultSb.append("=");
            resultSb.append(inputMap.get(value));
            resultSb.append("&");
        }
        resultSb.setLength(resultSb.length() - 1);
        return resultSb.toString();
    }
}
