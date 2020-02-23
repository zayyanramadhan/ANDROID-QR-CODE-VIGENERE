package com.zayyan.ruangbacafasilkom.restApiRetrofit.models


import com.google.gson.annotations.SerializedName

data class Login(
    val code: String,
    val message: String,
    val nama: String,
    val status: String,
    val username: String
)