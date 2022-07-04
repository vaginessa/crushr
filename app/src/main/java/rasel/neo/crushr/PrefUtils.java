package rasel.neo.crushr;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cymak on 10/6/14.
 */
public class PrefUtils {
    @SuppressLint("MutatingSharedPrefs")
    public static void addItem(Context ctx, String item, int id) {
        SharedPreferences prefs = ctx.getSharedPreferences(crushrProvider.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> set = prefs.getStringSet(crushrProvider.SHARED_PREF_LIST+id, new HashSet<>());
        set.add(item);
        editor.remove(crushrProvider.SHARED_PREF_LIST+id);
        editor.apply();
        editor.putStringSet(crushrProvider.SHARED_PREF_LIST+id, set);
        editor.apply();
    }

    @SuppressLint("MutatingSharedPrefs")
    public static void removeItem(Context ctx, String item, int id) {
        SharedPreferences prefs = ctx.getSharedPreferences(crushrProvider.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> set = prefs.getStringSet(crushrProvider.SHARED_PREF_LIST+id, new HashSet<>());
        set.remove(item);
        editor.remove(crushrProvider.SHARED_PREF_LIST+id);
        editor.apply();
        editor.putStringSet(crushrProvider.SHARED_PREF_LIST+id, set);
        editor.apply();
    }

    public static void setPrimaryColor(Context ctx, int color, int id) {
        SharedPreferences prefs = ctx.getSharedPreferences(crushrProvider.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(crushrProvider.SHARED_PREF_PRIMARY_COLOR + id, color);
        editor.apply();
    }

    public static void setSecondaryColor(Context ctx, int color, int id) {
        SharedPreferences prefs = ctx.getSharedPreferences(crushrProvider.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(crushrProvider.SHARED_PREF_SECONDARY_COLOR + id, color);
        editor.apply();
    }

    public static String intColorToArgbString(int color) {
        String alpha = Integer.toHexString(Color.alpha(color));
        String red = Integer.toHexString(Color.red(color));
        String green = Integer.toHexString(Color.green(color));
        String blue = Integer.toHexString(Color.blue(color));
        if (alpha.length() == 1)
            alpha = "0" + alpha;
        if (red.length() == 1)
            red = "0" + red;
        if (green.length() == 1)
            green = "0" + green;
        if (blue.length() == 1)
            blue = "0" + blue;
        return "#" + alpha + red + green + blue;
    }
}
