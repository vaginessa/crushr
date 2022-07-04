package com.tjm.crushr;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

/**
 * Created by tedmolinski on 8/9/15.
 */
public class crushrConfigActivity extends Activity {

    private int appWidgetId;
    private boolean shouldExecuteOnResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.crushr_config);
        appWidgetId = getIntent().getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
        shouldExecuteOnResume = false;

        // load previews
        loadPreviews();

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

        findViewById(R.id.configs_apply).setOnClickListener(v -> {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
            crushrProvider.updateAppWidget(getApplicationContext(), appWidgetManager, appWidgetId);

            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        });
    }

    private void loadPreviews() {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(crushrProvider.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        int primaryColor = prefs.getInt(crushrProvider.SHARED_PREF_PRIMARY_COLOR + appWidgetId, getApplicationContext().getResources().getColor(R.color.primary_color_1));
        int secondaryColor = prefs.getInt(crushrProvider.SHARED_PREF_SECONDARY_COLOR + appWidgetId, getApplicationContext().getResources().getColor(R.color.secondary_color_1));

        GradientDrawable borderPrimary = new GradientDrawable();
        GradientDrawable borderSecondary = new GradientDrawable();
        borderPrimary.setShape(GradientDrawable.OVAL);
        borderSecondary.setShape(GradientDrawable.OVAL);
        borderPrimary.setColor(primaryColor);
        borderSecondary.setColor(secondaryColor);
        borderPrimary.setStroke(1, 0xFF111111);
        borderSecondary.setStroke(1, 0xFF111111);

        (findViewById(R.id.primary_color_preview)).setBackground(borderPrimary);
        (findViewById(R.id.secondary_color_preview)).setBackground(borderSecondary);
    }

    protected void onResume() {
        super.onResume();
        if(shouldExecuteOnResume){
            loadPreviews();
        } else {
            shouldExecuteOnResume = true;
        }
    }
}
