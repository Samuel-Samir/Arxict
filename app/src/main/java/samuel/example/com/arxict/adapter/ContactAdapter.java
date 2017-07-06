package samuel.example.com.arxict.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import samuel.example.com.arxict.R;
import samuel.example.com.arxict.model.ContactData;

/**
 * Created by samuel on 7/5/2017.
 */

public class ContactAdapter  extends RecyclerView.Adapter<ContactAdapter.RecyclerViewAdapterHolder>{

    private List<ContactData> contactDataList ;
    private RecyclerItemViewCallback  recyclerItemViewCallback;
    private RecyclerPhoneCallback recyclerPhoneCallback;

    public void setApiResponse (List<ContactData> contactDataList )
    {
        this.contactDataList =contactDataList;
        notifyDataSetChanged();
    }

    public void setRecyclerItemViewCallback(RecyclerItemViewCallback recyclerItemViewCallback) {
        this.recyclerItemViewCallback = recyclerItemViewCallback;
    }
    public void setRecyclerPhoneCallback(RecyclerPhoneCallback recyclerPhoneCallback) {
        this.recyclerPhoneCallback = recyclerPhoneCallback;
    }
    @Override
    public ContactAdapter.RecyclerViewAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        int layoutPhotoItem = R.layout.contact_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutPhotoItem ,parent ,shouldAttachToParentImmediately);
        return new ContactAdapter.RecyclerViewAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactAdapter.RecyclerViewAdapterHolder holder, final int position) {

        ContactData contactData =contactDataList.get(position);
        if(contactData.getName() !=null && contactData.getPhone() !=null)
        {
            holder.name.setText(contactData.getName());
            holder.name.setContentDescription(contactData.getName());

            if (contactData.getPhoto() !=null)
            {
                holder.user_img.setImageBitmap(contactData.getPhoto());
            }

            else
                holder.user_img.setImageDrawable(holder.user_img.getContext().getResources().getDrawable(R.drawable.user));



            holder.holderLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerItemViewCallback.onItemClick(position);
                }
            });

            holder.phone_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerPhoneCallback.onItemClick(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(contactDataList== null)
            return 0;
        else
            return contactDataList.size();
    }

    public class RecyclerViewAdapterHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_user)
        TextView name ;

        @BindView(R.id.user)
        ImageView user_img ;

        @BindView(R.id.phone)
        ImageView phone_icon ;

        @BindView(R.id.contact_holder)
        LinearLayout holderLinearLayout;


        public RecyclerViewAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView );
        }

    }

    /**
     * interface used as a callback for item click
     */
    public interface RecyclerItemViewCallback {
        void onItemClick(int  position);
    }

    /**
     * interface used as a callback for item phone call
     */
    public interface RecyclerPhoneCallback {
        void onItemClick(int  position);
    }
}

