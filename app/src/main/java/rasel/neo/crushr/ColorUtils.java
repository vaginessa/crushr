package rasel.neo.crushr;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Arrays;

public class ColorUtils {

    private int appWidgetId;
    private int color;
    private RadioGroup firstColorGroup, secondColorGroup, thirdColorGroup, fourthColorGroup;
    private RadioButton rb;
    private EditText colorInputBox;

    private String color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11, color12,
            color13, color14, color15, color16, color17, color18, color19, color20, color21, color22, color23, color24;

    @SuppressLint("ResourceType")
    protected void colorDialogInit(AppCompatActivity appCompatActivity, Intent intent, Context context) {

        int width = (int) (appCompatActivity.getResources().getDisplayMetrics().widthPixels * 0.80);
        int height = (int) (appCompatActivity.getResources().getDisplayMetrics().heightPixels * 0.50);
        appCompatActivity.getWindow().setLayout(width, height);

        appWidgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
        firstColorGroup = appCompatActivity.findViewById(R.id.first_row);
        secondColorGroup = appCompatActivity.findViewById(R.id.second_row);
        thirdColorGroup = appCompatActivity.findViewById(R.id.third_row);
        fourthColorGroup = appCompatActivity.findViewById(R.id.fourth_row);
        colorInputBox = appCompatActivity.findViewById(R.id.color_input);

        color1 = appCompatActivity.getString(R.color.color_1);
        color2 = appCompatActivity.getString(R.color.color_2);
        color3 = appCompatActivity.getString(R.color.color_3);
        color4 = appCompatActivity.getString(R.color.color_4);
        color5 = appCompatActivity.getString(R.color.color_5);
        color6 = appCompatActivity.getString(R.color.color_6);
        color7 = appCompatActivity.getString(R.color.color_7);
        color8 = appCompatActivity.getString(R.color.color_8);
        color9 = appCompatActivity.getString(R.color.color_9);
        color10 = appCompatActivity.getString(R.color.color_10);
        color11 = appCompatActivity.getString(R.color.color_11);
        color12 = appCompatActivity.getString(R.color.color_12);
        color13 = appCompatActivity.getString(R.color.color_13);
        color14 = appCompatActivity.getString(R.color.color_14);
        color15 = appCompatActivity.getString(R.color.color_15);
        color16 = appCompatActivity.getString(R.color.color_16);
        color17 = appCompatActivity.getString(R.color.color_17);
        color18 = appCompatActivity.getString(R.color.color_18);
        color19 = appCompatActivity.getString(R.color.color_19);
        color20 = appCompatActivity.getString(R.color.color_20);
        color21 = appCompatActivity.getString(R.color.color_21);
        color22 = appCompatActivity.getString(R.color.color_22);
        color23 = appCompatActivity.getString(R.color.color_23);
        color24 = appCompatActivity.getString(R.color.color_24);

        SharedPreferences prefs = context.getSharedPreferences(CrushrProvider.SHARED_PREF_TAG, Context.MODE_PRIVATE);

        if(appCompatActivity.getClass().getName().equals("rasel.neo.crushr.PrimaryColorDialog")) {
            color = prefs.getInt(CrushrProvider.SHARED_PREF_PRIMARY_COLOR + appWidgetId, ContextCompat.getColor(context, R.color.color_22));
        } else if(appCompatActivity.getClass().getName().equals("rasel.neo.crushr.SecondaryColorDialog")) {
            color = prefs.getInt(CrushrProvider.SHARED_PREF_SECONDARY_COLOR + appWidgetId, ContextCompat.getColor(context, R.color.color_19));
        }

        loadPreview(appCompatActivity, context, color);
        listenColorGroupCheck(appCompatActivity);
        listenColorInputChange(appCompatActivity, context);
        listenButtonClicks(appCompatActivity, context);
    }

    private void loadPreview(AppCompatActivity appCompatActivity, Context context, int color) {
        String convertedArgb = ExtraUtils.intColorToArgbString(color);
        colorInputBox.setText(convertedArgb);
        (appCompatActivity.findViewById(R.id.color_preview)).setBackgroundColor(color);

        if (Arrays.asList(color1, color2, color3, color4, color5, color6, color7, color8,
                        color9, color10, color11, color12, color13, color14, color15, color16,
                        color17, color18, color19, color20, color21, color22, color23, color24)
                .contains(convertedArgb)) {
            loadColorSelections(appCompatActivity, context, color);
        } else {
            firstColorGroup.clearCheck();
            secondColorGroup.clearCheck();
            thirdColorGroup.clearCheck();
            fourthColorGroup.clearCheck();
        }
    }

