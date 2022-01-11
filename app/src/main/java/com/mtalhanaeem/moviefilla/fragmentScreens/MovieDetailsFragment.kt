package com.mtalhanaeem.moviefilla.fragmentScreens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.mtalhanaeem.moviefilla.R
import com.mtalhanaeem.moviefilla.databinding.FragmentMovieDetailsBinding
import com.mtalhanaeem.moviefilla.utils.BaseFragment
import com.mtalhanaeem.moviefilla.viewmodels.AllMoviesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {

    private lateinit var movieID: String
    private val allMoviesViewModel: AllMoviesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return getPersistentView(inflater, container, R.layout.fragment_movie_details)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasInitializedRootView) {
            hasInitializedRootView = true

            onInit()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onInit() {

        movieID = arguments?.getString("id")!!

        allMoviesViewModel.getMovieDetailsVM(movieID).observe(viewLifecycleOwner, {
            it?.let {
                if (it.isNotEmpty()) {
                    Glide.with(requireActivity())
                        .load("https://image.tmdb.org/t/p/w500/" + it[0].imageMovieDetailsModel)
                        .placeholder(
                            R.drawable.dummy
                        ).into(binding.imageMovieDetails)
                    binding.nameMovieDetails.text = it[0].nameMovieDetailsModel
                    binding.tagLineMovieDetails.text = it[0].tagMovieDetailsModel
                    binding.dateMovieDetails.text = it[0].dateMovieDetailsModel
                    binding.ratingMovieDetails.text = it[0].ratingMovieDetailsModel
                    binding.statusMovieDetails.text = it[0].statusMovieDetailsModel
                    binding.durationMovieDetails.text = it[0].durationMovieDetailsModel + " Minutes"
                    binding.overViewMovieDetails.text = it[0].overviewMovieDetailsModel
                } else {
                    showMessage("Please Wait. Getting From Server")
                    allMoviesViewModel.getMovieDetailsServerVM(movieID)
                        .observe(viewLifecycleOwner, { listFromServer ->
                            listFromServer?.let {

                            }
                        })
                }
            }
        })

        binding.backMovieDetails.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

    }
}