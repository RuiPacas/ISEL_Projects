package pt.isel.bgg.dto

import android.os.Parcel
import android.os.Parcelable

data class GameDto(
    val id: String?,
    val name: String?,
    val year_published: Int,
    val min_players: Int,
    val max_players: Int,
    val min_age: Int,
    val description: String?,
    val image_url: String?,
    val primary_publisher: String?,
    val artists: Array<String>?,
    val average_user_rating: Double,
    val rules_url: String?,
    val official_url: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArray(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeInt(year_published)
        parcel.writeInt(min_players)
        parcel.writeInt(max_players)
        parcel.writeInt(min_age)
        parcel.writeString(description)
        parcel.writeString(image_url)
        parcel.writeString(primary_publisher)
        parcel.writeStringArray(artists)
        parcel.writeDouble(average_user_rating)
        parcel.writeString(rules_url)
        parcel.writeString(official_url)
    }

    override fun describeContents(): Int = 0


    companion object CREATOR : Parcelable.Creator<GameDto> {
        override fun createFromParcel(parcel: Parcel): GameDto {
            return GameDto(parcel)
        }

        override fun newArray(size: Int): Array<GameDto?> {
            return arrayOfNulls(size)
        }
    }


}