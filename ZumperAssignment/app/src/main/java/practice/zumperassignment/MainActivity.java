package practice.zumperassignment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import practice.zumperassignment.fragments.RestaurantListFragment;
import practice.zumperassignment.fragments.RestaurantMapFragment;
import practice.zumperassignment.models.PlaceOrigin;
import practice.zumperassignment.utilities.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, Callback<PlaceOrigin>,
        RestaurantListFragment.OnLastResultShownFragmentListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private FragmentTransaction fragmentTransaction;
    private RestaurantListFragment listFragment;
    private RestaurantMapFragment mapFragment;
    private List<PlaceOrigin.Restaurant> restaurantList;
    private String nextPageToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        makeCallIfNetwork();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_list:
                if (listFragment!=null && listFragment.isVisible()) return true;
                changeToList();
                listFragment.setFragmentList(restaurantList,0);
                break;
            case R.id.menu_item_map:
                changeToMap();
                mapFragment.setFragmentList(restaurantList);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onResponse(Call<PlaceOrigin> call, Response<PlaceOrigin> response) {
        PlaceOrigin placeOrigin = response.body();
        nextPageToken = response.body().getNext_page_token();
        for (PlaceOrigin.Restaurant restaurant : placeOrigin.getRestaurants()){
            restaurantList.add(restaurant);
        }
    }

    @Override
    public void onFailure(Call<PlaceOrigin> call, Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onLastResultShownFragment(int lastIndex) {
        if (nextPageToken == null) return;
        makeNextPageApiCall(lastIndex);
    }

    /**
     * Calls method to make Api Call if network is available
     * Else, toast user that there is no network
     */
    private void makeCallIfNetwork(){
        if (hasNetwork()) makePlaceApiCall();
        else Toast.makeText(this,"No Network Connection :(",Toast.LENGTH_LONG).show();
    }

    /**
     * Make an API call in a background thread using Retrofit
     * Ultimately create PlaceApiService with queries and base url from string.xml
     * Currently enters hard-coded queries for San Francisco
     * but can take in a location parameter to dynamically make Api Call with user's current location
     */
    private void makePlaceApiCall(){
        Call<PlaceOrigin> call = ApiManager.createPlaceApiService(getString(R.string.places_base_url))
                .getPlaceOrigin(getString(R.string.places_api_key),getString(R.string.lat_lng_sf),getString(R.string.radius),getString(R.string.type));
        call.enqueue(this);
    }

    /**
     * Make an API call in a background thread using Retrofit
     * Called during infinite scrolling.
     * When user gets to the bottom of the list, this method is ultimately called with the latest index, to smoothly load new views in RecyclerView.
     * It has an anonymous Callback method
     * @param lastIndex
     */
    private void makeNextPageApiCall(final int lastIndex){
        Call<PlaceOrigin> call = ApiManager.createPlaceApiService(getString(R.string.places_base_url))
                .getNextPlaceOrigin(getString(R.string.places_api_key),getString(R.string.lat_lng_sf),getString(R.string.radius),getString(R.string.type), nextPageToken);
        call.enqueue(new Callback<PlaceOrigin>() {
            @Override
            public void onResponse(Call<PlaceOrigin> call, Response<PlaceOrigin> response) {
                PlaceOrigin placeOrigin = response.body();
                nextPageToken = placeOrigin.getNext_page_token();
                for (PlaceOrigin.Restaurant restaurant : placeOrigin.getRestaurants()){
                    restaurantList.add(restaurant);
                }
                listFragment.setFragmentList(restaurantList, lastIndex);
            }

            @Override
            public void onFailure(Call<PlaceOrigin> call, Throwable t) {
                t.printStackTrace();
            }
        }); //enqueue makes call in background instead of main thread like execute would
    }

    /**
     * Initializes Bottom Navigation View and ArrayList
     */
    private void initViews(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        restaurantList = new ArrayList<>();
    }

    /**
     * Initializes Fragment Transaction every time user changes fragments
     * Initializes new RestaurantListFragment only once
     * Inflates fragment into container
     */
    private void changeToList(){
        initFragmentTransaction();
        if (listFragment==null) listFragment = new RestaurantListFragment();
        fragmentTransaction.replace(R.id.fragment_container,listFragment);
        fragmentTransaction.commit();
    }

    /**
     * Initializes Fragment Transaction every time user changes fragments
     * Initializes new RestaurantMapFragment every time user goes to it
     * Inflates fragment into container
     */
    private void changeToMap(){
        initFragmentTransaction();
        mapFragment = new RestaurantMapFragment();
        fragmentTransaction.replace(R.id.fragment_container,mapFragment);
        fragmentTransaction.commit();
    }

    /**
     * Initializes global variable with fragment transaction
     */
    private void initFragmentTransaction(){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
    }

    /**
     * Check if there there is a network connection
     * Returns boolean
     * @return
     */
    private boolean hasNetwork(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) return true;
        Toast.makeText(this,"No Network Connected", Toast.LENGTH_SHORT).show();
        return false;
    }

}
