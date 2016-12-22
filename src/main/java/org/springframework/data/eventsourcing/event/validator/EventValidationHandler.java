package org.springframework.data.eventsourcing.event.validator;

import org.springframework.data.eventsourcing.event.DomainEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventValidationHandler {
    private List<Method> aggregateMethods;

    public EventValidationHandler() {
        aggregateMethods = getMethodsAnnotatedWithAggarateEventHandler(this.getClass());
    }

    public boolean validateEvent(DomainEvent event) throws Exception {
        Class clazz = event.getClass();
        for(Method method : aggregateMethods) {
            for(Class aClass : method.getParameterTypes()) {
                if(clazz.equals(aClass)) {
                    Object response = method.invoke(this, event);
                    return (Boolean) response;
                }
            }
        }
        throw new NoSuchMethodError("Unable to locate event validator method for event type: " + event.getClass().getSimpleName() + ".  Please confirm method is annotated with @" + EventValidator.class.getSimpleName());
    }

    private static List<Method> getMethodsAnnotatedWithAggarateEventHandler(final Class<?> type) {
        final List<Method> methods = new ArrayList<Method>();
        final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(type.getDeclaredMethods()));
        for (final Method method : allMethods) {
            if (method.isAnnotationPresent(EventValidator.class)) {
                methods.add(method);
            }
        }
        return methods;
    }
}
