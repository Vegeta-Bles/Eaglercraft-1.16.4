/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.color.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColorProvider;
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
    private final IdList<ItemColorProvider> providers = new IdList(32);

    public static ItemColors create(BlockColors blockColors) {
        ItemColors itemColors = new ItemColors();
        itemColors.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem)((Object)stack.getItem())).getColor(stack), Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS, Items.LEATHER_HORSE_ARMOR);
        itemColors.register((stack, tintIndex) -> GrassColors.getColor(0.5, 1.0), Blocks.TALL_GRASS, Blocks.LARGE_FERN);
        itemColors.register((stack, tintIndex) -> {
            int[] nArray;
            if (tintIndex != 1) {
                return -1;
            }
            CompoundTag compoundTag = stack.getSubTag("Explosion");
            int[] nArray2 = nArray = compoundTag != null && compoundTag.contains("Colors", 11) ? compoundTag.getIntArray("Colors") : null;
            if (nArray == null || nArray.length == 0) {
                return 0x8A8A8A;
            }
            if (nArray.length == 1) {
                return nArray[0];
            }
            int _snowman2 = 0;
            int _snowman3 = 0;
            int _snowman4 = 0;
            for (int n : nArray) {
                _snowman2 += (n & 0xFF0000) >> 16;
                _snowman3 += (n & 0xFF00) >> 8;
                _snowman4 += (n & 0xFF) >> 0;
            }
            return (_snowman2 /= nArray.length) << 16 | (_snowman3 /= nArray.length) << 8 | (_snowman4 /= nArray.length);
        }, Items.FIREWORK_STAR);
        itemColors.register((stack, tintIndex) -> tintIndex > 0 ? -1 : PotionUtil.getColor(stack), Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION);
        for (SpawnEggItem spawnEggItem : SpawnEggItem.getAll()) {
            itemColors.register((stack, tintIndex) -> spawnEggItem.getColor(tintIndex), spawnEggItem);
        }
        itemColors.register((stack, tintIndex) -> {
            BlockState blockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return blockColors.getColor(blockState, null, null, tintIndex);
        }, Blocks.GRASS_BLOCK, Blocks.GRASS, Blocks.FERN, Blocks.VINE, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.LILY_PAD);
        itemColors.register((stack, tintIndex) -> tintIndex == 0 ? PotionUtil.getColor(stack) : -1, Items.TIPPED_ARROW);
        itemColors.register((stack, tintIndex) -> tintIndex == 0 ? -1 : FilledMapItem.getMapColor(stack), Items.FILLED_MAP);
        return itemColors;
    }

    public int getColorMultiplier(ItemStack item, int tintIndex) {
        ItemColorProvider itemColorProvider = this.providers.get(Registry.ITEM.getRawId(item.getItem()));
        return itemColorProvider == null ? -1 : itemColorProvider.getColor(item, tintIndex);
    }

    public void register(ItemColorProvider mapper, ItemConvertible ... items) {
        for (ItemConvertible itemConvertible : items) {
            this.providers.set(mapper, Item.getRawId(itemConvertible.asItem()));
        }
    }
}

