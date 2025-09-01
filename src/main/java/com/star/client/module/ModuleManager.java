package com.star.client.module;

import java.util.*;

public class ModuleManager {
    private final Map<String, Module> modules = new LinkedHashMap<>();

    public void register(Module m) {
        modules.put(m.getName(), m);
    }

    public Module get(String name) { return modules.get(name); }
    public Collection<Module> list() { return modules.values(); }

    public void enable(String name) { Module m = modules.get(name); if (m != null) m.setEnabled(true); }
    public void disable(String name) { Module m = modules.get(name); if (m != null) m.setEnabled(false); }
}
