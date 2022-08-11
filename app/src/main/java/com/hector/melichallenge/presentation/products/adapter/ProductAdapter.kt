package com.hector.melichallenge.presentation.products.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hector.melichallenge.databinding.ItemProductBinding
import com.hector.melichallenge.domain.model.Product

class ProductAdapter(
    private val products: List<Product>
): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {


    var onItemClickListener: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
        holder.itemView.tag = product
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(it.tag as Product)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class ViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(product: Product) = with(binding) {
            textViewName.text = product.title
            textViewPrice.text = "$ ${product.price}"
            textViewInstallments.text = product.getInstallments() ?: ""
            textViewSeller.text = if(product.getBrand().isNullOrBlank()) "" else "Vendido por ${product.getBrand()}"
            textViewFull.visibility = if(product.isFullDelivery()) View.VISIBLE else View.INVISIBLE

            Glide
                .with(root.context)
                .load(product.thumbnail)
                .into(imageViewProduct)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}