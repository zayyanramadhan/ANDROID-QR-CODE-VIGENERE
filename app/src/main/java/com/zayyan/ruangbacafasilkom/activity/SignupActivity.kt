package com.zayyan.ruangbacafasilkom.activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.zayyan.ruangbacafasilkom.R
import com.zayyan.ruangbacafasilkom.restApiRetrofit.RetrofitClient
import com.zayyan.ruangbacafasilkom.restApiRetrofit.models.*
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class SignupActivity : AppCompatActivity() {

    var REGIS: String? = null
    var NIP: String? = null
    var NAMA: String? = null
    var FAK: String? = null
    var JUR: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val myIntent = intent
        val myBundle = myIntent.getBundleExtra("bundle")

        REGIS = myBundle.getString("Regis")
        NIP = myBundle.getString("nip")
        NAMA = myBundle.getString("nama")
        FAK = myBundle.getString("fak")
        JUR = myBundle.getString("jur")
        var tvTitle: TextView = findViewById(R.id.tvTitle)
        tvTitle.setText("Aktivasi " + REGIS)
        var vetNpm: TextView = findViewById(R.id.vetNpm)
        if (REGIS == "Dosen") {
            vetNpm.setText("NIP/NPT")
        }

//        val sFak: Spinner = findViewById(R.id.sFak)
//        val sProg: Spinner = findViewById(R.id.sProg)
        val sFak: TextView = findViewById(R.id.sFak)
        val sProg: TextView = findViewById(R.id.sProg)
        val tvSignin: TextView = findViewById(R.id.tvSignin)
        val btSignup: TextView = findViewById(R.id.btnSignup)
        val etNama: TextView = findViewById(R.id.etNama)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etAlamat: EditText = findViewById(R.id.etAlamat)
        val etTlp: EditText = findViewById(R.id.etTlp)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val etCpassword: EditText = findViewById(R.id.etCpassword)

        etNama.setText(NAMA)
        etNpm.setText(NIP)
        sFak.setText(FAK)
        sProg.setText(JUR)

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Mohon Tunggu....")
        progressDialog.setCanceledOnTouchOutside(false)
//        progressDialog.show()
//        val listSpinner: MutableList<String> =
//            ArrayList()
//        RetrofitClient.instance.getfakultas().enqueue(object : Callback<Fakultas> {
//            override fun onResponse(
//                call: Call<Fakultas>,
//                response: Response<Fakultas>
//            ) {
//                if (response.isSuccessful()) {
//                    val fakultasItems: List<Item> =
//                        response.body()?.items!!
//
//                    listSpinner.add("Pilih Fakultas")
//                    for (i in fakultasItems.indices) {
//                        var namafakultas: String = fakultasItems[i].nama_fakultas
//                        listSpinner.add(namafakultas)
//                    }
//                    // Set hasil result json ke dalam adapter spinner
//                    val adapter = ArrayAdapter(
//                        this@SignupActivity,
//                        android.R.layout.simple_spinner_item, listSpinner
//                    )
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                    sFak.setAdapter(adapter)
//                    progressDialog.dismiss()
//                } else {
//                    call.clone().enqueue(this)
//                }
//            }
//
//            override fun onFailure(
//                call: Call<Fakultas>,
//                t: Throwable
//            ) {
//                Toast.makeText(
//                    applicationContext,
//                    "Koneksi internet anda bermasalah !",
//                    Toast.LENGTH_SHORT
//                ).show()
//                progressDialog.dismiss()
//            }
//        })
//
//        sFak.setOnItemSelectedListener(object : OnItemSelectedListener {
//            override fun onItemSelected(
//                parentView: AdapterView<*>?,
//                selectedItemView: View?,
//                position: Int,
//                id: Long
//            ) {
//                progressDialog.show()
//                sProg.setVisibility(View.VISIBLE)
//                sProg.setClickable(true)
//                val spinItem: String = listSpinner[position]
//                val listSpinnerP: MutableList<String> =
//                    ArrayList()
//                if (spinItem != "Pilih Fakultas") {
//                    RetrofitClient.instance.getprogdi(spinItem).enqueue(object : Callback<Progdi> {
//                        override fun onResponse(
//                            call: Call<Progdi>,
//                            response: Response<Progdi>
//                        ) {
//                            if (response.isSuccessful()) {
//                                val progdiItems: List<ItemX> =
//                                    response.body()?.items!!
//                                listSpinnerP.add("Pilih Program Studi")
//                                for (i in progdiItems.indices) {
//                                    var namafakultas: String = progdiItems[i].nama_progdi
//                                    listSpinnerP.add(namafakultas)
//                                }
//                                // Set hasil result json ke dalam adapter spinner
//                                val adapterp = ArrayAdapter(
//                                    this@SignupActivity,
//                                    android.R.layout.simple_spinner_item, listSpinnerP
//                                )
//                                adapterp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                                sProg.setAdapter(adapterp)
//                                progressDialog.dismiss()
//                            } else {
//                                call.clone().enqueue(this)
//                            }
//                        }
//
//                        override fun onFailure(
//                            call: Call<Progdi>,
//                            t: Throwable
//                        ) {
//                            Toast.makeText(
//                                applicationContext,
//                                "Koneksi internet anda bermasalah !",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            progressDialog.dismiss()
//                        }
//                    })
//                } else {
//                    sProg.setVisibility(View.INVISIBLE)
//                    sProg.setClickable(false)
//                    listSpinnerP.clear()
//                    progressDialog.dismiss()
//                }
//            }
//
//            override fun onNothingSelected(parentView: AdapterView<*>?) {
//            }
//        })

        tvSignin.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val backlogin = Intent(applicationContext, LoginActivity::class.java)
                startActivity(backlogin)
            }
        })

        btSignup.setOnClickListener(object : View.OnClickListener {
            @SuppressLint("MissingPermission")
            override fun onClick(v: View?) {
                cekInputData()
                if (cekInputData()) {
                    val mngr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                    val IMEI: String = mngr.deviceId
                    if (REGIS == "Dosen") {
                        progressDialog.show()
                        RetrofitClient.instance.regdosen(
                            etNama.text.toString(),
                            etNpm.text.toString(),
                            etAlamat.text.toString(),
                            etTlp.text.toString(),
                            etEmail.text.toString(),
                            sProg.text.toString(),
                            etPassword.text.toString(),
                            IMEI
                        ).enqueue(object : Callback<RegDosen> {
                            override fun onResponse(
                                call: Call<RegDosen>,
                                response: Response<RegDosen>
                            ) {
                                val pesan: String? = response.body()?.message
                                Toast.makeText(applicationContext, pesan, Toast.LENGTH_LONG).show()
                                progressDialog.dismiss()
                                val backlogin =
                                    Intent(applicationContext, LoginActivity::class.java)
                                startActivity(backlogin)
                            }

                            override fun onFailure(call: Call<RegDosen>, t: Throwable) {
                                Toast.makeText(
                                    applicationContext,
                                    "Koneksi internet anda bermasalah !",
                                    Toast.LENGTH_SHORT
                                ).show()
                                progressDialog.dismiss()
                            }

                        })
                    }
                    if (REGIS == "Mahasiswa") {
                        progressDialog.show()
                        RetrofitClient.instance.regmahasiswa(
                            etNama.text.toString(),
                            etNpm.text.toString(),
                            etAlamat.text.toString(),
                            etTlp.text.toString(),
                            etEmail.text.toString(),
                            sProg.text.toString(),
                            etPassword.text.toString(),
                            IMEI
                        ).enqueue(object : Callback<RegMahasiswa> {
                            override fun onResponse(
                                call: Call<RegMahasiswa>,
                                response: Response<RegMahasiswa>
                            ) {
                                val pesan: String? = response.body()?.message
                                Toast.makeText(applicationContext, pesan, Toast.LENGTH_LONG).show()
                                progressDialog.dismiss()
                                val backlogin =
                                    Intent(applicationContext, LoginActivity::class.java)
                                startActivity(backlogin)
                            }

                            override fun onFailure(call: Call<RegMahasiswa>, t: Throwable) {
                                Toast.makeText(
                                    applicationContext,
                                    "Koneksi internet anda bermasalah !",
                                    Toast.LENGTH_SHORT
                                ).show()
                                progressDialog.dismiss()
                            }

                        })
                    } else {

                    }
                }

            }
        })


    }

    private fun cekInputData(): Boolean {
        var isOk = true
        if (TextUtils.isEmpty(etNpm.getText().toString().trim({ it <= ' ' }))) {
            isOk = false
            if (REGIS == "Dosen") {
                etNpm.setError("Mohon Masukan NIP/NPT !!")
            } else {
                etNpm.setError("Mohon Masukan NPM !!")
            }

        }
        if (TextUtils.isEmpty(etNama.getText().toString().trim({ it <= ' ' }))) {
            isOk = false
            etNama.setError("Mohon Masukan Nama!!")
        }
        if (TextUtils.isEmpty(etEmail.getText().toString().trim({ it <= ' ' }))) {
            isOk = false
            etEmail.setError("Mohon Masukan Email!!")
        }
        if (TextUtils.isEmpty(etAlamat.getText().toString().trim({ it <= ' ' }))) {
            isOk = false
            etAlamat.setError("Mohon Masukan Alamat!!")
        }
        if (TextUtils.isEmpty(etTlp.getText().toString().trim({ it <= ' ' }))) {
            isOk = false
            etTlp.setError("Mohon Masukan No Telepon!!")
        }
        if (TextUtils.isEmpty(etPassword.getText().toString().trim({ it <= ' ' }))) {
            isOk = false
            etPassword.setError("Mohon Masukan Password!!")
        }
        if (TextUtils.isEmpty(etCpassword.getText().toString().trim({ it <= ' ' }))) {
            isOk = false
            etCpassword.setError("Mohon Masukan Confirm Password!!")
        }
        if (etCpassword.getText().toString() != etPassword.getText().toString()) {
            isOk = false
            etCpassword.setError("Password Tidak Sama !!")
        }
        if (!isValidEmail(etEmail.text.toString().trim({ it <= ' ' }))) {
            isOk = false
            etEmail.setError("Format Email Salah !!")
        }
//        if (sFak.selectedItem.toString().equals("Pilih Fakultas")) {
//            isOk = false
//            Toast.makeText(this, "Mohon Pilih Fakultas !!", Toast.LENGTH_LONG).show()
//        }
//        try {
//            if (sProg.selectedItem.toString().equals("Pilih Program Studi")) {
//                isOk = false
//                Toast.makeText(this, "Mohon Pilih Program Studi !!", Toast.LENGTH_LONG).show()
//            }
//        } catch (e: Exception) {
//            isOk = false
//            Toast.makeText(this, "Mohon Pilih Fakultas !!", Toast.LENGTH_LONG).show()
//        }
        return isOk
    }

    fun isValidEmail(email: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
