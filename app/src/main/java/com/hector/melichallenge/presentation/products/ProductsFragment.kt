package com.hector.melichallenge.presentation.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hector.melichallenge.R
import com.hector.melichallenge.databinding.FragmentProductsBinding
import com.hector.melichallenge.domain.model.Product
import com.hector.melichallenge.presentation.products.adapter.ProductAdapter
import com.hector.melichallenge.presentation.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var binding: FragmentProductsBinding
    private lateinit var navController: NavController
    private var products: MutableList<Product> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater)
        navController = findNavController()

        setUpRecyclerView()
        setUpSearchView()
        setUpStateFlow()
        setUpEventFlow()

        return binding.root
    }

    private fun setUpRecyclerView() {
        val adapter = ProductAdapter(products)
        adapter.onItemClickListener = { product ->
            val bundle = Bundle().apply {
                putString(Constants.NAV_ARGUMENT_PRODUCT_ID, product.id)
            }
            navController.navigate(
                R.id.action_productsFragment_to_productDetailFragment,
                bundle
            )
        }
        val linearLayoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
    }

    private fun setUpSearchView() {
        binding.viewSearch.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if(hasFocus) {
                navController.popBackStack()
            }
        }
    }

    private fun setUpStateFlow() = lifecycleScope.launchWhenStarted {

        viewModel.state.collectLatest { state ->
            binding.progressBar.isVisible = state.loading
            binding.recyclerView.isVisible = !state.loading
            binding.viewSearch.searchView.isFocusable = false
            binding.viewSearch.searchView.isEnabled = !state.loading

            state.products?.let {
                products.addAll(it)
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }

            binding.textViewTotalResults.text = "${state.searchDetail?.getTotal() ?: 0} resultados"
            binding.viewSearch.searchView.setQuery(state.query, false)
        }

    }

    private fun setUpEventFlow() = lifecycleScope.launchWhenStarted {

        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is ProductsViewModel.UIEvent.ShowError -> {
                    Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }
}