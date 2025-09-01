package com.star.client.events;

import com.star.client.bus.EventBus;

public class EventManager {
    private static final EventBus bus = new EventBus();

    public static void register(Object obj) {
        bus.register(obj);
    }

    public static void unregister(Object obj) {
        bus.unregister(obj);
    }

    public static void post(Event event) {
        bus.post(event);
    }
}
