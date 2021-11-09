package com.empire.habits.models

import android.os.Parcel
import android.os.Parcelable

data class ItemModel(val word: String?, val description: String?, val hour: String?, val minute: String?, val color: String?, val repeat: Boolean): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(word)
        parcel.writeString(description)
        parcel.writeString(hour)
        parcel.writeString(minute)
        parcel.writeString(color)
        parcel.writeByte(if (repeat) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemModel> {
        override fun createFromParcel(parcel: Parcel): ItemModel {
            return ItemModel(parcel)
        }

        override fun newArray(size: Int): Array<ItemModel?> {
            return arrayOfNulls(size)
        }
    }
}