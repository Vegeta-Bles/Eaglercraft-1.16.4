/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.sound;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundContainer;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class WeightedSoundSet
implements SoundContainer<Sound> {
    private final List<SoundContainer<Sound>> sounds = Lists.newArrayList();
    private final Random random = new Random();
    private final Identifier id;
    @Nullable
    private final Text subtitle;

    public WeightedSoundSet(Identifier id, @Nullable String subtitle) {
        this.id = id;
        this.subtitle = subtitle == null ? null : new TranslatableText(subtitle);
    }

    @Override
    public int getWeight() {
        int n = 0;
        for (SoundContainer<Sound> soundContainer : this.sounds) {
            n += soundContainer.getWeight();
        }
        return n;
    }

    @Override
    public Sound getSound() {
        int n = this.getWeight();
        if (this.sounds.isEmpty() || n == 0) {
            return SoundManager.MISSING_SOUND;
        }
        _snowman = this.random.nextInt(n);
        for (SoundContainer<Sound> soundContainer : this.sounds) {
            if ((_snowman -= soundContainer.getWeight()) >= 0) continue;
            return soundContainer.getSound();
        }
        return SoundManager.MISSING_SOUND;
    }

    public void add(SoundContainer<Sound> soundContainer) {
        this.sounds.add(soundContainer);
    }

    @Nullable
    public Text getSubtitle() {
        return this.subtitle;
    }

    @Override
    public void preload(SoundSystem soundSystem) {
        for (SoundContainer<Sound> soundContainer : this.sounds) {
            soundContainer.preload(soundSystem);
        }
    }

    @Override
    public /* synthetic */ Object getSound() {
        return this.getSound();
    }
}

