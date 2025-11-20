package net.minecraft.client.render;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;

public class RenderLayers {
   private static final Map<Block, RenderLayer> BLOCKS = Util.make(Maps.newHashMap(), _snowman -> {
      RenderLayer _snowmanx = RenderLayer.getTripwire();
      _snowman.put(Blocks.TRIPWIRE, _snowmanx);
      RenderLayer _snowmanxx = RenderLayer.getCutoutMipped();
      _snowman.put(Blocks.GRASS_BLOCK, _snowmanxx);
      _snowman.put(Blocks.IRON_BARS, _snowmanxx);
      _snowman.put(Blocks.GLASS_PANE, _snowmanxx);
      _snowman.put(Blocks.TRIPWIRE_HOOK, _snowmanxx);
      _snowman.put(Blocks.HOPPER, _snowmanxx);
      _snowman.put(Blocks.CHAIN, _snowmanxx);
      _snowman.put(Blocks.JUNGLE_LEAVES, _snowmanxx);
      _snowman.put(Blocks.OAK_LEAVES, _snowmanxx);
      _snowman.put(Blocks.SPRUCE_LEAVES, _snowmanxx);
      _snowman.put(Blocks.ACACIA_LEAVES, _snowmanxx);
      _snowman.put(Blocks.BIRCH_LEAVES, _snowmanxx);
      _snowman.put(Blocks.DARK_OAK_LEAVES, _snowmanxx);
      RenderLayer _snowmanxxx = RenderLayer.getCutout();
      _snowman.put(Blocks.OAK_SAPLING, _snowmanxxx);
      _snowman.put(Blocks.SPRUCE_SAPLING, _snowmanxxx);
      _snowman.put(Blocks.BIRCH_SAPLING, _snowmanxxx);
      _snowman.put(Blocks.JUNGLE_SAPLING, _snowmanxxx);
      _snowman.put(Blocks.ACACIA_SAPLING, _snowmanxxx);
      _snowman.put(Blocks.DARK_OAK_SAPLING, _snowmanxxx);
      _snowman.put(Blocks.GLASS, _snowmanxxx);
      _snowman.put(Blocks.WHITE_BED, _snowmanxxx);
      _snowman.put(Blocks.ORANGE_BED, _snowmanxxx);
      _snowman.put(Blocks.MAGENTA_BED, _snowmanxxx);
      _snowman.put(Blocks.LIGHT_BLUE_BED, _snowmanxxx);
      _snowman.put(Blocks.YELLOW_BED, _snowmanxxx);
      _snowman.put(Blocks.LIME_BED, _snowmanxxx);
      _snowman.put(Blocks.PINK_BED, _snowmanxxx);
      _snowman.put(Blocks.GRAY_BED, _snowmanxxx);
      _snowman.put(Blocks.LIGHT_GRAY_BED, _snowmanxxx);
      _snowman.put(Blocks.CYAN_BED, _snowmanxxx);
      _snowman.put(Blocks.PURPLE_BED, _snowmanxxx);
      _snowman.put(Blocks.BLUE_BED, _snowmanxxx);
      _snowman.put(Blocks.BROWN_BED, _snowmanxxx);
      _snowman.put(Blocks.GREEN_BED, _snowmanxxx);
      _snowman.put(Blocks.RED_BED, _snowmanxxx);
      _snowman.put(Blocks.BLACK_BED, _snowmanxxx);
      _snowman.put(Blocks.POWERED_RAIL, _snowmanxxx);
      _snowman.put(Blocks.DETECTOR_RAIL, _snowmanxxx);
      _snowman.put(Blocks.COBWEB, _snowmanxxx);
      _snowman.put(Blocks.GRASS, _snowmanxxx);
      _snowman.put(Blocks.FERN, _snowmanxxx);
      _snowman.put(Blocks.DEAD_BUSH, _snowmanxxx);
      _snowman.put(Blocks.SEAGRASS, _snowmanxxx);
      _snowman.put(Blocks.TALL_SEAGRASS, _snowmanxxx);
      _snowman.put(Blocks.DANDELION, _snowmanxxx);
      _snowman.put(Blocks.POPPY, _snowmanxxx);
      _snowman.put(Blocks.BLUE_ORCHID, _snowmanxxx);
      _snowman.put(Blocks.ALLIUM, _snowmanxxx);
      _snowman.put(Blocks.AZURE_BLUET, _snowmanxxx);
      _snowman.put(Blocks.RED_TULIP, _snowmanxxx);
      _snowman.put(Blocks.ORANGE_TULIP, _snowmanxxx);
      _snowman.put(Blocks.WHITE_TULIP, _snowmanxxx);
      _snowman.put(Blocks.PINK_TULIP, _snowmanxxx);
      _snowman.put(Blocks.OXEYE_DAISY, _snowmanxxx);
      _snowman.put(Blocks.CORNFLOWER, _snowmanxxx);
      _snowman.put(Blocks.WITHER_ROSE, _snowmanxxx);
      _snowman.put(Blocks.LILY_OF_THE_VALLEY, _snowmanxxx);
      _snowman.put(Blocks.BROWN_MUSHROOM, _snowmanxxx);
      _snowman.put(Blocks.RED_MUSHROOM, _snowmanxxx);
      _snowman.put(Blocks.TORCH, _snowmanxxx);
      _snowman.put(Blocks.WALL_TORCH, _snowmanxxx);
      _snowman.put(Blocks.SOUL_TORCH, _snowmanxxx);
      _snowman.put(Blocks.SOUL_WALL_TORCH, _snowmanxxx);
      _snowman.put(Blocks.FIRE, _snowmanxxx);
      _snowman.put(Blocks.SOUL_FIRE, _snowmanxxx);
      _snowman.put(Blocks.SPAWNER, _snowmanxxx);
      _snowman.put(Blocks.REDSTONE_WIRE, _snowmanxxx);
      _snowman.put(Blocks.WHEAT, _snowmanxxx);
      _snowman.put(Blocks.OAK_DOOR, _snowmanxxx);
      _snowman.put(Blocks.LADDER, _snowmanxxx);
      _snowman.put(Blocks.RAIL, _snowmanxxx);
      _snowman.put(Blocks.IRON_DOOR, _snowmanxxx);
      _snowman.put(Blocks.REDSTONE_TORCH, _snowmanxxx);
      _snowman.put(Blocks.REDSTONE_WALL_TORCH, _snowmanxxx);
      _snowman.put(Blocks.CACTUS, _snowmanxxx);
      _snowman.put(Blocks.SUGAR_CANE, _snowmanxxx);
      _snowman.put(Blocks.REPEATER, _snowmanxxx);
      _snowman.put(Blocks.OAK_TRAPDOOR, _snowmanxxx);
      _snowman.put(Blocks.SPRUCE_TRAPDOOR, _snowmanxxx);
      _snowman.put(Blocks.BIRCH_TRAPDOOR, _snowmanxxx);
      _snowman.put(Blocks.JUNGLE_TRAPDOOR, _snowmanxxx);
      _snowman.put(Blocks.ACACIA_TRAPDOOR, _snowmanxxx);
      _snowman.put(Blocks.DARK_OAK_TRAPDOOR, _snowmanxxx);
      _snowman.put(Blocks.CRIMSON_TRAPDOOR, _snowmanxxx);
      _snowman.put(Blocks.WARPED_TRAPDOOR, _snowmanxxx);
      _snowman.put(Blocks.ATTACHED_PUMPKIN_STEM, _snowmanxxx);
      _snowman.put(Blocks.ATTACHED_MELON_STEM, _snowmanxxx);
      _snowman.put(Blocks.PUMPKIN_STEM, _snowmanxxx);
      _snowman.put(Blocks.MELON_STEM, _snowmanxxx);
      _snowman.put(Blocks.VINE, _snowmanxxx);
      _snowman.put(Blocks.LILY_PAD, _snowmanxxx);
      _snowman.put(Blocks.NETHER_WART, _snowmanxxx);
      _snowman.put(Blocks.BREWING_STAND, _snowmanxxx);
      _snowman.put(Blocks.COCOA, _snowmanxxx);
      _snowman.put(Blocks.BEACON, _snowmanxxx);
      _snowman.put(Blocks.FLOWER_POT, _snowmanxxx);
      _snowman.put(Blocks.POTTED_OAK_SAPLING, _snowmanxxx);
      _snowman.put(Blocks.POTTED_SPRUCE_SAPLING, _snowmanxxx);
      _snowman.put(Blocks.POTTED_BIRCH_SAPLING, _snowmanxxx);
      _snowman.put(Blocks.POTTED_JUNGLE_SAPLING, _snowmanxxx);
      _snowman.put(Blocks.POTTED_ACACIA_SAPLING, _snowmanxxx);
      _snowman.put(Blocks.POTTED_DARK_OAK_SAPLING, _snowmanxxx);
      _snowman.put(Blocks.POTTED_FERN, _snowmanxxx);
      _snowman.put(Blocks.POTTED_DANDELION, _snowmanxxx);
      _snowman.put(Blocks.POTTED_POPPY, _snowmanxxx);
      _snowman.put(Blocks.POTTED_BLUE_ORCHID, _snowmanxxx);
      _snowman.put(Blocks.POTTED_ALLIUM, _snowmanxxx);
      _snowman.put(Blocks.POTTED_AZURE_BLUET, _snowmanxxx);
      _snowman.put(Blocks.POTTED_RED_TULIP, _snowmanxxx);
      _snowman.put(Blocks.POTTED_ORANGE_TULIP, _snowmanxxx);
      _snowman.put(Blocks.POTTED_WHITE_TULIP, _snowmanxxx);
      _snowman.put(Blocks.POTTED_PINK_TULIP, _snowmanxxx);
      _snowman.put(Blocks.POTTED_OXEYE_DAISY, _snowmanxxx);
      _snowman.put(Blocks.POTTED_CORNFLOWER, _snowmanxxx);
      _snowman.put(Blocks.POTTED_LILY_OF_THE_VALLEY, _snowmanxxx);
      _snowman.put(Blocks.POTTED_WITHER_ROSE, _snowmanxxx);
      _snowman.put(Blocks.POTTED_RED_MUSHROOM, _snowmanxxx);
      _snowman.put(Blocks.POTTED_BROWN_MUSHROOM, _snowmanxxx);
      _snowman.put(Blocks.POTTED_DEAD_BUSH, _snowmanxxx);
      _snowman.put(Blocks.POTTED_CACTUS, _snowmanxxx);
      _snowman.put(Blocks.CARROTS, _snowmanxxx);
      _snowman.put(Blocks.POTATOES, _snowmanxxx);
      _snowman.put(Blocks.COMPARATOR, _snowmanxxx);
      _snowman.put(Blocks.ACTIVATOR_RAIL, _snowmanxxx);
      _snowman.put(Blocks.IRON_TRAPDOOR, _snowmanxxx);
      _snowman.put(Blocks.SUNFLOWER, _snowmanxxx);
      _snowman.put(Blocks.LILAC, _snowmanxxx);
      _snowman.put(Blocks.ROSE_BUSH, _snowmanxxx);
      _snowman.put(Blocks.PEONY, _snowmanxxx);
      _snowman.put(Blocks.TALL_GRASS, _snowmanxxx);
      _snowman.put(Blocks.LARGE_FERN, _snowmanxxx);
      _snowman.put(Blocks.SPRUCE_DOOR, _snowmanxxx);
      _snowman.put(Blocks.BIRCH_DOOR, _snowmanxxx);
      _snowman.put(Blocks.JUNGLE_DOOR, _snowmanxxx);
      _snowman.put(Blocks.ACACIA_DOOR, _snowmanxxx);
      _snowman.put(Blocks.DARK_OAK_DOOR, _snowmanxxx);
      _snowman.put(Blocks.END_ROD, _snowmanxxx);
      _snowman.put(Blocks.CHORUS_PLANT, _snowmanxxx);
      _snowman.put(Blocks.CHORUS_FLOWER, _snowmanxxx);
      _snowman.put(Blocks.BEETROOTS, _snowmanxxx);
      _snowman.put(Blocks.KELP, _snowmanxxx);
      _snowman.put(Blocks.KELP_PLANT, _snowmanxxx);
      _snowman.put(Blocks.TURTLE_EGG, _snowmanxxx);
      _snowman.put(Blocks.DEAD_TUBE_CORAL, _snowmanxxx);
      _snowman.put(Blocks.DEAD_BRAIN_CORAL, _snowmanxxx);
      _snowman.put(Blocks.DEAD_BUBBLE_CORAL, _snowmanxxx);
      _snowman.put(Blocks.DEAD_FIRE_CORAL, _snowmanxxx);
      _snowman.put(Blocks.DEAD_HORN_CORAL, _snowmanxxx);
      _snowman.put(Blocks.TUBE_CORAL, _snowmanxxx);
      _snowman.put(Blocks.BRAIN_CORAL, _snowmanxxx);
      _snowman.put(Blocks.BUBBLE_CORAL, _snowmanxxx);
      _snowman.put(Blocks.FIRE_CORAL, _snowmanxxx);
      _snowman.put(Blocks.HORN_CORAL, _snowmanxxx);
      _snowman.put(Blocks.DEAD_TUBE_CORAL_FAN, _snowmanxxx);
      _snowman.put(Blocks.DEAD_BRAIN_CORAL_FAN, _snowmanxxx);
      _snowman.put(Blocks.DEAD_BUBBLE_CORAL_FAN, _snowmanxxx);
      _snowman.put(Blocks.DEAD_FIRE_CORAL_FAN, _snowmanxxx);
      _snowman.put(Blocks.DEAD_HORN_CORAL_FAN, _snowmanxxx);
      _snowman.put(Blocks.TUBE_CORAL_FAN, _snowmanxxx);
      _snowman.put(Blocks.BRAIN_CORAL_FAN, _snowmanxxx);
      _snowman.put(Blocks.BUBBLE_CORAL_FAN, _snowmanxxx);
      _snowman.put(Blocks.FIRE_CORAL_FAN, _snowmanxxx);
      _snowman.put(Blocks.HORN_CORAL_FAN, _snowmanxxx);
      _snowman.put(Blocks.DEAD_TUBE_CORAL_WALL_FAN, _snowmanxxx);
      _snowman.put(Blocks.DEAD_BRAIN_CORAL_WALL_FAN, _snowmanxxx);
      _snowman.put(Blocks.DEAD_BUBBLE_CORAL_WALL_FAN, _snowmanxxx);
      _snowman.put(Blocks.DEAD_FIRE_CORAL_WALL_FAN, _snowmanxxx);
      _snowman.put(Blocks.DEAD_HORN_CORAL_WALL_FAN, _snowmanxxx);
      _snowman.put(Blocks.TUBE_CORAL_WALL_FAN, _snowmanxxx);
      _snowman.put(Blocks.BRAIN_CORAL_WALL_FAN, _snowmanxxx);
      _snowman.put(Blocks.BUBBLE_CORAL_WALL_FAN, _snowmanxxx);
      _snowman.put(Blocks.FIRE_CORAL_WALL_FAN, _snowmanxxx);
      _snowman.put(Blocks.HORN_CORAL_WALL_FAN, _snowmanxxx);
      _snowman.put(Blocks.SEA_PICKLE, _snowmanxxx);
      _snowman.put(Blocks.CONDUIT, _snowmanxxx);
      _snowman.put(Blocks.BAMBOO_SAPLING, _snowmanxxx);
      _snowman.put(Blocks.BAMBOO, _snowmanxxx);
      _snowman.put(Blocks.POTTED_BAMBOO, _snowmanxxx);
      _snowman.put(Blocks.SCAFFOLDING, _snowmanxxx);
      _snowman.put(Blocks.STONECUTTER, _snowmanxxx);
      _snowman.put(Blocks.LANTERN, _snowmanxxx);
      _snowman.put(Blocks.SOUL_LANTERN, _snowmanxxx);
      _snowman.put(Blocks.CAMPFIRE, _snowmanxxx);
      _snowman.put(Blocks.SOUL_CAMPFIRE, _snowmanxxx);
      _snowman.put(Blocks.SWEET_BERRY_BUSH, _snowmanxxx);
      _snowman.put(Blocks.WEEPING_VINES, _snowmanxxx);
      _snowman.put(Blocks.WEEPING_VINES_PLANT, _snowmanxxx);
      _snowman.put(Blocks.TWISTING_VINES, _snowmanxxx);
      _snowman.put(Blocks.TWISTING_VINES_PLANT, _snowmanxxx);
      _snowman.put(Blocks.NETHER_SPROUTS, _snowmanxxx);
      _snowman.put(Blocks.CRIMSON_FUNGUS, _snowmanxxx);
      _snowman.put(Blocks.WARPED_FUNGUS, _snowmanxxx);
      _snowman.put(Blocks.CRIMSON_ROOTS, _snowmanxxx);
      _snowman.put(Blocks.WARPED_ROOTS, _snowmanxxx);
      _snowman.put(Blocks.POTTED_CRIMSON_FUNGUS, _snowmanxxx);
      _snowman.put(Blocks.POTTED_WARPED_FUNGUS, _snowmanxxx);
      _snowman.put(Blocks.POTTED_CRIMSON_ROOTS, _snowmanxxx);
      _snowman.put(Blocks.POTTED_WARPED_ROOTS, _snowmanxxx);
      _snowman.put(Blocks.CRIMSON_DOOR, _snowmanxxx);
      _snowman.put(Blocks.WARPED_DOOR, _snowmanxxx);
      RenderLayer _snowmanxxxx = RenderLayer.getTranslucent();
      _snowman.put(Blocks.ICE, _snowmanxxxx);
      _snowman.put(Blocks.NETHER_PORTAL, _snowmanxxxx);
      _snowman.put(Blocks.WHITE_STAINED_GLASS, _snowmanxxxx);
      _snowman.put(Blocks.ORANGE_STAINED_GLASS, _snowmanxxxx);
      _snowman.put(Blocks.MAGENTA_STAINED_GLASS, _snowmanxxxx);
      _snowman.put(Blocks.LIGHT_BLUE_STAINED_GLASS, _snowmanxxxx);
      _snowman.put(Blocks.YELLOW_STAINED_GLASS, _snowmanxxxx);
      _snowman.put(Blocks.LIME_STAINED_GLASS, _snowmanxxxx);
      _snowman.put(Blocks.PINK_STAINED_GLASS, _snowmanxxxx);
      _snowman.put(Blocks.GRAY_STAINED_GLASS, _snowmanxxxx);
      _snowman.put(Blocks.LIGHT_GRAY_STAINED_GLASS, _snowmanxxxx);
      _snowman.put(Blocks.CYAN_STAINED_GLASS, _snowmanxxxx);
      _snowman.put(Blocks.PURPLE_STAINED_GLASS, _snowmanxxxx);
      _snowman.put(Blocks.BLUE_STAINED_GLASS, _snowmanxxxx);
      _snowman.put(Blocks.BROWN_STAINED_GLASS, _snowmanxxxx);
      _snowman.put(Blocks.GREEN_STAINED_GLASS, _snowmanxxxx);
      _snowman.put(Blocks.RED_STAINED_GLASS, _snowmanxxxx);
      _snowman.put(Blocks.BLACK_STAINED_GLASS, _snowmanxxxx);
      _snowman.put(Blocks.WHITE_STAINED_GLASS_PANE, _snowmanxxxx);
      _snowman.put(Blocks.ORANGE_STAINED_GLASS_PANE, _snowmanxxxx);
      _snowman.put(Blocks.MAGENTA_STAINED_GLASS_PANE, _snowmanxxxx);
      _snowman.put(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, _snowmanxxxx);
      _snowman.put(Blocks.YELLOW_STAINED_GLASS_PANE, _snowmanxxxx);
      _snowman.put(Blocks.LIME_STAINED_GLASS_PANE, _snowmanxxxx);
      _snowman.put(Blocks.PINK_STAINED_GLASS_PANE, _snowmanxxxx);
      _snowman.put(Blocks.GRAY_STAINED_GLASS_PANE, _snowmanxxxx);
      _snowman.put(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, _snowmanxxxx);
      _snowman.put(Blocks.CYAN_STAINED_GLASS_PANE, _snowmanxxxx);
      _snowman.put(Blocks.PURPLE_STAINED_GLASS_PANE, _snowmanxxxx);
      _snowman.put(Blocks.BLUE_STAINED_GLASS_PANE, _snowmanxxxx);
      _snowman.put(Blocks.BROWN_STAINED_GLASS_PANE, _snowmanxxxx);
      _snowman.put(Blocks.GREEN_STAINED_GLASS_PANE, _snowmanxxxx);
      _snowman.put(Blocks.RED_STAINED_GLASS_PANE, _snowmanxxxx);
      _snowman.put(Blocks.BLACK_STAINED_GLASS_PANE, _snowmanxxxx);
      _snowman.put(Blocks.SLIME_BLOCK, _snowmanxxxx);
      _snowman.put(Blocks.HONEY_BLOCK, _snowmanxxxx);
      _snowman.put(Blocks.FROSTED_ICE, _snowmanxxxx);
      _snowman.put(Blocks.BUBBLE_COLUMN, _snowmanxxxx);
   });
   private static final Map<Fluid, RenderLayer> FLUIDS = Util.make(Maps.newHashMap(), _snowman -> {
      RenderLayer _snowmanx = RenderLayer.getTranslucent();
      _snowman.put(Fluids.FLOWING_WATER, _snowmanx);
      _snowman.put(Fluids.WATER, _snowmanx);
   });
   private static boolean fancyGraphicsOrBetter;

