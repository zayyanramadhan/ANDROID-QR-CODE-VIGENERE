package com.zayyan.ruangbacafasilkom.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.zayyan.ruangbacafasilkom.R
import com.zayyan.ruangbacafasilkom.services.vinegere
import kotlinx.android.synthetic.main.activity_pinjam.*
import java.sql.Timestamp
import java.util.*

class KunjunganPtgActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kunjungan_ptg)

        TimeZone.getTimeZone("Asia/Jakarta")
        val timestamp = Timestamp(System.currentTimeMillis())
        val getwaktu: String = timestamp.getTime().toString()
        val timenow: String = getwaktu.substring(0, 10)


        val encode: String? = vinegere.vinegere("Kunjungan<~>"+timenow, null)

        val barCodeEncoder = BarcodeEncoder()
        val bitmap = barCodeEncoder.encodeBitmap(encode, BarcodeFormat.QR_CODE, 400, 400)
        imv.setImageBitmap(bitmap)
    }
}
