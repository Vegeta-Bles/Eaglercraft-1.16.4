package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class BoneMealTask extends Task<VillagerEntity> {
   private long startTime;
   private long lastEndEntityAge;
   private int duration;
   private Optional<BlockPos> pos = Optional.empty();

   public BoneMealTask() {
      super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT));
   }

   protected boolean shouldRun(ServerWorld _snowman, VillagerEntity _snowman) {
      if (_snowman.age % 10 == 0 && (this.lastEndEntityAge == 0L || this.lastEndEntityAge + 160L <= (long)_snowman.age)) {
         if (_snowman.getInventory().count(Items.BONE_MEAL) <= 0) {
            return false;
         } else {
            this.pos = this.findBoneMealPos(_snowman, _snowman);
            return this.pos.isPresent();
         }
      } else {
         return false;
      }
   }

   protected boolean shouldKeepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      return this.duration < 80 && this.pos.isPresent();
   }

   private Optional<BlockPos> findBoneMealPos(ServerWorld world, VillagerEntity entity) {
      BlockPos.Mutable _snowman = new BlockPos.Mutable();
      Optional<BlockPos> _snowmanx = Optional.empty();
      int _snowmanxx = 0;

      for (int _snowmanxxx = -1; _snowmanxxx <= 1; _snowmanxxx++) {
         for (int _snowmanxxxx = -1; _snowmanxxxx <= 1; _snowmanxxxx++) {
            for (int _snowmanxxxxx = -1; _snowmanxxxxx <= 1; _snowmanxxxxx++) {
               _snowman.set(entity.getBlockPos(), _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
               if (this.canBoneMeal(_snowman, world)) {
                  if (world.random.nextInt(++_snowmanxx) == 0) {
                     _snowmanx = Optional.of(_snowman.toImmutable());
                  }
               }
            }
         }
      }

      return _snowmanx;
   }

   private boolean canBoneMeal(BlockPos pos, ServerWorld world) {
      BlockState _snowman = world.getBlockState(pos);
      Block _snowmanx = _snowman.getBlock();
      return _snowmanx instanceof CropBlock && !((CropBlock)_snowmanx).isMature(_snowman);
   }

   protected void run(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      this.addLookWalkTargets(_snowman);
      _snowman.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BONE_MEAL));
      this.startTime = _snowman;
      this.duration = 0;
   }

   private void addLookWalkTargets(VillagerEntity villager) {
      this.pos.ifPresent(_snowmanx -> {
         BlockPosLookTarget _snowmanxx = new BlockPosLookTarget(_snowmanx);
         villager.getBrain().remember(MemoryModuleType.LOOK_TARGET, _snowmanxx);
         villager.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(_snowmanxx, 0.5F, 1));
      });
   }

   protected void finishRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      _snowman.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
      this.lastEndEntityAge = (long)_snowman.age;
   }

   protected void keepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      BlockPos _snowmanxxx = this.pos.get();
      if (_snowman >= this.startTime && _snowmanxxx.isWithinDistance(_snowman.getPos(), 1.0)) {
         ItemStack _snowmanxxxx = ItemStack.EMPTY;
         SimpleInventory _snowmanxxxxx = _snowman.getInventory();
         int _snowmanxxxxxx = _snowmanxxxxx.size();

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxx++) {
            ItemStack _snowmanxxxxxxxx = _snowmanxxxxx.getStack(_snowmanxxxxxxx);
            if (_snowmanxxxxxxxx.getItem() == Items.BONE_MEAL) {
               _snowmanxxxx = _snowmanxxxxxxxx;
               break;
            }
         }

         if (!_snowmanxxxx.isEmpty() && BoneMealItem.useOnFertilizable(_snowmanxxxx, _snowman, _snowmanxxx)) {
            _snowman.syncWorldEvent(2005, _snowmanxxx, 0);
            this.pos = this.findBoneMealPos(_snowman, _snowman);
            this.addLookWalkTargets(_snowman);
            this.startTime = _snowman + 40L;
         }

         this.duration++;
      }
   }
}
