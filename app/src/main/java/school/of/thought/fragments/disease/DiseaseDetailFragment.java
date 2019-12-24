package school.of.thought.fragments.disease;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import school.of.thought.R;
import school.of.thought.model.Disease;
import school.of.thought.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiseaseDetailFragment extends Fragment {

    private Disease disease;

    public static DiseaseDetailFragment newInstance(Disease disease) {
        DiseaseDetailFragment fragment = new DiseaseDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(Utils.DISEASE_NAME, disease);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            disease = getArguments().getParcelable(Utils.DISEASE_NAME);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_disease_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView diseaseDesc = view.findViewById(R.id.disease_desc);

        if (disease != null) {
            diseaseDesc.setText(disease.getDescription());
        }

    }

}
