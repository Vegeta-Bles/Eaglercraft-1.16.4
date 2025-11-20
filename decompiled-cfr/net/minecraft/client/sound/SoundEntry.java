/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.sound;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.sound.Sound;

public class SoundEntry {
    private final List<Sound> sounds;
    private final boolean replace;
    private final String subtitle;

    public SoundEntry(List<Sound> sounds, boolean replace, String subtitle) {
        this.sounds = sounds;
        this.replace = replace;
        this.subtitle = subtitle;
    }

    public List<Sound> getSounds() {
        return this.sounds;
    }

    public boolean canReplace() {
        return this.replace;
    }

    @Nullable
    public String getSubtitle() {
        return this.subtitle;
    }
}

