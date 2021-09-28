package ru.denis.appforsequenia.filmslistfragment

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.squareup.picasso.Picasso
import ru.denis.appforsequenia.R
import ru.denis.appforsequenia.databinding.ItemFilmsBinding
import ru.denis.appforsequenia.databinding.ItemGenresBinding
import ru.denis.appforsequenia.databinding.ItemTitleBinding
import ru.denis.appforsequenia.interfaces.ForFirstFragment
import ru.denis.appforsequenia.presenter.Presenter
import ru.denis.appforsequenia.repository.MoviesRecyclerViewItem

sealed class FilmsRecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {


    //Холдер заголовка.
    class TitleViewHolder(private val binding: ItemTitleBinding) : FilmsRecyclerViewHolder(binding){
        fun bind(title: MoviesRecyclerViewItem.Title){
            binding.title.text = title.title
        }
    }

    //Холдер списка жанров.
    class GenresViewHolder(private val binding: ItemGenresBinding) : FilmsRecyclerViewHolder(binding){
        fun bind(
            genres: MoviesRecyclerViewItem.Genres,
            presenter: Presenter,
            position: Int,
            filmRecyclerViewAdapter: FilmRecyclerViewAdapter,
            fragment1view: ForFirstFragment?
        ){

            if(position == filmRecyclerViewAdapter.positionClick) {
                binding.genresItemButton.setBackgroundColor(Color.rgb(80, 208, 222))
                filmRecyclerViewAdapter.button = binding.genresItemButton
            }else{
                binding.genresItemButton.setBackgroundColor(Color.rgb(95, 95, 95))
            }

            with(binding) {
                genresItemButton.text = genres.itemGenres
                    genresItemButton.setOnClickListener {
                        filmRecyclerViewAdapter.button?.setBackgroundColor(Color.rgb(95, 95, 95))
                        genresItemButton.setBackgroundColor(Color.rgb(80, 208, 222))
                        filmRecyclerViewAdapter.positionClick = position
                        presenter.loadDataOfFilms(genresItemButton.text as String?,
                            fragment1view as FilmsListFragment
                        )
                }
            }


        }
    }

    //Холдер обложек фильмов.
    class FilmsViewHolder(private val binding: ItemFilmsBinding) : FilmsRecyclerViewHolder(binding) {
        fun bind(films: MoviesRecyclerViewItem.Movies, presenter: Presenter) {

            binding.nameOfFilm.setTextColor(Color.WHITE)
            binding.nameOfFilm.setBackgroundColor(Color.argb(100, 59, 59, 59))
            binding.nameOfFilm.text = films.itemMovies[0].localized_name.toString()
            Picasso.get().load(
                "https://st.kp.yandex.net/images/film_iphone/"
                        + films.itemMovies[0].image_url?.substringAfterLast('/')
            ).into(binding.imageView)

            //Навигация на фрагмент детализации.
            binding.imageView.setOnClickListener {
                val action = FilmsListFragmentDirections.actionFilmsListFragmentToDescriptionFragment(
                    title = films.itemMovies[0].localized_name.toString(),
                    engName = films.itemMovies[0].name,
                    year = films.itemMovies[0].year.toString(),
                    rank = films.itemMovies[0].rating.toString(),
                    description = films.itemMovies[0].description.toString(),
                    img = films.itemMovies[0].image_url.toString()
                    )
                presenter.loadDescriptionFragment(it, action)
            }


            if (films.itemMovies.size == 2) {
                binding.nameOfFilm2.setTextColor(Color.WHITE)
                binding.nameOfFilm2.setBackgroundColor(Color.argb(100, 59, 59, 59))
                binding.nameOfFilm2.text = films.itemMovies[1].localized_name.toString()
                Picasso.get().load(
                    "https://st.kp.yandex.net/images/film_iphone/"
                            + films.itemMovies[1].image_url?.substringAfterLast('/')
                ).into(binding.imageView2)

                //Навигация на фрагмент детализации.
                binding.imageView2.setOnClickListener {
                    val action = FilmsListFragmentDirections.actionFilmsListFragmentToDescriptionFragment(
                        title = films.itemMovies[1].localized_name.toString(),
                        engName = films.itemMovies[1].name,
                        year = films.itemMovies[1].year.toString(),
                        rank = films.itemMovies[1].rating.toString(),
                        description = films.itemMovies[1].description.toString(),
                        img = films.itemMovies[1].image_url.toString()
                    )
                    presenter.loadDescriptionFragment(it, action)
                }

            } else {
                binding.nameOfFilm2.setTextColor(Color.parseColor("#1f1f1f"))
                binding.nameOfFilm2.setBackgroundColor(Color.alpha(0))
                binding.imageView2.setImageResource(R.drawable.back)
            }
        }
    }
}