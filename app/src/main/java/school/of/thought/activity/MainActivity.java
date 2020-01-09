package school.of.thought.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.logging.Logger;

import school.of.thought.R;
import school.of.thought.fragments.disease.DiseaseFragment;
import school.of.thought.fragments.doctors.DoctorListFragment;
import school.of.thought.fragments.donor_receiver.ApplierListFragment;
import school.of.thought.fragments.donor_receiver.DonorListFragment;
import school.of.thought.fragments.donor_receiver.PeerToPeerDonorReceiverListFragment;
import school.of.thought.fragments.donor_receiver.ReceiverListFragment;
import school.of.thought.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private boolean isDarkTheme;
    private VideoView videoView;
    private Switch checkTheme;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCurrentTheme();
        setContentView(R.layout.activity_main);

        initNavigationDrawer();

        init();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new DiseaseFragment()).commit();
        }

        findViewById(R.id.donate).setOnClickListener(view -> {
            if (user != null) {
                startActivity(new Intent(getApplicationContext(), DonateActivity.class));
            } else {
                Intent intent = new Intent(this, LoginRegistrationHolder.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
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
        MaterialButton loginLogout = navHeaderView.findViewById(R.id.login_logout);

        if (user != null) {
            if (user.getDisplayName() == null || user.getDisplayName().isEmpty()) {
                name.setText("User");
            } else name.setText(user.getDisplayName());

            mobile.setText(user.getPhoneNumber());

            Glide.with(this).load(Utils.COMMON_USER_AVATAR_URL).circleCrop().into(avatar);

            loginLogout.setText("Logout");
            loginLogout.setOnClickListener(view -> {
                FirebaseAuth.getInstance().signOut();
                recreate();
            });
        } else {
            loginLogout.setText("Login");
            loginLogout.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), LoginRegistrationHolder.class);
                startActivity(intent);
            });
        }

        Menu menu_nav = nav.getMenu();
        MenuItem menuItem = menu_nav.findItem(R.id.app_bar_switch_theme);
        checkTheme = menuItem.getActionView().findViewById(R.id.switch_theme);

        if (isDarkTheme) checkTheme.setChecked(false);
        else checkTheme.setChecked(true);

        //add listener
        checkTheme.setOnCheckedChangeListener((buttonView, isChecked) ->
                changeTheme()
        );

        nav.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.app_bar_switch_theme:
                    if (checkTheme.isChecked())
                        checkTheme.setChecked(false);
                    else checkTheme.setChecked(true);

                    changeTheme();
                    break;

                case R.id.disease_list:
                    openFragment(new DiseaseFragment());
                    break;

                case R.id.peer_to_peer_donor_receiver_list:
                    openFragment(new PeerToPeerDonorReceiverListFragment());
                    break;

                case R.id.donor_list:
                    openFragment(new DonorListFragment());
                    break;

                case R.id.receiver_list:
                    openFragment(new ReceiverListFragment());
                    break;

                case R.id.doctor_list:
                    openFragment(new DoctorListFragment());
                    break;

                case R.id.applier_list:
                    openFragment(new ApplierListFragment());
                    break;

                case R.id.donate:
                    if (user != null) {
                        startActivity(new Intent(getApplicationContext(), DonateActivity.class));
                    } else {
                        Intent intent = new Intent(this, LoginRegistrationHolder.class);
                        startActivity(intent);
                    }
                    break;

                case R.id.apply_for_scholarship:
                    Intent intent = new Intent(getApplicationContext(), ApplyHolderActivity.class);
                    intent.putExtra(Utils.APPLY_FOR, Utils.FRAGMENT_APPLY_FOR_SCHOLARSHIP);
                    startActivity(intent);
                    break;

                case R.id.apply_for_help:
                    intent = new Intent(getApplicationContext(), ApplyHolderActivity.class);
                    intent.putExtra(Utils.APPLY_FOR, Utils.FRAGMENT_APPLY_FOR_HELP);
                    startActivity(intent);
                    break;

                case R.id.profile:
                    intent = new Intent(getApplicationContext(), ProfileAboutHolderActivity.class);
                    intent.putExtra(Utils.PROFILE_OR_ABOUT, 0);
                    startActivity(intent);
                    break;

                case R.id.about_us:
                    intent = new Intent(getApplicationContext(), ProfileAboutHolderActivity.class);
                    intent.putExtra(Utils.PROFILE_OR_ABOUT, 1);
                    startActivity(intent);
                    break;

                case R.id.doctor_registration:
                    startActivity(new Intent(getApplicationContext(), DoctorRegistration.class));
                    break;
            }

            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragment_container,
                fragment).commit();

        fragmentManager.executePendingTransactions();
    }

    private void init() {
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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
