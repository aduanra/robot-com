package fr.atech.robotcom.reseau;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MinaTcpClientTest {

    private MinaTcpServer server;
    private MinaTcpClient client;

    @Before
    public void setUp() throws Exception {
        server = new MinaTcpServer(null, new RobotServerHandler());
        client = new MinaTcpClient("localhost", null, new RadioCommandeClientHandler());
    }

    @After
    public void tearDown() {
        System.out.println("Fin du test et déconnexion");
        server.disconnect();
        client.disconnect();
    }

    @Test
    public void messageSent() {
        final String messageToSend = "Message de test du connexionTask: yes ça marche !";
        client.sendMessage(messageToSend);
    }
}