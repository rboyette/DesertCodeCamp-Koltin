package com.rachelleboyette.desertcodecampdemo.model

import android.os.Parcel
import android.os.Parcelable

class Cat(val weight: Weight = Weight(),
          val id: String = "",
          val name: String = "",
          val temperament: String = "",
          val origin: String = "",
          val description: String = "",
          val lifeSpan: String = "",
          val adaptability: Int = 0,
          val alternativeNames: String? = "",
          val affectionLevel: Int = 0,
          val childFriendly: Int = 0,
          val dogFriendly: Int = 0,
          val energyLevel: Int = 0,
          val grooming: Int = 0,
          val healthIssues: Int = 0,
          val intelligence: Int = 0,
          val sheddingLevel: Int = 0,
          val socialNeeds: Int = 0,
          val vocalization: Int = 0,
          val wikipediaUrl: String? = "") : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readParcelable<Weight>(Weight::class.java.classLoader) ?: Weight(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt() ,
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(weight, flags)
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(temperament)
        parcel.writeString(origin)
        parcel.writeString(description)
        parcel.writeString(lifeSpan)
        parcel.writeInt(adaptability)
        parcel.writeString(alternativeNames)
        parcel.writeInt(affectionLevel)
        parcel.writeInt(childFriendly)
        parcel.writeInt(dogFriendly)
        parcel.writeInt(energyLevel)
        parcel.writeInt(grooming)
        parcel.writeInt(healthIssues)
        parcel.writeInt(intelligence)
        parcel.writeInt(sheddingLevel)
        parcel.writeInt(socialNeeds)
        parcel.writeInt(vocalization)
        parcel.writeString(wikipediaUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cat> {
        override fun createFromParcel(parcel: Parcel): Cat {
            return Cat(parcel)
        }

        override fun newArray(size: Int): Array<Cat?> {
            return arrayOfNulls(size)
        }
    }
}