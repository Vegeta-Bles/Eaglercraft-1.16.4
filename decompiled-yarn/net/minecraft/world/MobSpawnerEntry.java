package net.minecraft.world;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.WeightedPicker;

public class MobSpawnerEntry extends WeightedPicker.Entry {
   private final CompoundTag entityTag;

   public MobSpawnerEntry() {
      super(1);
      this.entityTag = new CompoundTag();
      this.entityTag.putString("id", "minecraft:pig");
   }

   public MobSpawnerEntry(CompoundTag tag) {
      this(tag.contains("Weight", 99) ? tag.getInt("Weight") : 1, tag.getCompound("Entity"));
   }

   public MobSpawnerEntry(int weight, CompoundTag entityTag) {
      super(weight);
      this.entityTag = entityTag;
      Identifier _snowman = Identifier.tryParse(entityTag.getString("id"));
      if (_snowman != null) {
         entityTag.putString("id", _snowman.toString());
      } else {
         entityTag.putString("id", "minecraft:pig");
      }
   }

   public CompoundTag serialize() {
      CompoundTag _snowman = new CompoundTag();
      _snowman.put("Entity", this.entityTag);
      _snowman.putInt("Weight", this.weight);
      return _snowman;
   }

   public CompoundTag getEntityTag() {
      return this.entityTag;
   }
}
