package com.jll.jlltestapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jll.jlltestapp.data.remote.APIEndPoint
import com.jll.jlltestapp.data.remote.model.Product
import java.lang.Exception


private const val TAG = "ProductsPagingSource"


class ProductsPagingSource(private val apiService: APIEndPoint): PagingSource<Int, Product>() {

    var count=0
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {

        return try {
            val skip:Int = count*15
            count++
            val limit:Int = skip+15


            val response=    apiService.getProductsList(skip = skip, limit = limit)


            LoadResult.Page(
                data = response.products,
                prevKey = if (skip == 0) null else skip - limit,
                nextKey = if (response.products.isNotEmpty()) skip + limit else null)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }




    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}