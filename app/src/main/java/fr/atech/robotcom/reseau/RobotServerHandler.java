package fr.atech.robotcom.reseau;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.atech.robotcom.ui.robot.RobotViewModel;

public class RobotServerHandler extends IoHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RobotServerHandler.class);

    private RobotViewModel robotViewModel;

    public RobotServerHandler(RobotViewModel robotViewModel) {
        this.robotViewModel = robotViewModel;
    }

    // Pour test
    RobotServerHandler() {
    }

    @Override
    public void messageReceived(final IoSession session, final Object message) {
        LOGGER.debug("Reception du message: " + message);
        robotViewModel.log("Reception du message: " + message);

        final String clientMessage = message.toString().trim();
        robotViewModel.setLastClientCommand(clientMessage);

        if (clientMessage.equalsIgnoreCase("quit")) {
            session.closeOnFlush();
            return;
        }

        final String response = "Message "+clientMessage+" reçu par le robot";
        session.write(response);


        // // Get all connexionTask sessions
//		Collection<IoSession> sessions = session.getService().getManagedSessions().values();
        // // send data to all clients
//		for (IoSession sess : sessions) {
//			sess.write(buffer);
//		}
    }

    @Override
    public void sessionClosed(final IoSession session) throws Exception {
        LOGGER.info("Fermeture de la session");
        robotViewModel.log("Fermeture de la session");
        super.sessionClosed(session);
    }

    @Override
    public void exceptionCaught(final IoSession session, final Throwable cause) throws Exception {
        LOGGER.error("Erreur : ", cause);
        robotViewModel.log("Erreur: " + cause.getMessage());
        super.exceptionCaught(session, cause);
    }

    @Override
    public void messageSent(final IoSession session, final Object message) throws Exception {
        LOGGER.debug("Message envoye: " + message);
        robotViewModel.log("Message envoye: " + message);
        super.messageSent(session, message);
    }

    @Override
    public void sessionCreated(final IoSession session) throws Exception {
        LOGGER.debug("Creation de la session");
        robotViewModel.log("Creation de la session");
        super.sessionCreated(session);
    }

    @Override
    public void sessionIdle(final IoSession session, final IdleStatus status) throws Exception {
        LOGGER.info("Hibernation de la session" + session.getIdleCount(status));
        robotViewModel.log("Hibernation de la session" + session.getIdleCount(status));
        super.sessionIdle(session, status);
    }

    @Override
    public void sessionOpened(final IoSession session) throws Exception {
        LOGGER.info("Ouverture de la session");
        robotViewModel.log("Ouverture de la session");
        super.sessionOpened(session);
    }

}
