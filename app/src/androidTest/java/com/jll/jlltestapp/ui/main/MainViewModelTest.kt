package com.jll.jlltestapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.jll.jlltestapp.data.remote.model.Product
import com.jll.jlltestapp.data.remote.model.ProductResponse
import com.jll.jlltestapp.domain.repository.ProductRepository
import com.jll.jlltestapp.domain.usecase.ProductUseCase
import com.jll.jlltestapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {


    private val testDispatcher = StandardTestDispatcher()
    lateinit var mainViewModel: MainViewModel

    lateinit var productUseCase: ProductUseCase

    @Mock
    lateinit var repository: ProductRepository

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        productUseCase = Mockito.mock(ProductUseCase::class.java)
        mainViewModel = MainViewModel(productUseCase)
    }


    @Test
    fun searchProduct() {

        runBlocking {
            Mockito.`when`(productUseCase.invoke("Laptop"))
                .thenReturn(flowOf(Resource.Success( ProductResponse(1,
                    listOf(
                        Product(brand ="Samsung",  category= "laptops",
                            description= "Samsung Galaxy Book S (2020) Laptop With Intel Lakefield Chip, 8GB of RAM Launched",
                            discountPercentage= 4.15,
                            id = 1,
                            images = listOf(  "https://i.dummyjson.com/data/products/7/1.jpg",
                                "https://i.dummyjson.com/data/products/7/2.jpg",
                                "https://i.dummyjson.com/data/products/7/3.jpg",
                                "https://i.dummyjson.com/data/products/7/thumbnail.jpg"),
                            price= 1499,
                            rating= 4.25,
                            stock= 50,
                            thumbnail =  "https://i.dummyjson.com/data/products/7/thumbnail.jpg",
                            title = "Samsung Galaxy Book")),2,5))))
            mainViewModel.getProductList()
            val result = mainViewModel.productResponseFlow
            assertEquals(flowOf(ProductResponse(1,
                listOf(
                    Product(brand ="Samsung",  category= "laptops",
                        description= "Samsung Galaxy Book S (2020) Laptop With Intel Lakefield Chip, 8GB of RAM Launched",
                        discountPercentage= 4.15,
                        id = 1,
                        images = listOf(  "https://i.dummyjson.com/data/products/7/1.jpg",
                            "https://i.dummyjson.com/data/products/7/2.jpg",
                            "https://i.dummyjson.com/data/products/7/3.jpg",
                            "https://i.dummyjson.com/data/products/7/thumbnail.jpg"),
                        price= 1499,
                        rating= 4.25,
                        stock= 50,
                        thumbnail =  "https://i.dummyjson.com/data/products/7/thumbnail.jpg",
                        title = "Samsung Galaxy Book")),2,5)) , result)
        }
    }





}