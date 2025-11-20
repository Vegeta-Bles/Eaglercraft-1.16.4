package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class DragonFireballEntity extends ExplosiveProjectileEntity {
   public DragonFireballEntity(EntityType<? extends DragonFireballEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public DragonFireballEntity(World world, double x, double y, double z, double directionX, double directionY, double directionZ) {
      super(EntityType.DRAGON_FIREBALL, x, y, z, directionX, directionY, directionZ, world);
   }

   public DragonFireballEntity(World world, LivingEntity owner, double directionX, double directionY, double directionZ) {
      super(EntityType.DRAGON_FIREBALL, owner, directionX, directionY, directionZ, world);
   }

   @Override
   protected void onCollision(HitResult hitResult) {
      super.onCollision(hitResult);
      Entity _snowman = this.getOwner();
      if (hitResult.getType() != HitResult.Type.ENTITY || !((EntityHitResult)hitResult).getEntity().isPartOf(_snowman)) {
         if (!this.world.isClient) {
            List<LivingEntity> _snowmanx = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(4.0, 2.0, 4.0));
            AreaEffectCloudEntity _snowmanxx = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
            if (_snowman instanceof LivingEntity) {
               _snowmanxx.setOwner((LivingEntity)_snowman);
            }

            _snowmanxx.setParticleType(ParticleTypes.DRAGON_BREATH);
            _snowmanxx.setRadius(3.0F);
            _snowmanxx.setDuration(600);
            _snowmanxx.setRadiusGrowth((7.0F - _snowmanxx.getRadius()) / (float)_snowmanxx.getDuration());
            _snowmanxx.addEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 1));
            if (!_snowmanx.isEmpty()) {
               for (LivingEntity _snowmanxxx : _snowmanx) {
                  double _snowmanxxxx = this.squaredDistanceTo(_snowmanxxx);
                  if (_snowmanxxxx < 16.0) {
                     _snowmanxx.updatePosition(_snowmanxxx.getX(), _snowmanxxx.getY(), _snowmanxxx.getZ());
                     break;
                  }
               }
            }

            this.world.syncWorldEvent(2006, this.getBlockPos(), this.isSilent() ? -1 : 1);
            this.world.spawnEntity(_snowmanxx);
            this.remove();
         }
      }
   }

   @Override
   public boolean collides() {
      return false;
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      return false;
   }

   @Override
   protected ParticleEffect getParticleType() {
      return ParticleTypes.DRAGON_BREATH;
   }

   @Override
   protected boolean isBurning() {
      return false;
   }
}
