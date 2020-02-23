package com.zayyan.ruangbacafasilkom.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.google.zxing.integration.android.IntentIntegrator
import com.zayyan.ruangbacafasilkom.R
import com.zayyan.ruangbacafasilkom.adapter.ItemAdapterRP
import com.zayyan.ruangbacafasilkom.adapter.ItemAdapterRPNew
import com.zayyan.ruangbacafasilkom.restApiRetrofit.RetrofitClient
import com.zayyan.ruangbacafasilkom.restApiRetrofit.models.ItemXXX
import com.zayyan.ruangbacafasilkom.restApiRetrofit.models.RiwayatPinjam
import com.zayyan.ruangbacafasilkom.restApiRetrofit.models.RiwayatPinjamNew
import com.zayyan.ruangbacafasilkom.services.vinegere
import kotlinx.android.synthetic.main.activity_kembali_ptg.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class KembaliPtgActivity : AppCompatActivity() {
    val CUSTOMIZED_REQUEST_CODE = 0x0000ffff
    private var ItemAdapterRP = ItemAdapterRP(this)
    private var ItemAdapterRPNew = ItemAdapterRPNew(this)
    var items: ArrayList<ItemXXX> = ArrayList<ItemXXX>()
    var USERNAME: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kembali_ptg)

        val rvItem: RecyclerView = findViewById(R.id.rvItem)
        rvItem.layoutManager = LinearLayoutManager(this)
        rvItem.setHasFixedSize(true)

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Mohon Tunggu....")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

        val myIntent = intent
        val myBundle = myIntent.getBundleExtra("bundle")

        val status: String = myBundle.getString("status").toString()
        val username: String = myBundle.getString("username").toString()
        val nama: String = myBundle.getString("nama").toString()

        data.setText("Riwayat "+status+" : \n"+nama+"("+username+")")

        USERNAME = username

        RetrofitClient.instance.getriwayatpinjam(username).enqueue(object : Callback<RiwayatPinjam> {
            override fun onResponse(call: Call<RiwayatPinjam>, response: Response<RiwayatPinjam>) {

                if (response.isSuccessful()) {
                    val items = response.body()?.items

                    rvItem.setAdapter(ItemAdapterRP)
                    if (items != null) {
                        ItemAdapterRP.setListItem(items)
                    }
                    progressDialog.dismiss()
                } else {
                    call.clone().enqueue(this)
                }
            }

            override fun onFailure(call: Call<RiwayatPinjam>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@KembaliPtgActivity, "Ulangi Kembali Internet Bermasalah", Toast.LENGTH_LONG).show()
            }
        })

        btPinjam.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                IntentIntegrator(this@KembaliPtgActivity).setOrientationLocked(false).setCaptureActivity(
                    QrscanActivity::class.java
                ).initiateScan()
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
            qrkembali(resultscan)
        }
    }

    private fun qrkembali(getresult: String) {
        try {
            var result = vinegere.vinegere(null,getresult)?.replace("`2*","\"")
            val parser: Parser = Parser()
            val stringBuilder: StringBuilder = StringBuilder(result!!)
            val json: JsonObject = parser.parse(stringBuilder) as JsonObject
            val setresult: String = json.string("judul").toString()
            val getuser: String = USERNAME.toString()

            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Mohon Tunggu....")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            RetrofitClient.instance.apikembali(getuser,setresult).enqueue(object : Callback<RiwayatPinjamNew> {
                override fun onResponse(call: Call<RiwayatPinjamNew>, response: Response<RiwayatPinjamNew>) {
                    val pesan: String? = response.body()?.message
                    Toast.makeText(applicationContext,pesan,Toast.LENGTH_LONG).show()
                    if (response.isSuccessful()) {

                        try {
                            val items = response.body()?.items

                            rvItem.setAdapter(ItemAdapterRPNew)
                            if (items != null) {
                                ItemAdapterRPNew.setListItem(items)
                            }
                            progressDialog.dismiss()
                            Toast.makeText(this@KembaliPtgActivity, pesan, Toast.LENGTH_LONG).show()
                        }catch (e: java.lang.Exception){
                            progressDialog.dismiss()
                            Toast.makeText(this@KembaliPtgActivity, "Judul : "+setresult+" \n"+pesan, Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this@KembaliPtgActivity,"Gagal "+pesan,Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<RiwayatPinjamNew>, t: Throwable) {
                    progressDialog.dismiss()
                    Toast.makeText(this@KembaliPtgActivity,"Internet Bermasalah", Toast.LENGTH_LONG).show()
                }
            })

        }catch (e: Exception){
            Toast.makeText(this, "Kesalahan Pada QR Code ", Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}

