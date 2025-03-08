package net.optionsbot;

import net.optionsbot.concurrent.TraceableThread;
import net.optionsbot.util.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OptionsBotApplication {
    private static final Logger LOG = LoggerFactory.getLogger(OptionsBotApplication.class);

    public static void main(String[] args) {
        new TraceableThread(() -> {
            LoggerUtil.createLogsDirectory();
            LoggerUtil.initializeLogFiles();
        }).start();
    }
}
