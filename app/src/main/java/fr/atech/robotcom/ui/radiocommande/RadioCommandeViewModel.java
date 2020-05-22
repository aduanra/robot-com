package fr.atech.robotcom.ui.radiocommande;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.atech.robotcom.reseau.MinaTcpClient;
import fr.atech.robotcom.reseau.RadioCommandeClientHandler;

public class RadioCommandeViewModel extends ViewModel {

    private MinaTcpClient client;
    private RadioCommandeClientHandler tcpClientHandler;
    private String hostname = "192.168.1.3";
    private Integer port = null;

    private MutableLiveData<String> logContent;

    public RadioCommandeViewModel() {
        logContent = new MutableLiveData<>();
        logContent.setValue("Ceci est mon log...");

        //initTcpCommunication();
    }

    private void initTcpCommunication() {
        // Associe le view model au TCP client handler pour qu'il puisse l'appeler quand des événement réseau surviennent
        tcpClientHandler = new RadioCommandeClientHandler(this);

        // Démarre le client TCP
        client = new MinaTcpClient(hostname, port, tcpClientHandler);
    }

    // Ajoute une ligne au log
    public void log(final String logToDisplay) {
        logContent.setValue(logContent.getValue() + "\n" + logToDisplay);
    }

    public void sendCommand(final String command){
        client.sendMessage(command);
    }

    public LiveData<String> getLogContent() {
        return logContent;
    }

}