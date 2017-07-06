package samuel.example.com.arxict.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import samuel.example.com.arxict.adapter.PostsAdapter;
import samuel.example.com.arxict.R;
import samuel.example.com.arxict.model.PostContent;
import samuel.example.com.arxict.network.PostAsyncTask;

import static samuel.example.com.arxict.utilities.checkInternetConnection;
import static samuel.example.com.arxict.utilities.isTablet;


public class PostsFragment extends Fragment
{
    List<PostContent> postsList ;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    private PostsAdapter postsAdapter;

    private String savedInstanceData ="dada";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posts, container, false);
        ButterKnife.bind(this , rootView);
        postsAdapter = new PostsAdapter();
        onOrientationChange(getResources().getConfiguration().orientation , savedInstanceState);

        return rootView ;
    }

    /**
     * this function used to check orientation and set layout manager
     * @param orientation
     * @param savedInstanceState
     */
    public void onOrientationChange(int orientation ,  Bundle savedInstanceState){
        int landScape=2;
        int portrait= 1;
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        if (isTablet(getActivity()))
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

    /**
     * get the dada from api
     * @param savedInstanceState
     */
    private  void getData (Bundle savedInstanceState)
    {
        if (savedInstanceState==null) {
            if (checkInternetConnection ()) {

                PostAsyncTask postAsyncTask =new PostAsyncTask();
                postAsyncTask.setFetchPostAsyncTaskCallBack(new PostAsyncTask.FetchPostAsyncTaskCallBack() {
                    @Override
                    public void onPostExecute(List<PostContent> postContents) {
                        postsList =postContents ;
                        postsAdapter.setApiResponse(postsList);
                    }
                });
                postAsyncTask.execute();

            }
        }
        else {
            postsList =  savedInstanceState.getParcelableArrayList(savedInstanceData);
            postsAdapter.setApiResponse(postsList);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(savedInstanceData, (ArrayList<? extends Parcelable>) postsList);

    }



}
