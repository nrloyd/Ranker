package com.example.ranker;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GroupRecyclerAdapter groupRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateListActivity.class));
            }
        });
        
        initializeDisplayContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        groupRecyclerAdapter.notifyDataSetChanged();
    }

    private void initializeDisplayContent() {

        //populate list with group names
        //final ListView listGroups = findViewById(R.id.list_groups);
        final RecyclerView recyclerGroups = (RecyclerView) findViewById(R.id.list_groups);
        final LinearLayoutManager groupsLayoutManager = new LinearLayoutManager(this);
        recyclerGroups.setLayoutManager(groupsLayoutManager);

        List<RankGroup> groups = DataManager.getInstance().getGroups();

        groupRecyclerAdapter = new GroupRecyclerAdapter(this, groups);
        recyclerGroups.setAdapter(groupRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
