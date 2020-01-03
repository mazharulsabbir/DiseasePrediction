package school.of.thought.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import school.of.thought.R;
import school.of.thought.fragments.apply.ApplyForHelpFragment;
import school.of.thought.fragments.apply.ApplyForScholarshipFragment;
import school.of.thought.utils.Utils;

public class ApplyHolderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_scholership);

        int type = getIntent().getIntExtra(Utils.APPLY_FOR, 0);//0 for scholarship fragment & 1 for help fragment

        if (savedInstanceState == null) {

            Fragment f;
            if (type == 0) {
                f = new ApplyForScholarshipFragment();
                setToolbarTitle("Apply For Scholarship");
            } else{
                f = new ApplyForHelpFragment();
                setToolbarTitle("Apply For Help");
            }

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, f)
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
