package com.kebenny.learnchinesecharacters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by kebenny on 2/19/2017.
 */
public class GuessCharView extends SurfaceView implements SurfaceHolder.Callback {

    Context context;

    // Our SurfaceHolder to lock the surface before we draw our graphics
    SurfaceHolder ourHolder;

    // A Canvas and a Paint object
    Canvas canvas;
    Paint paint;

    // The size of the screen in pixels
    int screenX;
    int screenY;
    String sGuessWord;
    String last_GuessWord = "";
    String prefUserName;
    String combo_as_learnt;
    Integer nextWord;

    // Random object
    Random random = new Random();

    SharedPreferences appPreference;

    Boolean levelClear = false;

    public GuessCharView(Context context, int x, int y) {

        // The next line of code asks the
        // SurfaceView class to set up our object.
        // How kind.
        super(context);

        // Make a globally available copy of the context so we can use it in another method
        this.context = context;

        // Initialize ourHolder and paint objects
        ourHolder = getHolder();
        ourHolder.addCallback(this);
        ourHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        paint = new Paint();

        screenX = x;
        screenY = y;

        appPreference = context.getSharedPreferences("App_Preference", Context.MODE_PRIVATE);
        prefUserName = appPreference.getString("user_name", null);
        combo_as_learnt = appPreference.getString("combo_as_learnt", null);

        //draw();
        //invalidate();

    }

    public void surfaceCreated(SurfaceHolder ourHolder) {

        draw();
        invalidate();
    }

    public void surfaceChanged(SurfaceHolder ourHolder, int format, int w, int h) {
        // set preview size etc here ... then
        //Toast.makeText(context, "inside surfaceChanged", Toast.LENGTH_LONG).show();
        //draw();
        //invalidate();
    }

    public void surfaceDestroyed(SurfaceHolder ourHolder) {

    }

