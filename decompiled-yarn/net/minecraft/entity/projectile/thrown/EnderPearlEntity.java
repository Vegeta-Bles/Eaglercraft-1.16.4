package net.minecraft.entity.projectile.thrown;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class EnderPearlEntity extends ThrownItemEntity {
   public EnderPearlEntity(EntityType<? extends EnderPearlEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public EnderPearlEntity(World world, LivingEntity owner) {
      super(EntityType.ENDER_PEARL, owner, world);
   }

   public EnderPearlEntity(World world, double x, double y, double z) {
      super(EntityType.ENDER_PEARL, x, y, z, world);
   }

   @Override
   protected Item getDefaultItem() {
      return Items.ENDER_PEARL;
   }

   @Override
   protected void onEntityHit(EntityHitResult entityHitResult) {
      super.onEntityHit(entityHitResult);
      entityHitResult.getEntity().damage(DamageSource.thrownProjectile(this, this.getOwner()), 0.0F);
   }

   @Override
   protected void onCollision(HitResult hitResult) {
      super.onCollision(hitResult);
      Entity _snowman = this.getOwner();

      for (int _snowmanx = 0; _snowmanx < 32; _snowmanx++) {
         this.world
            .addParticle(
               ParticleTypes.PORTAL,
               this.getX(),
               this.getY() + this.random.nextDouble() * 2.0,
               this.getZ(),
               this.random.nextGaussian(),
               0.0,
               this.random.nextGaussian()
            );
      }

      if (!this.world.isClient && !this.removed) {
         if (_snowman instanceof ServerPlayerEntity) {
            ServerPlayerEntity _snowmanx = (ServerPlayerEntity)_snowman;
            if (_snowmanx.networkHandler.getConnection().isOpen() && _snowmanx.world == this.world && !_snowmanx.isSleeping()) {
               if (this.random.nextFloat() < 0.05F && this.world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
                  EndermiteEntity _snowmanxx = EntityType.ENDERMITE.create(this.world);
                  _snowmanxx.setPlayerSpawned(true);
                  _snowmanxx.refreshPositionAndAngles(_snowman.getX(), _snowman.getY(), _snowman.getZ(), _snowman.yaw, _snowman.pitch);
                  this.world.spawnEntity(_snowmanxx);
               }

               if (_snowman.hasVehicle()) {
                  _snowman.stopRiding();
               }

               _snowman.requestTeleport(this.getX(), this.getY(), this.getZ());
               _snowman.fallDistance = 0.0F;
               _snowman.damage(DamageSource.FALL, 5.0F);
            }
         } else if (_snowman != null) {
            _snowman.requestTeleport(this.getX(), this.getY(), this.getZ());
            _snowman.fallDistance = 0.0F;
         }

         this.remove();
      }
   }

   @Override
   public void tick() {
      Entity _snowman = this.getOwner();
      if (_snowman instanceof PlayerEntity && !_snowman.isAlive()) {
         this.remove();
      } else {
         super.tick();
      }
   }

   @Nullable
   @Override
   public Entity moveToWorld(ServerWorld destination) {
      Entity _snowman = this.getOwner();
      if (_snowman != null && _snowman.world.getRegistryKey() != destination.getRegistryKey()) {
         this.setOwner(null);
      }

      return super.moveToWorld(destination);
   }
}
