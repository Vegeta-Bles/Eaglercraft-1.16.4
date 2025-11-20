package net.minecraft.entity.passive;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Npc;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.Merchant;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffers;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public abstract class MerchantEntity extends PassiveEntity implements Npc, Merchant {
   private static final TrackedData<Integer> HEAD_ROLLING_TIME_LEFT = DataTracker.registerData(MerchantEntity.class, TrackedDataHandlerRegistry.INTEGER);
   @Nullable
   private PlayerEntity customer;
   @Nullable
   protected TradeOfferList offers;
   private final SimpleInventory inventory = new SimpleInventory(8);

   public MerchantEntity(EntityType<? extends MerchantEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 16.0F);
      this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0F);
   }

   @Override
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      if (entityData == null) {
         entityData = new PassiveEntity.PassiveData(false);
      }

      return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
   }

   public int getHeadRollingTimeLeft() {
      return this.dataTracker.get(HEAD_ROLLING_TIME_LEFT);
   }

   public void setHeadRollingTimeLeft(int ticks) {
      this.dataTracker.set(HEAD_ROLLING_TIME_LEFT, ticks);
   }

   @Override
   public int getExperience() {
      return 0;
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return this.isBaby() ? 0.81F : 1.62F;
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(HEAD_ROLLING_TIME_LEFT, 0);
   }

   @Override
   public void setCurrentCustomer(@Nullable PlayerEntity customer) {
      this.customer = customer;
   }

   @Nullable
   @Override
   public PlayerEntity getCurrentCustomer() {
      return this.customer;
   }

   public boolean hasCustomer() {
      return this.customer != null;
   }

   @Override
   public TradeOfferList getOffers() {
      if (this.offers == null) {
         this.offers = new TradeOfferList();
         this.fillRecipes();
      }

      return this.offers;
   }

   @Override
   public void setOffersFromServer(@Nullable TradeOfferList offers) {
   }

   @Override
   public void setExperienceFromServer(int experience) {
   }

   @Override
   public void trade(TradeOffer offer) {
      offer.use();
      this.ambientSoundChance = -this.getMinAmbientSoundDelay();
      this.afterUsing(offer);
      if (this.customer instanceof ServerPlayerEntity) {
         Criteria.VILLAGER_TRADE.handle((ServerPlayerEntity)this.customer, this, offer.getMutableSellItem());
      }
   }

   protected abstract void afterUsing(TradeOffer offer);

   @Override
   public boolean isLeveledMerchant() {
      return true;
   }

   @Override
   public void onSellingItem(ItemStack stack) {
      if (!this.world.isClient && this.ambientSoundChance > -this.getMinAmbientSoundDelay() + 20) {
         this.ambientSoundChance = -this.getMinAmbientSoundDelay();
         this.playSound(this.getTradingSound(!stack.isEmpty()), this.getSoundVolume(), this.getSoundPitch());
      }
   }

   @Override
   public SoundEvent getYesSound() {
      return SoundEvents.ENTITY_VILLAGER_YES;
   }

   protected SoundEvent getTradingSound(boolean sold) {
      return sold ? SoundEvents.ENTITY_VILLAGER_YES : SoundEvents.ENTITY_VILLAGER_NO;
   }

   public void playCelebrateSound() {
      this.playSound(SoundEvents.ENTITY_VILLAGER_CELEBRATE, this.getSoundVolume(), this.getSoundPitch());
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      TradeOfferList _snowman = this.getOffers();
      if (!_snowman.isEmpty()) {
         tag.put("Offers", _snowman.toTag());
      }

      tag.put("Inventory", this.inventory.getTags());
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      if (tag.contains("Offers", 10)) {
         this.offers = new TradeOfferList(tag.getCompound("Offers"));
      }

      this.inventory.readTags(tag.getList("Inventory", 10));
   }

   @Nullable
   @Override
   public Entity moveToWorld(ServerWorld destination) {
      this.resetCustomer();
      return super.moveToWorld(destination);
   }

   protected void resetCustomer() {
      this.setCurrentCustomer(null);
   }

   @Override
   public void onDeath(DamageSource source) {
      super.onDeath(source);
      this.resetCustomer();
   }

   protected void produceParticles(ParticleEffect parameters) {
      for (int _snowman = 0; _snowman < 5; _snowman++) {
         double _snowmanx = this.random.nextGaussian() * 0.02;
         double _snowmanxx = this.random.nextGaussian() * 0.02;
         double _snowmanxxx = this.random.nextGaussian() * 0.02;
         this.world.addParticle(parameters, this.getParticleX(1.0), this.getRandomBodyY() + 1.0, this.getParticleZ(1.0), _snowmanx, _snowmanxx, _snowmanxxx);
      }
   }

   @Override
   public boolean canBeLeashedBy(PlayerEntity player) {
      return false;
   }

   public SimpleInventory getInventory() {
      return this.inventory;
   }

   @Override
   public boolean equip(int slot, ItemStack item) {
      if (super.equip(slot, item)) {
         return true;
      } else {
         int _snowman = slot - 300;
         if (_snowman >= 0 && _snowman < this.inventory.size()) {
            this.inventory.setStack(_snowman, item);
            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public World getMerchantWorld() {
      return this.world;
   }

   protected abstract void fillRecipes();

   protected void fillRecipesFromPool(TradeOfferList recipeList, TradeOffers.Factory[] pool, int count) {
      Set<Integer> _snowman = Sets.newHashSet();
      if (pool.length > count) {
         while (_snowman.size() < count) {
            _snowman.add(this.random.nextInt(pool.length));
         }
      } else {
         for (int _snowmanx = 0; _snowmanx < pool.length; _snowmanx++) {
            _snowman.add(_snowmanx);
         }
      }

      for (Integer _snowmanx : _snowman) {
         TradeOffers.Factory _snowmanxx = pool[_snowmanx];
         TradeOffer _snowmanxxx = _snowmanxx.create(this, this.random);
         if (_snowmanxxx != null) {
            recipeList.add(_snowmanxxx);
         }
      }
   }

   @Override
   public Vec3d method_30951(float _snowman) {
      float _snowmanx = MathHelper.lerp(_snowman, this.prevBodyYaw, this.bodyYaw) * (float) (Math.PI / 180.0);
      Vec3d _snowmanxx = new Vec3d(0.0, this.getBoundingBox().getYLength() - 1.0, 0.2);
      return this.method_30950(_snowman).add(_snowmanxx.rotateY(-_snowmanx));
   }
}
