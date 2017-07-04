package samuel.example.com.arxict;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostsFragment extends Fragment {
    List<PostContent> postsList ;
    private RecyclerView mRecyclerView;
    private PostsAdapter postsAdapter;
   // private ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.fragment_posts, container, false);
       // progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        postsAdapter = new PostsAdapter();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
      onOrientationChange(getResources().getConfiguration().orientation , savedInstanceState);

        return rootView ;
    }

    public void onOrientationChange(int orientation ,  Bundle savedInstanceState){
        int landScape=2;
        int portrait= 1;
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        if (widthPixels>=1023 || heightPixels>=1023)
        {
            landScape=3;
            portrait=2;
        }

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(portrait, StaggeredGridLayoutManager.VERTICAL ));
            mRecyclerView.setAdapter(postsAdapter);

        }
        else if(orientation == Configuration.ORIENTATION_LANDSCAPE){

            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(landScape, StaggeredGridLayoutManager.VERTICAL ));
            mRecyclerView.setAdapter(postsAdapter);

        }
        getData ();

    }


    private  void getData ()
    {
        ApiInterface apiService = ApiInterface.ApiClient.getClient().create(ApiInterface.class);
        Call<List<PostContent>> call = apiService.getPosta();
        call.enqueue(new Callback<List<PostContent>>() {
            @Override
            public void onResponse(Call<List<PostContent>> call, Response<List<PostContent>> response) {
                postsList = response.body();
                postsAdapter.setApiResponse(postsList);

            }

            @Override
            public void onFailure(Call<List<PostContent>> call, Throwable t) {

            }
        });


    }

}
