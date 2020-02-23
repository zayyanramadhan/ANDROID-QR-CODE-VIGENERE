package com.zayyan.ruangbacafasilkom.activity

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
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
import androidx.core.app.NotificationCompat
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.google.zxing.integration.android.IntentIntegrator
import com.zayyan.ruangbacafasilkom.R
import com.zayyan.ruangbacafasilkom.restApiRetrofit.RetrofitClient
import com.zayyan.ruangbacafasilkom.restApiRetrofit.models.Cekpinjam
import com.zayyan.ruangbacafasilkom.restApiRetrofit.models.Kunjungan
import com.zayyan.ruangbacafasilkom.restApiRetrofit.models.Login
import com.zayyan.ruangbacafasilkom.restApiRetrofit.models.Outkunjungan
import com.zayyan.ruangbacafasilkom.services.vinegere
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.sql.Timestamp
import java.util.*
import kotlin.text.toUpperCase as toUpperCase1


class MainActivity : AppCompatActivity() {
    val CUSTOMIZED_REQUEST_CODE = 0x0000ffff
    val FILENAME = "Login"
    var USERNAME: String = ""
    var STAT: String = ""
    var NAMA: String = ""
    var MENUS: String = ""
    var DENDA: String = ""
    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            USERNAME = fusername
            STAT = fstatus
            NAMA = fnama
            nama.setText(fnama.toUpperCase1() + " (" + fusername + ")")
            status.setText(fstatus.toUpperCase1())
        } else {
            Toast.makeText(this, "Silakan Login Ulang ", Toast.LENGTH_LONG).show()
        }

        menubuku.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val pinjampjg = Intent(applicationContext, PinjamPJGActivity::class.java)
                val myBundle = Bundle()
                myBundle.putString("status", STAT)
                myBundle.putString("username", USERNAME)
                myBundle.putString("nama", NAMA)
                pinjampjg.putExtra("bundle", myBundle)
                startActivity(pinjampjg)
            }
        })
        pinjam.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                    val pinjamA = Intent(applicationContext, PinjamActivity::class.java)
                    startActivity(pinjamA)
            }
        })
        kembali.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val pinjamA = Intent(applicationContext, KembaliActivity::class.java)
                startActivity(pinjamA)
            }
        })

        kunjungan.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                MENUS = "kunjungan"
                IntentIntegrator(this@MainActivity).setOrientationLocked(false).setCaptureActivity(
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

        scanner.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                MENUS = "scanner"
                IntentIntegrator(this@MainActivity).setOrientationLocked(false)
                    .setCaptureActivity(
                        QrscanActivity::class.java
                    ).initiateScan()
            }
        })

        RetrofitClient.instance.apicekpinjam(
            USERNAME
        ).enqueue(object : Callback<Cekpinjam> {
            override fun onResponse(
                call: Call<Cekpinjam>,
                response: Response<Cekpinjam>
            ) {
                val pesan: String? = response.body()?.message
                if (pesan == "Denda Pinjam Buku") {
                    val denda: String? = response.body()?.items
                    Toast.makeText(this@MainActivity, "Segera Kembalikan Buku : "+denda, Toast.LENGTH_LONG).show()
                    showNotification("Segera Kembalikan Buku!","Judul: "+denda.toString())
                    DENDA = pesan.toString()
                }
            }

            override fun onFailure(call: Call<Cekpinjam>, t: Throwable) {

                Toast.makeText(this@MainActivity, "Internet Bermasalah", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun showNotification(title: String, message: String) {
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel("YOUR_CHANNEL_ID",
                "YOUR_CHANNEL_NAME",
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "YOUR_NOTIFICATION_CHANNEL_DISCRIPTION"
            mNotificationManager.createNotificationChannel(channel)
        }
        val mBuilder = NotificationCompat.Builder(applicationContext, "YOUR_CHANNEL_ID")
            .setSmallIcon(R.mipmap.ic_launcher) // notification icon
            .setContentTitle(title) // title for notification
            .setContentText(message)// message for notification
            .setAutoCancel(true) // clear notification after click
        val intent = Intent(applicationContext, MainActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        mBuilder.setContentIntent(pi)
        mNotificationManager.notify(0, mBuilder.build())
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
            if (MENUS == "kunjungan") {
                kunjungan(resultscan)
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


    private fun kunjungan(getresult: String) {
        val parsevin = vinegere.vinegere(null, getresult)
        val getparsevin: String = parsevin.toString()
        val split = getparsevin.split("<~>")
        if (split.size == 2) {
            try {
                val kunjungan = split[0]
                val gettime = split[1].toInt()
                TimeZone.getTimeZone("Asia/Jakarta")
                val timestamp = Timestamp(System.currentTimeMillis())
                val getwaktu: String = timestamp.getTime().toString()
                val timenow: Int = getwaktu.substring(0, 10).toInt()
                val cektime = timenow - gettime
                if (cektime <= 300) {
                    if (kunjungan == "Kunjungan") {
                        RetrofitClient.instance.apikunjungan(
                            USERNAME
                        ).enqueue(object : Callback<Kunjungan> {
                            override fun onResponse(
                                call: Call<Kunjungan>,
                                response: Response<Kunjungan>
                            ) {
                                val pesan: String? = response.body()?.message
                                val code: String? = response.body()?.code
                                if (code != "222") {
                                    Toast.makeText(this@MainActivity, pesan, Toast.LENGTH_LONG)
                                        .show()
                                }else{
                                    outkunjungan(getresult)
                                }
                            }
                            override fun onFailure(call: Call<Kunjungan>, t: Throwable) {
                                Toast.makeText(this@MainActivity, "Internet Bermasalah", Toast.LENGTH_LONG).show()
                            }
                        })
                    } else {
                        Toast.makeText(this, "Kesalahan Pada QR Code", Toast.LENGTH_LONG).show()
                    }
                } else if (cektime > 300) {
                    Toast.makeText(this, "QR Code Expired, Kunjungan ", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(this, "Kesalahan Pada QR Code", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Kesalahan Pada QR Code ", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Kesalahan Pada QR Code", Toast.LENGTH_LONG).show()
        }
    }

    private fun outkunjungan(getresult: String) {
        val parsevin = vinegere.vinegere(null, getresult)
        val getparsevin: String = parsevin.toString()
        val split = getparsevin.split("<~>")
        if (split.size == 2) {
            try {
                val kunjungan = split[0]
                val gettime = split[1].toInt()
                TimeZone.getTimeZone("Asia/Jakarta")
                val timestamp = Timestamp(System.currentTimeMillis())
                val getwaktu: String = timestamp.getTime().toString()
                val timenow: Int = getwaktu.substring(0, 10).toInt()
                val cektime = timenow - gettime
                if (cektime <= 300) {
                    if (kunjungan == "Kunjungan") {
                        RetrofitClient.instance.apikunjunganout(
                            USERNAME
                        ).enqueue(object : Callback<Outkunjungan> {
                            override fun onResponse(
                                call: Call<Outkunjungan>,
                                response: Response<Outkunjungan>
                            ) {
                                val pesan: String? = response.body()?.message
                                Toast.makeText(this@MainActivity, pesan, Toast.LENGTH_LONG).show()
                            }
                            override fun onFailure(call: Call<Outkunjungan>, t: Throwable) {
                                Toast.makeText(this@MainActivity, "Internet Bermasalah", Toast.LENGTH_LONG).show()
                            }
                        })
                    } else {
                        Toast.makeText(this, "Kesalahan Pada QR Code", Toast.LENGTH_LONG).show()
                    }
                } else if (cektime > 300) {
                    Toast.makeText(this, "QR Code Expired, Kunjungan ", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(this, "Kesalahan Pada QR Code", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Kesalahan Pada QR Code ", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Kesalahan Pada QR Code", Toast.LENGTH_LONG).show()
        }
    }
}
