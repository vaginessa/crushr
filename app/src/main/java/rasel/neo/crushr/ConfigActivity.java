package rasel.neo.crushr;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;

import rasel.neo.crushr.dialogs.BGColorDialog;
import rasel.neo.crushr.dialogs.FontSizeDialog;
import rasel.neo.crushr.dialogs.FontStyleDialog;
import rasel.neo.crushr.dialogs.PrimaryColorDialog;
import rasel.neo.crushr.dialogs.SecondaryColorDialog;
import rasel.neo.crushr.dialogs.TextColorDialog;
import rasel.neo.crushr.dialogs.WidgetBGColorDialog;
import rasel.neo.crushr.utils.BaseUtils;

public class ConfigActivity extends AppCompatActivity {

    private AppCompatTextView chooseEnterKeyAction;
    private int appWidgetId;
    private boolean shouldExecuteOnResume;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.crushr_config);
        appWidgetId = getIntent().getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
        shouldExecuteOnResume = false;

        chooseEnterKeyAction = (AppCompatTextView) findViewById(R.id.choose_enterKey_action);

        // load previews
        loadPreviews();

        findViewById(R.id.github_btn).setOnClickListener(v -> {
            Intent openLink = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.github_url)));
            startActivity(openLink);
        });

        findViewById(R.id.primary_color_dialog).setOnClickListener(v -> {
            Intent primaryIntent = new Intent(getApplicationContext(), PrimaryColorDialog.class);
            primaryIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            startActivity(primaryIntent);
        });

        findViewById(R.id.secondary_color_dialog).setOnClickListener(v -> {
            Intent secondaryIntent = new Intent(getApplicationContext(), SecondaryColorDialog.class);
            secondaryIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            startActivity(secondaryIntent);
        });

        findViewById(R.id.widgetBG_color_dialog).setOnClickListener(v -> {
            Intent widgetBGIntent = new Intent(getApplicationContext(), WidgetBGColorDialog.class);
            widgetBGIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            startActivity(widgetBGIntent);
        });

        findViewById(R.id.text_color_dialog).setOnClickListener(v -> {
            Intent textColorIntent = new Intent(getApplicationContext(), TextColorDialog.class);
            textColorIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            startActivity(textColorIntent);
        });

        findViewById(R.id.bg_color_dialog).setOnClickListener(v -> {
            Intent bgColorIntent = new Intent(getApplicationContext(), BGColorDialog.class);
            bgColorIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            startActivity(bgColorIntent);
        });

        findViewById(R.id.font_size_dialog).setOnClickListener(v -> {
            Intent fontSizeIntent = new Intent(getApplicationContext(), FontSizeDialog.class);
            fontSizeIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            startActivity(fontSizeIntent);
        });

        findViewById(R.id.font_style_dialog).setOnClickListener(v -> {
            Intent fontStyleIntent = new Intent(getApplicationContext(), FontStyleDialog.class);
            fontStyleIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            startActivity(fontStyleIntent);
        });

        chooseEnterKeyAction.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(ConfigActivity.this, chooseEnterKeyAction);
            popupMenu.getMenuInflater().inflate(R.menu.enter_actions, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                chooseEnterKeyAction.setText(menuItem.getTitle());
                switch(menuItem.getItemId()) {
                    case R.id.item_add:
                        BaseUtils.setEnterKeyAction(getApplicationContext(), 0);
                        break;
                    case R.id.item_newLine:
                        BaseUtils.setEnterKeyAction(getApplicationContext(), 1);
                        break;
                } return true;
            });
            popupMenu.show();
        });

        findViewById(R.id.btn_reset).setOnClickListener(v -> {
            BaseUtils.setPrimaryColor(getApplicationContext(), ContextCompat.getColor(getApplicationContext(), R.color.color_22), appWidgetId);
            BaseUtils.setSecondaryColor(getApplicationContext(), ContextCompat.getColor(getApplicationContext(), R.color.color_19), appWidgetId);
            BaseUtils.setWidgetBGColor(getApplicationContext(), ContextCompat.getColor(getApplicationContext(), android.R.color.transparent), appWidgetId);
            BaseUtils.setTextColor(getApplicationContext(), ContextCompat.getColor(getApplicationContext(), R.color.color_6), appWidgetId);
            BaseUtils.setBGColor(getApplicationContext(), ContextCompat.getColor(getApplicationContext(), R.color.color_20), appWidgetId);
            BaseUtils.setFontSize(getApplicationContext(), 14, appWidgetId);
            BaseUtils.setFontStyle(getApplicationContext(), Typeface.NORMAL, appWidgetId);
            BaseUtils.setEnterKeyAction(getApplicationContext(), 0);
            loadPreviews();
        });

        findViewById(R.id.configs_apply).setOnClickListener(v -> {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
            CrushrProvider.updateAppWidget(getApplicationContext(), appWidgetManager, appWidgetId);

            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        });
    }

    private void loadPreviews() {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE);
        int primaryColor = prefs.getInt(Constants.SHARED_PREF_PRIMARY_COLOR + appWidgetId, ContextCompat.getColor(getApplicationContext(), R.color.color_22));
        int secondaryColor = prefs.getInt(Constants.SHARED_PREF_SECONDARY_COLOR + appWidgetId, ContextCompat.getColor(getApplicationContext(), R.color.color_19));
        int widgetBGColor = prefs.getInt(Constants.SHARED_PREF_WIDGETBG_COLOR + appWidgetId, ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));
        int textColor = prefs.getInt(Constants.SHARED_PREF_TEXT_COLOR + appWidgetId, ContextCompat.getColor(getApplicationContext(), R.color.color_6));
        int BGColor = prefs.getInt(Constants.SHARED_PREF_BG_COLOR + appWidgetId, ContextCompat.getColor(getApplicationContext(), R.color.color_20));
        float fontSize = prefs.getFloat(Constants.SHARED_PREF_FONT_SIZE + (float) appWidgetId, 14);
        int checkedStyle = prefs.getInt(Constants.SHARED_PREF_FONT_STYLE + appWidgetId, Typeface.NORMAL);
        int enterKeyActon = prefs.getInt(Constants.SHARED_PREF_ENTER_ACTION, 0);

        GradientDrawable borderPrimary = new GradientDrawable();
        borderPrimary.setShape(GradientDrawable.OVAL);
        borderPrimary.setColor(primaryColor);
        borderPrimary.setStroke(1, 0xFF111111);
        (findViewById(R.id.primary_color_preview)).setBackground(borderPrimary);

        GradientDrawable borderSecondary = new GradientDrawable();
        borderSecondary.setShape(GradientDrawable.OVAL);
        borderSecondary.setColor(secondaryColor);
        borderSecondary.setStroke(1, 0xFF111111);
        (findViewById(R.id.secondary_color_preview)).setBackground(borderSecondary);

        GradientDrawable borderWidgetBG = new GradientDrawable();
        borderWidgetBG.setShape(GradientDrawable.OVAL);
        borderWidgetBG.setColor(widgetBGColor);
        borderWidgetBG.setStroke(1, 0xFF111111);
        (findViewById(R.id.widgetBG_color_preview)).setBackground(borderWidgetBG);

        GradientDrawable borderTextColor = new GradientDrawable();
        borderTextColor.setShape(GradientDrawable.OVAL);
        borderTextColor.setColor(textColor);
        borderTextColor.setStroke(1, 0xFF111111);
        (findViewById(R.id.text_color_preview)).setBackground(borderTextColor);

        GradientDrawable borderBG = new GradientDrawable();
        borderBG.setShape(GradientDrawable.OVAL);
        borderBG.setColor(BGColor);
        borderBG.setStroke(1, 0xFF111111);
        (findViewById(R.id.bg_color_preview)).setBackground(borderBG);

        ((TextView) findViewById(R.id.font_size_preview)).setText(String.valueOf(fontSize));

        TextView fontStylePreview = findViewById(R.id.font_style_preview);
        if(checkedStyle == Typeface.NORMAL) {
            fontStylePreview.setText(R.string.fontStyle_normal);
            fontStylePreview.setTypeface(null, Typeface.NORMAL);
        } else if(checkedStyle == Typeface.BOLD) {
            fontStylePreview.setText(R.string.fontStyle_bold);
            fontStylePreview.setTypeface(null, Typeface.BOLD);
        } else if(checkedStyle == Typeface.ITALIC) {
            fontStylePreview.setText(R.string.fontStyle_italic);
            fontStylePreview.setTypeface(null, Typeface.ITALIC);
        } else if(checkedStyle == Typeface.BOLD_ITALIC) {
            fontStylePreview.setText(R.string.fontStyle_boldItalic);
            fontStylePreview.setTypeface(null, Typeface.BOLD_ITALIC);
        }

        if(enterKeyActon == 0) {
            chooseEnterKeyAction.setText(R.string.add);
        } else if(enterKeyActon == 1) {
            chooseEnterKeyAction.setText(R.string.new_line);
        }
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
