package com.example.ranker;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager ourInstance = null;

    private List<RankGroup> mGroups = new ArrayList<>();
    
    public static DataManager getInstance(){
        if(ourInstance == null){
            ourInstance = new DataManager();
            ourInstance.initializeExampleGroups();
        }
        return ourInstance;
    }

    public List<RankGroup> getGroups() {
        return mGroups;
    }

    private void initializeExampleGroups() {
        String[] group1 = {"Ravens", "Bengals", "Browns", "Steelers"};
        mGroups.add(new RankGroup("AFC North", group1));
        String[] group2 = {"Justin, JC, Chris, Joey, Lance"};
        mGroups.add(new RankGroup("NSYNC members", group2));
        String[] group3 =
                {"San Francisco", "Los Angeles", "Oakland", "Long Beach", "San Jose",
                        "San Diego", "Sacramento", "Fresno", "Anaheim", "Bakersfield"};
        mGroups.add(new RankGroup("California cities", group3));
        String[] group4 = {"ABC", "NBC", "PBS", "FOX", "CBS"};
        mGroups.add(new RankGroup("TV channels", group4));
    }
}
