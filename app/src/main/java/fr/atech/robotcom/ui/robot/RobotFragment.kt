package fr.atech.robotcom.ui.robot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import fr.atech.robotcom.R

class RobotFragment : Fragment() {

    private lateinit var robotViewModel: RobotViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        robotViewModel =
                ViewModelProviders.of(this).get(RobotViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_infos_robot, container, false)
        val textView: TextView = root.findViewById(R.id.text_infos_robot_title)
        robotViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
