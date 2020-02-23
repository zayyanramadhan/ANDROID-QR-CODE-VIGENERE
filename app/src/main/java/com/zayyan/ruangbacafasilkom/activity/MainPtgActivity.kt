package com.zayyan.ruangbacafasilkom.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.google.zxing.integration.android.IntentIntegrator
import com.zayyan.ruangbacafasilkom.R
import com.zayyan.ruangbacafasilkom.services.vinegere
import kotlinx.android.synthetic.main.activity_main_ptg.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.sql.Timestamp
import java.util.*
import kotlin.text.toUpperCase as toUpperCase1


class MainPtgActivity : AppCompatActivity() {
    val CUSTOMIZED_REQUEST_CODE = 0x0000ffff
    val FILENAME = "Login"
    var MENUS: String = ""
    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ptg)

        val bgapp: ImageView = findViewById(R.id.bgapp)
        val book: ImageView = findViewById(R.id.book)
        val textSplash: LinearLayout = findViewById(R.id.textSplash)
        val textHome: LinearLayout = findViewById(R.id.textHome)
        val menu: LinearLayout = findViewById(R.id.menu)
        val bganim = AnimationUtils.loadAnimation(this, R.anim.bganim)
        val bookanim = AnimationUtils.loadAnimation(this, R.anim.bookanim)
        val frombotton = AnimationUtils.loadAnimation(this, R.anim.frombotton)

        val display = windowManager.defaultDisplay
        val size: Point = Point()
        display.getSize(size)
        val width: Int = size.x
        val height: Int = size.y

        //Toast.makeText(this,"lebar : "+width+"\ntinggi : "+height,Toast.LENGTH_LONG).show()

        if (height < 720) {
            bgapp.animate().translationY((-300).toFloat()).setDuration(1500).startDelay = 600
            textSplash.animate().translationY(70F).setDuration(1500).startDelay = 600
        } else if (height > 720 && height < 1350) {
            bgapp.animate().translationY((-1300).toFloat()).setDuration(1500).startDelay = 600
            textSplash.animate().translationY(70F).setDuration(1500).startDelay = 600
        }else if (height > 1350 && height < 2056) {
            bgapp.animate().translationY((-1650).toFloat()).setDuration(1500).startDelay = 600
            textSplash.animate().translationY(70F).setDuration(1500).startDelay = 600
        } else {
            bgapp.animate().translationY((-1700).toFloat()).setDuration(1500).startDelay = 600
            textSplash.animate().translationY(140F).setDuration(1500).startDelay = 600
        }
        book.animate().alpha(0F).setDuration(1500).startDelay = 1200
        textHome.startAnimation(frombotton)
        menu.startAnimation(frombotton)


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
            nama.setText(fnama.toUpperCase1() + " (" + fusername + ")")
            status.setText(fstatus.toUpperCase1())
        } else {
            Toast.makeText(this, "Silakan Login Ulang", Toast.LENGTH_LONG).show()
        }

        menubuku.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val bukuA = Intent(applicationContext, BukuActivity::class.java)
                startActivity(bukuA)
            }
        })
        pinjam.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                MENUS = "pinjam"
                IntentIntegrator(this@MainPtgActivity).setOrientationLocked(false)
                    .setCaptureActivity(
                        QrscanActivity::class.java
                    ).initiateScan()
            }
        })
        kembali.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                MENUS = "kembali"
                IntentIntegrator(this@MainPtgActivity).setOrientationLocked(false)
                    .setCaptureActivity(
                        QrscanActivity::class.java
                    ).initiateScan()
            }
        })
        kunjungan.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val kunjunganA = Intent(applicationContext, KunjunganPtgActivity::class.java)
                startActivity(kunjunganA)
            }
        })

        scanner.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                MENUS = "scanner"
                IntentIntegrator(this@MainPtgActivity).setOrientationLocked(false)
                    .setCaptureActivity(
                        QrscanActivity::class.java
                    ).initiateScan()
            }
        })

        logout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val sdcard = filesDir
                val file = File(sdcard, FILENAME)
                if (file.exists()) {
                    file.delete()
                    val backLogin = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(backLogin)
                    finish()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != CUSTOMIZED_REQUEST_CODE && requestCode != IntentIntegrator.REQUEST_CODE) {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data)
            return
        }
        when (requestCode) {
            CUSTOMIZED_REQUEST_CODE -> {
                Toast.makeText(this, "REQUEST_CODE = $requestCode", Toast.LENGTH_LONG).show()
            }
            else -> {
            }
        }

        val result = IntentIntegrator.parseActivityResult(resultCode, data)

        if (result.contents == null) {
            Log.d("MainActivity", "Cancelled scan")
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            Log.d("MainActivity", "Scanned")
            val resultscan: String = result.contents
            if (MENUS == "pinjam") {
                qrpinjam(resultscan)
                MENUS == ""
            } else if (MENUS == "kembali") {
                qrkembali(resultscan)
                MENUS == ""
            } else if (MENUS == "scanner") {
                qrscan(resultscan)
                MENUS == ""
            } else {
                Toast.makeText(this, "Kesalahan Pada QR Code", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun qrscan(resultscan: String) {
        try {
            var result = vinegere.vinegere(null, resultscan)?.replace("`2*", "\"")
            val parser: Parser = Parser()
            val stringBuilder: StringBuilder = StringBuilder(result!!)
            val json: JsonObject = parser.parse(stringBuilder) as JsonObject
            val judul: String = json.string("judul").toString().replace("@%", "/")
            val nama: String = json.string("nama").toString().replace("@%", "/")
            val npm: String = json.string("npm").toString().replace("@%", "/")
            val kode: String = json.string("kode").toString().replace("@%", "/")
            val kat: String = json.string("kat").toString().replace("@%", "/")

            val scanbuku = Intent(applicationContext, ScanBukuActivity::class.java)
            val myBundle = Bundle()
            myBundle.putString("judul", judul)
            myBundle.putString("npm", npm)
            myBundle.putString("nama", nama)
            myBundle.putString("kode", kode)
            myBundle.putString("kat", kat)
            scanbuku.putExtra("bundle", myBundle)
            startActivity(scanbuku)
        }catch (e: java.lang.Exception){
            Toast.makeText(this, "Kesalahan Pada QR Code", Toast.LENGTH_LONG).show()
        }
    }


    private fun qrpinjam(getresult: String) {
        val parsevin = vinegere.vinegere(null, getresult)
        val getparsevin: String = parsevin.toString()
        val split = getparsevin.split("@$")
        if (split.size == 5) {
            try {
                val pinjam = split[0]
                Toast.makeText(this, pinjam, Toast.LENGTH_LONG).show()
                val status = split[1]
                val username = split[2]
                val nama = split[3]
                val gettime = split[4].toInt()
                TimeZone.getTimeZone("Asia/Jakarta")
                val timestamp = Timestamp(System.currentTimeMillis())
                val getwaktu: String = timestamp.getTime().toString()
                val timenow: Int = getwaktu.substring(0, 10).toInt()
                val cektime = timenow - gettime
                if (cektime <= 300) {
                    if (pinjam == "Pinjam") {
                        val pinjamptg = Intent(applicationContext, PinjamPtgActivity::class.java)
                        val myBundle = Bundle()
                        myBundle.putString("status", status)
                        myBundle.putString("username", username)
                        myBundle.putString("nama", nama)
                        pinjamptg.putExtra("bundle", myBundle)
                        startActivity(pinjamptg)
                    } else {
                        Toast.makeText(this, "Kesalahan Pada QR Code", Toast.LENGTH_LONG).show()
                    }
                } else if (cektime > 300) {
                    Toast.makeText(this, "QR Code Expired, Pinjam ", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(this, "Kesalahan Pada QR Code", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Kesalahan Pada QR Code " + e, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Kesalahan Pada QR Code", Toast.LENGTH_LONG).show()
        }
    }

    private fun qrkembali(getresult: String) {
        val parsevin = vinegere.vinegere(null, getresult)
        val getparsevin: String = parsevin.toString()
        val split = getparsevin.split("@$")
        if (split.size == 5) {
            try {
                val pinjam = split[0]
                Toast.makeText(this, pinjam, Toast.LENGTH_LONG).show()
                val status = split[1]
                val username = split[2]
                val nama = split[3]
                val gettime = split[4].toInt()
                TimeZone.getTimeZone("Asia/Jakarta")
                val timestamp = Timestamp(System.currentTimeMillis())
                val getwaktu: String = timestamp.getTime().toString()
                val timenow: Int = getwaktu.substring(0, 10).toInt()
                val cektime = timenow - gettime
                if (cektime <= 300) {
                    if (pinjam == "Kembali") {
                        val kembaliptg = Intent(applicationContext, KembaliPtgActivity::class.java)
                        val myBundle = Bundle()
                        myBundle.putString("status", status)
                        myBundle.putString("username", username)
                        myBundle.putString("nama", nama)
                        kembaliptg.putExtra("bundle", myBundle)
                        startActivity(kembaliptg)
                    } else {
                        Toast.makeText(this, "Kesalahan Pada QR Code", Toast.LENGTH_LONG).show()
                    }
                } else if (cektime > 300) {
                    Toast.makeText(this, "QR Code Expired, Pinjam ", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(this, "Kesalahan Pada QR Code", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Kesalahan Pada QR Code " + e, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Kesalahan Pada QR Code", Toast.LENGTH_LONG).show()
        }
    }
}
