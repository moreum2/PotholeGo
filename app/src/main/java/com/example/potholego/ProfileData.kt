// ProfileData.kt
package com.example.potholego

import android.os.Parcel
import android.os.Parcelable

data class ProfileData(
    val imgUrl: String,
    val name: String,
    val date: String,
    val vibrationDetected: Boolean,
    val institution: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imgUrl)
        parcel.writeString(name)
        parcel.writeString(date)
        parcel.writeByte(if (vibrationDetected) 1 else 0)
        parcel.writeString(institution)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProfileData> {
        override fun createFromParcel(parcel: Parcel): ProfileData {
            return ProfileData(parcel)
        }

        override fun newArray(size: Int): Array<ProfileData?> {
            return arrayOfNulls(size)
        }
    }
}
