package com.tjm.crushr;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by tedmolinski on 8/9/15.
 */
public class crushrConfigActivity extends Activity {

    private int appWidgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.crushr_config);
        appWidgetId = getIntent().getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);

        findViewById(R.id.primary_color_dialog).setOnClickListener(v -> {
            Intent configIntent = new Intent(getApplicationContext(), primaryColorDialog.class);
            configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            startActivity(configIntent);
        });

        findViewById(R.id.secondary_color_dialog).setOnClickListener(v -> {
            Intent configIntent = new Intent(getApplicationContext(), secondaryColorDialog.class);
            configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            startActivity(configIntent);
        });

        findViewById(R.id.input_ok).setOnClickListener(v -> {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
            crushrProvider.updateAppWidget(getApplicationContext(), appWidgetManager, appWidgetId);

            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
