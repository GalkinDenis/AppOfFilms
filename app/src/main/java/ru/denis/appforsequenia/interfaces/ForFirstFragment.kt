package ru.denis.appforsequenia.interfaces

import android.view.View
import androidx.navigation.NavDirections
import ru.denis.appforsequenia.repository.MoviesRecyclerViewItem

interface ForFirstFragment {
    fun initListOfFilmsToAdapter(homeItemsList: MutableList<MoviesRecyclerViewItem>)
    fun loadDescriptionFragment(textButton: View, action: NavDirections)
    fun showToast()
}