package com.jll.jlltestapp.ui.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jll.jlltestapp.data.remote.model.Product
import com.jll.jlltestapp.data.remote.model.ProductDetailResponse
import com.jll.jlltestapp.data.remote.model.ProductResponse
import com.jll.jlltestapp.domain.usecase.ProductUseCase
import com.jll.jlltestapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: ProductUseCase) :ViewModel() {


    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")

    val searchQuery = _searchQuery.asStateFlow()

    private val _productResponseFlow = MutableSharedFlow<Resource<ProductResponse>>()
    val productResponseFlow = _productResponseFlow.asSharedFlow()



    fun searchProduct(query:String){
        viewModelScope.launch {
            useCase.invoke(query).collect{
                _productResponseFlow.emit(it)
            }
        }
    }

     fun getProductList(): LiveData<PagingData<Product>> {
        return useCase.invoke().cachedIn(viewModelScope)
    }


    }




