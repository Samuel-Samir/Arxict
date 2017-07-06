package samuel.example.com.arxict.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import samuel.example.com.arxict.ArxictApp;
import samuel.example.com.arxict.network.ConnectivityReceiver;
import samuel.example.com.arxict.R;
import samuel.example.com.arxict.adapter.ViewPagerAdapter;
import samuel.example.com.arxict.model.UserData;
import samuel.example.com.arxict.ui.fragment.ContactsFragment;
import samuel.example.com.arxict.ui.fragment.PostsFragment;

import static samuel.example.com.arxict.utilities.getUserFromSharedPreferences;
import static samuel.example.com.arxict.utilities.saveUserToSharedPreferences;
import static samuel.example.com.arxict.utilities.showSnackbar;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
         tabLayout = (TabLayout) findViewById(R.id.tab_layout);
         viewPager = (ViewPager) findViewById(R.id.pager);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));

        }

        setFragments () ;

    }

    private  void setFragments ()
    {
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter customAdapter = new ViewPagerAdapter( getSupportFragmentManager()  );

        ArrayList<String> fragmentsNames = new ArrayList<>();
        fragmentsNames.add(getResources().getString(R.string.contacts));
        fragmentsNames.add(getResources().getString(R.string.post));


        ArrayList<Fragment> fragmentsList =new ArrayList<>();
        fragmentsList.add(new ContactsFragment());
        fragmentsList.add(new PostsFragment());
        customAdapter.setData(fragmentsNames ,fragmentsList);

        viewPager.setAdapter(customAdapter);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        ArxictApp.getInstance().setConnectivityListener(MainActivity.this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnackbar(isConnected, findViewById(android.R.id.content), this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_log_out) {
            UserData user = getUserFromSharedPreferences(this);
            if (!user.getUserEmail().equals("-1")) {


                user.setUserName("-1");
                user.setUserEmail("-1");
                saveUserToSharedPreferences(user, this);
            }
            startActivity(new Intent(this , SignInActivity.class));
            return true;
        }
        if (id==R.id.action_language)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
