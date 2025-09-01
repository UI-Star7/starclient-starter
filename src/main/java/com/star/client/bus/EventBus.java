package com.star.client.bus;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public final class EventBus {
    private final Map<Class<?>, List<Listener<?>>> map = new ConcurrentHashMap<>();

    public <T> void subscribe(Class<T> type, Consumer<T> fn) {
        map.computeIfAbsent(type, k -> new ArrayList<>()).add(new Listener<>(fn));
    }

    @SuppressWarnings("unchecked")
    public <T> void post(T event) {
        List<Listener<?>> ls = map.getOrDefault(event.getClass(), List.of());
        for (Listener<?> l : ls) ((Listener<T>) l).fn.accept(event);
    }

    private static final class Listener<T> {
        final Consumer<T> fn;
        Listener(Consumer<T> fn) { this.fn = fn; }
    }
}
