package net.minecraft.entity.passive;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityInteraction;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.Schedule;
import net.minecraft.entity.ai.brain.sensor.GolemLastSeenSensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.VillagerTaskListProvider;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillageGossipType;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerGossips;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public class VillagerEntity extends MerchantEntity implements InteractionObserver, VillagerDataContainer {
   private static final TrackedData<VillagerData> VILLAGER_DATA = DataTracker.registerData(VillagerEntity.class, TrackedDataHandlerRegistry.VILLAGER_DATA);
   public static final Map<Item, Integer> ITEM_FOOD_VALUES = ImmutableMap.of(Items.BREAD, 4, Items.POTATO, 1, Items.CARROT, 1, Items.BEETROOT, 1);
   private static final Set<Item> GATHERABLE_ITEMS = ImmutableSet.of(
      Items.BREAD, Items.POTATO, Items.CARROT, Items.WHEAT, Items.WHEAT_SEEDS, Items.BEETROOT, new Item[]{Items.BEETROOT_SEEDS}
   );
   private int levelUpTimer;
   private boolean levelingUp;
   @Nullable
   private PlayerEntity lastCustomer;
   private byte foodLevel;
   private final VillagerGossips gossip = new VillagerGossips();
   private long gossipStartTime;
   private long lastGossipDecayTime;
   private int experience;
   private long lastRestockTime;
   private int restocksToday;
   private long lastRestockCheckTime;
   private boolean natural;
   private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(
      MemoryModuleType.HOME,
      MemoryModuleType.JOB_SITE,
      MemoryModuleType.POTENTIAL_JOB_SITE,
      MemoryModuleType.MEETING_POINT,
      MemoryModuleType.MOBS,
      MemoryModuleType.VISIBLE_MOBS,
      MemoryModuleType.VISIBLE_VILLAGER_BABIES,
      MemoryModuleType.NEAREST_PLAYERS,
      MemoryModuleType.NEAREST_VISIBLE_PLAYER,
      MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
      MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
      MemoryModuleType.WALK_TARGET,
      new MemoryModuleType[]{
         MemoryModuleType.LOOK_TARGET,
         MemoryModuleType.INTERACTION_TARGET,
         MemoryModuleType.BREED_TARGET,
         MemoryModuleType.PATH,
         MemoryModuleType.DOORS_TO_CLOSE,
         MemoryModuleType.NEAREST_BED,
         MemoryModuleType.HURT_BY,
         MemoryModuleType.HURT_BY_ENTITY,
         MemoryModuleType.NEAREST_HOSTILE,
         MemoryModuleType.SECONDARY_JOB_SITE,
         MemoryModuleType.HIDING_PLACE,
         MemoryModuleType.HEARD_BELL_TIME,
         MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
         MemoryModuleType.LAST_SLEPT,
         MemoryModuleType.LAST_WOKEN,
         MemoryModuleType.LAST_WORKED_AT_POI,
         MemoryModuleType.GOLEM_DETECTED_RECENTLY
      }
   );
   private static final ImmutableList<SensorType<? extends Sensor<? super VillagerEntity>>> SENSORS = ImmutableList.of(
      SensorType.NEAREST_LIVING_ENTITIES,
      SensorType.NEAREST_PLAYERS,
      SensorType.NEAREST_ITEMS,
      SensorType.NEAREST_BED,
      SensorType.HURT_BY,
      SensorType.VILLAGER_HOSTILES,
      SensorType.VILLAGER_BABIES,
      SensorType.SECONDARY_POIS,
      SensorType.GOLEM_DETECTED
   );
   public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<VillagerEntity, PointOfInterestType>> POINTS_OF_INTEREST = ImmutableMap.of(
      MemoryModuleType.HOME,
      (BiPredicate<VillagerEntity, PointOfInterestType>)(_snowman, _snowmanx) -> _snowmanx == PointOfInterestType.HOME,
      MemoryModuleType.JOB_SITE,
      (BiPredicate<VillagerEntity, PointOfInterestType>)(_snowman, _snowmanx) -> _snowman.getVillagerData().getProfession().getWorkStation() == _snowmanx,
      MemoryModuleType.POTENTIAL_JOB_SITE,
      (BiPredicate<VillagerEntity, PointOfInterestType>)(_snowman, _snowmanx) -> PointOfInterestType.IS_USED_BY_PROFESSION.test(_snowmanx),
      MemoryModuleType.MEETING_POINT,
      (BiPredicate<VillagerEntity, PointOfInterestType>)(_snowman, _snowmanx) -> _snowmanx == PointOfInterestType.MEETING
   );

   public VillagerEntity(EntityType<? extends VillagerEntity> _snowman, World _snowman) {
      this(_snowman, _snowman, VillagerType.PLAINS);
   }

   public VillagerEntity(EntityType<? extends VillagerEntity> entityType, World world, VillagerType type) {
      super(entityType, world);
      ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(true);
      this.getNavigation().setCanSwim(true);
      this.setCanPickUpLoot(true);
      this.setVillagerData(this.getVillagerData().withType(type).withProfession(VillagerProfession.NONE));
   }

   @Override
   public Brain<VillagerEntity> getBrain() {
      return (Brain<VillagerEntity>)super.getBrain();
   }

   @Override
   protected Brain.Profile<VillagerEntity> createBrainProfile() {
      return Brain.createProfile(MEMORY_MODULES, SENSORS);
   }

   @Override
   protected Brain<?> deserializeBrain(Dynamic<?> _snowman) {
      Brain<VillagerEntity> _snowmanx = this.createBrainProfile().deserialize(_snowman);
      this.initBrain(_snowmanx);
      return _snowmanx;
   }

   public void reinitializeBrain(ServerWorld world) {
      Brain<VillagerEntity> _snowman = this.getBrain();
      _snowman.stopAllTasks(world, this);
      this.brain = _snowman.copy();
      this.initBrain(this.getBrain());
   }

   private void initBrain(Brain<VillagerEntity> brain) {
      VillagerProfession _snowman = this.getVillagerData().getProfession();
      if (this.isBaby()) {
         brain.setSchedule(Schedule.VILLAGER_BABY);
         brain.setTaskList(Activity.PLAY, VillagerTaskListProvider.createPlayTasks(0.5F));
      } else {
         brain.setSchedule(Schedule.VILLAGER_DEFAULT);
         brain.setTaskList(
            Activity.WORK,
            VillagerTaskListProvider.createWorkTasks(_snowman, 0.5F),
            ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryModuleState.VALUE_PRESENT))
         );
      }

      brain.setTaskList(Activity.CORE, VillagerTaskListProvider.createCoreTasks(_snowman, 0.5F));
      brain.setTaskList(
         Activity.MEET,
         VillagerTaskListProvider.createMeetTasks(_snowman, 0.5F),
         ImmutableSet.of(Pair.of(MemoryModuleType.MEETING_POINT, MemoryModuleState.VALUE_PRESENT))
      );
      brain.setTaskList(Activity.REST, VillagerTaskListProvider.createRestTasks(_snowman, 0.5F));
      brain.setTaskList(Activity.IDLE, VillagerTaskListProvider.createIdleTasks(_snowman, 0.5F));
      brain.setTaskList(Activity.PANIC, VillagerTaskListProvider.createPanicTasks(_snowman, 0.5F));
      brain.setTaskList(Activity.PRE_RAID, VillagerTaskListProvider.createPreRaidTasks(_snowman, 0.5F));
      brain.setTaskList(Activity.RAID, VillagerTaskListProvider.createRaidTasks(_snowman, 0.5F));
      brain.setTaskList(Activity.HIDE, VillagerTaskListProvider.createHideTasks(_snowman, 0.5F));
      brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
      brain.setDefaultActivity(Activity.IDLE);
      brain.doExclusively(Activity.IDLE);
      brain.refreshActivities(this.world.getTimeOfDay(), this.world.getTime());
   }

   @Override
   protected void onGrowUp() {
      super.onGrowUp();
      if (this.world instanceof ServerWorld) {
         this.reinitializeBrain((ServerWorld)this.world);
      }
   }

   public static DefaultAttributeContainer.Builder createVillagerAttributes() {
      return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0);
   }

   public boolean isNatural() {
      return this.natural;
   }

   @Override
   protected void mobTick() {
      this.world.getProfiler().push("villagerBrain");
      this.getBrain().tick((ServerWorld)this.world, this);
      this.world.getProfiler().pop();
      if (this.natural) {
         this.natural = false;
      }

      if (!this.hasCustomer() && this.levelUpTimer > 0) {
         this.levelUpTimer--;
         if (this.levelUpTimer <= 0) {
            if (this.levelingUp) {
               this.levelUp();
               this.levelingUp = false;
            }

            this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0));
         }
      }

      if (this.lastCustomer != null && this.world instanceof ServerWorld) {
         ((ServerWorld)this.world).handleInteraction(EntityInteraction.TRADE, this.lastCustomer, this);
         this.world.sendEntityStatus(this, (byte)14);
         this.lastCustomer = null;
      }

      if (!this.isAiDisabled() && this.random.nextInt(100) == 0) {
         Raid _snowman = ((ServerWorld)this.world).getRaidAt(this.getBlockPos());
         if (_snowman != null && _snowman.isActive() && !_snowman.isFinished()) {
            this.world.sendEntityStatus(this, (byte)42);
         }
      }

      if (this.getVillagerData().getProfession() == VillagerProfession.NONE && this.hasCustomer()) {
         this.resetCustomer();
      }

      super.mobTick();
   }

   @Override
   public void tick() {
      super.tick();
      if (this.getHeadRollingTimeLeft() > 0) {
         this.setHeadRollingTimeLeft(this.getHeadRollingTimeLeft() - 1);
      }

      this.decayGossip();
   }

   @Override
   public ActionResult interactMob(PlayerEntity player, Hand hand) {
      ItemStack _snowman = player.getStackInHand(hand);
      if (_snowman.getItem() == Items.VILLAGER_SPAWN_EGG || !this.isAlive() || this.hasCustomer() || this.isSleeping()) {
         return super.interactMob(player, hand);
      } else if (this.isBaby()) {
         this.sayNo();
         return ActionResult.success(this.world.isClient);
      } else {
         boolean _snowmanx = this.getOffers().isEmpty();
         if (hand == Hand.MAIN_HAND) {
            if (_snowmanx && !this.world.isClient) {
               this.sayNo();
            }

            player.incrementStat(Stats.TALKED_TO_VILLAGER);
         }

         if (_snowmanx) {
            return ActionResult.success(this.world.isClient);
         } else {
            if (!this.world.isClient && !this.offers.isEmpty()) {
               this.beginTradeWith(player);
            }

            return ActionResult.success(this.world.isClient);
         }
      }
   }

   private void sayNo() {
      this.setHeadRollingTimeLeft(40);
      if (!this.world.isClient()) {
         this.playSound(SoundEvents.ENTITY_VILLAGER_NO, this.getSoundVolume(), this.getSoundPitch());
      }
   }

   private void beginTradeWith(PlayerEntity customer) {
      this.prepareRecipesFor(customer);
      this.setCurrentCustomer(customer);
      this.sendOffers(customer, this.getDisplayName(), this.getVillagerData().getLevel());
   }

   @Override
   public void setCurrentCustomer(@Nullable PlayerEntity customer) {
      boolean _snowman = this.getCurrentCustomer() != null && customer == null;
      super.setCurrentCustomer(customer);
      if (_snowman) {
         this.resetCustomer();
      }
   }

   @Override
   protected void resetCustomer() {
      super.resetCustomer();
      this.clearCurrentBonus();
   }

   private void clearCurrentBonus() {
      for (TradeOffer _snowman : this.getOffers()) {
         _snowman.clearSpecialPrice();
      }
   }

   @Override
   public boolean canRefreshTrades() {
      return true;
   }

   public void restock() {
      this.updatePricesOnDemand();

      for (TradeOffer _snowman : this.getOffers()) {
         _snowman.resetUses();
      }

      this.lastRestockTime = this.world.getTime();
      this.restocksToday++;
   }

   private boolean needRestock() {
      for (TradeOffer _snowman : this.getOffers()) {
         if (_snowman.method_21834()) {
            return true;
         }
      }

      return false;
   }

   private boolean canRestock() {
      return this.restocksToday == 0 || this.restocksToday < 2 && this.world.getTime() > this.lastRestockTime + 2400L;
   }

   public boolean shouldRestock() {
      long _snowman = this.lastRestockTime + 12000L;
      long _snowmanx = this.world.getTime();
      boolean _snowmanxx = _snowmanx > _snowman;
      long _snowmanxxx = this.world.getTimeOfDay();
      if (this.lastRestockCheckTime > 0L) {
         long _snowmanxxxx = this.lastRestockCheckTime / 24000L;
         long _snowmanxxxxx = _snowmanxxx / 24000L;
         _snowmanxx |= _snowmanxxxxx > _snowmanxxxx;
      }

      this.lastRestockCheckTime = _snowmanxxx;
      if (_snowmanxx) {
         this.lastRestockTime = _snowmanx;
         this.clearDailyRestockCount();
      }

      return this.canRestock() && this.needRestock();
   }

   private void method_21723() {
      int _snowman = 2 - this.restocksToday;
      if (_snowman > 0) {
         for (TradeOffer _snowmanx : this.getOffers()) {
            _snowmanx.resetUses();
         }
      }

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         this.updatePricesOnDemand();
      }
   }

   private void updatePricesOnDemand() {
      for (TradeOffer _snowman : this.getOffers()) {
         _snowman.updatePriceOnDemand();
      }
   }

   private void prepareRecipesFor(PlayerEntity player) {
      int _snowman = this.getReputation(player);
      if (_snowman != 0) {
         for (TradeOffer _snowmanx : this.getOffers()) {
            _snowmanx.increaseSpecialPrice(-MathHelper.floor((float)_snowman * _snowmanx.getPriceMultiplier()));
         }
      }

      if (player.hasStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE)) {
         StatusEffectInstance _snowmanx = player.getStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE);
         int _snowmanxx = _snowmanx.getAmplifier();

         for (TradeOffer _snowmanxxx : this.getOffers()) {
            double _snowmanxxxx = 0.3 + 0.0625 * (double)_snowmanxx;
            int _snowmanxxxxx = (int)Math.floor(_snowmanxxxx * (double)_snowmanxxx.getOriginalFirstBuyItem().getCount());
            _snowmanxxx.increaseSpecialPrice(-Math.max(_snowmanxxxxx, 1));
         }
      }
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      VillagerData.CODEC.encodeStart(NbtOps.INSTANCE, this.getVillagerData()).resultOrPartial(LOGGER::error).ifPresent(_snowmanx -> tag.put("VillagerData", _snowmanx));
      tag.putByte("FoodLevel", this.foodLevel);
      tag.put("Gossips", (Tag)this.gossip.serialize(NbtOps.INSTANCE).getValue());
      tag.putInt("Xp", this.experience);
      tag.putLong("LastRestock", this.lastRestockTime);
      tag.putLong("LastGossipDecay", this.lastGossipDecayTime);
      tag.putInt("RestocksToday", this.restocksToday);
      if (this.natural) {
         tag.putBoolean("AssignProfessionWhenSpawned", true);
      }
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      if (tag.contains("VillagerData", 10)) {
         DataResult<VillagerData> _snowman = VillagerData.CODEC.parse(new Dynamic(NbtOps.INSTANCE, tag.get("VillagerData")));
         _snowman.resultOrPartial(LOGGER::error).ifPresent(this::setVillagerData);
      }

      if (tag.contains("Offers", 10)) {
         this.offers = new TradeOfferList(tag.getCompound("Offers"));
      }

      if (tag.contains("FoodLevel", 1)) {
         this.foodLevel = tag.getByte("FoodLevel");
      }

      ListTag _snowman = tag.getList("Gossips", 10);
      this.gossip.deserialize(new Dynamic(NbtOps.INSTANCE, _snowman));
      if (tag.contains("Xp", 3)) {
         this.experience = tag.getInt("Xp");
      }

      this.lastRestockTime = tag.getLong("LastRestock");
      this.lastGossipDecayTime = tag.getLong("LastGossipDecay");
      this.setCanPickUpLoot(true);
      if (this.world instanceof ServerWorld) {
         this.reinitializeBrain((ServerWorld)this.world);
      }

      this.restocksToday = tag.getInt("RestocksToday");
      if (tag.contains("AssignProfessionWhenSpawned")) {
         this.natural = tag.getBoolean("AssignProfessionWhenSpawned");
      }
   }

   @Override
   public boolean canImmediatelyDespawn(double distanceSquared) {
      return false;
   }

   @Nullable
   @Override
   protected SoundEvent getAmbientSound() {
      if (this.isSleeping()) {
         return null;
      } else {
         return this.hasCustomer() ? SoundEvents.ENTITY_VILLAGER_TRADE : SoundEvents.ENTITY_VILLAGER_AMBIENT;
      }
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_VILLAGER_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_VILLAGER_DEATH;
   }

   public void playWorkSound() {
      SoundEvent _snowman = this.getVillagerData().getProfession().getWorkSound();
      if (_snowman != null) {
         this.playSound(_snowman, this.getSoundVolume(), this.getSoundPitch());
      }
   }

   public void setVillagerData(VillagerData villagerData) {
      VillagerData _snowman = this.getVillagerData();
      if (_snowman.getProfession() != villagerData.getProfession()) {
         this.offers = null;
      }

      this.dataTracker.set(VILLAGER_DATA, villagerData);
   }

   @Override
   public VillagerData getVillagerData() {
      return this.dataTracker.get(VILLAGER_DATA);
   }

   @Override
   protected void afterUsing(TradeOffer offer) {
      int _snowman = 3 + this.random.nextInt(4);
      this.experience = this.experience + offer.getMerchantExperience();
      this.lastCustomer = this.getCurrentCustomer();
      if (this.canLevelUp()) {
         this.levelUpTimer = 40;
         this.levelingUp = true;
         _snowman += 5;
      }

      if (offer.shouldRewardPlayerExperience()) {
         this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.getX(), this.getY() + 0.5, this.getZ(), _snowman));
      }
   }

   @Override
   public void setAttacker(@Nullable LivingEntity attacker) {
      if (attacker != null && this.world instanceof ServerWorld) {
         ((ServerWorld)this.world).handleInteraction(EntityInteraction.VILLAGER_HURT, attacker, this);
         if (this.isAlive() && attacker instanceof PlayerEntity) {
            this.world.sendEntityStatus(this, (byte)13);
         }
      }

      super.setAttacker(attacker);
   }

   @Override
   public void onDeath(DamageSource source) {
      LOGGER.info("Villager {} died, message: '{}'", this, source.getDeathMessage(this).getString());
      Entity _snowman = source.getAttacker();
      if (_snowman != null) {
         this.notifyDeath(_snowman);
      }

      this.method_30958();
      super.onDeath(source);
   }

   private void method_30958() {
      this.releaseTicketFor(MemoryModuleType.HOME);
      this.releaseTicketFor(MemoryModuleType.JOB_SITE);
      this.releaseTicketFor(MemoryModuleType.POTENTIAL_JOB_SITE);
      this.releaseTicketFor(MemoryModuleType.MEETING_POINT);
   }

   private void notifyDeath(Entity killer) {
      if (this.world instanceof ServerWorld) {
         Optional<List<LivingEntity>> _snowman = this.brain.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS);
         if (_snowman.isPresent()) {
            ServerWorld _snowmanx = (ServerWorld)this.world;
            _snowman.get()
               .stream()
               .filter(_snowmanxx -> _snowmanxx instanceof InteractionObserver)
               .forEach(_snowmanxx -> _snowman.handleInteraction(EntityInteraction.VILLAGER_KILLED, killer, (InteractionObserver)_snowmanxx));
         }
      }
   }

   public void releaseTicketFor(MemoryModuleType<GlobalPos> _snowman) {
      if (this.world instanceof ServerWorld) {
         MinecraftServer _snowmanx = ((ServerWorld)this.world).getServer();
         this.brain.getOptionalMemory(_snowman).ifPresent(_snowmanxxxxx -> {
            ServerWorld _snowmanxxx = _snowman.getWorld(_snowmanxxxxx.getDimension());
            if (_snowmanxxx != null) {
               PointOfInterestStorage _snowmanxxxx = _snowmanxxx.getPointOfInterestStorage();
               Optional<PointOfInterestType> _snowmanxxxxx = _snowmanxxxx.getType(_snowmanxxxxx.getPos());
               BiPredicate<VillagerEntity, PointOfInterestType> _snowmanxxxxx = POINTS_OF_INTEREST.get(_snowman);
               if (_snowmanxxxxx.isPresent() && _snowmanxxxxx.test(this, _snowmanxxxxx.get())) {
                  _snowmanxxxx.releaseTicket(_snowmanxxxxx.getPos());
                  DebugInfoSender.sendPointOfInterest(_snowmanxxx, _snowmanxxxxx.getPos());
               }
            }
         });
      }
   }

   @Override
   public boolean isReadyToBreed() {
      return this.foodLevel + this.getAvailableFood() >= 12 && this.getBreedingAge() == 0;
   }

   private boolean lacksFood() {
      return this.foodLevel < 12;
   }

   private void consumeAvailableFood() {
      if (this.lacksFood() && this.getAvailableFood() != 0) {
         for (int _snowman = 0; _snowman < this.getInventory().size(); _snowman++) {
            ItemStack _snowmanx = this.getInventory().getStack(_snowman);
            if (!_snowmanx.isEmpty()) {
               Integer _snowmanxx = ITEM_FOOD_VALUES.get(_snowmanx.getItem());
               if (_snowmanxx != null) {
                  int _snowmanxxx = _snowmanx.getCount();

                  for (int _snowmanxxxx = _snowmanxxx; _snowmanxxxx > 0; _snowmanxxxx--) {
                     this.foodLevel = (byte)(this.foodLevel + _snowmanxx);
                     this.getInventory().removeStack(_snowman, 1);
                     if (!this.lacksFood()) {
                        return;
                     }
                  }
               }
            }
         }
      }
   }

   public int getReputation(PlayerEntity player) {
      return this.gossip.getReputationFor(player.getUuid(), _snowman -> true);
   }

   private void depleteFood(int amount) {
      this.foodLevel = (byte)(this.foodLevel - amount);
   }

   public void eatForBreeding() {
      this.consumeAvailableFood();
      this.depleteFood(12);
   }

   public void setOffers(TradeOfferList offers) {
      this.offers = offers;
   }

   private boolean canLevelUp() {
      int _snowman = this.getVillagerData().getLevel();
      return VillagerData.canLevelUp(_snowman) && this.experience >= VillagerData.getUpperLevelExperience(_snowman);
   }

   private void levelUp() {
      this.setVillagerData(this.getVillagerData().withLevel(this.getVillagerData().getLevel() + 1));
      this.fillRecipes();
   }

   @Override
   protected Text getDefaultName() {
      return new TranslatableText(
         this.getType().getTranslationKey() + '.' + Registry.VILLAGER_PROFESSION.getId(this.getVillagerData().getProfession()).getPath()
      );
   }

   @Override
   public void handleStatus(byte status) {
      if (status == 12) {
         this.produceParticles(ParticleTypes.HEART);
      } else if (status == 13) {
         this.produceParticles(ParticleTypes.ANGRY_VILLAGER);
      } else if (status == 14) {
         this.produceParticles(ParticleTypes.HAPPY_VILLAGER);
      } else if (status == 42) {
         this.produceParticles(ParticleTypes.SPLASH);
      } else {
         super.handleStatus(status);
      }
   }

   @Nullable
   @Override
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      if (spawnReason == SpawnReason.BREEDING) {
         this.setVillagerData(this.getVillagerData().withProfession(VillagerProfession.NONE));
      }

      if (spawnReason == SpawnReason.COMMAND
         || spawnReason == SpawnReason.SPAWN_EGG
         || spawnReason == SpawnReason.SPAWNER
         || spawnReason == SpawnReason.DISPENSER) {
         this.setVillagerData(this.getVillagerData().withType(VillagerType.forBiome(world.method_31081(this.getBlockPos()))));
      }

      if (spawnReason == SpawnReason.STRUCTURE) {
         this.natural = true;
      }

      return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
   }

   public VillagerEntity createChild(ServerWorld _snowman, PassiveEntity _snowman) {
      double _snowmanxx = this.random.nextDouble();
      VillagerType _snowmanxxx;
      if (_snowmanxx < 0.5) {
         _snowmanxxx = VillagerType.forBiome(_snowman.method_31081(this.getBlockPos()));
      } else if (_snowmanxx < 0.75) {
         _snowmanxxx = this.getVillagerData().getType();
      } else {
         _snowmanxxx = ((VillagerEntity)_snowman).getVillagerData().getType();
      }

      VillagerEntity _snowmanxxxx = new VillagerEntity(EntityType.VILLAGER, _snowman, _snowmanxxx);
      _snowmanxxxx.initialize(_snowman, _snowman.getLocalDifficulty(_snowmanxxxx.getBlockPos()), SpawnReason.BREEDING, null, null);
      return _snowmanxxxx;
   }

   @Override
   public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
      if (world.getDifficulty() != Difficulty.PEACEFUL) {
         LOGGER.info("Villager {} was struck by lightning {}.", this, lightning);
         WitchEntity _snowman = EntityType.WITCH.create(world);
         _snowman.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.yaw, this.pitch);
         _snowman.initialize(world, world.getLocalDifficulty(_snowman.getBlockPos()), SpawnReason.CONVERSION, null, null);
         _snowman.setAiDisabled(this.isAiDisabled());
         if (this.hasCustomName()) {
            _snowman.setCustomName(this.getCustomName());
            _snowman.setCustomNameVisible(this.isCustomNameVisible());
         }

         _snowman.setPersistent();
         world.spawnEntityAndPassengers(_snowman);
         this.method_30958();
         this.remove();
      } else {
         super.onStruckByLightning(world, lightning);
      }
   }

   @Override
   protected void loot(ItemEntity item) {
      ItemStack _snowman = item.getStack();
      if (this.canGather(_snowman)) {
         SimpleInventory _snowmanx = this.getInventory();
         boolean _snowmanxx = _snowmanx.canInsert(_snowman);
         if (!_snowmanxx) {
            return;
         }

         this.method_29499(item);
         this.sendPickup(item, _snowman.getCount());
         ItemStack _snowmanxxx = _snowmanx.addStack(_snowman);
         if (_snowmanxxx.isEmpty()) {
            item.remove();
         } else {
            _snowman.setCount(_snowmanxxx.getCount());
         }
      }
   }

   @Override
   public boolean canGather(ItemStack stack) {
      Item _snowman = stack.getItem();
      return (GATHERABLE_ITEMS.contains(_snowman) || this.getVillagerData().getProfession().getGatherableItems().contains(_snowman)) && this.getInventory().canInsert(stack);
   }

   public boolean wantsToStartBreeding() {
      return this.getAvailableFood() >= 24;
   }

   public boolean canBreed() {
      return this.getAvailableFood() < 12;
   }

   private int getAvailableFood() {
      SimpleInventory _snowman = this.getInventory();
      return ITEM_FOOD_VALUES.entrySet().stream().mapToInt(_snowmanx -> _snowman.count(_snowmanx.getKey()) * _snowmanx.getValue()).sum();
   }

   public boolean hasSeedToPlant() {
      return this.getInventory().containsAny(ImmutableSet.of(Items.WHEAT_SEEDS, Items.POTATO, Items.CARROT, Items.BEETROOT_SEEDS));
   }

   @Override
   protected void fillRecipes() {
      VillagerData _snowman = this.getVillagerData();
      Int2ObjectMap<TradeOffers.Factory[]> _snowmanx = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(_snowman.getProfession());
      if (_snowmanx != null && !_snowmanx.isEmpty()) {
         TradeOffers.Factory[] _snowmanxx = (TradeOffers.Factory[])_snowmanx.get(_snowman.getLevel());
         if (_snowmanxx != null) {
            TradeOfferList _snowmanxxx = this.getOffers();
            this.fillRecipesFromPool(_snowmanxxx, _snowmanxx, 2);
         }
      }
   }

   public void talkWithVillager(ServerWorld world, VillagerEntity villager, long time) {
      if ((time < this.gossipStartTime || time >= this.gossipStartTime + 1200L)
         && (time < villager.gossipStartTime || time >= villager.gossipStartTime + 1200L)) {
         this.gossip.shareGossipFrom(villager.gossip, this.random, 10);
         this.gossipStartTime = time;
         villager.gossipStartTime = time;
         this.summonGolem(world, time, 5);
      }
   }

   private void decayGossip() {
      long _snowman = this.world.getTime();
      if (this.lastGossipDecayTime == 0L) {
         this.lastGossipDecayTime = _snowman;
      } else if (_snowman >= this.lastGossipDecayTime + 24000L) {
         this.gossip.decay();
         this.lastGossipDecayTime = _snowman;
      }
   }

   public void summonGolem(ServerWorld world, long time, int _snowman) {
      if (this.canSummonGolem(time)) {
         Box _snowmanx = this.getBoundingBox().expand(10.0, 10.0, 10.0);
         List<VillagerEntity> _snowmanxx = world.getNonSpectatingEntities(VillagerEntity.class, _snowmanx);
         List<VillagerEntity> _snowmanxxx = _snowmanxx.stream().filter(_snowmanxxxx -> _snowmanxxxx.canSummonGolem(time)).limit(5L).collect(Collectors.toList());
         if (_snowmanxxx.size() >= _snowman) {
            IronGolemEntity _snowmanxxxx = this.spawnIronGolem(world);
            if (_snowmanxxxx != null) {
               _snowmanxx.forEach(GolemLastSeenSensor::method_30233);
            }
         }
      }
   }

   public boolean canSummonGolem(long time) {
      return !this.hasRecentlyWorkedAndSlept(this.world.getTime()) ? false : !this.brain.hasMemoryModule(MemoryModuleType.GOLEM_DETECTED_RECENTLY);
   }

   @Nullable
   private IronGolemEntity spawnIronGolem(ServerWorld world) {
      BlockPos _snowman = this.getBlockPos();

      for (int _snowmanx = 0; _snowmanx < 10; _snowmanx++) {
         double _snowmanxx = (double)(world.random.nextInt(16) - 8);
         double _snowmanxxx = (double)(world.random.nextInt(16) - 8);
         BlockPos _snowmanxxxx = this.method_30023(_snowman, _snowmanxx, _snowmanxxx);
         if (_snowmanxxxx != null) {
            IronGolemEntity _snowmanxxxxx = EntityType.IRON_GOLEM.create(world, null, null, null, _snowmanxxxx, SpawnReason.MOB_SUMMONED, false, false);
            if (_snowmanxxxxx != null) {
               if (_snowmanxxxxx.canSpawn(world, SpawnReason.MOB_SUMMONED) && _snowmanxxxxx.canSpawn(world)) {
                  world.spawnEntityAndPassengers(_snowmanxxxxx);
                  return _snowmanxxxxx;
               }

               _snowmanxxxxx.remove();
            }
         }
      }

      return null;
   }

   @Nullable
   private BlockPos method_30023(BlockPos _snowman, double _snowman, double _snowman) {
      int _snowmanxxx = 6;
      BlockPos _snowmanxxxx = _snowman.add(_snowman, 6.0, _snowman);
      BlockState _snowmanxxxxx = this.world.getBlockState(_snowmanxxxx);

      for (int _snowmanxxxxxx = 6; _snowmanxxxxxx >= -6; _snowmanxxxxxx--) {
         BlockPos _snowmanxxxxxxx = _snowmanxxxx;
         BlockState _snowmanxxxxxxxx = _snowmanxxxxx;
         _snowmanxxxx = _snowmanxxxx.down();
         _snowmanxxxxx = this.world.getBlockState(_snowmanxxxx);
         if ((_snowmanxxxxxxxx.isAir() || _snowmanxxxxxxxx.getMaterial().isLiquid()) && _snowmanxxxxx.getMaterial().blocksLight()) {
            return _snowmanxxxxxxx;
         }
      }

      return null;
   }

   @Override
   public void onInteractionWith(EntityInteraction interaction, Entity entity) {
      if (interaction == EntityInteraction.ZOMBIE_VILLAGER_CURED) {
         this.gossip.startGossip(entity.getUuid(), VillageGossipType.MAJOR_POSITIVE, 20);
         this.gossip.startGossip(entity.getUuid(), VillageGossipType.MINOR_POSITIVE, 25);
      } else if (interaction == EntityInteraction.TRADE) {
         this.gossip.startGossip(entity.getUuid(), VillageGossipType.TRADING, 2);
      } else if (interaction == EntityInteraction.VILLAGER_HURT) {
         this.gossip.startGossip(entity.getUuid(), VillageGossipType.MINOR_NEGATIVE, 25);
      } else if (interaction == EntityInteraction.VILLAGER_KILLED) {
         this.gossip.startGossip(entity.getUuid(), VillageGossipType.MAJOR_NEGATIVE, 25);
      }
   }

   @Override
   public int getExperience() {
      return this.experience;
   }

   public void setExperience(int amount) {
      this.experience = amount;
   }

   private void clearDailyRestockCount() {
      this.method_21723();
      this.restocksToday = 0;
   }

   public VillagerGossips getGossip() {
      return this.gossip;
   }

   public void setGossipDataFromTag(Tag tag) {
      this.gossip.deserialize(new Dynamic(NbtOps.INSTANCE, tag));
   }

   @Override
   protected void sendAiDebugData() {
      super.sendAiDebugData();
      DebugInfoSender.sendBrainDebugData(this);
   }

   @Override
   public void sleep(BlockPos pos) {
      super.sleep(pos);
      this.brain.remember(MemoryModuleType.LAST_SLEPT, this.world.getTime());
      this.brain.forget(MemoryModuleType.WALK_TARGET);
      this.brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
   }

   @Override
   public void wakeUp() {
      super.wakeUp();
      this.brain.remember(MemoryModuleType.LAST_WOKEN, this.world.getTime());
   }

   private boolean hasRecentlyWorkedAndSlept(long worldTime) {
      Optional<Long> _snowman = this.brain.getOptionalMemory(MemoryModuleType.LAST_SLEPT);
      return _snowman.isPresent() ? worldTime - _snowman.get() < 24000L : false;
   }
}
