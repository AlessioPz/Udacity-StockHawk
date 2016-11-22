package com.udacity.stockhawk.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.utility.Utility;

import java.text.DateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    final private Context context;
    private String[] rows;

    public HistoryAdapter(Context context) {
        this.context = context;
    }

    public void setRows(String[] rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(context).inflate(R.layout.list_item_history, parent, false);

        return new HistoryAdapter.HistoryViewHolder(item);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {

        String[] historyComponents = rows[position].split(",");
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(Long.parseLong(historyComponents[0]));

        holder.hy_date.setText(Utility.formatDateToOutput(context, date));
        holder.hy_close.setText(historyComponents[1].trim());

    }

    @Override
    public int getItemCount() {
        return rows.length;
    }


    class HistoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.hy_date)
        TextView hy_date;

        @BindView(R.id.hy_close)
        TextView hy_close;

        HistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
