package net.minecraft.entity.projectile.thrown;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class SnowballEntity extends ThrownItemEntity {
   public SnowballEntity(EntityType<? extends SnowballEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public SnowballEntity(World world, LivingEntity owner) {
      super(EntityType.SNOWBALL, owner, world);
   }

   public SnowballEntity(World world, double x, double y, double z) {
      super(EntityType.SNOWBALL, x, y, z, world);
   }

   @Override
   protected Item getDefaultItem() {
      return Items.SNOWBALL;
   }

   private ParticleEffect getParticleParameters() {
      ItemStack _snowman = this.getItem();
      return (ParticleEffect)(_snowman.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, _snowman));
   }

   @Override
   public void handleStatus(byte status) {
      if (status == 3) {
         ParticleEffect _snowman = this.getParticleParameters();

         for (int _snowmanx = 0; _snowmanx < 8; _snowmanx++) {
            this.world.addParticle(_snowman, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   protected void onEntityHit(EntityHitResult entityHitResult) {
      super.onEntityHit(entityHitResult);
      Entity _snowman = entityHitResult.getEntity();
      int _snowmanx = _snowman instanceof BlazeEntity ? 3 : 0;
      _snowman.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float)_snowmanx);
   }

   @Override
   protected void onCollision(HitResult hitResult) {
      super.onCollision(hitResult);
      if (!this.world.isClient) {
         this.world.sendEntityStatus(this, (byte)3);
         this.remove();
      }
   }
}
