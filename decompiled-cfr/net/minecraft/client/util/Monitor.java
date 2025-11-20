/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.lwjgl.glfw.GLFW
 *  org.lwjgl.glfw.GLFWVidMode
 *  org.lwjgl.glfw.GLFWVidMode$Buffer
 */
package net.minecraft.client.util;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.util.VideoMode;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public final class Monitor {
    private final long handle;
    private final List<VideoMode> videoModes;
    private VideoMode currentVideoMode;
    private int x;
    private int y;

    public Monitor(long l) {
        this.handle = l;
        this.videoModes = Lists.newArrayList();
        this.populateVideoModes();
    }

    public void populateVideoModes() {
        Object _snowman2;
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        this.videoModes.clear();
        GLFWVidMode.Buffer buffer = GLFW.glfwGetVideoModes((long)this.handle);
        for (int i = buffer.limit() - 1; i >= 0; --i) {
            buffer.position(i);
            _snowman2 = new VideoMode(buffer);
            if (((VideoMode)_snowman2).getRedBits() < 8 || ((VideoMode)_snowman2).getGreenBits() < 8 || ((VideoMode)_snowman2).getBlueBits() < 8) continue;
            this.videoModes.add((VideoMode)_snowman2);
        }
        int[] nArray = new int[1];
        _snowman2 = new int[1];
        GLFW.glfwGetMonitorPos((long)this.handle, (int[])nArray, (int[])_snowman2);
        this.x = nArray[0];
        this.y = (int)_snowman2[0];
        GLFWVidMode _snowman3 = GLFW.glfwGetVideoMode((long)this.handle);
        this.currentVideoMode = new VideoMode(_snowman3);
    }

    public VideoMode findClosestVideoMode(Optional<VideoMode> optional) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        if (optional.isPresent()) {
            VideoMode videoMode = optional.get();
            for (VideoMode videoMode2 : this.videoModes) {
                if (!videoMode2.equals(videoMode)) continue;
                return videoMode2;
            }
        }
        return this.getCurrentVideoMode();
    }

    public int findClosestVideoModeIndex(VideoMode videoMode) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        return this.videoModes.indexOf(videoMode);
    }

    public VideoMode getCurrentVideoMode() {
        return this.currentVideoMode;
    }

    public int getViewportX() {
        return this.x;
    }

    public int getViewportY() {
        return this.y;
    }

    public VideoMode getVideoMode(int n) {
        return this.videoModes.get(n);
    }

    public int getVideoModeCount() {
        return this.videoModes.size();
    }

    public long getHandle() {
        return this.handle;
    }

    public String toString() {
        return String.format("Monitor[%s %sx%s %s]", this.handle, this.x, this.y, this.currentVideoMode);
    }
}

