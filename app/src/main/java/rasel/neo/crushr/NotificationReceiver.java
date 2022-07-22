package rasel.neo.crushr;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import rasel.neo.crushr.dialogs.NewTaskDialog;
import rasel.neo.crushr.dialogs.SingleTaskDialog;
import rasel.neo.crushr.utils.BaseUtils;
import rasel.neo.crushr.utils.ExtraUtils;

public class NotificationReceiver extends BroadcastReceiver {

    private static NotificationManager notifyManager;

    public static void createNotification(Context ctx, String text, int appWidgetId) {

        Intent openIntent = new Intent(ctx, SingleTaskDialog.class);
        openIntent.putExtra("rasel.neo.crushr.OPEN", true);
        openIntent.putExtra("text", text);
        openIntent.putExtra("id", appWidgetId);
        openIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        openIntent.setAction(Long.toString(System.currentTimeMillis()));
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent openPending =
                PendingIntent.getActivity(ctx, 0, openIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent addIntent = new Intent(ctx, NewTaskDialog.class);
        addIntent.putExtra("rasel.neo.crushr.ADD", true);
        addIntent.putExtra("id", appWidgetId);
        addIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        addIntent.setAction(Long.toString(System.currentTimeMillis()));
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent addPending =
                PendingIntent.getActivity(ctx, 1, addIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent removeIntent = new Intent(ctx, NotificationReceiver.class);
        removeIntent.putExtra("rasel.neo.crushr.REMOVE", true);
        removeIntent.putExtra("text", text);
        removeIntent.putExtra("id", appWidgetId);
        removeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        removeIntent.setAction(Long.toString(System.currentTimeMillis()));
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent removePending =
                PendingIntent.getBroadcast(ctx, 2, removeIntent, PendingIntent.FLAG_ONE_SHOT);

        Intent unpinIntent = new Intent(ctx, NotificationReceiver.class);
        unpinIntent.putExtra("rasel.neo.crushr.UNPIN", true);
        unpinIntent.putExtra("text", text);
        unpinIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        unpinIntent.setAction(Long.toString(System.currentTimeMillis()));
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent unpinPending =
                PendingIntent.getBroadcast(ctx, 3, unpinIntent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = "rasel.neo.crushr.pinned_task";
        String channelTitle = ctx.getString(R.string.pinned_task);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, channelId);
        int importance;

        String contentTitle, contentText;
        if(text.length() < 50) {
            contentTitle = text;
            contentText = null;
        } else {
            contentTitle = null;
            contentText = text;
        }

        if (notifyManager == null) {
            notifyManager = (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = notifyManager.getNotificationChannel(channelId);
            if (notificationChannel == null) {
                notificationChannel = new NotificationChannel(channelId, channelTitle, importance);
                notifyManager.createNotificationChannel(notificationChannel);
            }
        } else {
            importance = Notification.PRIORITY_DEFAULT;
        }

        builder.setSmallIcon(R.drawable.icon_notify)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setPriority(importance)
                .setShowWhen(false)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(contentText))
                .setContentIntent(openPending)
                .addAction(R.drawable.icon_add, ctx.getString(R.string.add), addPending)
                .addAction(R.drawable.icon_remove, ctx.getString(R.string.remove), removePending)
                .addAction(R.drawable.icon_unpin, ctx.getString(R.string.unpin), unpinPending);

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        notifyManager.notify(text, Constants.NOTIFY_ID, notification);
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.hasExtra("rasel.neo.crushr.UNPIN")) {
            String TAG = intent.getExtras().getString("text");
            ExtraUtils.cancelNotification(context, TAG, Constants.NOTIFY_ID);
        } else if(intent.hasExtra("rasel.neo.crushr.REMOVE")) {
            String text = intent.getExtras().getString("text");
            int id = intent.getExtras().getInt("id");
            BaseUtils.removeItem(context, text, id);
            ExtraUtils.refreshListView(context, id);
        }
    }
}
