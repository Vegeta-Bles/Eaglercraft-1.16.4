package net.minecraft.village.raid;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;

public class Raid {
   private static final Text EVENT_TEXT = new TranslatableText("event.minecraft.raid");
   private static final Text VICTORY_SUFFIX_TEXT = new TranslatableText("event.minecraft.raid.victory");
   private static final Text DEFEAT_SUFFIX_TEXT = new TranslatableText("event.minecraft.raid.defeat");
   private static final Text VICTORY_TITLE = EVENT_TEXT.shallowCopy().append(" - ").append(VICTORY_SUFFIX_TEXT);
   private static final Text DEFEAT_TITLE = EVENT_TEXT.shallowCopy().append(" - ").append(DEFEAT_SUFFIX_TEXT);
   private final Map<Integer, RaiderEntity> waveToCaptain = Maps.newHashMap();
   private final Map<Integer, Set<RaiderEntity>> waveToRaiders = Maps.newHashMap();
   private final Set<UUID> heroesOfTheVillage = Sets.newHashSet();
   private long ticksActive;
   private BlockPos center;
   private final ServerWorld world;
   private boolean started;
   private final int id;
   private float totalHealth;
   private int badOmenLevel;
   private boolean active;
   private int wavesSpawned;
   private final ServerBossBar bar = new ServerBossBar(EVENT_TEXT, BossBar.Color.RED, BossBar.Style.NOTCHED_10);
   private int postRaidTicks;
   private int preRaidTicks;
   private final Random random = new Random();
   private final int waveCount;
   private Raid.Status status;
   private int finishCooldown;
   private Optional<BlockPos> preCalculatedRavagerSpawnLocation = Optional.empty();

   public Raid(int id, ServerWorld world, BlockPos pos) {
      this.id = id;
      this.world = world;
      this.active = true;
      this.preRaidTicks = 300;
      this.bar.setPercent(0.0F);
      this.center = pos;
      this.waveCount = this.getMaxWaves(world.getDifficulty());
      this.status = Raid.Status.ONGOING;
   }

