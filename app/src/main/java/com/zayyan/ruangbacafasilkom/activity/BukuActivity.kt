package com.zayyan.ruangbacafasilkom.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zayyan.ruangbacafasilkom.R
import com.zayyan.ruangbacafasilkom.adapter.ItemAdapter
import com.zayyan.ruangbacafasilkom.restApiRetrofit.RetrofitClient
import com.zayyan.ruangbacafasilkom.restApiRetrofit.models.AllBuku
import com.zayyan.ruangbacafasilkom.restApiRetrofit.models.ItemXX
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class BukuActivity : AppCompatActivity() {
    private var itemAdapter = ItemAdapter(this@BukuActivity)
    var items: ArrayList<ItemXX> = ArrayList<ItemXX>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buku)

        val rvItem: RecyclerView = findViewById(R.id.rvItem)
        rvItem.layoutManager = LinearLayoutManager(this)
        rvItem.setHasFixedSize(true)

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Mohon Tunggu....")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        RetrofitClient.instance.getbukuall().enqueue(object : Callback<AllBuku> {
            override fun onResponse(call: Call<AllBuku>, response: Response<AllBuku>) {

                if (response.isSuccessful()) {
                    val items =
                        response.body()?.items!!

                    rvItem.setAdapter(itemAdapter)
                    if (items != null) {
                        itemAdapter.setListItem(items)
                    }
                    progressDialog.dismiss()
                } else {
                    call.clone().enqueue(this)
                }
            }

            override fun onFailure(call: Call<AllBuku>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@BukuActivity,"Internet Bermasalah", Toast.LENGTH_LONG).show()
            }
        })
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        getMenuInflater().inflate(R.menu.menu, menu)
//
//        val searchitem: MenuItem = menu?.findItem(R.id.action_search)!!
//        val searchview: SearchView = searchitem?.actionView as SearchView
//        searchview.setImeOptions(EditorInfo.IME_ACTION_DONE)
//        searchview.setOnQueryTextListener(object : OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                itemAdapter.filter?.filter(newText)
//                return false
//            }
//        })
//        return super.onCreateOptionsMenu(menu)
//    }
}
