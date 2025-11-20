package net.minecraft.entity.projectile;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class SmallFireballEntity extends AbstractFireballEntity {
   public SmallFireballEntity(EntityType<? extends SmallFireballEntity> arg, World arg2) {
      super(arg, arg2);
   }

   public SmallFireballEntity(World world, LivingEntity owner, double velocityX, double velocityY, double velocityZ) {
      super(EntityType.SMALL_FIREBALL, owner, velocityX, velocityY, velocityZ, world);
   }

   public SmallFireballEntity(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      super(EntityType.SMALL_FIREBALL, x, y, z, velocityX, velocityY, velocityZ, world);
   }

   @Override
   protected void onEntityHit(EntityHitResult entityHitResult) {
      super.onEntityHit(entityHitResult);
      if (!this.world.isClient) {
         Entity lv = entityHitResult.getEntity();
         if (!lv.isFireImmune()) {
            Entity lv2 = this.getOwner();
            int i = lv.getFireTicks();
            lv.setOnFireFor(5);
            boolean bl = lv.damage(DamageSource.fireball(this, lv2), 5.0F);
            if (!bl) {
               lv.setFireTicks(i);
            } else if (lv2 instanceof LivingEntity) {
               this.dealDamage((LivingEntity)lv2, lv);
            }
         }
      }
   }

   @Override
   protected void onBlockHit(BlockHitResult blockHitResult) {
      super.onBlockHit(blockHitResult);
      if (!this.world.isClient) {
         Entity lv = this.getOwner();
         if (lv == null || !(lv instanceof MobEntity) || this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            BlockPos lv3 = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
            if (this.world.isAir(lv3)) {
               this.world.setBlockState(lv3, AbstractFireBlock.getState(this.world, lv3));
            }
         }
      }
   }

   @Override
   protected void onCollision(HitResult hitResult) {
      super.onCollision(hitResult);
      if (!this.world.isClient) {
         this.remove();
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
}
