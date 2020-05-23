package fr.atech.robotcom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.atech.robotcom.reseau.MinaTcpClient;
import fr.atech.robotcom.ui.Loggable;

public class LoggableStub implements Loggable {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggableStub.class);

    @Override
    public void log(final String logToDisplay) {
        LOGGER.error(logToDisplay);
    }
}
