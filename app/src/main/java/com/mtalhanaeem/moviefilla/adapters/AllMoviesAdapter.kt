package com.mtalhanaeem.moviefilla.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mtalhanaeem.moviefilla.R
import com.mtalhanaeem.moviefilla.models.AllMoviesModel


class AllMoviesAdapter(
    private val context: Context,
) : ListAdapter<AllMoviesModel, AllMoviesAdapter.AdapterViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        return AdapterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, context)
    }

    class AdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageAllMovies: ImageView = itemView.findViewById(R.id.imageAllMovies)
        private val nameAllMovies: TextView = itemView.findViewById(R.id.nameAllMovies)

        fun bind(item: AllMoviesModel, context: Context) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + item.imageAllMovieModel)
                .placeholder(R.drawable.dummy).into(imageAllMovies)
            nameAllMovies.text = item.nameAllMovieModel

            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("id", item.numberAllMovieModel)
                Navigation.findNavController(it)
                    .navigate(R.id.action_allMoviesFragment_to_movieDetailsFragment, bundle)
            }
        }

        companion object {
            fun create(parent: ViewGroup): AdapterViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_all_movies, parent, false)
                return AdapterViewHolder(view)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<AllMoviesModel>() {
        override fun areItemsTheSame(oldItem: AllMoviesModel, newItem: AllMoviesModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AllMoviesModel, newItem: AllMoviesModel): Boolean {
            return oldItem.idAllMovieModel == newItem.idAllMovieModel
        }
    }
}