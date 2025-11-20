/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ComparisonChain
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.entity.effect;

import com.google.common.collect.ComparisonChain;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.nbt.CompoundTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StatusEffectInstance
implements Comparable<StatusEffectInstance> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final StatusEffect type;
    private int duration;
    private int amplifier;
    private boolean splash;
    private boolean ambient;
    private boolean permanent;
    private boolean showParticles;
    private boolean showIcon;
    @Nullable
    private StatusEffectInstance hiddenEffect;

    public StatusEffectInstance(StatusEffect statusEffect) {
        this(statusEffect, 0, 0);
    }

    public StatusEffectInstance(StatusEffect type, int duration) {
        this(type, duration, 0);
    }

    public StatusEffectInstance(StatusEffect type, int duration, int amplifier) {
        this(type, duration, amplifier, false, true);
    }

    public StatusEffectInstance(StatusEffect type, int duration, int amplifier, boolean ambient, boolean visible) {
        this(type, duration, amplifier, ambient, visible, visible);
    }

    public StatusEffectInstance(StatusEffect type, int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon) {
        this(type, duration, amplifier, ambient, showParticles, showIcon, null);
    }

    public StatusEffectInstance(StatusEffect type, int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon, @Nullable StatusEffectInstance hiddenEffect) {
        this.type = type;
        this.duration = duration;
        this.amplifier = amplifier;
        this.ambient = ambient;
        this.showParticles = showParticles;
        this.showIcon = showIcon;
        this.hiddenEffect = hiddenEffect;
    }

    public StatusEffectInstance(StatusEffectInstance statusEffectInstance) {
        this.type = statusEffectInstance.type;
        this.copyFrom(statusEffectInstance);
    }

    void copyFrom(StatusEffectInstance that) {
        this.duration = that.duration;
        this.amplifier = that.amplifier;
        this.ambient = that.ambient;
        this.showParticles = that.showParticles;
        this.showIcon = that.showIcon;
    }

    public boolean upgrade(StatusEffectInstance that) {
        if (this.type != that.type) {
            LOGGER.warn("This method should only be called for matching effects!");
        }
        boolean bl = false;
        if (that.amplifier > this.amplifier) {
            if (that.duration < this.duration) {
                StatusEffectInstance statusEffectInstance = this.hiddenEffect;
                this.hiddenEffect = new StatusEffectInstance(this);
                this.hiddenEffect.hiddenEffect = statusEffectInstance;
            }
            this.amplifier = that.amplifier;
            this.duration = that.duration;
            bl = true;
        } else if (that.duration > this.duration) {
            if (that.amplifier == this.amplifier) {
                this.duration = that.duration;
                bl = true;
            } else if (this.hiddenEffect == null) {
                this.hiddenEffect = new StatusEffectInstance(that);
            } else {
                this.hiddenEffect.upgrade(that);
            }
        }
        if (!that.ambient && this.ambient || bl) {
            this.ambient = that.ambient;
            bl = true;
        }
        if (that.showParticles != this.showParticles) {
            this.showParticles = that.showParticles;
            bl = true;
        }
        if (that.showIcon != this.showIcon) {
            this.showIcon = that.showIcon;
            bl = true;
        }
        return bl;
    }

    public StatusEffect getEffectType() {
        return this.type;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getAmplifier() {
        return this.amplifier;
    }

    public boolean isAmbient() {
        return this.ambient;
    }

    public boolean shouldShowParticles() {
        return this.showParticles;
    }

    public boolean shouldShowIcon() {
        return this.showIcon;
    }

    public boolean update(LivingEntity entity, Runnable overwriteCallback) {
        if (this.duration > 0) {
            if (this.type.canApplyUpdateEffect(this.duration, this.amplifier)) {
                this.applyUpdateEffect(entity);
            }
            this.updateDuration();
            if (this.duration == 0 && this.hiddenEffect != null) {
                this.copyFrom(this.hiddenEffect);
                this.hiddenEffect = this.hiddenEffect.hiddenEffect;
                overwriteCallback.run();
            }
        }
        return this.duration > 0;
    }

    private int updateDuration() {
        if (this.hiddenEffect != null) {
            this.hiddenEffect.updateDuration();
        }
        return --this.duration;
    }

    public void applyUpdateEffect(LivingEntity entity) {
        if (this.duration > 0) {
            this.type.applyUpdateEffect(entity, this.amplifier);
        }
    }

    public String getTranslationKey() {
        return this.type.getTranslationKey();
    }

    public String toString() {
        String string = this.amplifier > 0 ? this.getTranslationKey() + " x " + (this.amplifier + 1) + ", Duration: " + this.duration : this.getTranslationKey() + ", Duration: " + this.duration;
        if (this.splash) {
            string = string + ", Splash: true";
        }
        if (!this.showParticles) {
            string = string + ", Particles: false";
        }
        if (!this.showIcon) {
            string = string + ", Show Icon: false";
        }
        return string;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof StatusEffectInstance) {
            StatusEffectInstance statusEffectInstance = (StatusEffectInstance)o;
            return this.duration == statusEffectInstance.duration && this.amplifier == statusEffectInstance.amplifier && this.splash == statusEffectInstance.splash && this.ambient == statusEffectInstance.ambient && this.type.equals(statusEffectInstance.type);
        }
        return false;
    }

    public int hashCode() {
        int n = this.type.hashCode();
        n = 31 * n + this.duration;
        n = 31 * n + this.amplifier;
        n = 31 * n + (this.splash ? 1 : 0);
        n = 31 * n + (this.ambient ? 1 : 0);
        return n;
    }

    public CompoundTag toTag(CompoundTag tag) {
        tag.putByte("Id", (byte)StatusEffect.getRawId(this.getEffectType()));
        this.typelessToTag(tag);
        return tag;
    }

    private void typelessToTag(CompoundTag tag) {
        tag.putByte("Amplifier", (byte)this.getAmplifier());
        tag.putInt("Duration", this.getDuration());
        tag.putBoolean("Ambient", this.isAmbient());
        tag.putBoolean("ShowParticles", this.shouldShowParticles());
        tag.putBoolean("ShowIcon", this.shouldShowIcon());
        if (this.hiddenEffect != null) {
            CompoundTag compoundTag = new CompoundTag();
            this.hiddenEffect.toTag(compoundTag);
            tag.put("HiddenEffect", compoundTag);
        }
    }

    public static StatusEffectInstance fromTag(CompoundTag tag) {
        byte by = tag.getByte("Id");
        StatusEffect _snowman2 = StatusEffect.byRawId(by);
        if (_snowman2 == null) {
            return null;
        }
        return StatusEffectInstance.fromTag(_snowman2, tag);
    }

    private static StatusEffectInstance fromTag(StatusEffect type, CompoundTag tag) {
        byte by = tag.getByte("Amplifier");
        int _snowman2 = tag.getInt("Duration");
        boolean _snowman3 = tag.getBoolean("Ambient");
        boolean _snowman4 = true;
        if (tag.contains("ShowParticles", 1)) {
            _snowman4 = tag.getBoolean("ShowParticles");
        }
        boolean _snowman5 = _snowman4;
        if (tag.contains("ShowIcon", 1)) {
            _snowman5 = tag.getBoolean("ShowIcon");
        }
        StatusEffectInstance _snowman6 = null;
        if (tag.contains("HiddenEffect", 10)) {
            _snowman6 = StatusEffectInstance.fromTag(type, tag.getCompound("HiddenEffect"));
        }
        return new StatusEffectInstance(type, _snowman2, by < 0 ? (byte)0 : by, _snowman3, _snowman4, _snowman5, _snowman6);
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public boolean isPermanent() {
        return this.permanent;
    }

    @Override
    public int compareTo(StatusEffectInstance statusEffectInstance) {
        int n = 32147;
        if (this.getDuration() > 32147 && statusEffectInstance.getDuration() > 32147 || this.isAmbient() && statusEffectInstance.isAmbient()) {
            return ComparisonChain.start().compare(Boolean.valueOf(this.isAmbient()), Boolean.valueOf(statusEffectInstance.isAmbient())).compare(this.getEffectType().getColor(), statusEffectInstance.getEffectType().getColor()).result();
        }
        return ComparisonChain.start().compare(Boolean.valueOf(this.isAmbient()), Boolean.valueOf(statusEffectInstance.isAmbient())).compare(this.getDuration(), statusEffectInstance.getDuration()).compare(this.getEffectType().getColor(), statusEffectInstance.getEffectType().getColor()).result();
    }

    @Override
    public /* synthetic */ int compareTo(Object that) {
        return this.compareTo((StatusEffectInstance)that);
    }
}

