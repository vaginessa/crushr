package rasel.neo.crushr.dialogs;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import rasel.neo.crushr.Constants;
import rasel.neo.crushr.R;
import rasel.neo.crushr.utils.BaseUtils;

public class FontStyleDialog extends AppCompatActivity {

    private int appWidgetId;
    private AppCompatRadioButton normal, bold, italic, boldItalic;
    private int checkedStyle;
    private SharedPreferences prefs;
    private AppCompatTextView preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.font_style_dialog);

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.80);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.40);
        getWindow().setLayout(width, height);

        appWidgetId = getIntent().getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
        prefs = getApplicationContext().getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        checkedStyle = prefs.getInt(Constants.SHARED_PREF_FONT_STYLE + appWidgetId, Typeface.NORMAL);
        preview = findViewById(R.id.preview);
        normal = findViewById(R.id.normal);
        bold = findViewById(R.id.bold);
        italic = findViewById(R.id.italic);
        boldItalic = findViewById(R.id.bold_italic);

        // initially load all previews as soon as onCreate method is called
        loadInitPreviews();

        findViewById(R.id.input_cancel).setOnClickListener(v -> finish());

        findViewById(R.id.input_ok).setOnClickListener(v -> {
            BaseUtils.setFontStyle(getApplicationContext(), checkedStyle, appWidgetId);
            finish();
        });
    }

    private void loadInitPreviews() {
        if(checkedStyle == Typeface.NORMAL) {
            normal.setChecked(true);
            preview.setTypeface(null, Typeface.NORMAL);
        } else if(checkedStyle == Typeface.BOLD) {
            bold.setChecked(true);
            preview.setTypeface(null, Typeface.BOLD);
        } else if(checkedStyle == Typeface.ITALIC) {
            italic.setChecked(true);
            preview.setTypeface(null, Typeface.ITALIC);
        } else if(checkedStyle == Typeface.BOLD_ITALIC) {
            boldItalic.setChecked(true);
            preview.setTypeface(null, Typeface.BOLD_ITALIC);
        }

        int textColor = prefs.getInt(Constants.SHARED_PREF_TEXT_COLOR + appWidgetId, ContextCompat.getColor(getApplicationContext(), R.color.color_6));
        int BGColor = prefs.getInt(Constants.SHARED_PREF_BG_COLOR + appWidgetId, ContextCompat.getColor(getApplicationContext(), R.color.color_20));
        float fontSize = prefs.getFloat(Constants.SHARED_PREF_FONT_SIZE + (float) appWidgetId, 14);

        preview.setTextColor(textColor);
        preview.setBackgroundColor(BGColor);
        preview.setTextSize(fontSize);
    }

    @SuppressLint("NonConstantResourceId")
    public void onFontStyleChecked(View view) {
        switch(view.getId()) {
            case R.id.normal:
                bold.setChecked(false);
                italic.setChecked(false);
                boldItalic.setChecked(false);
                checkedStyle = Typeface.NORMAL;
                preview.setTypeface(null, Typeface.NORMAL);
                break;
            case R.id.bold:
                normal.setChecked(false);
                italic.setChecked(false);
                boldItalic.setChecked(false);
                checkedStyle = Typeface.BOLD;
                preview.setTypeface(null, Typeface.BOLD);
                break;
            case R.id.italic:
                normal.setChecked(false);
                bold.setChecked(false);
                boldItalic.setChecked(false);
                checkedStyle = Typeface.ITALIC;
                preview.setTypeface(null, Typeface.ITALIC);
                break;
            case R.id.bold_italic:
                normal.setChecked(false);
                bold.setChecked(false);
                italic.setChecked(false);
                checkedStyle = Typeface.BOLD_ITALIC;
                preview.setTypeface(null, Typeface.BOLD_ITALIC);
                break;
        }
    }
}
