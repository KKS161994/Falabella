package croom.konekom.`in`.falabella.model

import android.os.Parcel
import android.os.Parcelable



class BeerParcel(var abv: String?,
               var ibu: String?,
               var id: Int,
               var style: String,
               var name: String,
               var ounces: Float, val count:Int = 0): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readFloat(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(abv)
        parcel.writeString(ibu)
        parcel.writeInt(id)
        parcel.writeString(style)
        parcel.writeString(name)
        parcel.writeFloat(ounces)
        parcel.writeInt(count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BeerParcel> {
        override fun createFromParcel(parcel: Parcel): BeerParcel {
            return BeerParcel(parcel)
        }

        override fun newArray(size: Int): Array<BeerParcel?> {
            return arrayOfNulls(size)
        }
    }
}