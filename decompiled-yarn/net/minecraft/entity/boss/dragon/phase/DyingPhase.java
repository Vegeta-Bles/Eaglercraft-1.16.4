package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;

public class DyingPhase extends AbstractPhase {
   private Vec3d target;
   private int ticks;

   public DyingPhase(EnderDragonEntity _snowman) {
      super(_snowman);
   }

   @Override
   public void clientTick() {
      if (this.ticks++ % 10 == 0) {
         float _snowman = (this.dragon.getRandom().nextFloat() - 0.5F) * 8.0F;
         float _snowmanx = (this.dragon.getRandom().nextFloat() - 0.5F) * 4.0F;
         float _snowmanxx = (this.dragon.getRandom().nextFloat() - 0.5F) * 8.0F;
         this.dragon
            .world
            .addParticle(
               ParticleTypes.EXPLOSION_EMITTER,
               this.dragon.getX() + (double)_snowman,
               this.dragon.getY() + 2.0 + (double)_snowmanx,
               this.dragon.getZ() + (double)_snowmanxx,
               0.0,
               0.0,
               0.0
            );
      }
   }

   @Override
   public void serverTick() {
      this.ticks++;
      if (this.target == null) {
         BlockPos _snowman = this.dragon.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, EndPortalFeature.ORIGIN);
         this.target = Vec3d.ofBottomCenter(_snowman);
      }

      double _snowman = this.target.squaredDistanceTo(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
      if (!(_snowman < 100.0) && !(_snowman > 22500.0) && !this.dragon.horizontalCollision && !this.dragon.verticalCollision) {
         this.dragon.setHealth(1.0F);
      } else {
         this.dragon.setHealth(0.0F);
      }
   }

   @Override
   public void beginPhase() {
      this.target = null;
      this.ticks = 0;
   }

   @Override
   public float getMaxYAcceleration() {
      return 3.0F;
   }

   @Nullable
   @Override
   public Vec3d getTarget() {
      return this.target;
   }

   @Override
   public PhaseType<DyingPhase> getType() {
      return PhaseType.DYING;
   }
}
