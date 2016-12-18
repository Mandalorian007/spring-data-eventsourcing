package org.springframework.data.eventsourcing.aggregate;

import org.springframework.data.eventsourcing.event.DomainEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AggregateUpdater {
    private List<Method> aggregateMethods;

    public AggregateUpdater() {
        aggregateMethods = getMethodsAnnotatedWithAggarateEventHandler(this.getClass());
    }

    public void invokeAggregateUpdate(DomainEvent event) throws Exception {
        Class clazz = event.getClass();
        for(Method method : aggregateMethods) {
            for(Class aClass : method.getParameterTypes()) {
                if(clazz.equals(aClass)) {
                    method.invoke(this, event);
                    return;
                }
            }
        }
    }

    private static List<Method> getMethodsAnnotatedWithAggarateEventHandler(final Class<?> type) {
        final List<Method> methods = new ArrayList<Method>();
        final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(type.getDeclaredMethods()));
        for (final Method method : allMethods) {
            if (method.isAnnotationPresent(AggregateEventHandler.class)) {
                methods.add(method);
            }
        }
        return methods;
    }
}
