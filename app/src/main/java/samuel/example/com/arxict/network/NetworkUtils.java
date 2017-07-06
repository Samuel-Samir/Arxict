package samuel.example.com.arxict.network;


import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


/**
 * Created by samuel on 7/6/2017.
 */

public class NetworkUtils{

        private static final String TAG  = NetworkUtils.class.getName();
        private static final String Post_URL ="https://jsonplaceholder.typicode.com/posts";



        public static URL buildUrl ()
        {
            Uri buildUri = Uri.parse(Post_URL).buildUpon()
                    .build();
            URL url = null ;
            try {
                url = new URL(buildUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        }

        public static String getResponseFromAPI (URL url) throws IOException
        {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            try {

                InputStream inputStream = httpURLConnection.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput)
                    return scanner.next();
                else
                    return null;
            }
            finally {
                httpURLConnection.disconnect();
            }


        }



}


