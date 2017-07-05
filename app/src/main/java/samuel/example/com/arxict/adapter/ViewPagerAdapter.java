package samuel.example.com.arxict.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by samuel on 7/4/2017.
 */

public class ViewPagerAdapter  extends FragmentPagerAdapter {

    private ArrayList<String> fragmentsNames ;
    private ArrayList <Fragment> fragmentsList;
    public ViewPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
        this.fragmentsNames =new ArrayList<String>();
        this.fragmentsList = new ArrayList<Fragment>();
    }

    public void setData (ArrayList<String> fragmentsNames ,ArrayList <Fragment> fragmentsList )
    {
        this.fragmentsList =fragmentsList ;
        this.fragmentsNames =fragmentsNames;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {

        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        if(fragmentsList == null)
        {
            return 0;
        }
        else
            return fragmentsList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsNames.get(position);
    }
}