package net.minecraft.entity.decoration;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.EulerAngle;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class ArmorStandEntity extends LivingEntity {
   private static final EulerAngle DEFAULT_HEAD_ROTATION = new EulerAngle(0.0F, 0.0F, 0.0F);
   private static final EulerAngle DEFAULT_BODY_ROTATION = new EulerAngle(0.0F, 0.0F, 0.0F);
   private static final EulerAngle DEFAULT_LEFT_ARM_ROTATION = new EulerAngle(-10.0F, 0.0F, -10.0F);
   private static final EulerAngle DEFAULT_RIGHT_ARM_ROTATION = new EulerAngle(-15.0F, 0.0F, 10.0F);
   private static final EulerAngle DEFAULT_LEFT_LEG_ROTATION = new EulerAngle(-1.0F, 0.0F, -1.0F);
   private static final EulerAngle DEFAULT_RIGHT_LEG_ROTATION = new EulerAngle(1.0F, 0.0F, 1.0F);
   private static final EntityDimensions field_26745 = new EntityDimensions(0.0F, 0.0F, true);
   private static final EntityDimensions field_26746 = EntityType.ARMOR_STAND.getDimensions().scaled(0.5F);
   public static final TrackedData<Byte> ARMOR_STAND_FLAGS = DataTracker.registerData(ArmorStandEntity.class, TrackedDataHandlerRegistry.BYTE);
   public static final TrackedData<EulerAngle> TRACKER_HEAD_ROTATION = DataTracker.registerData(ArmorStandEntity.class, TrackedDataHandlerRegistry.ROTATION);
   public static final TrackedData<EulerAngle> TRACKER_BODY_ROTATION = DataTracker.registerData(ArmorStandEntity.class, TrackedDataHandlerRegistry.ROTATION);
   public static final TrackedData<EulerAngle> TRACKER_LEFT_ARM_ROTATION = DataTracker.registerData(ArmorStandEntity.class, TrackedDataHandlerRegistry.ROTATION);
   public static final TrackedData<EulerAngle> TRACKER_RIGHT_ARM_ROTATION = DataTracker.registerData(
      ArmorStandEntity.class, TrackedDataHandlerRegistry.ROTATION
   );
   public static final TrackedData<EulerAngle> TRACKER_LEFT_LEG_ROTATION = DataTracker.registerData(ArmorStandEntity.class, TrackedDataHandlerRegistry.ROTATION);
   public static final TrackedData<EulerAngle> TRACKER_RIGHT_LEG_ROTATION = DataTracker.registerData(
      ArmorStandEntity.class, TrackedDataHandlerRegistry.ROTATION
   );
   private static final Predicate<Entity> RIDEABLE_MINECART_PREDICATE = _snowman -> _snowman instanceof AbstractMinecartEntity
         && ((AbstractMinecartEntity)_snowman).getMinecartType() == AbstractMinecartEntity.Type.RIDEABLE;
   private final DefaultedList<ItemStack> heldItems = DefaultedList.ofSize(2, ItemStack.EMPTY);
   private final DefaultedList<ItemStack> armorItems = DefaultedList.ofSize(4, ItemStack.EMPTY);
   private boolean invisible;
   public long lastHitTime;
   private int disabledSlots;
   private EulerAngle headRotation = DEFAULT_HEAD_ROTATION;
   private EulerAngle bodyRotation = DEFAULT_BODY_ROTATION;
   private EulerAngle leftArmRotation = DEFAULT_LEFT_ARM_ROTATION;
   private EulerAngle rightArmRotation = DEFAULT_RIGHT_ARM_ROTATION;
   private EulerAngle leftLegRotation = DEFAULT_LEFT_LEG_ROTATION;
   private EulerAngle rightLegRotation = DEFAULT_RIGHT_LEG_ROTATION;

   public ArmorStandEntity(EntityType<? extends ArmorStandEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.stepHeight = 0.0F;
   }

   public ArmorStandEntity(World world, double x, double y, double z) {
      this(EntityType.ARMOR_STAND, world);
      this.updatePosition(x, y, z);
   }

   @Override
   public void calculateDimensions() {
      double _snowman = this.getX();
      double _snowmanx = this.getY();
      double _snowmanxx = this.getZ();
      super.calculateDimensions();
      this.updatePosition(_snowman, _snowmanx, _snowmanxx);
   }

   private boolean canClip() {
      return !this.isMarker() && !this.hasNoGravity();
   }

   @Override
   public boolean canMoveVoluntarily() {
      return super.canMoveVoluntarily() && this.canClip();
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(ARMOR_STAND_FLAGS, (byte)0);
      this.dataTracker.startTracking(TRACKER_HEAD_ROTATION, DEFAULT_HEAD_ROTATION);
      this.dataTracker.startTracking(TRACKER_BODY_ROTATION, DEFAULT_BODY_ROTATION);
      this.dataTracker.startTracking(TRACKER_LEFT_ARM_ROTATION, DEFAULT_LEFT_ARM_ROTATION);
      this.dataTracker.startTracking(TRACKER_RIGHT_ARM_ROTATION, DEFAULT_RIGHT_ARM_ROTATION);
      this.dataTracker.startTracking(TRACKER_LEFT_LEG_ROTATION, DEFAULT_LEFT_LEG_ROTATION);
      this.dataTracker.startTracking(TRACKER_RIGHT_LEG_ROTATION, DEFAULT_RIGHT_LEG_ROTATION);
   }

   @Override
   public Iterable<ItemStack> getItemsHand() {
      return this.heldItems;
   }

   @Override
   public Iterable<ItemStack> getArmorItems() {
      return this.armorItems;
   }

   @Override
   public ItemStack getEquippedStack(EquipmentSlot slot) {
      switch (slot.getType()) {
         case HAND:
            return this.heldItems.get(slot.getEntitySlotId());
         case ARMOR:
            return this.armorItems.get(slot.getEntitySlotId());
         default:
            return ItemStack.EMPTY;
      }
   }

   @Override
   public void equipStack(EquipmentSlot slot, ItemStack stack) {
      switch (slot.getType()) {
         case HAND:
            this.onEquipStack(stack);
            this.heldItems.set(slot.getEntitySlotId(), stack);
            break;
         case ARMOR:
            this.onEquipStack(stack);
            this.armorItems.set(slot.getEntitySlotId(), stack);
      }
   }

   @Override
   public boolean equip(int slot, ItemStack item) {
      EquipmentSlot _snowman;
      if (slot == 98) {
         _snowman = EquipmentSlot.MAINHAND;
      } else if (slot == 99) {
         _snowman = EquipmentSlot.OFFHAND;
      } else if (slot == 100 + EquipmentSlot.HEAD.getEntitySlotId()) {
         _snowman = EquipmentSlot.HEAD;
      } else if (slot == 100 + EquipmentSlot.CHEST.getEntitySlotId()) {
         _snowman = EquipmentSlot.CHEST;
      } else if (slot == 100 + EquipmentSlot.LEGS.getEntitySlotId()) {
         _snowman = EquipmentSlot.LEGS;
      } else {
         if (slot != 100 + EquipmentSlot.FEET.getEntitySlotId()) {
            return false;
         }

         _snowman = EquipmentSlot.FEET;
      }

      if (!item.isEmpty() && !MobEntity.canEquipmentSlotContain(_snowman, item) && _snowman != EquipmentSlot.HEAD) {
         return false;
      } else {
         this.equipStack(_snowman, item);
         return true;
      }
   }

   @Override
   public boolean canEquip(ItemStack stack) {
      EquipmentSlot _snowman = MobEntity.getPreferredEquipmentSlot(stack);
      return this.getEquippedStack(_snowman).isEmpty() && !this.isSlotDisabled(_snowman);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      ListTag _snowman = new ListTag();

      for (ItemStack _snowmanx : this.armorItems) {
         CompoundTag _snowmanxx = new CompoundTag();
         if (!_snowmanx.isEmpty()) {
            _snowmanx.toTag(_snowmanxx);
         }

         _snowman.add(_snowmanxx);
      }

      tag.put("ArmorItems", _snowman);
      ListTag _snowmanx = new ListTag();

      for (ItemStack _snowmanxx : this.heldItems) {
         CompoundTag _snowmanxxx = new CompoundTag();
         if (!_snowmanxx.isEmpty()) {
            _snowmanxx.toTag(_snowmanxxx);
         }

         _snowmanx.add(_snowmanxxx);
      }

      tag.put("HandItems", _snowmanx);
      tag.putBoolean("Invisible", this.isInvisible());
      tag.putBoolean("Small", this.isSmall());
      tag.putBoolean("ShowArms", this.shouldShowArms());
      tag.putInt("DisabledSlots", this.disabledSlots);
      tag.putBoolean("NoBasePlate", this.shouldHideBasePlate());
      if (this.isMarker()) {
         tag.putBoolean("Marker", this.isMarker());
      }

      tag.put("Pose", this.serializePose());
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      if (tag.contains("ArmorItems", 9)) {
         ListTag _snowman = tag.getList("ArmorItems", 10);

         for (int _snowmanx = 0; _snowmanx < this.armorItems.size(); _snowmanx++) {
            this.armorItems.set(_snowmanx, ItemStack.fromTag(_snowman.getCompound(_snowmanx)));
         }
      }

      if (tag.contains("HandItems", 9)) {
         ListTag _snowman = tag.getList("HandItems", 10);

         for (int _snowmanx = 0; _snowmanx < this.heldItems.size(); _snowmanx++) {
            this.heldItems.set(_snowmanx, ItemStack.fromTag(_snowman.getCompound(_snowmanx)));
         }
      }

      this.setInvisible(tag.getBoolean("Invisible"));
      this.setSmall(tag.getBoolean("Small"));
      this.setShowArms(tag.getBoolean("ShowArms"));
      this.disabledSlots = tag.getInt("DisabledSlots");
      this.setHideBasePlate(tag.getBoolean("NoBasePlate"));
      this.setMarker(tag.getBoolean("Marker"));
      this.noClip = !this.canClip();
      CompoundTag _snowman = tag.getCompound("Pose");
      this.deserializePose(_snowman);
   }

   private void deserializePose(CompoundTag _snowman) {
      ListTag _snowmanx = _snowman.getList("Head", 5);
      this.setHeadRotation(_snowmanx.isEmpty() ? DEFAULT_HEAD_ROTATION : new EulerAngle(_snowmanx));
      ListTag _snowmanxx = _snowman.getList("Body", 5);
      this.setBodyRotation(_snowmanxx.isEmpty() ? DEFAULT_BODY_ROTATION : new EulerAngle(_snowmanxx));
      ListTag _snowmanxxx = _snowman.getList("LeftArm", 5);
      this.setLeftArmRotation(_snowmanxxx.isEmpty() ? DEFAULT_LEFT_ARM_ROTATION : new EulerAngle(_snowmanxxx));
      ListTag _snowmanxxxx = _snowman.getList("RightArm", 5);
      this.setRightArmRotation(_snowmanxxxx.isEmpty() ? DEFAULT_RIGHT_ARM_ROTATION : new EulerAngle(_snowmanxxxx));
      ListTag _snowmanxxxxx = _snowman.getList("LeftLeg", 5);
      this.setLeftLegRotation(_snowmanxxxxx.isEmpty() ? DEFAULT_LEFT_LEG_ROTATION : new EulerAngle(_snowmanxxxxx));
      ListTag _snowmanxxxxxx = _snowman.getList("RightLeg", 5);
      this.setRightLegRotation(_snowmanxxxxxx.isEmpty() ? DEFAULT_RIGHT_LEG_ROTATION : new EulerAngle(_snowmanxxxxxx));
   }

   private CompoundTag serializePose() {
      CompoundTag _snowman = new CompoundTag();
      if (!DEFAULT_HEAD_ROTATION.equals(this.headRotation)) {
         _snowman.put("Head", this.headRotation.serialize());
      }

      if (!DEFAULT_BODY_ROTATION.equals(this.bodyRotation)) {
         _snowman.put("Body", this.bodyRotation.serialize());
      }

      if (!DEFAULT_LEFT_ARM_ROTATION.equals(this.leftArmRotation)) {
         _snowman.put("LeftArm", this.leftArmRotation.serialize());
      }

      if (!DEFAULT_RIGHT_ARM_ROTATION.equals(this.rightArmRotation)) {
         _snowman.put("RightArm", this.rightArmRotation.serialize());
      }

      if (!DEFAULT_LEFT_LEG_ROTATION.equals(this.leftLegRotation)) {
         _snowman.put("LeftLeg", this.leftLegRotation.serialize());
      }

      if (!DEFAULT_RIGHT_LEG_ROTATION.equals(this.rightLegRotation)) {
         _snowman.put("RightLeg", this.rightLegRotation.serialize());
      }

      return _snowman;
   }

   @Override
   public boolean isPushable() {
      return false;
   }

   @Override
   protected void pushAway(Entity entity) {
   }

   @Override
   protected void tickCramming() {
      List<Entity> _snowman = this.world.getOtherEntities(this, this.getBoundingBox(), RIDEABLE_MINECART_PREDICATE);

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         Entity _snowmanxx = _snowman.get(_snowmanx);
         if (this.squaredDistanceTo(_snowmanxx) <= 0.2) {
            _snowmanxx.pushAwayFrom(this);
         }
      }
   }

   @Override
   public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
      ItemStack _snowman = player.getStackInHand(hand);
      if (this.isMarker() || _snowman.getItem() == Items.NAME_TAG) {
         return ActionResult.PASS;
      } else if (player.isSpectator()) {
         return ActionResult.SUCCESS;
      } else if (player.world.isClient) {
         return ActionResult.CONSUME;
      } else {
         EquipmentSlot _snowmanx = MobEntity.getPreferredEquipmentSlot(_snowman);
         if (_snowman.isEmpty()) {
            EquipmentSlot _snowmanxx = this.slotFromPosition(hitPos);
            EquipmentSlot _snowmanxxx = this.isSlotDisabled(_snowmanxx) ? _snowmanx : _snowmanxx;
            if (this.hasStackEquipped(_snowmanxxx) && this.equip(player, _snowmanxxx, _snowman, hand)) {
               return ActionResult.SUCCESS;
            }
         } else {
            if (this.isSlotDisabled(_snowmanx)) {
               return ActionResult.FAIL;
            }

            if (_snowmanx.getType() == EquipmentSlot.Type.HAND && !this.shouldShowArms()) {
               return ActionResult.FAIL;
            }

            if (this.equip(player, _snowmanx, _snowman, hand)) {
               return ActionResult.SUCCESS;
            }
         }

         return ActionResult.PASS;
      }
   }

   private EquipmentSlot slotFromPosition(Vec3d _snowman) {
      EquipmentSlot _snowmanx = EquipmentSlot.MAINHAND;
      boolean _snowmanxx = this.isSmall();
      double _snowmanxxx = _snowmanxx ? _snowman.y * 2.0 : _snowman.y;
      EquipmentSlot _snowmanxxxx = EquipmentSlot.FEET;
      if (_snowmanxxx >= 0.1 && _snowmanxxx < 0.1 + (_snowmanxx ? 0.8 : 0.45) && this.hasStackEquipped(_snowmanxxxx)) {
         _snowmanx = EquipmentSlot.FEET;
      } else if (_snowmanxxx >= 0.9 + (_snowmanxx ? 0.3 : 0.0) && _snowmanxxx < 0.9 + (_snowmanxx ? 1.0 : 0.7) && this.hasStackEquipped(EquipmentSlot.CHEST)) {
         _snowmanx = EquipmentSlot.CHEST;
      } else if (_snowmanxxx >= 0.4 && _snowmanxxx < 0.4 + (_snowmanxx ? 1.0 : 0.8) && this.hasStackEquipped(EquipmentSlot.LEGS)) {
         _snowmanx = EquipmentSlot.LEGS;
      } else if (_snowmanxxx >= 1.6 && this.hasStackEquipped(EquipmentSlot.HEAD)) {
         _snowmanx = EquipmentSlot.HEAD;
      } else if (!this.hasStackEquipped(EquipmentSlot.MAINHAND) && this.hasStackEquipped(EquipmentSlot.OFFHAND)) {
         _snowmanx = EquipmentSlot.OFFHAND;
      }

      return _snowmanx;
   }

   private boolean isSlotDisabled(EquipmentSlot slot) {
      return (this.disabledSlots & 1 << slot.getArmorStandSlotId()) != 0 || slot.getType() == EquipmentSlot.Type.HAND && !this.shouldShowArms();
   }

   private boolean equip(PlayerEntity player, EquipmentSlot slot, ItemStack stack, Hand hand) {
      ItemStack _snowman = this.getEquippedStack(slot);
      if (!_snowman.isEmpty() && (this.disabledSlots & 1 << slot.getArmorStandSlotId() + 8) != 0) {
         return false;
      } else if (_snowman.isEmpty() && (this.disabledSlots & 1 << slot.getArmorStandSlotId() + 16) != 0) {
         return false;
      } else if (player.abilities.creativeMode && _snowman.isEmpty() && !stack.isEmpty()) {
         ItemStack _snowmanx = stack.copy();
         _snowmanx.setCount(1);
         this.equipStack(slot, _snowmanx);
         return true;
      } else if (stack.isEmpty() || stack.getCount() <= 1) {
         this.equipStack(slot, stack);
         player.setStackInHand(hand, _snowman);
         return true;
      } else if (!_snowman.isEmpty()) {
         return false;
      } else {
         ItemStack _snowmanx = stack.copy();
         _snowmanx.setCount(1);
         this.equipStack(slot, _snowmanx);
         stack.decrement(1);
         return true;
      }
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.world.isClient || this.removed) {
         return false;
      } else if (DamageSource.OUT_OF_WORLD.equals(source)) {
         this.remove();
         return false;
      } else if (this.isInvulnerableTo(source) || this.invisible || this.isMarker()) {
         return false;
      } else if (source.isExplosive()) {
         this.onBreak(source);
         this.remove();
         return false;
      } else if (DamageSource.IN_FIRE.equals(source)) {
         if (this.isOnFire()) {
            this.updateHealth(source, 0.15F);
         } else {
            this.setOnFireFor(5);
         }

         return false;
      } else if (DamageSource.ON_FIRE.equals(source) && this.getHealth() > 0.5F) {
         this.updateHealth(source, 4.0F);
         return false;
      } else {
         boolean _snowman = source.getSource() instanceof PersistentProjectileEntity;
         boolean _snowmanx = _snowman && ((PersistentProjectileEntity)source.getSource()).getPierceLevel() > 0;
         boolean _snowmanxx = "player".equals(source.getName());
         if (!_snowmanxx && !_snowman) {
            return false;
         } else if (source.getAttacker() instanceof PlayerEntity && !((PlayerEntity)source.getAttacker()).abilities.allowModifyWorld) {
            return false;
         } else if (source.isSourceCreativePlayer()) {
            this.playBreakSound();
            this.spawnBreakParticles();
            this.remove();
            return _snowmanx;
         } else {
            long _snowmanxxx = this.world.getTime();
            if (_snowmanxxx - this.lastHitTime > 5L && !_snowman) {
               this.world.sendEntityStatus(this, (byte)32);
               this.lastHitTime = _snowmanxxx;
            } else {
               this.breakAndDropItem(source);
               this.spawnBreakParticles();
               this.remove();
            }

            return true;
         }
      }
   }

   @Override
   public void handleStatus(byte status) {
      if (status == 32) {
         if (this.world.isClient) {
            this.world.playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ARMOR_STAND_HIT, this.getSoundCategory(), 0.3F, 1.0F, false);
            this.lastHitTime = this.world.getTime();
         }
      } else {
         super.handleStatus(status);
      }
   }

   @Override
   public boolean shouldRender(double distance) {
      double _snowman = this.getBoundingBox().getAverageSideLength() * 4.0;
      if (Double.isNaN(_snowman) || _snowman == 0.0) {
         _snowman = 4.0;
      }

      _snowman *= 64.0;
      return distance < _snowman * _snowman;
   }

   private void spawnBreakParticles() {
      if (this.world instanceof ServerWorld) {
         ((ServerWorld)this.world)
            .spawnParticles(
               new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.getDefaultState()),
               this.getX(),
               this.getBodyY(0.6666666666666666),
               this.getZ(),
               10,
               (double)(this.getWidth() / 4.0F),
               (double)(this.getHeight() / 4.0F),
               (double)(this.getWidth() / 4.0F),
               0.05
            );
      }
   }

   private void updateHealth(DamageSource damageSource, float amount) {
      float _snowman = this.getHealth();
      _snowman -= amount;
      if (_snowman <= 0.5F) {
         this.onBreak(damageSource);
         this.remove();
      } else {
         this.setHealth(_snowman);
      }
   }

   private void breakAndDropItem(DamageSource damageSource) {
      Block.dropStack(this.world, this.getBlockPos(), new ItemStack(Items.ARMOR_STAND));
      this.onBreak(damageSource);
   }

   private void onBreak(DamageSource damageSource) {
      this.playBreakSound();
      this.drop(damageSource);

      for (int _snowman = 0; _snowman < this.heldItems.size(); _snowman++) {
         ItemStack _snowmanx = this.heldItems.get(_snowman);
         if (!_snowmanx.isEmpty()) {
            Block.dropStack(this.world, this.getBlockPos().up(), _snowmanx);
            this.heldItems.set(_snowman, ItemStack.EMPTY);
         }
      }

      for (int _snowmanx = 0; _snowmanx < this.armorItems.size(); _snowmanx++) {
         ItemStack _snowmanxx = this.armorItems.get(_snowmanx);
         if (!_snowmanxx.isEmpty()) {
            Block.dropStack(this.world, this.getBlockPos().up(), _snowmanxx);
            this.armorItems.set(_snowmanx, ItemStack.EMPTY);
         }
      }
   }

   private void playBreakSound() {
      this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ARMOR_STAND_BREAK, this.getSoundCategory(), 1.0F, 1.0F);
   }

   @Override
   protected float turnHead(float bodyRotation, float headRotation) {
      this.prevBodyYaw = this.prevYaw;
      this.bodyYaw = this.yaw;
      return 0.0F;
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return dimensions.height * (this.isBaby() ? 0.5F : 0.9F);
   }

   @Override
   public double getHeightOffset() {
      return this.isMarker() ? 0.0 : 0.1F;
   }

   @Override
   public void travel(Vec3d movementInput) {
      if (this.canClip()) {
         super.travel(movementInput);
      }
   }

   @Override
   public void setYaw(float yaw) {
      this.prevBodyYaw = this.prevYaw = yaw;
      this.prevHeadYaw = this.headYaw = yaw;
   }

   @Override
   public void setHeadYaw(float headYaw) {
      this.prevBodyYaw = this.prevYaw = headYaw;
      this.prevHeadYaw = this.headYaw = headYaw;
   }

   @Override
   public void tick() {
      super.tick();
      EulerAngle _snowman = this.dataTracker.get(TRACKER_HEAD_ROTATION);
      if (!this.headRotation.equals(_snowman)) {
         this.setHeadRotation(_snowman);
      }

      EulerAngle _snowmanx = this.dataTracker.get(TRACKER_BODY_ROTATION);
      if (!this.bodyRotation.equals(_snowmanx)) {
         this.setBodyRotation(_snowmanx);
      }

      EulerAngle _snowmanxx = this.dataTracker.get(TRACKER_LEFT_ARM_ROTATION);
      if (!this.leftArmRotation.equals(_snowmanxx)) {
         this.setLeftArmRotation(_snowmanxx);
      }

      EulerAngle _snowmanxxx = this.dataTracker.get(TRACKER_RIGHT_ARM_ROTATION);
      if (!this.rightArmRotation.equals(_snowmanxxx)) {
         this.setRightArmRotation(_snowmanxxx);
      }

      EulerAngle _snowmanxxxx = this.dataTracker.get(TRACKER_LEFT_LEG_ROTATION);
      if (!this.leftLegRotation.equals(_snowmanxxxx)) {
         this.setLeftLegRotation(_snowmanxxxx);
      }

      EulerAngle _snowmanxxxxx = this.dataTracker.get(TRACKER_RIGHT_LEG_ROTATION);
      if (!this.rightLegRotation.equals(_snowmanxxxxx)) {
         this.setRightLegRotation(_snowmanxxxxx);
      }
   }

   @Override
   protected void updatePotionVisibility() {
      this.setInvisible(this.invisible);
   }

   @Override
   public void setInvisible(boolean invisible) {
      this.invisible = invisible;
      super.setInvisible(invisible);
   }

   @Override
   public boolean isBaby() {
      return this.isSmall();
   }

   @Override
   public void kill() {
      this.remove();
   }

   @Override
   public boolean isImmuneToExplosion() {
      return this.isInvisible();
   }

   @Override
   public PistonBehavior getPistonBehavior() {
      return this.isMarker() ? PistonBehavior.IGNORE : super.getPistonBehavior();
   }

   private void setSmall(boolean small) {
      this.dataTracker.set(ARMOR_STAND_FLAGS, this.setBitField(this.dataTracker.get(ARMOR_STAND_FLAGS), 1, small));
   }

   public boolean isSmall() {
      return (this.dataTracker.get(ARMOR_STAND_FLAGS) & 1) != 0;
   }

   private void setShowArms(boolean showArms) {
      this.dataTracker.set(ARMOR_STAND_FLAGS, this.setBitField(this.dataTracker.get(ARMOR_STAND_FLAGS), 4, showArms));
   }

   public boolean shouldShowArms() {
      return (this.dataTracker.get(ARMOR_STAND_FLAGS) & 4) != 0;
   }

   private void setHideBasePlate(boolean hideBasePlate) {
      this.dataTracker.set(ARMOR_STAND_FLAGS, this.setBitField(this.dataTracker.get(ARMOR_STAND_FLAGS), 8, hideBasePlate));
   }

   public boolean shouldHideBasePlate() {
      return (this.dataTracker.get(ARMOR_STAND_FLAGS) & 8) != 0;
   }

   private void setMarker(boolean marker) {
      this.dataTracker.set(ARMOR_STAND_FLAGS, this.setBitField(this.dataTracker.get(ARMOR_STAND_FLAGS), 16, marker));
   }

   public boolean isMarker() {
      return (this.dataTracker.get(ARMOR_STAND_FLAGS) & 16) != 0;
   }

   private byte setBitField(byte value, int bitField, boolean set) {
      if (set) {
         value = (byte)(value | bitField);
      } else {
         value = (byte)(value & ~bitField);
      }

      return value;
   }

   public void setHeadRotation(EulerAngle _snowman) {
      this.headRotation = _snowman;
      this.dataTracker.set(TRACKER_HEAD_ROTATION, _snowman);
   }

   public void setBodyRotation(EulerAngle _snowman) {
      this.bodyRotation = _snowman;
      this.dataTracker.set(TRACKER_BODY_ROTATION, _snowman);
   }

   public void setLeftArmRotation(EulerAngle _snowman) {
      this.leftArmRotation = _snowman;
      this.dataTracker.set(TRACKER_LEFT_ARM_ROTATION, _snowman);
   }

   public void setRightArmRotation(EulerAngle _snowman) {
      this.rightArmRotation = _snowman;
      this.dataTracker.set(TRACKER_RIGHT_ARM_ROTATION, _snowman);
   }

   public void setLeftLegRotation(EulerAngle _snowman) {
      this.leftLegRotation = _snowman;
      this.dataTracker.set(TRACKER_LEFT_LEG_ROTATION, _snowman);
   }

   public void setRightLegRotation(EulerAngle _snowman) {
      this.rightLegRotation = _snowman;
      this.dataTracker.set(TRACKER_RIGHT_LEG_ROTATION, _snowman);
   }

   public EulerAngle getHeadRotation() {
      return this.headRotation;
   }

   public EulerAngle getBodyRotation() {
      return this.bodyRotation;
   }

   public EulerAngle getLeftArmRotation() {
      return this.leftArmRotation;
   }

   public EulerAngle getRightArmRotation() {
      return this.rightArmRotation;
   }

   public EulerAngle getLeftLegRotation() {
      return this.leftLegRotation;
   }

   public EulerAngle getRightLegRotation() {
      return this.rightLegRotation;
   }

   @Override
   public boolean collides() {
      return super.collides() && !this.isMarker();
   }

   @Override
   public boolean handleAttack(Entity attacker) {
      return attacker instanceof PlayerEntity && !this.world.canPlayerModifyAt((PlayerEntity)attacker, this.getBlockPos());
   }

   @Override
   public Arm getMainArm() {
      return Arm.RIGHT;
   }

   @Override
   protected SoundEvent getFallSound(int distance) {
      return SoundEvents.ENTITY_ARMOR_STAND_FALL;
   }

   @Nullable
   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_ARMOR_STAND_HIT;
   }

   @Nullable
   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_ARMOR_STAND_BREAK;
   }

   @Override
   public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
   }

   @Override
   public boolean isAffectedBySplashPotions() {
      return false;
   }

   @Override
   public void onTrackedDataSet(TrackedData<?> data) {
      if (ARMOR_STAND_FLAGS.equals(data)) {
         this.calculateDimensions();
         this.inanimate = !this.isMarker();
      }

      super.onTrackedDataSet(data);
   }

   @Override
   public boolean isMobOrPlayer() {
      return false;
   }

   @Override
   public EntityDimensions getDimensions(EntityPose pose) {
      return this.method_31168(this.isMarker());
   }

   private EntityDimensions method_31168(boolean _snowman) {
      if (_snowman) {
         return field_26745;
      } else {
         return this.isBaby() ? field_26746 : this.getType().getDimensions();
      }
   }

   @Override
   public Vec3d method_31166(float _snowman) {
      if (this.isMarker()) {
         Box _snowmanx = this.method_31168(false).method_30757(this.getPos());
         BlockPos _snowmanxx = this.getBlockPos();
         int _snowmanxxx = Integer.MIN_VALUE;

         for (BlockPos _snowmanxxxx : BlockPos.iterate(new BlockPos(_snowmanx.minX, _snowmanx.minY, _snowmanx.minZ), new BlockPos(_snowmanx.maxX, _snowmanx.maxY, _snowmanx.maxZ))) {
            int _snowmanxxxxx = Math.max(this.world.getLightLevel(LightType.BLOCK, _snowmanxxxx), this.world.getLightLevel(LightType.SKY, _snowmanxxxx));
            if (_snowmanxxxxx == 15) {
               return Vec3d.ofCenter(_snowmanxxxx);
            }

            if (_snowmanxxxxx > _snowmanxxx) {
               _snowmanxxx = _snowmanxxxxx;
               _snowmanxx = _snowmanxxxx.toImmutable();
            }
         }

         return Vec3d.ofCenter(_snowmanxx);
      } else {
         return super.method_31166(_snowman);
      }
   }
}
