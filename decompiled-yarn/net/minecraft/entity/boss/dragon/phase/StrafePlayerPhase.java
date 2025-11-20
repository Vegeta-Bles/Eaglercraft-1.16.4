package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StrafePlayerPhase extends AbstractPhase {
   private static final Logger LOGGER = LogManager.getLogger();
   private int field_7060;
   private Path field_7059;
   private Vec3d target;
   private LivingEntity field_7062;
   private boolean field_7058;

   public StrafePlayerPhase(EnderDragonEntity _snowman) {
      super(_snowman);
   }

   @Override
   public void serverTick() {
      if (this.field_7062 == null) {
         LOGGER.warn("Skipping player strafe phase because no player was found");
         this.dragon.getPhaseManager().setPhase(PhaseType.HOLDING_PATTERN);
      } else {
         if (this.field_7059 != null && this.field_7059.isFinished()) {
            double _snowman = this.field_7062.getX();
            double _snowmanx = this.field_7062.getZ();
            double _snowmanxx = _snowman - this.dragon.getX();
            double _snowmanxxx = _snowmanx - this.dragon.getZ();
            double _snowmanxxxx = (double)MathHelper.sqrt(_snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx);
            double _snowmanxxxxx = Math.min(0.4F + _snowmanxxxx / 80.0 - 1.0, 10.0);
            this.target = new Vec3d(_snowman, this.field_7062.getY() + _snowmanxxxxx, _snowmanx);
         }

         double _snowman = this.target == null ? 0.0 : this.target.squaredDistanceTo(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
         if (_snowman < 100.0 || _snowman > 22500.0) {
            this.method_6860();
         }

         double _snowmanx = 64.0;
         if (this.field_7062.squaredDistanceTo(this.dragon) < 4096.0) {
            if (this.dragon.canSee(this.field_7062)) {
               this.field_7060++;
               Vec3d _snowmanxx = new Vec3d(this.field_7062.getX() - this.dragon.getX(), 0.0, this.field_7062.getZ() - this.dragon.getZ()).normalize();
               Vec3d _snowmanxxx = new Vec3d(
                     (double)MathHelper.sin(this.dragon.yaw * (float) (Math.PI / 180.0)),
                     0.0,
                     (double)(-MathHelper.cos(this.dragon.yaw * (float) (Math.PI / 180.0)))
                  )
                  .normalize();
               float _snowmanxxxx = (float)_snowmanxxx.dotProduct(_snowmanxx);
               float _snowmanxxxxx = (float)(Math.acos((double)_snowmanxxxx) * 180.0F / (float)Math.PI);
               _snowmanxxxxx += 0.5F;
               if (this.field_7060 >= 5 && _snowmanxxxxx >= 0.0F && _snowmanxxxxx < 10.0F) {
                  double _snowmanxxxxxx = 1.0;
                  Vec3d _snowmanxxxxxxx = this.dragon.getRotationVec(1.0F);
                  double _snowmanxxxxxxxx = this.dragon.partHead.getX() - _snowmanxxxxxxx.x * 1.0;
                  double _snowmanxxxxxxxxx = this.dragon.partHead.getBodyY(0.5) + 0.5;
                  double _snowmanxxxxxxxxxx = this.dragon.partHead.getZ() - _snowmanxxxxxxx.z * 1.0;
                  double _snowmanxxxxxxxxxxx = this.field_7062.getX() - _snowmanxxxxxxxx;
                  double _snowmanxxxxxxxxxxxx = this.field_7062.getBodyY(0.5) - _snowmanxxxxxxxxx;
                  double _snowmanxxxxxxxxxxxxx = this.field_7062.getZ() - _snowmanxxxxxxxxxx;
                  if (!this.dragon.isSilent()) {
                     this.dragon.world.syncWorldEvent(null, 1017, this.dragon.getBlockPos(), 0);
                  }

                  DragonFireballEntity _snowmanxxxxxxxxxxxxxx = new DragonFireballEntity(this.dragon.world, this.dragon, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
                  _snowmanxxxxxxxxxxxxxx.refreshPositionAndAngles(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, 0.0F, 0.0F);
                  this.dragon.world.spawnEntity(_snowmanxxxxxxxxxxxxxx);
                  this.field_7060 = 0;
                  if (this.field_7059 != null) {
                     while (!this.field_7059.isFinished()) {
                        this.field_7059.next();
                     }
                  }

                  this.dragon.getPhaseManager().setPhase(PhaseType.HOLDING_PATTERN);
               }
            } else if (this.field_7060 > 0) {
               this.field_7060--;
            }
         } else if (this.field_7060 > 0) {
            this.field_7060--;
         }
      }
   }

   private void method_6860() {
      if (this.field_7059 == null || this.field_7059.isFinished()) {
         int _snowman = this.dragon.getNearestPathNodeIndex();
         int _snowmanx = _snowman;
         if (this.dragon.getRandom().nextInt(8) == 0) {
            this.field_7058 = !this.field_7058;
            _snowmanx = _snowman + 6;
         }

         if (this.field_7058) {
            _snowmanx++;
         } else {
            _snowmanx--;
         }

         if (this.dragon.getFight() != null && this.dragon.getFight().getAliveEndCrystals() > 0) {
            _snowmanx %= 12;
            if (_snowmanx < 0) {
               _snowmanx += 12;
            }
         } else {
            _snowmanx -= 12;
            _snowmanx &= 7;
            _snowmanx += 12;
         }

         this.field_7059 = this.dragon.findPath(_snowman, _snowmanx, null);
         if (this.field_7059 != null) {
            this.field_7059.next();
         }
      }

      this.method_6861();
   }

   private void method_6861() {
      if (this.field_7059 != null && !this.field_7059.isFinished()) {
         Vec3i _snowman = this.field_7059.method_31032();
         this.field_7059.next();
         double _snowmanx = (double)_snowman.getX();
         double _snowmanxx = (double)_snowman.getZ();

         double _snowmanxxx;
         do {
            _snowmanxxx = (double)((float)_snowman.getY() + this.dragon.getRandom().nextFloat() * 20.0F);
         } while (_snowmanxxx < (double)_snowman.getY());

         this.target = new Vec3d(_snowmanx, _snowmanxxx, _snowmanxx);
      }
   }

   @Override
   public void beginPhase() {
      this.field_7060 = 0;
      this.target = null;
      this.field_7059 = null;
      this.field_7062 = null;
   }

   public void method_6862(LivingEntity _snowman) {
      this.field_7062 = _snowman;
      int _snowmanx = this.dragon.getNearestPathNodeIndex();
      int _snowmanxx = this.dragon.getNearestPathNodeIndex(this.field_7062.getX(), this.field_7062.getY(), this.field_7062.getZ());
      int _snowmanxxx = MathHelper.floor(this.field_7062.getX());
      int _snowmanxxxx = MathHelper.floor(this.field_7062.getZ());
      double _snowmanxxxxx = (double)_snowmanxxx - this.dragon.getX();
      double _snowmanxxxxxx = (double)_snowmanxxxx - this.dragon.getZ();
      double _snowmanxxxxxxx = (double)MathHelper.sqrt(_snowmanxxxxx * _snowmanxxxxx + _snowmanxxxxxx * _snowmanxxxxxx);
      double _snowmanxxxxxxxx = Math.min(0.4F + _snowmanxxxxxxx / 80.0 - 1.0, 10.0);
      int _snowmanxxxxxxxxx = MathHelper.floor(this.field_7062.getY() + _snowmanxxxxxxxx);
      PathNode _snowmanxxxxxxxxxx = new PathNode(_snowmanxxx, _snowmanxxxxxxxxx, _snowmanxxxx);
      this.field_7059 = this.dragon.findPath(_snowmanx, _snowmanxx, _snowmanxxxxxxxxxx);
      if (this.field_7059 != null) {
         this.field_7059.next();
         this.method_6861();
      }
   }

   @Nullable
   @Override
   public Vec3d getTarget() {
      return this.target;
   }

   @Override
   public PhaseType<StrafePlayerPhase> getType() {
      return PhaseType.STRAFE_PLAYER;
   }
}
