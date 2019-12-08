package school.of.thought.fragments.login_registration;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import school.of.thought.R;
import school.of.thought.activity.LoginRegistrationHolder;

public class Login extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView add_button;
    TextView registration;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        add_button = view.findViewById(R.id.add_button);
        registration = view.findViewById(R.id.registration);
        add_button.setOnClickListener(view1 -> Registration());
        registration.setOnClickListener(view12 -> Registration());

        view.findViewById(R.id.cirLoginButton).setOnClickListener(login -> {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            TextInputLayout mEmail, mPassword;
            mEmail = view.findViewById(R.id.textInputEmail);
            mPassword = view.findViewById(R.id.textInputPassword);

            String email = mEmail.getEditText().getText().toString().trim();
            String pass = mPassword.getEditText().getText().toString().trim();

            boolean valid = true;

            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email can't be empty");
                valid = false;
            } else mEmail.setErrorEnabled(false);

            if (valid)
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mEmail.setError("Invalid email address");
                    valid = false;
                } else mEmail.setErrorEnabled(false);

            if (pass.length() < 6) {
                mPassword.setError("Password should be at least 6");
                valid = false;
            } else mPassword.setErrorEnabled(false);

            if (valid)
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = task.getResult().getUser();
                        Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Login failed : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        });
        return view;
    }

    public void Registration() {
        ((LoginRegistrationHolder) getActivity()).addFirstFragment(new Registration());
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }
}
