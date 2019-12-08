package school.of.thought.fragments.login_registration;

import android.os.Bundle;
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

        if (validateName(name) && validateEmail(email) && validatePhone(phone) && validatePassword(password)) {
            androidx.fragment.app.Fragment twoFactorAuth = TwoFactorAuth.newInstance(phoneNo);
            ((LoginRegistrationHolder) getActivity()).addFirstFragment(twoFactorAuth);
            getActivity().overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
        } else {

        }
    }

    private boolean validatePassword(@NonNull String password) {
        if (password.isEmpty()) {
            mPassword.setError("Name Can't be empty");
            return false;
        } else {
            mPassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone(@NonNull String phone) {
        if (phone.isEmpty()) {
            mPhone.setError("Name Can't be empty");
            return false;
        } else {
            mPhone.setErrorEnabled(false);

            if (phone.length() > 11) {
                if (phone.contains("+88") && phoneNo.length() == 14) {
                    return true;
                }
            } else {
                if (phone.contains("+88")) {
                    mPhone.setError("Invalid Phone Number");
                    return false;
                } else {
                    if (phone.length() == 11) {
                        phoneNo = "+88" + phone;
                        return true;
                    }
                }
            }
            mPhone.setError("Invalid Phone Number");
            return false;
        }
    }

    private boolean validateEmail(@NonNull String email) {
        if (email.isEmpty()) {
//            mEmail.setError("Name Can't be empty");
            return true;
        } else {
            mEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateName(@NonNull String name) {
        if (name.isEmpty()) {
            mName.setError("Name Can't be empty");
            return false;
        } else {
            mName.setErrorEnabled(false);
            return true;
        }
    }

    public void Login() {
        ((LoginRegistrationHolder) getActivity()).addFirstFragment(new Login());
        getActivity().overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
