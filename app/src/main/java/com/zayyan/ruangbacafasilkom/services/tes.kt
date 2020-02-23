package com.zayyan.ruangbacafasilkom.services

import java.lang.Exception
import java.sql.Timestamp
import java.util.*

fun main() {
    TimeZone.getTimeZone("Asia/Jakarta")
    val timestamp = Timestamp(System.currentTimeMillis())
    val getwaktu: String = timestamp.getTime().toString()
    val tes1: String ="gysdgysd"
    val tes: String = getwaktu.substring(0, 10)
    val split = tes1.split("<~>")
    try {

        print(split[1])
    }catch (e: Exception){
        print(e)
    }
}