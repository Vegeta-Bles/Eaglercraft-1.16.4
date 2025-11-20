package net.minecraft.client.color.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.collection.IdList;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class ItemColors {
   private final IdList<ItemColorProvider> providers = new IdList<>(32);

   public ItemColors() {
   }

   public static ItemColors create(BlockColors blockColors) {
      ItemColors lv = new ItemColors();
      lv.register(
         (stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem)stack.getItem()).getColor(stack),
         Items.LEATHER_HELMET,
         Items.LEATHER_CHESTPLATE,
         Items.LEATHER_LEGGINGS,
         Items.LEATHER_BOOTS,
         Items.LEATHER_HORSE_ARMOR
      );
      lv.register((stack, tintIndex) -> GrassColors.getColor(0.5, 1.0), Blocks.TALL_GRASS, Blocks.LARGE_FERN);
      lv.register((stack, tintIndex) -> {
         if (tintIndex != 1) {
            return -1;
         } else {
            CompoundTag lvx = stack.getSubTag("Explosion");
            int[] is = lvx != null && lvx.contains("Colors", 11) ? lvx.getIntArray("Colors") : null;
            if (is != null && is.length != 0) {
               if (is.length == 1) {
                  return is[0];
               } else {
                  int j = 0;
                  int k = 0;
                  int l = 0;

                  for (int m : is) {
                     j += (m & 0xFF0000) >> 16;
                     k += (m & 0xFF00) >> 8;
                     l += (m & 0xFF) >> 0;
                  }

                  j /= is.length;
                  k /= is.length;
                  l /= is.length;
                  return j << 16 | k << 8 | l;
               }
            } else {
               return 9079434;
            }
         }
      }, Items.FIREWORK_STAR);
      lv.register((stack, tintIndex) -> tintIndex > 0 ? -1 : PotionUtil.getColor(stack), Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION);

      for (SpawnEggItem lv2 : SpawnEggItem.getAll()) {
         lv.register((stack, tintIndex) -> lv2.getColor(tintIndex), lv2);
      }

      lv.register(
         (stack, tintIndex) -> {
            BlockState lvx = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return blockColors.getColor(lvx, null, null, tintIndex);
         },
         Blocks.GRASS_BLOCK,
         Blocks.GRASS,
         Blocks.FERN,
         Blocks.VINE,
         Blocks.OAK_LEAVES,
         Blocks.SPRUCE_LEAVES,
         Blocks.BIRCH_LEAVES,
         Blocks.JUNGLE_LEAVES,
         Blocks.ACACIA_LEAVES,
         Blocks.DARK_OAK_LEAVES,
         Blocks.LILY_PAD
      );
      lv.register((stack, tintIndex) -> tintIndex == 0 ? PotionUtil.getColor(stack) : -1, Items.TIPPED_ARROW);
      lv.register((stack, tintIndex) -> tintIndex == 0 ? -1 : FilledMapItem.getMapColor(stack), Items.FILLED_MAP);
      return lv;
   }

   public int getColorMultiplier(ItemStack item, int tintIndex) {
      ItemColorProvider lv = this.providers.get(Registry.ITEM.getRawId(item.getItem()));
      return lv == null ? -1 : lv.getColor(item, tintIndex);
   }

   public void register(ItemColorProvider mapper, ItemConvertible... items) {
      for (ItemConvertible lv : items) {
         this.providers.set(mapper, Item.getRawId(lv.asItem()));
      }
   }
}
