package fr.atech.robotcom.ui.radiocommande;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.atech.robotcom.reseau.MinaTcpClient;
import fr.atech.robotcom.reseau.RadioCommandeClientHandler;


public class RadioCommandeViewModel extends ViewModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(RadioCommandeViewModel.class);

    private MinaTcpClient client;
    private RadioCommandeClientHandler tcpClientHandler;
    private String hostname = "localhost";
    private Integer port = null;

    private MutableLiveData<String> logContent = new MutableLiveData<>();

    public RadioCommandeViewModel() {

    }

    public void initTcpCommunication() {
        // Associe le view model au TCP connexionTask handler pour qu'il puisse l'appeler quand des événement réseau surviennent
        tcpClientHandler = new RadioCommandeClientHandler(this);

        // Démarre le connexionTask TCP
        client = new MinaTcpClient(hostname, port, tcpClientHandler);
    }

    // Ajoute une ligne au log
    public void log(final String logToDisplay) {
        LOGGER.info("Log rc: " + logToDisplay);
        logContent.setValue(logContent.getValue() + "\n" + logToDisplay);
    }

    public void sendCommand(final String command){
        client.sendMessage(command);
    }

    public LiveData<String> getLogContent() {
        return logContent;
    }

}