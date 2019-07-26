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
class Currency(@field:SerializedName("Currency")
               @field:ColumnInfo(name = "Currency")
               var currency: String?, @field:SerializedName("CurrencyLong")
               @field:ColumnInfo(name = "CurrencyLong")
               var currencyLong: String?, @field:SerializedName("MinConfirmation")
               var minConfirmation: String?, @field:SerializedName("TxFee")
               @field:ColumnInfo(name = "TxFee")
               var txFee: Double, @field:SerializedName("IsActive")
               var isActive: Boolean, @field:SerializedName("IsRestricted")
               var isRestricted: Boolean, @field:SerializedName("CoinType")
               var coinType: String?, @field:SerializedName("BaseAddress")
               var baseAddress: String?, @field:SerializedName("Notice")
               var notice: String?) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
