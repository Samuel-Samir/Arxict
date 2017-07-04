package samuel.example.com.arxict;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

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

        private TextView post_title ;
        private TextView post_body ;
        private TextView user ;


        public RecyclerViewAdapterHolder(View itemView) {
            super(itemView);
            user = (TextView) itemView.findViewById(R.id.text_user);
            post_title =(TextView) itemView.findViewById(R.id.post_title);
            post_body =(TextView) itemView.findViewById(R.id.post_body);

        }

    }
}
