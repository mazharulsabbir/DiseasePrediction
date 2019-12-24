package school.of.thought.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import school.of.thought.R;
import school.of.thought.fragments.disease.DiseaseDetailFragment;
import school.of.thought.model.Disease;
import school.of.thought.utils.Utils;

public class DiseaseQuestionsAnswering extends AppCompatActivity {
    private Disease disease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_questions_answering);

        if (getIntent() != null) {
            disease = getIntent().getParcelableExtra(Utils.DISEASE_NAME);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(disease.getName());
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, DiseaseDetailFragment
                            .newInstance(disease)
                    )
                    .commit();
        }
    }
}
