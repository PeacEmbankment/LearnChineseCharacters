package com.kebenny.learnchinesecharacters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class DailyProgress extends AppCompatActivity {


    String prefUserName;
    String combo_as_learnt;

    DailyProgressCursorAdapter dailyProgressCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_daily_progress);

        SharedPreferences appPreference = this.getSharedPreferences("App_Preference", Context.MODE_PRIVATE);
        prefUserName = appPreference.getString("user_name", null);
        combo_as_learnt = appPreference.getString("combo_as_learnt", null);

        DBHelper dbHelper = new DBHelper(this);

// Get access to the underlying writeable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
// Query for items from the database and get a cursor back
        //String sql = "select word, correct_combo, correct_count, _id from learning_Summary order by correct_count desc";

        String sql = "select substr(last_correct_date,1,10) _id, count(*) iTodayLearnt, (select count(*) from learning_Summary t2 where substr(t2.last_correct_date,1,10) <= substr(t1.last_correct_date,1,10)  and correct_combo >= " + combo_as_learnt + ") iCumulativeLearnt from learning_Summary t1 where correct_combo >= " + combo_as_learnt + "  and user_name='"  + prefUserName + "' group by substr(last_correct_date,1,10) order by substr(last_correct_date,1,10) desc";
        Cursor dailyProgressCursor = db.rawQuery(sql, null);

        // Find ListView to populate
        ListView listView1 = (ListView) findViewById(R.id.listView1);
// Setup cursor adapter using cursor from last step
        DailyProgressCursorAdapter dailyProgressCursorAdapter = new DailyProgressCursorAdapter(this, dailyProgressCursor );
// Attach cursor adapter to the ListView
        listView1.setAdapter(dailyProgressCursorAdapter);

        // add header

        View header = getLayoutInflater().inflate(R.layout.daily_progress_header, null, false);
        listView1.addHeaderView(header);
    }




}

