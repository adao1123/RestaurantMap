package practice.zumperassignment.providers;

import practice.zumperassignment.models.PlaceDetailOrigin;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by adao1 on 5/31/2017.
 */

public interface PlaceDetailApiService {
    @GET("/maps/api/place/details/json")
    Call<PlaceDetailOrigin> getPlaceDetailOrigin(@Query("key") String apiKey,
                                                 @Query("placeid") String placeid);
}
