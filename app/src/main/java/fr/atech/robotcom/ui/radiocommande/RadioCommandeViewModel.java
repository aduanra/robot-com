package fr.atech.robotcom.ui.radiocommande;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RadioCommandeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RadioCommandeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}