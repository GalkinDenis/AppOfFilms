package ru.denis.appforsequenia.repository

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.denis.appforsequenia.interfaces.ForRepository
import ru.denis.appforsequenia.model.Model

class Repository:  ForRepository {

    private var modelView: Model? = null
    override fun attachModel(model: Model) {
        modelView = model
    }

    override fun detachModel() {
        modelView = null
    }

    override suspend fun loadFromRepository() : Response<MoviesRecyclerViewItem.ItemJson> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://s3-eu-west-1.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(GetJson::class.java)

        return service.getItemJson("sequeniatesttask/films.json")
    }

}
