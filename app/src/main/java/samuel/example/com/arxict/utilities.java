package samuel.example.com.arxict;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.util.List;

/**
 * Created by samuel on 7/5/2017.
 */

public class utilities {


    public static boolean checkInternetConnection() {
        return ConnectivityReceiver.isConnected();
    }

    public static void showSnackbar(boolean isConnected, View view, Context context) {
        if (isConnected) {
            showSnackbarConnected(view, context);
        } else {
            showSnackbarDisconnected(view, context);
        }
    }

    private static void showSnackbarConnected(View view, Context context) {
        Snackbar snackbar = Snackbar.make(view, R.string.snackbar_message_connected, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        snackbar.show();
    }

    public static void showSnackbarDisconnected(View view, Context context) {
        Snackbar snackbar = Snackbar.make(view, R.string.snackbar_message_disconnected, Snackbar.LENGTH_INDEFINITE);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        snackbar.show();
    }

}