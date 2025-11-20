package net.minecraft.util;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemScatterer {
   private static final Random RANDOM = new Random();

   public static void spawn(World _snowman, BlockPos _snowman, Inventory _snowman) {
      spawn(_snowman, (double)_snowman.getX(), (double)_snowman.getY(), (double)_snowman.getZ(), _snowman);
   }

   public static void spawn(World _snowman, Entity _snowman, Inventory _snowman) {
      spawn(_snowman, _snowman.getX(), _snowman.getY(), _snowman.getZ(), _snowman);
   }

   private static void spawn(World world, double x, double y, double z, Inventory inventory) {
      for (int _snowman = 0; _snowman < inventory.size(); _snowman++) {
         spawn(world, x, y, z, inventory.getStack(_snowman));
      }
   }

   public static void spawn(World world, BlockPos pos, DefaultedList<ItemStack> items) {
      items.forEach(_snowmanxx -> spawn(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), _snowmanxx));
   }

   public static void spawn(World _snowman, double x, double y, double z, ItemStack item) {
      double _snowmanx = (double)EntityType.ITEM.getWidth();
      double _snowmanxx = 1.0 - _snowmanx;
      double _snowmanxxx = _snowmanx / 2.0;
      double _snowmanxxxx = Math.floor(x) + RANDOM.nextDouble() * _snowmanxx + _snowmanxxx;
      double _snowmanxxxxx = Math.floor(y) + RANDOM.nextDouble() * _snowmanxx;
      double _snowmanxxxxxx = Math.floor(z) + RANDOM.nextDouble() * _snowmanxx + _snowmanxxx;

      while (!item.isEmpty()) {
         ItemEntity _snowmanxxxxxxx = new ItemEntity(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, item.split(RANDOM.nextInt(21) + 10));
         float _snowmanxxxxxxxx = 0.05F;
         _snowmanxxxxxxx.setVelocity(RANDOM.nextGaussian() * 0.05F, RANDOM.nextGaussian() * 0.05F + 0.2F, RANDOM.nextGaussian() * 0.05F);
         _snowman.spawnEntity(_snowmanxxxxxxx);
      }
   }
}
