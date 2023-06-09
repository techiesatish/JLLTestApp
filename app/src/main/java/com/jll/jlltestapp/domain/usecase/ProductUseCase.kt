package com.jll.jlltestapp.domain.usecase

import androidx.paging.PagingData
import com.jll.jlltestapp.data.remote.model.Product
import com.jll.jlltestapp.data.remote.model.ProductDetailResponse
import com.jll.jlltestapp.data.remote.model.ProductResponse
import com.jll.jlltestapp.data.remote.model.toProductDetail
import com.jll.jlltestapp.domain.repository.ProductRepository
import com.jll.jlltestapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

//#NOTE For keep it simple I have used only one use case for production highly recommeded to use different use cases
class ProductUseCase (private val productRepository: ProductRepository) {

    suspend operator fun invoke(id: Int): Flow<Resource<ProductDetailResponse>> = flow {
        try {
            emit(Resource.Loading())
            val product = productRepository.getProductById(id).toProductDetail()
            emit(Resource.Success(product))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't connect to server. Check your internet connection."))
        }
    }

     operator fun invoke() =productRepository.getProductsByList()


    suspend operator fun invoke(query: String): Flow<Resource<ProductResponse>> = flow {
        try {
            emit(Resource.Loading())
            val product = productRepository.searchProduct(query)
            emit(Resource.Success(product))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't connect to server. Check your internet connection."))
        }
    }
}
