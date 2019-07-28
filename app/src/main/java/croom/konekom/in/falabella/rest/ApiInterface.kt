package croom.konekom.`in`.falabella.rest

import croom.konekom.`in`.falabella.model.Beer
import retrofit2.Call
import retrofit2.http.GET

/***
 * Created By Kartikey Kumar Srivastava
 */

//API to be called
interface ApiInterface {

    @get:GET("beercraft")
    val allBeers: Call<List<Beer>>

}
