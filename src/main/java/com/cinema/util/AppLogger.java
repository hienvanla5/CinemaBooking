package com.cinema.util;

import java.io.IOException;
import java.util.logging.*;

public class AppLogger {

    private static final AppLogger INSTANCE = new AppLogger();
    private final Logger logger;

    private AppLogger() {

        logger = Logger.getLogger("CinemaApp");

        logger.setUseParentHandlers(false);

        try {
            FileHandler fileHandler = new FileHandler("app.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            System.err.println("Cannot create log file: " + e.getMessage());
        }

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);

        logger.setLevel(Level.ALL);

        for (Handler handler : logger.getHandlers()) {
            handler.setLevel(Level.ALL);
        }
    }

    public static AppLogger getInstance() {
        return INSTANCE;
    }

    public void logInfo(String message) {
        logger.info(message);
    }

    public void logWarning(String message) {
        logger.warning(message);
    }

    public void logError(String message) {
        logger.severe(message);
    }

    public void logError(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }

    public void logDebug(String message) {
        logger.fine(message);
    }
}
