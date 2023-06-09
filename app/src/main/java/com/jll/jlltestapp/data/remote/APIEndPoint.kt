package com.jll.jlltestapp.data.remote

import com.jll.jlltestapp.data.remote.model.Product
import com.jll.jlltestapp.data.remote.model.ProductDetailResponse
import com.jll.jlltestapp.data.remote.model.ProductResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIEndPoint {
    @GET("/products?")
    suspend fun getProductsList(@Query("skip") skip: Int,@Query("limit") limit: Int): ProductResponse

    @GET("/product/{id}")
    suspend fun getProductsById( @Path("id") Id: Int):ProductDetailResponse

    @GET("/products/search?")
    suspend fun getProductsByQuery( @Query("q") Query: String): ProductResponse
}
