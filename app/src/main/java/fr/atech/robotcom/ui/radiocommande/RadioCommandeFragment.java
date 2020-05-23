package fr.atech.robotcom.ui.radiocommande;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import fr.atech.robotcom.R;
import fr.atech.robotcom.ui.Loggable;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static fr.atech.robotcom.reseau.NetworkUtils.hasNetworkConnectivity;

public class RadioCommandeFragment extends Fragment {

    private RadioCommandeViewModel radioCommandeViewModel;

    // Connexion
    private EditText hostnameEditText;
    private Button connectButton;
    private View.OnClickListener connectButtonOnClickListener = v -> connectButtonClicked();


    // Logs
    private ScrollView logScroller;
    private TextView logText;

    // Commandes
    private Button stopButton;
    private View.OnClickListener stopButtonOnClickListener = v -> stopButtonClicked();

    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Associe le fragment au ViewModel
        radioCommandeViewModel = ViewModelProviders.of(this).get(RadioCommandeViewModel.class);

        // Affiche le fragment
        view = inflater.inflate(R.layout.fragment_radiocommande, container, false);

        // Connexion
        connectButton =  view.findViewById(R.id.button_rc_connect);
        connectButton.setOnClickListener(connectButtonOnClickListener);
        hostnameEditText = view.findViewById(R.id.editText_rc_hostname);

        // Commandes
        stopButton = view.findViewById(R.id.button_rc_stop);
        stopButton.setOnClickListener(stopButtonOnClickListener);

        // Log
        initLogDisplay();

        return view;
    }


    private void stopButtonClicked() {
        radioCommandeViewModel.sendCommand("stop");
    }

    private void connectButtonClicked() {
        final String hostname = hostnameEditText.getText().toString();
        if (!isHostnameCorrect(hostname)) return;
        if (!hasNetworkConnectivity(radioCommandeViewModel, getActivity().getApplicationContext())) return;

        hostnameEditText.setSelected(false);
        hostnameEditText.clearFocus();
        view.findViewById(R.id.layout_rc).requestFocus();

        radioCommandeViewModel.initTcpCommunication(hostname.trim());
    }


    private boolean isHostnameCorrect(String hostname) {
        if(hostname==null || hostname.trim()==""){
            radioCommandeViewModel.log("Saisir une adresse IP");
            return false;
        }
        return true;
    }


    private void initLogDisplay() {
        // Récupère la zone de log
        logText = view.findViewById(R.id.text_radiocommande_log);
        logScroller = view.findViewById(R.id.scroller_rc_log);

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
