package net.minecraft.entity.effect;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class StatusEffect {
   private final Map<EntityAttribute, EntityAttributeModifier> attributeModifiers = Maps.newHashMap();
   private final StatusEffectType type;
   private final int color;
   @Nullable
   private String translationKey;

   @Nullable
   public static StatusEffect byRawId(int rawId) {
      return Registry.STATUS_EFFECT.get(rawId);
   }

   public static int getRawId(StatusEffect type) {
      return Registry.STATUS_EFFECT.getRawId(type);
   }

   protected StatusEffect(StatusEffectType type, int color) {
      this.type = type;
      this.color = color;
   }

   public void applyUpdateEffect(LivingEntity entity, int amplifier) {
      if (this == StatusEffects.REGENERATION) {
         if (entity.getHealth() < entity.getMaxHealth()) {
            entity.heal(1.0F);
         }
      } else if (this == StatusEffects.POISON) {
         if (entity.getHealth() > 1.0F) {
            entity.damage(DamageSource.MAGIC, 1.0F);
         }
      } else if (this == StatusEffects.WITHER) {
         entity.damage(DamageSource.WITHER, 1.0F);
      } else if (this == StatusEffects.HUNGER && entity instanceof PlayerEntity) {
         ((PlayerEntity)entity).addExhaustion(0.005F * (float)(amplifier + 1));
      } else if (this == StatusEffects.SATURATION && entity instanceof PlayerEntity) {
         if (!entity.world.isClient) {
            ((PlayerEntity)entity).getHungerManager().add(amplifier + 1, 1.0F);
         }
      } else if ((this != StatusEffects.INSTANT_HEALTH || entity.isUndead()) && (this != StatusEffects.INSTANT_DAMAGE || !entity.isUndead())) {
         if (this == StatusEffects.INSTANT_DAMAGE && !entity.isUndead() || this == StatusEffects.INSTANT_HEALTH && entity.isUndead()) {
            entity.damage(DamageSource.MAGIC, (float)(6 << amplifier));
         }
      } else {
         entity.heal((float)Math.max(4 << amplifier, 0));
      }
   }

   public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
      if ((this != StatusEffects.INSTANT_HEALTH || target.isUndead()) && (this != StatusEffects.INSTANT_DAMAGE || !target.isUndead())) {
         if (this == StatusEffects.INSTANT_DAMAGE && !target.isUndead() || this == StatusEffects.INSTANT_HEALTH && target.isUndead()) {
            int _snowman = (int)(proximity * (double)(6 << amplifier) + 0.5);
            if (source == null) {
               target.damage(DamageSource.MAGIC, (float)_snowman);
            } else {
               target.damage(DamageSource.magic(source, attacker), (float)_snowman);
            }
         } else {
            this.applyUpdateEffect(target, amplifier);
         }
      } else {
         int _snowman = (int)(proximity * (double)(4 << amplifier) + 0.5);
         target.heal((float)_snowman);
      }
   }

   public boolean canApplyUpdateEffect(int duration, int amplifier) {
      if (this == StatusEffects.REGENERATION) {
         int _snowman = 50 >> amplifier;
         return _snowman > 0 ? duration % _snowman == 0 : true;
      } else if (this == StatusEffects.POISON) {
         int _snowman = 25 >> amplifier;
         return _snowman > 0 ? duration % _snowman == 0 : true;
      } else if (this == StatusEffects.WITHER) {
         int _snowman = 40 >> amplifier;
         return _snowman > 0 ? duration % _snowman == 0 : true;
      } else {
         return this == StatusEffects.HUNGER;
      }
   }

   public boolean isInstant() {
      return false;
   }

   protected String loadTranslationKey() {
      if (this.translationKey == null) {
         this.translationKey = Util.createTranslationKey("effect", Registry.STATUS_EFFECT.getId(this));
      }

      return this.translationKey;
   }

   public String getTranslationKey() {
      return this.loadTranslationKey();
   }

   public Text getName() {
      return new TranslatableText(this.getTranslationKey());
   }

   public StatusEffectType getType() {
      return this.type;
   }

   public int getColor() {
      return this.color;
   }

   public StatusEffect addAttributeModifier(EntityAttribute attribute, String uuid, double amount, EntityAttributeModifier.Operation operation) {
      EntityAttributeModifier _snowman = new EntityAttributeModifier(UUID.fromString(uuid), this::getTranslationKey, amount, operation);
      this.attributeModifiers.put(attribute, _snowman);
      return this;
   }

   public Map<EntityAttribute, EntityAttributeModifier> getAttributeModifiers() {
      return this.attributeModifiers;
   }

   public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
      for (Entry<EntityAttribute, EntityAttributeModifier> _snowman : this.attributeModifiers.entrySet()) {
         EntityAttributeInstance _snowmanx = attributes.getCustomInstance(_snowman.getKey());
         if (_snowmanx != null) {
            _snowmanx.removeModifier(_snowman.getValue());
         }
      }
   }

   public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
      for (Entry<EntityAttribute, EntityAttributeModifier> _snowman : this.attributeModifiers.entrySet()) {
         EntityAttributeInstance _snowmanx = attributes.getCustomInstance(_snowman.getKey());
         if (_snowmanx != null) {
            EntityAttributeModifier _snowmanxx = _snowman.getValue();
            _snowmanx.removeModifier(_snowmanxx);
            _snowmanx.addPersistentModifier(
               new EntityAttributeModifier(
                  _snowmanxx.getId(), this.getTranslationKey() + " " + amplifier, this.adjustModifierAmount(amplifier, _snowmanxx), _snowmanxx.getOperation()
               )
            );
         }
      }
   }

   public double adjustModifierAmount(int amplifier, EntityAttributeModifier modifier) {
      return modifier.getValue() * (double)(amplifier + 1);
   }

   public boolean isBeneficial() {
      return this.type == StatusEffectType.BENEFICIAL;
   }
}
