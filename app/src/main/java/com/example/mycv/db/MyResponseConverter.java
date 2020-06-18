package com.example.mycv.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MyResponseConverter {
    @TypeConverter
    public static Education toEducation(String value) {
        Type education = new TypeToken<Education>() {}.getType();
        return new Gson().fromJson(value, education);
    }

    @TypeConverter
    public static String fromEducation(Education education) {
        Gson gson = new Gson();
        String json = gson.toJson(education);
        return json;
    }

    @TypeConverter
    public static Skills toSkills(String value) {
        Type skills = new TypeToken<Skills>() {}.getType();
        return new Gson().fromJson(value, skills);
    }

    @TypeConverter
    public static String fromSkills(Skills skills) {
        Gson gson = new Gson();
        String json = gson.toJson(skills);
        return json;
    }

    @TypeConverter
    public String fromJobList(List<Job> joblist) {
        if (joblist == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Job>>() {
        }.getType();
        String json = gson.toJson(joblist, type);
        return json;
    }

    @TypeConverter
    public List<Job> toJobList(String joblistString) {
        if (joblistString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Job>>() {
        }.getType();
        List<Job> joblist = gson.fromJson(joblistString, type);
        return joblist;
    }

    @TypeConverter
    public String fromProgramsList(List<Program> programslist) {
        if (programslist == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Program>>() {
        }.getType();
        String json = gson.toJson(programslist, type);
        return json;
    }

    @TypeConverter
    public List<Program> toProgramsList(String programslistString) {
        if (programslistString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Program>>() {
        }.getType();
        List<Program> programsList = gson.fromJson(programslistString, type);
        return programsList;
    }


}
