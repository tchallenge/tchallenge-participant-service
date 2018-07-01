package ru.tchallenge.pilot.service.utility.batch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class BatchManager {

    public static final BatchManager INSTANCE = new BatchManager();

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    private BatchManager() {

    }

    public void submit(final BatchRunnable runnable) {
        executorService.submit(() -> {
            try {
                runnable.run();
            } catch (final Exception exception) {
                handleBatchException(exception);
            }
        });
    }

    private void handleBatchException(final Exception exception) {
        log.error("Batch operation failure", exception);
    }
}
