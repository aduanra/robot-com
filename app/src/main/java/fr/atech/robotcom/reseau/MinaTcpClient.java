package fr.atech.robotcom.reseau;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class MinaTcpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinaTcpClient.class);

    private IoConnector connector;
    private static IoSession session;

    public MinaTcpClient() {
        connector = new NioSocketConnector();

        //connector.getFilterChain().addLast( "logger", new LoggingFilter() );
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(StandardCharsets.UTF_8)));

        connector.setHandler(new RadioCommandeClientHandler());

        final ConnectFuture connFuture = connector.connect(new InetSocketAddress("localhost", MinaTcpServer.PORT));
        connFuture.awaitUninterruptibly();
        session = connFuture.getSession();
        LOGGER.info("Démarrage du client TCP sur le port " + MinaTcpServer.PORT);
    }

    public void sendMessage(final Object message) {
        LOGGER.debug("Envoi du message " + message);
        session.write(message);
    }

    public void disconnect() {
        // Close the session, after all threads have finished processing
        LOGGER.info("Deconnexion");
        this.connector.dispose(true);
    }

}
