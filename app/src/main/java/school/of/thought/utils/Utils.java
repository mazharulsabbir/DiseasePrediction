package school.of.thought.utils;

import com.google.firebase.auth.FirebaseUser;

public class Utils {

    public static final String COMMON_USER_AVATAR_URL = "https://firebasestorage.googleapis.com/v0/b/diseaseprediction-8dd8c.appspot.com/o/profile_image%2Favatar-placeholder.png?alt=media&token=648c54fc-77f7-4f01-b702-6e3ad5cbf35b";

    public static final String CURRENT_THEME = "current-theme";
    public static final String THEME = "theme";
    public static final String DOCTORS_REF = "doctors/";

    public static final String DISEASE_LIST = "disease-list/";
    public static final String DISEASE_NAME = "disease-name";
    public static final String USER_REF = "users/";

    public static String getUserInfoReference(FirebaseUser user) {
        return "users/" + user.getUid() + "/";
    }
}
