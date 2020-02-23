package com.zayyan.ruangbacafasilkom.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.zayyan.ruangbacafasilkom.R
import com.zayyan.ruangbacafasilkom.restApiRetrofit.models.ItemXXXX
import java.util.*


class ItemAdapterRPNew(private val context: Context? = null): RecyclerView.Adapter<ItemAdapterRPNew.ItemViewHolder>(),
    Filterable {
    private var listItem: ArrayList<ItemXXXX> = ArrayList()
    private var listItemFull: ArrayList<ItemXXXX> = ArrayList(listItem)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pinjam, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        holder.tvJudul.setText(listItem[position].nama)
        holder.tvKode.setText(listItem[position].kodeBuku)
        holder.tvTglpinjam.setText(listItem[position].tglPinjam)
        holder.tvStatus.setText(listItem[position].status)
        if (listItem[position].denda != listItem[position].bayar_denda && listItem[position].denda!=""){
            try {
                var denda1: Int = listItem[position].denda.toInt()
                var denda2: Int = listItem[position].bayar_denda.toInt()
                var denda: Int = denda1-denda2
                if (listItem[position].status == "Selesai"){
                    holder.tvStatus.setText("Kurang Denda: " + denda)
                }
            }catch (e: Exception){
                if (listItem[position].status == "Selesai"){
                    holder.tvStatus.setText("Kurang Denda: " + listItem[position].denda)
                }else
                    holder.tvStatus.setText(listItem[position].status + " Denda: " + listItem[position].denda)
            }
        }
        else{
            holder.tvStatus.setText(listItem[position].status)
            if (listItem[position].status == "Selesai"){
                holder.tvStatus.setTextColor(Color.GREEN)
            }
        }
        holder.cardItem.setOnClickListener(View.OnClickListener {
            Toast.makeText(
                context, "Name : " + listItem[position].nama,
                Toast.LENGTH_SHORT
            ).show()
//            val intent = Intent(context, BukuActivity::class.java)
//            intent.putExtra("position", position)
//            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    fun setListItem(listItem: List<ItemXXXX>) {
        if (listItem.isNotEmpty()) {
            this.listItem.clear()
        }
        this.listItem.addAll(listItem)
    }

    fun addItem(item: ItemXXXX) {
        listItem.add(item)
        notifyItemInserted(listItem.size - 1)
    }

    fun updateItem(position: Int, item: ItemXXXX) {
        listItem[position] = item
        notifyItemChanged(position, item)
    }

    fun deleteItem(position: Int) {
        listItem.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, listItem.size)
    }

    inner class ItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvJudul: TextView
        var tvKode: TextView
        var tvTglpinjam: TextView
        var tvStatus: TextView
        var cardItem: CardView

        init {
            tvJudul = itemView.findViewById(R.id.tvJudul)
            tvKode = itemView.findViewById(R.id.tvKode)
            tvTglpinjam = itemView.findViewById(R.id.tvTglpinjam)
            tvStatus = itemView.findViewById(R.id.tvStatus)
            cardItem = itemView.findViewById(R.id.cardItem)
        }
    }

    override fun getFilter(): Filter? {
        return myFilter
    }

    private val myFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults? {
            val filteredList: ArrayList<ItemXXXX> = ArrayList()
            if (constraint.toString().isEmpty()) {
                filteredList.addAll(listItemFull)
            } else {
                val filterPattern =
                    constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in listItemFull) {
                    if (item.nama.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(
            constraint: CharSequence?,
            results: FilterResults
        ) {
            listItem.clear()
            listItem.addAll(results.values as List<ItemXXXX>)
            notifyDataSetChanged()
        }
    }
}
