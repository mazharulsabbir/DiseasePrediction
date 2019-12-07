package school.of.thought.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import school.of.thought.R;
import school.of.thought.adapter.DiseaseListAdapter;
import school.of.thought.model.Disease;
import school.of.thought.utils.Utils;

public class MainActivity extends AppCompatActivity implements DiseaseListAdapter.onDiseasesListener {

    private boolean isDarkTheme;
    private List<Disease> diseases = new ArrayList<>();
    private VideoView videoView;
    private Switch checkTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCurrentTheme();
        setContentView(R.layout.activity_main);

        initNavigationDrawer();

        init();
    }

    private void initNavigationDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nav = findViewById(R.id.nav_view);

        Menu menu_nav = nav.getMenu();
        MenuItem menuItem = menu_nav.findItem(R.id.app_bar_switch_theme);
        checkTheme = menuItem.getActionView().findViewById(R.id.switch_theme);

        if (isDarkTheme) checkTheme.setChecked(false);
        else checkTheme.setChecked(true);

        nav.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.app_bar_switch_theme:
                    if (checkTheme.isChecked())
                        checkTheme.setChecked(false);
                    else checkTheme.setChecked(true);

                    changeTheme();
                    return true;
            }

            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

        //add listener
        checkTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //your action
            changeTheme();
        });
    }

    private void init() {
        initDummyData();

        RecyclerView recyclerView = findViewById(R.id.disease_recycler_view);
        DiseaseListAdapter diseaseListAdapter = new DiseaseListAdapter(diseases, this, getApplicationContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setAdapter(diseaseListAdapter);

        videoView = findViewById(R.id.video);
        String uri = "https://firebasestorage.googleapis.com/v0/b/wireless-project-in-lab.appspot.com/o/Backstreet%20Boys%20-%20Show%20Me%20The%20Meaning%20Of%20Being%20Lonely_HIGH.mp4?alt=media&token=895ae878-58d4-4f8c-8cee-9b75ea03c1a4";

        videoView.setVideoURI(Uri.parse(uri));

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();

        videoView.setOnPreparedListener(mediaPlayer -> {
            Logger.getLogger("Video Prepared").warning("Prepared");

            ProgressBar progressBar = findViewById(R.id.loading_video);
            progressBar.setVisibility(View.GONE);
        });
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

    public void changeTheme() {
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

    @Override
    public void onDiseasesClick(int position) {
        Intent intent = new Intent(this, LoginRegistrationHolder.class);
        startActivity(intent);
    }
}
