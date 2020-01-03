package school.of.thought.fragments.donor_receiver;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import school.of.thought.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApplierListFragment extends Fragment {


    public ApplierListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_applier_list, container, false);
    }

}
