package rasel.neo.crushr.dialogs;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import java.util.Objects;

import rasel.neo.crushr.Constants;
import rasel.neo.crushr.R;
import rasel.neo.crushr.utils.BaseUtils;

public class FontSizeDialog extends AppCompatActivity {

    private int appWidgetId;
    private SharedPreferences prefs;
    private float fontSize;
    private AppCompatTextView preview;
    private AppCompatEditText fontSizeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.font_size_dialog);

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.80);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.30);
        getWindow().setLayout(width, height);

        appWidgetId = getIntent().getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
        prefs = getApplicationContext().getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        fontSizeInput = findViewById(R.id.fontSize_input);
        preview = findViewById(R.id.preview);
        fontSize = prefs.getFloat(Constants.SHARED_PREF_FONT_SIZE + (float) appWidgetId, 14);

        // initially load all previews as soon as onCreate method is called
        loadInitPreviews();

        fontSizeInput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String fontSizeStr = Objects.requireNonNull(fontSizeInput.getText()).toString().trim();
                if(!fontSizeStr.isEmpty()) {
                    preview.setTextSize(Float.parseFloat(fontSizeStr));
                }
            }
        });

        findViewById(R.id.input_cancel).setOnClickListener(v -> finish());

        findViewById(R.id.input_ok).setOnClickListener(v -> {
            String fontSizeStr = Objects.requireNonNull(fontSizeInput.getText()).toString().trim();
            if(!fontSizeStr.isEmpty()) {
                fontSize = Float.parseFloat(fontSizeStr);
            }

            BaseUtils.setFontSize(getApplicationContext(), fontSize, (float) appWidgetId);
            finish();
        });
    }

    private void loadInitPreviews() {
        int textColor = prefs.getInt(Constants.SHARED_PREF_TEXT_COLOR + appWidgetId, ContextCompat.getColor(getApplicationContext(), R.color.color_6));
        int BGColor = prefs.getInt(Constants.SHARED_PREF_BG_COLOR + appWidgetId, ContextCompat.getColor(getApplicationContext(), R.color.color_20));

        preview.setTextColor(textColor);
        preview.setBackgroundColor(BGColor);
        preview.setTextSize(fontSize);
        fontSizeInput.setText(String.valueOf(fontSize));
    }
}
