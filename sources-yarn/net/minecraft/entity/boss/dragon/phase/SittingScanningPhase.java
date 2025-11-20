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

   public SittingScanningPhase(EnderDragonEntity arg) {
      super(arg);
      this.CLOSE_PLAYER_PREDICATE = new TargetPredicate().setBaseMaxDistance(20.0).setPredicate(arg2 -> Math.abs(arg2.getY() - arg.getY()) <= 10.0);
   }

   @Override
   public void serverTick() {
      this.ticks++;
      LivingEntity lv = this.dragon
         .world
         .getClosestPlayer(this.CLOSE_PLAYER_PREDICATE, this.dragon, this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
      if (lv != null) {
         if (this.ticks > 25) {
            this.dragon.getPhaseManager().setPhase(PhaseType.SITTING_ATTACKING);
         } else {
            Vec3d lv2 = new Vec3d(lv.getX() - this.dragon.getX(), 0.0, lv.getZ() - this.dragon.getZ()).normalize();
            Vec3d lv3 = new Vec3d(
                  (double)MathHelper.sin(this.dragon.yaw * (float) (Math.PI / 180.0)),
                  0.0,
                  (double)(-MathHelper.cos(this.dragon.yaw * (float) (Math.PI / 180.0)))
               )
               .normalize();
            float f = (float)lv3.dotProduct(lv2);
            float g = (float)(Math.acos((double)f) * 180.0F / (float)Math.PI) + 0.5F;
            if (g < 0.0F || g > 10.0F) {
               double d = lv.getX() - this.dragon.partHead.getX();
               double e = lv.getZ() - this.dragon.partHead.getZ();
               double h = MathHelper.clamp(
                  MathHelper.wrapDegrees(180.0 - MathHelper.atan2(d, e) * 180.0F / (float)Math.PI - (double)this.dragon.yaw), -100.0, 100.0
               );
               this.dragon.field_20865 *= 0.8F;
               float i = MathHelper.sqrt(d * d + e * e) + 1.0F;
               float j = i;
               if (i > 40.0F) {
                  i = 40.0F;
               }

               this.dragon.field_20865 = (float)((double)this.dragon.field_20865 + h * (double)(0.7F / i / j));
               this.dragon.yaw = this.dragon.yaw + this.dragon.field_20865;
            }
         }
      } else if (this.ticks >= 100) {
         lv = this.dragon.world.getClosestPlayer(PLAYER_WITHIN_RANGE_PREDICATE, this.dragon, this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
         this.dragon.getPhaseManager().setPhase(PhaseType.TAKEOFF);
         if (lv != null) {
            this.dragon.getPhaseManager().setPhase(PhaseType.CHARGING_PLAYER);
            this.dragon.getPhaseManager().create(PhaseType.CHARGING_PLAYER).setTarget(new Vec3d(lv.getX(), lv.getY(), lv.getZ()));
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
