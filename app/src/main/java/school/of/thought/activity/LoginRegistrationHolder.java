package school.of.thought.activity;

import androidx.appcompat.app.AppCompatActivity;
import school.of.thought.R;
import school.of.thought.fragments.login_registration.Login;

import android.app.Fragment;
import android.os.Bundle;

public class LoginRegistrationHolder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration_holder);
        Fragment login = new Login();
        addFirstFragment(login);
    }

    public void addFirstFragment(Fragment replaceFragment) {
        this.getFragmentManager().beginTransaction()
                .replace(R.id.login_registration_holder,replaceFragment).addToBackStack(null)
                .commit();
    }
}
