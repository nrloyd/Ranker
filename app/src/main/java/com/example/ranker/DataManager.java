package com.example.ranker;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager ourInstance = null;

    private List<RankGroup> mGroups = new ArrayList<>();
    
    public static DataManager getInstance(){
        if(ourInstance == null){
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    public static DataManager returnNewInstance() {
        ourInstance = new DataManager();
        return ourInstance;
    }

    public List<RankGroup> getGroups() {
        return mGroups;
    }
}
