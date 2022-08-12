package com.hector.melichallenge.presentation.product_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hector.melichallenge.databinding.ItemPictureBinding
import com.hector.melichallenge.domain.model.ProductPicture

class ImageViewPagerAdapter(
    private val pictures: List<ProductPicture>
): RecyclerView.Adapter<ImageViewPagerAdapter.PictureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        return PictureViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bind(
            pictures[position]
        )
    }

    override fun getItemCount(): Int {
        return pictures.size
    }

    class PictureViewHolder(private val binding: ItemPictureBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(picture: ProductPicture) = with(binding) {
            Glide
                .with(root.context)
                .load(picture.url)
                .into(imageView)
        }

        companion object {
            fun from(parent: ViewGroup): PictureViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPictureBinding.inflate(layoutInflater, parent, false)
                return PictureViewHolder(binding)
            }
        }
    }


}