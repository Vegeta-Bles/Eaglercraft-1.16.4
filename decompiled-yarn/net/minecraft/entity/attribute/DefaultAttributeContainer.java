package net.minecraft.entity.attribute;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.util.registry.Registry;

public class DefaultAttributeContainer {
   private final Map<EntityAttribute, EntityAttributeInstance> instances;

   public DefaultAttributeContainer(Map<EntityAttribute, EntityAttributeInstance> instances) {
      this.instances = ImmutableMap.copyOf(instances);
   }

   private EntityAttributeInstance require(EntityAttribute attribute) {
      EntityAttributeInstance _snowman = this.instances.get(attribute);
      if (_snowman == null) {
         throw new IllegalArgumentException("Can't find attribute " + Registry.ATTRIBUTE.getId(attribute));
      } else {
         return _snowman;
      }
   }

   public double getValue(EntityAttribute attribute) {
      return this.require(attribute).getValue();
   }

   public double getBaseValue(EntityAttribute attribute) {
      return this.require(attribute).getBaseValue();
   }

   public double getModifierValue(EntityAttribute attribute, UUID uuid) {
      EntityAttributeModifier _snowman = this.require(attribute).getModifier(uuid);
      if (_snowman == null) {
         throw new IllegalArgumentException("Can't find modifier " + uuid + " on attribute " + Registry.ATTRIBUTE.getId(attribute));
      } else {
         return _snowman.getValue();
      }
   }

   @Nullable
   public EntityAttributeInstance createOverride(Consumer<EntityAttributeInstance> updateCallback, EntityAttribute attribute) {
      EntityAttributeInstance _snowman = this.instances.get(attribute);
      if (_snowman == null) {
         return null;
      } else {
         EntityAttributeInstance _snowmanx = new EntityAttributeInstance(attribute, updateCallback);
         _snowmanx.setFrom(_snowman);
         return _snowmanx;
      }
   }

   public static DefaultAttributeContainer.Builder builder() {
      return new DefaultAttributeContainer.Builder();
   }

   public boolean has(EntityAttribute type) {
      return this.instances.containsKey(type);
   }

   public boolean hasModifier(EntityAttribute type, UUID uuid) {
      EntityAttributeInstance _snowman = this.instances.get(type);
      return _snowman != null && _snowman.getModifier(uuid) != null;
   }

   public static class Builder {
      private final Map<EntityAttribute, EntityAttributeInstance> instances = Maps.newHashMap();
      private boolean unmodifiable;

      public Builder() {
      }

      private EntityAttributeInstance checkedAdd(EntityAttribute attribute) {
         EntityAttributeInstance _snowman = new EntityAttributeInstance(attribute, attributex -> {
            if (this.unmodifiable) {
               throw new UnsupportedOperationException("Tried to change value for default attribute instance: " + Registry.ATTRIBUTE.getId(attribute));
            }
         });
         this.instances.put(attribute, _snowman);
         return _snowman;
      }

      public DefaultAttributeContainer.Builder add(EntityAttribute attribute) {
         this.checkedAdd(attribute);
         return this;
      }

      public DefaultAttributeContainer.Builder add(EntityAttribute attribute, double baseValue) {
         EntityAttributeInstance _snowman = this.checkedAdd(attribute);
         _snowman.setBaseValue(baseValue);
         return this;
      }

      public DefaultAttributeContainer build() {
         this.unmodifiable = true;
         return new DefaultAttributeContainer(this.instances);
      }
   }
}
