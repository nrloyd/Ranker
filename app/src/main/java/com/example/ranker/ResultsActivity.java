package com.example.ranker;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

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
        final Intent intent = getIntent();
        groupPosition = intent.getIntExtra(GROUP_INFO, GROUP_POSITION);
        Log.d(TAG, "GROUP_INFO: " + GROUP_INFO);
        mGroup = DataManager.getInstance().getGroups().get(groupPosition);

        //populate list with items
        final RecyclerView recyclerItems = (RecyclerView) findViewById(R.id.ranked_list);
        final LinearLayoutManager itemsLayoutManager = new LinearLayoutManager(this);
        recyclerItems.setLayoutManager(itemsLayoutManager);

        List<String> ranks = new ArrayList<String>(Arrays.asList(mGroup.presentRanking()[0]));
        List<String> items = new ArrayList<String>(Arrays.asList(mGroup.presentRanking()[1]));

        RankedListRecyclerAdapter rankedListRecyclerAdapter = new RankedListRecyclerAdapter(this, ranks, items);
        recyclerItems.setAdapter(rankedListRecyclerAdapter);

        Button resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGroup.reset();
                Intent resetIntent = new Intent(ResultsActivity.this, RankerActivity.class);
                resetIntent.putExtra(RankerActivity.GROUP_INFO, groupPosition);
                resetIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(resetIntent);
            }
        });

        Button backToMainButton = findViewById(R.id.back_button);
        backToMainButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
