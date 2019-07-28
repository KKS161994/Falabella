package croom.konekom.`in`.falabella.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Beer")
class Beer(    @PrimaryKey(autoGenerate = true)
               var primid: Int = 0,
               @SerializedName("abv")
               @ColumnInfo(name = "abv")
               var abv: String?,
               @SerializedName("ibu")
               @ColumnInfo(name = "ibu")
               var ibu: String?,
               @SerializedName("id")
               @ColumnInfo(name = "id")
               var id: Int,
               @SerializedName("style")
               @ColumnInfo(name = "style")
               var style: String,
           @SerializedName("name")
           @ColumnInfo(name = "name")
           var name: String,
               @SerializedName("ounces")
               @ColumnInfo(name = "ounces")
               var ounces: Float, var count:Int = 0){

}

fun Beer.convertTo():BeerParcel{
    return BeerParcel(this.abv,this.ibu,this.id,this.style,this.name,this.ounces,this.count)
}
