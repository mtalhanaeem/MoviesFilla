package com.mtalhanaeem.moviefilla.fragmentScreens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mtalhanaeem.moviefilla.R
import com.mtalhanaeem.moviefilla.adapters.AllMoviesAdapter
import com.mtalhanaeem.moviefilla.databinding.FragmentAllMoviesBinding
import com.mtalhanaeem.moviefilla.utils.BaseFragment
import com.mtalhanaeem.moviefilla.viewmodels.AllMoviesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AllMoviesFragment : BaseFragment<FragmentAllMoviesBinding>() {

    private val allMoviesViewModel: AllMoviesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return getPersistentView(inflater, container, R.layout.fragment_all_movies)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasInitializedRootView) {
            hasInitializedRootView = true

            onInit()
        }
    }


    private fun onInit() {

        val adapter = AllMoviesAdapter(requireContext())
        binding.recyclerAllMovies.adapter = adapter
        binding.recyclerAllMovies.layoutManager = LinearLayoutManager(requireContext())

        allMoviesViewModel.getAllMoviesVM.observe(viewLifecycleOwner, {
            it?.let {
                if (it.isNotEmpty()) {
                    adapter.submitList(it.toList())
                } else {
                    showMessage("Please Wait. Getting From Server")
                    allMoviesViewModel.getAllMoviesServerVM.observe(
                        viewLifecycleOwner,
                        { listFromServer ->
                            listFromServer?.let {

                            }
                        })
                }
            }
        })
    }
}