/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.sound;

import net.minecraft.client.sound.SoundInstance;

public interface TickableSoundInstance
extends SoundInstance {
    public boolean isDone();

    public void tick();
}

