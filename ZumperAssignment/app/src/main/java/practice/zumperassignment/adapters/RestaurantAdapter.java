package practice.zumperassignment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import practice.zumperassignment.models.PlaceOrigin;
import practice.zumperassignment.R;

/**
 * Created by adao1 on 5/30/2017.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PlaceOrigin.Restaurant> restaurants;
    private Context context;
    private OnRestaurantItemClickListener listener;
    private OnLastResultShownListener lastResultShownListener;

    public RestaurantAdapter(List<PlaceOrigin.Restaurant> restaurants,  OnRestaurantItemClickListener listener, OnLastResultShownListener lastResultShownListener) {
        this.restaurants = restaurants;
        this.listener = listener;
        this.lastResultShownListener = lastResultShownListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_restaurant,parent,false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PlaceOrigin.Restaurant restaurant = restaurants.get(position);
        RestaurantViewHolder viewHolder = (RestaurantViewHolder)holder;
        viewHolder.nameTv.setText(restaurant.getName());
        viewHolder.ratingTv.setRating(restaurant.getRating());
        Picasso.with(context).load(restaurant.getIcon()).into(viewHolder.photoIv);
        viewHolder.bind(listener,restaurant.getPlace_id());
        if (position>=restaurants.size()-1) lastResultShownListener.onLastResultShown(position);
    }

    @Override
    public int getItemCount() {
        return (restaurants == null) ? 0 : restaurants.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTv;
        public RatingBar ratingTv;
        public ImageView photoIv;
        public RestaurantViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView)itemView.findViewById(R.id.item_name);
            ratingTv = (RatingBar)itemView.findViewById(R.id.item_rating);
            photoIv = (ImageView)itemView.findViewById(R.id.item_image);
        }
        public void bind(final OnRestaurantItemClickListener listener, final String restaurantId){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onRestaurantItemClicked(restaurantId);
                }
            });
        }
    }

    /**
     * Passes onClick event
     */
    public interface OnRestaurantItemClickListener{
        void onRestaurantItemClicked(String restaurantId);
    }

    /**
     * Tell the parent fragment/activity that last result is shown so load some more
     */
    public interface OnLastResultShownListener{
        void onLastResultShown(int lastIndex);
    }
}
