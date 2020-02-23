package com.zayyan.ruangbacafasilkom.activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.zayyan.ruangbacafasilkom.R
import com.zayyan.ruangbacafasilkom.restApiRetrofit.RetrofitClient
import com.zayyan.ruangbacafasilkom.restApiRetrofit.models.Login
import com.zayyan.ruangbacafasilkom.services.vinegere
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.sql.Timestamp
import java.util.*


class LoginActivity : AppCompatActivity() {
    val CUSTOMIZED_REQUEST_CODE = 0x0000ffff
    val FILENAME = "Login"
    var getstatus: String =""
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tvSignup.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                IntentIntegrator(this@LoginActivity).setOrientationLocked(false).setCaptureActivity(
                    QrscanActivity::class.java
                ).initiateScan()
            }
        })

        val mngr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val IMEI: String = mngr.deviceId
        //Toast.makeText(this,IMEI,Toast.LENGTH_LONG).show()
        btnSignin.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                cekInputData()
                if (cekInputData()){
                    val progressDialog = ProgressDialog(this@LoginActivity)
                    progressDialog.setMessage("Mohon Tunggu....")
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.show()
                    RetrofitClient.instance.login(
                        etUser.text.toString(),
                        etPassword.text.toString(),
                        IMEI
                    ).enqueue(object : Callback<Login> {
                        override fun onResponse(
                            call: Call<Login>,
                            response: Response<Login>
                        ) {
                            val pesan: String? = response.body()?.message
                            Toast.makeText(applicationContext, pesan, Toast.LENGTH_LONG).show()
                            if (response.body()?.nama!=null){
                                val nama: String? = response.body()?.nama
                                val status: String? = response.body()?.status
                                val username: String? = response.body()?.username
                                getstatus = status!!
                                val setcmd: String = nama+"<~>"+status+"<~>"+username
                                val cmd: String? = vinegere.vinegere(setcmd,null)
                                val file: File = File(filesDir, FILENAME)

                                var outputStream: FileOutputStream? = null

                                try {
                                    file.createNewFile()
                                    outputStream = FileOutputStream(file, false)
                                    if (cmd != null) {
                                        outputStream.write(cmd.toByteArray())
                                    }
                                    outputStream.flush()
                                    outputStream.close()

                                    progressDialog.dismiss()

                                    Toast.makeText(applicationContext, pesan, Toast.LENGTH_LONG).show()
                                    if(getstatus == "petugas"){
                                        val mainmenu = Intent(applicationContext, MainPtgActivity::class.java)
                                        startActivity(mainmenu)
                                        finish()
                                    }
                                    else {
                                        val mainmenu = Intent(applicationContext, MainActivity::class.java)
                                        startActivity(mainmenu)
                                        finish()
                                    }
                                } catch (e: java.lang.Exception) {
                                    e.printStackTrace()
                                    progressDialog.dismiss()
                                    Toast.makeText(applicationContext, "error", Toast.LENGTH_LONG).show()
                                }
                            }
                            else{
                                progressDialog.dismiss()

                                Toast.makeText(applicationContext, "9A9AL "+pesan, Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<Login>, t: Throwable) {
                            Toast.makeText(
                                applicationContext,
                                "Koneksi internet anda bermasalah !",
                                Toast.LENGTH_SHORT
                            ).show()
                            progressDialog.dismiss()
                        }

                    })
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
            register(resultscan)
        }
    }

    private fun register(getresult: String) {
        val parsevin = vinegere.vinegere(null, getresult)
        val getparsevin: String = parsevin.toString()
        val split = getparsevin.split("<~>")
        if (split.size==6) {
            try {
                val getuser = split[0]
                val gettime = split[1].toInt()
                val nip = split[2]
                val nama = split[3]
                val fak = split[4]
                val jur = split[5]
                TimeZone.getTimeZone("Asia/Jakarta")
                val timestamp = Timestamp(System.currentTimeMillis())
                val getwaktu: String = timestamp.getTime().toString()
                val timenow: Int = getwaktu.substring(0, 10).toInt()
                val cektime = timenow - gettime
                if (cektime <= 300){
                    if (getuser == "Mahasiswa" || getuser == "Dosen") {
                        val mahasiswa = Intent(applicationContext, SignupActivity::class.java)
                        val myBundle = Bundle()
                        myBundle.putString("Regis", getuser)
                        myBundle.putString("nip", nip)
                        myBundle.putString("nama", nama)
                        myBundle.putString("fak", fak)
                        myBundle.putString("jur", jur)
                        mahasiswa.putExtra("bundle", myBundle)
                        startActivity(mahasiswa)
                        finish()
                    }
                    else{
                        Toast.makeText(this, "Kesalahan Pada QR Code", Toast.LENGTH_LONG).show()
                    }
                } else if (cektime > 300) {
                    Toast.makeText(this, "QR Code Expired, Register ", Toast.LENGTH_LONG)
                        .show()
                }else {
                    Toast.makeText(this, "Kesalahan Pada QR Code", Toast.LENGTH_LONG).show()
                }
            }catch (e: Exception){
                Toast.makeText(this, "Kesalahan Pada QR Code ", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Kesalahan Pada QR Code", Toast.LENGTH_LONG).show()
        }
    }
    private fun cekInputData(): Boolean {
        var isOk = true
        if (TextUtils.isEmpty(etUser.getText().toString().trim({ it <= ' ' }))) {
            isOk = false
            etUser.setError("Mohon Masukan Username !!")
        }
        if (TextUtils.isEmpty(etPassword.getText().toString().trim({ it <= ' ' }))) {
            isOk = false
            etPassword.setError("Mohon Masukan Password!!")
        }
        return isOk
    }
}
