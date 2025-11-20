package net.minecraft.entity.passive;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class HorseEntity extends HorseBaseEntity {
   private static final UUID HORSE_ARMOR_BONUS_ID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
   private static final TrackedData<Integer> VARIANT = DataTracker.registerData(HorseEntity.class, TrackedDataHandlerRegistry.INTEGER);

   public HorseEntity(EntityType<? extends HorseEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   protected void initAttributes() {
      this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue((double)this.getChildHealthBonus());
      this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(this.getChildMovementSpeedBonus());
      this.getAttributeInstance(EntityAttributes.HORSE_JUMP_STRENGTH).setBaseValue(this.getChildJumpStrengthBonus());
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(VARIANT, 0);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("Variant", this.getVariant());
      if (!this.items.getStack(1).isEmpty()) {
         tag.put("ArmorItem", this.items.getStack(1).toTag(new CompoundTag()));
      }
   }

   public ItemStack getArmorType() {
      return this.getEquippedStack(EquipmentSlot.CHEST);
   }

   private void equipArmor(ItemStack stack) {
      this.equipStack(EquipmentSlot.CHEST, stack);
      this.setEquipmentDropChance(EquipmentSlot.CHEST, 0.0F);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.setVariant(tag.getInt("Variant"));
      if (tag.contains("ArmorItem", 10)) {
         ItemStack _snowman = ItemStack.fromTag(tag.getCompound("ArmorItem"));
         if (!_snowman.isEmpty() && this.isHorseArmor(_snowman)) {
            this.items.setStack(1, _snowman);
         }
      }

      this.updateSaddle();
   }

   private void setVariant(int variant) {
      this.dataTracker.set(VARIANT, variant);
   }

   private int getVariant() {
      return this.dataTracker.get(VARIANT);
   }

   private void setVariant(HorseColor color, HorseMarking marking) {
      this.setVariant(color.getIndex() & 0xFF | marking.getIndex() << 8 & 0xFF00);
   }

   public HorseColor getColor() {
      return HorseColor.byIndex(this.getVariant() & 0xFF);
   }

   public HorseMarking getMarking() {
      return HorseMarking.byIndex((this.getVariant() & 0xFF00) >> 8);
   }

   @Override
   protected void updateSaddle() {
      if (!this.world.isClient) {
         super.updateSaddle();
         this.setArmorTypeFromStack(this.items.getStack(1));
         this.setEquipmentDropChance(EquipmentSlot.CHEST, 0.0F);
      }
   }

   private void setArmorTypeFromStack(ItemStack stack) {
      this.equipArmor(stack);
      if (!this.world.isClient) {
         this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).removeModifier(HORSE_ARMOR_BONUS_ID);
         if (this.isHorseArmor(stack)) {
            int _snowman = ((HorseArmorItem)stack.getItem()).getBonus();
            if (_snowman != 0) {
               this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR)
                  .addTemporaryModifier(
                     new EntityAttributeModifier(HORSE_ARMOR_BONUS_ID, "Horse armor bonus", (double)_snowman, EntityAttributeModifier.Operation.ADDITION)
                  );
            }
         }
      }
   }

   @Override
   public void onInventoryChanged(Inventory sender) {
      ItemStack _snowman = this.getArmorType();
      super.onInventoryChanged(sender);
      ItemStack _snowmanx = this.getArmorType();
      if (this.age > 20 && this.isHorseArmor(_snowmanx) && _snowman != _snowmanx) {
         this.playSound(SoundEvents.ENTITY_HORSE_ARMOR, 0.5F, 1.0F);
      }
   }

   @Override
   protected void playWalkSound(BlockSoundGroup group) {
      super.playWalkSound(group);
      if (this.random.nextInt(10) == 0) {
         this.playSound(SoundEvents.ENTITY_HORSE_BREATHE, group.getVolume() * 0.6F, group.getPitch());
      }
   }

   @Override
   protected SoundEvent getAmbientSound() {
      super.getAmbientSound();
      return SoundEvents.ENTITY_HORSE_AMBIENT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      super.getDeathSound();
      return SoundEvents.ENTITY_HORSE_DEATH;
   }

   @Nullable
   @Override
   protected SoundEvent getEatSound() {
      return SoundEvents.ENTITY_HORSE_EAT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      super.getHurtSound(source);
      return SoundEvents.ENTITY_HORSE_HURT;
   }

   @Override
   protected SoundEvent getAngrySound() {
      super.getAngrySound();
      return SoundEvents.ENTITY_HORSE_ANGRY;
   }

   @Override
   public ActionResult interactMob(PlayerEntity player, Hand hand) {
      ItemStack _snowman = player.getStackInHand(hand);
      if (!this.isBaby()) {
         if (this.isTame() && player.shouldCancelInteraction()) {
            this.openInventory(player);
            return ActionResult.success(this.world.isClient);
         }

         if (this.hasPassengers()) {
            return super.interactMob(player, hand);
         }
      }

      if (!_snowman.isEmpty()) {
         if (this.isBreedingItem(_snowman)) {
            return this.method_30009(player, _snowman);
         }

         ActionResult _snowmanx = _snowman.useOnEntity(player, this, hand);
         if (_snowmanx.isAccepted()) {
            return _snowmanx;
         }

         if (!this.isTame()) {
            this.playAngrySound();
            return ActionResult.success(this.world.isClient);
         }

         boolean _snowmanxx = !this.isBaby() && !this.isSaddled() && _snowman.getItem() == Items.SADDLE;
         if (this.isHorseArmor(_snowman) || _snowmanxx) {
            this.openInventory(player);
            return ActionResult.success(this.world.isClient);
         }
      }

      if (this.isBaby()) {
         return super.interactMob(player, hand);
      } else {
         this.putPlayerOnBack(player);
         return ActionResult.success(this.world.isClient);
      }
   }

   @Override
   public boolean canBreedWith(AnimalEntity other) {
      if (other == this) {
         return false;
      } else {
         return !(other instanceof DonkeyEntity) && !(other instanceof HorseEntity) ? false : this.canBreed() && ((HorseBaseEntity)other).canBreed();
      }
   }

   @Override
   public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
      HorseBaseEntity _snowman;
      if (entity instanceof DonkeyEntity) {
         _snowman = EntityType.MULE.create(world);
      } else {
         HorseEntity _snowmanx = (HorseEntity)entity;
         _snowman = EntityType.HORSE.create(world);
         int _snowmanxx = this.random.nextInt(9);
         HorseColor _snowmanxxx;
         if (_snowmanxx < 4) {
            _snowmanxxx = this.getColor();
         } else if (_snowmanxx < 8) {
            _snowmanxxx = _snowmanx.getColor();
         } else {
            _snowmanxxx = Util.getRandom(HorseColor.values(), this.random);
         }

         int _snowmanxxxx = this.random.nextInt(5);
         HorseMarking _snowmanxxxxx;
         if (_snowmanxxxx < 2) {
            _snowmanxxxxx = this.getMarking();
         } else if (_snowmanxxxx < 4) {
            _snowmanxxxxx = _snowmanx.getMarking();
         } else {
            _snowmanxxxxx = Util.getRandom(HorseMarking.values(), this.random);
         }

         ((HorseEntity)_snowman).setVariant(_snowmanxxx, _snowmanxxxxx);
      }

      this.setChildAttributes(entity, _snowman);
      return _snowman;
   }

   @Override
   public boolean hasArmorSlot() {
      return true;
   }

   @Override
   public boolean isHorseArmor(ItemStack item) {
      return item.getItem() instanceof HorseArmorItem;
   }

   @Nullable
   @Override
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      HorseColor _snowman;
      if (entityData instanceof HorseEntity.HorseData) {
         _snowman = ((HorseEntity.HorseData)entityData).color;
      } else {
         _snowman = Util.getRandom(HorseColor.values(), this.random);
         entityData = new HorseEntity.HorseData(_snowman);
      }

      this.setVariant(_snowman, Util.getRandom(HorseMarking.values(), this.random));
      return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
   }

   public static class HorseData extends PassiveEntity.PassiveData {
      public final HorseColor color;

      public HorseData(HorseColor color) {
         super(true);
         this.color = color;
      }
   }
}
