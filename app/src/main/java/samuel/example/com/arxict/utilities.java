package samuel.example.com.arxict;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import samuel.example.com.arxict.model.UserData;
import samuel.example.com.arxict.model.dataBase.UserContract;
import samuel.example.com.arxict.model.dataBase.UserDbHelper;
import samuel.example.com.arxict.network.ConnectivityReceiver;

/**
 * Created by samuel on 7/5/2017.
 */

public class utilities {


    public static boolean checkInternetConnection() {
        return ConnectivityReceiver.isConnected();
    }

    /**
     * this function used to control the showSnackbar
     * @param isConnected
     * @param view
     * @param context
     */
    public static void showSnackbar(boolean isConnected, View view, Context context) {
        if (isConnected) {
            showSnackbarConnected(view, context);
        } else {
            showSnackbarDisconnected(view, context);
        }
    }


    /**
     * this function used to connected Snackbar
     * @param view
     * @param context
     */
    private static void showSnackbarConnected(View view, Context context) {
        Snackbar snackbar = Snackbar.make(view, R.string.snackbar_message_connected, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        snackbar.show();
    }

    /**
     * this function used to Disconnected Snackbar
     * @param view
     * @param context
     */
    public static void showSnackbarDisconnected(View view, Context context) {
        Snackbar snackbar = Snackbar.make(view, R.string.snackbar_message_disconnected, Snackbar.LENGTH_INDEFINITE);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        snackbar.show();
    }

    /**
     * this function used to add new user to db
     * @param userDbHelper
     * @param userData
     * @return index of new user
     */
    public static double addNewUserTODb(final UserDbHelper userDbHelper, final UserData userData )
    {

        UserData userData1FromDb = getUserFromDb (userDbHelper , userData.getUserEmail());
        if (userData1FromDb!=null)
        {
            return -2 ;
        }
        SQLiteDatabase sqLiteDatabase = userDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UserContract.UserInfo.COLUMN_USER_NAME, userData.getUserName());
        cv.put(UserContract.UserInfo.COLUMN_EMAIL, userData.getUserEmail());
        cv.put(UserContract.UserInfo.COLUMN_PASSWORD, userData.getUserPassword());
        double index = sqLiteDatabase.insert(UserContract.UserInfo.TABLE_NAME, null, cv);
        Log.d("db_index = ", String.valueOf(index));
        return index;

    }

    /**
     * this function used to get new user from db
     * @param userDbHelper
     * @param email
     * @return user data
     */
    public static UserData getUserFromDb (final UserDbHelper userDbHelper , final String email)
    {

        SQLiteDatabase sqLiteDatabase = userDbHelper.getReadableDatabase();
        String selection = UserContract.UserInfo.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = { email};
        Cursor cursor =   sqLiteDatabase.query(
                UserContract.UserInfo.TABLE_NAME,
                null,
                selection ,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.getCount() <1)
        {
            return null;
        }
        else {
            int userNameIndex = cursor.getColumnIndex(UserContract.UserInfo.COLUMN_USER_NAME);
            int emailIndex = cursor.getColumnIndex(UserContract.UserInfo.COLUMN_EMAIL);
            int passwordIndex = cursor.getColumnIndex(UserContract.UserInfo.COLUMN_PASSWORD);

            UserData userData = new UserData();
            while (cursor.moveToNext()) {
                userData.setUserName(cursor.getString(userNameIndex));
                userData.setUserEmail(cursor.getString(emailIndex));
                userData.setUserPassword(cursor.getString(passwordIndex));
                break;
            }
            return userData;

        }

    }

    /**
     * this function used to add  user to Shared Preferences
     * @param user
     * @param context
     */
    public static void saveUserToSharedPreferences(UserData user, Context context) {

        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.shared_pref_signed_in_user), Context.MODE_PRIVATE).edit();
        editor.putString(context.getString(R.string.shared_pref_signed_in_user_name), user.getUserName());
        editor.putString(context.getString(R.string.shared_pref_signed_in_user_email), user.getUserEmail());
        editor.commit();


    }

    /**
     * this function used to get user from Shared Preferences
     * @param context
     * @return
     */
    public static UserData getUserFromSharedPreferences(Context context) {
        UserData user = new UserData();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.shared_pref_signed_in_user), Context.MODE_PRIVATE);

        user.setUserEmail(sharedPref.getString(context.getString(R.string.shared_pref_signed_in_user_email), "-1"));
        user.setUserName(sharedPref.getString(context.getString(R.string.shared_pref_signed_in_user_name), "-1"));
        return user;
    }

    /**
     * this function used to check is phone tablet or phone
     * @param context
     * @return boolean is Tablet
     */
    public static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

}