/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.Immutable
 */
package net.minecraft.world;

import javax.annotation.concurrent.Immutable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;

@Immutable
public class LocalDifficulty {
    private final Difficulty globalDifficulty;
    private final float localDifficulty;

    public LocalDifficulty(Difficulty difficulty, long timeOfDay, long inhabitedTime, float moonSize) {
        this.globalDifficulty = difficulty;
        this.localDifficulty = this.setLocalDifficulty(difficulty, timeOfDay, inhabitedTime, moonSize);
    }

    public Difficulty getGlobalDifficulty() {
        return this.globalDifficulty;
    }

    public float getLocalDifficulty() {
        return this.localDifficulty;
    }

    public boolean isHarderThan(float difficulty) {
        return this.localDifficulty > difficulty;
    }

    public float getClampedLocalDifficulty() {
        if (this.localDifficulty < 2.0f) {
            return 0.0f;
        }
        if (this.localDifficulty > 4.0f) {
            return 1.0f;
        }
        return (this.localDifficulty - 2.0f) / 2.0f;
    }

    private float setLocalDifficulty(Difficulty difficulty, long timeOfDay, long inhabitedTime, float moonSize) {
        if (difficulty == Difficulty.PEACEFUL) {
            return 0.0f;
        }
        boolean bl = difficulty == Difficulty.HARD;
        float _snowman2 = 0.75f;
        float _snowman3 = MathHelper.clamp(((float)timeOfDay + -72000.0f) / 1440000.0f, 0.0f, 1.0f) * 0.25f;
        _snowman2 += _snowman3;
        float _snowman4 = 0.0f;
        _snowman4 += MathHelper.clamp((float)inhabitedTime / 3600000.0f, 0.0f, 1.0f) * (bl ? 1.0f : 0.75f);
        _snowman4 += MathHelper.clamp(moonSize * 0.25f, 0.0f, _snowman3);
        if (difficulty == Difficulty.EASY) {
            _snowman4 *= 0.5f;
        }
        return (float)difficulty.getId() * (_snowman2 += _snowman4);
    }
}

