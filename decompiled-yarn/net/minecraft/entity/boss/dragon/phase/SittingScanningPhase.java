package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class SittingScanningPhase extends AbstractSittingPhase {
   private static final TargetPredicate PLAYER_WITHIN_RANGE_PREDICATE = new TargetPredicate().setBaseMaxDistance(150.0);
   private final TargetPredicate CLOSE_PLAYER_PREDICATE;
   private int ticks;

   public SittingScanningPhase(EnderDragonEntity _snowman) {
      super(_snowman);
      this.CLOSE_PLAYER_PREDICATE = new TargetPredicate().setBaseMaxDistance(20.0).setPredicate(_snowmanxx -> Math.abs(_snowmanxx.getY() - _snowman.getY()) <= 10.0);
   }

   @Override
   public void serverTick() {
      this.ticks++;
      LivingEntity _snowman = this.dragon.world.getClosestPlayer(this.CLOSE_PLAYER_PREDICATE, this.dragon, this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
      if (_snowman != null) {
         if (this.ticks > 25) {
            this.dragon.getPhaseManager().setPhase(PhaseType.SITTING_ATTACKING);
         } else {
            Vec3d _snowmanx = new Vec3d(_snowman.getX() - this.dragon.getX(), 0.0, _snowman.getZ() - this.dragon.getZ()).normalize();
            Vec3d _snowmanxx = new Vec3d(
                  (double)MathHelper.sin(this.dragon.yaw * (float) (Math.PI / 180.0)),
                  0.0,
                  (double)(-MathHelper.cos(this.dragon.yaw * (float) (Math.PI / 180.0)))
               )
               .normalize();
            float _snowmanxxx = (float)_snowmanxx.dotProduct(_snowmanx);
            float _snowmanxxxx = (float)(Math.acos((double)_snowmanxxx) * 180.0F / (float)Math.PI) + 0.5F;
            if (_snowmanxxxx < 0.0F || _snowmanxxxx > 10.0F) {
               double _snowmanxxxxx = _snowman.getX() - this.dragon.partHead.getX();
               double _snowmanxxxxxx = _snowman.getZ() - this.dragon.partHead.getZ();
               double _snowmanxxxxxxx = MathHelper.clamp(
                  MathHelper.wrapDegrees(180.0 - MathHelper.atan2(_snowmanxxxxx, _snowmanxxxxxx) * 180.0F / (float)Math.PI - (double)this.dragon.yaw), -100.0, 100.0
               );
               this.dragon.field_20865 *= 0.8F;
               float _snowmanxxxxxxxx = MathHelper.sqrt(_snowmanxxxxx * _snowmanxxxxx + _snowmanxxxxxx * _snowmanxxxxxx) + 1.0F;
               float _snowmanxxxxxxxxx = _snowmanxxxxxxxx;
               if (_snowmanxxxxxxxx > 40.0F) {
                  _snowmanxxxxxxxx = 40.0F;
               }

               this.dragon.field_20865 = (float)((double)this.dragon.field_20865 + _snowmanxxxxxxx * (double)(0.7F / _snowmanxxxxxxxx / _snowmanxxxxxxxxx));
               this.dragon.yaw = this.dragon.yaw + this.dragon.field_20865;
            }
         }
      } else if (this.ticks >= 100) {
         _snowman = this.dragon.world.getClosestPlayer(PLAYER_WITHIN_RANGE_PREDICATE, this.dragon, this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
         this.dragon.getPhaseManager().setPhase(PhaseType.TAKEOFF);
         if (_snowman != null) {
            this.dragon.getPhaseManager().setPhase(PhaseType.CHARGING_PLAYER);
            this.dragon.getPhaseManager().create(PhaseType.CHARGING_PLAYER).setTarget(new Vec3d(_snowman.getX(), _snowman.getY(), _snowman.getZ()));
         }
      }
   }

   @Override
   public void beginPhase() {
      this.ticks = 0;
   }

   @Override
   public PhaseType<SittingScanningPhase> getType() {
      return PhaseType.SITTING_SCANNING;
   }
}
