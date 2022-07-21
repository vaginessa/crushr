package rasel.neo.crushr.utils;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.service.notification.StatusBarNotification;

import java.util.Objects;

import rasel.neo.crushr.CrushrProvider;
import rasel.neo.crushr.R;

public class ExtraUtils {

    // changes int type color codes to argb string type
    protected static String intColorToArgbString(int color) {
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

    // checks if notification exists or not
    public static boolean notificationExist(Context ctx, String TAG) {
        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);
        StatusBarNotification[] notifications = notificationManager.getActiveNotifications();
        for (StatusBarNotification notification : notifications) {
            if(Objects.equals(notification.getTag(), TAG)) {
                return true;
            }
        } return false;
    }

    // cancels notification
    public static void cancelNotification(Context ctx, String tag, int id) {
        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(tag, id);
    }

    // checks if a specific package exists or not
    public static boolean packageExist(Context ctx, String packageName) {
        try {
            ApplicationInfo info = ctx.getPackageManager().getApplicationInfo(
                    packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return null != info;
        } catch (Exception e) {
            return false;
        }
    }

    // reloads the main listview
    public static void refreshListView(Context ctx, int id) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ctx);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(ctx, CrushrProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.crushr_listview);
        CrushrProvider.updateAppWidget(ctx, appWidgetManager, id);
    }
}
