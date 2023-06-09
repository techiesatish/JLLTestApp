package com.jll.jlltestapp.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jll.jlltestapp.data.remote.model.Product
import com.jll.jlltestapp.data.remote.model.ProductDetailResponse
import com.jll.jlltestapp.data.remote.model.ProductResponse
import com.jll.jlltestapp.domain.repository.ProductRepository
import com.jll.jlltestapp.domain.usecase.ProductUseCase
import com.jll.jlltestapp.ui.main.MainViewModel
import com.jll.jlltestapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    lateinit var detailViewModel: DetailViewModel

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
        detailViewModel = DetailViewModel(productUseCase)
    }



    @Test
    fun getProductById() {
        runBlocking {
            Mockito.`when`(productUseCase.invoke(1))
                .thenReturn(
                    flowOf(
                        Resource.Success( ProductDetailResponse(brand ="Samsung",  category= "laptops",
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
                            title = "Samsung Galaxy Book"))))

            detailViewModel.getProductById(1)
            val result = detailViewModel.productFlow
            assertEquals(
                flowOf(
                    ProductResponse(1,
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