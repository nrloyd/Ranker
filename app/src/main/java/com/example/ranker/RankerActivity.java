package com.example.ranker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RankerActivity extends AppCompatActivity {
    public static final String GROUP_INFO = "com.example.ranker.GROUP_INFO";
    private RankGroup mGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranker);

        initializeDisplayContent();

        TextView textView = findViewById(R.id.textView);

        displayGroup(textView);
    }

    private void displayGroup(TextView textView) {
        textView.setText(mGroup.getTitle());
    }

    private void initializeDisplayContent() {
        Intent intent = getIntent();
        mGroup = intent.getParcelableExtra(GROUP_INFO);
    }
}
