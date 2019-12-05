package school.of.thought;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import school.of.thought.activity.MainActivity;
import school.of.thought.utils.Utils;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(Utils.THEME, MODE_PRIVATE);
        int mTheme = preferences.getInt(Utils.CURRENT_THEME, R.style.AppTheme_Light);
        setTheme(mTheme);

        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }, 300);
    }
}
