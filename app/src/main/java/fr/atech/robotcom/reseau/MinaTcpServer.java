package fr.atech.robotcom.reseau;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

// Pour une lib réseau plus simple cf https://github.com/EsotericSoftware/kryonet
// Mina cf https://mina.apache.org/mina-project/quick-start-guide.html
public class MinaTcpServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinaTcpServer.class);
    public static final int PORT = 18567;

    private NioSocketAcceptor acceptor;

    private Integer port = MinaTcpServer.PORT;

    public MinaTcpServer(final Integer port,
                         final RobotServerHandler robotServerHandler) throws IOException {

        if(port!=null) this.port = port;
        if(robotServerHandler==null) throw new RuntimeException("robotServerHandler ne peut être null");

        startServer(robotServerHandler);
    }

    private void startServer(final RobotServerHandler robotServerHandler) throws IOException {
        acceptor = new NioSocketAcceptor();

        //acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(StandardCharsets.UTF_8)));

        acceptor.setHandler(robotServerHandler);

        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

        acceptor.bind(new InetSocketAddress(PORT));

        LOGGER.info("Démarrage du serveur TCP sur le port " + PORT);
    }

    public void disconnect() {
        LOGGER.info("Deconnexion");
        acceptor.dispose(true);
    }


}
