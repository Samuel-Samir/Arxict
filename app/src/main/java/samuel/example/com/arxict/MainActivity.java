package samuel.example.com.arxict;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewPager = (ViewPager) findViewById(R.id.viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        setFragments () ;
    }

    private  void setFragments ()
    {
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter customAdapter = new ViewPagerAdapter( getSupportFragmentManager()  );

        ArrayList<String> fragmentsNames = new ArrayList<>();
       // fragmentsNames.add(getResources().getString(R.string.top_news));
       // fragmentsNames.add(getResources().getString(R.string.latest_news));
        fragmentsNames.add("fr1");
        fragmentsNames.add("fr2");


        ArrayList<Fragment> fragmentsList =new ArrayList<>();
        fragmentsList.add(new BlankFragment());
        fragmentsList.add(new BlankFragment());
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

}
