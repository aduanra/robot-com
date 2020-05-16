package fr.atech.robotcom.ui.radiocommande

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RadioCommandeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is radiocommande Fragment"
    }
    val text: LiveData<String> = _text
}