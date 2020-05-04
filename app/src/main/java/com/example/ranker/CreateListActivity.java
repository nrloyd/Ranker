package com.example.ranker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        Button createButton = findViewById(R.id.create_list_button);
        createButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editListTitle = (EditText) findViewById(R.id.edit_list_name);
                EditText editListContent = (EditText) findViewById(R.id.edit_list_content);
                String listTitle = editListTitle.getText().toString();
                String listContent = editListContent.getText().toString();
                DataManager.getInstance().getGroups().add(new RankGroup(listTitle,listContent.split("\\r?\\n")));

                Intent intent = new Intent(CreateListActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
