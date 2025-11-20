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
   public SmallFireballEntity(EntityType<? extends SmallFireballEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
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
         Entity _snowman = entityHitResult.getEntity();
         if (!_snowman.isFireImmune()) {
            Entity _snowmanx = this.getOwner();
            int _snowmanxx = _snowman.getFireTicks();
            _snowman.setOnFireFor(5);
            boolean _snowmanxxx = _snowman.damage(DamageSource.fireball(this, _snowmanx), 5.0F);
            if (!_snowmanxxx) {
               _snowman.setFireTicks(_snowmanxx);
            } else if (_snowmanx instanceof LivingEntity) {
               this.dealDamage((LivingEntity)_snowmanx, _snowman);
            }
         }
      }
   }

   @Override
   protected void onBlockHit(BlockHitResult blockHitResult) {
      super.onBlockHit(blockHitResult);
      if (!this.world.isClient) {
         Entity _snowman = this.getOwner();
         if (_snowman == null || !(_snowman instanceof MobEntity) || this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            BlockPos _snowmanx = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
            if (this.world.isAir(_snowmanx)) {
               this.world.setBlockState(_snowmanx, AbstractFireBlock.getState(this.world, _snowmanx));
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
