package com.example.ranker;

import android.os.Parcel;
import android.os.Parcelable;

public class RankGroup implements Parcelable {
    private String mTitle;
    private String[] mStrings;

    public RankGroup(String title, String[] strings){
        mTitle = title;
        mStrings = strings;
    }

    private RankGroup(Parcel source) {
        mTitle = source.readString();
        mStrings = source.createStringArray();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeStringArray(mStrings);
    }

    public static final Parcelable.Creator<RankGroup> CREATOR
            = new Parcelable.Creator<RankGroup>() {
        @Override
        public RankGroup createFromParcel(Parcel source) {
            return new RankGroup(source);
        }

        @Override
        public RankGroup[] newArray(int size) {
            return new RankGroup[size];
        }
    };
}
