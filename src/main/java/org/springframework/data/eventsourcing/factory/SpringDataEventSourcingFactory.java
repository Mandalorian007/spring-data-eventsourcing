package org.springframework.data.eventsourcing.factory;

import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.data.eventsourcing.aggregate.AggregateUpdater;
import org.springframework.data.eventsourcing.config.ApplicationContextSingleton;
import org.springframework.data.eventsourcing.event.store.CouchbaseEventStore;
import org.springframework.data.eventsourcing.event.validator.EventValidationHandler;
import org.springframework.data.eventsourcing.template.EventSourcingTemplate;

public class SpringDataEventSourcingFactory {
    @Setter
    private AggregateUpdater aggregateUpdater;
    @Setter
    private EventValidationHandler eventValidationHandler;

    public EventSourcingTemplate build() {
        ApplicationContext context = ApplicationContextSingleton.getApplicationContext();
        CouchbaseEventStore couchbaseEventStore = context.getBean(CouchbaseEventStore.class);

        //TODO handle nulls and defaults
        return new EventSourcingTemplate(couchbaseEventStore, eventValidationHandler, aggregateUpdater);
    }
}