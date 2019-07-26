package croom.konekom.`in`.a100pitask.model

import com.google.gson.annotations.SerializedName

/***
 * Created By Kartikey Kumar Srivastava
 */

//Response Model that will be received for getCurrencies Call
data class SuccessResponse(@field:SerializedName("success")
                      var isSuccess: Boolean, @field:SerializedName("message")
                      var message: String?,@SerializedName("result")
val currencies: List<Currency>)