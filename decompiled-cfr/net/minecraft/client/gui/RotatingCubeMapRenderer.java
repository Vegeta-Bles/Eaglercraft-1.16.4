/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.util.math.MathHelper;

public class RotatingCubeMapRenderer {
    private final MinecraftClient client;
    private final CubeMapRenderer cubeMap;
    private float time;

    public RotatingCubeMapRenderer(CubeMapRenderer cubeMap) {
        this.cubeMap = cubeMap;
        this.client = MinecraftClient.getInstance();
    }

    public void render(float delta, float alpha) {
        this.time += delta;
        this.cubeMap.draw(this.client, MathHelper.sin(this.time * 0.001f) * 5.0f + 25.0f, -this.time * 0.1f, alpha);
    }
}

