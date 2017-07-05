package samuel.example.com.arxict.ui;


import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import samuel.example.com.arxict.AccessContacts;
import samuel.example.com.arxict.ApiInterface;
import samuel.example.com.arxict.R;
import samuel.example.com.arxict.adapter.ContactAdapter;
import samuel.example.com.arxict.adapter.PostsAdapter;
import samuel.example.com.arxict.model.ContactData;
import samuel.example.com.arxict.model.PostContent;


public class ContactsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ContactAdapter contactAdapter;
    private List<ContactData> contactDataList ;
    private AccessContacts accessContacts ;
    public static String CONTACT_OBJECT="contact";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
         accessContacts = new AccessContacts(getActivity().getContentResolver());
         mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        contactAdapter = new ContactAdapter();
        onOrientationChange(getResources().getConfiguration().orientation , savedInstanceState);

        contactAdapter.setRecyclerItemViewCallback(new ContactAdapter.RecyclerItemViewCallback() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(getActivity() , ContacDtetailsActivity.class);
                intent.putExtra(CONTACT_OBJECT ,contactDataList.get(position));
                getActivity().startActivity(intent);
            }
        });

        contactAdapter.setRecyclerPhoneCallback(new ContactAdapter.RecyclerPhoneCallback() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + contactDataList.get(position).getPhone() ));
                startActivity(intent);
            }
        });

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
            mRecyclerView.setAdapter(contactAdapter);

        }
        else if(orientation == Configuration.ORIENTATION_LANDSCAPE){

            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(landScape, StaggeredGridLayoutManager.VERTICAL ));
            mRecyclerView.setAdapter(contactAdapter);

        }
        getData ();

    }

    private  void getData ()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                contactDataList = accessContacts.getContacts();

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        contactAdapter.setApiResponse(contactDataList);
                    }
                });


            }
        }).start();


    }
}
