package org.springframework.data.eventsourcing.event.validator;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.eventsourcing.event.DomainEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventValidationHandler implements ApplicationContextAware {
    private List<Method> aggregateMethods;
    private ApplicationContext context;
    private EventValidationHandler bean;

    public EventValidationHandler() {
        aggregateMethods = getMethodsAnnotatedWithAggregateEventHandler(this.getClass());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        bean = context.getBean(this.getClass());
    }

    public boolean validateEvent(DomainEvent event) throws Exception {
        Class clazz = event.getClass();
        for(Method method : aggregateMethods) {
            for(Class aClass : method.getParameterTypes()) {
                if(clazz.equals(aClass)) {
                    Object response = method.invoke(bean, event);
                    return (Boolean) response;
                }
            }
        }
        throw new NoSuchMethodError("Unable to locate event validator method for event type: " + event.getClass().getSimpleName() + ".  Please confirm method is annotated with @" + EventValidator.class.getSimpleName());
    }

    private static List<Method> getMethodsAnnotatedWithAggregateEventHandler(final Class<?> type) {
        final List<Method> methods = new ArrayList<>();
        final List<Method> allMethods = new ArrayList<>(Arrays.asList(type.getDeclaredMethods()));
        for (final Method method : allMethods) {
            if (method.isAnnotationPresent(EventValidator.class)) {
                methods.add(method);
            }
        }
        return methods;
    }
}
