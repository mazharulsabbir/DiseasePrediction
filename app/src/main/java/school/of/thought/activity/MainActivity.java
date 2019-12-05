package school.of.thought.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import school.of.thought.R;
import school.of.thought.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private int mTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(Utils.THEME, MODE_PRIVATE);
        mTheme = preferences.getInt(Utils.CURRENT_THEME, R.style.AppTheme_Light);
        setTheme(mTheme);

        setContentView(R.layout.activity_main);
    }

    public void changeTheme(View view) {
        SharedPreferences.Editor editor = getSharedPreferences(Utils.THEME, MODE_PRIVATE).edit();

        if (mTheme == R.style.AppTheme_Dark) {
            editor.putInt(Utils.CURRENT_THEME, R.style.AppTheme_Light);
            editor.apply();
        } else {
            editor.putInt(Utils.CURRENT_THEME, R.style.AppTheme_Dark);
            editor.apply();
        }

        recreate();
    }
}
