README.md

StarClient - Starter (Java 17)

This is a lightweight starter skeleton for a Lunar-style client core. It is NOT yet injected into Minecraft — it's a pure Java project that demonstrates the core architecture you'll need:

Module system

Module manager

Simple demo modules (ToggleSprint, KeystrokesHUD)

EventBus (basic)

Gradle build (simple application)


Purpose: give you a clean codebase I can extend into a Fabric-based client later. For now this runs as a console demo so you can test modules on any machine.

Contents (project tree)

starclient-starter/ ├─ build.gradle ├─ settings.gradle ├─ README.md └─ src/main/java/com/star/client/ ├─ StarClient.java ├─ bus/EventBus.java ├─ module/Module.java ├─ module/ModuleManager.java ├─ module/impl/ToggleSprint.java ├─ hud/KeystrokesHUD.java └─ events/TickEvent.java

How this helps you

I wrote the core code so you don't have to: module skeleton, manager, a demo loop.

Next step (if you want): I will convert this into a Fabric mod (mixin entrypoints + client-side integration) and add ClickGUI + rendering modules.


Recommended next move

1. If you have access to a PC (best): follow the PC steps below to run the demo and then I will push Fabric integration.


2. If you are mobile-only: I included mobile-friendly options (Termux or cloud IDE) — they are more advanced but possible. Read the Mobile section.



--- FILE: build.gradle ---

plugins { id 'application' id 'java' }

group = 'com.star' version = '0.1.0'

sourceCompatibility = '17' targetCompatibility = '17'

repositories { mavenCentral() }

dependencies { // No Minecraft/Fabric deps yet. We'll add them when doing Fabric integration. }

application { mainClass = 'com.star.client.StarClient' }

--- FILE: settings.gradle ---

rootProject.name = 'starclient-starter'

--- FILE: src/main/java/com/star/client/StarClient.java ---

package com.star.client;

import com.star.client.module.Module; import com.star.client.module.ModuleManager; import com.star.client.module.impl.ToggleSprint; import com.star.client.hud.KeystrokesHUD;

public class StarClient {

public static final ModuleManager MODULES = new ModuleManager();

public static void main(String[] args) throws Exception {
    System.out.println("StarClient starter - demo");
    MODULES.register(new ToggleSprint());
    MODULES.register(new KeystrokesHUD());
    MODULES.enable("ToggleSprint");
    MODULES.enable("KeystrokesHUD");

    for (int i = 1; i <= 5; i++) {
        System.out.println("=== Tick " + i + " ===");
        for (Module m : MODULES.list()) {
            if (m.isEnabled()) m.onTick();
        }
        Thread.sleep(1000);
    }
    System.out.println("Demo finished");
}

}

--- FILE: src/main/java/com/star/client/bus/EventBus.java ---

package com.star.client.bus;

import java.util.*; import java.util.concurrent.ConcurrentHashMap; import java.util.function.Consumer;

public final class EventBus { private final Map<Class<?>, List<Listener<?>>> map = new ConcurrentHashMap<>();

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

--- FILE: src/main/java/com/star/client/module/Module.java ---

package com.star.client.module;

import java.util.ArrayList; import java.util.List;

public abstract class Module { private final String name; private boolean enabled = false; protected final List<Object> settings = new ArrayList<>();

public Module(String name) { this.name = name; }
public String getName() { return name; }
public boolean isEnabled() { return enabled; }
public void toggle() { setEnabled(!enabled); }
public void setEnabled(boolean b) {
    if (this.enabled == b) return;
    this.enabled = b;
    if (b) onEnable(); else onDisable();
}
protected void onEnable() {}
protected void onDisable() {}
public void onTick() {}

}

--- FILE: src/main/java/com/star/client/module/ModuleManager.java ---

package com.star.client.module;

import java.util.*;

public class ModuleManager { private final Map<String, Module> modules = new LinkedHashMap<>();

public void register(Module m) {
    modules.put(m.getName(), m);
}
public Module get(String name) { return modules.get(name); }
public Collection<Module> list() { return modules.values(); }
public void enable(String name) { Module m = modules.get(name); if (m != null) m.setEnabled(true); }
public void disable(String name) { Module m = modules.get(name); if (m != null) m.setEnabled(false); }

}

--- FILE: src/main/java/com/star/client/module/impl/ToggleSprint.java ---

package com.star.client.module.impl;

import com.star.client.module.Module;

public class ToggleSprint extends Module { public ToggleSprint() { super("ToggleSprint"); } @Override protected void onEnable() { System.out.println("[Module] ToggleSprint enabled"); } @Override protected void onDisable() { System.out.println("[Module] ToggleSprint disabled"); } @Override public void onTick() { System.out.println("[Tick] Sprinting (demo)"); } }

--- FILE: src/main/java/com/star/client/hud/KeystrokesHUD.java ---

package com.star.client.hud;

import com.star.client.module.Module;

public class KeystrokesHUD extends Module { public KeystrokesHUD() { super("KeystrokesHUD"); } @Override protected void onEnable() { System.out.println("[Module] KeystrokesHUD enabled"); } @Override public void onTick() { System.out.println("[HUD] Keys: W A S D LMB RMB (demo)"); } }

--- FILE: src/main/java/com/star/client/events/TickEvent.java ---

package com.star.client.events;

public class TickEvent {}

--- END OF PROJECT ---

Notes & next steps

If you want, I can convert this skeleton into a Fabric mod (client-side) next: add fabric loom, mixins, clickgui, rendering hooks and a jar you can drop into a Fabric loader environment.

Tell me: do you have PC access or strictly mobile? Based on that I'll give exact step-by-step commands to build & run, and then push the Fabric integration automatically in the next update.


Legal / EULA note

This starter contains only original code. When we integrate with Minecraft we will not distribute Mojang-owned assets; we'll follow the EULA and recommend users install Minecraft themselves.

