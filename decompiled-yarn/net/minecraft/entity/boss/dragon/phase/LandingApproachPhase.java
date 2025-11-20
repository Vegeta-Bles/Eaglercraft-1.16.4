package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;

public class LandingApproachPhase extends AbstractPhase {
   private static final TargetPredicate PLAYERS_IN_RANGE_PREDICATE = new TargetPredicate().setBaseMaxDistance(128.0);
   private Path field_7047;
   private Vec3d target;

   public LandingApproachPhase(EnderDragonEntity _snowman) {
      super(_snowman);
   }

   @Override
   public PhaseType<LandingApproachPhase> getType() {
      return PhaseType.LANDING_APPROACH;
   }

   @Override
   public void beginPhase() {
      this.field_7047 = null;
      this.target = null;
   }

   @Override
   public void serverTick() {
      double _snowman = this.target == null ? 0.0 : this.target.squaredDistanceTo(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
      if (_snowman < 100.0 || _snowman > 22500.0 || this.dragon.horizontalCollision || this.dragon.verticalCollision) {
         this.method_6844();
      }
   }

   @Nullable
   @Override
   public Vec3d getTarget() {
      return this.target;
   }

   private void method_6844() {
      if (this.field_7047 == null || this.field_7047.isFinished()) {
         int _snowman = this.dragon.getNearestPathNodeIndex();
         BlockPos _snowmanx = this.dragon.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPortalFeature.ORIGIN);
         PlayerEntity _snowmanxx = this.dragon.world.getClosestPlayer(PLAYERS_IN_RANGE_PREDICATE, (double)_snowmanx.getX(), (double)_snowmanx.getY(), (double)_snowmanx.getZ());
         int _snowmanxxx;
         if (_snowmanxx != null) {
            Vec3d _snowmanxxxx = new Vec3d(_snowmanxx.getX(), 0.0, _snowmanxx.getZ()).normalize();
            _snowmanxxx = this.dragon.getNearestPathNodeIndex(-_snowmanxxxx.x * 40.0, 105.0, -_snowmanxxxx.z * 40.0);
         } else {
            _snowmanxxx = this.dragon.getNearestPathNodeIndex(40.0, (double)_snowmanx.getY(), 0.0);
         }

         PathNode _snowmanxxxx = new PathNode(_snowmanx.getX(), _snowmanx.getY(), _snowmanx.getZ());
         this.field_7047 = this.dragon.findPath(_snowman, _snowmanxxx, _snowmanxxxx);
         if (this.field_7047 != null) {
            this.field_7047.next();
         }
      }

      this.method_6845();
      if (this.field_7047 != null && this.field_7047.isFinished()) {
         this.dragon.getPhaseManager().setPhase(PhaseType.LANDING);
      }
   }

   private void method_6845() {
      if (this.field_7047 != null && !this.field_7047.isFinished()) {
         Vec3i _snowman = this.field_7047.method_31032();
         this.field_7047.next();
         double _snowmanx = (double)_snowman.getX();
         double _snowmanxx = (double)_snowman.getZ();

         double _snowmanxxx;
         do {
            _snowmanxxx = (double)((float)_snowman.getY() + this.dragon.getRandom().nextFloat() * 20.0F);
         } while (_snowmanxxx < (double)_snowman.getY());

         this.target = new Vec3d(_snowmanx, _snowmanxxx, _snowmanxx);
      }
   }
}
