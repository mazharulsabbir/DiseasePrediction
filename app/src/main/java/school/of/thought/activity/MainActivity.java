package school.of.thought.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import school.of.thought.R;
import school.of.thought.adapter.DiseaseListAdapter;
import school.of.thought.model.Disease;
import school.of.thought.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private boolean isDarkTheme;
    private List<Disease> diseases = new ArrayList<>();
    private VideoView videoView;
    private Switch checkTheme;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCurrentTheme();
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        initNavigationDrawer();

        init();

        findViewById(R.id.donate).setOnClickListener(view -> {
            if (user != null) {
                //todo: open donation activity for donate us
            } else {
                Intent intent = new Intent(this, LoginRegistrationHolder.class);
                startActivity(intent);
            }
        });
    }

    private void initNavigationDrawer() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nav = findViewById(R.id.nav_view);

        View navHeaderView = nav.getHeaderView(0);

        ImageView avatar = navHeaderView.findViewById(R.id.nav_header_avatar);
        TextView name = navHeaderView.findViewById(R.id.nav_header_name);
        TextView mobile = navHeaderView.findViewById(R.id.nav_header_mobile);

        if (user != null) {
            if (user.getDisplayName().isEmpty()) {
                name.setText("User");
            } else name.setText(user.getDisplayName());

            mobile.setText(user.getPhoneNumber());

            Glide.with(this).load(Utils.COMMON_USER_AVATAR_URL).circleCrop().into(avatar);
        }

        Menu menu_nav = nav.getMenu();
        MenuItem menuItem = menu_nav.findItem(R.id.app_bar_switch_theme);
        checkTheme = menuItem.getActionView().findViewById(R.id.switch_theme);

        if (isDarkTheme) checkTheme.setChecked(false);
        else checkTheme.setChecked(true);

        //add listener
        checkTheme.setOnCheckedChangeListener((buttonView, isChecked) -> changeTheme());

        nav.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.app_bar_switch_theme:
                    if (checkTheme.isChecked())
                        checkTheme.setChecked(false);
                    else checkTheme.setChecked(true);

                    changeTheme();
                    break;

                case R.id.login_logout:
                    if (user != null) {
                        FirebaseAuth.getInstance().signOut();
                        recreate();
                    } else {
                        startActivity(new Intent(getApplicationContext(), LoginRegistrationHolder.class));
                    }
                    break;

                case R.id.doctor_registration:
                    startActivity(new Intent(getApplicationContext(), DoctorRegistration.class));
                    break;

                case R.id.doctor_list:
                    startActivity(new Intent(getApplicationContext(), DoctorsFragmentsHolder.class));
                    break;

            }

            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void init() {
        initDiseaseListData();

        videoView = findViewById(R.id.video);
        String uri = "https://firebasestorage.googleapis.com/v0/b/wireless-project-in-lab.appspot.com/o/Backstreet%20Boys%20-%20Show%20Me%20The%20Meaning%20Of%20Being%20Lonely_HIGH.mp4?alt=media&token=895ae878-58d4-4f8c-8cee-9b75ea03c1a4";

//        videoView.setVideoURI(Uri.parse(uri));

//        MediaController mediaController = new MediaController(this);
//        videoView.setMediaController(mediaController);
//        mediaController.setAnchorView(videoView);
//        videoView.start();

        videoView.setOnPreparedListener(mediaPlayer -> {
            Logger.getLogger("Video Prepared").warning("Prepared");

            ProgressBar progressBar = findViewById(R.id.loading_video);
            progressBar.setVisibility(View.GONE);
        });

        ProgressBar progressBar = findViewById(R.id.loading_video);
        progressBar.setVisibility(View.GONE);
    }

    private void initDiseaseListData() {
        RecyclerView recyclerView = findViewById(R.id.disease_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        DiseaseListAdapter diseaseListAdapter = new DiseaseListAdapter(diseases, getApplicationContext());

        recyclerView.setAdapter(diseaseListAdapter);

        diseaseListAdapter.setOnItemClickListener(p -> {
            Log.d(TAG, "initDiseaseListData: Name: " + diseases.get(p).getName());
            Log.d(TAG, "initDiseaseListData: Desc: " + diseases.get(p).getDescription());

            if (user != null) {
                Intent intent = new Intent(getApplicationContext(), DiseaseQuestionsAnswering.class);
                intent.putExtra(Utils.DISEASE_NAME, diseases.get(p));
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), LoginRegistrationHolder.class);
                startActivity(intent);
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(Utils.DISEASE_LIST).keepSynced(true);

//        reference.onDisconnect().setValue()

        reference.child(Utils.DISEASE_LIST)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.d(TAG, "onDataChange() returned: " + dataSnapshot.getChildrenCount());

                        if (dataSnapshot.hasChildren()) {

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Disease disease = snapshot.getValue(Disease.class);

                                if (disease != null) {
                                    diseases.add(disease);
                                } else Log.d(TAG, "onDataChange: " + "null");
                            }

                            diseaseListAdapter.notifyDataSetChanged();

                        } else Log.d(TAG, "onDataChange: " + "no data");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled: ", databaseError.toException());
                    }
                });
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

        initCurrentTheme();
//        recreate();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
