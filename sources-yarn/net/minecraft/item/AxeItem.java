package net.minecraft.item;

import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AxeItem extends MiningToolItem {
   private static final Set<Material> field_23139 = Sets.newHashSet(
      new Material[]{Material.WOOD, Material.NETHER_WOOD, Material.PLANT, Material.REPLACEABLE_PLANT, Material.BAMBOO, Material.GOURD}
   );
   private static final Set<Block> EFFECTIVE_BLOCKS = Sets.newHashSet(
      new Block[]{
         Blocks.LADDER,
         Blocks.SCAFFOLDING,
         Blocks.OAK_BUTTON,
         Blocks.SPRUCE_BUTTON,
         Blocks.BIRCH_BUTTON,
         Blocks.JUNGLE_BUTTON,
         Blocks.DARK_OAK_BUTTON,
         Blocks.ACACIA_BUTTON,
         Blocks.CRIMSON_BUTTON,
         Blocks.WARPED_BUTTON
      }
   );
   protected static final Map<Block, Block> STRIPPED_BLOCKS = new Builder()
      .put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD)
      .put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG)
      .put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD)
      .put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG)
      .put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD)
      .put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG)
      .put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD)
      .put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG)
      .put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD)
      .put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG)
      .put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD)
      .put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG)
      .put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM)
      .put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE)
      .put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM)
      .put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE)
      .build();

   protected AxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
      super(attackDamage, attackSpeed, material, EFFECTIVE_BLOCKS, settings);
   }

   @Override
   public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
      Material lv = state.getMaterial();
      return field_23139.contains(lv) ? this.miningSpeed : super.getMiningSpeedMultiplier(stack, state);
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      World lv = context.getWorld();
      BlockPos lv2 = context.getBlockPos();
      BlockState lv3 = lv.getBlockState(lv2);
      Block lv4 = STRIPPED_BLOCKS.get(lv3.getBlock());
      if (lv4 != null) {
         PlayerEntity lv5 = context.getPlayer();
         lv.playSound(lv5, lv2, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
         if (!lv.isClient) {
            lv.setBlockState(lv2, lv4.getDefaultState().with(PillarBlock.AXIS, lv3.get(PillarBlock.AXIS)), 11);
            if (lv5 != null) {
               context.getStack().damage(1, lv5, p -> p.sendToolBreakStatus(context.getHand()));
            }
         }

         return ActionResult.success(lv.isClient);
      } else {
         return ActionResult.PASS;
      }
   }
}
