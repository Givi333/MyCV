package com.example.mycv.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ProgramConverter {
    @TypeConverter
    public String fromLinksList(List<String> linkList) {
        if (linkList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        String json = gson.toJson(linkList, type);
        return json;
    }

    @TypeConverter
    public List<Program> toLinksList(String linkListString) {
        if (linkListString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        List<Program> programsList = gson.fromJson(linkListString, type);
        return programsList;
    }
}
