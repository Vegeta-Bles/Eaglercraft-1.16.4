package net.minecraft.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum DoubleBlockHalf implements StringIdentifiable {
   UPPER,
   LOWER;

   private DoubleBlockHalf() {
   }

   @Override
   public String toString() {
      return this.asString();
   }

   @Override
   public String asString() {
      return this == UPPER ? "upper" : "lower";
   }
}
