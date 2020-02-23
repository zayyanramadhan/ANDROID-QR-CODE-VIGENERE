package com.zayyan.ruangbacafasilkom.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zayyan.ruangbacafasilkom.R
import kotlinx.android.synthetic.main.activity_scan_buku.*

class ScanBukuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_buku)

        val myIntent = intent
        val myBundle = myIntent.getBundleExtra("bundle")

        val judul: String = myBundle.getString("judul").toString()
        val npm: String = myBundle.getString("npm").toString()
        val nama: String = myBundle.getString("nama").toString()
        val kode: String = myBundle.getString("kode").toString().replace("@%", "/")
        val kat: String = myBundle.getString("kat").toString()

        tvjudul.setText(judul)
        tvnpm.setText(npm)
        tvnama.setText(nama)
        tvkode.setText(kode)
        tvkategori.setText(kat)
    }
}
