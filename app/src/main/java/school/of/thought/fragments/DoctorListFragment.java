package school.of.thought.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import school.of.thought.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorListFragment extends Fragment {


    private View rootView;
    public DoctorListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_doctor_list, container, false);


        return rootView;
    }

}
