/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.util;

import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.util.Monitor;
import net.minecraft.client.util.MonitorTracker;
import net.minecraft.client.util.Window;

public final class WindowProvider
implements AutoCloseable {
    private final MinecraftClient client;
    private final MonitorTracker monitorTracker;

    public WindowProvider(MinecraftClient client) {
        this.client = client;
        this.monitorTracker = new MonitorTracker(Monitor::new);
    }

    public Window createWindow(WindowSettings settings, @Nullable String videoMode, String title) {
        return new Window(this.client, this.monitorTracker, settings, videoMode, title);
    }

    @Override
    public void close() {
        this.monitorTracker.stop();
    }
}

