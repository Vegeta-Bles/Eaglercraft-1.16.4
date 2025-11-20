package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.GameRules;

public class FarmerVillagerTask extends Task<VillagerEntity> {
   @Nullable
   private BlockPos currentTarget;
   private long nextResponseTime;
   private int ticksRan;
   private final List<BlockPos> targetPositions = Lists.newArrayList();

   public FarmerVillagerTask() {
      super(
         ImmutableMap.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleState.VALUE_ABSENT,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleState.VALUE_ABSENT,
            MemoryModuleType.SECONDARY_JOB_SITE,
            MemoryModuleState.VALUE_PRESENT
         )
      );
   }

   protected boolean shouldRun(ServerWorld _snowman, VillagerEntity _snowman) {
      if (!_snowman.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
         return false;
      } else if (_snowman.getVillagerData().getProfession() != VillagerProfession.FARMER) {
         return false;
      } else {
         BlockPos.Mutable _snowmanxx = _snowman.getBlockPos().mutableCopy();
         this.targetPositions.clear();

         for (int _snowmanxxx = -1; _snowmanxxx <= 1; _snowmanxxx++) {
            for (int _snowmanxxxx = -1; _snowmanxxxx <= 1; _snowmanxxxx++) {
               for (int _snowmanxxxxx = -1; _snowmanxxxxx <= 1; _snowmanxxxxx++) {
                  _snowmanxx.set(_snowman.getX() + (double)_snowmanxxx, _snowman.getY() + (double)_snowmanxxxx, _snowman.getZ() + (double)_snowmanxxxxx);
                  if (this.isSuitableTarget(_snowmanxx, _snowman)) {
                     this.targetPositions.add(new BlockPos(_snowmanxx));
                  }
               }
            }
         }

         this.currentTarget = this.chooseRandomTarget(_snowman);
         return this.currentTarget != null;
      }
   }

   @Nullable
   private BlockPos chooseRandomTarget(ServerWorld world) {
      return this.targetPositions.isEmpty() ? null : this.targetPositions.get(world.getRandom().nextInt(this.targetPositions.size()));
   }

   private boolean isSuitableTarget(BlockPos pos, ServerWorld world) {
      BlockState _snowman = world.getBlockState(pos);
      Block _snowmanx = _snowman.getBlock();
      Block _snowmanxx = world.getBlockState(pos.down()).getBlock();
      return _snowmanx instanceof CropBlock && ((CropBlock)_snowmanx).isMature(_snowman) || _snowman.isAir() && _snowmanxx instanceof FarmlandBlock;
   }

   protected void run(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      if (_snowman > this.nextResponseTime && this.currentTarget != null) {
         _snowman.getBrain().remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(this.currentTarget));
         _snowman.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosLookTarget(this.currentTarget), 0.5F, 1));
      }
   }

   protected void finishRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      _snowman.getBrain().forget(MemoryModuleType.LOOK_TARGET);
      _snowman.getBrain().forget(MemoryModuleType.WALK_TARGET);
      this.ticksRan = 0;
      this.nextResponseTime = _snowman + 40L;
   }

   protected void keepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      if (this.currentTarget == null || this.currentTarget.isWithinDistance(_snowman.getPos(), 1.0)) {
         if (this.currentTarget != null && _snowman > this.nextResponseTime) {
            BlockState _snowmanxxx = _snowman.getBlockState(this.currentTarget);
            Block _snowmanxxxx = _snowmanxxx.getBlock();
            Block _snowmanxxxxx = _snowman.getBlockState(this.currentTarget.down()).getBlock();
            if (_snowmanxxxx instanceof CropBlock && ((CropBlock)_snowmanxxxx).isMature(_snowmanxxx)) {
               _snowman.breakBlock(this.currentTarget, true, _snowman);
            }

            if (_snowmanxxx.isAir() && _snowmanxxxxx instanceof FarmlandBlock && _snowman.hasSeedToPlant()) {
               SimpleInventory _snowmanxxxxxx = _snowman.getInventory();

               for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxxx.size(); _snowmanxxxxxxx++) {
                  ItemStack _snowmanxxxxxxxx = _snowmanxxxxxx.getStack(_snowmanxxxxxxx);
                  boolean _snowmanxxxxxxxxx = false;
                  if (!_snowmanxxxxxxxx.isEmpty()) {
                     if (_snowmanxxxxxxxx.getItem() == Items.WHEAT_SEEDS) {
                        _snowman.setBlockState(this.currentTarget, Blocks.WHEAT.getDefaultState(), 3);
                        _snowmanxxxxxxxxx = true;
                     } else if (_snowmanxxxxxxxx.getItem() == Items.POTATO) {
                        _snowman.setBlockState(this.currentTarget, Blocks.POTATOES.getDefaultState(), 3);
                        _snowmanxxxxxxxxx = true;
                     } else if (_snowmanxxxxxxxx.getItem() == Items.CARROT) {
                        _snowman.setBlockState(this.currentTarget, Blocks.CARROTS.getDefaultState(), 3);
                        _snowmanxxxxxxxxx = true;
                     } else if (_snowmanxxxxxxxx.getItem() == Items.BEETROOT_SEEDS) {
                        _snowman.setBlockState(this.currentTarget, Blocks.BEETROOTS.getDefaultState(), 3);
                        _snowmanxxxxxxxxx = true;
                     }
                  }

                  if (_snowmanxxxxxxxxx) {
                     _snowman.playSound(
                        null,
                        (double)this.currentTarget.getX(),
                        (double)this.currentTarget.getY(),
                        (double)this.currentTarget.getZ(),
                        SoundEvents.ITEM_CROP_PLANT,
                        SoundCategory.BLOCKS,
                        1.0F,
                        1.0F
                     );
                     _snowmanxxxxxxxx.decrement(1);
                     if (_snowmanxxxxxxxx.isEmpty()) {
                        _snowmanxxxxxx.setStack(_snowmanxxxxxxx, ItemStack.EMPTY);
                     }
                     break;
                  }
               }
            }

            if (_snowmanxxxx instanceof CropBlock && !((CropBlock)_snowmanxxxx).isMature(_snowmanxxx)) {
               this.targetPositions.remove(this.currentTarget);
               this.currentTarget = this.chooseRandomTarget(_snowman);
               if (this.currentTarget != null) {
                  this.nextResponseTime = _snowman + 20L;
                  _snowman.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosLookTarget(this.currentTarget), 0.5F, 1));
                  _snowman.getBrain().remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(this.currentTarget));
               }
            }
         }

         this.ticksRan++;
      }
   }

   protected boolean shouldKeepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      return this.ticksRan < 200;
   }
}
