package net.minecraft.entity;

import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemEntity extends Entity {
   private static final TrackedData<ItemStack> STACK = DataTracker.registerData(ItemEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
   private int age;
   private int pickupDelay;
   private int health = 5;
   private UUID thrower;
   private UUID owner;
   public final float hoverHeight;

   public ItemEntity(EntityType<? extends ItemEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.hoverHeight = (float)(Math.random() * Math.PI * 2.0);
   }

   public ItemEntity(World world, double x, double y, double z) {
      this(EntityType.ITEM, world);
      this.updatePosition(x, y, z);
      this.yaw = this.random.nextFloat() * 360.0F;
      this.setVelocity(this.random.nextDouble() * 0.2 - 0.1, 0.2, this.random.nextDouble() * 0.2 - 0.1);
   }

   public ItemEntity(World world, double x, double y, double z, ItemStack stack) {
      this(world, x, y, z);
      this.setStack(stack);
   }

   private ItemEntity(ItemEntity _snowman) {
      super(_snowman.getType(), _snowman.world);
      this.setStack(_snowman.getStack().copy());
      this.copyPositionAndRotation(_snowman);
      this.age = _snowman.age;
      this.hoverHeight = _snowman.hoverHeight;
   }

   @Override
   protected boolean canClimb() {
      return false;
   }

   @Override
   protected void initDataTracker() {
      this.getDataTracker().startTracking(STACK, ItemStack.EMPTY);
   }

   @Override
   public void tick() {
      if (this.getStack().isEmpty()) {
         this.remove();
      } else {
         super.tick();
         if (this.pickupDelay > 0 && this.pickupDelay != 32767) {
            this.pickupDelay--;
         }

         this.prevX = this.getX();
         this.prevY = this.getY();
         this.prevZ = this.getZ();
         Vec3d _snowman = this.getVelocity();
         float _snowmanx = this.getStandingEyeHeight() - 0.11111111F;
         if (this.isTouchingWater() && this.getFluidHeight(FluidTags.WATER) > (double)_snowmanx) {
            this.applyBuoyancy();
         } else if (this.isInLava() && this.getFluidHeight(FluidTags.LAVA) > (double)_snowmanx) {
            this.method_24348();
         } else if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
         }

         if (this.world.isClient) {
            this.noClip = false;
         } else {
            this.noClip = !this.world.isSpaceEmpty(this);
            if (this.noClip) {
               this.pushOutOfBlocks(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0, this.getZ());
            }
         }

         if (!this.onGround || squaredHorizontalLength(this.getVelocity()) > 1.0E-5F || (this.age + this.getEntityId()) % 4 == 0) {
            this.move(MovementType.SELF, this.getVelocity());
            float _snowmanxx = 0.98F;
            if (this.onGround) {
               _snowmanxx = this.world.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0, this.getZ())).getBlock().getSlipperiness() * 0.98F;
            }

            this.setVelocity(this.getVelocity().multiply((double)_snowmanxx, 0.98, (double)_snowmanxx));
            if (this.onGround) {
               Vec3d _snowmanxxx = this.getVelocity();
               if (_snowmanxxx.y < 0.0) {
                  this.setVelocity(_snowmanxxx.multiply(1.0, -0.5, 1.0));
               }
            }
         }

         boolean _snowmanxxx = MathHelper.floor(this.prevX) != MathHelper.floor(this.getX())
            || MathHelper.floor(this.prevY) != MathHelper.floor(this.getY())
            || MathHelper.floor(this.prevZ) != MathHelper.floor(this.getZ());
         int _snowmanxxxx = _snowmanxxx ? 2 : 40;
         if (this.age % _snowmanxxxx == 0) {
            if (this.world.getFluidState(this.getBlockPos()).isIn(FluidTags.LAVA) && !this.isFireImmune()) {
               this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
            }

            if (!this.world.isClient && this.canMerge()) {
               this.tryMerge();
            }
         }

         if (this.age != -32768) {
            this.age++;
         }

         this.velocityDirty = this.velocityDirty | this.updateWaterState();
         if (!this.world.isClient) {
            double _snowmanxxxxx = this.getVelocity().subtract(_snowman).lengthSquared();
            if (_snowmanxxxxx > 0.01) {
               this.velocityDirty = true;
            }
         }

         if (!this.world.isClient && this.age >= 6000) {
            this.remove();
         }
      }
   }

   private void applyBuoyancy() {
      Vec3d _snowman = this.getVelocity();
      this.setVelocity(_snowman.x * 0.99F, _snowman.y + (double)(_snowman.y < 0.06F ? 5.0E-4F : 0.0F), _snowman.z * 0.99F);
   }

   private void method_24348() {
      Vec3d _snowman = this.getVelocity();
      this.setVelocity(_snowman.x * 0.95F, _snowman.y + (double)(_snowman.y < 0.06F ? 5.0E-4F : 0.0F), _snowman.z * 0.95F);
   }

   private void tryMerge() {
      if (this.canMerge()) {
         for (ItemEntity _snowman : this.world.getEntitiesByClass(ItemEntity.class, this.getBoundingBox().expand(0.5, 0.0, 0.5), _snowmanx -> _snowmanx != this && _snowmanx.canMerge())) {
            if (_snowman.canMerge()) {
               this.tryMerge(_snowman);
               if (this.removed) {
                  break;
               }
            }
         }
      }
   }

   private boolean canMerge() {
      ItemStack _snowman = this.getStack();
      return this.isAlive() && this.pickupDelay != 32767 && this.age != -32768 && this.age < 6000 && _snowman.getCount() < _snowman.getMaxCount();
   }

   private void tryMerge(ItemEntity other) {
      ItemStack _snowman = this.getStack();
      ItemStack _snowmanx = other.getStack();
      if (Objects.equals(this.getOwner(), other.getOwner()) && canMerge(_snowman, _snowmanx)) {
         if (_snowmanx.getCount() < _snowman.getCount()) {
            merge(this, _snowman, other, _snowmanx);
         } else {
            merge(other, _snowmanx, this, _snowman);
         }
      }
   }

   public static boolean canMerge(ItemStack stack1, ItemStack stack2) {
      if (stack2.getItem() != stack1.getItem()) {
         return false;
      } else if (stack2.getCount() + stack1.getCount() > stack2.getMaxCount()) {
         return false;
      } else {
         return stack2.hasTag() ^ stack1.hasTag() ? false : !stack2.hasTag() || stack2.getTag().equals(stack1.getTag());
      }
   }

   public static ItemStack merge(ItemStack stack1, ItemStack stack2, int maxCount) {
      int _snowman = Math.min(Math.min(stack1.getMaxCount(), maxCount) - stack1.getCount(), stack2.getCount());
      ItemStack _snowmanx = stack1.copy();
      _snowmanx.increment(_snowman);
      stack2.decrement(_snowman);
      return _snowmanx;
   }

   private static void merge(ItemEntity targetEntity, ItemStack stack1, ItemStack stack2) {
      ItemStack _snowman = merge(stack1, stack2, 64);
      targetEntity.setStack(_snowman);
   }

   private static void merge(ItemEntity targetEntity, ItemStack targetStack, ItemEntity sourceEntity, ItemStack sourceStack) {
      merge(targetEntity, targetStack, sourceStack);
      targetEntity.pickupDelay = Math.max(targetEntity.pickupDelay, sourceEntity.pickupDelay);
      targetEntity.age = Math.min(targetEntity.age, sourceEntity.age);
      if (sourceStack.isEmpty()) {
         sourceEntity.remove();
      }
   }

   @Override
   public boolean isFireImmune() {
      return this.getStack().getItem().isFireproof() || super.isFireImmune();
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else if (!this.getStack().isEmpty() && this.getStack().getItem() == Items.NETHER_STAR && source.isExplosive()) {
         return false;
      } else if (!this.getStack().getItem().damage(source)) {
         return false;
      } else {
         this.scheduleVelocityUpdate();
         this.health = (int)((float)this.health - amount);
         if (this.health <= 0) {
            this.remove();
         }

         return false;
      }
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      tag.putShort("Health", (short)this.health);
      tag.putShort("Age", (short)this.age);
      tag.putShort("PickupDelay", (short)this.pickupDelay);
      if (this.getThrower() != null) {
         tag.putUuid("Thrower", this.getThrower());
      }

      if (this.getOwner() != null) {
         tag.putUuid("Owner", this.getOwner());
      }

      if (!this.getStack().isEmpty()) {
         tag.put("Item", this.getStack().toTag(new CompoundTag()));
      }
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      this.health = tag.getShort("Health");
      this.age = tag.getShort("Age");
      if (tag.contains("PickupDelay")) {
         this.pickupDelay = tag.getShort("PickupDelay");
      }

      if (tag.containsUuid("Owner")) {
         this.owner = tag.getUuid("Owner");
      }

      if (tag.containsUuid("Thrower")) {
         this.thrower = tag.getUuid("Thrower");
      }

      CompoundTag _snowman = tag.getCompound("Item");
      this.setStack(ItemStack.fromTag(_snowman));
      if (this.getStack().isEmpty()) {
         this.remove();
      }
   }

   @Override
   public void onPlayerCollision(PlayerEntity player) {
      if (!this.world.isClient) {
         ItemStack _snowman = this.getStack();
         Item _snowmanx = _snowman.getItem();
         int _snowmanxx = _snowman.getCount();
         if (this.pickupDelay == 0 && (this.owner == null || this.owner.equals(player.getUuid())) && player.inventory.insertStack(_snowman)) {
            player.sendPickup(this, _snowmanxx);
            if (_snowman.isEmpty()) {
               this.remove();
               _snowman.setCount(_snowmanxx);
            }

            player.increaseStat(Stats.PICKED_UP.getOrCreateStat(_snowmanx), _snowmanxx);
            player.method_29499(this);
         }
      }
   }

   @Override
   public Text getName() {
      Text _snowman = this.getCustomName();
      return (Text)(_snowman != null ? _snowman : new TranslatableText(this.getStack().getTranslationKey()));
   }

   @Override
   public boolean isAttackable() {
      return false;
   }

   @Nullable
   @Override
   public Entity moveToWorld(ServerWorld destination) {
      Entity _snowman = super.moveToWorld(destination);
      if (!this.world.isClient && _snowman instanceof ItemEntity) {
         ((ItemEntity)_snowman).tryMerge();
      }

      return _snowman;
   }

   public ItemStack getStack() {
      return this.getDataTracker().get(STACK);
   }

   public void setStack(ItemStack stack) {
      this.getDataTracker().set(STACK, stack);
   }

   @Override
   public void onTrackedDataSet(TrackedData<?> data) {
      super.onTrackedDataSet(data);
      if (STACK.equals(data)) {
         this.getStack().setHolder(this);
      }
   }

   @Nullable
   public UUID getOwner() {
      return this.owner;
   }

   public void setOwner(@Nullable UUID uuid) {
      this.owner = uuid;
   }

   @Nullable
   public UUID getThrower() {
      return this.thrower;
   }

   public void setThrower(@Nullable UUID uuid) {
      this.thrower = uuid;
   }

   public int getAge() {
      return this.age;
   }

   public void setToDefaultPickupDelay() {
      this.pickupDelay = 10;
   }

   public void resetPickupDelay() {
      this.pickupDelay = 0;
   }

   public void setPickupDelayInfinite() {
      this.pickupDelay = 32767;
   }

   public void setPickupDelay(int pickupDelay) {
      this.pickupDelay = pickupDelay;
   }

   public boolean cannotPickup() {
      return this.pickupDelay > 0;
   }

   public void setCovetedItem() {
      this.age = -6000;
   }

   public void setDespawnImmediately() {
      this.setPickupDelayInfinite();
      this.age = 5999;
   }

   public float method_27314(float _snowman) {
      return ((float)this.getAge() + _snowman) / 20.0F + this.hoverHeight;
   }

   @Override
   public Packet<?> createSpawnPacket() {
      return new EntitySpawnS2CPacket(this);
   }

   public ItemEntity method_29271() {
      return new ItemEntity(this);
   }
}
