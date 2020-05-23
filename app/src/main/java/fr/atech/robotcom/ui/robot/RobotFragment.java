package fr.atech.robotcom.ui.robot;

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

import fr.atech.robotcom.R;

import static fr.atech.robotcom.reseau.NetworkUtils.hasNetworkConnectivity;

public class RobotFragment extends Fragment {

    private RobotViewModel robotViewModel;

    private Button startServerButton;
    private View.OnClickListener startServerOnClickListener = v -> startServerButtonClicked();

    // Logs
    private ScrollView logScroller;
    private TextView logText;

    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        robotViewModel = ViewModelProviders.of(this).get(RobotViewModel.class);
        view = inflater.inflate(R.layout.fragment_robot, container, false);

        // Connexion
        startServerButton =  view.findViewById(R.id.button_robot_connect);
        startServerButton.setOnClickListener(startServerOnClickListener);

        // Log
        initLogDisplay();

        return view;
    }

    private void startServerButtonClicked() {

        if (!hasNetworkConnectivity(robotViewModel, getActivity().getApplicationContext())) {
            robotViewModel.log("Pas de connexion réseau");
            return;
        }

        robotViewModel.startServer();
    }


    private void initLogDisplay() {
        // Récupère la zone de log
        logText = view.findViewById(R.id.text_robot_log);
        logScroller = view.findViewById(R.id.scroller_robot_log);

        // Met à jour la zone de log avec le log du ViewModel quand il change
        robotViewModel.getLogContent().observe(getViewLifecycleOwner(), s -> updateLog(s));
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
