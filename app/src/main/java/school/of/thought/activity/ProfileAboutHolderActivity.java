package school.of.thought.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import school.of.thought.R;
import school.of.thought.fragments.profile_about.AboutFragment;
import school.of.thought.fragments.profile_about.ProfileFragment;
import school.of.thought.utils.Utils;

public class ProfileAboutHolderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_about_holder);

        if (savedInstanceState==null){
            Fragment f;
            if (getIntent().getIntExtra(Utils.PROFILE_OR_ABOUT, 0) == 0) {
                f = new ProfileFragment();
                setToolbarTitle("Profile");
            }else {
                f = new AboutFragment();
                setToolbarTitle("About");
            }

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,f)
                    .commit();
        }
    }

    private void setToolbarTitle(String toolbarTitle) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(toolbarTitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
