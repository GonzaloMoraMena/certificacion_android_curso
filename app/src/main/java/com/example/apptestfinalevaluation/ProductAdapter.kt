package com.example.apptestfinalevaluation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apptestfinalevaluation.model.local.ProductEntity
import kotlinx.android.synthetic.main.product_item.view.*

class ProductAdapter(val callback: CallbackInterface) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var productList = emptyList<ProductEntity>()

    fun updateAdapter(mList: List<ProductEntity>) {
        Log.d("data", mList.toString())
        productList = mList
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img_product = itemView.iv_product
        val name_product = itemView.product_name
        val price_product = itemView.product_price
        val clickLister = itemView.setOnClickListener {
            callback.passTheData(productList[adapterPosition])
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount() = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        Log.d("data", holder.name_product.text.toString())
        Log.d("data", productList.size.toString())
        holder.name_product.text = productList[position].name
        holder.price_product.text = productList[position].price
        Glide.with(holder.itemView.context).load(productList[position].image)
            .into(holder.img_product)
    }

    interface CallbackInterface {
        fun passTheData(product: ProductEntity)
    }

}