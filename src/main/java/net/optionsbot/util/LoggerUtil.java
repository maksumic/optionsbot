package net.optionsbot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class LoggerUtil {
    private static final Logger LOG = LoggerFactory.getLogger(LoggerUtil.class);
    private static final Path LOG_DIR = Paths.get(".logs");

    private LoggerUtil() {
        // Non-instantiable class
    }

    public static void createLogsDirectory() {
        try {
            Files.createDirectories(LOG_DIR);
        } catch (IOException e) {
            System.err.println("Failed to create logs directory: " + e.getMessage());
        }
    }

    public static void initializeLogFiles() {
        LOG.debug("Logger initialized: debug.log");
        LOG.info("Logger initialized: info.log");
        LOG.warn("Logger initialized: warn.log");
        LOG.error("Logger initialized: error.log");
    }
}
