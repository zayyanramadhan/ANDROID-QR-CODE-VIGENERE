package com.zayyan.ruangbacafasilkom.restApiRetrofit.models


import com.google.gson.annotations.SerializedName

data class Progdi(
    val code: String,
    val items: List<ItemX>,
    val message: String
)