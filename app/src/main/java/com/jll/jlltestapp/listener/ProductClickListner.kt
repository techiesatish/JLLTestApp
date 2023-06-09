package com.jll.jlltestapp.listener

import com.jll.jlltestapp.data.remote.model.Product

interface ProductClickListener {
    fun onProductClicked(character: Product?)
}