package fr.atech.robotcom.reseau;

import android.widget.TextView;
import fr.atech.robotcom.R;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RadioCommandeClientHandler extends IoHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RadioCommandeClientHandler.class);

    @Override
    public void messageReceived(final IoSession session, final Object message) {

        LOGGER.debug("Reception du message: " + message);

        String str = message.toString();
        if (str.trim().equalsIgnoreCase("quit")) {
            session.closeOnFlush();
            return;
        }

        final String response = "Message bien reçu !";
        session.write(response);

//        TextView receivedText = (TextView) findViewById(R.id.text_radiocommande_log);
//        receivedText.setText("Robot: " + receivedText);


        // // Get all client sessions
//		Collection<IoSession> sessions = session.getService().getManagedSessions().values();
        // // send data to all clients
//		for (IoSession sess : sessions) {
//			sess.write(buffer);
//		}
    }

    @Override
    public void sessionClosed(final IoSession session) throws Exception {
        LOGGER.info("Fermeture de la session");
        super.sessionClosed(session);
    }

    @Override
    public void exceptionCaught(final IoSession session, final Throwable cause) throws Exception {
        LOGGER.error("Erreur : ", cause);
        super.exceptionCaught(session, cause);
    }

    @Override
    public void messageSent(final IoSession session, final Object message) throws Exception {
        LOGGER.debug("Message envoye: " + message);
        super.messageSent(session, message);
    }

    @Override
    public void sessionCreated(final IoSession session) throws Exception {
        LOGGER.debug("Creation de la session");
        super.sessionCreated(session);
    }

    @Override
    public void sessionIdle(final IoSession session, final IdleStatus status) throws Exception {
        LOGGER.info("Hibernation de la session" +  session.getIdleCount(status));
        super.sessionIdle(session, status);
    }

    @Override
    public void sessionOpened(final IoSession session) throws Exception {
        LOGGER.info("Ouverture de la session");
        super.sessionOpened(session);
    }

}
