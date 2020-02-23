package com.zayyan.ruangbacafasilkom.restApiRetrofit.models


import com.google.gson.annotations.SerializedName

data class AllBuku(
    val code: String,
    val items: List<ItemXX>,
    val message: String
)