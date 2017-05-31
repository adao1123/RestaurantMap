package practice.zumperassignment.utilities;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import practice.zumperassignment.providers.PlaceDetailApiService;
import practice.zumperassignment.providers.PlacesApiService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by adao1 on 5/30/2017.
 */

public class ApiManager {

    /**
     * Creates and returns PlacesApiService interface
     * @param baseUrl
     * @return
     */
    public static PlacesApiService createPlaceApiService(String baseUrl){
        return getRetrofit(baseUrl).create(PlacesApiService.class);
    }

    /**
     * Creates and returns PlaceDetailApiService interface
     * @param baseUrl
     * @return
     */
    public static PlaceDetailApiService createPlaceDetailApiService(String baseUrl){
        return getRetrofit(baseUrl).create(PlaceDetailApiService.class);
    }

    /**
     * Returns HTTPLogging to make it much easier to follow and debug retrofit api calls made
     * @return
     */
    private static OkHttpClient getOkHttpClient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    /**
     * Builds and returns Retrofit object built with static baseUrl and GsonConverterFactory
     * @return
     */
    private static Retrofit getRetrofit(String baseUrl){
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient()).build();
    }
}
