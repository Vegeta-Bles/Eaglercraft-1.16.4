package net.minecraft.entity.mob;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.ai.control.JumpControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.SwordItem;
import net.minecraft.loot.context.LootContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.packet.s2c.play.EntityAttachS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class MobEntity extends LivingEntity {
   private static final TrackedData<Byte> MOB_FLAGS = DataTracker.registerData(MobEntity.class, TrackedDataHandlerRegistry.BYTE);
   public int ambientSoundChance;
   protected int experiencePoints;
   protected LookControl lookControl;
   protected MoveControl moveControl;
   protected JumpControl jumpControl;
   private final BodyControl bodyControl;
   protected EntityNavigation navigation;
   protected final GoalSelector goalSelector;
   protected final GoalSelector targetSelector;
   private LivingEntity target;
   private final MobVisibilityCache visibilityCache;
   private final DefaultedList<ItemStack> handItems = DefaultedList.ofSize(2, ItemStack.EMPTY);
   protected final float[] handDropChances = new float[2];
   private final DefaultedList<ItemStack> armorItems = DefaultedList.ofSize(4, ItemStack.EMPTY);
   protected final float[] armorDropChances = new float[4];
   private boolean pickUpLoot;
   private boolean persistent;
   private final Map<PathNodeType, Float> pathfindingPenalties = Maps.newEnumMap(PathNodeType.class);
   private Identifier lootTable;
   private long lootTableSeed;
   @Nullable
   private Entity holdingEntity;
   private int holdingEntityId;
   @Nullable
   private CompoundTag leashTag;
   private BlockPos positionTarget = BlockPos.ORIGIN;
   private float positionTargetRange = -1.0F;

   protected MobEntity(EntityType<? extends MobEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.goalSelector = new GoalSelector(_snowman.getProfilerSupplier());
      this.targetSelector = new GoalSelector(_snowman.getProfilerSupplier());
      this.lookControl = new LookControl(this);
      this.moveControl = new MoveControl(this);
      this.jumpControl = new JumpControl(this);
      this.bodyControl = this.createBodyControl();
      this.navigation = this.createNavigation(_snowman);
      this.visibilityCache = new MobVisibilityCache(this);
      Arrays.fill(this.armorDropChances, 0.085F);
      Arrays.fill(this.handDropChances, 0.085F);
      if (_snowman != null && !_snowman.isClient) {
         this.initGoals();
      }
   }

   protected void initGoals() {
   }

   public static DefaultAttributeContainer.Builder createMobAttributes() {
      return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
   }

   protected EntityNavigation createNavigation(World world) {
      return new MobNavigation(this, world);
   }

   protected boolean movesIndependently() {
      return false;
   }

   public float getPathfindingPenalty(PathNodeType nodeType) {
      MobEntity _snowman;
      if (this.getVehicle() instanceof MobEntity && ((MobEntity)this.getVehicle()).movesIndependently()) {
         _snowman = (MobEntity)this.getVehicle();
      } else {
         _snowman = this;
      }

      Float _snowmanx = _snowman.pathfindingPenalties.get(nodeType);
      return _snowmanx == null ? nodeType.getDefaultPenalty() : _snowmanx;
   }

   public void setPathfindingPenalty(PathNodeType nodeType, float penalty) {
      this.pathfindingPenalties.put(nodeType, penalty);
   }

   public boolean method_29244(PathNodeType _snowman) {
      return _snowman != PathNodeType.DANGER_FIRE && _snowman != PathNodeType.DANGER_CACTUS && _snowman != PathNodeType.DANGER_OTHER && _snowman != PathNodeType.WALKABLE_DOOR;
   }

   protected BodyControl createBodyControl() {
      return new BodyControl(this);
   }

   public LookControl getLookControl() {
      return this.lookControl;
   }

   public MoveControl getMoveControl() {
      if (this.hasVehicle() && this.getVehicle() instanceof MobEntity) {
         MobEntity _snowman = (MobEntity)this.getVehicle();
         return _snowman.getMoveControl();
      } else {
         return this.moveControl;
      }
   }

   public JumpControl getJumpControl() {
      return this.jumpControl;
   }

   public EntityNavigation getNavigation() {
      if (this.hasVehicle() && this.getVehicle() instanceof MobEntity) {
         MobEntity _snowman = (MobEntity)this.getVehicle();
         return _snowman.getNavigation();
      } else {
         return this.navigation;
      }
   }

   public MobVisibilityCache getVisibilityCache() {
      return this.visibilityCache;
   }

   @Nullable
   public LivingEntity getTarget() {
      return this.target;
   }

   public void setTarget(@Nullable LivingEntity target) {
      this.target = target;
   }

   @Override
   public boolean canTarget(EntityType<?> type) {
      return type != EntityType.GHAST;
   }

   public boolean canUseRangedWeapon(RangedWeaponItem weapon) {
      return false;
   }

   public void onEatingGrass() {
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(MOB_FLAGS, (byte)0);
   }

   public int getMinAmbientSoundDelay() {
      return 80;
   }

   public void playAmbientSound() {
      SoundEvent _snowman = this.getAmbientSound();
      if (_snowman != null) {
         this.playSound(_snowman, this.getSoundVolume(), this.getSoundPitch());
      }
   }

   @Override
   public void baseTick() {
      super.baseTick();
      this.world.getProfiler().push("mobBaseTick");
      if (this.isAlive() && this.random.nextInt(1000) < this.ambientSoundChance++) {
         this.resetSoundDelay();
         this.playAmbientSound();
      }

      this.world.getProfiler().pop();
   }

   @Override
   protected void playHurtSound(DamageSource source) {
      this.resetSoundDelay();
      super.playHurtSound(source);
   }

   private void resetSoundDelay() {
      this.ambientSoundChance = -this.getMinAmbientSoundDelay();
   }

   @Override
   protected int getCurrentExperience(PlayerEntity player) {
      if (this.experiencePoints > 0) {
         int _snowman = this.experiencePoints;

         for (int _snowmanx = 0; _snowmanx < this.armorItems.size(); _snowmanx++) {
            if (!this.armorItems.get(_snowmanx).isEmpty() && this.armorDropChances[_snowmanx] <= 1.0F) {
               _snowman += 1 + this.random.nextInt(3);
            }
         }

         for (int _snowmanxx = 0; _snowmanxx < this.handItems.size(); _snowmanxx++) {
            if (!this.handItems.get(_snowmanxx).isEmpty() && this.handDropChances[_snowmanxx] <= 1.0F) {
               _snowman += 1 + this.random.nextInt(3);
            }
         }

         return _snowman;
      } else {
         return this.experiencePoints;
      }
   }

   public void playSpawnEffects() {
      if (this.world.isClient) {
         for (int _snowman = 0; _snowman < 20; _snowman++) {
            double _snowmanx = this.random.nextGaussian() * 0.02;
            double _snowmanxx = this.random.nextGaussian() * 0.02;
            double _snowmanxxx = this.random.nextGaussian() * 0.02;
            double _snowmanxxxx = 10.0;
            this.world
               .addParticle(
                  ParticleTypes.POOF, this.offsetX(1.0) - _snowmanx * 10.0, this.getRandomBodyY() - _snowmanxx * 10.0, this.getParticleZ(1.0) - _snowmanxxx * 10.0, _snowmanx, _snowmanxx, _snowmanxxx
               );
         }
      } else {
         this.world.sendEntityStatus(this, (byte)20);
      }
   }

   @Override
   public void handleStatus(byte status) {
      if (status == 20) {
         this.playSpawnEffects();
      } else {
         super.handleStatus(status);
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.world.isClient) {
         this.updateLeash();
         if (this.age % 5 == 0) {
            this.updateGoalControls();
         }
      }
   }

   protected void updateGoalControls() {
      boolean _snowman = !(this.getPrimaryPassenger() instanceof MobEntity);
      boolean _snowmanx = !(this.getVehicle() instanceof BoatEntity);
      this.goalSelector.setControlEnabled(Goal.Control.MOVE, _snowman);
      this.goalSelector.setControlEnabled(Goal.Control.JUMP, _snowman && _snowmanx);
      this.goalSelector.setControlEnabled(Goal.Control.LOOK, _snowman);
   }

   @Override
   protected float turnHead(float bodyRotation, float headRotation) {
      this.bodyControl.tick();
      return headRotation;
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return null;
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putBoolean("CanPickUpLoot", this.canPickUpLoot());
      tag.putBoolean("PersistenceRequired", this.persistent);
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

      for (ItemStack _snowmanxx : this.handItems) {
         CompoundTag _snowmanxxx = new CompoundTag();
         if (!_snowmanxx.isEmpty()) {
            _snowmanxx.toTag(_snowmanxxx);
         }

         _snowmanx.add(_snowmanxxx);
      }

      tag.put("HandItems", _snowmanx);
      ListTag _snowmanxx = new ListTag();

      for (float _snowmanxxx : this.armorDropChances) {
         _snowmanxx.add(FloatTag.of(_snowmanxxx));
      }

      tag.put("ArmorDropChances", _snowmanxx);
      ListTag _snowmanxxx = new ListTag();

      for (float _snowmanxxxx : this.handDropChances) {
         _snowmanxxx.add(FloatTag.of(_snowmanxxxx));
      }

      tag.put("HandDropChances", _snowmanxxx);
      if (this.holdingEntity != null) {
         CompoundTag _snowmanxxxx = new CompoundTag();
         if (this.holdingEntity instanceof LivingEntity) {
            UUID _snowmanxxxxx = this.holdingEntity.getUuid();
            _snowmanxxxx.putUuid("UUID", _snowmanxxxxx);
         } else if (this.holdingEntity instanceof AbstractDecorationEntity) {
            BlockPos _snowmanxxxxx = ((AbstractDecorationEntity)this.holdingEntity).getDecorationBlockPos();
            _snowmanxxxx.putInt("X", _snowmanxxxxx.getX());
            _snowmanxxxx.putInt("Y", _snowmanxxxxx.getY());
            _snowmanxxxx.putInt("Z", _snowmanxxxxx.getZ());
         }

         tag.put("Leash", _snowmanxxxx);
      } else if (this.leashTag != null) {
         tag.put("Leash", this.leashTag.copy());
      }

      tag.putBoolean("LeftHanded", this.isLeftHanded());
      if (this.lootTable != null) {
         tag.putString("DeathLootTable", this.lootTable.toString());
         if (this.lootTableSeed != 0L) {
            tag.putLong("DeathLootTableSeed", this.lootTableSeed);
         }
      }

      if (this.isAiDisabled()) {
         tag.putBoolean("NoAI", this.isAiDisabled());
      }
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      if (tag.contains("CanPickUpLoot", 1)) {
         this.setCanPickUpLoot(tag.getBoolean("CanPickUpLoot"));
      }

      this.persistent = tag.getBoolean("PersistenceRequired");
      if (tag.contains("ArmorItems", 9)) {
         ListTag _snowman = tag.getList("ArmorItems", 10);

         for (int _snowmanx = 0; _snowmanx < this.armorItems.size(); _snowmanx++) {
            this.armorItems.set(_snowmanx, ItemStack.fromTag(_snowman.getCompound(_snowmanx)));
         }
      }

      if (tag.contains("HandItems", 9)) {
         ListTag _snowman = tag.getList("HandItems", 10);

         for (int _snowmanx = 0; _snowmanx < this.handItems.size(); _snowmanx++) {
            this.handItems.set(_snowmanx, ItemStack.fromTag(_snowman.getCompound(_snowmanx)));
         }
      }

      if (tag.contains("ArmorDropChances", 9)) {
         ListTag _snowman = tag.getList("ArmorDropChances", 5);

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            this.armorDropChances[_snowmanx] = _snowman.getFloat(_snowmanx);
         }
      }

      if (tag.contains("HandDropChances", 9)) {
         ListTag _snowman = tag.getList("HandDropChances", 5);

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            this.handDropChances[_snowmanx] = _snowman.getFloat(_snowmanx);
         }
      }

      if (tag.contains("Leash", 10)) {
         this.leashTag = tag.getCompound("Leash");
      }

      this.setLeftHanded(tag.getBoolean("LeftHanded"));
      if (tag.contains("DeathLootTable", 8)) {
         this.lootTable = new Identifier(tag.getString("DeathLootTable"));
         this.lootTableSeed = tag.getLong("DeathLootTableSeed");
      }

      this.setAiDisabled(tag.getBoolean("NoAI"));
   }

   @Override
   protected void dropLoot(DamageSource source, boolean causedByPlayer) {
      super.dropLoot(source, causedByPlayer);
      this.lootTable = null;
   }

   @Override
   protected LootContext.Builder getLootContextBuilder(boolean causedByPlayer, DamageSource source) {
      return super.getLootContextBuilder(causedByPlayer, source).random(this.lootTableSeed, this.random);
   }

   @Override
   public final Identifier getLootTable() {
      return this.lootTable == null ? this.getLootTableId() : this.lootTable;
   }

   protected Identifier getLootTableId() {
      return super.getLootTable();
   }

   public void setForwardSpeed(float forwardSpeed) {
      this.forwardSpeed = forwardSpeed;
   }

   public void setUpwardSpeed(float upwardSpeed) {
      this.upwardSpeed = upwardSpeed;
   }

   public void setSidewaysSpeed(float sidewaysMovement) {
      this.sidewaysSpeed = sidewaysMovement;
   }

   @Override
   public void setMovementSpeed(float movementSpeed) {
      super.setMovementSpeed(movementSpeed);
      this.setForwardSpeed(movementSpeed);
   }

   @Override
   public void tickMovement() {
      super.tickMovement();
      this.world.getProfiler().push("looting");
      if (!this.world.isClient && this.canPickUpLoot() && this.isAlive() && !this.dead && this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
         for (ItemEntity _snowman : this.world.getNonSpectatingEntities(ItemEntity.class, this.getBoundingBox().expand(1.0, 0.0, 1.0))) {
            if (!_snowman.removed && !_snowman.getStack().isEmpty() && !_snowman.cannotPickup() && this.canGather(_snowman.getStack())) {
               this.loot(_snowman);
            }
         }
      }

      this.world.getProfiler().pop();
   }

   protected void loot(ItemEntity item) {
      ItemStack _snowman = item.getStack();
      if (this.tryEquip(_snowman)) {
         this.method_29499(item);
         this.sendPickup(item, _snowman.getCount());
         item.remove();
      }
   }

   public boolean tryEquip(ItemStack equipment) {
      EquipmentSlot _snowman = getPreferredEquipmentSlot(equipment);
      ItemStack _snowmanx = this.getEquippedStack(_snowman);
      boolean _snowmanxx = this.prefersNewEquipment(equipment, _snowmanx);
      if (_snowmanxx && this.canPickupItem(equipment)) {
         double _snowmanxxx = (double)this.getDropChance(_snowman);
         if (!_snowmanx.isEmpty() && (double)Math.max(this.random.nextFloat() - 0.1F, 0.0F) < _snowmanxxx) {
            this.dropStack(_snowmanx);
         }

         this.equipLootStack(_snowman, equipment);
         this.onEquipStack(equipment);
         return true;
      } else {
         return false;
      }
   }

   protected void equipLootStack(EquipmentSlot slot, ItemStack stack) {
      this.equipStack(slot, stack);
      this.updateDropChances(slot);
      this.persistent = true;
   }

   public void updateDropChances(EquipmentSlot slot) {
      switch (slot.getType()) {
         case HAND:
            this.handDropChances[slot.getEntitySlotId()] = 2.0F;
            break;
         case ARMOR:
            this.armorDropChances[slot.getEntitySlotId()] = 2.0F;
      }
   }

   protected boolean prefersNewEquipment(ItemStack newStack, ItemStack oldStack) {
      if (oldStack.isEmpty()) {
         return true;
      } else if (newStack.getItem() instanceof SwordItem) {
         if (!(oldStack.getItem() instanceof SwordItem)) {
            return true;
         } else {
            SwordItem _snowman = (SwordItem)newStack.getItem();
            SwordItem _snowmanx = (SwordItem)oldStack.getItem();
            return _snowman.getAttackDamage() != _snowmanx.getAttackDamage() ? _snowman.getAttackDamage() > _snowmanx.getAttackDamage() : this.prefersNewDamageableItem(newStack, oldStack);
         }
      } else if (newStack.getItem() instanceof BowItem && oldStack.getItem() instanceof BowItem) {
         return this.prefersNewDamageableItem(newStack, oldStack);
      } else if (newStack.getItem() instanceof CrossbowItem && oldStack.getItem() instanceof CrossbowItem) {
         return this.prefersNewDamageableItem(newStack, oldStack);
      } else if (newStack.getItem() instanceof ArmorItem) {
         if (EnchantmentHelper.hasBindingCurse(oldStack)) {
            return false;
         } else if (!(oldStack.getItem() instanceof ArmorItem)) {
            return true;
         } else {
            ArmorItem _snowman = (ArmorItem)newStack.getItem();
            ArmorItem _snowmanx = (ArmorItem)oldStack.getItem();
            if (_snowman.getProtection() != _snowmanx.getProtection()) {
               return _snowman.getProtection() > _snowmanx.getProtection();
            } else {
               return _snowman.method_26353() != _snowmanx.method_26353() ? _snowman.method_26353() > _snowmanx.method_26353() : this.prefersNewDamageableItem(newStack, oldStack);
            }
         }
      } else {
         if (newStack.getItem() instanceof MiningToolItem) {
            if (oldStack.getItem() instanceof BlockItem) {
               return true;
            }

            if (oldStack.getItem() instanceof MiningToolItem) {
               MiningToolItem _snowman = (MiningToolItem)newStack.getItem();
               MiningToolItem _snowmanx = (MiningToolItem)oldStack.getItem();
               if (_snowman.getAttackDamage() != _snowmanx.getAttackDamage()) {
                  return _snowman.getAttackDamage() > _snowmanx.getAttackDamage();
               }

               return this.prefersNewDamageableItem(newStack, oldStack);
            }
         }

         return false;
      }
   }

   public boolean prefersNewDamageableItem(ItemStack newStack, ItemStack oldStack) {
      if (newStack.getDamage() >= oldStack.getDamage() && (!newStack.hasTag() || oldStack.hasTag())) {
         return newStack.hasTag() && oldStack.hasTag()
            ? newStack.getTag().getKeys().stream().anyMatch(_snowman -> !_snowman.equals("Damage"))
               && !oldStack.getTag().getKeys().stream().anyMatch(_snowman -> !_snowman.equals("Damage"))
            : false;
      } else {
         return true;
      }
   }

   public boolean canPickupItem(ItemStack stack) {
      return true;
   }

   public boolean canGather(ItemStack stack) {
      return this.canPickupItem(stack);
   }

   public boolean canImmediatelyDespawn(double distanceSquared) {
      return true;
   }

   public boolean cannotDespawn() {
      return this.hasVehicle();
   }

   protected boolean isDisallowedInPeaceful() {
      return false;
   }

   @Override
   public void checkDespawn() {
      if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.isDisallowedInPeaceful()) {
         this.remove();
      } else if (!this.isPersistent() && !this.cannotDespawn()) {
         Entity _snowman = this.world.getClosestPlayer(this, -1.0);
         if (_snowman != null) {
            double _snowmanx = _snowman.squaredDistanceTo(this);
            int _snowmanxx = this.getType().getSpawnGroup().getImmediateDespawnRange();
            int _snowmanxxx = _snowmanxx * _snowmanxx;
            if (_snowmanx > (double)_snowmanxxx && this.canImmediatelyDespawn(_snowmanx)) {
               this.remove();
            }

            int _snowmanxxxx = this.getType().getSpawnGroup().getDespawnStartRange();
            int _snowmanxxxxx = _snowmanxxxx * _snowmanxxxx;
            if (this.despawnCounter > 600 && this.random.nextInt(800) == 0 && _snowmanx > (double)_snowmanxxxxx && this.canImmediatelyDespawn(_snowmanx)) {
               this.remove();
            } else if (_snowmanx < (double)_snowmanxxxxx) {
               this.despawnCounter = 0;
            }
         }
      } else {
         this.despawnCounter = 0;
      }
   }

   @Override
   protected final void tickNewAi() {
      this.despawnCounter++;
      this.world.getProfiler().push("sensing");
      this.visibilityCache.clear();
      this.world.getProfiler().pop();
      this.world.getProfiler().push("targetSelector");
      this.targetSelector.tick();
      this.world.getProfiler().pop();
      this.world.getProfiler().push("goalSelector");
      this.goalSelector.tick();
      this.world.getProfiler().pop();
      this.world.getProfiler().push("navigation");
      this.navigation.tick();
      this.world.getProfiler().pop();
      this.world.getProfiler().push("mob tick");
      this.mobTick();
      this.world.getProfiler().pop();
      this.world.getProfiler().push("controls");
      this.world.getProfiler().push("move");
      this.moveControl.tick();
      this.world.getProfiler().swap("look");
      this.lookControl.tick();
      this.world.getProfiler().swap("jump");
      this.jumpControl.tick();
      this.world.getProfiler().pop();
      this.world.getProfiler().pop();
      this.sendAiDebugData();
   }

   protected void sendAiDebugData() {
      DebugInfoSender.sendGoalSelector(this.world, this, this.goalSelector);
   }

   protected void mobTick() {
   }

   public int getLookPitchSpeed() {
      return 40;
   }

   public int getBodyYawSpeed() {
      return 75;
   }

   public int getLookYawSpeed() {
      return 10;
   }

   public void lookAtEntity(Entity targetEntity, float maxYawChange, float maxPitchChange) {
      double _snowman = targetEntity.getX() - this.getX();
      double _snowmanx = targetEntity.getZ() - this.getZ();
      double _snowmanxx;
      if (targetEntity instanceof LivingEntity) {
         LivingEntity _snowmanxxx = (LivingEntity)targetEntity;
         _snowmanxx = _snowmanxxx.getEyeY() - this.getEyeY();
      } else {
         _snowmanxx = (targetEntity.getBoundingBox().minY + targetEntity.getBoundingBox().maxY) / 2.0 - this.getEyeY();
      }

      double _snowmanxxx = (double)MathHelper.sqrt(_snowman * _snowman + _snowmanx * _snowmanx);
      float _snowmanxxxx = (float)(MathHelper.atan2(_snowmanx, _snowman) * 180.0F / (float)Math.PI) - 90.0F;
      float _snowmanxxxxx = (float)(-(MathHelper.atan2(_snowmanxx, _snowmanxxx) * 180.0F / (float)Math.PI));
      this.pitch = this.changeAngle(this.pitch, _snowmanxxxxx, maxPitchChange);
      this.yaw = this.changeAngle(this.yaw, _snowmanxxxx, maxYawChange);
   }

   private float changeAngle(float oldAngle, float newAngle, float maxChangeInAngle) {
      float _snowman = MathHelper.wrapDegrees(newAngle - oldAngle);
      if (_snowman > maxChangeInAngle) {
         _snowman = maxChangeInAngle;
      }

      if (_snowman < -maxChangeInAngle) {
         _snowman = -maxChangeInAngle;
      }

      return oldAngle + _snowman;
   }

   public static boolean canMobSpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
      BlockPos _snowman = pos.down();
      return spawnReason == SpawnReason.SPAWNER || world.getBlockState(_snowman).allowsSpawning(world, _snowman, type);
   }

   public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
      return true;
   }

   public boolean canSpawn(WorldView world) {
      return !world.containsFluid(this.getBoundingBox()) && world.intersectsEntities(this);
   }

   public int getLimitPerChunk() {
      return 4;
   }

   public boolean spawnsTooManyForEachTry(int count) {
      return false;
   }

   @Override
   public int getSafeFallDistance() {
      if (this.getTarget() == null) {
         return 3;
      } else {
         int _snowman = (int)(this.getHealth() - this.getMaxHealth() * 0.33F);
         _snowman -= (3 - this.world.getDifficulty().getId()) * 4;
         if (_snowman < 0) {
            _snowman = 0;
         }

         return _snowman + 3;
      }
   }

   @Override
   public Iterable<ItemStack> getItemsHand() {
      return this.handItems;
   }

   @Override
   public Iterable<ItemStack> getArmorItems() {
      return this.armorItems;
   }

   @Override
   public ItemStack getEquippedStack(EquipmentSlot slot) {
      switch (slot.getType()) {
         case HAND:
            return this.handItems.get(slot.getEntitySlotId());
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
            this.handItems.set(slot.getEntitySlotId(), stack);
            break;
         case ARMOR:
            this.armorItems.set(slot.getEntitySlotId(), stack);
      }
   }

   @Override
   protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
      super.dropEquipment(source, lootingMultiplier, allowDrops);

      for (EquipmentSlot _snowman : EquipmentSlot.values()) {
         ItemStack _snowmanx = this.getEquippedStack(_snowman);
         float _snowmanxx = this.getDropChance(_snowman);
         boolean _snowmanxxx = _snowmanxx > 1.0F;
         if (!_snowmanx.isEmpty()
            && !EnchantmentHelper.hasVanishingCurse(_snowmanx)
            && (allowDrops || _snowmanxxx)
            && Math.max(this.random.nextFloat() - (float)lootingMultiplier * 0.01F, 0.0F) < _snowmanxx) {
            if (!_snowmanxxx && _snowmanx.isDamageable()) {
               _snowmanx.setDamage(_snowmanx.getMaxDamage() - this.random.nextInt(1 + this.random.nextInt(Math.max(_snowmanx.getMaxDamage() - 3, 1))));
            }

            this.dropStack(_snowmanx);
            this.equipStack(_snowman, ItemStack.EMPTY);
         }
      }
   }

   protected float getDropChance(EquipmentSlot slot) {
      float _snowman;
      switch (slot.getType()) {
         case HAND:
            _snowman = this.handDropChances[slot.getEntitySlotId()];
            break;
         case ARMOR:
            _snowman = this.armorDropChances[slot.getEntitySlotId()];
            break;
         default:
            _snowman = 0.0F;
      }

      return _snowman;
   }

   protected void initEquipment(LocalDifficulty difficulty) {
      if (this.random.nextFloat() < 0.15F * difficulty.getClampedLocalDifficulty()) {
         int _snowman = this.random.nextInt(2);
         float _snowmanx = this.world.getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
         if (this.random.nextFloat() < 0.095F) {
            _snowman++;
         }

         if (this.random.nextFloat() < 0.095F) {
            _snowman++;
         }

         if (this.random.nextFloat() < 0.095F) {
            _snowman++;
         }

         boolean _snowmanxx = true;

         for (EquipmentSlot _snowmanxxx : EquipmentSlot.values()) {
            if (_snowmanxxx.getType() == EquipmentSlot.Type.ARMOR) {
               ItemStack _snowmanxxxx = this.getEquippedStack(_snowmanxxx);
               if (!_snowmanxx && this.random.nextFloat() < _snowmanx) {
                  break;
               }

               _snowmanxx = false;
               if (_snowmanxxxx.isEmpty()) {
                  Item _snowmanxxxxx = getEquipmentForSlot(_snowmanxxx, _snowman);
                  if (_snowmanxxxxx != null) {
                     this.equipStack(_snowmanxxx, new ItemStack(_snowmanxxxxx));
                  }
               }
            }
         }
      }
   }

   public static EquipmentSlot getPreferredEquipmentSlot(ItemStack stack) {
      Item _snowman = stack.getItem();
      if (_snowman != Blocks.CARVED_PUMPKIN.asItem() && (!(_snowman instanceof BlockItem) || !(((BlockItem)_snowman).getBlock() instanceof AbstractSkullBlock))) {
         if (_snowman instanceof ArmorItem) {
            return ((ArmorItem)_snowman).getSlotType();
         } else if (_snowman == Items.ELYTRA) {
            return EquipmentSlot.CHEST;
         } else {
            return _snowman == Items.SHIELD ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
         }
      } else {
         return EquipmentSlot.HEAD;
      }
   }

   @Nullable
   public static Item getEquipmentForSlot(EquipmentSlot equipmentSlot, int equipmentLevel) {
      switch (equipmentSlot) {
         case HEAD:
            if (equipmentLevel == 0) {
               return Items.LEATHER_HELMET;
            } else if (equipmentLevel == 1) {
               return Items.GOLDEN_HELMET;
            } else if (equipmentLevel == 2) {
               return Items.CHAINMAIL_HELMET;
            } else if (equipmentLevel == 3) {
               return Items.IRON_HELMET;
            } else if (equipmentLevel == 4) {
               return Items.DIAMOND_HELMET;
            }
         case CHEST:
            if (equipmentLevel == 0) {
               return Items.LEATHER_CHESTPLATE;
            } else if (equipmentLevel == 1) {
               return Items.GOLDEN_CHESTPLATE;
            } else if (equipmentLevel == 2) {
               return Items.CHAINMAIL_CHESTPLATE;
            } else if (equipmentLevel == 3) {
               return Items.IRON_CHESTPLATE;
            } else if (equipmentLevel == 4) {
               return Items.DIAMOND_CHESTPLATE;
            }
         case LEGS:
            if (equipmentLevel == 0) {
               return Items.LEATHER_LEGGINGS;
            } else if (equipmentLevel == 1) {
               return Items.GOLDEN_LEGGINGS;
            } else if (equipmentLevel == 2) {
               return Items.CHAINMAIL_LEGGINGS;
            } else if (equipmentLevel == 3) {
               return Items.IRON_LEGGINGS;
            } else if (equipmentLevel == 4) {
               return Items.DIAMOND_LEGGINGS;
            }
         case FEET:
            if (equipmentLevel == 0) {
               return Items.LEATHER_BOOTS;
            } else if (equipmentLevel == 1) {
               return Items.GOLDEN_BOOTS;
            } else if (equipmentLevel == 2) {
               return Items.CHAINMAIL_BOOTS;
            } else if (equipmentLevel == 3) {
               return Items.IRON_BOOTS;
            } else if (equipmentLevel == 4) {
               return Items.DIAMOND_BOOTS;
            }
         default:
            return null;
      }
   }

   protected void updateEnchantments(LocalDifficulty difficulty) {
      float _snowman = difficulty.getClampedLocalDifficulty();
      this.method_30759(_snowman);

      for (EquipmentSlot _snowmanx : EquipmentSlot.values()) {
         if (_snowmanx.getType() == EquipmentSlot.Type.ARMOR) {
            this.method_30758(_snowman, _snowmanx);
         }
      }
   }

   protected void method_30759(float _snowman) {
      if (!this.getMainHandStack().isEmpty() && this.random.nextFloat() < 0.25F * _snowman) {
         this.equipStack(
            EquipmentSlot.MAINHAND, EnchantmentHelper.enchant(this.random, this.getMainHandStack(), (int)(5.0F + _snowman * (float)this.random.nextInt(18)), false)
         );
      }
   }

   protected void method_30758(float _snowman, EquipmentSlot _snowman) {
      ItemStack _snowmanxx = this.getEquippedStack(_snowman);
      if (!_snowmanxx.isEmpty() && this.random.nextFloat() < 0.5F * _snowman) {
         this.equipStack(_snowman, EnchantmentHelper.enchant(this.random, _snowmanxx, (int)(5.0F + _snowman * (float)this.random.nextInt(18)), false));
      }
   }

   @Nullable
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      this.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE)
         .addPersistentModifier(
            new EntityAttributeModifier("Random spawn bonus", this.random.nextGaussian() * 0.05, EntityAttributeModifier.Operation.MULTIPLY_BASE)
         );
      if (this.random.nextFloat() < 0.05F) {
         this.setLeftHanded(true);
      } else {
         this.setLeftHanded(false);
      }

      return entityData;
   }

   public boolean canBeControlledByRider() {
      return false;
   }

   public void setPersistent() {
      this.persistent = true;
   }

   public void setEquipmentDropChance(EquipmentSlot slot, float chance) {
      switch (slot.getType()) {
         case HAND:
            this.handDropChances[slot.getEntitySlotId()] = chance;
            break;
         case ARMOR:
            this.armorDropChances[slot.getEntitySlotId()] = chance;
      }
   }

   public boolean canPickUpLoot() {
      return this.pickUpLoot;
   }

   public void setCanPickUpLoot(boolean pickUpLoot) {
      this.pickUpLoot = pickUpLoot;
   }

   @Override
   public boolean canEquip(ItemStack stack) {
      EquipmentSlot _snowman = getPreferredEquipmentSlot(stack);
      return this.getEquippedStack(_snowman).isEmpty() && this.canPickUpLoot();
   }

   public boolean isPersistent() {
      return this.persistent;
   }

   @Override
   public final ActionResult interact(PlayerEntity player, Hand hand) {
      if (!this.isAlive()) {
         return ActionResult.PASS;
      } else if (this.getHoldingEntity() == player) {
         this.detachLeash(true, !player.abilities.creativeMode);
         return ActionResult.success(this.world.isClient);
      } else {
         ActionResult _snowman = this.method_29506(player, hand);
         if (_snowman.isAccepted()) {
            return _snowman;
         } else {
            _snowman = this.interactMob(player, hand);
            return _snowman.isAccepted() ? _snowman : super.interact(player, hand);
         }
      }
   }

   private ActionResult method_29506(PlayerEntity _snowman, Hand _snowman) {
      ItemStack _snowmanxx = _snowman.getStackInHand(_snowman);
      if (_snowmanxx.getItem() == Items.LEAD && this.canBeLeashedBy(_snowman)) {
         this.attachLeash(_snowman, true);
         _snowmanxx.decrement(1);
         return ActionResult.success(this.world.isClient);
      } else {
         if (_snowmanxx.getItem() == Items.NAME_TAG) {
            ActionResult _snowmanxxx = _snowmanxx.useOnEntity(_snowman, this, _snowman);
            if (_snowmanxxx.isAccepted()) {
               return _snowmanxxx;
            }
         }

         if (_snowmanxx.getItem() instanceof SpawnEggItem) {
            if (this.world instanceof ServerWorld) {
               SpawnEggItem _snowmanxxx = (SpawnEggItem)_snowmanxx.getItem();
               Optional<MobEntity> _snowmanxxxx = _snowmanxxx.spawnBaby(_snowman, this, (EntityType<? extends MobEntity>)this.getType(), (ServerWorld)this.world, this.getPos(), _snowmanxx);
               _snowmanxxxx.ifPresent(_snowmanxxxxx -> this.onPlayerSpawnedChild(_snowman, _snowmanxxxxx));
               return _snowmanxxxx.isPresent() ? ActionResult.SUCCESS : ActionResult.PASS;
            } else {
               return ActionResult.CONSUME;
            }
         } else {
            return ActionResult.PASS;
         }
      }
   }

   protected void onPlayerSpawnedChild(PlayerEntity player, MobEntity child) {
   }

   protected ActionResult interactMob(PlayerEntity player, Hand hand) {
      return ActionResult.PASS;
   }

   public boolean isInWalkTargetRange() {
      return this.isInWalkTargetRange(this.getBlockPos());
   }

   public boolean isInWalkTargetRange(BlockPos pos) {
      return this.positionTargetRange == -1.0F
         ? true
         : this.positionTarget.getSquaredDistance(pos) < (double)(this.positionTargetRange * this.positionTargetRange);
   }

   public void setPositionTarget(BlockPos target, int range) {
      this.positionTarget = target;
      this.positionTargetRange = (float)range;
   }

   public BlockPos getPositionTarget() {
      return this.positionTarget;
   }

   public float getPositionTargetRange() {
      return this.positionTargetRange;
   }

   public boolean hasPositionTarget() {
      return this.positionTargetRange != -1.0F;
   }

   @Nullable
   public <T extends MobEntity> T method_29243(EntityType<T> _snowman, boolean _snowman) {
      if (this.removed) {
         return null;
      } else {
         T _snowmanxx = (T)_snowman.create(this.world);
         _snowmanxx.copyPositionAndRotation(this);
         _snowmanxx.setBaby(this.isBaby());
         _snowmanxx.setAiDisabled(this.isAiDisabled());
         if (this.hasCustomName()) {
            _snowmanxx.setCustomName(this.getCustomName());
            _snowmanxx.setCustomNameVisible(this.isCustomNameVisible());
         }

         if (this.isPersistent()) {
            _snowmanxx.setPersistent();
         }

         _snowmanxx.setInvulnerable(this.isInvulnerable());
         if (_snowman) {
            _snowmanxx.setCanPickUpLoot(this.canPickUpLoot());

            for (EquipmentSlot _snowmanxxx : EquipmentSlot.values()) {
               ItemStack _snowmanxxxx = this.getEquippedStack(_snowmanxxx);
               if (!_snowmanxxxx.isEmpty()) {
                  _snowmanxx.equipStack(_snowmanxxx, _snowmanxxxx.copy());
                  _snowmanxx.setEquipmentDropChance(_snowmanxxx, this.getDropChance(_snowmanxxx));
                  _snowmanxxxx.setCount(0);
               }
            }
         }

         this.world.spawnEntity(_snowmanxx);
         if (this.hasVehicle()) {
            Entity _snowmanxxxx = this.getVehicle();
            this.stopRiding();
            _snowmanxx.startRiding(_snowmanxxxx, true);
         }

         this.remove();
         return _snowmanxx;
      }
   }

   protected void updateLeash() {
      if (this.leashTag != null) {
         this.deserializeLeashTag();
      }

      if (this.holdingEntity != null) {
         if (!this.isAlive() || !this.holdingEntity.isAlive()) {
            this.detachLeash(true, true);
         }
      }
   }

   public void detachLeash(boolean sendPacket, boolean dropItem) {
      if (this.holdingEntity != null) {
         this.teleporting = false;
         if (!(this.holdingEntity instanceof PlayerEntity)) {
            this.holdingEntity.teleporting = false;
         }

         this.holdingEntity = null;
         this.leashTag = null;
         if (!this.world.isClient && dropItem) {
            this.dropItem(Items.LEAD);
         }

         if (!this.world.isClient && sendPacket && this.world instanceof ServerWorld) {
            ((ServerWorld)this.world).getChunkManager().sendToOtherNearbyPlayers(this, new EntityAttachS2CPacket(this, null));
         }
      }
   }

   public boolean canBeLeashedBy(PlayerEntity player) {
      return !this.isLeashed() && !(this instanceof Monster);
   }

   public boolean isLeashed() {
      return this.holdingEntity != null;
   }

   @Nullable
   public Entity getHoldingEntity() {
      if (this.holdingEntity == null && this.holdingEntityId != 0 && this.world.isClient) {
         this.holdingEntity = this.world.getEntityById(this.holdingEntityId);
      }

      return this.holdingEntity;
   }

   public void attachLeash(Entity entity, boolean sendPacket) {
      this.holdingEntity = entity;
      this.leashTag = null;
      this.teleporting = true;
      if (!(this.holdingEntity instanceof PlayerEntity)) {
         this.holdingEntity.teleporting = true;
      }

      if (!this.world.isClient && sendPacket && this.world instanceof ServerWorld) {
         ((ServerWorld)this.world).getChunkManager().sendToOtherNearbyPlayers(this, new EntityAttachS2CPacket(this, this.holdingEntity));
      }

      if (this.hasVehicle()) {
         this.stopRiding();
      }
   }

   public void setHoldingEntityId(int id) {
      this.holdingEntityId = id;
      this.detachLeash(false, false);
   }

   @Override
   public boolean startRiding(Entity entity, boolean force) {
      boolean _snowman = super.startRiding(entity, force);
      if (_snowman && this.isLeashed()) {
         this.detachLeash(true, true);
      }

      return _snowman;
   }

   private void deserializeLeashTag() {
      if (this.leashTag != null && this.world instanceof ServerWorld) {
         if (this.leashTag.containsUuid("UUID")) {
            UUID _snowman = this.leashTag.getUuid("UUID");
            Entity _snowmanx = ((ServerWorld)this.world).getEntity(_snowman);
            if (_snowmanx != null) {
               this.attachLeash(_snowmanx, true);
               return;
            }
         } else if (this.leashTag.contains("X", 99) && this.leashTag.contains("Y", 99) && this.leashTag.contains("Z", 99)) {
            BlockPos _snowman = new BlockPos(this.leashTag.getInt("X"), this.leashTag.getInt("Y"), this.leashTag.getInt("Z"));
            this.attachLeash(LeashKnotEntity.getOrCreate(this.world, _snowman), true);
            return;
         }

         if (this.age > 100) {
            this.dropItem(Items.LEAD);
            this.leashTag = null;
         }
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

      if (!item.isEmpty() && !canEquipmentSlotContain(_snowman, item) && _snowman != EquipmentSlot.HEAD) {
         return false;
      } else {
         this.equipStack(_snowman, item);
         return true;
      }
   }

   @Override
   public boolean isLogicalSideForUpdatingMovement() {
      return this.canBeControlledByRider() && super.isLogicalSideForUpdatingMovement();
   }

   public static boolean canEquipmentSlotContain(EquipmentSlot slot, ItemStack item) {
      EquipmentSlot _snowman = getPreferredEquipmentSlot(item);
      return _snowman == slot || _snowman == EquipmentSlot.MAINHAND && slot == EquipmentSlot.OFFHAND || _snowman == EquipmentSlot.OFFHAND && slot == EquipmentSlot.MAINHAND;
   }

   @Override
   public boolean canMoveVoluntarily() {
      return super.canMoveVoluntarily() && !this.isAiDisabled();
   }

   public void setAiDisabled(boolean aiDisabled) {
      byte _snowman = this.dataTracker.get(MOB_FLAGS);
      this.dataTracker.set(MOB_FLAGS, aiDisabled ? (byte)(_snowman | 1) : (byte)(_snowman & -2));
   }

   public void setLeftHanded(boolean leftHanded) {
      byte _snowman = this.dataTracker.get(MOB_FLAGS);
      this.dataTracker.set(MOB_FLAGS, leftHanded ? (byte)(_snowman | 2) : (byte)(_snowman & -3));
   }

   public void setAttacking(boolean attacking) {
      byte _snowman = this.dataTracker.get(MOB_FLAGS);
      this.dataTracker.set(MOB_FLAGS, attacking ? (byte)(_snowman | 4) : (byte)(_snowman & -5));
   }

   public boolean isAiDisabled() {
      return (this.dataTracker.get(MOB_FLAGS) & 1) != 0;
   }

   public boolean isLeftHanded() {
      return (this.dataTracker.get(MOB_FLAGS) & 2) != 0;
   }

   public boolean isAttacking() {
      return (this.dataTracker.get(MOB_FLAGS) & 4) != 0;
   }

   public void setBaby(boolean baby) {
   }

   @Override
   public Arm getMainArm() {
      return this.isLeftHanded() ? Arm.LEFT : Arm.RIGHT;
   }

   @Override
   public boolean canTarget(LivingEntity target) {
      return target.getType() == EntityType.PLAYER && ((PlayerEntity)target).abilities.invulnerable ? false : super.canTarget(target);
   }

   @Override
   public boolean tryAttack(Entity target) {
      float _snowman = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
      float _snowmanx = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
      if (target instanceof LivingEntity) {
         _snowman += EnchantmentHelper.getAttackDamage(this.getMainHandStack(), ((LivingEntity)target).getGroup());
         _snowmanx += (float)EnchantmentHelper.getKnockback(this);
      }

      int _snowmanxx = EnchantmentHelper.getFireAspect(this);
      if (_snowmanxx > 0) {
         target.setOnFireFor(_snowmanxx * 4);
      }

      boolean _snowmanxxx = target.damage(DamageSource.mob(this), _snowman);
      if (_snowmanxxx) {
         if (_snowmanx > 0.0F && target instanceof LivingEntity) {
            ((LivingEntity)target)
               .takeKnockback(
                  _snowmanx * 0.5F, (double)MathHelper.sin(this.yaw * (float) (Math.PI / 180.0)), (double)(-MathHelper.cos(this.yaw * (float) (Math.PI / 180.0)))
               );
            this.setVelocity(this.getVelocity().multiply(0.6, 1.0, 0.6));
         }

         if (target instanceof PlayerEntity) {
            PlayerEntity _snowmanxxxx = (PlayerEntity)target;
            this.disablePlayerShield(_snowmanxxxx, this.getMainHandStack(), _snowmanxxxx.isUsingItem() ? _snowmanxxxx.getActiveItem() : ItemStack.EMPTY);
         }

         this.dealDamage(this, target);
         this.onAttacking(target);
      }

      return _snowmanxxx;
   }

   private void disablePlayerShield(PlayerEntity player, ItemStack mobStack, ItemStack playerStack) {
      if (!mobStack.isEmpty() && !playerStack.isEmpty() && mobStack.getItem() instanceof AxeItem && playerStack.getItem() == Items.SHIELD) {
         float _snowman = 0.25F + (float)EnchantmentHelper.getEfficiency(this) * 0.05F;
         if (this.random.nextFloat() < _snowman) {
            player.getItemCooldownManager().set(Items.SHIELD, 100);
            this.world.sendEntityStatus(player, (byte)30);
         }
      }
   }

   protected boolean isAffectedByDaylight() {
      if (this.world.isDay() && !this.world.isClient) {
         float _snowman = this.getBrightnessAtEyes();
         BlockPos _snowmanx = this.getVehicle() instanceof BoatEntity
            ? new BlockPos(this.getX(), (double)Math.round(this.getY()), this.getZ()).up()
            : new BlockPos(this.getX(), (double)Math.round(this.getY()), this.getZ());
         if (_snowman > 0.5F && this.random.nextFloat() * 30.0F < (_snowman - 0.4F) * 2.0F && this.world.isSkyVisible(_snowmanx)) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected void swimUpward(Tag<Fluid> fluid) {
      if (this.getNavigation().canSwim()) {
         super.swimUpward(fluid);
      } else {
         this.setVelocity(this.getVelocity().add(0.0, 0.3, 0.0));
      }
   }

   @Override
   protected void method_30076() {
      super.method_30076();
      this.detachLeash(true, false);
   }
}
