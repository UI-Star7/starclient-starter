package com.star.client.module.impl;

import com.star.client.module.Module;

public class ToggleSprint extends Module {
    public ToggleSprint() { super("ToggleSprint"); }

    @Override protected void onEnable() { System.out.println("[Module] ToggleSprint enabled"); }

    @Override protected void onDisable() { System.out.println("[Module] ToggleSprint disabled"); }

    @Override public void onTick() { System.out.println("[Tick] Sprinting (demo)"); }
}
