package net.minecraft.util.function;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;

public class MaterialPredicate implements Predicate<BlockState> {
   private static final MaterialPredicate IS_AIR = new MaterialPredicate(Material.AIR) {
      @Override
      public boolean test(@Nullable BlockState arg) {
         return arg != null && arg.isAir();
      }
   };
   private final Material material;

   private MaterialPredicate(Material material) {
      this.material = material;
   }

   public static MaterialPredicate create(Material material) {
      return material == Material.AIR ? IS_AIR : new MaterialPredicate(material);
   }

   public boolean test(@Nullable BlockState arg) {
      return arg != null && arg.getMaterial() == this.material;
   }
}
