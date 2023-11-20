package com.example.potholego

import android.os.Parcel
import android.os.Parcelable

data class ProfileData(
    val img: Int,
    val name: String,
    val date: String,
    val vibrationDetected: Boolean,
    val institution: String // 새롭게 추가된 부분
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(img)
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