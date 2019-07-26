package croom.konekom.`in`.a100pitask.rest

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import croom.konekom.`in`.a100pitask.constant.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/***
 * Created By Kartikey Kumar Srivastava
 */


object ApiClient {
    private var retrofit: Retrofit? = null
    internal var gson = GsonBuilder()
            .setLenient()
            .create()

    val client: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
            }
            return retrofit!!
        }

}
