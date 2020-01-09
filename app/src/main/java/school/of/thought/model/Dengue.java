package school.of.thought.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dengue implements Parcelable {
    @SerializedName("Severe_headache")
    @Expose
    private String severeHeadache;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("blooding")
    @Expose
    private String blooding;
    @SerializedName("days")
    @Expose
    private String days;
    @SerializedName("dengue")
    @Expose
    private String dengue;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("high_fever")
    @Expose
    private String highFever;
    @SerializedName("joint_pain")
    @Expose
    private String jointPain;
    @SerializedName("muscle_pain")
    @Expose
    private String musclePain;
    @SerializedName("pain_behind_eyes")
    @Expose
    private String painBehindEyes;
    @SerializedName("rash")
    @Expose
    private String rash;
    @SerializedName("swollen_gland")
    @Expose
    private String swollenGland;
    @SerializedName("vomiting")
    @Expose
    private String vomiting;

    public Dengue(String severeHeadache, String age, String blooding, String days, String gender, String highFever, String jointPain, String musclePain, String painBehindEyes, String rash, String swollenGland, String vomiting) {
        this.severeHeadache = severeHeadache;
        this.age = age;
        this.blooding = blooding;
        this.days = days;
        this.gender = gender;
        this.highFever = highFever;
        this.jointPain = jointPain;
        this.musclePain = musclePain;
        this.painBehindEyes = painBehindEyes;
        this.rash = rash;
        this.swollenGland = swollenGland;
        this.vomiting = vomiting;
    }


    protected Dengue(Parcel in) {
        severeHeadache = in.readString();
        age = in.readString();
        blooding = in.readString();
        days = in.readString();
        dengue = in.readString();
        gender = in.readString();
        highFever = in.readString();
        jointPain = in.readString();
        musclePain = in.readString();
        painBehindEyes = in.readString();
        rash = in.readString();
        swollenGland = in.readString();
        vomiting = in.readString();
    }

    public static final Creator<Dengue> CREATOR = new Creator<Dengue>() {
        @Override
        public Dengue createFromParcel(Parcel in) {
            return new Dengue(in);
        }

        @Override
        public Dengue[] newArray(int size) {
            return new Dengue[size];
        }
    };

    public String getSevereHeadache() {
        return severeHeadache;
    }

    public void setSevereHeadache(String severeHeadache) {
        this.severeHeadache = severeHeadache;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBlooding() {
        return blooding;
    }

    public void setBlooding(String blooding) {
        this.blooding = blooding;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getDengue() {
        return dengue;
    }

    public void setDengue(String dengue) {
        this.dengue = dengue;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHighFever() {
        return highFever;
    }

    public void setHighFever(String highFever) {
        this.highFever = highFever;
    }

    public String getJointPain() {
        return jointPain;
    }

    public void setJointPain(String jointPain) {
        this.jointPain = jointPain;
    }

    public String getMusclePain() {
        return musclePain;
    }

    public void setMusclePain(String musclePain) {
        this.musclePain = musclePain;
    }

    public String getPainBehindEyes() {
        return painBehindEyes;
    }

    public void setPainBehindEyes(String painBehindEyes) {
        this.painBehindEyes = painBehindEyes;
    }

    public String getRash() {
        return rash;
    }

    public void setRash(String rash) {
        this.rash = rash;
    }

    public String getSwollenGland() {
        return swollenGland;
    }

    public void setSwollenGland(String swollenGland) {
        this.swollenGland = swollenGland;
    }

    public String getVomiting() {
        return vomiting;
    }

    public void setVomiting(String vomiting) {
        this.vomiting = vomiting;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(severeHeadache);
        parcel.writeString(age);
        parcel.writeString(blooding);
        parcel.writeString(days);
        parcel.writeString(dengue);
        parcel.writeString(gender);
        parcel.writeString(highFever);
        parcel.writeString(jointPain);
        parcel.writeString(musclePain);
        parcel.writeString(painBehindEyes);
        parcel.writeString(rash);
        parcel.writeString(swollenGland);
        parcel.writeString(vomiting);
    }
}
