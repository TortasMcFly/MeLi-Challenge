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
import com.hector.melichallenge.domain.util.ErrorMessage
import com.hector.melichallenge.presentation.products.adapter.ProductAdapter
import com.hector.melichallenge.presentation.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val viewModel: ProductsViewModel by viewModels()
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private var products: MutableList<Product> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater)
        navController = findNavController()

        setUpStateFlow()
        setUpEventFlow()
        setUpRecyclerView()
        setUpSearchView()
        setUpOnClickListeners()

        return binding.root
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
            binding.viewNoResults.root.isVisible = products.isEmpty() && !state.loading
        }

    }

    private fun setUpEventFlow() = lifecycleScope.launchWhenStarted {

        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is ProductsViewModel.UIEvent.ShowError -> {
                    if(event.error.type == ErrorMessage.ErrorType.IO) {
                        binding.viewNoInternet.root.isVisible = true
                        return@collectLatest
                    }
                    Snackbar.make(binding.root, event.error.message, Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun setUpRecyclerView() {
        val adapter = ProductAdapter(products)
        adapter.onItemClickListener = { product ->
            val bundle = Bundle().apply {
                putString(Constants.NAV_ARGUMENT_PRODUCT_ID, product.id)
                putString(Constants.NAV_ARGUMENT_PRODUCT_INSTALLMENTS, product.getInstallments())
                putString(Constants.NAV_ARGUMENT_QUERY_SEARCH, binding.viewSearch.searchView.query.toString())
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

    private fun setUpOnClickListeners() {
        binding.viewSearch.backAction.isVisible = true
        binding.viewSearch.backAction.setOnClickListener {
            navController.popBackStack()
        }
        
        binding.viewNoInternet.buttonRetry.setOnClickListener {
            val query = binding.viewSearch.searchView.query.toString()
            binding.viewNoInternet.root.isVisible = false
            viewModel.onEvent(ProductsEvent.SearchProduct(query, 0))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}