package com.kebenny.learnchinesecharacters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kebenny on 2/23/2017.
 */

public class DailyProgressCursorAdapter extends CursorAdapter {
    public DailyProgressCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.listview_daily_progress, null, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        TextView tvTodayLearnt = (TextView) view.findViewById(R.id.tvTodayLearnt);
        TextView tvCumulativeLearnt = (TextView) view.findViewById(R.id.tvCumulativeLearnt);
        // Extract properties from cursor
        String sDate = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
        int iTodayLearnt = cursor.getInt(cursor.getColumnIndexOrThrow("iTodayLearnt"));
        int iCumulativeLearnt = cursor.getInt(cursor.getColumnIndexOrThrow("iCumulativeLearnt"));
        // Populate fields with extracted properties
        tvDate.setText(sDate);
        tvTodayLearnt.setText(String.valueOf(iTodayLearnt));
        tvCumulativeLearnt.setText(String.valueOf(iCumulativeLearnt));
    }
}