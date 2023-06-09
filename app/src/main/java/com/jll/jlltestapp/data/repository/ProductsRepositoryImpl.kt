package com.jll.jlltestapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.jll.jlltestapp.data.paging.ProductsPagingSource
import com.jll.jlltestapp.data.remote.APIEndPoint
import com.jll.jlltestapp.data.remote.model.Product
import com.jll.jlltestapp.data.remote.model.ProductDetailResponse
import com.jll.jlltestapp.data.remote.model.ProductResponse
import com.jll.jlltestapp.domain.repository.ProductRepository

private const val TAG = "ProductsRepositoryImpl"

class ProductRepositoryImpl (private val api: APIEndPoint ) : ProductRepository {
    override suspend fun getProductById(id: Int):ProductDetailResponse{
        return api.getProductsById(id)
    }

    override fun getProductsByList(): LiveData<PagingData<Product>> {
        return Pager( config = PagingConfig(pageSize = 15, enablePlaceholders = false, initialLoadSize = 5),
            pagingSourceFactory = { ProductsPagingSource(api)}, initialKey = 0).liveData
    }

    override suspend fun searchProduct(query: String): ProductResponse{
        return api.getProductsByQuery(query)
    }

}