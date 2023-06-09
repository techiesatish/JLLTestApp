package com.jll.jlltestapp.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jll.jlltestapp.data.remote.model.Product
import com.jll.jlltestapp.databinding.ProductItemBinding
import com.jll.jlltestapp.listener.ProductClickListener


class ProductListAdapter: PagingDataAdapter<Product, ProductListAdapter.ProductListViewHolder>(ProductComparator()) {
    var productClickListener: ProductClickListener? = null

    inner class ProductListViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            productClickListener
            itemView.setOnClickListener {
                productClickListener?.onProductClicked(getItem(absoluteAdapterPosition))
            }
        }

        fun bindCharacter(product: Product) {
            binding.apply {
                tvProductTitle.text=product.title
                tvProductDesc.text=product.description
                tvProductPrice.text=product.price.toString()
                binding.imgProduct.load(product.thumbnail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        getItem(position)?.let { holder.bindCharacter(it) }
    }

    class ProductComparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product) =
            oldItem == newItem
    }


}
