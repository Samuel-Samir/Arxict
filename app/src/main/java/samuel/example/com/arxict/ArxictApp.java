package samuel.example.com.arxict;

import android.app.Application;

/**
 * Created by samuel on 7/5/2017.
 */

public class ArxictApp extends Application {

    private static ArxictApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized ArxictApp getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        if(listener!=null)
            ConnectivityReceiver.connectivityReceiverListener  = listener;
    }
}