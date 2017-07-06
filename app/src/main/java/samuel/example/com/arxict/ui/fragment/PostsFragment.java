package samuel.example.com.arxict.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import samuel.example.com.arxict.network.ApiInterface;
import samuel.example.com.arxict.adapter.PostsAdapter;
import samuel.example.com.arxict.R;
import samuel.example.com.arxict.model.PostContent;

import static samuel.example.com.arxict.utilities.checkInternetConnection;


public class PostsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    List<PostContent> postsList ;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private PostsAdapter postsAdapter;

    private String savedInstanceData ="dada";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.fragment_posts, container, false);
        ButterKnife.bind(this , rootView);
        swipeRefreshLayout.setRefreshing(true);
        postsAdapter = new PostsAdapter();
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
        getData ( savedInstanceState);

    }


    private  void getData (Bundle savedInstanceState)
    {
        if (savedInstanceState==null) {
            if (checkInternetConnection ()) {
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
        else {
            postsList =  savedInstanceState.getParcelableArrayList(savedInstanceData);
            postsAdapter.setApiResponse(postsList);
        }

        swipeRefreshLayout.setRefreshing(false);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(savedInstanceData, (ArrayList<? extends Parcelable>) postsList);

    }

    @Override
    public void onRefresh() {
        getData(null);    }
}
