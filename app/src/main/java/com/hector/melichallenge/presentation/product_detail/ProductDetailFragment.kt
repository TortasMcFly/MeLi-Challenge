package com.hector.melichallenge.presentation.product_detail

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
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.hector.melichallenge.R
import com.hector.melichallenge.databinding.FragmentProductDetailBinding
import com.hector.melichallenge.domain.model.ProductPicture
import com.hector.melichallenge.domain.util.ErrorMessage
import com.hector.melichallenge.presentation.product_detail.adapter.ImageViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private val viewModel: ProductDetailViewModel by viewModels()

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater)
        navController = findNavController()


        setUpStateFlow()
        setUpEventFlow()
        setUpSearchView()
        setUpOnClickListeners()

        return binding.root
    }

    private fun setUpStateFlow() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        viewModel.state.collectLatest { state ->

            binding.content.isVisible = !state.loading
            binding.progressBar.isVisible = state.loading
            binding.viewSearch.searchView.setQuery(state.query, false)
            binding.textViewInstallments.text = state.installments

            state.product?.let { product ->

                binding.textViewName.text = product.title
                binding.textViewPrice.text = "$ ${product.price}"

                val hasBrand = !product.getBrand().isNullOrBlank()
                binding.textViewBrand.text = if(hasBrand) product.getBrand() else ""
                binding.viewBrand.isVisible = hasBrand

                binding.textViewFull.visibility = if(product.isFullDelivery()) View.VISIBLE else View.INVISIBLE
                setUpViewPager(product.pictures)
            }

        }
    }

    private fun setUpEventFlow() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        viewModel.eventFlow.collectLatest { event ->

            when(event) {
                is ProductDetailViewModel.UIEvent.ShowError -> {
                    if(event.error.type == ErrorMessage.ErrorType.IO) {
                        Snackbar.make(binding.root, getString(R.string.text_label_no_internet), Snackbar.LENGTH_LONG).show()
                        return@collectLatest
                    }
                    Snackbar.make(binding.root, event.error.message, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpViewPager(pictures: List<ProductPicture>) {
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPager.adapter = ImageViewPagerAdapter(pictures)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.textViewPage.text = "${position+1}/${pictures.size}"
            }
        })
    }

    private fun setUpSearchView() {
        binding.viewSearch.searchView.isFocusable = false
        binding.viewSearch.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if(hasFocus) {
                navController.popBackStack(R.id.searchFragment, false)
            }
        }
    }

    private fun setUpOnClickListeners() {
        binding.viewSearch.backAction.isVisible = true
        binding.viewSearch.backAction.setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}