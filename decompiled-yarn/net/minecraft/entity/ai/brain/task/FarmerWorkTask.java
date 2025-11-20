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
      Optional<GlobalPos> _snowman = entity.getBrain().getOptionalMemory(MemoryModuleType.JOB_SITE);
      if (_snowman.isPresent()) {
         GlobalPos _snowmanx = _snowman.get();
         BlockState _snowmanxx = world.getBlockState(_snowmanx.getPos());
         if (_snowmanxx.isOf(Blocks.COMPOSTER)) {
            this.craftAndDropBread(entity);
            this.compostSeeds(world, entity, _snowmanx, _snowmanxx);
         }
      }
   }

   private void compostSeeds(ServerWorld world, VillagerEntity entity, GlobalPos pos, BlockState composterState) {
      BlockPos _snowman = pos.getPos();
      if (composterState.get(ComposterBlock.LEVEL) == 8) {
         composterState = ComposterBlock.emptyFullComposter(composterState, world, _snowman);
      }

      int _snowmanx = 20;
      int _snowmanxx = 10;
      int[] _snowmanxxx = new int[COMPOSTABLES.size()];
      SimpleInventory _snowmanxxxx = entity.getInventory();
      int _snowmanxxxxx = _snowmanxxxx.size();
      BlockState _snowmanxxxxxx = composterState;

      for (int _snowmanxxxxxxx = _snowmanxxxxx - 1; _snowmanxxxxxxx >= 0 && _snowmanx > 0; _snowmanxxxxxxx--) {
         ItemStack _snowmanxxxxxxxx = _snowmanxxxx.getStack(_snowmanxxxxxxx);
         int _snowmanxxxxxxxxx = COMPOSTABLES.indexOf(_snowmanxxxxxxxx.getItem());
         if (_snowmanxxxxxxxxx != -1) {
            int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.getCount();
            int _snowmanxxxxxxxxxxx = _snowmanxxx[_snowmanxxxxxxxxx] + _snowmanxxxxxxxxxx;
            _snowmanxxx[_snowmanxxxxxxxxx] = _snowmanxxxxxxxxxxx;
            int _snowmanxxxxxxxxxxxx = Math.min(Math.min(_snowmanxxxxxxxxxxx - 10, _snowmanx), _snowmanxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxxx > 0) {
               _snowmanx -= _snowmanxxxxxxxxxxxx;

               for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxx++) {
                  _snowmanxxxxxx = ComposterBlock.compost(_snowmanxxxxxx, world, _snowmanxxxxxxxx, _snowman);
                  if (_snowmanxxxxxx.get(ComposterBlock.LEVEL) == 7) {
                     this.method_30232(world, composterState, _snowman, _snowmanxxxxxx);
                     return;
                  }
               }
            }
         }
      }

      this.method_30232(world, composterState, _snowman, _snowmanxxxxxx);
   }

   private void method_30232(ServerWorld _snowman, BlockState _snowman, BlockPos _snowman, BlockState _snowman) {
      _snowman.syncWorldEvent(1500, _snowman, _snowman != _snowman ? 1 : 0);
   }

   private void craftAndDropBread(VillagerEntity entity) {
      SimpleInventory _snowman = entity.getInventory();
      if (_snowman.count(Items.BREAD) <= 36) {
         int _snowmanx = _snowman.count(Items.WHEAT);
         int _snowmanxx = 3;
         int _snowmanxxx = 3;
         int _snowmanxxxx = Math.min(3, _snowmanx / 3);
         if (_snowmanxxxx != 0) {
            int _snowmanxxxxx = _snowmanxxxx * 3;
            _snowman.removeItem(Items.WHEAT, _snowmanxxxxx);
            ItemStack _snowmanxxxxxx = _snowman.addStack(new ItemStack(Items.BREAD, _snowmanxxxx));
            if (!_snowmanxxxxxx.isEmpty()) {
               entity.dropStack(_snowmanxxxxxx, 0.5F);
            }
         }
      }
   }
}
