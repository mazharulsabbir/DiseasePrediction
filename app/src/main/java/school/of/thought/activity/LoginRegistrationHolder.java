package school.of.thought.activity;

import android.app.Fragment;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import school.of.thought.R;
import school.of.thought.fragments.login_registration.Login;

public class LoginRegistrationHolder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration_holder);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.login_registration_holder, new Login())
                    .commit();
        }
    }

    public void addFirstFragment(Fragment replaceFragment) {
        this.getFragmentManager().beginTransaction()
                .replace(R.id.login_registration_holder, replaceFragment).addToBackStack(null)
                .commit();
    }
}
