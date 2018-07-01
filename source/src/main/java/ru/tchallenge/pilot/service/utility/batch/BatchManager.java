package ru.tchallenge.pilot.service.utility.batch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;

@Slf4j
@ManagedComponent
public class BatchManager extends GenericApplicationComponent {

    private ExecutorService executorService;

    public void submit(BatchRunnable runnable) {
        this.executorService.submit(() -> {
            try {
                runnable.run();
            } catch (Exception exception) {
                handleBatchException(exception);
            }
        });
    }

    @Override
    public void init() {
        super.init();
        this.executorService = Executors.newFixedThreadPool(3);
    }

    private void handleBatchException(Exception exception) {
        log.error("Batch operation failure", exception);
    }
}