    private void listenColorGroupCheck(AppCompatActivity appCompatActivity) {
        firstColorGroup.setOnCheckedChangeListener((group, checkedId) -> {
            rb = appCompatActivity.findViewById(checkedId);
            if (rb != null && rb.isChecked()) {
                secondColorGroup.clearCheck();
                thirdColorGroup.clearCheck();
                fourthColorGroup.clearCheck();
            }
        });
        secondColorGroup.setOnCheckedChangeListener((group, checkedId) -> {
            rb = appCompatActivity.findViewById(checkedId);
            if (rb != null && rb.isChecked()) {
                firstColorGroup.clearCheck();
                thirdColorGroup.clearCheck();
                fourthColorGroup.clearCheck();
            }
        });
        thirdColorGroup.setOnCheckedChangeListener((group, checkedId) -> {
            rb = appCompatActivity.findViewById(checkedId);
            if (rb != null && rb.isChecked()) {
                firstColorGroup.clearCheck();
                secondColorGroup.clearCheck();
                fourthColorGroup.clearCheck();
            }
        });
        fourthColorGroup.setOnCheckedChangeListener((group, checkedId) -> {
            rb = appCompatActivity.findViewById(checkedId);
            if (rb != null && rb.isChecked()) {
                firstColorGroup.clearCheck();
                secondColorGroup.clearCheck();
                thirdColorGroup.clearCheck();
            }
        });
    }

