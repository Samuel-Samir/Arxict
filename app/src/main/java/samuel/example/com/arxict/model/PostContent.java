package samuel.example.com.arxict.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by samuel on 7/4/2017.
 */

public class PostContent implements Parcelable {

    private  int userId ;
    private  int id ;
    private  String title ;
    private  String body ;


    public PostContent (){}
    protected PostContent(Parcel in) {
        userId = in.readInt();
        id = in.readInt();
        title = in.readString();
        body = in.readString();
    }

    public static final Creator<PostContent> CREATOR = new Creator<PostContent>() {
        @Override
        public PostContent createFromParcel(Parcel in) {
            return new PostContent(in);
        }

        @Override
        public PostContent[] newArray(int size) {
            return new PostContent[size];
        }
    };

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(body);
    }
}

