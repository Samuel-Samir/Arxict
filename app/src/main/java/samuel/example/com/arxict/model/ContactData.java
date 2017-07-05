package samuel.example.com.arxict.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by samuel on 7/5/2017.
 */

public class ContactData implements Parcelable {

    private String name ;
    private String phone;
    private String email;
    private Bitmap photo;
    public ContactData (){};
    public ContactData(String name, String phone, String email, Bitmap photo) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.photo = photo;
    }

    protected ContactData(Parcel in) {
        name = in.readString();
        phone = in.readString();
        email = in.readString();
        photo = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<ContactData> CREATOR = new Creator<ContactData>() {
        @Override
        public ContactData createFromParcel(Parcel in) {
            return new ContactData(in);
        }

        @Override
        public ContactData[] newArray(int size) {
            return new ContactData[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeParcelable(photo, flags);
    }
}
