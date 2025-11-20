package net.minecraft.entity.projectile;

import javax.annotation.Nullable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TridentEntity extends PersistentProjectileEntity {
   private static final TrackedData<Byte> LOYALTY = DataTracker.registerData(TridentEntity.class, TrackedDataHandlerRegistry.BYTE);
   private static final TrackedData<Boolean> ENCHANTED = DataTracker.registerData(TridentEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private ItemStack tridentStack = new ItemStack(Items.TRIDENT);
   private boolean dealtDamage;
   public int returnTimer;

   public TridentEntity(EntityType<? extends TridentEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public TridentEntity(World world, LivingEntity owner, ItemStack stack) {
      super(EntityType.TRIDENT, owner, world);
      this.tridentStack = stack.copy();
      this.dataTracker.set(LOYALTY, (byte)EnchantmentHelper.getLoyalty(stack));
      this.dataTracker.set(ENCHANTED, stack.hasGlint());
   }

   public TridentEntity(World world, double x, double y, double z) {
      super(EntityType.TRIDENT, x, y, z, world);
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(LOYALTY, (byte)0);
      this.dataTracker.startTracking(ENCHANTED, false);
   }

   @Override
   public void tick() {
      if (this.inGroundTime > 4) {
         this.dealtDamage = true;
      }

      Entity _snowman = this.getOwner();
      if ((this.dealtDamage || this.isNoClip()) && _snowman != null) {
         int _snowmanx = this.dataTracker.get(LOYALTY);
         if (_snowmanx > 0 && !this.isOwnerAlive()) {
            if (!this.world.isClient && this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
               this.dropStack(this.asItemStack(), 0.1F);
            }

            this.remove();
         } else if (_snowmanx > 0) {
            this.setNoClip(true);
            Vec3d _snowmanxx = new Vec3d(_snowman.getX() - this.getX(), _snowman.getEyeY() - this.getY(), _snowman.getZ() - this.getZ());
            this.setPos(this.getX(), this.getY() + _snowmanxx.y * 0.015 * (double)_snowmanx, this.getZ());
            if (this.world.isClient) {
               this.lastRenderY = this.getY();
            }

            double _snowmanxxx = 0.05 * (double)_snowmanx;
            this.setVelocity(this.getVelocity().multiply(0.95).add(_snowmanxx.normalize().multiply(_snowmanxxx)));
            if (this.returnTimer == 0) {
               this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0F, 1.0F);
            }

            this.returnTimer++;
         }
      }

      super.tick();
   }

   private boolean isOwnerAlive() {
      Entity _snowman = this.getOwner();
      return _snowman == null || !_snowman.isAlive() ? false : !(_snowman instanceof ServerPlayerEntity) || !_snowman.isSpectator();
   }

   @Override
   protected ItemStack asItemStack() {
      return this.tridentStack.copy();
   }

   public boolean isEnchanted() {
      return this.dataTracker.get(ENCHANTED);
   }

   @Nullable
   @Override
   protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
      return this.dealtDamage ? null : super.getEntityCollision(currentPosition, nextPosition);
   }

   @Override
   protected void onEntityHit(EntityHitResult entityHitResult) {
      Entity _snowman = entityHitResult.getEntity();
      float _snowmanx = 8.0F;
      if (_snowman instanceof LivingEntity) {
         LivingEntity _snowmanxx = (LivingEntity)_snowman;
         _snowmanx += EnchantmentHelper.getAttackDamage(this.tridentStack, _snowmanxx.getGroup());
      }

      Entity _snowmanxx = this.getOwner();
      DamageSource _snowmanxxx = DamageSource.trident(this, (Entity)(_snowmanxx == null ? this : _snowmanxx));
      this.dealtDamage = true;
      SoundEvent _snowmanxxxx = SoundEvents.ITEM_TRIDENT_HIT;
      if (_snowman.damage(_snowmanxxx, _snowmanx)) {
         if (_snowman.getType() == EntityType.ENDERMAN) {
            return;
         }

         if (_snowman instanceof LivingEntity) {
            LivingEntity _snowmanxxxxx = (LivingEntity)_snowman;
            if (_snowmanxx instanceof LivingEntity) {
               EnchantmentHelper.onUserDamaged(_snowmanxxxxx, _snowmanxx);
               EnchantmentHelper.onTargetDamaged((LivingEntity)_snowmanxx, _snowmanxxxxx);
            }

            this.onHit(_snowmanxxxxx);
         }
      }

      this.setVelocity(this.getVelocity().multiply(-0.01, -0.1, -0.01));
      float _snowmanxxxxx = 1.0F;
      if (this.world instanceof ServerWorld && this.world.isThundering() && EnchantmentHelper.hasChanneling(this.tridentStack)) {
         BlockPos _snowmanxxxxxx = _snowman.getBlockPos();
         if (this.world.isSkyVisible(_snowmanxxxxxx)) {
            LightningEntity _snowmanxxxxxxx = EntityType.LIGHTNING_BOLT.create(this.world);
            _snowmanxxxxxxx.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(_snowmanxxxxxx));
            _snowmanxxxxxxx.setChanneler(_snowmanxx instanceof ServerPlayerEntity ? (ServerPlayerEntity)_snowmanxx : null);
            this.world.spawnEntity(_snowmanxxxxxxx);
            _snowmanxxxx = SoundEvents.ITEM_TRIDENT_THUNDER;
            _snowmanxxxxx = 5.0F;
         }
      }

      this.playSound(_snowmanxxxx, _snowmanxxxxx, 1.0F);
   }

   @Override
   protected SoundEvent getHitSound() {
      return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
   }

   @Override
   public void onPlayerCollision(PlayerEntity player) {
      Entity _snowman = this.getOwner();
      if (_snowman == null || _snowman.getUuid() == player.getUuid()) {
         super.onPlayerCollision(player);
      }
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      if (tag.contains("Trident", 10)) {
         this.tridentStack = ItemStack.fromTag(tag.getCompound("Trident"));
      }

      this.dealtDamage = tag.getBoolean("DealtDamage");
      this.dataTracker.set(LOYALTY, (byte)EnchantmentHelper.getLoyalty(this.tridentStack));
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.put("Trident", this.tridentStack.toTag(new CompoundTag()));
      tag.putBoolean("DealtDamage", this.dealtDamage);
   }

   @Override
   public void age() {
      int _snowman = this.dataTracker.get(LOYALTY);
      if (this.pickupType != PersistentProjectileEntity.PickupPermission.ALLOWED || _snowman <= 0) {
         super.age();
      }
   }

   @Override
   protected float getDragInWater() {
      return 0.99F;
   }

   @Override
   public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
      return true;
   }
}
