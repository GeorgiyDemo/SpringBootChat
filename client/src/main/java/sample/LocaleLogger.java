package sample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;

public class LocaleLogger {

    protected static final Logger parentLogger = LogManager.getLogger();

    private Logger logger = parentLogger;

    protected Logger getLogger() {
        return logger;
    }

    protected void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void log(String message) {
        logger.info(message);
    }
}