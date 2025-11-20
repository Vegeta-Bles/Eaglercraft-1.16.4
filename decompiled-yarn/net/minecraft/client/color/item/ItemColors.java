package net.minecraft.client.color.item;

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

public class ItemColors {
   private final IdList<ItemColorProvider> providers = new IdList<>(32);

   public ItemColors() {
   }

   public static ItemColors create(BlockColors blockColors) {
      ItemColors _snowman = new ItemColors();
      _snowman.register(
         (stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem)stack.getItem()).getColor(stack),
         Items.LEATHER_HELMET,
         Items.LEATHER_CHESTPLATE,
         Items.LEATHER_LEGGINGS,
         Items.LEATHER_BOOTS,
         Items.LEATHER_HORSE_ARMOR
      );
      _snowman.register((stack, tintIndex) -> GrassColors.getColor(0.5, 1.0), Blocks.TALL_GRASS, Blocks.LARGE_FERN);
      _snowman.register((stack, tintIndex) -> {
         if (tintIndex != 1) {
            return -1;
         } else {
            CompoundTag _snowmanx = stack.getSubTag("Explosion");
            int[] _snowmanx = _snowmanx != null && _snowmanx.contains("Colors", 11) ? _snowmanx.getIntArray("Colors") : null;
            if (_snowmanx != null && _snowmanx.length != 0) {
               if (_snowmanx.length == 1) {
                  return _snowmanx[0];
               } else {
                  int _snowmanxx = 0;
                  int _snowmanxxx = 0;
                  int _snowmanxxxx = 0;

                  for (int _snowmanxxxxx : _snowmanx) {
                     _snowmanxx += (_snowmanxxxxx & 0xFF0000) >> 16;
                     _snowmanxxx += (_snowmanxxxxx & 0xFF00) >> 8;
                     _snowmanxxxx += (_snowmanxxxxx & 0xFF) >> 0;
                  }

                  _snowmanxx /= _snowmanx.length;
                  _snowmanxxx /= _snowmanx.length;
                  _snowmanxxxx /= _snowmanx.length;
                  return _snowmanxx << 16 | _snowmanxxx << 8 | _snowmanxxxx;
               }
            } else {
               return 9079434;
            }
         }
      }, Items.FIREWORK_STAR);
      _snowman.register((stack, tintIndex) -> tintIndex > 0 ? -1 : PotionUtil.getColor(stack), Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION);

      for (SpawnEggItem _snowmanx : SpawnEggItem.getAll()) {
         _snowman.register((stack, tintIndex) -> _snowman.getColor(tintIndex), _snowmanx);
      }

      _snowman.register(
         (stack, tintIndex) -> {
            BlockState _snowmanx = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return blockColors.getColor(_snowmanx, null, null, tintIndex);
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
      _snowman.register((stack, tintIndex) -> tintIndex == 0 ? PotionUtil.getColor(stack) : -1, Items.TIPPED_ARROW);
      _snowman.register((stack, tintIndex) -> tintIndex == 0 ? -1 : FilledMapItem.getMapColor(stack), Items.FILLED_MAP);
      return _snowman;
   }

   public int getColorMultiplier(ItemStack item, int tintIndex) {
      ItemColorProvider _snowman = this.providers.get(Registry.ITEM.getRawId(item.getItem()));
      return _snowman == null ? -1 : _snowman.getColor(item, tintIndex);
   }

   public void register(ItemColorProvider mapper, ItemConvertible... items) {
      for (ItemConvertible _snowman : items) {
         this.providers.set(mapper, Item.getRawId(_snowman.asItem()));
      }
   }
}
