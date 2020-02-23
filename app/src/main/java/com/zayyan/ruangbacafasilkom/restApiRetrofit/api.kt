package com.zayyan.ruangbacafasilkom.restApiRetrofit


import com.zayyan.ruangbacafasilkom.restApiRetrofit.models.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @GET("getallfakultas")
    fun getfakultas(): Call<Fakultas>

    @FormUrlEncoded
    @POST("getallprogdi")
    fun getprogdi(
        @Field("progdi") progdi: String
    ): Call<Progdi>

    @FormUrlEncoded
    @POST("regmahasiswa")
    fun regmahasiswa(
        @Field("nama") nama: String,
        @Field("npm") npm: String,
        @Field("alamat") alamat: String,
        @Field("no_telp") no_telp: String,
        @Field("email") email: String,
        @Field("prog") prog: String,
        @Field("pass") pass: String,
        @Field("imei") imei: String
    ): Call<RegMahasiswa>

    @FormUrlEncoded
    @POST("regdosen")
    fun regdosen(
        @Field("nama") nama: String,
        @Field("nip") npm: String,
        @Field("alamat") alamat: String,
        @Field("no_telp") no_telp: String,
        @Field("email") email: String,
        @Field("prog") prog: String,
        @Field("pass") pass: String,
        @Field("imei") imei: String
    ): Call<RegDosen>

    @FormUrlEncoded
    @POST("loginapp")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("imei") imei: String
    ): Call<Login>

    @GET("getbukuall")
    fun getbukuall(): Call<AllBuku>

    @FormUrlEncoded
    @POST("riwayatpinjam")
    fun getriwayatpinjam(
        @Field("username") username: String
    ): Call<RiwayatPinjam>

    @FormUrlEncoded
    @POST("apipinjam")
    fun apipinjam(
        @Field("username") username: String,
        @Field("buku") buku: String
    ): Call<RiwayatPinjamNew>

    @FormUrlEncoded
    @POST("apikembali")
    fun apikembali(
        @Field("username") username: String,
        @Field("buku") buku: String
    ): Call<RiwayatPinjamNew>

    @FormUrlEncoded
    @POST("apikunjungan")
    fun apikunjungan(
        @Field("username") username: String
    ): Call<Kunjungan>

    @FormUrlEncoded
    @POST("apikunjunganout")
    fun apikunjunganout(
        @Field("username") username: String
    ): Call<Outkunjungan>

    @FormUrlEncoded
    @POST("apicekpinjam")
    fun apicekpinjam(
        @Field("username") username: String
    ): Call<Cekpinjam>
}