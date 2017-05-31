package practice.zumperassignment.providers;

import practice.zumperassignment.models.PlaceOrigin;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by adao1 on 5/30/2017.
 */

public interface PlacesApiService {
    @GET("/maps/api/place/nearbysearch/json")
    Call<PlaceOrigin> getPlaceOrigin(@Query("key") String apiKey,
                                     @Query("location") String location,
                                     @Query("radius") String radius,
                                     @Query("type") String type);

    @GET("/maps/api/place/nearbysearch/json")
    Call<PlaceOrigin> getNextPlaceOrigin(@Query("key") String apiKey,
                                         @Query("location") String location,
                                         @Query("radius") String radius,
                                         @Query("type") String type,
                                         @Query("pagetoken") String nextPageToken);
}
