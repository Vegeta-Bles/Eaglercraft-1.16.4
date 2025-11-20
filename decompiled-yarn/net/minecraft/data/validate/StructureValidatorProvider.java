package net.minecraft.data.validate;

import net.minecraft.data.SnbtProvider;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.datafixer.Schemas;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.structure.Structure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureValidatorProvider implements SnbtProvider.Tweaker {
   private static final Logger field_24617 = LogManager.getLogger();

   public StructureValidatorProvider() {
   }

   @Override
   public CompoundTag write(String name, CompoundTag nbt) {
      return name.startsWith("data/minecraft/structures/") ? update(name, addDataVersion(nbt)) : nbt;
   }

   private static CompoundTag addDataVersion(CompoundTag nbt) {
      if (!nbt.contains("DataVersion", 99)) {
         nbt.putInt("DataVersion", 500);
      }

      return nbt;
   }

   private static CompoundTag update(String _snowman, CompoundTag _snowman) {
      Structure _snowmanxx = new Structure();
      int _snowmanxxx = _snowman.getInt("DataVersion");
      int _snowmanxxxx = 2532;
      if (_snowmanxxx < 2532) {
         field_24617.warn("SNBT Too old, do not forget to update: " + _snowmanxxx + " < " + 2532 + ": " + _snowman);
      }

      CompoundTag _snowmanxxxxx = NbtHelper.update(Schemas.getFixer(), DataFixTypes.STRUCTURE, _snowman, _snowmanxxx);
      _snowmanxx.fromTag(_snowmanxxxxx);
      return _snowmanxx.toTag(new CompoundTag());
   }
}
