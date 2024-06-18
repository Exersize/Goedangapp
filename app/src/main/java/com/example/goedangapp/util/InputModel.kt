package com.example.goedangapp.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class InputModel(
val jenis :String,
val quantity : Int,
val iconurl :String,
val price :Double,
val weight :String,
) : Parcelable
{
    val totalPrice: Double
        get()= quantity*price
}

