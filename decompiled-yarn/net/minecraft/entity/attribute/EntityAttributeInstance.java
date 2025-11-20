package net.minecraft.entity.attribute;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.registry.Registry;

public class EntityAttributeInstance {
   private final EntityAttribute type;
   private final Map<EntityAttributeModifier.Operation, Set<EntityAttributeModifier>> operationToModifiers = Maps.newEnumMap(
      EntityAttributeModifier.Operation.class
   );
   private final Map<UUID, EntityAttributeModifier> idToModifiers = new Object2ObjectArrayMap();
   private final Set<EntityAttributeModifier> persistentModifiers = new ObjectArraySet();
   private double baseValue;
   private boolean dirty = true;
   private double value;
   private final Consumer<EntityAttributeInstance> updateCallback;

   public EntityAttributeInstance(EntityAttribute type, Consumer<EntityAttributeInstance> updateCallback) {
      this.type = type;
      this.updateCallback = updateCallback;
      this.baseValue = type.getDefaultValue();
   }

   public EntityAttribute getAttribute() {
      return this.type;
   }

   public double getBaseValue() {
      return this.baseValue;
   }

   public void setBaseValue(double baseValue) {
      if (baseValue != this.baseValue) {
         this.baseValue = baseValue;
         this.onUpdate();
      }
   }

   public Set<EntityAttributeModifier> getModifiers(EntityAttributeModifier.Operation operation) {
      return this.operationToModifiers.computeIfAbsent(operation, operationx -> Sets.newHashSet());
   }

   public Set<EntityAttributeModifier> getModifiers() {
      return ImmutableSet.copyOf(this.idToModifiers.values());
   }

   @Nullable
   public EntityAttributeModifier getModifier(UUID uuid) {
      return this.idToModifiers.get(uuid);
   }

   public boolean hasModifier(EntityAttributeModifier modifier) {
      return this.idToModifiers.get(modifier.getId()) != null;
   }

   private void addModifier(EntityAttributeModifier modifier) {
      EntityAttributeModifier _snowman = this.idToModifiers.putIfAbsent(modifier.getId(), modifier);
      if (_snowman != null) {
         throw new IllegalArgumentException("Modifier is already applied on this attribute!");
      } else {
         this.getModifiers(modifier.getOperation()).add(modifier);
         this.onUpdate();
      }
   }

   public void addTemporaryModifier(EntityAttributeModifier modifier) {
      this.addModifier(modifier);
   }

   public void addPersistentModifier(EntityAttributeModifier modifier) {
      this.addModifier(modifier);
      this.persistentModifiers.add(modifier);
   }

   protected void onUpdate() {
      this.dirty = true;
      this.updateCallback.accept(this);
   }

   public void removeModifier(EntityAttributeModifier modifier) {
      this.getModifiers(modifier.getOperation()).remove(modifier);
      this.idToModifiers.remove(modifier.getId());
      this.persistentModifiers.remove(modifier);
      this.onUpdate();
   }

   public void removeModifier(UUID uuid) {
      EntityAttributeModifier _snowman = this.getModifier(uuid);
      if (_snowman != null) {
         this.removeModifier(_snowman);
      }
   }

   public boolean tryRemoveModifier(UUID uuid) {
      EntityAttributeModifier _snowman = this.getModifier(uuid);
      if (_snowman != null && this.persistentModifiers.contains(_snowman)) {
         this.removeModifier(_snowman);
         return true;
      } else {
         return false;
      }
   }

   public void clearModifiers() {
      for (EntityAttributeModifier _snowman : this.getModifiers()) {
         this.removeModifier(_snowman);
      }
   }

   public double getValue() {
      if (this.dirty) {
         this.value = this.computeValue();
         this.dirty = false;
      }

      return this.value;
   }

   private double computeValue() {
      double _snowman = this.getBaseValue();

      for (EntityAttributeModifier _snowmanx : this.getModifiersByOperation(EntityAttributeModifier.Operation.ADDITION)) {
         _snowman += _snowmanx.getValue();
      }

      double _snowmanx = _snowman;

      for (EntityAttributeModifier _snowmanxx : this.getModifiersByOperation(EntityAttributeModifier.Operation.MULTIPLY_BASE)) {
         _snowmanx += _snowman * _snowmanxx.getValue();
      }

      for (EntityAttributeModifier _snowmanxx : this.getModifiersByOperation(EntityAttributeModifier.Operation.MULTIPLY_TOTAL)) {
         _snowmanx *= 1.0 + _snowmanxx.getValue();
      }

      return this.type.clamp(_snowmanx);
   }

   private Collection<EntityAttributeModifier> getModifiersByOperation(EntityAttributeModifier.Operation operation) {
      return this.operationToModifiers.getOrDefault(operation, Collections.emptySet());
   }

   public void setFrom(EntityAttributeInstance other) {
      this.baseValue = other.baseValue;
      this.idToModifiers.clear();
      this.idToModifiers.putAll(other.idToModifiers);
      this.persistentModifiers.clear();
      this.persistentModifiers.addAll(other.persistentModifiers);
      this.operationToModifiers.clear();
      other.operationToModifiers.forEach((operation, modifiers) -> this.getModifiers(operation).addAll(modifiers));
      this.onUpdate();
   }

   public CompoundTag toTag() {
      CompoundTag _snowman = new CompoundTag();
      _snowman.putString("Name", Registry.ATTRIBUTE.getId(this.type).toString());
      _snowman.putDouble("Base", this.baseValue);
      if (!this.persistentModifiers.isEmpty()) {
         ListTag _snowmanx = new ListTag();

         for (EntityAttributeModifier _snowmanxx : this.persistentModifiers) {
            _snowmanx.add(_snowmanxx.toTag());
         }

         _snowman.put("Modifiers", _snowmanx);
      }

      return _snowman;
   }

   public void fromTag(CompoundTag tag) {
      this.baseValue = tag.getDouble("Base");
      if (tag.contains("Modifiers", 9)) {
         ListTag _snowman = tag.getList("Modifiers", 10);

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            EntityAttributeModifier _snowmanxx = EntityAttributeModifier.fromTag(_snowman.getCompound(_snowmanx));
            if (_snowmanxx != null) {
               this.idToModifiers.put(_snowmanxx.getId(), _snowmanxx);
               this.getModifiers(_snowmanxx.getOperation()).add(_snowmanxx);
               this.persistentModifiers.add(_snowmanxx);
            }
         }
      }

      this.onUpdate();
   }
}
