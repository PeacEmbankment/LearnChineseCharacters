package com.kebenny.learnchinesecharacters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Learning_Summary_with_RecyclerView extends AppCompatActivity {

    DBHelper helper;
    List<DatabaseModelLearningProgress> dbList;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String selectedLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning__summary_with__recycler_view);

        // getIntent() is a method from the started activity
        Intent myIntent = getIntent(); // gets the previously created intent
        selectedLevel = myIntent.getStringExtra("selectedLevel");

        //Log.d("Selected Level",selectedLevel.toString());

        helper = new DBHelper(this);
        DBHelper.selectedLevelForList=selectedLevel;
        dbList= new ArrayList<DatabaseModelLearningProgress>();
        dbList = helper.getDataFromDB();

        mRecyclerView = (RecyclerView)findViewById(R.id.recycleview_in_learning_summary);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerViewAdapter(this,dbList);
        mRecyclerView.setAdapter(mAdapter);

    }
}
