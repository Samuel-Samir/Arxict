package samuel.example.com.arxict.network;


import android.os.AsyncTask;
import org.json.JSONArray;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import samuel.example.com.arxict.model.PostContent;

/**
 * Created by samuel on 7/6/2017.
 */

public class PostAsyncTask extends AsyncTask<String ,Void ,List<PostContent> > {

    private final String LOG_TAG = PostAsyncTask.class.getName();
    private FetchPostAsyncTaskCallBack fetchPostAsyncTaskCallBack;



    public void setFetchPostAsyncTaskCallBack (FetchPostAsyncTaskCallBack fetchPostAsyncTaskCallBack)
    {
        this.fetchPostAsyncTaskCallBack =fetchPostAsyncTaskCallBack;

    }


    @Override
    protected List<PostContent> doInBackground(String... params) {

        URL postUrlRequest = NetworkUtils.buildUrl();
        try {

            String jasonResponse =NetworkUtils.getResponseFromAPI(postUrlRequest);
            JSONArray jsonArray = new JSONArray(jasonResponse);
            int listSize  = jsonArray.length() -1;
            List <PostContent> postContents = new ArrayList<PostContent>();
            for (int i=0 ;i<listSize ;i++)
            {
                PostContent postContentObj = new PostContent();
                postContentObj.setId(jsonArray.getJSONObject(i).getInt("id"));
                postContentObj.setUserId(jsonArray.getJSONObject(i).getInt("userId"));
                postContentObj.setTitle(jsonArray.getJSONObject(i).getString("title"));
                postContentObj.setBody(jsonArray.getJSONObject(i).getString("body"));

                postContents.add(postContentObj);
            }

            return postContents;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }





    @Override
    protected void onPostExecute(List<PostContent> postContents) {
        if(postContents!=null)
        {
            fetchPostAsyncTaskCallBack.onPostExecute(postContents);
        }

    }

    public  interface FetchPostAsyncTaskCallBack
    {
        void onPostExecute (List<PostContent>  postContents);
    }

}