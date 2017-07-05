package samuel.example.com.arxict.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import samuel.example.com.arxict.R;
import samuel.example.com.arxict.model.PostContent;

/**
 * Created by samuel on 7/4/2017.
 */

public class PostsAdapter  extends RecyclerView.Adapter<PostsAdapter.RecyclerViewAdapterHolder>{

    List<PostContent> postContentList ;
    public void setApiResponse (List<PostContent> postContentList )
    {
        this.postContentList =postContentList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        int layoutPhotoItem = R.layout.post_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutPhotoItem ,parent ,shouldAttachToParentImmediately);
        return new RecyclerViewAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterHolder holder, int position) {
        PostContent postContent = postContentList.get(position);
        holder.user.setText("User "+String.valueOf(postContent.getUserId()));
        holder.post_title.setText(postContent.getTitle());
        holder.post_body.setText(postContent.getBody());

    }

    @Override
    public int getItemCount() {
        if(postContentList== null)
            return 0;
        else
            return postContentList.size();
    }

    public class RecyclerViewAdapterHolder extends RecyclerView.ViewHolder {

         @BindView(R.id.post_title)
         TextView post_title ;

         @BindView(R.id.post_body)
         TextView post_body ;

        @BindView(R.id.text_user)
        TextView user ;


        public RecyclerViewAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
            /*
            user = (TextView) itemView.findViewById(R.id.text_user);
            post_title =(TextView) itemView.findViewById(R.id.post_title);
            post_body =(TextView) itemView.findViewById(R.id.post_body);*/

        }

    }
}
