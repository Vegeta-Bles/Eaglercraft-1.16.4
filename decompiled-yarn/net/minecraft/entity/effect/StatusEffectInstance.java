package net.minecraft.entity.effect;

import com.google.common.collect.ComparisonChain;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StatusEffectInstance implements Comparable<StatusEffectInstance> {
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

   public StatusEffectInstance(StatusEffect _snowman) {
      this(_snowman, 0, 0);
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

   public StatusEffectInstance(
      StatusEffect type, int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon, @Nullable StatusEffectInstance hiddenEffect
   ) {
      this.type = type;
      this.duration = duration;
      this.amplifier = amplifier;
      this.ambient = ambient;
      this.showParticles = showParticles;
      this.showIcon = showIcon;
      this.hiddenEffect = hiddenEffect;
   }

   public StatusEffectInstance(StatusEffectInstance _snowman) {
      this.type = _snowman.type;
      this.copyFrom(_snowman);
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

      boolean _snowman = false;
      if (that.amplifier > this.amplifier) {
         if (that.duration < this.duration) {
            StatusEffectInstance _snowmanx = this.hiddenEffect;
            this.hiddenEffect = new StatusEffectInstance(this);
            this.hiddenEffect.hiddenEffect = _snowmanx;
         }

         this.amplifier = that.amplifier;
         this.duration = that.duration;
         _snowman = true;
      } else if (that.duration > this.duration) {
         if (that.amplifier == this.amplifier) {
            this.duration = that.duration;
            _snowman = true;
         } else if (this.hiddenEffect == null) {
            this.hiddenEffect = new StatusEffectInstance(that);
         } else {
            this.hiddenEffect.upgrade(that);
         }
      }

      if (!that.ambient && this.ambient || _snowman) {
         this.ambient = that.ambient;
         _snowman = true;
      }

      if (that.showParticles != this.showParticles) {
         this.showParticles = that.showParticles;
         _snowman = true;
      }

      if (that.showIcon != this.showIcon) {
         this.showIcon = that.showIcon;
         _snowman = true;
      }

      return _snowman;
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

   @Override
   public String toString() {
      String _snowman;
      if (this.amplifier > 0) {
         _snowman = this.getTranslationKey() + " x " + (this.amplifier + 1) + ", Duration: " + this.duration;
      } else {
         _snowman = this.getTranslationKey() + ", Duration: " + this.duration;
      }

      if (this.splash) {
         _snowman = _snowman + ", Splash: true";
      }

      if (!this.showParticles) {
         _snowman = _snowman + ", Particles: false";
      }

      if (!this.showIcon) {
         _snowman = _snowman + ", Show Icon: false";
      }

      return _snowman;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof StatusEffectInstance)) {
         return false;
      } else {
         StatusEffectInstance _snowman = (StatusEffectInstance)o;
         return this.duration == _snowman.duration
            && this.amplifier == _snowman.amplifier
            && this.splash == _snowman.splash
            && this.ambient == _snowman.ambient
            && this.type.equals(_snowman.type);
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.type.hashCode();
      _snowman = 31 * _snowman + this.duration;
      _snowman = 31 * _snowman + this.amplifier;
      _snowman = 31 * _snowman + (this.splash ? 1 : 0);
      return 31 * _snowman + (this.ambient ? 1 : 0);
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
         CompoundTag _snowman = new CompoundTag();
         this.hiddenEffect.toTag(_snowman);
         tag.put("HiddenEffect", _snowman);
      }
   }

   public static StatusEffectInstance fromTag(CompoundTag tag) {
      int _snowman = tag.getByte("Id");
      StatusEffect _snowmanx = StatusEffect.byRawId(_snowman);
      return _snowmanx == null ? null : fromTag(_snowmanx, tag);
   }

   private static StatusEffectInstance fromTag(StatusEffect type, CompoundTag tag) {
      int _snowman = tag.getByte("Amplifier");
      int _snowmanx = tag.getInt("Duration");
      boolean _snowmanxx = tag.getBoolean("Ambient");
      boolean _snowmanxxx = true;
      if (tag.contains("ShowParticles", 1)) {
         _snowmanxxx = tag.getBoolean("ShowParticles");
      }

      boolean _snowmanxxxx = _snowmanxxx;
      if (tag.contains("ShowIcon", 1)) {
         _snowmanxxxx = tag.getBoolean("ShowIcon");
      }

      StatusEffectInstance _snowmanxxxxx = null;
      if (tag.contains("HiddenEffect", 10)) {
         _snowmanxxxxx = fromTag(type, tag.getCompound("HiddenEffect"));
      }

      return new StatusEffectInstance(type, _snowmanx, _snowman < 0 ? 0 : _snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   public void setPermanent(boolean permanent) {
      this.permanent = permanent;
   }

   public boolean isPermanent() {
      return this.permanent;
   }

   public int compareTo(StatusEffectInstance _snowman) {
      int _snowmanx = 32147;
      return (this.getDuration() <= 32147 || _snowman.getDuration() <= 32147) && (!this.isAmbient() || !_snowman.isAmbient())
         ? ComparisonChain.start()
            .compare(this.isAmbient(), _snowman.isAmbient())
            .compare(this.getDuration(), _snowman.getDuration())
            .compare(this.getEffectType().getColor(), _snowman.getEffectType().getColor())
            .result()
         : ComparisonChain.start().compare(this.isAmbient(), _snowman.isAmbient()).compare(this.getEffectType().getColor(), _snowman.getEffectType().getColor()).result();
   }
}
