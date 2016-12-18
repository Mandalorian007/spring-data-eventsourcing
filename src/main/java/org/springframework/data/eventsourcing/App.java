package org.springframework.data.eventsourcing;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.eventsourcing.event.DomainEvent;
import org.springframework.data.eventsourcing.event.store.CouchbaseEventStore;

@SpringBootApplication
public class App implements CommandLineRunner {
    @Autowired
    ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        CouchbaseEventStore eventStore = context.getBean(CouchbaseEventStore.class);
        //eventStore.save(new CouchbaseDomainEventWrapper(new FakeEvent("bob")));

        System.out.println(eventStore.findAll());
    }
}

@Data
@AllArgsConstructor
class FakeEvent extends DomainEvent {
    private String firstName;
}
