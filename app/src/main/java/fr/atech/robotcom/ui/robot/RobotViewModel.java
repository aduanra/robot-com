package fr.atech.robotcom.ui.robot;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import fr.atech.robotcom.reseau.MinaTcpClient;
import fr.atech.robotcom.reseau.MinaTcpServer;
import fr.atech.robotcom.reseau.NetworkUtils;
import fr.atech.robotcom.reseau.RadioCommandeClientHandler;
import fr.atech.robotcom.reseau.RobotServerHandler;
import fr.atech.robotcom.ui.Loggable;
import fr.atech.robotcom.ui.radiocommande.RadioCommandeViewModel;

public class RobotViewModel extends ViewModel implements Loggable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RobotViewModel.class);

    private MinaTcpServer server;
    private RobotServerHandler tcpServerHandler;

    private Integer port = MinaTcpServer.PORT;

    private MutableLiveData<String> logContent = new MutableLiveData<>();
    private MutableLiveData<String> lastClientCommand = new MutableLiveData<>();

    public RobotViewModel() {

    }


    public void startServer() {

        // Associe le view model au TCP server handler pour qu'il puisse l'appeler quand des événement réseau surviennent
        tcpServerHandler = new RobotServerHandler(this);

        // Démarre la connexion TCP
        try {
            server = new MinaTcpServer(null, tcpServerHandler);

        } catch (IOException e) {
            log("Echec de démarrage du serveur sur le port " + port + ": " + e.getCause().getMessage());
            LOGGER.error("Echec de démarrage du serveur sur le port " + port, e);
            return;
        }

        log("Serveur démarré sur " + NetworkUtils.getIPAddress(true) + ":" + port);
    }

    // Ajoute une ligne au log
    @Override
    public void log(final String logToDisplay) {
        LOGGER.info("Log robot: " + logToDisplay);
        logContent.postValue(logContent.getValue() + "\n" + logToDisplay);
    }

    public LiveData<String> getLogContent() {
        return logContent;
    }

    public MutableLiveData<String> getLastClientCommand() { return lastClientCommand; }

    public void setLastClientCommand(String lastClientCommand) {  this.lastClientCommand.postValue(lastClientCommand); }
}