package fr.atech.robotcom.ui.configuration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import fr.atech.robotcom.R

class ConfigurationFragment : Fragment() {

    private lateinit var configurationViewModel: ConfigurationViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        configurationViewModel =
                ViewModelProviders.of(this).get(ConfigurationViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_configuration, container, false)
        val textView: TextView = root.findViewById(R.id.text_configuration)
        configurationViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
