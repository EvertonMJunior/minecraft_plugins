package net.goodcraft.api.json;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JSONUtils {

    public static String toJson(Object jsonObject) {
        return new Gson().toJson(jsonObject);
    }

    public static Object fromJson(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }
}
