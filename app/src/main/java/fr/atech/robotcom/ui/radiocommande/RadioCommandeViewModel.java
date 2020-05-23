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
import fr.atech.robotcom.reseau.MinaTcpServer;
import fr.atech.robotcom.reseau.NetworkUtils;
import fr.atech.robotcom.reseau.RadioCommandeClientHandler;
import fr.atech.robotcom.ui.Loggable;


public class RadioCommandeViewModel extends ViewModel implements Loggable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RadioCommandeViewModel.class);

    private MinaTcpClient client;
    private RadioCommandeClientHandler tcpClientHandler;
    private Integer port = MinaTcpServer.PORT;

    private MutableLiveData<String> logContent = new MutableLiveData<>();
    private MutableLiveData<String> lastServerResponse = new MutableLiveData<>();

    public RadioCommandeViewModel() {

    }

    public void initTcpCommunication(final String hostname) {
        if(client!=null) return; // Déjà connecté

        // Associe le view model au TCP client handler pour qu'il puisse l'appeler quand des événement réseau surviennent
        tcpClientHandler = new RadioCommandeClientHandler(this);

        // Démarre la connexion TCP
        log("IP du client : " + NetworkUtils.getIPAddress(true));
        client = new MinaTcpClient(hostname, port, tcpClientHandler, this);
    }

    // Ajoute une ligne au log de la radio commande
    @Override
    public void log(final String logToDisplay) {
        LOGGER.info("Log rc: " + logToDisplay);
        logContent.postValue(logContent.getValue() + "\n" + logToDisplay);
    }

    // Envoie une commmande au serveur
    public void sendCommand(final String command){
        client.sendMessage(command.trim());
    }

    public LiveData<String> getLogContent() {
        return logContent;
    }

    public void setLastServerResponse(final String serverResponse) {
        this.lastServerResponse.postValue(serverResponse);
    }

    public MutableLiveData<String> getLastServerResponse() {
        return lastServerResponse;
    }
}