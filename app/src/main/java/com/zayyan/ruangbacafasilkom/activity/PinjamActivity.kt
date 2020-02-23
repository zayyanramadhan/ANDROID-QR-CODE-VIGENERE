package com.zayyan.ruangbacafasilkom.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.google.zxing.BarcodeFormat
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.zayyan.ruangbacafasilkom.R
import com.zayyan.ruangbacafasilkom.services.vinegere
import kotlinx.android.synthetic.main.activity_pinjam.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.sql.Timestamp
import java.util.*

class PinjamActivity() : AppCompatActivity() {
    val FILENAME = "Login"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pinjam)

        val sdcard = filesDir
        val file = File(sdcard, FILENAME)
        if (file.exists()) {
            val text = StringBuilder()
            try {
                val br = BufferedReader(FileReader(file))
                var line = br.readLine()
                while (line != null) {
                    text.append(line)
                    line = br.readLine()
                }
            } catch (e: IOException) {
                println("Error" + e.message)
            }
            val decode: String? = vinegere.vinegere(null, text.toString())
            val split = decode?.split("<~>")
            val fnama: String = split?.get(0) ?: toString()
            val fstatus: String = split?.get(1) ?: toString()
            val fusername: String = split?.get(2) ?: toString()

            status.setText(fstatus.toUpperCase())
            nama.setText(fnama.toUpperCase())
            username.setText(fusername.toUpperCase())
            TimeZone.getTimeZone("Asia/Jakarta")
            val timestamp = Timestamp(System.currentTimeMillis())
            val getwaktu: String = timestamp.getTime().toString()
            val timenow: String = getwaktu.substring(0, 10)


            val encode: String? = vinegere.vinegere("Pinjam@$"+fstatus+"@$"+fusername+"@$"+fnama+"@$"+timenow, null)

            val barCodeEncoder = BarcodeEncoder()
            val bitmap = barCodeEncoder.encodeBitmap(encode, BarcodeFormat.QR_CODE, 400, 400)
            imv.setImageBitmap(bitmap)
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}