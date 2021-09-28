package ru.denis.appforsequenia.presenter

import android.view.View
import androidx.navigation.NavDirections
import ru.denis.appforsequenia.filmslistfragment.FilmsListFragment
import ru.denis.appforsequenia.interfaces.ForFirstFragment
import ru.denis.appforsequenia.model.Model
import ru.denis.appforsequenia.repository.MoviesRecyclerViewItem

class Presenter(private val model: Model) {

    var filmsListView: ForFirstFragment? = null
    fun attachView(firstFragment: ForFirstFragment) {
        filmsListView = firstFragment
        model.attachPresenter(this)
    }

    fun detachView() {
        filmsListView = null
        model.detachPresenter()
    }

    //Запрос к модели по загрузке данных.
    fun loadDataOfFilms(textButton: String?, filmsListFragment: FilmsListFragment) {
        model.loadDataOfFilms(textButton, filmsListFragment)
    }

    //Обновление списка.
    fun loadDataOfFilmsCompleted(homeItemsList: MutableList<MoviesRecyclerViewItem>) {
        filmsListView?.initListOfFilmsToAdapter(homeItemsList)
    }

    //Навигация на фрагмент детализации.
    fun loadDescriptionFragment(textButton: View, action: NavDirections) {
        filmsListView?.loadDescriptionFragment(textButton, action)
    }

    fun showToast() {
        filmsListView?.showToast()
    }

}