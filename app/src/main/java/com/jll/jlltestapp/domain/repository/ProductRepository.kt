package com.jll.jlltestapp.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.jll.jlltestapp.data.remote.model.Product
import com.jll.jlltestapp.data.remote.model.ProductDetailResponse
import com.jll.jlltestapp.data.remote.model.ProductResponse
import com.jll.jlltestapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ProductRepository {
     suspend fun getProductById(id: Int):ProductDetailResponse
     fun getProductsByList(): LiveData<PagingData<Product>>
     suspend fun searchProduct(query:String): ProductResponse
}
