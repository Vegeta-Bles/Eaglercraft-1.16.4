package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;

public class HoldingPatternPhase extends AbstractPhase {
   private static final TargetPredicate PLAYERS_IN_RANGE_PREDICATE = new TargetPredicate().setBaseMaxDistance(64.0);
   private Path field_7043;
   private Vec3d target;
   private boolean field_7044;

   public HoldingPatternPhase(EnderDragonEntity _snowman) {
      super(_snowman);
   }

   @Override
   public PhaseType<HoldingPatternPhase> getType() {
      return PhaseType.HOLDING_PATTERN;
   }

   @Override
   public void serverTick() {
      double _snowman = this.target == null ? 0.0 : this.target.squaredDistanceTo(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
      if (_snowman < 100.0 || _snowman > 22500.0 || this.dragon.horizontalCollision || this.dragon.verticalCollision) {
         this.method_6841();
      }
   }

   @Override
   public void beginPhase() {
      this.field_7043 = null;
      this.target = null;
   }

   @Nullable
   @Override
   public Vec3d getTarget() {
      return this.target;
   }

   private void method_6841() {
      if (this.field_7043 != null && this.field_7043.isFinished()) {
         BlockPos _snowman = this.dragon.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(EndPortalFeature.ORIGIN));
         int _snowmanx = this.dragon.getFight() == null ? 0 : this.dragon.getFight().getAliveEndCrystals();
         if (this.dragon.getRandom().nextInt(_snowmanx + 3) == 0) {
            this.dragon.getPhaseManager().setPhase(PhaseType.LANDING_APPROACH);
            return;
         }

         double _snowmanxx = 64.0;
         PlayerEntity _snowmanxxx = this.dragon.world.getClosestPlayer(PLAYERS_IN_RANGE_PREDICATE, (double)_snowman.getX(), (double)_snowman.getY(), (double)_snowman.getZ());
         if (_snowmanxxx != null) {
            _snowmanxx = _snowman.getSquaredDistance(_snowmanxxx.getPos(), true) / 512.0;
         }

         if (_snowmanxxx != null
            && !_snowmanxxx.abilities.invulnerable
            && (this.dragon.getRandom().nextInt(MathHelper.abs((int)_snowmanxx) + 2) == 0 || this.dragon.getRandom().nextInt(_snowmanx + 2) == 0)) {
            this.method_6843(_snowmanxxx);
            return;
         }
      }

      if (this.field_7043 == null || this.field_7043.isFinished()) {
         int _snowmanxxxx = this.dragon.getNearestPathNodeIndex();
         int _snowmanxxxxx = _snowmanxxxx;
         if (this.dragon.getRandom().nextInt(8) == 0) {
            this.field_7044 = !this.field_7044;
            _snowmanxxxxx = _snowmanxxxx + 6;
         }

         if (this.field_7044) {
            _snowmanxxxxx++;
         } else {
            _snowmanxxxxx--;
         }

         if (this.dragon.getFight() != null && this.dragon.getFight().getAliveEndCrystals() >= 0) {
            _snowmanxxxxx %= 12;
            if (_snowmanxxxxx < 0) {
               _snowmanxxxxx += 12;
            }
         } else {
            _snowmanxxxxx -= 12;
            _snowmanxxxxx &= 7;
            _snowmanxxxxx += 12;
         }

         this.field_7043 = this.dragon.findPath(_snowmanxxxx, _snowmanxxxxx, null);
         if (this.field_7043 != null) {
            this.field_7043.next();
         }
      }

      this.method_6842();
   }

   private void method_6843(PlayerEntity _snowman) {
      this.dragon.getPhaseManager().setPhase(PhaseType.STRAFE_PLAYER);
      this.dragon.getPhaseManager().create(PhaseType.STRAFE_PLAYER).method_6862(_snowman);
   }

   private void method_6842() {
      if (this.field_7043 != null && !this.field_7043.isFinished()) {
         Vec3i _snowman = this.field_7043.method_31032();
         this.field_7043.next();
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
   public void crystalDestroyed(EndCrystalEntity crystal, BlockPos pos, DamageSource source, @Nullable PlayerEntity player) {
      if (player != null && !player.abilities.invulnerable) {
         this.method_6843(player);
      }
   }
}
