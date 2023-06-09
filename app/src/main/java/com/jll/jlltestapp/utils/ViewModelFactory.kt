package com.jll.jlltestapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jll.jlltestapp.domain.usecase.ProductUseCase
import com.jll.jlltestapp.ui.details.DetailViewModel
import com.jll.jlltestapp.ui.main.MainViewModel
import javax.inject.Inject

class ViewModelFactory
@Inject constructor(private val repository: ProductUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository) as T
        } else if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            DetailViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}