package com.jll.jlltestapp.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.jll.jlltestapp.data.remote.model.Product
import com.jll.jlltestapp.data.remote.model.ProductDetailResponse
import com.jll.jlltestapp.data.remote.model.toProductDetail
import com.jll.jlltestapp.data.repository.ProductRepositoryImpl
import com.jll.jlltestapp.domain.usecase.ProductUseCase
import com.jll.jlltestapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "DetailViewModel"
@HiltViewModel
class DetailViewModel @Inject constructor(private val useCase: ProductUseCase) :ViewModel() {

    private val _productDetailsFlow = MutableSharedFlow<Resource<ProductDetailResponse>>()
    val productFlow = _productDetailsFlow.asSharedFlow()




    fun getProductById(id:Int) {

         viewModelScope.launch {
             useCase.invoke(id).collect{
                 _productDetailsFlow.emit(it)
             }
         }
        }





}