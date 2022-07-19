package rasel.neo.crushr;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;
    private final int appWidgetId;
    private final List<String> itemList = new ArrayList<>();

    protected RemoteViewFactory(Context ctx, Intent intent) {
        context = ctx;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        onDataSetChanged();
    }

    @Override
    public void onDataSetChanged() {
        itemList.clear();
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        Set<String> set = prefs.getStringSet(Constants.SHARED_PREF_LIST + appWidgetId, new HashSet<>());

        Object[] list = set.toArray();
        for(Object item : list) {
            itemList.add(item.toString());
        }
    }

    @Override
    public void onDestroy() {
        itemList.clear();
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews itemViews = new RemoteViews(context.getPackageName(), R.layout.crushr_item);

        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        int textColor = prefs.getInt(Constants.SHARED_PREF_TEXT_COLOR + appWidgetId, ContextCompat.getColor(context, R.color.color_6));
        int BGColor = prefs.getInt(Constants.SHARED_PREF_BG_COLOR + appWidgetId, ContextCompat.getColor(context, R.color.color_20));
        float fontSize = prefs.getFloat(Constants.SHARED_PREF_FONT_SIZE + (float) appWidgetId, 14);
        int checkedStyle = prefs.getInt(Constants.SHARED_PREF_FONT_STYLE + appWidgetId, Typeface.NORMAL);

        SpannableString spannableString = new SpannableString(itemList.get(position));
        if(checkedStyle == Typeface.NORMAL) {
            spannableString.setSpan(new StyleSpan(Typeface.NORMAL), 0, spannableString.length(), 0);
        } else if(checkedStyle == Typeface.BOLD) {
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, spannableString.length(), 0);
        } else if(checkedStyle == Typeface.ITALIC) {
            spannableString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spannableString.length(), 0);
        } else if(checkedStyle == Typeface.BOLD_ITALIC) {
            spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, spannableString.length(), 0);
        }
        itemViews.setTextViewText(R.id.todo, spannableString);

        itemViews.setTextColor(R.id.todo, textColor);
        itemViews.setInt(R.id.bullet, "setColorFilter", textColor);
        itemViews.setInt(R.id.container, "setBackgroundColor", BGColor);
        itemViews.setTextViewTextSize(R.id.todo, COMPLEX_UNIT_SP, fontSize);

        Intent i = new Intent();
        Bundle extras = new Bundle();
        extras.putString(Constants.EXTRA_WORD, itemList.get(position));
        extras.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        i.putExtras(extras);
        itemViews.setOnClickFillInIntent(R.id.container, i);

        return itemViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
