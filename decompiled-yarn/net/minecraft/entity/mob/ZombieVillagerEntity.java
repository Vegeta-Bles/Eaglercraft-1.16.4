package net.minecraft.entity.mob;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityInteraction;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class ZombieVillagerEntity extends ZombieEntity implements VillagerDataContainer {
   private static final TrackedData<Boolean> CONVERTING = DataTracker.registerData(ZombieVillagerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final TrackedData<VillagerData> VILLAGER_DATA = DataTracker.registerData(ZombieVillagerEntity.class, TrackedDataHandlerRegistry.VILLAGER_DATA);
   private int conversionTimer;
   private UUID converter;
   private Tag gossipData;
   private CompoundTag offerData;
   private int xp;

   public ZombieVillagerEntity(EntityType<? extends ZombieVillagerEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.setVillagerData(this.getVillagerData().withProfession(Registry.VILLAGER_PROFESSION.getRandom(this.random)));
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(CONVERTING, false);
      this.dataTracker.startTracking(VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      VillagerData.CODEC.encodeStart(NbtOps.INSTANCE, this.getVillagerData()).resultOrPartial(LOGGER::error).ifPresent(_snowmanx -> tag.put("VillagerData", _snowmanx));
      if (this.offerData != null) {
         tag.put("Offers", this.offerData);
      }

      if (this.gossipData != null) {
         tag.put("Gossips", this.gossipData);
      }

      tag.putInt("ConversionTime", this.isConverting() ? this.conversionTimer : -1);
      if (this.converter != null) {
         tag.putUuid("ConversionPlayer", this.converter);
      }

      tag.putInt("Xp", this.xp);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      if (tag.contains("VillagerData", 10)) {
         DataResult<VillagerData> _snowman = VillagerData.CODEC.parse(new Dynamic(NbtOps.INSTANCE, tag.get("VillagerData")));
         _snowman.resultOrPartial(LOGGER::error).ifPresent(this::setVillagerData);
      }

      if (tag.contains("Offers", 10)) {
         this.offerData = tag.getCompound("Offers");
      }

      if (tag.contains("Gossips", 10)) {
         this.gossipData = tag.getList("Gossips", 10);
      }

      if (tag.contains("ConversionTime", 99) && tag.getInt("ConversionTime") > -1) {
         this.setConverting(tag.containsUuid("ConversionPlayer") ? tag.getUuid("ConversionPlayer") : null, tag.getInt("ConversionTime"));
      }

      if (tag.contains("Xp", 3)) {
         this.xp = tag.getInt("Xp");
      }
   }

   @Override
   public void tick() {
      if (!this.world.isClient && this.isAlive() && this.isConverting()) {
         int _snowman = this.getConversionRate();
         this.conversionTimer -= _snowman;
         if (this.conversionTimer <= 0) {
            this.finishConversion((ServerWorld)this.world);
         }
      }

      super.tick();
   }

   @Override
   public ActionResult interactMob(PlayerEntity player, Hand hand) {
      ItemStack _snowman = player.getStackInHand(hand);
      if (_snowman.getItem() == Items.GOLDEN_APPLE) {
         if (this.hasStatusEffect(StatusEffects.WEAKNESS)) {
            if (!player.abilities.creativeMode) {
               _snowman.decrement(1);
            }

            if (!this.world.isClient) {
               this.setConverting(player.getUuid(), this.random.nextInt(2401) + 3600);
            }

            return ActionResult.SUCCESS;
         } else {
            return ActionResult.CONSUME;
         }
      } else {
         return super.interactMob(player, hand);
      }
   }

   @Override
   protected boolean canConvertInWater() {
      return false;
   }

   @Override
   public boolean canImmediatelyDespawn(double distanceSquared) {
      return !this.isConverting() && this.xp == 0;
   }

   public boolean isConverting() {
      return this.getDataTracker().get(CONVERTING);
   }

   private void setConverting(@Nullable UUID uuid, int delay) {
      this.converter = uuid;
      this.conversionTimer = delay;
      this.getDataTracker().set(CONVERTING, true);
      this.removeStatusEffect(StatusEffects.WEAKNESS);
      this.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, delay, Math.min(this.world.getDifficulty().getId() - 1, 0)));
      this.world.sendEntityStatus(this, (byte)16);
   }

   @Override
   public void handleStatus(byte status) {
      if (status == 16) {
         if (!this.isSilent()) {
            this.world
               .playSound(
                  this.getX(),
                  this.getEyeY(),
                  this.getZ(),
                  SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE,
                  this.getSoundCategory(),
                  1.0F + this.random.nextFloat(),
                  this.random.nextFloat() * 0.7F + 0.3F,
                  false
               );
         }
      } else {
         super.handleStatus(status);
      }
   }

   private void finishConversion(ServerWorld world) {
      VillagerEntity _snowman = this.method_29243(EntityType.VILLAGER, false);

      for (EquipmentSlot _snowmanx : EquipmentSlot.values()) {
         ItemStack _snowmanxx = this.getEquippedStack(_snowmanx);
         if (!_snowmanxx.isEmpty()) {
            if (EnchantmentHelper.hasBindingCurse(_snowmanxx)) {
               _snowman.equip(_snowmanx.getEntitySlotId() + 300, _snowmanxx);
            } else {
               double _snowmanxxx = (double)this.getDropChance(_snowmanx);
               if (_snowmanxxx > 1.0) {
                  this.dropStack(_snowmanxx);
               }
            }
         }
      }

      _snowman.setVillagerData(this.getVillagerData());
      if (this.gossipData != null) {
         _snowman.setGossipDataFromTag(this.gossipData);
      }

      if (this.offerData != null) {
         _snowman.setOffers(new TradeOfferList(this.offerData));
      }

      _snowman.setExperience(this.xp);
      _snowman.initialize(world, world.getLocalDifficulty(_snowman.getBlockPos()), SpawnReason.CONVERSION, null, null);
      if (this.converter != null) {
         PlayerEntity _snowmanxx = world.getPlayerByUuid(this.converter);
         if (_snowmanxx instanceof ServerPlayerEntity) {
            Criteria.CURED_ZOMBIE_VILLAGER.trigger((ServerPlayerEntity)_snowmanxx, this, _snowman);
            world.handleInteraction(EntityInteraction.ZOMBIE_VILLAGER_CURED, _snowmanxx, _snowman);
         }
      }

      _snowman.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0));
      if (!this.isSilent()) {
         world.syncWorldEvent(null, 1027, this.getBlockPos(), 0);
      }
   }

   private int getConversionRate() {
      int _snowman = 1;
      if (this.random.nextFloat() < 0.01F) {
         int _snowmanx = 0;
         BlockPos.Mutable _snowmanxx = new BlockPos.Mutable();

         for (int _snowmanxxx = (int)this.getX() - 4; _snowmanxxx < (int)this.getX() + 4 && _snowmanx < 14; _snowmanxxx++) {
            for (int _snowmanxxxx = (int)this.getY() - 4; _snowmanxxxx < (int)this.getY() + 4 && _snowmanx < 14; _snowmanxxxx++) {
               for (int _snowmanxxxxx = (int)this.getZ() - 4; _snowmanxxxxx < (int)this.getZ() + 4 && _snowmanx < 14; _snowmanxxxxx++) {
                  Block _snowmanxxxxxx = this.world.getBlockState(_snowmanxx.set(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx)).getBlock();
                  if (_snowmanxxxxxx == Blocks.IRON_BARS || _snowmanxxxxxx instanceof BedBlock) {
                     if (this.random.nextFloat() < 0.3F) {
                        _snowman++;
                     }

                     _snowmanx++;
                  }
               }
            }
         }
      }

      return _snowman;
   }

   @Override
   protected float getSoundPitch() {
      return this.isBaby()
         ? (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 2.0F
         : (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
   }

   @Override
   public SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_ZOMBIE_VILLAGER_AMBIENT;
   }

   @Override
   public SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_ZOMBIE_VILLAGER_HURT;
   }

   @Override
   public SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_ZOMBIE_VILLAGER_DEATH;
   }

   @Override
   public SoundEvent getStepSound() {
      return SoundEvents.ENTITY_ZOMBIE_VILLAGER_STEP;
   }

   @Override
   protected ItemStack getSkull() {
      return ItemStack.EMPTY;
   }

   public void setOfferData(CompoundTag offerTag) {
      this.offerData = offerTag;
   }

   public void setGossipData(Tag gossipTag) {
      this.gossipData = gossipTag;
   }

   @Nullable
   @Override
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      this.setVillagerData(this.getVillagerData().withType(VillagerType.forBiome(world.method_31081(this.getBlockPos()))));
      return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
   }

   public void setVillagerData(VillagerData data) {
      VillagerData _snowman = this.getVillagerData();
      if (_snowman.getProfession() != data.getProfession()) {
         this.offerData = null;
      }

      this.dataTracker.set(VILLAGER_DATA, data);
   }

   @Override
   public VillagerData getVillagerData() {
      return this.dataTracker.get(VILLAGER_DATA);
   }

   public void setXp(int xp) {
      this.xp = xp;
   }
}
