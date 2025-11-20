package net.minecraft.client.tutorial;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.toast.TutorialToast;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.GameMode;

public class FindTreeTutorialStepHandler implements TutorialStepHandler {
   private static final Set<Block> TREE_BLOCKS = Sets.newHashSet(
      new Block[]{
         Blocks.OAK_LOG,
         Blocks.SPRUCE_LOG,
         Blocks.BIRCH_LOG,
         Blocks.JUNGLE_LOG,
         Blocks.ACACIA_LOG,
         Blocks.DARK_OAK_LOG,
         Blocks.WARPED_STEM,
         Blocks.CRIMSON_STEM,
         Blocks.OAK_WOOD,
         Blocks.SPRUCE_WOOD,
         Blocks.BIRCH_WOOD,
         Blocks.JUNGLE_WOOD,
         Blocks.ACACIA_WOOD,
         Blocks.DARK_OAK_WOOD,
         Blocks.WARPED_HYPHAE,
         Blocks.CRIMSON_HYPHAE,
         Blocks.OAK_LEAVES,
         Blocks.SPRUCE_LEAVES,
         Blocks.BIRCH_LEAVES,
         Blocks.JUNGLE_LEAVES,
         Blocks.ACACIA_LEAVES,
         Blocks.DARK_OAK_LEAVES,
         Blocks.NETHER_WART_BLOCK,
         Blocks.WARPED_WART_BLOCK
      }
   );
   private static final Text TITLE = new TranslatableText("tutorial.find_tree.title");
   private static final Text DESCRIPTION = new TranslatableText("tutorial.find_tree.description");
   private final TutorialManager manager;
   private TutorialToast toast;
   private int ticks;

   public FindTreeTutorialStepHandler(TutorialManager manager) {
      this.manager = manager;
   }

   @Override
   public void tick() {
      this.ticks++;
      if (this.manager.getGameMode() != GameMode.SURVIVAL) {
         this.manager.setStep(TutorialStep.NONE);
      } else {
         if (this.ticks == 1) {
            ClientPlayerEntity _snowman = this.manager.getClient().player;
            if (_snowman != null) {
               for (Block _snowmanx : TREE_BLOCKS) {
                  if (_snowman.inventory.contains(new ItemStack(_snowmanx))) {
                     this.manager.setStep(TutorialStep.CRAFT_PLANKS);
                     return;
                  }
               }

               if (hasBrokenTreeBlocks(_snowman)) {
                  this.manager.setStep(TutorialStep.CRAFT_PLANKS);
                  return;
               }
            }
         }

         if (this.ticks >= 6000 && this.toast == null) {
            this.toast = new TutorialToast(TutorialToast.Type.TREE, TITLE, DESCRIPTION, false);
            this.manager.getClient().getToastManager().add(this.toast);
         }
      }
   }

   @Override
   public void destroy() {
      if (this.toast != null) {
         this.toast.hide();
         this.toast = null;
      }
   }

   @Override
   public void onTarget(ClientWorld world, HitResult hitResult) {
      if (hitResult.getType() == HitResult.Type.BLOCK) {
         BlockState _snowman = world.getBlockState(((BlockHitResult)hitResult).getBlockPos());
         if (TREE_BLOCKS.contains(_snowman.getBlock())) {
            this.manager.setStep(TutorialStep.PUNCH_TREE);
         }
      }
   }

   @Override
   public void onSlotUpdate(ItemStack stack) {
      for (Block _snowman : TREE_BLOCKS) {
         if (stack.getItem() == _snowman.asItem()) {
            this.manager.setStep(TutorialStep.CRAFT_PLANKS);
            return;
         }
      }
   }

   public static boolean hasBrokenTreeBlocks(ClientPlayerEntity player) {
      for (Block _snowman : TREE_BLOCKS) {
         if (player.getStatHandler().getStat(Stats.MINED.getOrCreateStat(_snowman)) > 0) {
            return true;
         }
      }

      return false;
   }
}
