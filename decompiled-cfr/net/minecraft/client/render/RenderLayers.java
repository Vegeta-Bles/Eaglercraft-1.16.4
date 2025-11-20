/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.render;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;

public class RenderLayers {
    private static final Map<Block, RenderLayer> BLOCKS = Util.make(Maps.newHashMap(), hashMap -> {
        RenderLayer renderLayer = RenderLayer.getTripwire();
        hashMap.put(Blocks.TRIPWIRE, renderLayer);
        _snowman = RenderLayer.getCutoutMipped();
        hashMap.put(Blocks.GRASS_BLOCK, _snowman);
        hashMap.put(Blocks.IRON_BARS, _snowman);
        hashMap.put(Blocks.GLASS_PANE, _snowman);
        hashMap.put(Blocks.TRIPWIRE_HOOK, _snowman);
        hashMap.put(Blocks.HOPPER, _snowman);
        hashMap.put(Blocks.CHAIN, _snowman);
        hashMap.put(Blocks.JUNGLE_LEAVES, _snowman);
        hashMap.put(Blocks.OAK_LEAVES, _snowman);
        hashMap.put(Blocks.SPRUCE_LEAVES, _snowman);
        hashMap.put(Blocks.ACACIA_LEAVES, _snowman);
        hashMap.put(Blocks.BIRCH_LEAVES, _snowman);
        hashMap.put(Blocks.DARK_OAK_LEAVES, _snowman);
        _snowman = RenderLayer.getCutout();
        hashMap.put(Blocks.OAK_SAPLING, _snowman);
        hashMap.put(Blocks.SPRUCE_SAPLING, _snowman);
        hashMap.put(Blocks.BIRCH_SAPLING, _snowman);
        hashMap.put(Blocks.JUNGLE_SAPLING, _snowman);
        hashMap.put(Blocks.ACACIA_SAPLING, _snowman);
        hashMap.put(Blocks.DARK_OAK_SAPLING, _snowman);
        hashMap.put(Blocks.GLASS, _snowman);
        hashMap.put(Blocks.WHITE_BED, _snowman);
        hashMap.put(Blocks.ORANGE_BED, _snowman);
        hashMap.put(Blocks.MAGENTA_BED, _snowman);
        hashMap.put(Blocks.LIGHT_BLUE_BED, _snowman);
        hashMap.put(Blocks.YELLOW_BED, _snowman);
        hashMap.put(Blocks.LIME_BED, _snowman);
        hashMap.put(Blocks.PINK_BED, _snowman);
        hashMap.put(Blocks.GRAY_BED, _snowman);
        hashMap.put(Blocks.LIGHT_GRAY_BED, _snowman);
        hashMap.put(Blocks.CYAN_BED, _snowman);
        hashMap.put(Blocks.PURPLE_BED, _snowman);
        hashMap.put(Blocks.BLUE_BED, _snowman);
        hashMap.put(Blocks.BROWN_BED, _snowman);
        hashMap.put(Blocks.GREEN_BED, _snowman);
        hashMap.put(Blocks.RED_BED, _snowman);
        hashMap.put(Blocks.BLACK_BED, _snowman);
        hashMap.put(Blocks.POWERED_RAIL, _snowman);
        hashMap.put(Blocks.DETECTOR_RAIL, _snowman);
        hashMap.put(Blocks.COBWEB, _snowman);
        hashMap.put(Blocks.GRASS, _snowman);
        hashMap.put(Blocks.FERN, _snowman);
        hashMap.put(Blocks.DEAD_BUSH, _snowman);
        hashMap.put(Blocks.SEAGRASS, _snowman);
        hashMap.put(Blocks.TALL_SEAGRASS, _snowman);
        hashMap.put(Blocks.DANDELION, _snowman);
        hashMap.put(Blocks.POPPY, _snowman);
        hashMap.put(Blocks.BLUE_ORCHID, _snowman);
        hashMap.put(Blocks.ALLIUM, _snowman);
        hashMap.put(Blocks.AZURE_BLUET, _snowman);
        hashMap.put(Blocks.RED_TULIP, _snowman);
        hashMap.put(Blocks.ORANGE_TULIP, _snowman);
        hashMap.put(Blocks.WHITE_TULIP, _snowman);
        hashMap.put(Blocks.PINK_TULIP, _snowman);
        hashMap.put(Blocks.OXEYE_DAISY, _snowman);
        hashMap.put(Blocks.CORNFLOWER, _snowman);
        hashMap.put(Blocks.WITHER_ROSE, _snowman);
        hashMap.put(Blocks.LILY_OF_THE_VALLEY, _snowman);
        hashMap.put(Blocks.BROWN_MUSHROOM, _snowman);
        hashMap.put(Blocks.RED_MUSHROOM, _snowman);
        hashMap.put(Blocks.TORCH, _snowman);
        hashMap.put(Blocks.WALL_TORCH, _snowman);
        hashMap.put(Blocks.SOUL_TORCH, _snowman);
        hashMap.put(Blocks.SOUL_WALL_TORCH, _snowman);
        hashMap.put(Blocks.FIRE, _snowman);
        hashMap.put(Blocks.SOUL_FIRE, _snowman);
        hashMap.put(Blocks.SPAWNER, _snowman);
        hashMap.put(Blocks.REDSTONE_WIRE, _snowman);
        hashMap.put(Blocks.WHEAT, _snowman);
        hashMap.put(Blocks.OAK_DOOR, _snowman);
        hashMap.put(Blocks.LADDER, _snowman);
        hashMap.put(Blocks.RAIL, _snowman);
        hashMap.put(Blocks.IRON_DOOR, _snowman);
        hashMap.put(Blocks.REDSTONE_TORCH, _snowman);
        hashMap.put(Blocks.REDSTONE_WALL_TORCH, _snowman);
        hashMap.put(Blocks.CACTUS, _snowman);
        hashMap.put(Blocks.SUGAR_CANE, _snowman);
        hashMap.put(Blocks.REPEATER, _snowman);
        hashMap.put(Blocks.OAK_TRAPDOOR, _snowman);
        hashMap.put(Blocks.SPRUCE_TRAPDOOR, _snowman);
        hashMap.put(Blocks.BIRCH_TRAPDOOR, _snowman);
        hashMap.put(Blocks.JUNGLE_TRAPDOOR, _snowman);
        hashMap.put(Blocks.ACACIA_TRAPDOOR, _snowman);
        hashMap.put(Blocks.DARK_OAK_TRAPDOOR, _snowman);
        hashMap.put(Blocks.CRIMSON_TRAPDOOR, _snowman);
        hashMap.put(Blocks.WARPED_TRAPDOOR, _snowman);
        hashMap.put(Blocks.ATTACHED_PUMPKIN_STEM, _snowman);
        hashMap.put(Blocks.ATTACHED_MELON_STEM, _snowman);
        hashMap.put(Blocks.PUMPKIN_STEM, _snowman);
        hashMap.put(Blocks.MELON_STEM, _snowman);
        hashMap.put(Blocks.VINE, _snowman);
        hashMap.put(Blocks.LILY_PAD, _snowman);
        hashMap.put(Blocks.NETHER_WART, _snowman);
        hashMap.put(Blocks.BREWING_STAND, _snowman);
        hashMap.put(Blocks.COCOA, _snowman);
        hashMap.put(Blocks.BEACON, _snowman);
        hashMap.put(Blocks.FLOWER_POT, _snowman);
        hashMap.put(Blocks.POTTED_OAK_SAPLING, _snowman);
        hashMap.put(Blocks.POTTED_SPRUCE_SAPLING, _snowman);
        hashMap.put(Blocks.POTTED_BIRCH_SAPLING, _snowman);
        hashMap.put(Blocks.POTTED_JUNGLE_SAPLING, _snowman);
        hashMap.put(Blocks.POTTED_ACACIA_SAPLING, _snowman);
        hashMap.put(Blocks.POTTED_DARK_OAK_SAPLING, _snowman);
        hashMap.put(Blocks.POTTED_FERN, _snowman);
        hashMap.put(Blocks.POTTED_DANDELION, _snowman);
        hashMap.put(Blocks.POTTED_POPPY, _snowman);
        hashMap.put(Blocks.POTTED_BLUE_ORCHID, _snowman);
        hashMap.put(Blocks.POTTED_ALLIUM, _snowman);
        hashMap.put(Blocks.POTTED_AZURE_BLUET, _snowman);
        hashMap.put(Blocks.POTTED_RED_TULIP, _snowman);
        hashMap.put(Blocks.POTTED_ORANGE_TULIP, _snowman);
        hashMap.put(Blocks.POTTED_WHITE_TULIP, _snowman);
        hashMap.put(Blocks.POTTED_PINK_TULIP, _snowman);
        hashMap.put(Blocks.POTTED_OXEYE_DAISY, _snowman);
        hashMap.put(Blocks.POTTED_CORNFLOWER, _snowman);
        hashMap.put(Blocks.POTTED_LILY_OF_THE_VALLEY, _snowman);
        hashMap.put(Blocks.POTTED_WITHER_ROSE, _snowman);
        hashMap.put(Blocks.POTTED_RED_MUSHROOM, _snowman);
        hashMap.put(Blocks.POTTED_BROWN_MUSHROOM, _snowman);
        hashMap.put(Blocks.POTTED_DEAD_BUSH, _snowman);
        hashMap.put(Blocks.POTTED_CACTUS, _snowman);
        hashMap.put(Blocks.CARROTS, _snowman);
        hashMap.put(Blocks.POTATOES, _snowman);
        hashMap.put(Blocks.COMPARATOR, _snowman);
        hashMap.put(Blocks.ACTIVATOR_RAIL, _snowman);
        hashMap.put(Blocks.IRON_TRAPDOOR, _snowman);
        hashMap.put(Blocks.SUNFLOWER, _snowman);
        hashMap.put(Blocks.LILAC, _snowman);
        hashMap.put(Blocks.ROSE_BUSH, _snowman);
        hashMap.put(Blocks.PEONY, _snowman);
        hashMap.put(Blocks.TALL_GRASS, _snowman);
        hashMap.put(Blocks.LARGE_FERN, _snowman);
        hashMap.put(Blocks.SPRUCE_DOOR, _snowman);
        hashMap.put(Blocks.BIRCH_DOOR, _snowman);
        hashMap.put(Blocks.JUNGLE_DOOR, _snowman);
        hashMap.put(Blocks.ACACIA_DOOR, _snowman);
        hashMap.put(Blocks.DARK_OAK_DOOR, _snowman);
        hashMap.put(Blocks.END_ROD, _snowman);
        hashMap.put(Blocks.CHORUS_PLANT, _snowman);
        hashMap.put(Blocks.CHORUS_FLOWER, _snowman);
        hashMap.put(Blocks.BEETROOTS, _snowman);
        hashMap.put(Blocks.KELP, _snowman);
        hashMap.put(Blocks.KELP_PLANT, _snowman);
        hashMap.put(Blocks.TURTLE_EGG, _snowman);
        hashMap.put(Blocks.DEAD_TUBE_CORAL, _snowman);
        hashMap.put(Blocks.DEAD_BRAIN_CORAL, _snowman);
        hashMap.put(Blocks.DEAD_BUBBLE_CORAL, _snowman);
        hashMap.put(Blocks.DEAD_FIRE_CORAL, _snowman);
        hashMap.put(Blocks.DEAD_HORN_CORAL, _snowman);
        hashMap.put(Blocks.TUBE_CORAL, _snowman);
        hashMap.put(Blocks.BRAIN_CORAL, _snowman);
        hashMap.put(Blocks.BUBBLE_CORAL, _snowman);
        hashMap.put(Blocks.FIRE_CORAL, _snowman);
        hashMap.put(Blocks.HORN_CORAL, _snowman);
        hashMap.put(Blocks.DEAD_TUBE_CORAL_FAN, _snowman);
        hashMap.put(Blocks.DEAD_BRAIN_CORAL_FAN, _snowman);
        hashMap.put(Blocks.DEAD_BUBBLE_CORAL_FAN, _snowman);
        hashMap.put(Blocks.DEAD_FIRE_CORAL_FAN, _snowman);
        hashMap.put(Blocks.DEAD_HORN_CORAL_FAN, _snowman);
        hashMap.put(Blocks.TUBE_CORAL_FAN, _snowman);
        hashMap.put(Blocks.BRAIN_CORAL_FAN, _snowman);
        hashMap.put(Blocks.BUBBLE_CORAL_FAN, _snowman);
        hashMap.put(Blocks.FIRE_CORAL_FAN, _snowman);
        hashMap.put(Blocks.HORN_CORAL_FAN, _snowman);
        hashMap.put(Blocks.DEAD_TUBE_CORAL_WALL_FAN, _snowman);
        hashMap.put(Blocks.DEAD_BRAIN_CORAL_WALL_FAN, _snowman);
        hashMap.put(Blocks.DEAD_BUBBLE_CORAL_WALL_FAN, _snowman);
        hashMap.put(Blocks.DEAD_FIRE_CORAL_WALL_FAN, _snowman);
        hashMap.put(Blocks.DEAD_HORN_CORAL_WALL_FAN, _snowman);
        hashMap.put(Blocks.TUBE_CORAL_WALL_FAN, _snowman);
        hashMap.put(Blocks.BRAIN_CORAL_WALL_FAN, _snowman);
        hashMap.put(Blocks.BUBBLE_CORAL_WALL_FAN, _snowman);
        hashMap.put(Blocks.FIRE_CORAL_WALL_FAN, _snowman);
        hashMap.put(Blocks.HORN_CORAL_WALL_FAN, _snowman);
        hashMap.put(Blocks.SEA_PICKLE, _snowman);
        hashMap.put(Blocks.CONDUIT, _snowman);
        hashMap.put(Blocks.BAMBOO_SAPLING, _snowman);
        hashMap.put(Blocks.BAMBOO, _snowman);
        hashMap.put(Blocks.POTTED_BAMBOO, _snowman);
        hashMap.put(Blocks.SCAFFOLDING, _snowman);
        hashMap.put(Blocks.STONECUTTER, _snowman);
        hashMap.put(Blocks.LANTERN, _snowman);
        hashMap.put(Blocks.SOUL_LANTERN, _snowman);
        hashMap.put(Blocks.CAMPFIRE, _snowman);
        hashMap.put(Blocks.SOUL_CAMPFIRE, _snowman);
        hashMap.put(Blocks.SWEET_BERRY_BUSH, _snowman);
        hashMap.put(Blocks.WEEPING_VINES, _snowman);
        hashMap.put(Blocks.WEEPING_VINES_PLANT, _snowman);
        hashMap.put(Blocks.TWISTING_VINES, _snowman);
        hashMap.put(Blocks.TWISTING_VINES_PLANT, _snowman);
        hashMap.put(Blocks.NETHER_SPROUTS, _snowman);
        hashMap.put(Blocks.CRIMSON_FUNGUS, _snowman);
        hashMap.put(Blocks.WARPED_FUNGUS, _snowman);
        hashMap.put(Blocks.CRIMSON_ROOTS, _snowman);
        hashMap.put(Blocks.WARPED_ROOTS, _snowman);
        hashMap.put(Blocks.POTTED_CRIMSON_FUNGUS, _snowman);
        hashMap.put(Blocks.POTTED_WARPED_FUNGUS, _snowman);
        hashMap.put(Blocks.POTTED_CRIMSON_ROOTS, _snowman);
        hashMap.put(Blocks.POTTED_WARPED_ROOTS, _snowman);
        hashMap.put(Blocks.CRIMSON_DOOR, _snowman);
        hashMap.put(Blocks.WARPED_DOOR, _snowman);
        _snowman = RenderLayer.getTranslucent();
        hashMap.put(Blocks.ICE, _snowman);
        hashMap.put(Blocks.NETHER_PORTAL, _snowman);
        hashMap.put(Blocks.WHITE_STAINED_GLASS, _snowman);
        hashMap.put(Blocks.ORANGE_STAINED_GLASS, _snowman);
        hashMap.put(Blocks.MAGENTA_STAINED_GLASS, _snowman);
        hashMap.put(Blocks.LIGHT_BLUE_STAINED_GLASS, _snowman);
        hashMap.put(Blocks.YELLOW_STAINED_GLASS, _snowman);
        hashMap.put(Blocks.LIME_STAINED_GLASS, _snowman);
        hashMap.put(Blocks.PINK_STAINED_GLASS, _snowman);
        hashMap.put(Blocks.GRAY_STAINED_GLASS, _snowman);
        hashMap.put(Blocks.LIGHT_GRAY_STAINED_GLASS, _snowman);
        hashMap.put(Blocks.CYAN_STAINED_GLASS, _snowman);
        hashMap.put(Blocks.PURPLE_STAINED_GLASS, _snowman);
        hashMap.put(Blocks.BLUE_STAINED_GLASS, _snowman);
        hashMap.put(Blocks.BROWN_STAINED_GLASS, _snowman);
        hashMap.put(Blocks.GREEN_STAINED_GLASS, _snowman);
        hashMap.put(Blocks.RED_STAINED_GLASS, _snowman);
        hashMap.put(Blocks.BLACK_STAINED_GLASS, _snowman);
        hashMap.put(Blocks.WHITE_STAINED_GLASS_PANE, _snowman);
        hashMap.put(Blocks.ORANGE_STAINED_GLASS_PANE, _snowman);
        hashMap.put(Blocks.MAGENTA_STAINED_GLASS_PANE, _snowman);
        hashMap.put(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, _snowman);
        hashMap.put(Blocks.YELLOW_STAINED_GLASS_PANE, _snowman);
        hashMap.put(Blocks.LIME_STAINED_GLASS_PANE, _snowman);
        hashMap.put(Blocks.PINK_STAINED_GLASS_PANE, _snowman);
        hashMap.put(Blocks.GRAY_STAINED_GLASS_PANE, _snowman);
        hashMap.put(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, _snowman);
        hashMap.put(Blocks.CYAN_STAINED_GLASS_PANE, _snowman);
        hashMap.put(Blocks.PURPLE_STAINED_GLASS_PANE, _snowman);
        hashMap.put(Blocks.BLUE_STAINED_GLASS_PANE, _snowman);
        hashMap.put(Blocks.BROWN_STAINED_GLASS_PANE, _snowman);
        hashMap.put(Blocks.GREEN_STAINED_GLASS_PANE, _snowman);
        hashMap.put(Blocks.RED_STAINED_GLASS_PANE, _snowman);
        hashMap.put(Blocks.BLACK_STAINED_GLASS_PANE, _snowman);
        hashMap.put(Blocks.SLIME_BLOCK, _snowman);
        hashMap.put(Blocks.HONEY_BLOCK, _snowman);
        hashMap.put(Blocks.FROSTED_ICE, _snowman);
        hashMap.put(Blocks.BUBBLE_COLUMN, _snowman);
    });
    private static final Map<Fluid, RenderLayer> FLUIDS = Util.make(Maps.newHashMap(), hashMap -> {
        RenderLayer renderLayer = RenderLayer.getTranslucent();
        hashMap.put(Fluids.FLOWING_WATER, renderLayer);
        hashMap.put(Fluids.WATER, renderLayer);
    });
    private static boolean fancyGraphicsOrBetter;

