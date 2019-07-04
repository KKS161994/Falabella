package croom.konekom.in.a100pitask.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import croom.konekom.in.a100pitask.constant.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/***
 * Created By Kartikey Kumar Srivastava
 */


public class ApiClient {
    private static Retrofit retrofit;
    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}
