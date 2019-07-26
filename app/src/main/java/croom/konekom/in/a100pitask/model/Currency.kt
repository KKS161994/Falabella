package croom.konekom.`in`.a100pitask.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import com.google.gson.annotations.SerializedName

/***
 * Created By Kartikey Kumar Srivastava
 */

//Currency model both as a table as well as model for retrofit
@Entity(tableName = "Currency")
class Currency(@SerializedName("Currency")
               @ColumnInfo(name = "Currency")
               var currency: String?,
               @SerializedName("CurrencyLong")
               @ColumnInfo(name = "CurrencyLong")
               var currencyLong: String?,
               @SerializedName("MinConfirmation")
               var minConfirmation: String?,
               @SerializedName("TxFee")
               @ColumnInfo(name = "TxFee")
               var txFee: Double,
               @SerializedName("IsActive")
               var isActive: Boolean,
               @SerializedName("IsRestricted")
               var isRestricted: Boolean,
               @SerializedName("CoinType")
               var coinType: String?,
               @SerializedName("BaseAddress")
               var baseAddress: String?,
               @SerializedName("Notice")
               var notice: String?) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