    private void listenColorInputChange(AppCompatActivity appCompatActivity, Context context) {
        colorInputBox.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String colorStr = colorInputBox.getText().toString().trim();
                View colorPreview = appCompatActivity.findViewById(R.id.color_preview);
                try{
                    if(colorStr.length() < 2) {
                        colorPreview.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
                    } else if((colorStr.length() == 7) || (colorStr.length() == 9)) {
                        colorPreview.setBackgroundColor(Color.parseColor(colorStr));
                    } else if(colorStr.equalsIgnoreCase("#fff")) {
                        colorPreview.setBackgroundColor(Color.parseColor(colorStr + "fff"));
                    } else if(colorStr.equalsIgnoreCase("#000")) {
                        colorPreview.setBackgroundColor(Color.parseColor(colorStr + "000"));
                    } else if((2 <= colorStr.length() && colorStr.length() < 7)) {
                        colorPreview.setBackgroundColor(Color.parseColor(String.format("%1$-" + 7 + "s", colorStr).replace(' ', '0')));
                    } else if(colorStr.length() == 8) {
                        colorPreview.setBackgroundColor(Color.parseColor(colorStr.substring(0, 7)));
                    } else if(colorStr.length() > 9) {
                        colorPreview.setBackgroundColor(Color.parseColor(colorStr.substring(0, 9)));
                    }
                } catch(NumberFormatException ignored) {}
            }
        });
    }

    private void listenButtonClicks(AppCompatActivity appCompatActivity, Context context) {
        appCompatActivity.findViewById(R.id.input_cancel).setOnClickListener(v -> appCompatActivity.finish());

        appCompatActivity.findViewById(R.id.input_ok).setOnClickListener(v -> {
            String colorStr = colorInputBox.getText().toString().trim();
            if(appCompatActivity.getClass().getName().equals("rasel.neo.crushr.PrimaryColorDialog")) {
                try{
                    if(colorStr.length() < 2) {
                        Toast.makeText(context, appCompatActivity.getString(R.string.no_change_toast), Toast.LENGTH_SHORT).show();
                    } else if((colorStr.length() == 7) || (colorStr.length() == 9)) {
                        BaseUtils.setPrimaryColor(context, Color.parseColor(colorStr), appWidgetId);
                    } else if(colorStr.equalsIgnoreCase("#fff")) {
                        BaseUtils.setPrimaryColor(context, Color.parseColor(colorStr + "fff"), appWidgetId);
                    } else if(colorStr.equalsIgnoreCase("#000")) {
                        BaseUtils.setPrimaryColor(context, Color.parseColor(colorStr + "000"), appWidgetId);
                    } else if((2 <= colorStr.length() && colorStr.length() < 7)) {
                        BaseUtils.setPrimaryColor(context, Color.parseColor(String.format("%1$-" + 7 + "s", colorStr).replace(' ', '0')), appWidgetId);
                    } else if(colorStr.length() == 8) {
                        BaseUtils.setPrimaryColor(context, Color.parseColor(colorStr.substring(0, 7)), appWidgetId);
                    } else if(colorStr.length() > 9) {
                        BaseUtils.setPrimaryColor(context, Color.parseColor(colorStr.substring(0, 9)), appWidgetId);
                    }
                } catch(NumberFormatException ignored) {}
            } else if(appCompatActivity.getClass().getName().equals("rasel.neo.crushr.SecondaryColorDialog")) {
                try{
                    if(colorStr.length() < 2) {
                        Toast.makeText(context, appCompatActivity.getString(R.string.no_change_toast), Toast.LENGTH_SHORT).show();
                    } else if((colorStr.length() == 7) || (colorStr.length() == 9)) {
                        BaseUtils.setSecondaryColor(context, Color.parseColor(colorStr), appWidgetId);
                    } else if(colorStr.equalsIgnoreCase("#fff")) {
                        BaseUtils.setSecondaryColor(context, Color.parseColor(colorStr + "fff"), appWidgetId);
                    } else if(colorStr.equalsIgnoreCase("#000")) {
                        BaseUtils.setSecondaryColor(context, Color.parseColor(colorStr + "000"), appWidgetId);
                    } else if((2 <= colorStr.length() && colorStr.length() < 7)) {
                        BaseUtils.setSecondaryColor(context, Color.parseColor(String.format("%1$-" + 7 + "s", colorStr).replace(' ', '0')), appWidgetId);
                    } else if(colorStr.length() == 8) {
                        BaseUtils.setSecondaryColor(context, Color.parseColor(colorStr.substring(0, 7)), appWidgetId);
                    } else if(colorStr.length() > 9) {
                        BaseUtils.setSecondaryColor(context, Color.parseColor(colorStr.substring(0, 9)), appWidgetId);
                    }
                } catch(NumberFormatException ignored) {}
            }
            appCompatActivity.finish();
        });
    }

    private void loadColorSelections(AppCompatActivity appCompatActivity, Context context, int color) {
        if (color == ContextCompat.getColor(context, R.color.color_1)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_1)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_2)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_2)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_3)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_3)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_4)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_4)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_5)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_5)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_6)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_6)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_7)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_7)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_8)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_8)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_9)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_9)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_10)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_10)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_11)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_11)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_12)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_12)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_13)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_13)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_14)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_14)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_15)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_15)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_16)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_16)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_17)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_17)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_18)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_18)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_19)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_19)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_20)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_20)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_21)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_21)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_22)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_22)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_23)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_23)).setChecked(true);
        } else if (color == ContextCompat.getColor(context, R.color.color_24)) {
            ((RadioButton) appCompatActivity.findViewById(R.id.color_24)).setChecked(true);
        }
    }

    @SuppressLint({"NonConstantResourceId", "ResourceType"})
    protected void onColorButtonClicked(View view, AppCompatActivity appCompatActivity, Context context) {
        View colorPreview = appCompatActivity.findViewById(R.id.color_preview);
        switch(view.getId()) {
            case R.id.color_1:
                colorInputBox.setText(color1);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_1));
                break;
            case R.id.color_2:
                colorInputBox.setText(color2);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_2));
                break;
            case R.id.color_3:
                colorInputBox.setText(color3);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_3));
                break;
            case R.id.color_4:
                colorInputBox.setText(color4);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_4));
                break;
            case R.id.color_5:
                colorInputBox.setText(color5);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_5));
                break;
            case R.id.color_6:
                colorInputBox.setText(color6);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_6));
                break;
            case R.id.color_7:
                colorInputBox.setText(color7);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_7));
                break;
            case R.id.color_8:
                colorInputBox.setText(color8);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_8));
                break;
            case R.id.color_9:
                colorInputBox.setText(color9);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_9));
                break;
            case R.id.color_10:
                colorInputBox.setText(color10);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_10));
                break;
            case R.id.color_11:
                colorInputBox.setText(color11);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_11));
                break;
            case R.id.color_12:
                colorInputBox.setText(color12);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_12));
                break;
            case R.id.color_13:
                colorInputBox.setText(color13);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_13));
                break;
            case R.id.color_14:
                colorInputBox.setText(color14);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_14));
                break;
            case R.id.color_15:
                colorInputBox.setText(color15);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_15));
                break;
            case R.id.color_16:
                colorInputBox.setText(color16);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_16));
                break;
            case R.id.color_17:
                colorInputBox.setText(color17);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_17));
                break;
            case R.id.color_18:
                colorInputBox.setText(color18);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_18));
                break;
            case R.id.color_19:
                colorInputBox.setText(color19);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_19));
                break;
            case R.id.color_20:
                colorInputBox.setText(color20);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_20));
                break;
            case R.id.color_21:
                colorInputBox.setText(color21);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_21));
                break;
            case R.id.color_22:
                colorInputBox.setText(color22);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_22));
                break;
            case R.id.color_23:
                colorInputBox.setText(color23);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_23));
                break;
            case R.id.color_24:
                colorInputBox.setText(color24);
                colorPreview.setBackgroundColor(ContextCompat.getColor(context, R.color.color_24));
                break;
        }
    }
}