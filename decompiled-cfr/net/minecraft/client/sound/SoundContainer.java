/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.sound;

import net.minecraft.client.sound.SoundSystem;

public interface SoundContainer<T> {
    public int getWeight();

    public T getSound();

    public void preload(SoundSystem var1);
}

