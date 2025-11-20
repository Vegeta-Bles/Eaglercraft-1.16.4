package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;

public class FarmerWorkTask extends VillagerWorkTask {
   private static final List<Item> COMPOSTABLES = ImmutableList.of(Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS);

   public FarmerWorkTask() {
   }

   @Override
   protected void performAdditionalWork(ServerWorld world, VillagerEntity entity) {
      Optional<GlobalPos> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.JOB_SITE);
      if (optional.isPresent()) {
         GlobalPos lv = optional.get();
         BlockState lv2 = world.getBlockState(lv.getPos());
         if (lv2.isOf(Blocks.COMPOSTER)) {
            this.craftAndDropBread(entity);
            this.compostSeeds(world, entity, lv, lv2);
         }
      }
   }

   private void compostSeeds(ServerWorld world, VillagerEntity entity, GlobalPos pos, BlockState composterState) {
      BlockPos lv = pos.getPos();
      if (composterState.get(ComposterBlock.LEVEL) == 8) {
         composterState = ComposterBlock.emptyFullComposter(composterState, world, lv);
      }

      int i = 20;
      int j = 10;
      int[] is = new int[COMPOSTABLES.size()];
      SimpleInventory lv2 = entity.getInventory();
      int k = lv2.size();
      BlockState lv3 = composterState;

      for (int l = k - 1; l >= 0 && i > 0; l--) {
         ItemStack lv4 = lv2.getStack(l);
         int m = COMPOSTABLES.indexOf(lv4.getItem());
         if (m != -1) {
            int n = lv4.getCount();
            int o = is[m] + n;
            is[m] = o;
            int p = Math.min(Math.min(o - 10, i), n);
            if (p > 0) {
               i -= p;

               for (int q = 0; q < p; q++) {
                  lv3 = ComposterBlock.compost(lv3, world, lv4, lv);
                  if (lv3.get(ComposterBlock.LEVEL) == 7) {
                     this.method_30232(world, composterState, lv, lv3);
                     return;
                  }
               }
            }
         }
      }

      this.method_30232(world, composterState, lv, lv3);
   }

   private void method_30232(ServerWorld arg, BlockState arg2, BlockPos arg3, BlockState arg4) {
      arg.syncWorldEvent(1500, arg3, arg4 != arg2 ? 1 : 0);
   }

   private void craftAndDropBread(VillagerEntity entity) {
      SimpleInventory lv = entity.getInventory();
      if (lv.count(Items.BREAD) <= 36) {
         int i = lv.count(Items.WHEAT);
         int j = 3;
         int k = 3;
         int l = Math.min(3, i / 3);
         if (l != 0) {
            int m = l * 3;
            lv.removeItem(Items.WHEAT, m);
            ItemStack lv2 = lv.addStack(new ItemStack(Items.BREAD, l));
            if (!lv2.isEmpty()) {
               entity.dropStack(lv2, 0.5F);
            }
         }
      }
   }
}
