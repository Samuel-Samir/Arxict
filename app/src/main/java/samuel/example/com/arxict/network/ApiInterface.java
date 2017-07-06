package samuel.example.com.arxict.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import samuel.example.com.arxict.model.PostContent;

/**
 * Created by samuel on 7/4/2017.
 */


public interface ApiInterface {





    @GET("posts/")
    Call<List<PostContent>> getPosta();


    class ApiClient {

        public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
        private static Retrofit retrofit = null;


        public static Retrofit getClient() {
            if (retrofit==null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }

    class ApiClientFootBall {

        public static final String BASE_URL_FOOT_BALL = "http://api.football-data.org/v1/";
        private static Retrofit retrofit = null;


        public static Retrofit getClient() {
            if (retrofit==null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL_FOOT_BALL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }
}
