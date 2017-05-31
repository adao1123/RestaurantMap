package practice.zumperassignment.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import practice.zumperassignment.DetailActivity;
import practice.zumperassignment.models.PlaceOrigin;
import practice.zumperassignment.R;

/**
 * Created by adao1 on 5/30/2017.
 */

public class RestaurantMapFragment extends Fragment
        implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.InfoWindowAdapter{

    private static final String TAG = RestaurantMapFragment.class.getSimpleName();
    private GoogleMap map;
    private SupportMapFragment mapFragment;
    private List<PlaceOrigin.Restaurant> restaurantList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_map,container,false);
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initGoogleMaps();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (restaurantList!=null) setGoogleMapCameraPosition();
        if (restaurantList!=null) plotStoreLocation();
        map.setOnInfoWindowClickListener(this);

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Context context = getActivity().getApplicationContext();
        return getInfoLayout(context,marker);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent detailIntent = new Intent(getContext(),DetailActivity.class);
        detailIntent.putExtra(getString(R.string.detail_intent_key),marker.getSnippet());
        startActivity(detailIntent);
    }

    /**
     * Initialize Google Maps
     */
    private void initGoogleMaps(){
        if (map == null){
            mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map);
            mapFragment.getMapAsync(this);
        }
    }

    /**
     * Sets Map Camera Position and animation
     */
    private void setGoogleMapCameraPosition(){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(Double.parseDouble(getString(R.string.lat_sf)), Double.parseDouble(getString(R.string.lng_sf)))).zoom(15).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    /**
     * Plots all of the restaurants on the map from object data
     */
    private void plotStoreLocation(){
        setGoogleMarkerInfoTab();
        for (PlaceOrigin.Restaurant restaurant : restaurantList) {
            MarkerOptions marker = new MarkerOptions()
                    .position(new LatLng(restaurant.getGeometry().getLocation().getLat(), restaurant.getGeometry().getLocation().getLng()))
                    .title(restaurant.getName())
                    .snippet(String.valueOf(restaurant.getPlace_id()));
            marker.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
            map.addMarker(marker);
        }
    }

    /**
     * Sets Info Window Adapter
     */
    private void setGoogleMarkerInfoTab() {
        map.setInfoWindowAdapter(this);
    }

    /**
     * Makes Linear Layout for info window
     * @param context
     * @param marker
     * @return
     */
    private LinearLayout getInfoLayout(Context context, Marker marker){
        LinearLayout info = new LinearLayout(context);
        info.setOrientation(LinearLayout.VERTICAL);
        info.addView(getTitleTv(context,marker));
        info.addView(getSnippetTv(context));
        return info;
    }

    /**
     * Initializes Title textview and its properties
     * @param context
     * @param marker
     * @return
     */
    private TextView getTitleTv(Context context, Marker marker){
        TextView title = new TextView(context);
        title.setTextColor(Color.BLACK);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(null, Typeface.BOLD);
        title.setText(marker.getTitle());
        return title;
    }

    /**
     * Initiliazes snippet view that holds place_id
     * @param context
     * @return
     */
    private TextView getSnippetTv(Context context){
        TextView snippet = new TextView(context);
        snippet.setTextColor(Color.GRAY);
        snippet.setVisibility(View.GONE);
        return snippet;
    }

    /**
     * Called from parent activity to update restaurant list and plot points
     * @param list
     */
    public void setFragmentList(List<PlaceOrigin.Restaurant> list){
        restaurantList = list;
        if (map!=null) plotStoreLocation();
    }
}
