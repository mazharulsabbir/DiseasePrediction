package school.of.thought.utils;

import com.google.firebase.auth.FirebaseUser;

public interface Utils {

    String COMMON_USER_AVATAR_URL = "https://firebasestorage.googleapis.com/v0/b/diseaseprediction-8dd8c.appspot.com/o/profile_image%2Favatar-placeholder.png?alt=media&token=648c54fc-77f7-4f01-b702-6e3ad5cbf35b";

    String CURRENT_THEME = "current-theme";
    String THEME = "theme";
    String DOCTORS_REF = "doctors/";

    String DISEASE_LIST = "disease-list/";
    String DISEASE_NAME = "disease-name";

    String USER_REF = "users/";

    int QUES_TYPE_DROPDOWN = 0;
    int QUES_TYPE_RADIO_GROUP = 1;
    int QUES_TYPE_TEXT = 2;

    String TYPE_DROPDOWN = "dropdown";
    String TYPE_RADIO_GROUP = "radio_button";
    String TYPE_TEXT = "text";
    String DENGUE = "dengue_result";

    static String getUserInfoReference(FirebaseUser user) {
        return "users/" + user.getUid() + "/";
    }

    static String getQuesListOf(String of) {
        return "disease-list/" + of + "/questions/";

    }
}
