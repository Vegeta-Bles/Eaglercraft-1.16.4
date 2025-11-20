package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class SittingFlamingPhase extends AbstractSittingPhase {
   private int ticks;
   private int field_7052;
   private AreaEffectCloudEntity field_7051;

   public SittingFlamingPhase(EnderDragonEntity _snowman) {
      super(_snowman);
   }

   @Override
   public void clientTick() {
      this.ticks++;
      if (this.ticks % 2 == 0 && this.ticks < 10) {
         Vec3d _snowman = this.dragon.method_6834(1.0F).normalize();
         _snowman.rotateY((float) (-Math.PI / 4));
         double _snowmanx = this.dragon.partHead.getX();
         double _snowmanxx = this.dragon.partHead.getBodyY(0.5);
         double _snowmanxxx = this.dragon.partHead.getZ();

         for (int _snowmanxxxx = 0; _snowmanxxxx < 8; _snowmanxxxx++) {
            double _snowmanxxxxx = _snowmanx + this.dragon.getRandom().nextGaussian() / 2.0;
            double _snowmanxxxxxx = _snowmanxx + this.dragon.getRandom().nextGaussian() / 2.0;
            double _snowmanxxxxxxx = _snowmanxxx + this.dragon.getRandom().nextGaussian() / 2.0;

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 6; _snowmanxxxxxxxx++) {
               this.dragon
                  .world
                  .addParticle(
                     ParticleTypes.DRAGON_BREATH, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, -_snowman.x * 0.08F * (double)_snowmanxxxxxxxx, -_snowman.y * 0.6F, -_snowman.z * 0.08F * (double)_snowmanxxxxxxxx
                  );
            }

            _snowman.rotateY((float) (Math.PI / 16));
         }
      }
   }

   @Override
   public void serverTick() {
      this.ticks++;
      if (this.ticks >= 200) {
         if (this.field_7052 >= 4) {
            this.dragon.getPhaseManager().setPhase(PhaseType.TAKEOFF);
         } else {
            this.dragon.getPhaseManager().setPhase(PhaseType.SITTING_SCANNING);
         }
      } else if (this.ticks == 10) {
         Vec3d _snowman = new Vec3d(this.dragon.partHead.getX() - this.dragon.getX(), 0.0, this.dragon.partHead.getZ() - this.dragon.getZ()).normalize();
         float _snowmanx = 5.0F;
         double _snowmanxx = this.dragon.partHead.getX() + _snowman.x * 5.0 / 2.0;
         double _snowmanxxx = this.dragon.partHead.getZ() + _snowman.z * 5.0 / 2.0;
         double _snowmanxxxx = this.dragon.partHead.getBodyY(0.5);
         double _snowmanxxxxx = _snowmanxxxx;
         BlockPos.Mutable _snowmanxxxxxx = new BlockPos.Mutable(_snowmanxx, _snowmanxxxx, _snowmanxxx);

         while (this.dragon.world.isAir(_snowmanxxxxxx)) {
            if (--_snowmanxxxxx < 0.0) {
               _snowmanxxxxx = _snowmanxxxx;
               break;
            }

            _snowmanxxxxxx.set(_snowmanxx, _snowmanxxxxx, _snowmanxxx);
         }

         _snowmanxxxxx = (double)(MathHelper.floor(_snowmanxxxxx) + 1);
         this.field_7051 = new AreaEffectCloudEntity(this.dragon.world, _snowmanxx, _snowmanxxxxx, _snowmanxxx);
         this.field_7051.setOwner(this.dragon);
         this.field_7051.setRadius(5.0F);
         this.field_7051.setDuration(200);
         this.field_7051.setParticleType(ParticleTypes.DRAGON_BREATH);
         this.field_7051.addEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE));
         this.dragon.world.spawnEntity(this.field_7051);
      }
   }

   @Override
   public void beginPhase() {
      this.ticks = 0;
      this.field_7052++;
   }

   @Override
   public void endPhase() {
      if (this.field_7051 != null) {
         this.field_7051.remove();
         this.field_7051 = null;
      }
   }

   @Override
   public PhaseType<SittingFlamingPhase> getType() {
      return PhaseType.SITTING_FLAMING;
   }

   public void method_6857() {
      this.field_7052 = 0;
   }
}
