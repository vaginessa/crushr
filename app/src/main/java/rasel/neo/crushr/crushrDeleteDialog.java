package rasel.neo.crushr;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by cymak on 9/30/14.
 */
public class crushrDeleteDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.crushr_delete_dialog);

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.80);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.40);
        getWindow().setLayout(width, height);

        final String task = getIntent().getExtras().getString(crushrProvider.EXTRA_WORD);
        final int appWidgetId = getIntent().getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);

        TextView message = (TextView) findViewById(R.id.message);
        message.setMovementMethod(new ScrollingMovementMethod());
        message.setText(task);

        findViewById(R.id.copy_btn).setOnClickListener(v -> {
            ClipboardManager clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            String copyTask = message.getText().toString();
            ClipData clipData = ClipData.newPlainText("copyTask", copyTask);
            clipBoard.setPrimaryClip(clipData);

            Toast.makeText(getApplicationContext(), getString(R.string.text_copied_toast), Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.input_cancel).setOnClickListener(view -> finish());

        findViewById(R.id.input_ok).setOnClickListener(view -> {
            PrefUtils.removeItem(getApplicationContext(), task, appWidgetId);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getApplicationContext(), crushrProvider.class));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.crushr_listview);
            crushrProvider.updateAppWidget(getApplicationContext(), appWidgetManager, appWidgetId);

            finish();
        });
    }
}
