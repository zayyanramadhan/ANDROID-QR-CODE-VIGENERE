package com.zayyan.ruangbacafasilkom.restApiRetrofit.models


import com.google.gson.annotations.SerializedName

data class ItemXXX(
    @SerializedName("kode_buku")
    val kodeBuku: String,
    val nama: String,
    val status: String,
    @SerializedName("tgl_pinjam")
    val tglPinjam: String,
    val denda: String,
    val bayar_denda: String
)