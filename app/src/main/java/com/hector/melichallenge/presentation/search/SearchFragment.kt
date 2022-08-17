package com.hector.melichallenge.presentation.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.hector.melichallenge.R
import com.hector.melichallenge.databinding.FragmentSearchBinding
import com.hector.melichallenge.presentation.utils.Constants

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater)
        navController = findNavController()

        setUpTextChangeListener()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.viewSearch.searchView.requestFocus()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)
    }

    private fun setUpTextChangeListener() {
        binding.viewSearch.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if(!query.isNullOrBlank()) {
                    val bundle = Bundle().apply {
                        putString(Constants.NAV_ARGUMENT_QUERY_SEARCH, query)
                    }
                    navController.navigate(
                        R.id.action_searchFragment_to_productsFragment,
                        bundle
                    )
                }
                return false
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}