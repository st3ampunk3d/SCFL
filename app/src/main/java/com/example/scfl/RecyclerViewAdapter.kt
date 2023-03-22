package com.example.scfl

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.scfl.RecyclerRowMoveCallback.RecyclerViewRowTouchHelperContract
import java.util.*

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.MyViewModel>(),
    RecyclerViewRowTouchHelperContract {
    private var dataList: List<Rider?>? = null
    fun setDataList(dataList: List<Rider?>?) {
        this.dataList = dataList
    }
    fun getDataList() : List<Rider?>? {
        return dataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewModel {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)
            //LayoutInflater.from(parent.context).inflate(R.layout.simple_list_item_1, parent, false)
        return MyViewModel(view)
    }

    override fun onBindViewHolder(holder: MyViewModel, position: Int) {
        holder.lblItemName.text = dataList!![position]!!.name
        //holder.lblItemDetails.setText(dataList!![position].getDetail())
    }

    override fun getItemCount(): Int {
        return dataList!!.size
    }

    override fun onRowMoved(from: Int, to: Int) {
        if (from < to) {
            for (i in from until to) {
                dataList?.let { Collections.swap(it, i, i + 1) }
            }
        } else {
            for (i in from downTo to + 1) {
                dataList?.let { Collections.swap(it, i, i - 1) }
            }
        }
        notifyItemMoved(from, to)
    }


    override fun onRowSelected(myViewHolder: MyViewModel?) {
        myViewHolder!!.cardView.setCardBackgroundColor(Color.GRAY)
    }

    override fun onRowClear(myViewHolder: MyViewModel?) {
        myViewHolder!!.cardView.setCardBackgroundColor(Color.parseColor("#12dddd"))
    }

    inner class MyViewModel(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var lblItemName: TextView
        var lblItemDetails: TextView
        var cardView: CardView

        init {
            lblItemName = itemView.findViewById(R.id.lblItemName)
            lblItemDetails = itemView.findViewById(R.id.lblItemDetails)
            cardView = itemView.findViewById(R.id.cardView)
        }
    }
}