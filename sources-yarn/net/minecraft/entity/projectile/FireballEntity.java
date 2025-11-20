package net.minecraft.entity.projectile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

   public FireballEntity(EntityType<? extends FireballEntity> arg, World arg2) {
      super(arg, arg2);
   }

   @Environment(EnvType.CLIENT)
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
         boolean bl = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
         this.world
            .createExplosion(
               null,
               this.getX(),
               this.getY(),
               this.getZ(),
               (float)this.explosionPower,
               bl,
               bl ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE
            );
         this.remove();
      }
   }

   @Override
   protected void onEntityHit(EntityHitResult entityHitResult) {
      super.onEntityHit(entityHitResult);
      if (!this.world.isClient) {
         Entity lv = entityHitResult.getEntity();
         Entity lv2 = this.getOwner();
         lv.damage(DamageSource.fireball(this, lv2), 6.0F);
         if (lv2 instanceof LivingEntity) {
            this.dealDamage((LivingEntity)lv2, lv);
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
