package ru.denis.appforsequenia.filmslistfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import ru.denis.appforsequenia.R
import ru.denis.appforsequenia.databinding.ItemFilmsBinding
import ru.denis.appforsequenia.databinding.ItemGenresBinding
import ru.denis.appforsequenia.databinding.ItemTitleBinding
import ru.denis.appforsequenia.presenter.Presenter
import ru.denis.appforsequenia.repository.MoviesRecyclerViewItem

class FilmRecyclerViewAdapter(private var presenter: Presenter) : RecyclerView.Adapter<FilmsRecyclerViewHolder>() {

    var positionClick: Int = 0
    var button: Button? = null

    var items = mutableListOf<MoviesRecyclerViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is MoviesRecyclerViewItem.Title -> R.layout.item_title
            is MoviesRecyclerViewItem.Genres -> R.layout.item_genres
            is MoviesRecyclerViewItem.Movies -> R.layout.item_films
            else -> 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsRecyclerViewHolder {
        return when(viewType){
            R.layout.item_title -> FilmsRecyclerViewHolder.TitleViewHolder(
                ItemTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_genres -> FilmsRecyclerViewHolder.GenresViewHolder(
                ItemGenresBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_films -> FilmsRecyclerViewHolder.FilmsViewHolder(
                ItemFilmsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: FilmsRecyclerViewHolder, position: Int) {
        when(holder){
            is FilmsRecyclerViewHolder.TitleViewHolder -> holder.bind(items[position] as MoviesRecyclerViewItem.Title)

            is FilmsRecyclerViewHolder.GenresViewHolder -> {
                holder.bind(items[position] as MoviesRecyclerViewItem.Genres, presenter, position, this, presenter.filmsListView)
            }

            is FilmsRecyclerViewHolder.FilmsViewHolder -> {
                holder.bind(items[position] as MoviesRecyclerViewItem.Movies, presenter)
            }
        }
    }

}