   public Raid(ServerWorld world, CompoundTag tag) {
      this.world = world;
      this.id = tag.getInt("Id");
      this.started = tag.getBoolean("Started");
      this.active = tag.getBoolean("Active");
      this.ticksActive = tag.getLong("TicksActive");
      this.badOmenLevel = tag.getInt("BadOmenLevel");
      this.wavesSpawned = tag.getInt("GroupsSpawned");
      this.preRaidTicks = tag.getInt("PreRaidTicks");
      this.postRaidTicks = tag.getInt("PostRaidTicks");
      this.totalHealth = tag.getFloat("TotalHealth");
      this.center = new BlockPos(tag.getInt("CX"), tag.getInt("CY"), tag.getInt("CZ"));
      this.waveCount = tag.getInt("NumGroups");
      this.status = Raid.Status.fromName(tag.getString("Status"));
      this.heroesOfTheVillage.clear();
      if (tag.contains("HeroesOfTheVillage", 9)) {
         ListTag _snowman = tag.getList("HeroesOfTheVillage", 11);

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            this.heroesOfTheVillage.add(NbtHelper.toUuid(_snowman.get(_snowmanx)));
         }
      }
   }

   public boolean isFinished() {
      return this.hasWon() || this.hasLost();
   }

   public boolean isPreRaid() {
      return this.hasSpawned() && this.getRaiderCount() == 0 && this.preRaidTicks > 0;
   }

   public boolean hasSpawned() {
      return this.wavesSpawned > 0;
   }

   public boolean hasStopped() {
      return this.status == Raid.Status.STOPPED;
   }

   public boolean hasWon() {
      return this.status == Raid.Status.VICTORY;
   }

   public boolean hasLost() {
      return this.status == Raid.Status.LOSS;
   }

   public World getWorld() {
      return this.world;
   }

   public boolean hasStarted() {
      return this.started;
   }

   public int getGroupsSpawned() {
      return this.wavesSpawned;
   }

   private Predicate<ServerPlayerEntity> isInRaidDistance() {
      return player -> {
         BlockPos _snowman = player.getBlockPos();
         return player.isAlive() && this.world.getRaidAt(_snowman) == this;
      };
   }

   private void updateBarToPlayers() {
      Set<ServerPlayerEntity> _snowman = Sets.newHashSet(this.bar.getPlayers());
      List<ServerPlayerEntity> _snowmanx = this.world.getPlayers(this.isInRaidDistance());

      for (ServerPlayerEntity _snowmanxx : _snowmanx) {
         if (!_snowman.contains(_snowmanxx)) {
            this.bar.addPlayer(_snowmanxx);
         }
      }

      for (ServerPlayerEntity _snowmanxxx : _snowman) {
         if (!_snowmanx.contains(_snowmanxxx)) {
            this.bar.removePlayer(_snowmanxxx);
         }
      }
   }

   public int getMaxAcceptableBadOmenLevel() {
      return 5;
   }

   public int getBadOmenLevel() {
      return this.badOmenLevel;
   }

   public void start(PlayerEntity player) {
      if (player.hasStatusEffect(StatusEffects.BAD_OMEN)) {
         this.badOmenLevel = this.badOmenLevel + player.getStatusEffect(StatusEffects.BAD_OMEN).getAmplifier() + 1;
         this.badOmenLevel = MathHelper.clamp(this.badOmenLevel, 0, this.getMaxAcceptableBadOmenLevel());
      }

      player.removeStatusEffect(StatusEffects.BAD_OMEN);
   }

   public void invalidate() {
      this.active = false;
      this.bar.clearPlayers();
      this.status = Raid.Status.STOPPED;
   }

   public void tick() {
      if (!this.hasStopped()) {
         if (this.status == Raid.Status.ONGOING) {
            boolean _snowman = this.active;
            this.active = this.world.isChunkLoaded(this.center);
            if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
               this.invalidate();
               return;
            }

            if (_snowman != this.active) {
               this.bar.setVisible(this.active);
            }

            if (!this.active) {
               return;
            }

            if (!this.world.isNearOccupiedPointOfInterest(this.center)) {
               this.moveRaidCenter();
            }

            if (!this.world.isNearOccupiedPointOfInterest(this.center)) {
               if (this.wavesSpawned > 0) {
                  this.status = Raid.Status.LOSS;
               } else {
                  this.invalidate();
               }
            }

            this.ticksActive++;
            if (this.ticksActive >= 48000L) {
               this.invalidate();
               return;
            }

            int _snowmanx = this.getRaiderCount();
            if (_snowmanx == 0 && this.shouldSpawnMoreGroups()) {
               if (this.preRaidTicks <= 0) {
                  if (this.preRaidTicks == 0 && this.wavesSpawned > 0) {
                     this.preRaidTicks = 300;
                     this.bar.setName(EVENT_TEXT);
                     return;
                  }
               } else {
                  boolean _snowmanxx = this.preCalculatedRavagerSpawnLocation.isPresent();
                  boolean _snowmanxxx = !_snowmanxx && this.preRaidTicks % 5 == 0;
                  if (_snowmanxx && !this.world.getChunkManager().shouldTickChunk(new ChunkPos(this.preCalculatedRavagerSpawnLocation.get()))) {
                     _snowmanxxx = true;
                  }

                  if (_snowmanxxx) {
                     int _snowmanxxxx = 0;
                     if (this.preRaidTicks < 100) {
                        _snowmanxxxx = 1;
                     } else if (this.preRaidTicks < 40) {
                        _snowmanxxxx = 2;
                     }

                     this.preCalculatedRavagerSpawnLocation = this.preCalculateRavagerSpawnLocation(_snowmanxxxx);
                  }

                  if (this.preRaidTicks == 300 || this.preRaidTicks % 20 == 0) {
                     this.updateBarToPlayers();
                  }

                  this.preRaidTicks--;
                  this.bar.setPercent(MathHelper.clamp((float)(300 - this.preRaidTicks) / 300.0F, 0.0F, 1.0F));
               }
            }

            if (this.ticksActive % 20L == 0L) {
               this.updateBarToPlayers();
               this.removeObsoleteRaiders();
               if (_snowmanx > 0) {
                  if (_snowmanx <= 2) {
                     this.bar.setName(EVENT_TEXT.shallowCopy().append(" - ").append(new TranslatableText("event.minecraft.raid.raiders_remaining", _snowmanx)));
                  } else {
                     this.bar.setName(EVENT_TEXT);
                  }
               } else {
                  this.bar.setName(EVENT_TEXT);
               }
            }

            boolean _snowmanxxxx = false;
            int _snowmanxxxxx = 0;

            while (this.canSpawnRaiders()) {
               BlockPos _snowmanxxxxxx = this.preCalculatedRavagerSpawnLocation.isPresent()
                  ? this.preCalculatedRavagerSpawnLocation.get()
                  : this.getRavagerSpawnLocation(_snowmanxxxxx, 20);
               if (_snowmanxxxxxx != null) {
                  this.started = true;
                  this.spawnNextWave(_snowmanxxxxxx);
                  if (!_snowmanxxxx) {
                     this.playRaidHorn(_snowmanxxxxxx);
                     _snowmanxxxx = true;
                  }
               } else {
                  _snowmanxxxxx++;
               }

               if (_snowmanxxxxx > 3) {
                  this.invalidate();
                  break;
               }
            }

            if (this.hasStarted() && !this.shouldSpawnMoreGroups() && _snowmanx == 0) {
               if (this.postRaidTicks < 40) {
                  this.postRaidTicks++;
               } else {
                  this.status = Raid.Status.VICTORY;

                  for (UUID _snowmanxxxxxxx : this.heroesOfTheVillage) {
                     Entity _snowmanxxxxxxxx = this.world.getEntity(_snowmanxxxxxxx);
                     if (_snowmanxxxxxxxx instanceof LivingEntity && !_snowmanxxxxxxxx.isSpectator()) {
                        LivingEntity _snowmanxxxxxxxxx = (LivingEntity)_snowmanxxxxxxxx;
                        _snowmanxxxxxxxxx.addStatusEffect(
                           new StatusEffectInstance(StatusEffects.HERO_OF_THE_VILLAGE, 48000, this.badOmenLevel - 1, false, false, true)
                        );
                        if (_snowmanxxxxxxxxx instanceof ServerPlayerEntity) {
                           ServerPlayerEntity _snowmanxxxxxxxxxx = (ServerPlayerEntity)_snowmanxxxxxxxxx;
                           _snowmanxxxxxxxxxx.incrementStat(Stats.RAID_WIN);
                           Criteria.HERO_OF_THE_VILLAGE.trigger(_snowmanxxxxxxxxxx);
                        }
                     }
                  }
               }
            }

            this.markDirty();
         } else if (this.isFinished()) {
            this.finishCooldown++;
            if (this.finishCooldown >= 600) {
               this.invalidate();
               return;
            }

            if (this.finishCooldown % 20 == 0) {
               this.updateBarToPlayers();
               this.bar.setVisible(true);
               if (this.hasWon()) {
                  this.bar.setPercent(0.0F);
                  this.bar.setName(VICTORY_TITLE);
               } else {
                  this.bar.setName(DEFEAT_TITLE);
               }
            }
         }
      }
   }

   private void moveRaidCenter() {
      Stream<ChunkSectionPos> _snowman = ChunkSectionPos.stream(ChunkSectionPos.from(this.center), 2);
      _snowman.filter(this.world::isNearOccupiedPointOfInterest)
         .map(ChunkSectionPos::getCenterPos)
         .min(Comparator.comparingDouble(_snowmanx -> _snowmanx.getSquaredDistance(this.center)))
         .ifPresent(this::setCenter);
   }

   private Optional<BlockPos> preCalculateRavagerSpawnLocation(int proximity) {
      for (int _snowman = 0; _snowman < 3; _snowman++) {
         BlockPos _snowmanx = this.getRavagerSpawnLocation(proximity, 1);
         if (_snowmanx != null) {
            return Optional.of(_snowmanx);
         }
      }

      return Optional.empty();
   }

   private boolean shouldSpawnMoreGroups() {
      return this.hasExtraWave() ? !this.hasSpawnedExtraWave() : !this.hasSpawnedFinalWave();
   }

   private boolean hasSpawnedFinalWave() {
      return this.getGroupsSpawned() == this.waveCount;
   }

   private boolean hasExtraWave() {
      return this.badOmenLevel > 1;
   }

   private boolean hasSpawnedExtraWave() {
      return this.getGroupsSpawned() > this.waveCount;
   }

   private boolean isSpawningExtraWave() {
      return this.hasSpawnedFinalWave() && this.getRaiderCount() == 0 && this.hasExtraWave();
   }

   private void removeObsoleteRaiders() {
      Iterator<Set<RaiderEntity>> _snowman = this.waveToRaiders.values().iterator();
      Set<RaiderEntity> _snowmanx = Sets.newHashSet();

      while (_snowman.hasNext()) {
         Set<RaiderEntity> _snowmanxx = _snowman.next();

         for (RaiderEntity _snowmanxxx : _snowmanxx) {
            BlockPos _snowmanxxxx = _snowmanxxx.getBlockPos();
            if (_snowmanxxx.removed || _snowmanxxx.world.getRegistryKey() != this.world.getRegistryKey() || this.center.getSquaredDistance(_snowmanxxxx) >= 12544.0) {
               _snowmanx.add(_snowmanxxx);
            } else if (_snowmanxxx.age > 600) {
               if (this.world.getEntity(_snowmanxxx.getUuid()) == null) {
                  _snowmanx.add(_snowmanxxx);
               }

               if (!this.world.isNearOccupiedPointOfInterest(_snowmanxxxx) && _snowmanxxx.getDespawnCounter() > 2400) {
                  _snowmanxxx.setOutOfRaidCounter(_snowmanxxx.getOutOfRaidCounter() + 1);
               }

               if (_snowmanxxx.getOutOfRaidCounter() >= 30) {
                  _snowmanx.add(_snowmanxxx);
               }
            }
         }
      }

      for (RaiderEntity _snowmanxx : _snowmanx) {
         this.removeFromWave(_snowmanxx, true);
      }
   }

   private void playRaidHorn(BlockPos pos) {
      float _snowman = 13.0F;
      int _snowmanx = 64;
      Collection<ServerPlayerEntity> _snowmanxx = this.bar.getPlayers();

      for (ServerPlayerEntity _snowmanxxx : this.world.getPlayers()) {
         Vec3d _snowmanxxxx = _snowmanxxx.getPos();
         Vec3d _snowmanxxxxx = Vec3d.ofCenter(pos);
         float _snowmanxxxxxx = MathHelper.sqrt((_snowmanxxxxx.x - _snowmanxxxx.x) * (_snowmanxxxxx.x - _snowmanxxxx.x) + (_snowmanxxxxx.z - _snowmanxxxx.z) * (_snowmanxxxxx.z - _snowmanxxxx.z));
         double _snowmanxxxxxxx = _snowmanxxxx.x + (double)(13.0F / _snowmanxxxxxx) * (_snowmanxxxxx.x - _snowmanxxxx.x);
         double _snowmanxxxxxxxx = _snowmanxxxx.z + (double)(13.0F / _snowmanxxxxxx) * (_snowmanxxxxx.z - _snowmanxxxx.z);
         if (_snowmanxxxxxx <= 64.0F || _snowmanxx.contains(_snowmanxxx)) {
            _snowmanxxx.networkHandler
               .sendPacket(new PlaySoundS2CPacket(SoundEvents.EVENT_RAID_HORN, SoundCategory.NEUTRAL, _snowmanxxxxxxx, _snowmanxxx.getY(), _snowmanxxxxxxxx, 64.0F, 1.0F));
         }
      }
   }

   private void spawnNextWave(BlockPos pos) {
      boolean _snowman = false;
      int _snowmanx = this.wavesSpawned + 1;
      this.totalHealth = 0.0F;
      LocalDifficulty _snowmanxx = this.world.getLocalDifficulty(pos);
      boolean _snowmanxxx = this.isSpawningExtraWave();

      for (Raid.Member _snowmanxxxx : Raid.Member.VALUES) {
         int _snowmanxxxxx = this.getCount(_snowmanxxxx, _snowmanx, _snowmanxxx) + this.getBonusCount(_snowmanxxxx, this.random, _snowmanx, _snowmanxx, _snowmanxxx);
         int _snowmanxxxxxx = 0;

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxx++) {
            RaiderEntity _snowmanxxxxxxxx = _snowmanxxxx.type.create(this.world);
            if (!_snowman && _snowmanxxxxxxxx.canLead()) {
               _snowmanxxxxxxxx.setPatrolLeader(true);
               this.setWaveCaptain(_snowmanx, _snowmanxxxxxxxx);
               _snowman = true;
            }

            this.addRaider(_snowmanx, _snowmanxxxxxxxx, pos, false);
            if (_snowmanxxxx.type == EntityType.RAVAGER) {
               RaiderEntity _snowmanxxxxxxxxx = null;
               if (_snowmanx == this.getMaxWaves(Difficulty.NORMAL)) {
                  _snowmanxxxxxxxxx = EntityType.PILLAGER.create(this.world);
               } else if (_snowmanx >= this.getMaxWaves(Difficulty.HARD)) {
                  if (_snowmanxxxxxx == 0) {
                     _snowmanxxxxxxxxx = EntityType.EVOKER.create(this.world);
                  } else {
                     _snowmanxxxxxxxxx = EntityType.VINDICATOR.create(this.world);
                  }
               }

               _snowmanxxxxxx++;
               if (_snowmanxxxxxxxxx != null) {
                  this.addRaider(_snowmanx, _snowmanxxxxxxxxx, pos, false);
                  _snowmanxxxxxxxxx.refreshPositionAndAngles(pos, 0.0F, 0.0F);
                  _snowmanxxxxxxxxx.startRiding(_snowmanxxxxxxxx);
               }
            }
         }
      }

      this.preCalculatedRavagerSpawnLocation = Optional.empty();
      this.wavesSpawned++;
      this.updateBar();
      this.markDirty();
   }

   public void addRaider(int wave, RaiderEntity raider, @Nullable BlockPos pos, boolean existing) {
      boolean _snowman = this.addToWave(wave, raider);
      if (_snowman) {
         raider.setRaid(this);
         raider.setWave(wave);
         raider.setAbleToJoinRaid(true);
         raider.setOutOfRaidCounter(0);
         if (!existing && pos != null) {
            raider.updatePosition((double)pos.getX() + 0.5, (double)pos.getY() + 1.0, (double)pos.getZ() + 0.5);
            raider.initialize(this.world, this.world.getLocalDifficulty(pos), SpawnReason.EVENT, null, null);
            raider.addBonusForWave(wave, false);
            raider.setOnGround(true);
            this.world.spawnEntityAndPassengers(raider);
         }
      }
   }

   public void updateBar() {
      this.bar.setPercent(MathHelper.clamp(this.getCurrentRaiderHealth() / this.totalHealth, 0.0F, 1.0F));
   }

   public float getCurrentRaiderHealth() {
      float _snowman = 0.0F;

      for (Set<RaiderEntity> _snowmanx : this.waveToRaiders.values()) {
         for (RaiderEntity _snowmanxx : _snowmanx) {
            _snowman += _snowmanxx.getHealth();
         }
      }

      return _snowman;
   }

   private boolean canSpawnRaiders() {
      return this.preRaidTicks == 0 && (this.wavesSpawned < this.waveCount || this.isSpawningExtraWave()) && this.getRaiderCount() == 0;
   }

   public int getRaiderCount() {
      return this.waveToRaiders.values().stream().mapToInt(Set::size).sum();
   }

   public void removeFromWave(RaiderEntity entity, boolean countHealth) {
      Set<RaiderEntity> _snowman = this.waveToRaiders.get(entity.getWave());
      if (_snowman != null) {
         boolean _snowmanx = _snowman.remove(entity);
         if (_snowmanx) {
            if (countHealth) {
               this.totalHealth = this.totalHealth - entity.getHealth();
            }

            entity.setRaid(null);
            this.updateBar();
            this.markDirty();
         }
      }
   }

   private void markDirty() {
      this.world.getRaidManager().markDirty();
   }

   public static ItemStack getOminousBanner() {
      ItemStack _snowman = new ItemStack(Items.WHITE_BANNER);
      CompoundTag _snowmanx = _snowman.getOrCreateSubTag("BlockEntityTag");
      ListTag _snowmanxx = new BannerPattern.Patterns()
         .add(BannerPattern.RHOMBUS_MIDDLE, DyeColor.CYAN)
         .add(BannerPattern.STRIPE_BOTTOM, DyeColor.LIGHT_GRAY)
         .add(BannerPattern.STRIPE_CENTER, DyeColor.GRAY)
         .add(BannerPattern.BORDER, DyeColor.LIGHT_GRAY)
         .add(BannerPattern.STRIPE_MIDDLE, DyeColor.BLACK)
         .add(BannerPattern.HALF_HORIZONTAL, DyeColor.LIGHT_GRAY)
         .add(BannerPattern.CIRCLE_MIDDLE, DyeColor.LIGHT_GRAY)
         .add(BannerPattern.BORDER, DyeColor.BLACK)
         .toTag();
      _snowmanx.put("Patterns", _snowmanxx);
      _snowman.addHideFlag(ItemStack.TooltipSection.ADDITIONAL);
      _snowman.setCustomName(new TranslatableText("block.minecraft.ominous_banner").formatted(Formatting.GOLD));
      return _snowman;
   }

   @Nullable
   public RaiderEntity getCaptain(int wave) {
      return this.waveToCaptain.get(wave);
   }

   @Nullable
   private BlockPos getRavagerSpawnLocation(int proximity, int tries) {
      int _snowman = proximity == 0 ? 2 : 2 - proximity;
      BlockPos.Mutable _snowmanx = new BlockPos.Mutable();

      for (int _snowmanxx = 0; _snowmanxx < tries; _snowmanxx++) {
         float _snowmanxxx = this.world.random.nextFloat() * (float) (Math.PI * 2);
         int _snowmanxxxx = this.center.getX() + MathHelper.floor(MathHelper.cos(_snowmanxxx) * 32.0F * (float)_snowman) + this.world.random.nextInt(5);
         int _snowmanxxxxx = this.center.getZ() + MathHelper.floor(MathHelper.sin(_snowmanxxx) * 32.0F * (float)_snowman) + this.world.random.nextInt(5);
         int _snowmanxxxxxx = this.world.getTopY(Heightmap.Type.WORLD_SURFACE, _snowmanxxxx, _snowmanxxxxx);
         _snowmanx.set(_snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxx);
         if ((!this.world.isNearOccupiedPointOfInterest(_snowmanx) || proximity >= 2)
            && this.world.isRegionLoaded(_snowmanx.getX() - 10, _snowmanx.getY() - 10, _snowmanx.getZ() - 10, _snowmanx.getX() + 10, _snowmanx.getY() + 10, _snowmanx.getZ() + 10)
            && this.world.getChunkManager().shouldTickChunk(new ChunkPos(_snowmanx))
            && (
               SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, this.world, _snowmanx, EntityType.RAVAGER)
                  || this.world.getBlockState(_snowmanx.down()).isOf(Blocks.SNOW) && this.world.getBlockState(_snowmanx).isAir()
            )) {
            return _snowmanx;
         }
      }

      return null;
   }

   private boolean addToWave(int wave, RaiderEntity entity) {
      return this.addToWave(wave, entity, true);
   }

   public boolean addToWave(int wave, RaiderEntity entity, boolean countHealth) {
      this.waveToRaiders.computeIfAbsent(wave, _snowman -> Sets.newHashSet());
      Set<RaiderEntity> _snowman = this.waveToRaiders.get(wave);
      RaiderEntity _snowmanx = null;

      for (RaiderEntity _snowmanxx : _snowman) {
         if (_snowmanxx.getUuid().equals(entity.getUuid())) {
            _snowmanx = _snowmanxx;
            break;
         }
      }

      if (_snowmanx != null) {
         _snowman.remove(_snowmanx);
         _snowman.add(entity);
      }

      _snowman.add(entity);
      if (countHealth) {
         this.totalHealth = this.totalHealth + entity.getHealth();
      }

      this.updateBar();
      this.markDirty();
      return true;
   }

   public void setWaveCaptain(int wave, RaiderEntity entity) {
      this.waveToCaptain.put(wave, entity);
      entity.equipStack(EquipmentSlot.HEAD, getOminousBanner());
      entity.setEquipmentDropChance(EquipmentSlot.HEAD, 2.0F);
   }

   public void removeLeader(int wave) {
      this.waveToCaptain.remove(wave);
   }

   public BlockPos getCenter() {
      return this.center;
   }

   private void setCenter(BlockPos center) {
      this.center = center;
   }

   public int getRaidId() {
      return this.id;
   }

   private int getCount(Raid.Member member, int wave, boolean extra) {
      return extra ? member.countInWave[this.waveCount] : member.countInWave[wave];
   }

   private int getBonusCount(Raid.Member member, Random random, int wave, LocalDifficulty localDifficulty, boolean extra) {
      Difficulty _snowman = localDifficulty.getGlobalDifficulty();
      boolean _snowmanx = _snowman == Difficulty.EASY;
      boolean _snowmanxx = _snowman == Difficulty.NORMAL;
      int _snowmanxxx;
      switch (member) {
         case WITCH:
            if (_snowmanx || wave <= 2 || wave == 4) {
               return 0;
            }

            _snowmanxxx = 1;
            break;
         case PILLAGER:
         case VINDICATOR:
            if (_snowmanx) {
               _snowmanxxx = random.nextInt(2);
            } else if (_snowmanxx) {
               _snowmanxxx = 1;
            } else {
               _snowmanxxx = 2;
            }
            break;
         case RAVAGER:
            _snowmanxxx = !_snowmanx && extra ? 1 : 0;
            break;
         default:
            return 0;
      }

      return _snowmanxxx > 0 ? random.nextInt(_snowmanxxx + 1) : 0;
   }

   public boolean isActive() {
      return this.active;
   }

   public CompoundTag toTag(CompoundTag tag) {
      tag.putInt("Id", this.id);
      tag.putBoolean("Started", this.started);
      tag.putBoolean("Active", this.active);
      tag.putLong("TicksActive", this.ticksActive);
      tag.putInt("BadOmenLevel", this.badOmenLevel);
      tag.putInt("GroupsSpawned", this.wavesSpawned);
      tag.putInt("PreRaidTicks", this.preRaidTicks);
      tag.putInt("PostRaidTicks", this.postRaidTicks);
      tag.putFloat("TotalHealth", this.totalHealth);
      tag.putInt("NumGroups", this.waveCount);
      tag.putString("Status", this.status.getName());
      tag.putInt("CX", this.center.getX());
      tag.putInt("CY", this.center.getY());
      tag.putInt("CZ", this.center.getZ());
      ListTag _snowman = new ListTag();

      for (UUID _snowmanx : this.heroesOfTheVillage) {
         _snowman.add(NbtHelper.fromUuid(_snowmanx));
      }

      tag.put("HeroesOfTheVillage", _snowman);
      return tag;
   }

   public int getMaxWaves(Difficulty difficulty) {
      switch (difficulty) {
         case EASY:
            return 3;
         case NORMAL:
            return 5;
         case HARD:
            return 7;
         default:
            return 0;
      }
   }

   public float getEnchantmentChance() {
      int _snowman = this.getBadOmenLevel();
      if (_snowman == 2) {
         return 0.1F;
      } else if (_snowman == 3) {
         return 0.25F;
      } else if (_snowman == 4) {
         return 0.5F;
      } else {
         return _snowman == 5 ? 0.75F : 0.0F;
      }
   }

   public void addHero(Entity entity) {
      this.heroesOfTheVillage.add(entity.getUuid());
   }

   static enum Member {
      VINDICATOR(EntityType.VINDICATOR, new int[]{0, 0, 2, 0, 1, 4, 2, 5}),
      EVOKER(EntityType.EVOKER, new int[]{0, 0, 0, 0, 0, 1, 1, 2}),
      PILLAGER(EntityType.PILLAGER, new int[]{0, 4, 3, 3, 4, 4, 4, 2}),
      WITCH(EntityType.WITCH, new int[]{0, 0, 0, 0, 3, 0, 0, 1}),
      RAVAGER(EntityType.RAVAGER, new int[]{0, 0, 0, 1, 0, 1, 0, 2});

      private static final Raid.Member[] VALUES = values();
      private final EntityType<? extends RaiderEntity> type;
      private final int[] countInWave;

      private Member(EntityType<? extends RaiderEntity> type, int[] countInWave) {
         this.type = type;
         this.countInWave = countInWave;
      }
   }

   static enum Status {
      ONGOING,
      VICTORY,
      LOSS,
      STOPPED;

      private static final Raid.Status[] VALUES = values();

      private Status() {
      }

      private static Raid.Status fromName(String _snowman) {
         for (Raid.Status _snowmanx : VALUES) {
            if (_snowman.equalsIgnoreCase(_snowmanx.name())) {
               return _snowmanx;
            }
         }

         return ONGOING;
      }

      public String getName() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }
}
