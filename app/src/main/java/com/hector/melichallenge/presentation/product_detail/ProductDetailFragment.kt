package com.hector.melichallenge.presentation.product_detail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.hector.melichallenge.R
import com.hector.melichallenge.databinding.FragmentProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private val viewModel: ProductDetailViewModel by viewModels()

    private lateinit var binding: FragmentProductDetailBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater)
        navController = findNavController()


        setUpStateFlow()
        setUpEventFlow()
        setUpSearchView()

        return binding.root
    }

    private fun setUpStateFlow() = lifecycleScope.launchWhenStarted {
        viewModel.state.collectLatest { state ->

            binding.content.isVisible = !state.loading
            binding.progressBar.isVisible = state.loading
            binding.viewSearch.searchView.setQuery(state.query, false)

            state.product?.let { product ->

                binding.textViewName.text = product.title
                binding.textViewPrice.text = "$ ${product.price}"
                binding.textViewInstallments.text = product.getInstallments() ?: ""


                val hasBrand = !product.getBrand().isNullOrBlank()
                binding.textViewBrand.text = if(hasBrand) product.getBrand() else ""
                binding.viewBrand.isVisible = hasBrand

                binding.textViewFull.visibility = if(product.isFullDelivery()) View.VISIBLE else View.INVISIBLE

                Glide
                    .with(binding.root.context)
                    .load(product.thumbnail)
                    .into(binding.imageViewProduct)

            }

        }
    }

    private fun setUpEventFlow() = lifecycleScope.launchWhenStarted {
        viewModel.eventFlow.collectLatest { event ->

            when(event) {
                is ProductDetailViewModel.UIEvent.ShowError -> {
                    Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun setUpSearchView() {
        binding.viewSearch.searchView.isFocusable = false
        binding.viewSearch.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if(hasFocus) {
                navController.popBackStack(R.id.searchFragment, false)
            }
        }
    }
}