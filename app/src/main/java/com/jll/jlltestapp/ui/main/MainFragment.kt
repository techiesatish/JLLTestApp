package com.jll.jlltestapp.ui.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.jll.jlltestapp.R
import com.jll.jlltestapp.data.remote.model.Product
import com.jll.jlltestapp.data.remote.model.ProductDetailResponse
import com.jll.jlltestapp.data.remote.model.ProductResponse
import com.jll.jlltestapp.databinding.FragmentMainBinding
import com.jll.jlltestapp.listener.ProductClickListener
import com.jll.jlltestapp.ui.adapter.ProductListAdapter
import com.jll.jlltestapp.ui.adapter.ProductSearchListAdapter
import com.jll.jlltestapp.utils.Resource
import com.jll.jlltestapp.utils.hide
import com.jll.jlltestapp.utils.safeNavigate
import com.jll.jlltestapp.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


private const val TAG = "MainFragment"


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), ProductClickListener {

    private lateinit var binding: FragmentMainBinding
    private lateinit var productListViewModel: MainViewModel
    private  val productListAdapter=ProductListAdapter()
    private  lateinit var productSearchListAdapter:ProductSearchListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        productListViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        setupCharacterRecyclerView()
        collectPagingDataFromViewModel()
        collectFromSearchResults()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as? SearchView

        searchView?.isSubmitButtonEnabled = true

        searchView?.queryHint = "search..."
        searchView?.setQuery(productListViewModel.searchQuery.value, true)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                performSearchEvent(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                if(query.isEmpty()) collectPagingDataFromViewModel()  //if query is empty fetch again the paging data
                return false
            }

        })
    }


    private fun performSearchEvent(productName: String) {
        productListViewModel.searchProduct(productName)
    }

    private fun setupCharacterRecyclerView() {
        productListAdapter.productClickListener=this
        binding.rvProductList.adapter = productListAdapter
        productListAdapter.addLoadStateListener { loadState ->
            // show empty list
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading)
                binding.pbList.isVisible = true
            else {
                binding.pbList.isVisible = false
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    private fun collectPagingDataFromViewModel() { //collect
        lifecycleScope.launch {
            productListViewModel.getProductList().observe(viewLifecycleOwner) {
                it?.let {
                    productListAdapter.submitData(lifecycle, it)
                }
            }
        }
    }

    private fun collectFromSearchResults(){
            lifecycleScope.launch {
                productListViewModel.productResponseFlow
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

    private fun updateUIOnSuccess(data: ProductResponse?) {
        if(data!!.products.isEmpty()) //if no products available
        {
            binding.pbList.hide()
            Toast.makeText(requireContext(),getString(R.string.no_product),Toast.LENGTH_SHORT).show()
            return
        }

        //only proceed when list size>0
        binding.pbList.hide()
        productSearchListAdapter = ProductSearchListAdapter(arrayListOf())
        productSearchListAdapter.productClickListener=this
        binding.rvProductList.adapter = productSearchListAdapter
        productSearchListAdapter.apply {
            addProduct(data!!.products)
            notifyDataSetChanged()
        }

    }

    private fun updateUIOnError(message: String?) {
        binding.pbList.hide()
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()

    }

    private fun updateUIOnProgress(){
        binding.pbList.show() //show progress hide others
    }


    override fun onProductClicked(product: Product?) {
//        Log.d(TAG, "onProductClicked: ${product?.brand}")
        product?.let {
            findNavController().safeNavigate(MainFragmentDirections.actionMainFragmentToProductDetailFragment(it))
        }
    }


}
