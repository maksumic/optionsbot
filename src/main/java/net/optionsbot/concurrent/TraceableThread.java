package net.optionsbot.concurrent;

import org.slf4j.MDC;

import java.util.UUID;

public class TraceableThread extends Thread {
    private static final String THREAD_ID = "threadId";

    public TraceableThread(Runnable target) {
        super(() -> {
            String traceId = UUID.randomUUID().toString();
            MDC.put(THREAD_ID, traceId);
            try {
                target.run();
            } finally {
                MDC.remove(THREAD_ID);
            }
        });
    }
}
