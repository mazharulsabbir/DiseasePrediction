package school.of.thought.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserRegistration implements Parcelable{
    private String name, email, phone, password;

    public UserRegistration() {
    }

    public UserRegistration(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    protected UserRegistration(Parcel in) {
        name = in.readString();
        email = in.readString();
        phone = in.readString();
        password = in.readString();
    }

    public static final Creator<UserRegistration> CREATOR = new Creator<UserRegistration>() {
        @Override
        public UserRegistration createFromParcel(Parcel in) {
            return new UserRegistration(in);
        }

        @Override
        public UserRegistration[] newArray(int size) {
            return new UserRegistration[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(password);
    }
}
