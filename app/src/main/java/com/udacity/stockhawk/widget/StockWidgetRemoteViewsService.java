package com.udacity.stockhawk.widget;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import static android.view.View.INVISIBLE;

/**
 * RemoteViewsService controlling the data being shown in the scrollable weather detail widget
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class StockWidgetRemoteViewsService extends RemoteViewsService {
    public final String TAG = StockWidgetRemoteViewsService.class.getSimpleName();
    private static final String[] QUOTE_COLUMNS = {
            Contract.Quote.TABLE_NAME + "." + Contract.Quote._ID,
            Contract.Quote.COLUMN_SYMBOL,
            Contract.Quote.COLUMN_PRICE
    };
    // these indices must match the projection
    static final int INDEX_QUOTE_ID = 0;
    static final int INDEX_QUOTE_SYMBOL = 1;
    static final int INDEX_QUOTE_PRICE = 2;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }

                data = getContentResolver().query(Contract.Quote.uri,
                        QUOTE_COLUMNS,
                        null,
                        null,
                        Contract.Quote.COLUMN_SYMBOL);
                Log.d(TAG, "data: " + data);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_stock_list_item);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                    setRemoteContentDescription(views, data.getString(INDEX_QUOTE_SYMBOL));
                }

                String symbol = data.getString(INDEX_QUOTE_SYMBOL);
                String price = data.getString(INDEX_QUOTE_PRICE);

                views.setTextViewText(R.id.w_symbol, symbol);
                views.setTextViewText(R.id.w_price, price);
                views.setViewVisibility(R.id.widget_empty, INVISIBLE);

                Log.d(TAG, "getViewAt - position: " + position + " symbol: " + symbol);
                Log.d(TAG, "getViewAt - position: " + position + " price: " + price);

                final Intent fillInIntent = new Intent();
                fillInIntent.putExtra("symbol",symbol);
                views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);
                Log.d(TAG, "getViewAt - position: " + position + " views: " + views);
                return views;
            }

            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            private void setRemoteContentDescription(RemoteViews views, String description) {
                views.setContentDescription(R.id.symbol, description);
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_stock_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(INDEX_QUOTE_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
