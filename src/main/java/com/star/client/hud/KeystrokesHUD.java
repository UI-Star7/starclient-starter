package com.star.client.hud;

import com.star.client.module.Module;

public class KeystrokesHUD extends Module {
    public KeystrokesHUD() { super("KeystrokesHUD"); }

    @Override protected void onEnable() { System.out.println("[Module] KeystrokesHUD enabled"); }

    @Override public void onTick() { System.out.println("[HUD] Keys: W A S D LMB RMB (demo)"); }
}
