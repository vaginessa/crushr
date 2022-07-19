package rasel.neo.crushr.utils;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import rasel.neo.crushr.Constants;

public class BaseUtils {
    @SuppressLint("MutatingSharedPrefs")
    public static void addItem(Context ctx, String item, int id) {
        SharedPreferences prefs = ctx.getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> set = prefs.getStringSet(Constants.SHARED_PREF_LIST+id, new HashSet<>());
        set.add(item);
        editor.remove(Constants.SHARED_PREF_LIST+id);
        editor.apply();
        editor.putStringSet(Constants.SHARED_PREF_LIST+id, set);
        editor.apply();
    }

    @SuppressLint("MutatingSharedPrefs")
    public static void removeItem(Context ctx, String item, int id) {
        SharedPreferences prefs = ctx.getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> set = prefs.getStringSet(Constants.SHARED_PREF_LIST+id, new HashSet<>());
        set.remove(item);
        editor.remove(Constants.SHARED_PREF_LIST+id);
        editor.apply();
        editor.putStringSet(Constants.SHARED_PREF_LIST+id, set);
        editor.apply();
        if(ExtraUtils.notificationExist(ctx)) {
            NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel(Constants.NOTIFY_ID);
        }
    }

    public static void setPrimaryColor(Context ctx, int color, int id) {
        SharedPreferences prefs = ctx.getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(Constants.SHARED_PREF_PRIMARY_COLOR + id, color);
        editor.apply();
    }

    public static void setSecondaryColor(Context ctx, int color, int id) {
        SharedPreferences prefs = ctx.getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(Constants.SHARED_PREF_SECONDARY_COLOR + id, color);
        editor.apply();
    }

    public static void setWidgetBGColor(Context ctx, int color, int id) {
        SharedPreferences prefs = ctx.getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(Constants.SHARED_PREF_WIDGETBG_COLOR + id, color);
        editor.apply();
    }

    public static void setTextColor(Context ctx, int color, int id) {
        SharedPreferences prefs = ctx.getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(Constants.SHARED_PREF_TEXT_COLOR + id, color);
        editor.apply();
    }

    public static void setBGColor(Context ctx, int color, int id) {
        SharedPreferences prefs = ctx.getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(Constants.SHARED_PREF_BG_COLOR + id, color);
        editor.apply();
    }

    public static void setFontSize(Context ctx, float size, float id) {
        SharedPreferences prefs = ctx.getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(Constants.SHARED_PREF_FONT_SIZE + id, size);
        editor.apply();
    }

    public static void setFontStyle(Context ctx, int style, int id) {
        SharedPreferences prefs = ctx.getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(Constants.SHARED_PREF_FONT_STYLE + id, style);
        editor.apply();
    }
}
