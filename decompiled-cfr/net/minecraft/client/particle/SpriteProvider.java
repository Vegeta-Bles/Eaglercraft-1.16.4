/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.texture.Sprite;

public interface SpriteProvider {
    public Sprite getSprite(int var1, int var2);

    public Sprite getSprite(Random var1);
}

