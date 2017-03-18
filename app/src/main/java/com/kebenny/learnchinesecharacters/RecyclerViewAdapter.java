package com.kebenny.learnchinesecharacters;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kebenny on 2/25/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    static List<DatabaseModelLearningProgress> dbList;
    static Context context;
    static Integer height = 0;

    RecyclerViewAdapter(Context context, List<DatabaseModelLearningProgress> dbList) {
        this.dbList = new ArrayList<DatabaseModelLearningProgress>();
        this.context = context;
        this.dbList = dbList;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.learning_summary_row, null);


        // after adding these two lines, the android:layout_weight="1" in learning_summary_row.xml work
        // Calculate getMeasuredWidth is very fast.
        // However, calculate getMeasuredHeight is very slow. Therefore, add a static class variable so that no need to calculate for all items.
        int width = parent.getMeasuredWidth();

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            // Load the resolution into a Point object
            Point size = new Point();
            display.getSize(size);
            height = size.y/20;

        itemLayoutView.setLayoutParams(new RecyclerView.LayoutParams(width, height));
        itemLayoutView.setPadding(0,0,0,0);

        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

        holder.tvWord.setText(dbList.get(position).getWord());
        holder.tvLastCorrectDate.setText(dbList.get(position).getLastCorrectDate());
        holder.tvCorrectCount.setText(String.valueOf(dbList.get(position).getCorrectCount()));
        holder.tvWrongCount.setText(String.valueOf(dbList.get(position).getWrongCount()));
        holder.tvCorrectCombo.setText("(" + String.valueOf(dbList.get(position).getCorrectCombo())+ ")");
        holder.tvCorrectRate.setText(dbList.get(position).getCorrectRate());
    }

    @Override
    public int getItemCount() {
        return dbList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvWord, tvLastCorrectDate, tvCorrectCount, tvWrongCount, tvCorrectCombo, tvCorrectRate;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tvWord = (TextView) itemLayoutView.findViewById(R.id.tvidWord);
            tvLastCorrectDate = (TextView) itemLayoutView.findViewById(R.id.tvidLastCorrectDate);
            tvCorrectCount = (TextView) itemLayoutView.findViewById(R.id.tvidCorrectCount);
            tvWrongCount = (TextView) itemLayoutView.findViewById(R.id.tvidWrongCount);
            tvCorrectCombo = (TextView) itemLayoutView.findViewById(R.id.tvidCorrectCombo);
            tvCorrectRate = (TextView) itemLayoutView.findViewById(R.id.tvidCorrectRate);
        }

    }
}