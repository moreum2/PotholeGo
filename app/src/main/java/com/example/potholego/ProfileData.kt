package com.example.potholego

import android.os.Parcel
import android.os.Parcelable

data class ProfileData(val img: Int, val name: String, val date: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(img)
        parcel.writeString(name)
        parcel.writeString(date)
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
