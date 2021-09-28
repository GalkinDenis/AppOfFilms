package ru.denis.appforsequenia.interfaces

import retrofit2.Response
import ru.denis.appforsequenia.model.Model
import ru.denis.appforsequenia.repository.MoviesRecyclerViewItem

interface ForRepository {
    fun attachModel(model: Model)
    fun detachModel()
    suspend fun loadFromRepository() : Response<MoviesRecyclerViewItem.ItemJson>
}