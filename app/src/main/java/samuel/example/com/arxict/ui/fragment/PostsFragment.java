package samuel.example.com.arxict.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.error_cardView)
    CardView errorCardView ;
    private PostsAdapter postsAdapter;
    private String savedInstanceData ="dada";

    public PostsFragment()
    {
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posts, container, false);
        ButterKnife.bind(this , rootView);
        progressBar.setVisibility(View.VISIBLE);
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
            landScape=2;
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
                errorCardView.setVisibility(View.GONE);
                PostAsyncTask postAsyncTask =new PostAsyncTask();
                postAsyncTask.setFetchPostAsyncTaskCallBack(new PostAsyncTask.FetchPostAsyncTaskCallBack() {
                    @Override
                    public void onPostExecute(List<PostContent> postContents) {
                        postsList =postContents ;
                        postsAdapter.setApiResponse(postsList);
                        progressBar.setVisibility(View.GONE);

                    }
                });
                postAsyncTask.execute();

            }
            else{
                progressBar.setVisibility(View.GONE);
                errorCardView.setVisibility(View.VISIBLE);


            }

        }
        else {
            postsList =  savedInstanceState.getParcelableArrayList(savedInstanceData);
            postsAdapter.setApiResponse(postsList);
            progressBar.setVisibility(View.GONE);

        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(savedInstanceData, (ArrayList<? extends Parcelable>) postsList);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh_menu, menu);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId() ;
        if (id == R.id.action_refrech)
        {
            progressBar.setVisibility(View.VISIBLE);
            getData(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
