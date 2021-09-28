package ru.denis.appforsequenia.filmslistfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.denis.appforsequenia.databinding.FirstFragmentBinding
import ru.denis.appforsequenia.interfaces.ForFirstFragment
import ru.denis.appforsequenia.model.Model
import ru.denis.appforsequenia.presenter.Presenter
import ru.denis.appforsequenia.repository.MoviesRecyclerViewItem
import ru.denis.appforsequenia.repository.Repository

open class FilmsListFragment : Fragment(), ForFirstFragment {

    private var _binding: FirstFragmentBinding? = null
    private val binding get() = _binding!!
    private var presenter: Presenter? = null
    private var filmRecyclerViewAdapter: FilmRecyclerViewAdapter? = null

    override fun showToast() {
        Toast.makeText(
              context,
             "Отсутствует подключеие к интернету.",
              Toast.LENGTH_LONG
             ).show()
    }

    //Обновление списка.
    override fun initListOfFilmsToAdapter(homeItemsList: MutableList<MoviesRecyclerViewItem>) {
        filmRecyclerViewAdapter?.items = homeItemsList
    }

    //Навигация на фрагмент детализации.
    override fun loadDescriptionFragment(textButton: View, action: NavDirections) {
        textButton.findNavController().navigate(action)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = Presenter(Model(Repository()))
        presenter?.attachView(this)
        filmRecyclerViewAdapter = FilmRecyclerViewAdapter(presenter!!)
        presenter!!.loadDataOfFilms("", this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FirstFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Главная"

            binding.recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = filmRecyclerViewAdapter
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
        filmRecyclerViewAdapter?.button = null
        _binding = null
        presenter = null
    }

}