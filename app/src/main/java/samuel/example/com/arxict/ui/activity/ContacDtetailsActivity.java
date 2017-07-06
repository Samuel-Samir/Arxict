package samuel.example.com.arxict.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import samuel.example.com.arxict.R;
import samuel.example.com.arxict.model.ContactData;
import samuel.example.com.arxict.ui.fragment.ContactDetailsFragment;

import static samuel.example.com.arxict.ui.fragment.ContactsFragment.CONTACT_OBJECT;

public class ContacDtetailsActivity extends AppCompatActivity {
    FragmentManager mFragmentManager ;
    FragmentTransaction mFragmentTransaction ;
    public static String TAG="fragmebtTag";
    public static String BUNDLE_DATA="data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contac_dtetails);

        // check if getIntent value = null
        if (getIntent() != null && getIntent().getParcelableExtra(CONTACT_OBJECT) !=null )
        {
            ContactData contactData = getIntent().getParcelableExtra(CONTACT_OBJECT) ;
            if (contactData!=null)
            {
                // push ContactDetailsFragment
                ContactDetailsFragment contactDetailsFragment = new ContactDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(BUNDLE_DATA ,contactData );
                contactDetailsFragment.setArguments(bundle);
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.content_main,contactDetailsFragment , TAG).commit();
            }

        }

    }
}
