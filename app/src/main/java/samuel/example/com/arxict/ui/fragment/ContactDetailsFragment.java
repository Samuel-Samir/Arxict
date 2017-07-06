package samuel.example.com.arxict.ui.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import samuel.example.com.arxict.R;
import samuel.example.com.arxict.model.ContactData;

import static samuel.example.com.arxict.ui.activity.ContacDtetailsActivity.BUNDLE_DATA;

public class ContactDetailsFragment extends Fragment {

    private ContactData contactData;
    @BindView(R.id.user_name)
    TextView name;

    @BindView(R.id.phone_num)
    TextView phone;

    @BindView(R.id.email)
    TextView email;

    @BindView(R.id.user_pic)
    ImageView userImg;

    @BindView(R.id.phone_Img)
    ImageView phoneIcon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contact_details, container, false);
        ButterKnife.bind(this ,rootView);
        if (getArguments()!=null && getArguments().getParcelable(BUNDLE_DATA)!=null)
        {
            contactData = getArguments().getParcelable(BUNDLE_DATA);
            name.setText(contactData.getName());
            phone.setText(contactData.getPhone());
            phoneIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + contactData.getPhone() ));
                    startActivity(intent);
                }
            });

            if(contactData.getPhoto()!=null)
            {
                userImg.setImageBitmap(contactData.getPhoto());

            }

            if(contactData.getEmail()!=null)
            {
                email.setText(contactData.getEmail());
            }
            else if (contactData.getEmail()==null)
            {
                email.setText(getActivity().getResources().getString(R.string.no_mail));
            }
        }
        return rootView;
    }
}
