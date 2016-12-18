package org.springframework.data.eventsourcing.template;

import lombok.AllArgsConstructor;
import org.springframework.data.eventsourcing.aggregate.AggregateUpdater;
import org.springframework.data.eventsourcing.event.DomainEvent;
import org.springframework.data.eventsourcing.event.store.CouchbaseDomainEventWrapper;
import org.springframework.data.repository.CrudRepository;

@AllArgsConstructor
public class EventSourcingTemplate {
    private CrudRepository eventStoreRepository;
    private AggregateUpdater aggregateUpdater;

    public <T extends DomainEvent> void handle(T event) throws Exception {
        eventStoreRepository.save(new CouchbaseDomainEventWrapper(event));
        aggregateUpdater.invokeAggregateUpdate(event);
    }
}
