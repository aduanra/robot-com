package fr.atech.robotcom.ui.robot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RobotViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is robot Fragment"
    }
    val text: LiveData<String> = _text
}