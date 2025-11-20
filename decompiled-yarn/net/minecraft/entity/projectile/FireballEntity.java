package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class FireballEntity extends AbstractFireballEntity {
   public int explosionPower = 1;

   public FireballEntity(EntityType<? extends FireballEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public FireballEntity(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      super(EntityType.FIREBALL, x, y, z, velocityX, velocityY, velocityZ, world);
   }

   public FireballEntity(World world, LivingEntity owner, double velocityX, double velocityY, double velocityZ) {
      super(EntityType.FIREBALL, owner, velocityX, velocityY, velocityZ, world);
   }

   @Override
   protected void onCollision(HitResult hitResult) {
      super.onCollision(hitResult);
      if (!this.world.isClient) {
         boolean _snowman = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
         this.world
            .createExplosion(
               null,
               this.getX(),
               this.getY(),
               this.getZ(),
               (float)this.explosionPower,
               _snowman,
               _snowman ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE
            );
         this.remove();
      }
   }

   @Override
   protected void onEntityHit(EntityHitResult entityHitResult) {
      super.onEntityHit(entityHitResult);
      if (!this.world.isClient) {
         Entity _snowman = entityHitResult.getEntity();
         Entity _snowmanx = this.getOwner();
         _snowman.damage(DamageSource.fireball(this, _snowmanx), 6.0F);
         if (_snowmanx instanceof LivingEntity) {
            this.dealDamage((LivingEntity)_snowmanx, _snowman);
         }
      }
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("ExplosionPower", this.explosionPower);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      if (tag.contains("ExplosionPower", 99)) {
         this.explosionPower = tag.getInt("ExplosionPower");
      }
   }
}
