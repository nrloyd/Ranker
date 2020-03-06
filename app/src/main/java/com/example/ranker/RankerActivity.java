package com.example.ranker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RankerActivity extends AppCompatActivity {
    public static final String GROUP_INFO = "com.example.ranker.GROUP_INFO";
    public static final int GROUP_POSITION = -1;
    private int groupPosition;
    private RankGroup mGroup;
    private TextView mLeftTextView;
    private TextView mRightTextView;
    private TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranker);

        initializeDisplayContent();


    }

    private void displayGroup() {
        mTitleTextView.setText(mGroup.getTitle());
        String[] items = mGroup.presentTwoItems();
        mLeftTextView.setText(items[0]);
        mRightTextView.setText(items[1]);
    }

    private void initializeDisplayContent() {
        //get group information from intent
        Intent intent = getIntent();
        groupPosition = intent.getIntExtra(GROUP_INFO, GROUP_POSITION);
        mGroup = DataManager.getInstance().getGroups().get(groupPosition);

        mTitleTextView = findViewById(R.id.group_title);
        mLeftTextView = findViewById(R.id.left_option_name);
        mRightTextView = findViewById(R.id.right_option_name);
        CardView leftCard = findViewById(R.id.left_option);
        CardView rightCard = findViewById(R.id.right_option);
        CardView tieCard = findViewById(R.id.tie_option);

        displayGroup();

        leftCard.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                generalOnClick(0);
            }
        });

        rightCard.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                generalOnClick(1);
            }
        });

        tieCard.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                generalOnClick(2);
            }
        });

    }

    private void generalOnClick(int i) {
        mGroup.selectWinner(i);
        if (mGroup.isSorted() == 1) {
            showResults();
        } else {
            displayGroup();
        }
    }

    private void showResults() {
        Intent intent = new Intent(RankerActivity.this, ResultsActivity.class);
        intent.putExtra(ResultsActivity.GROUP_INFO, groupPosition);
        startActivity(intent);
    }
}
