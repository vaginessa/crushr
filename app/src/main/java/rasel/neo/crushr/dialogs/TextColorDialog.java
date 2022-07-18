package rasel.neo.crushr.dialogs;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import rasel.neo.crushr.R;
import rasel.neo.crushr.utils.ColorUtils;

public class TextColorDialog extends AppCompatActivity {

    private final ColorUtils colorUtils = new ColorUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.color_dialog);
        ((TextView) findViewById(R.id.color_dialog_title)).setText(R.string.text_color);

        colorUtils.colorDialogInit(this, getIntent(), getApplicationContext());
    }

    public void onRadioButtonChecked(View view) {
        colorUtils.onColorButtonClicked(view, this, getApplicationContext());
    }
}
