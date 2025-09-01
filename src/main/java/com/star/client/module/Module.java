package com.star.client.module;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    private final String name;
    private boolean enabled = false;
    protected final List<Object> settings = new ArrayList<>();

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
