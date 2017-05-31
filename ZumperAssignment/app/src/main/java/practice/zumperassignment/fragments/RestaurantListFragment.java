package practice.zumperassignment.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import practice.zumperassignment.DetailActivity;
import practice.zumperassignment.models.PlaceOrigin;
import practice.zumperassignment.R;
import practice.zumperassignment.adapters.RestaurantAdapter;

/**
 * Created by adao1 on 5/30/2017.
 */

public class RestaurantListFragment extends Fragment
        implements RestaurantAdapter.OnRestaurantItemClickListener, RestaurantAdapter.OnLastResultShownListener{

    private static final String TAG = RestaurantListFragment.class.getSimpleName();
    private RecyclerView restaurantRv;
    private List<PlaceOrigin.Restaurant> restaurantList;
    private OnLastResultShownFragmentListener listener;
    private RestaurantAdapter restaurantAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_list,container,false);
        setRetainInstance(true);
        restaurantRv = (RecyclerView)view.findViewById(R.id.restaurant_rv);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnLastResultShownFragmentListener) context;
    }

    @Override
    public void onRestaurantItemClicked(String restaurantId) {
        Intent detailIntent = new Intent(getContext(),DetailActivity.class);
        detailIntent.putExtra(getString(R.string.detail_intent_key),restaurantId);
        startActivity(detailIntent);
    }

    @Override
    public void onLastResultShown(int lastIndex) {
        listener.onLastResultShownFragment(lastIndex);
    }

    /**
     * Initializes RecyclerView
     */
    private void initRecyclerView(){
        restaurantRv.setLayoutManager(new LinearLayoutManager(getContext()));
        if (restaurantAdapter==null) restaurantAdapter = new RestaurantAdapter(restaurantList,this,this);
        if(restaurantList!=null) restaurantRv.setAdapter(restaurantAdapter);
    }

    /**
     * Called from parent activity to update restaurant list and smoothly add them to adapter
     * @param list
     * @param offset
     */
    public void setFragmentList(List<PlaceOrigin.Restaurant> list, int offset){
        restaurantList = list;
        if(restaurantAdapter!=null) restaurantAdapter.notifyItemRangeInserted(offset+1,list.size());
    }

    /**
     * Interface to communicate with parent activity and tell it that the last result of the recyclerview has been shown
     * So, it can load more items
     */
    public interface OnLastResultShownFragmentListener{
        void onLastResultShownFragment(int lastIndex);
    }
}
