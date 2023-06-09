package com.jll.jlltestapp.data.remote.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailResponse(
    @SerializedName("brand")
    val brand: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("discountPercentage")
    val discountPercentage: Double,
    @SerializedName("id")
    val id: Int,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("price")
    val price: Int,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("title")
    val title: String
)
fun ProductDetailResponse.toProductDetail(): ProductDetailResponse {
    return ProductDetailResponse(
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
