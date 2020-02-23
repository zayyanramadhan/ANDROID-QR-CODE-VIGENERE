package com.zayyan.ruangbacafasilkom.adapter

import android.content.Context
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
import com.zayyan.ruangbacafasilkom.restApiRetrofit.models.ItemXX
import java.util.*


class ItemAdapter(private val context: Context? = null): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(),
    Filterable {
    private var listItem: ArrayList<ItemXX> = ArrayList()
    private var listItemFull: ArrayList<ItemXX> = ArrayList(listItem)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        holder.tvJudul.setText(listItem[position].judul)
        holder.tvNama.setText(listItem[position].nama)
        holder.tvKode.setText(listItem[position].kode)
        holder.cardItem.setOnClickListener(View.OnClickListener {
            Toast.makeText(
                context, "Name : " + listItem[position].judul,
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

    fun setListItem(listItem: List<ItemXX>) {
        if (listItem.isNotEmpty()) {
            this.listItem.clear()
        }
        this.listItem.addAll(listItem)
    }

    fun addItem(item: ItemXX) {
        listItem.add(item)
        notifyItemInserted(listItem.size - 1)
    }

    fun updateItem(position: Int, item: ItemXX) {
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
        var tvNama: TextView
        var tvKode: TextView
        var cardItem: CardView

        init {
            tvJudul = itemView.findViewById(R.id.tvJudul)
            tvNama = itemView.findViewById(R.id.tvNama)
            tvKode = itemView.findViewById(R.id.tvKode)
            cardItem = itemView.findViewById(R.id.cardItem)
        }
    }

    override fun getFilter(): Filter? {
        return myFilter
    }

    private val myFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults? {
            val filteredList: ArrayList<ItemXX> = ArrayList()
            if (constraint.toString().isEmpty()) {
                filteredList.addAll(listItemFull)
            } else {
                val filterPattern =
                    constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in listItemFull) {
                    if (item.judul.toLowerCase().contains(filterPattern)) {
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
            listItem.addAll(results.values as List<ItemXX>)
            notifyDataSetChanged()
        }
    }
}

