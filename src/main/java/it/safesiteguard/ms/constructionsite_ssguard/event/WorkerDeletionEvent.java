package it.safesiteguard.ms.constructionsite_ssguard.event;

import it.safesiteguard.ms.constructionsite_ssguard.domain.Worker;
import org.springframework.context.ApplicationEvent;

public class WorkerDeletionEvent extends ApplicationEvent {

    private final Worker worker;

    public WorkerDeletionEvent(Object source, Worker worker) {
        super(source);
        this.worker = worker;
    }

    public Worker getWorker() {
        return worker;
    }

}
