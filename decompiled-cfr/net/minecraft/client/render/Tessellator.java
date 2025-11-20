/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;

public class Tessellator {
    private final BufferBuilder buffer;
    private static final Tessellator INSTANCE = new Tessellator();

    public static Tessellator getInstance() {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        return INSTANCE;
    }

    public Tessellator(int bufferCapacity) {
        this.buffer = new BufferBuilder(bufferCapacity);
    }

    public Tessellator() {
        this(0x200000);
    }

    public void draw() {
        this.buffer.end();
        BufferRenderer.draw(this.buffer);
    }

    public BufferBuilder getBuffer() {
        return this.buffer;
    }
}

