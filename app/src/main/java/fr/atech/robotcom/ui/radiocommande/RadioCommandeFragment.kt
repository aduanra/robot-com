package fr.atech.robotcom.ui.radiocommande

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import fr.atech.robotcom.R

class RadioCommandeFragment : Fragment() {

    private lateinit var radioCommandeViewModel: RadioCommandeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        radioCommandeViewModel =
                ViewModelProviders.of(this).get(RadioCommandeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_radiocommande, container, false)
        val textView: TextView = root.findViewById(R.id.text_radiocommande)
        radioCommandeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view.findViewById<View>(R.id.button_home).setOnClickListener {
//            val action = HomeFragmentDirections
//                    .actionHomeFragmentToHomeSecondFragment("From RadioCommandeFragment")
//            NavHostFragment.findNavController(this@RadioCommandeFragment)
//                    .navigate(action)
//        }
    }
}
