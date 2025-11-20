package net.minecraft.entity.boss.dragon.phase;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;

public class LandingPhase extends AbstractPhase {
   private Vec3d target;

   public LandingPhase(EnderDragonEntity _snowman) {
      super(_snowman);
   }

   @Override
   public void clientTick() {
      Vec3d _snowman = this.dragon.method_6834(1.0F).normalize();
      _snowman.rotateY((float) (-Math.PI / 4));
      double _snowmanx = this.dragon.partHead.getX();
      double _snowmanxx = this.dragon.partHead.getBodyY(0.5);
      double _snowmanxxx = this.dragon.partHead.getZ();

      for (int _snowmanxxxx = 0; _snowmanxxxx < 8; _snowmanxxxx++) {
         Random _snowmanxxxxx = this.dragon.getRandom();
         double _snowmanxxxxxx = _snowmanx + _snowmanxxxxx.nextGaussian() / 2.0;
         double _snowmanxxxxxxx = _snowmanxx + _snowmanxxxxx.nextGaussian() / 2.0;
         double _snowmanxxxxxxxx = _snowmanxxx + _snowmanxxxxx.nextGaussian() / 2.0;
         Vec3d _snowmanxxxxxxxxx = this.dragon.getVelocity();
         this.dragon
            .world
            .addParticle(
               ParticleTypes.DRAGON_BREATH, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, -_snowman.x * 0.08F + _snowmanxxxxxxxxx.x, -_snowman.y * 0.3F + _snowmanxxxxxxxxx.y, -_snowman.z * 0.08F + _snowmanxxxxxxxxx.z
            );
         _snowman.rotateY((float) (Math.PI / 16));
      }
   }

   @Override
   public void serverTick() {
      if (this.target == null) {
         this.target = Vec3d.ofBottomCenter(this.dragon.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPortalFeature.ORIGIN));
      }

      if (this.target.squaredDistanceTo(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ()) < 1.0) {
         this.dragon.getPhaseManager().create(PhaseType.SITTING_FLAMING).method_6857();
         this.dragon.getPhaseManager().setPhase(PhaseType.SITTING_SCANNING);
      }
   }

   @Override
   public float getMaxYAcceleration() {
      return 1.5F;
   }

   @Override
   public float method_6847() {
      float _snowman = MathHelper.sqrt(Entity.squaredHorizontalLength(this.dragon.getVelocity())) + 1.0F;
      float _snowmanx = Math.min(_snowman, 40.0F);
      return _snowmanx / _snowman;
   }

   @Override
   public void beginPhase() {
      this.target = null;
   }

   @Nullable
   @Override
   public Vec3d getTarget() {
      return this.target;
   }

   @Override
   public PhaseType<LandingPhase> getType() {
      return PhaseType.LANDING;
   }
}
