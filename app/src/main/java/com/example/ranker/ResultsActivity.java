package com.example.ranker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    public static final String GROUP_INFO = "com.example.ranker.GROUP_INFO";
    public static final int GROUP_POSITION = -1;
    private int groupPosition;
    private RankGroup mGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        initializeDisplayContent();

        TextView textView = findViewById(R.id.group_title);

        displayGroup(textView);
    }

    private void displayGroup(TextView textView) {
        textView.setText(mGroup.getTitle());
    }

    private void initializeDisplayContent() {
        //get group information from intent
        Intent intent = getIntent();
        groupPosition = intent.getIntExtra(GROUP_INFO, GROUP_POSITION);
        Log.d(TAG, "GROUP_INFO: " + GROUP_INFO);
        mGroup = DataManager.getInstance().getGroups().get(groupPosition);

        //populate list with items
        final RecyclerView recyclerItems = (RecyclerView) findViewById(R.id.ranked_list);
        final LinearLayoutManager itemsLayoutManager = new LinearLayoutManager(this);
        recyclerItems.setLayoutManager(itemsLayoutManager);

        List<String> items = new ArrayList<String>(Arrays.asList(mGroup.getStrings()));

        RankedListRecyclerAdapter rankedListRecyclerAdapter = new RankedListRecyclerAdapter(this, items);
        recyclerItems.setAdapter(rankedListRecyclerAdapter);
    }
}
