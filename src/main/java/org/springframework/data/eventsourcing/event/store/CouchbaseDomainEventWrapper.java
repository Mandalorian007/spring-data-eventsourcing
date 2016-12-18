package org.springframework.data.eventsourcing.event.store;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import lombok.Data;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.eventsourcing.event.DomainEvent;

import java.util.UUID;

@Data
@Document
public class CouchbaseDomainEventWrapper {
    @Id
    private String id;

    @Field
    private Class clazz;

    @Field
    private DomainEvent domainEvent;

    public CouchbaseDomainEventWrapper(DomainEvent domainEvent) {
        id = UUID.randomUUID().toString();
        clazz = domainEvent.getClass();
        this.domainEvent = domainEvent;
    }
}