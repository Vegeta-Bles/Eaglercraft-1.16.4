/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.sound;

import net.minecraft.client.sound.AbstractBeeSoundInstance;
import net.minecraft.client.sound.AggressiveBeeSoundInstance;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class PassiveBeeSoundInstance
extends AbstractBeeSoundInstance {
    public PassiveBeeSoundInstance(BeeEntity beeEntity) {
        super(beeEntity, SoundEvents.ENTITY_BEE_LOOP, SoundCategory.NEUTRAL);
    }

    @Override
    protected MovingSoundInstance getReplacement() {
        return new AggressiveBeeSoundInstance(this.bee);
    }

    @Override
    protected boolean shouldReplace() {
        return this.bee.hasAngerTime();
    }
}

