package fr.atech.robotcom.ui.radiocommande;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import fr.atech.robotcom.R;

public class RadioCommandeFragment extends Fragment {

    private RadioCommandeViewModel radioCommandeViewModel;

    private Button stopButton;
    private View.OnClickListener stopButtonOnClickListener = v -> stopButtonClicked();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Associe le fragment au ViewModel
        radioCommandeViewModel = ViewModelProviders.of(this).get(RadioCommandeViewModel.class);

        // Affiche le fragment
        View root = inflater.inflate(R.layout.fragment_radiocommande, container, false);

        displayLog(root);

        stopButton = root.findViewById(R.id.button_rc_stop);
        stopButton.setOnClickListener(stopButtonOnClickListener);

        return root;
    }


    private void stopButtonClicked() {
        radioCommandeViewModel.sendCommand("stop");
    }

    private void displayLog(View root) {
        // Récupère la zone de log
        final TextView logText = root.findViewById(R.id.text_radiocommande_log);

        // Met à jour la zone de log avec le log du ViewModel quand il change
        radioCommandeViewModel.getLogContent().observe(getViewLifecycleOwner(), s -> logText.setText(s));
    }


}
