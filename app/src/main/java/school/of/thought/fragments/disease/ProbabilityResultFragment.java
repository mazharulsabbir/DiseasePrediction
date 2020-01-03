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
import school.of.thought.model.Dengue;
import school.of.thought.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProbabilityResultFragment extends Fragment {

    private Dengue dengue;

    public static ProbabilityResultFragment newInstance(Dengue dengue) {
        ProbabilityResultFragment fragment = new ProbabilityResultFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Utils.DENGUE, dengue);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            dengue = getArguments().getParcelable(Utils.DENGUE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_probability_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView result = view.findViewById(R.id.result);

        result.setText(String.valueOf(dengue.getDengue()));
    }
}
