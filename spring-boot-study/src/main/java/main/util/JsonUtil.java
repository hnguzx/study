package main.util;

import com.google.gson.*;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author 志雄
 */
public class JsonUtil {

    public static JsonObject stringToJson(String jsonString){
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(jsonString);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return jsonObject;
    }

    public static String JsonToString(JsonObject jsonObject){
        String asString = jsonObject.getAsString();
        return asString;
    }

    public static String getStringFromJson(String jsonString,String target){
        JsonObject jsonObject = stringToJson(jsonString);
        return getStringFromJson(jsonObject,target);
    }

    public static String getStringFromJson(JsonObject jsonObject,String target){
        JsonElement jsonElement = jsonObject.get(target);
        String jsonString = null;

        if (!jsonElement.isJsonNull()){
            jsonString =  String.valueOf(jsonObject.get(target));
        }
        return handleResp(jsonString);
    }

    public static String beanToJson(Object o){
        return new Gson().toJson(o);
    }

    public static Object jsonToObject(String jsonString,Class beanClass){
        Gson gson = new Gson();
        Object o = gson.fromJson(jsonString, beanClass);
        return o;
    }

    public static JsonArray stringToJsonArray(String jsonArrayString){
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(jsonArrayString);
        JsonArray asJsonArray = jsonElement.getAsJsonArray();
        return asJsonArray;
    }

    public static Iterator getIteratorFromJson(String jsonString){
        JsonObject jsonObject = stringToJson(jsonString);
        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
        Iterator<Map.Entry<String, JsonElement>> iterator = entries.iterator();
        return iterator;
    }

    public static String handleResp(String response) {
        if(response==null){
            return response;
        }
        if (response.startsWith("\"")) {
            response = response.substring(1);
        }
        if (response.endsWith("\"")) {
            response = response.substring(0, response.length() - 1);
        }
        return response;
    }
}
