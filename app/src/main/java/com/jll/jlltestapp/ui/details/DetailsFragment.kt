package com.jll.jlltestapp.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.jll.jlltestapp.R
import com.jll.jlltestapp.data.remote.model.Product
import com.jll.jlltestapp.data.remote.model.ProductDetailResponse
import com.jll.jlltestapp.databinding.FragmentDetailsBinding
import com.jll.jlltestapp.utils.Resource
import com.jll.jlltestapp.utils.hide
import com.jll.jlltestapp.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details){

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var productListViewModel: DetailViewModel
    private lateinit var product: Product
    private val args: DetailsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)
        productListViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]
        getArgs()
        collectFromViewModel()
    }

    private fun getArgs() {
        product = args.product
        productListViewModel.getProductById(product.id)
    }

    private fun collectFromViewModel() {
        lifecycleScope.launch {
            productListViewModel.productFlow
                .collectLatest {
                    when (it) {
                        is Resource.Success -> {
                           updateUIOnSuccess(it.data)
                        }
                        is Resource.Error -> {
                            updateUIOnError(it.message)
                        }
                        is Resource.Loading -> {
                           updateUIOnProgress()
                        }
                    }
                }
        }
    }

    private fun updateUIOnSuccess(data: ProductDetailResponse?) {
        binding.clDetail.show()  //show main details hide others
        binding.pbDetails.hide()
        binding.tvErrorDetails.hide()
        binding.tvProductTitle.text=data?.title
        binding.tvProductPrice.text=data?.price.toString()
        binding.tvProductDesc.text=data?.description
        binding.imgProduct.load(data?.thumbnail)
    }

    private fun updateUIOnError(message: String?) {
        binding.pbDetails.hide() //show error hide others
        binding.clDetail.hide()
        binding.tvErrorDetails.show()
        binding.tvErrorDetails.text=message

    }

    private fun updateUIOnProgress(){
        binding.pbDetails.show() //show progress hide others
        binding.clDetail.hide()
        binding.tvErrorDetails.hide()
    }
}