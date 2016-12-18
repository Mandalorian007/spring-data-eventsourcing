package org.springframework.data.eventsourcing.event.store;

import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouchbaseEventStore extends CouchbasePagingAndSortingRepository<CouchbaseDomainEventWrapper, String> {
}
