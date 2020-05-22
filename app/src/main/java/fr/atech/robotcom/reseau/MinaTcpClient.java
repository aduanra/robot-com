package fr.atech.robotcom.reseau;

import android.os.AsyncTask;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;

import fr.atech.robotcom.ui.Loggable;

public class MinaTcpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinaTcpClient.class);

    private IoConnector connector;
    private static IoSession session;

    private String hostname;
    private Integer port = MinaTcpServer.PORT;
    private boolean isConnected = false;

    private final Loggable loggable;

    public MinaTcpClient(final String hostname,
                         final Integer port,
                         final RadioCommandeClientHandler tcpClientHandler,
                         final Loggable loggable) {

        if(hostname==null) throw new RuntimeException("hostname ne peut être null");
        this.hostname = hostname;
        if(port!=null) this.port = port;
        if(tcpClientHandler==null) throw new RuntimeException("tcpClientHandler ne peut être null");
        if(loggable==null) throw new RuntimeException("loggable ne peut être null");

        this.loggable = loggable;

        // TEMP: créé le serveur
        // TODO: cf sur la connectivité réseau: https://openclassrooms.com/fr/courses/2023346-creez-des-applications-pour-android/2028130-la-connectivite-reseau

        connectToServer(hostname, this.port, tcpClientHandler);
    }

    private void connectToServer(final String hostname,
                                 final Integer port,
                                 final RadioCommandeClientHandler tcpClientHandler) {
        log("Préparation de la connexion TCP à l'hote " + hostname + " sur le port " + port);

        connector = new NioSocketConnector();

        connector.getFilterChain().addLast( "logger", new LoggingFilter() );
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(StandardCharsets.UTF_8)));

        connector.setHandler(tcpClientHandler);

        new connexionTask().execute();
    }

    public class connexionTask extends AsyncTask<Void,Void,Void> {

        protected Void doInBackground(Void... Params) {
            final SocketAddress socketAddress = new InetSocketAddress(hostname, port);
            final ConnectFuture connFuture = connector.connect(socketAddress);
            connFuture.awaitUninterruptibly();

            try {
                session = connFuture.getSession();
                isConnected = true;
                log("Connecté à " + hostname + " sur le port " + port);

            } catch (Exception e) {
                loggable.log("Connexion impossible à l'hote " + hostname + " sur le port " + port + ": " + e.getCause().getMessage());
                LOGGER.error("Connexion impossible à l'hote " + hostname + " sur le port " + port, e);
            }
            return null;
        }

    }


    public void sendMessage(final Object message) {
        LOGGER.debug("Envoi du message " + message);
        session.write(message);
    }

    public void disconnect() {
        // Close the session, after all threads have finished processing
        log("Deconnexion");
        this.connector.dispose(true);
        isConnected = false;
    }

    public boolean isConnected() {
        return isConnected;
    }

    private void log(final String logMessage) {
        LOGGER.info(logMessage);
        loggable.log(logMessage);
    }


}
