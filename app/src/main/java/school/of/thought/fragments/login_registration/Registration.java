package school.of.thought.fragments.login_registration;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import school.of.thought.R;
import school.of.thought.activity.LoginRegistrationHolder;
import school.of.thought.fragments.login_registration.two_factor_auth.TwoFactorAuth;
import school.of.thought.model.UserRegistration;

public class Registration extends Fragment {

    private static final String TAG = "Login";
    TextInputLayout mName, mEmail, mPhone, mPassword;
    private String phoneNo = "", otpCode = "";
    private ImageView back_to_login;
    private TextView already_have_account;
    private CircularProgressButton register_button;
    private Toast toast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        already_have_account = view.findViewById(R.id.login);
        back_to_login = view.findViewById(R.id.back_to_login);
        register_button = view.findViewById(R.id.cirRegisterButton);

        already_have_account.setOnClickListener(view1 -> Login());
        back_to_login.setOnClickListener(view1 -> Login());
        register_button.setOnClickListener(view1 -> twoFactorAuth());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mName = view.findViewById(R.id.textInputName);
        mEmail = view.findViewById(R.id.textInputEmail);
        mPhone = view.findViewById(R.id.textInputMobile);
        mPassword = view.findViewById(R.id.textInputPassword);

    }

    private void twoFactorAuth() {
        String name, email, phone, password;

        name = String.valueOf(mName.getEditText().getText());
        email = String.valueOf(mEmail.getEditText().getText());
        phone = String.valueOf(mPhone.getEditText().getText());
        password = String.valueOf(mPassword.getEditText().getText());

        if (validateInfo(name, email, phone, password)) {
            androidx.fragment.app.Fragment twoFactorAuth = TwoFactorAuth.newInstance(new UserRegistration(name, email, phoneNo, password));
            ((LoginRegistrationHolder) getActivity()).addFirstFragment(twoFactorAuth);
            getActivity().overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
        } else {

        }
    }


    private boolean validateInfo(String name, String email, String phone, String password) {
        boolean valid = true;

        //validate name
        if (name.trim().isEmpty()) {
            mName.setError("Name Can't be empty");
            valid = false;
        } else {
            mName.setErrorEnabled(false);
        }

        //validate email
        if (email.trim().isEmpty()) {
            mEmail.setError("Email Can't be empty");
            valid = false;
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches())
                mEmail.setError("Invalid Email");
            else
                mEmail.setErrorEnabled(false);
        }

        //validate password
        if (password.trim().isEmpty()) {
            mPassword.setError("Password Can't be empty");
            valid = false;
        } else {
            if (password.trim().length() < 6) {
                mPassword.setError("Password should be at least 6");
                valid = false;
            } else
                mPassword.setErrorEnabled(false);
        }

        //validate phone number
        phoneNo = phone;

        if (phoneNo.trim().isEmpty()) {
            mPhone.setError("Phone Number Can't be empty");
            valid = false;

        } else if (phoneNo.trim().length() == 11) {
            phoneNo = "+88" + phone.trim();
            mPhone.setErrorEnabled(false);
        } else {
            valid = false;
            mPhone.setError("Invalid Phone Number");
        }

        return valid;
    }


    public void Login() {
        ((LoginRegistrationHolder) getActivity()).addFirstFragment(new Login());
        getActivity().overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
