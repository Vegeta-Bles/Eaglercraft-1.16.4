package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterestType;

public class ForgetCompletedPointOfInterestTask extends Task<LivingEntity> {
   private final MemoryModuleType<GlobalPos> memoryModule;
   private final Predicate<PointOfInterestType> condition;

   public ForgetCompletedPointOfInterestTask(PointOfInterestType poiType, MemoryModuleType<GlobalPos> memoryModule) {
      super(ImmutableMap.of(memoryModule, MemoryModuleState.VALUE_PRESENT));
      this.condition = poiType.getCompletionCondition();
      this.memoryModule = memoryModule;
   }

   @Override
   protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
      GlobalPos _snowman = entity.getBrain().getOptionalMemory(this.memoryModule).get();
      return world.getRegistryKey() == _snowman.getDimension() && _snowman.getPos().isWithinDistance(entity.getPos(), 16.0);
   }

   @Override
   protected void run(ServerWorld world, LivingEntity entity, long time) {
      Brain<?> _snowman = entity.getBrain();
      GlobalPos _snowmanx = _snowman.getOptionalMemory(this.memoryModule).get();
      BlockPos _snowmanxx = _snowmanx.getPos();
      ServerWorld _snowmanxxx = world.getServer().getWorld(_snowmanx.getDimension());
      if (_snowmanxxx == null || this.hasCompletedPointOfInterest(_snowmanxxx, _snowmanxx)) {
         _snowman.forget(this.memoryModule);
      } else if (this.isBedOccupiedByOthers(_snowmanxxx, _snowmanxx, entity)) {
         _snowman.forget(this.memoryModule);
         world.getPointOfInterestStorage().releaseTicket(_snowmanxx);
         DebugInfoSender.sendPointOfInterest(world, _snowmanxx);
      }
   }

   private boolean isBedOccupiedByOthers(ServerWorld world, BlockPos pos, LivingEntity entity) {
      BlockState _snowman = world.getBlockState(pos);
      return _snowman.getBlock().isIn(BlockTags.BEDS) && _snowman.get(BedBlock.OCCUPIED) && !entity.isSleeping();
   }

   private boolean hasCompletedPointOfInterest(ServerWorld world, BlockPos pos) {
      return !world.getPointOfInterestStorage().test(pos, this.condition);
   }
}
