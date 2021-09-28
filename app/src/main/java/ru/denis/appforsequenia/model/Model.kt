package ru.denis.appforsequenia.model

import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*
import retrofit2.Response
import ru.denis.appforsequenia.filmslistfragment.FilmsListFragment
import ru.denis.appforsequenia.interfaces.ForRepository
import ru.denis.appforsequenia.presenter.Presenter
import ru.denis.appforsequenia.repository.MoviesRecyclerViewItem

class Model(private val repository: ForRepository) {

    private var presenterView: Presenter? = null
    fun attachPresenter(presenter: Presenter) {
        presenterView = presenter
        repository.attachModel(this)
    }

    fun detachPresenter() {
        presenterView = null
        repository.detachModel()
    }

    //Обращение к JSON за данными по фильмам.
    fun loadDataOfFilms(textButton: String?, filmsListFragment: FilmsListFragment) {
        val homeItemsList = mutableListOf<MoviesRecyclerViewItem>()
        val listOfGenres = mutableListOf<String>()
        var filmItemsList = mutableListOf<MoviesRecyclerViewItem.Films>()

        CoroutineScope(Dispatchers.IO).launch {

            //Обращение к репозиторию за JSON.
            val moviesDeferred: Response<MoviesRecyclerViewItem.ItemJson>
            var response: Response<MoviesRecyclerViewItem.ItemJson>? = null

            try {
                moviesDeferred = repository.loadFromRepository()
                response = moviesDeferred

                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        val itemsFilmsEntity =
                            response.body()?.films?.sortedBy { it.localized_name }
                        if (itemsFilmsEntity != null) {

                            homeItemsList.add(MoviesRecyclerViewItem.Title("Жанры"))

                            //Формирование и сортировка списка жанров.
                            for (i in itemsFilmsEntity.indices) {
                                for (z in itemsFilmsEntity[i].genres?.indices!!) {
                                    itemsFilmsEntity[i].genres?.get(z)?.let { listOfGenres.add(it) }
                                }
                            }
                            val listOfGenresSorted = listOfGenres.distinct().sorted()
                            for (i in listOfGenresSorted.indices) {
                                homeItemsList.add(MoviesRecyclerViewItem.Genres(listOfGenresSorted[i]))
                            }
                            //

                            homeItemsList.add(MoviesRecyclerViewItem.Title("Фильмы"))

                            //Формирование списка обложек фильмов в RecyclerView.
                            /////////////////////
                            for (i in itemsFilmsEntity.indices) {
                                when {
                                    itemsFilmsEntity[i].genres?.contains(textButton) == true -> filmItemsList.add(
                                        itemsFilmsEntity[i]
                                    )
                                }
                                if (filmItemsList.size == 2) {
                                    homeItemsList.add(MoviesRecyclerViewItem.Movies(filmItemsList))
                                    filmItemsList = mutableListOf()
                                }
                                if (i == itemsFilmsEntity.size - 1) {
                                    if (filmItemsList.isNotEmpty()) {
                                        homeItemsList.add(
                                            MoviesRecyclerViewItem.Movies(
                                                filmItemsList
                                            )
                                        )
                                        filmItemsList = mutableListOf()
                                    }
                                }
                            }
                            /////////////////////

                        } else {
                            Log.e("RETROFIT_ERROR", response.code().toString())
                        }

                        //Обновление списка.
                        presenterView?.loadDataOfFilmsCompleted(homeItemsList)

                    }
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    presenterView?.showToast()
                }
            }
        }
    }
}