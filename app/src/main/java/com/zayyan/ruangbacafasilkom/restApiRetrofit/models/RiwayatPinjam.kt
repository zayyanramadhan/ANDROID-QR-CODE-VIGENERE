package com.zayyan.ruangbacafasilkom.restApiRetrofit.models


import com.google.gson.annotations.SerializedName

data class RiwayatPinjam(
    val code: String,
    val items: List<ItemXXX>,
    val message: String
)