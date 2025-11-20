/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  javax.annotation.Nullable
 *  org.lwjgl.PointerBuffer
 *  org.lwjgl.glfw.GLFW
 *  org.lwjgl.glfw.GLFWMonitorCallback
 */
package net.minecraft.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.client.util.Monitor;
import net.minecraft.client.util.MonitorFactory;
import net.minecraft.client.util.Window;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallback;

public class MonitorTracker {
    private final Long2ObjectMap<Monitor> pointerToMonitorMap = new Long2ObjectOpenHashMap();
    private final MonitorFactory monitorFactory;

    public MonitorTracker(MonitorFactory monitorFactory) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        this.monitorFactory = monitorFactory;
        GLFW.glfwSetMonitorCallback(this::handleMonitorEvent);
        PointerBuffer pointerBuffer = GLFW.glfwGetMonitors();
        if (pointerBuffer != null) {
            for (int i = 0; i < pointerBuffer.limit(); ++i) {
                long l = pointerBuffer.get(i);
                this.pointerToMonitorMap.put(l, (Object)monitorFactory.createMonitor(l));
            }
        }
    }

    private void handleMonitorEvent(long monitor, int event) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (event == 262145) {
            this.pointerToMonitorMap.put(monitor, (Object)this.monitorFactory.createMonitor(monitor));
        } else if (event == 262146) {
            this.pointerToMonitorMap.remove(monitor);
        }
    }

    @Nullable
    public Monitor getMonitor(long l) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        return (Monitor)this.pointerToMonitorMap.get(l);
    }

    @Nullable
    public Monitor getMonitor(Window window) {
        long l = GLFW.glfwGetWindowMonitor((long)window.getHandle());
        if (l != 0L) {
            return this.getMonitor(l);
        }
        int _snowman2 = window.getX();
        int _snowman3 = _snowman2 + window.getWidth();
        int _snowman4 = window.getY();
        int _snowman5 = _snowman4 + window.getHeight();
        int _snowman6 = -1;
        Monitor _snowman7 = null;
        for (Monitor monitor : this.pointerToMonitorMap.values()) {
            int n = monitor.getViewportX();
            _snowman = n + monitor.getCurrentVideoMode().getWidth();
            _snowman = monitor.getViewportY();
            _snowman = _snowman + monitor.getCurrentVideoMode().getHeight();
            _snowman = MonitorTracker.clamp(_snowman2, n, _snowman);
            _snowman = MonitorTracker.clamp(_snowman3, n, _snowman);
            _snowman = MonitorTracker.clamp(_snowman4, _snowman, _snowman);
            _snowman = MonitorTracker.clamp(_snowman5, _snowman, _snowman);
            _snowman = Math.max(0, _snowman - _snowman);
            _snowman = _snowman * (_snowman = Math.max(0, _snowman - _snowman));
            if (_snowman <= _snowman6) continue;
            _snowman7 = monitor;
            _snowman6 = _snowman;
        }
        return _snowman7;
    }

    public static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public void stop() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GLFWMonitorCallback gLFWMonitorCallback = GLFW.glfwSetMonitorCallback(null);
        if (gLFWMonitorCallback != null) {
            gLFWMonitorCallback.free();
        }
    }
}