    public static RenderLayer getBlockLayer(BlockState state) {
        Block block = state.getBlock();
        if (block instanceof LeavesBlock) {
            return fancyGraphicsOrBetter ? RenderLayer.getCutoutMipped() : RenderLayer.getSolid();
        }
        RenderLayer _snowman2 = BLOCKS.get(block);
        if (_snowman2 != null) {
            return _snowman2;
        }
        return RenderLayer.getSolid();
    }

    public static RenderLayer getMovingBlockLayer(BlockState blockState) {
        Block block = blockState.getBlock();
        if (block instanceof LeavesBlock) {
            return fancyGraphicsOrBetter ? RenderLayer.getCutoutMipped() : RenderLayer.getSolid();
        }
        RenderLayer _snowman2 = BLOCKS.get(block);
        if (_snowman2 != null) {
            if (_snowman2 == RenderLayer.getTranslucent()) {
                return RenderLayer.getTranslucentMovingBlock();
            }
            return _snowman2;
        }
        return RenderLayer.getSolid();
    }

    public static RenderLayer getEntityBlockLayer(BlockState state, boolean direct) {
        RenderLayer renderLayer = RenderLayers.getBlockLayer(state);
        if (renderLayer == RenderLayer.getTranslucent()) {
            if (!MinecraftClient.isFabulousGraphicsOrBetter()) {
                return TexturedRenderLayers.getEntityTranslucentCull();
            }
            return direct ? TexturedRenderLayers.getEntityTranslucentCull() : TexturedRenderLayers.getItemEntityTranslucentCull();
        }
        return TexturedRenderLayers.getEntityCutout();
    }

    public static RenderLayer getItemLayer(ItemStack stack, boolean direct) {
        Item item = stack.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem)item).getBlock();
            return RenderLayers.getEntityBlockLayer(block.getDefaultState(), direct);
        }
        return direct ? TexturedRenderLayers.getEntityTranslucentCull() : TexturedRenderLayers.getItemEntityTranslucentCull();
    }

    public static RenderLayer getFluidLayer(FluidState state) {
        RenderLayer renderLayer = FLUIDS.get(state.getFluid());
        if (renderLayer != null) {
            return renderLayer;
        }
        return RenderLayer.getSolid();
    }

    public static void setFancyGraphicsOrBetter(boolean fancyGraphicsOrBetter) {
        RenderLayers.fancyGraphicsOrBetter = fancyGraphicsOrBetter;
    }
}

