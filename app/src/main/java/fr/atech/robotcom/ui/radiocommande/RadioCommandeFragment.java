package fr.atech.robotcom.ui.radiocommande;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.Random;

import fr.atech.robotcom.R;

public class RadioCommandeFragment extends Fragment {

    private RadioCommandeViewModel radioCommandeViewModel;

    // Logs
    private ScrollView logScroller;
    private TextView logText;

    // Boutons
    private Button stopButton;
    private View.OnClickListener stopButtonOnClickListener = v -> stopButtonClicked();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Associe le fragment au ViewModel
        radioCommandeViewModel = ViewModelProviders.of(this).get(RadioCommandeViewModel.class);

        // Affiche le fragment
        View root = inflater.inflate(R.layout.fragment_radiocommande, container, false);

        initLogDisplay(root);

        stopButton = root.findViewById(R.id.button_rc_stop);
        stopButton.setOnClickListener(stopButtonOnClickListener);

        return root;
    }


    private void stopButtonClicked() {
//        radioCommandeViewModel.sendCommand("stop");
        radioCommandeViewModel.log("stop" + new Random().nextInt());
        scrollLogToBottom();
    }

    private void initLogDisplay(View root) {
        // Récupère la zone de log
        logText = root.findViewById(R.id.text_radiocommande_log);

        // Met à jour la zone de log avec le log du ViewModel quand il change
        radioCommandeViewModel.getLogContent().observe(getViewLifecycleOwner(), s -> updateLog(s));
    }

    private void updateLog(String s) {
        logText.setText(s);
        scrollLogToBottom();
    }

    private void scrollLogToBottom() {
        logScroller.post(new Runnable() {
            public void run() {
                logScroller.smoothScrollTo(0, logText.getBottom());
            }
        });
    }


}
