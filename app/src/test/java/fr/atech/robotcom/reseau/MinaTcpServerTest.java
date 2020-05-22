package fr.atech.robotcom.reseau;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MinaTcpServerTest {

    @Test
    public void messageReceived() throws IOException, InterruptedException {
        MinaTcpServer server = new MinaTcpServer(null, new RobotServerHandler());
        while(true){
            Thread.sleep(100);
        }
    }
}