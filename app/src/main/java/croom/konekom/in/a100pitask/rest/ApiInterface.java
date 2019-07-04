package croom.konekom.in.a100pitask.rest;

import java.util.List;

import croom.konekom.in.a100pitask.model.SuccessResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("getcurrencies")
    Call<SuccessResponse> getAllCurrencies();
}
