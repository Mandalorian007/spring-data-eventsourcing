package org.springframework.data.eventsourcing.factory;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.eventsourcing.aggregate.AggregateUpdater;
import org.springframework.data.eventsourcing.event.store.CouchbaseEventStore;
import org.springframework.data.eventsourcing.event.validator.EventValidationHandler;
import org.springframework.data.eventsourcing.template.EventSourcingTemplate;

public class SpringDataEventSourcingFactory {
    @Setter
    private AggregateUpdater aggregateUpdater;
    @Setter
    private EventValidationHandler eventValidationHandler;

    @Autowired
    private CouchbaseEventStore couchbaseEventStore;

    public EventSourcingTemplate build() {
        //TODO handle nulls and defaults
        return new EventSourcingTemplate(couchbaseEventStore, eventValidationHandler, aggregateUpdater);
    }
}