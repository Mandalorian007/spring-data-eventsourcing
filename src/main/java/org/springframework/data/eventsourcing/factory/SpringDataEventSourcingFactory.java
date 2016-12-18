package org.springframework.data.eventsourcing.factory;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.eventsourcing.aggregate.AggregateUpdater;
import org.springframework.data.eventsourcing.event.store.CouchbaseEventStore;
import org.springframework.data.eventsourcing.template.EventSourcingTemplate;

public class SpringDataEventSourcingFactory {
    @Getter
    @Setter
    private AggregateUpdater aggregateUpdater;

    @Autowired
    private CouchbaseEventStore couchbaseEventStore;

    public EventSourcingTemplate build() {
        return new EventSourcingTemplate(couchbaseEventStore, aggregateUpdater);
    }
}