package school.of.thought.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Disease implements Parcelable{
    private String image;
    private String name;
    private String description;

    public Disease() {
    }

    public Disease(String image, String name, String description) {
        this.image = image;
        this.name = name;
        this.description = description;
    }

    protected Disease(Parcel in) {
        image = in.readString();
        name = in.readString();
        description = in.readString();
    }

    public static final Creator<Disease> CREATOR = new Creator<Disease>() {
        @Override
        public Disease createFromParcel(Parcel in) {
            return new Disease(in);
        }

        @Override
        public Disease[] newArray(int size) {
            return new Disease[size];
        }
    };

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(image);
        parcel.writeString(name);
        parcel.writeString(description);
    }
}
