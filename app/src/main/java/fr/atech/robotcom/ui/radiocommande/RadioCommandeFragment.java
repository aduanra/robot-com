package fr.atech.robotcom.ui.radiocommande;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
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

import static android.content.Context.CONNECTIVITY_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class RadioCommandeFragment extends Fragment {

    private RadioCommandeViewModel radioCommandeViewModel;

    // Logs
    private ScrollView logScroller;
    private TextView logText;

    // Boutons
    private Button stopButton;
    private View.OnClickListener stopButtonOnClickListener = v -> stopButtonClicked();

    private Button connectButton;
    private View.OnClickListener connectButtonOnClickListener = v -> connectButtonClicked();

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

        connectButton =  root.findViewById(R.id.button_rc_connect);
        connectButton.setOnClickListener(connectButtonOnClickListener);

        return root;
    }


    private void stopButtonClicked() {
        radioCommandeViewModel.sendCommand("stop");
    }

    private void connectButtonClicked() {

        final ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
            boolean wifi = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            boolean mobile = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            radioCommandeViewModel.log("Wifi actif : " + wifi);
            radioCommandeViewModel.log("Réseau mobile actif : " + mobile);
        } else {
            radioCommandeViewModel.log("Pas de connexion réseau...");
            return;
        }

        radioCommandeViewModel.initTcpCommunication();
    }

    private void initLogDisplay(View root) {
        // Récupère la zone de log
        logText = root.findViewById(R.id.text_radiocommande_log);
        logScroller = root.findViewById(R.id.scroller_rc_log);

        // Met à jour la zone de log avec le log du ViewModel quand il change
        radioCommandeViewModel.getLogContent().observe(getViewLifecycleOwner(), s -> updateLog(s));
    }

    // Met à jour le log et scrolle
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
