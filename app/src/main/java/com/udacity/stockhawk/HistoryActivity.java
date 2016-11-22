package com.udacity.stockhawk;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.ui.HistoryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity {

    private HistoryAdapter adapter;

    private String symbol = "";

    @BindView(R.id.hy_recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        symbol = getIntent().getStringExtra("symbol");
        adapter = new HistoryAdapter(this);

        getSupportActionBar().setTitle(symbol + " " + getString(R.string.history_title));

        Cursor cursor = this.getContentResolver().query(
                Contract.Quote.uri,
                null,
                Contract.Quote.COLUMN_SYMBOL + " = ?",
                new String[]{symbol},
                null);
        if (cursor.moveToFirst()) {
            String history = cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_HISTORY));
            adapter.setRows(history.split("\n"));
        }

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
