package school.of.thought.utils;

import com.google.firebase.auth.FirebaseUser;

public class Utils {
    public static final String CURRENT_THEME = "current-theme";
    public static final String THEME = "theme";
    public static final String DOCTORS_REF = "doctors/";

    public static String getUserInfoReference(FirebaseUser user) {
        return "users/" + user.getUid() + "/";
    }
}
