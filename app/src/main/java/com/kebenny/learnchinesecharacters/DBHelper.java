package com.kebenny.learnchinesecharacters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kebenny on 2/19/2017.
 */
public class DBHelper extends SQLiteOpenHelper{
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "learnChi";

    static String selectedLevelForList;

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_chi_char = "CREATE TABLE IF NOT EXISTS chi_char ( 'word' TEXT PRIMARY KEY, 'level' INTEGER, 'remark' TEXT)";
        db.execSQL(CREATE_TABLE_chi_char);

        String CREATE_TABLE_learning_summary = "CREATE TABLE IF NOT EXISTS learning_summary ('_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'word' TEXT, 'correct_count' INTEGER, 'wrong_count' INTEGER, 'correct_combo' INTEGER, 'last_correct_date' TEXT, 'user_name' TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_learning_summary );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS learnChi");

        // Create tables again
        onCreate(db);

    }

    /* Retrive  data from database to list for RecyclerView*/
    public List<DatabaseModelLearningProgress> getDataFromDB(){
        List<DatabaseModelLearningProgress> modelList = new ArrayList<DatabaseModelLearningProgress>();
        String query = "select l._id, l.word, l.correct_count, l.wrong_count, l.correct_combo, l.last_correct_date from learning_summary l, chi_char c where l.word = c.word and c.level = '" + selectedLevelForList + "' order by l.correct_combo desc, l.correct_count desc, l.wrong_count desc";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                DatabaseModelLearningProgress model = new DatabaseModelLearningProgress();

                model.setWord(cursor.getString(1));
                model.setCorrectCount(cursor.getInt(2));
                model.setWrongCount(cursor.getInt(3));
                model.setCorrectCombo(cursor.getInt(4));
                model.setLastCorrectDate(cursor.getString(5));

                modelList.add(model);
            }while (cursor.moveToNext());
        }

        return modelList;
    }

}
