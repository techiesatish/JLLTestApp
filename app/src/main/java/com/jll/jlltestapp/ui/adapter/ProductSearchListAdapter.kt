package com.jll.jlltestapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jll.jlltestapp.data.remote.model.Product
import com.jll.jlltestapp.databinding.ProductItemBinding
import com.jll.jlltestapp.listener.ProductClickListener

class ProductSearchListAdapter(val list: ArrayList<Product>): RecyclerView.Adapter<ProductSearchListAdapter.ProductListViewHolder>() {


    var productClickListener: ProductClickListener? = null

    inner class ProductListViewHolder(private val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {



        fun bindCharacter(product: Product) {
            binding.apply {
                tvProductTitle.text=product.title
                tvProductDesc.text=product.description
                tvProductPrice.text=product.price.toString()
                binding.imgProduct.load(product.thumbnail)
            }
           binding.clCardItems.setOnClickListener {
               productClickListener?.onProductClicked(product) //listner for product items
           }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bindCharacter(list.get(position))
    }

    fun addProduct(users: List<Product>) {
        this.list.apply {
            clear()
            addAll(users)
        }

    }
}