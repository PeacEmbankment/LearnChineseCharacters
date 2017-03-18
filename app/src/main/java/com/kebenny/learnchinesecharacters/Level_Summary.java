package com.kebenny.learnchinesecharacters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Level_Summary extends AppCompatActivity {
    Context context;
    SQLiteDatabase db;
    String prefUserName;
    String combo_as_learnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level__summary);

        //Open connection to read data
        DBHelper dbHelper = new DBHelper(this);

        final SharedPreferences appPreference = getSharedPreferences("App_Preference", Context.MODE_PRIVATE);
        prefUserName = appPreference.getString("user_name", null);
        combo_as_learnt = appPreference.getString("combo_as_learnt", null);

        String sql;
        Cursor cCorrectCombo;
        TextView t;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // set 總字數 of each level in table

        sql = "select count(*) from chi_char where level = 1";
        cCorrectCombo = db.rawQuery(sql, null);
        cCorrectCombo.moveToFirst();
        t = (TextView) findViewById(R.id.totalCharInLevel1);
        t.setText(cCorrectCombo.getString(0));

        sql = "select count(*) from chi_char where level = 2";
        cCorrectCombo = db.rawQuery(sql, null);
        cCorrectCombo.moveToFirst();
        t = (TextView) findViewById(R.id.totalCharInLevel2);
        t.setText(cCorrectCombo.getString(0));

        sql = "select count(*) from chi_char where level = 3";
        cCorrectCombo = db.rawQuery(sql, null);
        cCorrectCombo.moveToFirst();
        t = (TextView) findViewById(R.id.totalCharInLevel3);
        t.setText(cCorrectCombo.getString(0));

        sql = "select count(*) from chi_char where level = 4";
        cCorrectCombo = db.rawQuery(sql, null);
        cCorrectCombo.moveToFirst();
        t = (TextView) findViewById(R.id.totalCharInLevel4);
        t.setText(cCorrectCombo.getString(0));

        sql = "select count(*) from chi_char where level = 5";
        cCorrectCombo = db.rawQuery(sql, null);
        cCorrectCombo.moveToFirst();
        t = (TextView) findViewById(R.id.totalCharInLevel5);
        t.setText(cCorrectCombo.getString(0));

        sql = "select count(*) from chi_char where level = 6";
        cCorrectCombo = db.rawQuery(sql, null);
        cCorrectCombo.moveToFirst();
        t = (TextView) findViewById(R.id.totalCharInLevel6);
        t.setText(cCorrectCombo.getString(0));

        sql = "select count(*) from chi_char where level = 7";
        cCorrectCombo = db.rawQuery(sql, null);
        cCorrectCombo.moveToFirst();
        t = (TextView) findViewById(R.id.totalCharInLevel7);
        t.setText(cCorrectCombo.getString(0));

        sql = "select count(*) from chi_char";
        cCorrectCombo = db.rawQuery(sql, null);
        cCorrectCombo.moveToFirst();
        t = (TextView) findViewById(R.id.totalCharInAllLevel);
        t.setText(cCorrectCombo.getString(0));

        // set 己學會 of each level in table

        sql = "select count(*) from learning_Summary l, chi_char c where l.word = c.word and c.level = 1 and  l.correct_combo>=" + combo_as_learnt + " and l.user_name='" + prefUserName + "'";;
        cCorrectCombo = db.rawQuery(sql, null);
        cCorrectCombo.moveToFirst();
        t = (TextView) findViewById(R.id.learntCharInLevel1);
        t.setText(cCorrectCombo.getString(0));

        sql = "select count(*) from learning_Summary l, chi_char c where l.word = c.word and c.level = 2 and  l.correct_combo>=" + combo_as_learnt + " and l.user_name='" + prefUserName + "'";;
        cCorrectCombo = db.rawQuery(sql, null);
        cCorrectCombo.moveToFirst();
        t = (TextView) findViewById(R.id.learntCharInLevel2);
        t.setText(cCorrectCombo.getString(0));

        sql = "select count(*) from learning_Summary l, chi_char c where l.word = c.word and c.level = 3 and  l.correct_combo>=" + combo_as_learnt + " and l.user_name='" + prefUserName + "'";;
        cCorrectCombo = db.rawQuery(sql, null);
        cCorrectCombo.moveToFirst();
        t = (TextView) findViewById(R.id.learntCharInLevel3);
        t.setText(cCorrectCombo.getString(0));

        sql = "select count(*) from learning_Summary l, chi_char c where l.word = c.word and c.level = 4 and  l.correct_combo>=" + combo_as_learnt + " and l.user_name='" + prefUserName + "'";;
        cCorrectCombo = db.rawQuery(sql, null);
        cCorrectCombo.moveToFirst();
        t = (TextView) findViewById(R.id.learntCharInLevel4);
        t.setText(cCorrectCombo.getString(0));

        sql = "select count(*) from learning_Summary l, chi_char c where l.word = c.word and c.level = 5 and  l.correct_combo>=" + combo_as_learnt + " and l.user_name='" + prefUserName + "'";;
        cCorrectCombo = db.rawQuery(sql, null);
        cCorrectCombo.moveToFirst();
        t = (TextView) findViewById(R.id.learntCharInLevel5);
        t.setText(cCorrectCombo.getString(0));

        sql = "select count(*) from learning_Summary l, chi_char c where l.word = c.word and c.level = 6 and  l.correct_combo>=" + combo_as_learnt + " and l.user_name='" + prefUserName + "'";;
        cCorrectCombo = db.rawQuery(sql, null);
        cCorrectCombo.moveToFirst();
        t = (TextView) findViewById(R.id.learntCharInLevel6);
        t.setText(cCorrectCombo.getString(0));

        sql = "select count(*) from learning_Summary l, chi_char c where l.word = c.word and c.level = 7 and  l.correct_combo>=" + combo_as_learnt + " and l.user_name='" + prefUserName + "'";;
        cCorrectCombo = db.rawQuery(sql, null);
        cCorrectCombo.moveToFirst();
        t = (TextView) findViewById(R.id.learntCharInLevel7);
        t.setText(cCorrectCombo.getString(0));

        sql = "select count(*) from learning_Summary l, chi_char c where l.word = c.word and  l.correct_combo>=" + combo_as_learnt + " and l.user_name='" + prefUserName + "'";;
        cCorrectCombo = db.rawQuery(sql, null);
        cCorrectCombo.moveToFirst();
        t = (TextView) findViewById(R.id.learntCharInAllLevel);
        t.setText(cCorrectCombo.getString(0));

        db.close();

        // set Toggle Button of each level

        ToggleButton mToggle1;
        mToggle1 = (ToggleButton)findViewById(R.id.levelToggle1);
        if ( appPreference.getString("GuessCharLevel1", "N").equals("Y")) {
            mToggle1.setChecked(true);
        } else {
            mToggle1.setChecked(false);
        }

        ToggleButton mToggle2;
        mToggle2 = (ToggleButton)findViewById(R.id.levelToggle2);
        if ( appPreference.getString("GuessCharLevel2", "N").equals("Y")) {
            mToggle2.setChecked(true);
        } else {
            mToggle2.setChecked(false);
        }

        ToggleButton mToggle3;
        mToggle3 = (ToggleButton)findViewById(R.id.levelToggle3);
        if ( appPreference.getString("GuessCharLevel3", "N").equals("Y")) {
            mToggle3.setChecked(true);
        } else {
            mToggle3.setChecked(false);
        }

        ToggleButton mToggle4;
        mToggle4 = (ToggleButton)findViewById(R.id.levelToggle4);
        if ( appPreference.getString("GuessCharLevel4", "N").equals("Y")) {
            mToggle4.setChecked(true);
        } else {
            mToggle4.setChecked(false);
        }

        ToggleButton mToggle5;
        mToggle5 = (ToggleButton)findViewById(R.id.levelToggle5);
        if ( appPreference.getString("GuessCharLevel5", "N").equals("Y")) {
            mToggle5.setChecked(true);
        } else {
            mToggle5.setChecked(false);
        }


        ToggleButton mToggle6;
        mToggle6 = (ToggleButton)findViewById(R.id.levelToggle6);
        if ( appPreference.getString("GuessCharLevel6", "N").equals("Y")) {
            mToggle6.setChecked(true);
        } else {
            mToggle6.setChecked(false);
        }

        ToggleButton mToggle7;
        mToggle7 = (ToggleButton)findViewById(R.id.levelToggle7);
        if ( appPreference.getString("GuessCharLevel7", "N").equals("Y")) {
            mToggle7.setChecked(true);
        } else {
            mToggle7.setChecked(false);
        }


        // toggle Button actions of each level

        final ToggleButton tb1 = (ToggleButton) findViewById(R.id.levelToggle1);
        // attach an OnClickListener
        tb1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Is the toggle on?
                boolean on = ((ToggleButton) v).isChecked();
                if (on) {
                    SharedPreferences.Editor editor = appPreference.edit();
                    editor.putString("GuessCharLevel1", "Y");
                    editor.commit();

                } else {
                    if (
                            //appPreference.getString("GuessCharLevel1", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel2", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel3", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel4", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel5", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel6", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel7", "N").equals("N")
                            ){
                        tb1.setChecked(true);
                        Toast.makeText(getApplicationContext(), "至少要選一個程度", Toast.LENGTH_LONG).show();
                    } else {
                        SharedPreferences.Editor editor = appPreference.edit();
                        editor.putString("GuessCharLevel1", "N");
                        editor.commit();
                    }
                }
            }
        });

        final ToggleButton tb2 = (ToggleButton) findViewById(R.id.levelToggle2);
        // attach an OnClickListener
        tb2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Is the toggle on?
                boolean on = ((ToggleButton) v).isChecked();
                if (on) {
                    SharedPreferences.Editor editor = appPreference.edit();
                    editor.putString("GuessCharLevel2", "Y");
                    editor.commit();

                } else {
                    if ( appPreference.getString("GuessCharLevel1", "N").equals("N") &&
                            //appPreference.getString("GuessCharLevel2", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel3", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel4", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel5", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel6", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel7", "N").equals("N")
                            ){
                        tb2.setChecked(true);
                        Toast.makeText(getApplicationContext(), "至少要選一個程度", Toast.LENGTH_LONG).show();
                    } else {
                        //Log.d("Level_Summary","GuessCarLevel2 isChecked NOT on");
                        SharedPreferences.Editor editor = appPreference.edit();
                        editor.putString("GuessCharLevel2", "N");
                        editor.commit();
                    }
                }
            }
        });

        final ToggleButton tb3 = (ToggleButton) findViewById(R.id.levelToggle3);
        // attach an OnClickListener
        tb3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Is the toggle on?
                boolean on = ((ToggleButton) v).isChecked();
                if (on) {
                    SharedPreferences.Editor editor = appPreference.edit();
                    editor.putString("GuessCharLevel3", "Y");
                    editor.commit();
                } else {
                    if ( appPreference.getString("GuessCharLevel1", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel2", "N").equals("N") &&
                            //appPreference.getString("GuessCharLevel3", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel4", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel5", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel6", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel7", "N").equals("N")
                            ){
                        tb3.setChecked(true);
                        Toast.makeText(getApplicationContext(), "至少要選一個程度", Toast.LENGTH_LONG).show();
                    } else {
                        SharedPreferences.Editor editor = appPreference.edit();
                        editor.putString("GuessCharLevel3", "N");
                        editor.commit();
                    }
                }
            }
        });

        final ToggleButton tb4 = (ToggleButton) findViewById(R.id.levelToggle4);
        // attach an OnClickListener
        tb4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Is the toggle on?
                boolean on = ((ToggleButton) v).isChecked();
                if (on) {
                    SharedPreferences.Editor editor = appPreference.edit();
                    editor.putString("GuessCharLevel4", "Y");
                    editor.commit();

                } else {
                    if ( appPreference.getString("GuessCharLevel1", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel2", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel3", "N").equals("N") &&
                            //appPreference.getString("GuessCharLevel4", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel5", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel6", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel7", "N").equals("N")
                            ){
                        tb4.setChecked(true);
                        Toast.makeText(getApplicationContext(), "至少要選一個程度", Toast.LENGTH_LONG).show();
                    } else {
                        SharedPreferences.Editor editor = appPreference.edit();
                        editor.putString("GuessCharLevel4", "N");
                        editor.commit();
                    }
                }
            }
        });

        final ToggleButton tb5 = (ToggleButton) findViewById(R.id.levelToggle5);
        // attach an OnClickListener
        tb5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Is the toggle on?
                boolean on = ((ToggleButton) v).isChecked();
                if (on) {
                    SharedPreferences.Editor editor = appPreference.edit();
                    editor.putString("GuessCharLevel5", "Y");
                    editor.commit();
                } else {
                    if ( appPreference.getString("GuessCharLevel1", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel2", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel3", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel4", "N").equals("N") &&
                            //appPreference.getString("GuessCharLevel5", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel6", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel7", "N").equals("N")
                            ){
                        tb5.setChecked(true);
                        Toast.makeText(getApplicationContext(), "至少要選一個程度", Toast.LENGTH_LONG).show();
                    } else {
                        SharedPreferences.Editor editor = appPreference.edit();
                        editor.putString("GuessCharLevel5", "N");
                        editor.commit();
                    }
                }
            }
        });

        final ToggleButton tb6 = (ToggleButton) findViewById(R.id.levelToggle6);
        // attach an OnClickListener
        tb6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Is the toggle on?
                boolean on = ((ToggleButton) v).isChecked();
                if (on) {
                    SharedPreferences.Editor editor = appPreference.edit();
                    editor.putString("GuessCharLevel6", "Y");
                    editor.commit();
                } else {
                    if ( appPreference.getString("GuessCharLevel1", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel2", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel3", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel4", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel5", "N").equals("N") &&
                            //appPreference.getString("GuessCharLevel6", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel7", "N").equals("N")
                            ){
                        tb6.setChecked(true);
                        Toast.makeText(getApplicationContext(), "至少要選一個程度", Toast.LENGTH_LONG).show();
                    } else {
                        SharedPreferences.Editor editor = appPreference.edit();
                        editor.putString("GuessCharLevel6", "N");
                        editor.commit();
                    }
                }
            }
        });

        final ToggleButton tb7 = (ToggleButton) findViewById(R.id.levelToggle7);
        // attach an OnClickListener
        tb7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Is the toggle on?
                boolean on = ((ToggleButton) v).isChecked();
                if (on) {
                    SharedPreferences.Editor editor = appPreference.edit();
                    editor.putString("GuessCharLevel7", "Y");
                    editor.commit();
                } else {
                    if ( appPreference.getString("GuessCharLevel1", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel2", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel3", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel4", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel5", "N").equals("N") &&
                            appPreference.getString("GuessCharLevel6", "N").equals("N")
                            //appPreference.getString("GuessCharLevel7", "N").equals("N")
                            ){
                        tb7.setChecked(true);
                        Toast.makeText(getApplicationContext(), "至少要選一個程度", Toast.LENGTH_LONG).show();
                    } else {
                        SharedPreferences.Editor editor = appPreference.edit();
                        editor.putString("GuessCharLevel7", "N");
                        editor.commit();
                    }
                }
            }
        });


        // Buton actions of each level to initiate list of words

        final Button button1 = (Button) findViewById(R.id.levelButton1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentLearningSummary = new Intent(getBaseContext(), Learning_Summary_with_RecyclerView.class);
                intentLearningSummary.putExtra("selectedLevel","1");
                startActivity(intentLearningSummary);
            }
        });

        final Button button2 = (Button) findViewById(R.id.levelButton2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentLearningSummary = new Intent(getBaseContext(), Learning_Summary_with_RecyclerView.class);
                intentLearningSummary.putExtra("selectedLevel","2");
                startActivity(intentLearningSummary);
            }
        });

        final Button button3 = (Button) findViewById(R.id.levelButton3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentLearningSummary = new Intent(getBaseContext(), Learning_Summary_with_RecyclerView.class);
                intentLearningSummary.putExtra("selectedLevel","3");
                startActivity(intentLearningSummary);
            }
        });

        final Button button4 = (Button) findViewById(R.id.levelButton4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentLearningSummary = new Intent(getBaseContext(), Learning_Summary_with_RecyclerView.class);
                intentLearningSummary.putExtra("selectedLevel","4");
                startActivity(intentLearningSummary);
            }
        });

        final Button button5 = (Button) findViewById(R.id.levelButton5);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentLearningSummary = new Intent(getBaseContext(), Learning_Summary_with_RecyclerView.class);
                intentLearningSummary.putExtra("selectedLevel","5");
                startActivity(intentLearningSummary);
            }
        });

        final Button button6 = (Button) findViewById(R.id.levelButton6);
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentLearningSummary = new Intent(getBaseContext(), Learning_Summary_with_RecyclerView.class);
                intentLearningSummary.putExtra("selectedLevel","6");
                startActivity(intentLearningSummary);
            }
        });

        final Button button7 = (Button) findViewById(R.id.levelButton7);
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentLearningSummary = new Intent(getBaseContext(), Learning_Summary_with_RecyclerView.class);
                intentLearningSummary.putExtra("selectedLevel","7");
                startActivity(intentLearningSummary);
            }
        });

    }
}
