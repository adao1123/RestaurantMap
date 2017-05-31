package practice.zumperassignment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import practice.zumperassignment.models.PlaceDetailOrigin;
import practice.zumperassignment.R;

/**
 * Created by adao1 on 5/31/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PlaceDetailOrigin.RestaurantDetail.Review> reviews;
    private Context context;

    public ReviewAdapter(List<PlaceDetailOrigin.RestaurantDetail.Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_review,parent,false);
        return new ReviewViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PlaceDetailOrigin.RestaurantDetail.Review review = reviews.get(position);
        ReviewViewHolder viewHolder = (ReviewViewHolder)holder;
        if (review.getAuthor_name()!=null) viewHolder.authorTv.setText(review.getAuthor_name());
        if (review.getText()!=null) viewHolder.textTv.setText(review.getText());
        if (review.getProfile_photo_url()!=null) Picasso.with(context).load(review.getProfile_photo_url()).into(viewHolder.profileIv);
    }

    @Override
    public int getItemCount() {
        return (reviews == null) ? 0 : reviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{
        public ImageView profileIv;
        public TextView authorTv;
        public TextView textTv;
        public ReviewViewHolder(View itemView) {
            super(itemView);
            profileIv = (ImageView)itemView.findViewById(R.id.item_review_img);
            authorTv = (TextView)itemView.findViewById(R.id.item_review_author);
            textTv = (TextView)itemView.findViewById(R.id.item_review_text);
        }
    }
}
