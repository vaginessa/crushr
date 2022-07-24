package rasel.neo.crushr;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.widget.RemoteViews;

import androidx.core.content.ContextCompat;

import java.util.HashSet;
import java.util.Set;

import rasel.neo.crushr.dialogs.NewTaskDialog;
import rasel.neo.crushr.dialogs.SingleTaskDialog;

public class CrushrProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews widgetViews = new RemoteViews(context.getPackageName(), R.layout.crushr_widget);

        Intent listIntent = new Intent(context, WidgetService.class);
        listIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        listIntent.setData(Uri.parse(listIntent.toUri(Intent.URI_INTENT_SCHEME)));
        widgetViews.setRemoteAdapter(R.id.crushr_listview, listIntent);

        Intent addIntent = new Intent(context, NewTaskDialog.class);
        addIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent addPendingIntent = PendingIntent.getActivity(context, appWidgetId, addIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        widgetViews.setOnClickPendingIntent(R.id.add_crushr_button, addPendingIntent);

        Intent clickIntent = new Intent(context, SingleTaskDialog.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent clickPI = PendingIntent.getActivity(context, appWidgetId, clickIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        widgetViews.setPendingIntentTemplate(R.id.crushr_listview, clickPI);

        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        Set<String> set = prefs.getStringSet(Constants.SHARED_PREF_LIST+appWidgetId, new HashSet<>());
        if(set.isEmpty()) {
            widgetViews.setViewVisibility(R.id.empty, View.VISIBLE);
            widgetViews.setViewVisibility(R.id.crushr_listview, View.GONE);
        } else {
            widgetViews.setViewVisibility(R.id.empty, View.GONE);
            widgetViews.setViewVisibility(R.id.crushr_listview, View.VISIBLE);
        }

        int primaryColor = prefs.getInt(Constants.SHARED_PREF_PRIMARY_COLOR + appWidgetId, ContextCompat.getColor(context, R.color.color_22));
        int secondaryColor = prefs.getInt(Constants.SHARED_PREF_SECONDARY_COLOR + appWidgetId, ContextCompat.getColor(context, R.color.color_19));
        int widgetBGColor = prefs.getInt(Constants.SHARED_PREF_WIDGETBG_COLOR + appWidgetId, ContextCompat.getColor(context, android.R.color.transparent));

        widgetViews.setInt(R.id.title, "setBackgroundColor", primaryColor);
        widgetViews.setInt(R.id.add_crushr_button_bg, "setColorFilter", secondaryColor);
        widgetViews.setInt(R.id.widget, "setBackgroundColor", widgetBGColor);

        if(primaryColor == 0) {
            widgetViews.setViewVisibility(R.id.header_shadow, View.GONE);
            widgetViews.setViewVisibility(R.id.logo, View.GONE);
        } else {
            widgetViews.setViewVisibility(R.id.header_shadow, View.VISIBLE);
            widgetViews.setViewVisibility(R.id.logo, View.VISIBLE);
        }
        if(prefs.getInt(Constants.SHARED_PREF_BG_COLOR + appWidgetId, ContextCompat.getColor(context, R.color.color_20)) == 0) {
            widgetViews.setViewVisibility(R.id.task_shadow, View.GONE);
        } else {
            widgetViews.setViewVisibility(R.id.task_shadow, View.VISIBLE);
        }

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.crushr_listview);
        appWidgetManager.updateAppWidget(appWidgetId, widgetViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null) {
            if(Intent.ACTION_BOOT_COMPLETED.equalsIgnoreCase(intent.getAction())) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName name = new ComponentName(context, CrushrProvider.class);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(name);
                onUpdate(context, appWidgetManager, appWidgetIds);
            }
        }
        super.onReceive(context, intent);
    }
}
