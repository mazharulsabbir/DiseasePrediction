package school.of.thought.fragments.apply;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import school.of.thought.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApplyForHelpFragment extends Fragment {


    public ApplyForHelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apply_for_help, container, false);
    }

}
