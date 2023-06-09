package com.jll.jlltestapp.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class Product(
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    val id: Int,
    val images: List<String>,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String
) : Parcelable

fun Product.toProduct(): Product {
    return Product(
        brand=brand,
        category=category,
        description=description,
        discountPercentage=discountPercentage,
        id=id,
        images=images,
        price=price,
        rating=rating,
        stock=stock,
        thumbnail=thumbnail,
        title=title)
}