   public static RenderLayer getBlockLayer(BlockState state) {
      Block _snowman = state.getBlock();
      if (_snowman instanceof LeavesBlock) {
         return fancyGraphicsOrBetter ? RenderLayer.getCutoutMipped() : RenderLayer.getSolid();
      } else {
         RenderLayer _snowmanx = BLOCKS.get(_snowman);
         return _snowmanx != null ? _snowmanx : RenderLayer.getSolid();
      }
   }

   public static RenderLayer getMovingBlockLayer(BlockState _snowman) {
      Block _snowmanx = _snowman.getBlock();
      if (_snowmanx instanceof LeavesBlock) {
         return fancyGraphicsOrBetter ? RenderLayer.getCutoutMipped() : RenderLayer.getSolid();
      } else {
         RenderLayer _snowmanxx = BLOCKS.get(_snowmanx);
         if (_snowmanxx != null) {
            return _snowmanxx == RenderLayer.getTranslucent() ? RenderLayer.getTranslucentMovingBlock() : _snowmanxx;
         } else {
            return RenderLayer.getSolid();
         }
      }
   }

   public static RenderLayer getEntityBlockLayer(BlockState state, boolean direct) {
      RenderLayer _snowman = getBlockLayer(state);
      if (_snowman == RenderLayer.getTranslucent()) {
         if (!MinecraftClient.isFabulousGraphicsOrBetter()) {
            return TexturedRenderLayers.getEntityTranslucentCull();
         } else {
            return direct ? TexturedRenderLayers.getEntityTranslucentCull() : TexturedRenderLayers.getItemEntityTranslucentCull();
         }
      } else {
         return TexturedRenderLayers.getEntityCutout();
      }
   }

   public static RenderLayer getItemLayer(ItemStack stack, boolean direct) {
      Item _snowman = stack.getItem();
      if (_snowman instanceof BlockItem) {
         Block _snowmanx = ((BlockItem)_snowman).getBlock();
         return getEntityBlockLayer(_snowmanx.getDefaultState(), direct);
      } else {
         return direct ? TexturedRenderLayers.getEntityTranslucentCull() : TexturedRenderLayers.getItemEntityTranslucentCull();
      }
   }

   public static RenderLayer getFluidLayer(FluidState state) {
      RenderLayer _snowman = FLUIDS.get(state.getFluid());
      return _snowman != null ? _snowman : RenderLayer.getSolid();
   }

   public static void setFancyGraphicsOrBetter(boolean fancyGraphicsOrBetter) {
      RenderLayers.fancyGraphicsOrBetter = fancyGraphicsOrBetter;
   }
}
