package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;

public class SeekSkyTask extends Task<LivingEntity> {
   private final float speed;

   public SeekSkyTask(float speed) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT));
      this.speed = speed;
   }

   @Override
   protected void run(ServerWorld world, LivingEntity entity, long time) {
      Optional<Vec3d> _snowman = Optional.ofNullable(this.findNearbySky(world, entity));
      if (_snowman.isPresent()) {
         entity.getBrain().remember(MemoryModuleType.WALK_TARGET, _snowman.map(_snowmanx -> new WalkTarget(_snowmanx, this.speed, 0)));
      }
   }

   @Override
   protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
      return !world.isSkyVisible(entity.getBlockPos());
   }

   @Nullable
   private Vec3d findNearbySky(ServerWorld world, LivingEntity entity) {
      Random _snowman = entity.getRandom();
      BlockPos _snowmanx = entity.getBlockPos();

      for (int _snowmanxx = 0; _snowmanxx < 10; _snowmanxx++) {
         BlockPos _snowmanxxx = _snowmanx.add(_snowman.nextInt(20) - 10, _snowman.nextInt(6) - 3, _snowman.nextInt(20) - 10);
         if (isSkyVisible(world, entity, _snowmanxxx)) {
            return Vec3d.ofBottomCenter(_snowmanxxx);
         }
      }

      return null;
   }

   public static boolean isSkyVisible(ServerWorld world, LivingEntity entity, BlockPos pos) {
      return world.isSkyVisible(pos) && (double)world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos).getY() <= entity.getY();
   }
}
