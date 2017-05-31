package practice.zumperassignment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import practice.zumperassignment.adapters.ReviewAdapter;
import practice.zumperassignment.models.PlaceDetailOrigin;
import practice.zumperassignment.utilities.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity
        implements Callback<PlaceDetailOrigin>, View.OnClickListener{

    private static final String TAG = DetailActivity.class.getSimpleName();
    private TextView phoneTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        makeCallIfNetwork();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detail_phonenumber:
                if (phoneTv==null) return;
                makePhoneCall(phoneTv.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void onResponse(Call<PlaceDetailOrigin> call, Response<PlaceDetailOrigin> response) {
        initViews(response.body().getRestaurantDetail());
    }

    @Override
    public void onFailure(Call<PlaceDetailOrigin> call, Throwable t) {
        t.printStackTrace();
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
     * CAlls methods to initialize and display views with object data
     * @param restaurantDetail
     */
    private void initViews(PlaceDetailOrigin.RestaurantDetail restaurantDetail){
        displayNameTv(restaurantDetail);
        displayAddressTv(restaurantDetail);
        displayPhoneTv(restaurantDetail);
        displayRatingBar(restaurantDetail);
        displayReviewsRv(restaurantDetail);
    }

    /**
     * Initializes TextView and sets text with object data
     * @param restaurantDetail
     */
    private void displayNameTv(PlaceDetailOrigin.RestaurantDetail restaurantDetail){
        TextView nameTv = (TextView)findViewById(R.id.detail_name);
        nameTv.setText(restaurantDetail.getName());
    }

    /**
     * Initializes TextView and sets text with object data
     * @param restaurantDetail
     */
    private void displayAddressTv(PlaceDetailOrigin.RestaurantDetail restaurantDetail){
        TextView addressTv = (TextView)findViewById(R.id.detail_address);
        addressTv.setText(restaurantDetail.getFormatted_address());
    }

    /**
     * Initializes TextView and sets text with object data
     * Set on click listener to allow user to make phone call
     * @param restaurantDetail
     */
    private void displayPhoneTv(PlaceDetailOrigin.RestaurantDetail restaurantDetail){
        phoneTv = (TextView)findViewById(R.id.detail_phonenumber);
        phoneTv.setText(restaurantDetail.getFormatted_phone_number());
        phoneTv.setOnClickListener(this);
    }

    /**
     * Initializes RatingBar and sets rating with object data
     * @param restaurantDetail
     */
    private void displayRatingBar(PlaceDetailOrigin.RestaurantDetail restaurantDetail){
        RatingBar ratingBar = (RatingBar)findViewById(R.id.detail_rating);
        ratingBar.setRating(restaurantDetail.getRating());
    }

    /**
     * Initializes RecyclerView and sets layoutManager and Adapter with object data
     * @param restaurantDetail
     */
    private void displayReviewsRv(PlaceDetailOrigin.RestaurantDetail restaurantDetail){
        RecyclerView reviewRv = (RecyclerView)findViewById(R.id.detail_review_rv);
        reviewRv.setLayoutManager(new LinearLayoutManager(this));
        reviewRv.setAdapter(new ReviewAdapter(Arrays.asList(restaurantDetail.getReviews())));
    }

    /**
     * Make an API call in a background thread using Retrofit
     * Ultimately create PlaceDetailApiService
     * Calls method to get placeId query from intent
     */
    private void makePlaceApiCall(){
        Call<PlaceDetailOrigin> call = ApiManager.createPlaceDetailApiService(getString(R.string.places_base_url))
                .getPlaceDetailOrigin(getString(R.string.places_api_key),getPlaceIdFromIntent());
        call.enqueue(this); //enqueue makes call in background instead of main thread like execute would
    }

    /**
     * Returns placeId String from intent
     * @return
     */
    private String getPlaceIdFromIntent(){
        return getIntent().getStringExtra(getString(R.string.detail_intent_key));
    }

    /**
     * Start implicit intent to make phone call
     * @param phoneNumber
     */
    private void makePhoneCall(String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
        startActivity(intent);
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