    private void draw() {

        if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            // Draw the background color
            //canvas.drawColor(Color.argb(255, 26, 128, 182));
            //canvas.drawColor(Color.argb(255, 255, 255, 255)); // white
            canvas.drawColor(Color.argb(255, 174, 247, 163)); // light green

            paint.setColor(Color.argb(255, 0, 0, 0));
            //paint.setColor(Color.argb(255, 255, 255, 255));

            // get content
            //because getSharedPreferences is a method of Context class. In order to access getSharedPreferences method inside View class you need to provide it with an instance of Context class.

            setTextSizeForWidth(paint, screenY * 2 / 10, prefUserName);
            canvas.drawText(prefUserName, screenX * 5 / 100, screenY * 10 / 100, paint);

            // Daily progress button

            paint.setColor(Color.argb(255, 255, 255, 255));
            canvas.drawRect(screenX * 7 / 10, screenY * 3 / 100, screenX * 95 / 100, screenY * 8 / 100, paint);

            paint.setColor(Color.argb(255, 0, 0, 0));
            setTextSizeForWidth(paint, screenY * 9 / 100, "學習進度");
            canvas.drawText("學習進度", screenX * 75 / 100, screenY * 65 / 1000, paint);

            // 識字總結 button

            paint.setColor(Color.argb(255, 255, 255, 255));
            canvas.drawRect(screenX * 7 / 10, screenY * 10 / 100, screenX * 95 / 100, screenY * 15 / 100, paint);

            paint.setColor(Color.argb(255, 0, 0, 0));
            setTextSizeForWidth(paint, screenY * 9 / 100, "識字總結");
            canvas.drawText("識字總結", screenX * 75 / 100, screenY * 134 / 1000, paint);

            // generate "Character" for guess
            //Open connection to read data
            DBHelper dbHelper = new DBHelper(context);

            SQLiteDatabase db = dbHelper.getReadableDatabase();

            // check for selected level for SQL in clause

            String inClause="";
            if (appPreference.getString("GuessCharLevel1", "N").equals("Y")){
                inClause = "1";
            }
            if (appPreference.getString("GuessCharLevel2", "N").equals("Y")){
                inClause = inClause + ",2";
            }
            if (appPreference.getString("GuessCharLevel3", "N").equals("Y")){
                inClause = inClause + ",3";
            }
            if (appPreference.getString("GuessCharLevel4", "N").equals("Y")){
                inClause = inClause + ",4";
            }
            if (appPreference.getString("GuessCharLevel5", "N").equals("Y")){
                inClause = inClause + ",5";
            }
            if (appPreference.getString("GuessCharLevel6", "N").equals("Y")){
                inClause = inClause + ",6";
            }
            if (appPreference.getString("GuessCharLevel7", "N").equals("Y")){
                inClause = inClause + ",7";
            }
            if (inClause.startsWith(",")){
                inClause = inClause.substring(1); // remove the first character
            }

            String sql = "select c.word, c.level, c.remark from chi_char c, learning_summary l " +
                    "where l.word = c.word and l.correct_combo < " + combo_as_learnt + " and c.level in (" + inClause + ") and user_name='" + prefUserName + "'";;

            Cursor c = db.rawQuery(sql, null);
            //Log.d("GuessCharView","sql for generate guess char: " + sql);
            //Log.d("GuessCharView","getCount of generate guess char: " + c.getCount());

            // move to a position in cursor but not repated the last guess char
            Boolean repeatedGuessChar = true;
            //Log.d("GuessCharView","last_GuessWord:" + last_GuessWord);

            while (c.getCount() >=2 && repeatedGuessChar){
                nextWord = random.nextInt(c.getCount());
                c.moveToPosition(nextWord);
                if (c.getString(0).equals(last_GuessWord)){
                    repeatedGuessChar = true;
                    //Log.d("GuessCharView","ramdom char: '" + c.getString(0) + "' equals last_guessWord: '" + last_GuessWord + "'");
                } else {
                    repeatedGuessChar = false;
                }
            }

            if (c.getCount()==1){
                c.moveToFirst();
                //Log.d("GuessCharView","getCount=1");
            }

            // Display of guess Char or   "恭喜你，認完這程度的字了。"
            if (c.getCount() <=0){
                levelClear = true;
            } else {
                levelClear = false;
            }

            if (levelClear){
                sql = "select count(*) from learning_Summary l, chi_char c where l.word = c.word and  l.correct_combo>=" + combo_as_learnt + " and l.user_name='" + prefUserName + "'";;
                Cursor cCorrectCombo = db.rawQuery(sql, null);
                cCorrectCombo.moveToFirst();
                //Log.d("GuessCharView","cCorrectCombo.getInt(0): " + cCorrectCombo.getInt(0));
                if (cCorrectCombo.getInt(0) == 3000){
                    setTextSizeForWidth(paint, screenY * 4 / 10, "恭喜你，認完所有程度的字了。");
                    canvas.drawText("恭喜你，認完所有程度的字了。", screenX * 15 / 100, screenY * 90 / 100, paint);
                    Drawable dWinOneLevel = getResources().getDrawable(R.drawable.afei_word_9);
                    dWinOneLevel.setBounds(screenX * 15 / 100, screenY * 2 / 10, screenX * 85 / 100, screenY * 2 / 10 + screenX * 7 / 10 * 681 / 423);
                    dWinOneLevel.draw(canvas);
                } else {
                    setTextSizeForWidth(paint, screenY * 4 / 10, "恭喜你，認完這程度的字了。");
                    canvas.drawText("恭喜你，認完這程度的字了。", screenX * 15 / 100, screenY * 70 / 100, paint);
                    Drawable dWinOneLevel = getResources().getDrawable(R.drawable.afei_word_5);
                    dWinOneLevel.setBounds(screenX * 15 / 100, screenY * 2 / 10, screenX * 75 / 100, screenY * 2 / 10 + screenX * 6 / 10 * 611 / 522);
                    dWinOneLevel.draw(canvas);
                }
            } else {
                setTextSizeForWidth(paint, screenY * 2 / 10, "你識得這個字嗎？");
                canvas.drawText("你識得這個字嗎？", screenX * 5 / 100, screenY * 15 / 100, paint);

                sGuessWord =c.getString(0);
                last_GuessWord = sGuessWord;
                setTextSizeForWidth(paint, screenY * 3 / 10, "我");
                canvas.drawText(sGuessWord, screenX * 20 / 100, screenY * 45 / 100, paint);

                //  (這是新字。你已學會" + 9999 + "個字)
                String sqlLearningSummary = "select correct_combo from learning_summary where word = '" + sGuessWord + "' and user_name='" + prefUserName + "'";
                Cursor c_correct_count = db.rawQuery(sqlLearningSummary, null);

                c_correct_count.moveToPosition(0);
                Integer iWordClassification = c_correct_count.getInt(0);

                String sPrefix;

                if (iWordClassification > 0) {
                    sPrefix = "(已答對" + iWordClassification + "次。";
                }
                else {
                    sPrefix = "(這是新字。";
                }

                String sqlLearningSummaryUser = "select _id from learning_summary where correct_combo >= " + combo_as_learnt + " and user_name='" + prefUserName + "'";
                Cursor c_guess = db.rawQuery(sqlLearningSummaryUser, null);

                String learnt_word_msg = sPrefix + "你已學會" + c_guess.getCount() + "個字)";
                setTextSizeForWidth(paint, screenY * 5 / 10, learnt_word_msg);
                canvas.drawText(learnt_word_msg, screenX * 5 / 100, screenY * 55 / 100, paint);

                paint.setColor(Color.argb(255, 255, 255, 255));
                canvas.drawRect(screenX * 1 / 10, screenY * 6 / 10, screenX * 4 / 10, screenY * 7 / 10, paint);
                canvas.drawRect(screenX * 6 / 10, screenY * 6 / 10, screenX * 9 / 10, screenY * 7 / 10, paint);

                paint.setColor(Color.argb(255, 0, 0, 0));
                setTextSizeForWidth(paint, screenY * 8 / 100, "識");
                canvas.drawText("識", screenX * 17 / 100, screenY * 68 / 100, paint);

                setTextSizeForWidth(paint, screenY * 1 / 10, "Skip");
                canvas.drawText("Skip", screenX * 65 / 100, screenY * 67 / 100, paint);

                // display graphic
                Integer randomGraphicPosition = random.nextInt(5);
                Integer graphicLeft=0;
                Integer graphicRight=0;
                Integer graphicTop=0;
                Integer graphicBottom = 0;

                switch (randomGraphicPosition) {
                    case 0: graphicLeft = screenX * 5 / 100;
                            graphicBottom = screenY * 30 / 100;
                            break;
                    case 1: graphicLeft = screenX * 5 / 100;
                            graphicBottom = screenY * 50 / 100;
                            break;
                    case 2: graphicLeft = screenX * 80 / 100;
                        graphicBottom = screenY * 35 / 100;
                        break;
                    case 3: graphicLeft = screenX * 82 / 100;
                        graphicBottom = screenY * 45 / 100;
                        break;
                    case 4: graphicLeft = screenX * 45 / 100;
                        graphicBottom = screenY * 15 / 100;
                        break;

                }
                Drawable d;
                Integer randomGraphic = random.nextInt(9);

                switch (randomGraphic) {
                    case 0: d = getResources().getDrawable(R.drawable.afei_word_1);
                                graphicTop = graphicBottom - screenY * 12/ 100;
                                graphicRight = graphicLeft + (graphicBottom-graphicTop) * 162/194;
                                break;
                    case 1: d = getResources().getDrawable(R.drawable.afei_word_2);
                        graphicTop = graphicBottom - screenY * 12/ 100;
                        graphicRight = graphicLeft + (graphicBottom-graphicTop) * 162/194;
                        break;
                    case 2: d = getResources().getDrawable(R.drawable.afei_word_3);
                        graphicTop = graphicBottom - screenY * 8/ 100;
                        graphicRight = graphicLeft + (graphicBottom-graphicTop) * 186/143;
                        break;
                    case 3: d = getResources().getDrawable(R.drawable.afei_word_4);
                        graphicTop = graphicBottom - screenY * 8 / 100;
                        graphicRight = graphicLeft + (graphicBottom-graphicTop) * 186/143;
                        break;
                    case 4: d = getResources().getDrawable(R.drawable.afei_word_5);
                        graphicTop = graphicBottom - screenY * 11/ 100;
                        graphicRight = graphicLeft + (graphicBottom-graphicTop) * 522/611;
                        break;
                    case 5: d = getResources().getDrawable(R.drawable.afei_word_6);
                        graphicTop = graphicBottom - screenY * 11/ 100;
                        graphicRight = graphicLeft + (graphicBottom-graphicTop) * 423/507;
                        break;
                    case 6: d = getResources().getDrawable(R.drawable.afei_word_7);
                        graphicTop = graphicBottom - screenY * 8/ 100;
                        graphicRight = graphicLeft + (graphicBottom-graphicTop) * 484/373;
                        break;
                    case 7: d = getResources().getDrawable(R.drawable.afei_word_8);
                        graphicTop = graphicBottom - screenY * 10/ 100;
                        graphicRight = graphicLeft + (graphicBottom-graphicTop) * 423/507;
                        break;
                    case 8: d = getResources().getDrawable(R.drawable.afei_word_9);
                        graphicTop = graphicBottom - screenY * 11/ 100;
                        graphicRight = graphicLeft + (graphicBottom-graphicTop) * 423/681;
                        break;
                    default:d = getResources().getDrawable(R.drawable.afei_word_1);
                }

                d.setBounds(graphicLeft, graphicTop, graphicRight, graphicBottom);
                d.draw(canvas);
            }

            // Show "Record count: 3000" at bottom in initial development
            //Cursor c_chi_char_count = db.rawQuery("select word, level, remark from chi_char", null);
            //setTextSizeForWidth(paint, screenY * 5 / 10, "Record count: " + c_chi_char_count.getCount());
            //canvas.drawText("Record count: " + c_chi_char_count.getCount(), screenX * 10 / 100, screenY * 90 / 100, paint);

            // close database
            db.close();

            invalidate();
            ourHolder.unlockCanvasAndPost(canvas);

        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:

                // click 識 button
                if (((motionEvent.getX() > screenX * 1 / 10) && (motionEvent.getX() < screenX * 4 / 10)) &&
                                ((motionEvent.getY() > screenY * 6 / 10) && (motionEvent.getY() < screenY * 7 / 10))){

                    //Open connection to read data
                    DBHelper dbHelper = new DBHelper(context);

                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                    String sql = "select correct_combo, correct_count from learning_Summary where word = '" + sGuessWord + "' and user_name='" + prefUserName + "'";
                    Cursor cCorrectCombo = db.rawQuery(sql, null);
                    cCorrectCombo.moveToFirst();
                    ;
                    //Log.d("識:", sql + "; CorrectCombo: " + cCorrectCombo.getInt(0));
                    Integer iNewCorrectCombo = cCorrectCombo.getInt(0) + 1;
                    Integer iNewCorrectCount = cCorrectCombo.getInt(1) + 1;

                    sql = "update learning_Summary set correct_combo = " + iNewCorrectCombo + ", correct_count =" + iNewCorrectCount + ", last_correct_date=date('now', 'localtime') " + " where word = '" + sGuessWord + "' and user_name='" + prefUserName + "'";
                    //sql = "update learning_Summary set correct_combo = " + iNewCorrectCombo + ", correct_count =" + iNewCorrectCount + ", last_correct_date='2017-02-25' " + " where word = '" + sGuessWord + "' and user_name='" + prefUserName + "'";
                    db.execSQL(sql);
                    db.close();

                    draw();

                }

                // click Skip  button
                if (((motionEvent.getX() > screenX * 6 / 10) && (motionEvent.getX() < screenX * 9 / 10)) &&
                        ((motionEvent.getY() > screenY * 6 / 10) && (motionEvent.getY() < screenY * 7 / 10))){

                    //Open connection to read data
                    DBHelper dbHelper = new DBHelper(context);

                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                    String sql = "select correct_combo, wrong_count from learning_Summary where word = '" + sGuessWord + "' and user_name='" + prefUserName + "'";
                    Cursor cCorrectCombo = db.rawQuery(sql, null);

                    cCorrectCombo.moveToFirst();
                    ;
                    //Log.d("識:", sql + "; CorrectCombo: " + cCorrectCombo.getInt(0));
                    Integer iNewCorrectCombo = 0;
                    Integer iNewWrongCount = cCorrectCombo.getInt(1) + 1;

                    sql = "update learning_Summary set correct_combo = " + iNewCorrectCombo + ", wrong_count =" + iNewWrongCount+ " where word = '" + sGuessWord + "' and user_name='" + prefUserName + "'";
                    //Log.d("GuessCharView","sql of skip: " + sql);
                    db.execSQL(sql);

                    draw();
                }

                // click Daily Progress button
                if (((motionEvent.getX() > screenX * 7 / 10) && (motionEvent.getX() < screenX * 95 / 100)) &&
                        ((motionEvent.getY() > screenY * 3 / 100) && (motionEvent.getY() < screenY * 8 / 100))){

                    Intent inent = new Intent(getContext(), DailyProgress.class);

                    // calling an activity using <intent-filter> action name
                    //  Intent inent = new Intent("com.hmkcode.android.ANOTHER_ACTIVITY");

                    getContext().startActivity(inent);
                }

                // click 識字總結 button
                if (((motionEvent.getX() > screenX * 7 / 10) && (motionEvent.getX() < screenX * 95 / 100)) &&
                        ((motionEvent.getY() > screenY * 10 / 100) && (motionEvent.getY() < screenY * 15 / 100))){

                    Intent inent = new Intent(getContext(), Level_Summary.class);
                    //Intent inent = new Intent(getContext(), Learning_Summary.class);
                    //Intent inent = new Intent(getContext(), Learning_Summary_with_RecyclerView.class);
                    // calling an activity using <intent-filter> action name
                    //  Intent inent = new Intent("com.hmkcode.android.ANOTHER_ACTIVITY");

                    getContext().startActivity(inent);

                }

                break;
        }
        return true;

    }

    private void setTextSizeForWidth(Paint paint, float desiredWidth, String text) {

        // Pick a reasonably large value for the test. Larger values produce
        // more accurate results, but may cause problems with hardware
        // acceleration. But there are workarounds for that, too; refer to
        // http://stackoverflow.com/questions/6253528/font-size-too-large-to-fit-in-cache
        float testTextSize;
        if (text.length() < 5) {
            testTextSize = 24f;
        } else {
            testTextSize = 8f;
        }
        // Get the bounds of the text, using our testTextSize.
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        // Calculate the desired size as a proportion of our testTextSize.
        float desiredTextSize = testTextSize * desiredWidth / bounds.width();

        // Set the paint for that size.
        paint.setTextSize(desiredTextSize);
    }

}
