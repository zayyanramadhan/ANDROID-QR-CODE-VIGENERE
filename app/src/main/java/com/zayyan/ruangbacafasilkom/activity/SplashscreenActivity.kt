package com.zayyan.ruangbacafasilkom.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.zayyan.ruangbacafasilkom.R
import com.zayyan.ruangbacafasilkom.services.vinegere
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException


class SplashscreenActivity : AppCompatActivity() {
    val FILENAME = "Login"
    var getstatus: String = ""
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        val display = windowManager.defaultDisplay
        val size: Point = Point()
        display.getSize(size)
        val width: Int = size.x
        val height: Int = size.y
        //Toast.makeText(this,"lebar : "+width+"\ntinggi : "+height,Toast.LENGTH_LONG).show()

        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_PHONE_STATE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) { // ask permission here
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                101
            )
        } else {
            val mngr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val IMEI: String = mngr.deviceId
            //Toast.makeText(this,"imei : "+IMEI,Toast.LENGTH_LONG).show()

        }

        Handler().postDelayed(Runnable {
            if (islogin()) {
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
                    val decode: String? = vinegere.vinegere(null,text.toString())
                    val split = decode?.split("<~>")
                    val fstatus: String = split?.get(1) ?: toString()
                    getstatus = fstatus
                }
                if (getstatus == "petugas"){
                    val intent = Intent(this, MainPtgActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }

    fun islogin(): Boolean {
        val sdcard = filesDir
        val file = File(sdcard, FILENAME)
        return file.exists()
    }

}
