package ru.denis.appforsequenia.repository

sealed class MoviesRecyclerViewItem {

    class Title(
        val title: String
    ) : MoviesRecyclerViewItem()

    class Genres(
        val itemGenres: String? = null
    ) : MoviesRecyclerViewItem()

    class Movies(
        val itemMovies: MutableList<Films>
    ) : MoviesRecyclerViewItem()

    class ItemJson(
        val films: List<Films>
    ) : MoviesRecyclerViewItem()

    class Films(
        val id: Long,
        val localized_name: String? = null,
        val name: String,
        val year: Long,
        val rating: Double? = null,
        val image_url: String? = null,
        val description: String? = null,
        val genres: List<String>? = null
    ) : MoviesRecyclerViewItem()

}