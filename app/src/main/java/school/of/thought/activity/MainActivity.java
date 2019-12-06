package school.of.thought.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import school.of.thought.R;
import school.of.thought.adapter.DiseaseListAdapter;
import school.of.thought.model.Disease;
import school.of.thought.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private boolean isDarkTheme;
    private List<Disease> diseases = new ArrayList<>();
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCurrentTheme();
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        initDummyData();

        RecyclerView recyclerView = findViewById(R.id.disease_recycler_view);
        DiseaseListAdapter diseaseListAdapter = new DiseaseListAdapter(diseases, getApplicationContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setAdapter(diseaseListAdapter);

//        videoView = findViewById(R.id.video);
//        String uri = "https://www.youtube.com/watch?v=8Lq3HyBCuAA&feature=youtu.be";
//
//        videoView.setVideoURI(Uri.parse(uri));
//
//        MediaController mediaController = new MediaController(this);
//        videoView.setMediaController(mediaController);
//        mediaController.setAnchorView(videoView);

    }

    private void initDummyData() {
        String desc = "Dengue fever is a disease caused by a family of viruses transmitted by Aedes mosquitoes. Symptoms of dengue fever include severe joint and muscle pain, swollen lymph nodes, headache, fever, exhaustion, and rash";

        for (int i = 0; i < 40; i++) {
            diseases.add(new Disease("", "Dengue", desc));
        }
    }

    private void initCurrentTheme() {
        SharedPreferences preferences = getSharedPreferences(Utils.THEME, MODE_PRIVATE);
        isDarkTheme = preferences.getBoolean(Utils.CURRENT_THEME, false);

        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    public void changeTheme(View view) {
        SharedPreferences.Editor editor = getSharedPreferences(Utils.THEME, MODE_PRIVATE).edit();

        if (isDarkTheme) {
            editor.putBoolean(Utils.CURRENT_THEME, false);
            editor.apply();
        } else {
            editor.putBoolean(Utils.CURRENT_THEME, true);
            editor.apply();
        }

        recreate();
    }
}
