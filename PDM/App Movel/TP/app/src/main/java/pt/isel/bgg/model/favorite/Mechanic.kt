package pt.isel.bgg.model.favorite

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Mechanic(
    @PrimaryKey val mechanicId: String,
    val mechanicName: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(mechanicId)
        parcel.writeString(mechanicName)
    }

    override fun describeContents(): Int = 0


    companion object CREATOR : Parcelable.Creator<Mechanic> {
        override fun createFromParcel(parcel: Parcel): Mechanic {
            return Mechanic(parcel)
        }

        override fun newArray(size: Int): Array<Mechanic?> {
            return arrayOfNulls(size)
        }
    }
}