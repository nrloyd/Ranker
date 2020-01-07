package com.example.ranker;

public class RankGroup {
    private String mTitle;
    private String[] mStrings;

    public RankGroup(String title, String[] strings){
        mTitle = title;
        mStrings = strings;
    }

    public String[] getStrings() {
        return mStrings;
    }

    public void setStrings(String[] strings) {
        mStrings = strings;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String toString(){
        return mTitle;
    }

}
