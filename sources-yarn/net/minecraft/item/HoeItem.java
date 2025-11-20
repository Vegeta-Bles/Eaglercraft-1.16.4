package net.minecraft.item;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class HoeItem extends MiningToolItem {
   private static final Set<Block> EFFECTIVE_BLOCKS = ImmutableSet.of(
      Blocks.NETHER_WART_BLOCK,
      Blocks.WARPED_WART_BLOCK,
      Blocks.HAY_BLOCK,
      Blocks.DRIED_KELP_BLOCK,
      Blocks.TARGET,
      Blocks.SHROOMLIGHT,
      new Block[]{
         Blocks.SPONGE,
         Blocks.WET_SPONGE,
         Blocks.JUNGLE_LEAVES,
         Blocks.OAK_LEAVES,
         Blocks.SPRUCE_LEAVES,
         Blocks.DARK_OAK_LEAVES,
         Blocks.ACACIA_LEAVES,
         Blocks.BIRCH_LEAVES
      }
   );
   protected static final Map<Block, BlockState> TILLED_BLOCKS = Maps.newHashMap(
      ImmutableMap.of(
         Blocks.GRASS_BLOCK,
         Blocks.FARMLAND.getDefaultState(),
         Blocks.GRASS_PATH,
         Blocks.FARMLAND.getDefaultState(),
         Blocks.DIRT,
         Blocks.FARMLAND.getDefaultState(),
         Blocks.COARSE_DIRT,
         Blocks.DIRT.getDefaultState()
      )
   );

   protected HoeItem(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
      super((float)attackDamage, attackSpeed, material, EFFECTIVE_BLOCKS, settings);
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      World lv = context.getWorld();
      BlockPos lv2 = context.getBlockPos();
      if (context.getSide() != Direction.DOWN && lv.getBlockState(lv2.up()).isAir()) {
         BlockState lv3 = TILLED_BLOCKS.get(lv.getBlockState(lv2).getBlock());
         if (lv3 != null) {
            PlayerEntity lv4 = context.getPlayer();
            lv.playSound(lv4, lv2, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!lv.isClient) {
               lv.setBlockState(lv2, lv3, 11);
               if (lv4 != null) {
                  context.getStack().damage(1, lv4, p -> p.sendToolBreakStatus(context.getHand()));
               }
            }

            return ActionResult.success(lv.isClient);
         }
      }

      return ActionResult.PASS;
   }
}
