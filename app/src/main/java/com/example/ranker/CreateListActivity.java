package com.example.ranker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateListActivity extends AppCompatActivity {
    public static final String GROUP_INFO = "com.example.ranker.GROUP_INFO";
    public static final int GROUP_POSITION = -1;
    private int groupPosition;
    private RankGroup mGroup;
    private boolean isNewGroup;
    private EditText editListTitle;
    private EditText editListContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        readDisplayStateValues();
        editListTitle = (EditText) findViewById(R.id.edit_list_name);
        editListContent = (EditText) findViewById(R.id.edit_list_content);
        if(!isNewGroup) displayExistingGroup();

        Button createButton = findViewById(R.id.save_list_button);
        createButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText editListTitle = (EditText) findViewById(R.id.edit_list_name);
                //EditText editListContent = (EditText) findViewById(R.id.edit_list_content);
                String listTitle = editListTitle.getText().toString();
                String listContent = editListContent.getText().toString();

                if(isNewGroup) {
                    DataManager.getInstance().getGroups().add(new RankGroup(listTitle,listContent.split("\\r?\\n")));
                } else {
                    mGroup.setTitle(listTitle);
                    mGroup.setStrings(listContent.split("\\r?\\n"));
                }

                Intent intent = new Intent(CreateListActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void readDisplayStateValues() {
        //get group information from intent
        Intent intent = getIntent();
        groupPosition = intent.getIntExtra(GROUP_INFO, GROUP_POSITION);
        try{
            mGroup = DataManager.getInstance().getGroups().get(groupPosition);
        } catch (ArrayIndexOutOfBoundsException e) {
            mGroup = null;
        }
        isNewGroup = mGroup == null;
    }

    private void displayExistingGroup() {
        editListTitle.setText(mGroup.getTitle());
        editListContent.setText(mGroup.getFormattedStrings());
    }
}
