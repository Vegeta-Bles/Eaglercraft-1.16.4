package net.minecraft.entity.attribute;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AttributeContainer {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map<EntityAttribute, EntityAttributeInstance> custom = Maps.newHashMap();
   private final Set<EntityAttributeInstance> tracked = Sets.newHashSet();
   private final DefaultAttributeContainer fallback;

   public AttributeContainer(DefaultAttributeContainer defaultAttributes) {
      this.fallback = defaultAttributes;
   }

   private void updateTrackedStatus(EntityAttributeInstance instance) {
      if (instance.getAttribute().isTracked()) {
         this.tracked.add(instance);
      }
   }

   public Set<EntityAttributeInstance> getTracked() {
      return this.tracked;
   }

   public Collection<EntityAttributeInstance> getAttributesToSend() {
      return this.custom.values().stream().filter(attribute -> attribute.getAttribute().isTracked()).collect(Collectors.toList());
   }

   @Nullable
   public EntityAttributeInstance getCustomInstance(EntityAttribute attribute) {
      return this.custom.computeIfAbsent(attribute, attributex -> this.fallback.createOverride(this::updateTrackedStatus, attributex));
   }

   public boolean hasAttribute(EntityAttribute attribute) {
      return this.custom.get(attribute) != null || this.fallback.has(attribute);
   }

   public boolean hasModifierForAttribute(EntityAttribute attribute, UUID uuid) {
      EntityAttributeInstance _snowman = this.custom.get(attribute);
      return _snowman != null ? _snowman.getModifier(uuid) != null : this.fallback.hasModifier(attribute, uuid);
   }

   public double getValue(EntityAttribute attribute) {
      EntityAttributeInstance _snowman = this.custom.get(attribute);
      return _snowman != null ? _snowman.getValue() : this.fallback.getValue(attribute);
   }

   public double getBaseValue(EntityAttribute attribute) {
      EntityAttributeInstance _snowman = this.custom.get(attribute);
      return _snowman != null ? _snowman.getBaseValue() : this.fallback.getBaseValue(attribute);
   }

   public double getModifierValue(EntityAttribute attribute, UUID uuid) {
      EntityAttributeInstance _snowman = this.custom.get(attribute);
      return _snowman != null ? _snowman.getModifier(uuid).getValue() : this.fallback.getModifierValue(attribute, uuid);
   }

   public void removeModifiers(Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers) {
      attributeModifiers.asMap().forEach((_snowman, _snowmanx) -> {
         EntityAttributeInstance _snowmanxx = this.custom.get(_snowman);
         if (_snowmanxx != null) {
            _snowmanx.forEach(_snowmanxx::removeModifier);
         }
      });
   }

   public void addTemporaryModifiers(Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers) {
      attributeModifiers.forEach((_snowman, _snowmanx) -> {
         EntityAttributeInstance _snowmanxx = this.getCustomInstance(_snowman);
         if (_snowmanxx != null) {
            _snowmanxx.removeModifier(_snowmanx);
            _snowmanxx.addTemporaryModifier(_snowmanx);
         }
      });
   }

   public void setFrom(AttributeContainer other) {
      other.custom.values().forEach(_snowman -> {
         EntityAttributeInstance _snowmanx = this.getCustomInstance(_snowman.getAttribute());
         if (_snowmanx != null) {
            _snowmanx.setFrom(_snowman);
         }
      });
   }

   public ListTag toTag() {
      ListTag _snowman = new ListTag();

      for (EntityAttributeInstance _snowmanx : this.custom.values()) {
         _snowman.add(_snowmanx.toTag());
      }

      return _snowman;
   }

   public void fromTag(ListTag tag) {
      for (int _snowman = 0; _snowman < tag.size(); _snowman++) {
         CompoundTag _snowmanx = tag.getCompound(_snowman);
         String _snowmanxx = _snowmanx.getString("Name");
         Util.ifPresentOrElse(Registry.ATTRIBUTE.getOrEmpty(Identifier.tryParse(_snowmanxx)), _snowmanxxx -> {
            EntityAttributeInstance _snowmanxxxx = this.getCustomInstance(_snowmanxxx);
            if (_snowmanxxxx != null) {
               _snowmanxxxx.fromTag(_snowman);
            }
         }, () -> LOGGER.warn("Ignoring unknown attribute '{}'", _snowman));
      }
   }
}
