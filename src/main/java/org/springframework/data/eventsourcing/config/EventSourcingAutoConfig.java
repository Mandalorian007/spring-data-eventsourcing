package org.springframework.data.eventsourcing.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.data.eventsourcing.event.store.CouchbaseEventStore;

@Configuration
@EnableCouchbaseRepositories(basePackageClasses = CouchbaseEventStore.class)
@ComponentScan(basePackageClasses = CouchbaseEventStore.class)
public class EventSourcingAutoConfig {
}
