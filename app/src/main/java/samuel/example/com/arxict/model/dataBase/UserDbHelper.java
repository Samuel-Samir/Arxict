package samuel.example.com.arxict.model.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import samuel.example.com.arxict.model.dataBase.UserContract.UserInfo;

/**
 * Created by samuel on 7/6/2017.
 */

public class UserDbHelper   extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="arxict.dp";
    private static final int DATABASE_VERSION = 1;

    public UserDbHelper(Context context){
        super(context , DATABASE_NAME , null , DATABASE_VERSION);

    }

    /**
     * Create Table
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_PHOTO_TABLE = "CREATE TABLE " + UserInfo.TABLE_NAME + " ( "+
                UserInfo.COLUMN_USER_NAME +" TEXT NOT NULL, "+
                UserInfo.COLUMN_EMAIL +" TEXT NOT NULL, "+
                UserInfo.COLUMN_PASSWORD +" REAL NOT NULL"+
                ");";

        db.execSQL(SQL_CREATE_PHOTO_TABLE);

    }

    /**
     * "DROP TABLE
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserInfo.TABLE_NAME );
        onCreate(db);
    }



}
