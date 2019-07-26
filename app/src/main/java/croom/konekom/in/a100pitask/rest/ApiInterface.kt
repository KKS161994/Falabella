package croom.konekom.`in`.a100pitask.rest

import croom.konekom.`in`.a100pitask.model.SuccessResponse
import retrofit2.Call
import retrofit2.http.GET

/***
 * Created By Kartikey Kumar Srivastava
 */

//API to be called
interface ApiInterface {
    @get:GET("getcurrencies")
    val allCurrencies: Call<SuccessResponse>


}
