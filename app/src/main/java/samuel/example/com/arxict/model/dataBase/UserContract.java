package samuel.example.com.arxict.model.dataBase;

import android.provider.BaseColumns;

/**
 * Created by samuel on 7/6/2017.
 */

public class UserContract {

    public static final class UserInfo implements BaseColumns
    {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_USER_NAME="name";
        public static final String COLUMN_EMAIL="email";
        public static final String COLUMN_PASSWORD="password";

    }
}
