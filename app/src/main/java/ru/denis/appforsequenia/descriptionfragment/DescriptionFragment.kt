package ru.denis.appforsequenia.descriptionfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import ru.denis.appforsequenia.databinding.DescriptionBinding

class DescriptionFragment : Fragment() {

    private var _binding: DescriptionBinding? = null

    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DescriptionBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            with(binding) {
                (activity as AppCompatActivity?)!!.supportActionBar!!.title = it.getString("title")

                Picasso.get().load(
                    "https://st.kp.yandex.net/images/film_iphone/"
                            + it.getString("img")?.substringAfterLast('/')
                ).into(binding.imageView)

                this.textView.text = it.getString("engName")
                this.textView2.text = it.getString("year")
                this.textView3.text = it.getString("rank")
                this.textView4.text = it.getString("description")

            }
            //letterId = it.getString(LETTER).toString()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}