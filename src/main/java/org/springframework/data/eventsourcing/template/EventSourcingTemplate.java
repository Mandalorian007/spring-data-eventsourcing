package org.springframework.data.eventsourcing.template;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.eventsourcing.aggregate.AggregateUpdater;
import org.springframework.data.eventsourcing.event.DomainEvent;
import org.springframework.data.eventsourcing.event.store.CouchbaseDomainEventWrapper;
import org.springframework.data.eventsourcing.event.store.CouchbaseEventStore;
import org.springframework.data.eventsourcing.event.validator.EventValidationHandler;
import org.springframework.data.repository.CrudRepository;

public class EventSourcingTemplate implements InitializingBean {
    @Autowired
    private ApplicationContext applicationContext;
    private CrudRepository eventStoreRepository;

    private EventValidationHandler eventValidationHandler;
    private AggregateUpdater aggregateUpdater;

    public EventSourcingTemplate(EventValidationHandler eventValidationHandler, AggregateUpdater aggregateUpdater) {
        this.eventValidationHandler = eventValidationHandler;
        this.aggregateUpdater = aggregateUpdater;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        eventStoreRepository = applicationContext.getBean(CouchbaseEventStore.class);
    }

    public <T extends DomainEvent> void handle(T event) throws RuntimeException {
        try {
            boolean isValid = eventValidationHandler.validateEvent(event);
            if (isValid) {
                eventStoreRepository.save(new CouchbaseDomainEventWrapper(event));
                aggregateUpdater.invokeAggregateUpdate(event);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}