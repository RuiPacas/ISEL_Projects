package pt.isel.bgg

import androidx.room.TypeConverter


class TypeConverters {
    @TypeConverter
    fun fromString(value: String): Array<String>? {
        return value.split("//,//").toTypedArray()
    }

    @TypeConverter
    fun stringToArray(sArray: Array<String>): String {
        var toReturn = ""
        for (s in sArray) {
            toReturn += s + "//,//"
        }
        return toReturn
    }
}
