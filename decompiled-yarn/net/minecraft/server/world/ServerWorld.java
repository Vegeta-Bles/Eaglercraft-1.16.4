package net.minecraft.server.world;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityInteraction;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Npc;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.map.MapState;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.BlockBreakingProgressS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockEventS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundFromEntityS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerSpawnPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldEventS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.tag.TagManager;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.collection.TypeFilterableList;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.CsvWriter;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.village.raid.Raid;
import net.minecraft.village.raid.RaidManager;
import net.minecraft.world.ForcedChunkState;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IdCountsState;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.PortalForcer;
import net.minecraft.world.ScheduledTick;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import net.minecraft.world.gen.Spawner;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerWorld extends World implements StructureWorldAccess {
   public static final BlockPos END_SPAWN_POS = new BlockPos(100, 50, 0);
   private static final Logger LOGGER = LogManager.getLogger();
   private final Int2ObjectMap<Entity> entitiesById = new Int2ObjectLinkedOpenHashMap();
   private final Map<UUID, Entity> entitiesByUuid = Maps.newHashMap();
   private final Queue<Entity> entitiesToLoad = Queues.newArrayDeque();
   private final List<ServerPlayerEntity> players = Lists.newArrayList();
   private final ServerChunkManager serverChunkManager;
   boolean inEntityTick;
   private final MinecraftServer server;
   private final ServerWorldProperties worldProperties;
   public boolean savingDisabled;
   private boolean allPlayersSleeping;
   private int idleTimeout;
   private final PortalForcer portalForcer;
   private final ServerTickScheduler<Block> blockTickScheduler = new ServerTickScheduler<>(
      this, _snowmanxxxxxxx -> _snowmanxxxxxxx == null || _snowmanxxxxxxx.getDefaultState().isAir(), Registry.BLOCK::getId, this::tickBlock
   );
   private final ServerTickScheduler<Fluid> fluidTickScheduler = new ServerTickScheduler<>(
      this, _snowmanxxxxxxx -> _snowmanxxxxxxx == null || _snowmanxxxxxxx == Fluids.EMPTY, Registry.FLUID::getId, this::tickFluid
   );
   private final Set<EntityNavigation> entityNavigations = Sets.newHashSet();
   protected final RaidManager raidManager;
   private final ObjectLinkedOpenHashSet<BlockEvent> syncedBlockEventQueue = new ObjectLinkedOpenHashSet();
   private boolean inBlockTick;
   private final List<Spawner> spawners;
   @Nullable
   private final EnderDragonFight enderDragonFight;
   private final StructureAccessor structureAccessor;
   private final boolean shouldTickTime;

   public ServerWorld(
      MinecraftServer server,
      Executor workerExecutor,
      LevelStorage.Session session,
      ServerWorldProperties properties,
      RegistryKey<World> _snowman,
      DimensionType _snowman,
      WorldGenerationProgressListener _snowman,
      ChunkGenerator _snowman,
      boolean debugWorld,
      long _snowman,
      List<Spawner> _snowman,
      boolean _snowman
   ) {
      super(properties, _snowman, _snowman, server::getProfiler, false, debugWorld, _snowman);
      this.shouldTickTime = _snowman;
      this.server = server;
      this.spawners = _snowman;
      this.worldProperties = properties;
      this.serverChunkManager = new ServerChunkManager(
         this,
         session,
         server.getDataFixer(),
         server.getStructureManager(),
         workerExecutor,
         _snowman,
         server.getPlayerManager().getViewDistance(),
         server.syncChunkWrites(),
         _snowman,
         () -> server.getOverworld().getPersistentStateManager()
      );
      this.portalForcer = new PortalForcer(this);
      this.calculateAmbientDarkness();
      this.initWeatherGradients();
      this.getWorldBorder().setMaxWorldBorderRadius(server.getMaxWorldBorderRadius());
      this.raidManager = this.getPersistentStateManager().getOrCreate(() -> new RaidManager(this), RaidManager.nameFor(this.getDimension()));
      if (!server.isSinglePlayer()) {
         properties.setGameMode(server.getDefaultGameMode());
      }

      this.structureAccessor = new StructureAccessor(this, server.getSaveProperties().getGeneratorOptions());
      if (this.getDimension().hasEnderDragonFight()) {
         this.enderDragonFight = new EnderDragonFight(
            this, server.getSaveProperties().getGeneratorOptions().getSeed(), server.getSaveProperties().getDragonFight()
         );
      } else {
         this.enderDragonFight = null;
      }
   }

   public void setWeather(int clearDuration, int rainDuration, boolean raining, boolean thundering) {
      this.worldProperties.setClearWeatherTime(clearDuration);
      this.worldProperties.setRainTime(rainDuration);
      this.worldProperties.setThunderTime(rainDuration);
      this.worldProperties.setRaining(raining);
      this.worldProperties.setThundering(thundering);
   }

   @Override
   public Biome getGeneratorStoredBiome(int biomeX, int biomeY, int biomeZ) {
      return this.getChunkManager().getChunkGenerator().getBiomeSource().getBiomeForNoiseGen(biomeX, biomeY, biomeZ);
   }

   public StructureAccessor getStructureAccessor() {
      return this.structureAccessor;
   }

   public void tick(BooleanSupplier shouldKeepTicking) {
      Profiler _snowman = this.getProfiler();
      this.inBlockTick = true;
      _snowman.push("world border");
      this.getWorldBorder().tick();
      _snowman.swap("weather");
      boolean _snowmanx = this.isRaining();
      if (this.getDimension().hasSkyLight()) {
         if (this.getGameRules().getBoolean(GameRules.DO_WEATHER_CYCLE)) {
            int _snowmanxx = this.worldProperties.getClearWeatherTime();
            int _snowmanxxx = this.worldProperties.getThunderTime();
            int _snowmanxxxx = this.worldProperties.getRainTime();
            boolean _snowmanxxxxx = this.properties.isThundering();
            boolean _snowmanxxxxxx = this.properties.isRaining();
            if (_snowmanxx > 0) {
               _snowmanxx--;
               _snowmanxxx = _snowmanxxxxx ? 0 : 1;
               _snowmanxxxx = _snowmanxxxxxx ? 0 : 1;
               _snowmanxxxxx = false;
               _snowmanxxxxxx = false;
            } else {
               if (_snowmanxxx > 0) {
                  if (--_snowmanxxx == 0) {
                     _snowmanxxxxx = !_snowmanxxxxx;
                  }
               } else if (_snowmanxxxxx) {
                  _snowmanxxx = this.random.nextInt(12000) + 3600;
               } else {
                  _snowmanxxx = this.random.nextInt(168000) + 12000;
               }

               if (_snowmanxxxx > 0) {
                  if (--_snowmanxxxx == 0) {
                     _snowmanxxxxxx = !_snowmanxxxxxx;
                  }
               } else if (_snowmanxxxxxx) {
                  _snowmanxxxx = this.random.nextInt(12000) + 12000;
               } else {
                  _snowmanxxxx = this.random.nextInt(168000) + 12000;
               }
            }

            this.worldProperties.setThunderTime(_snowmanxxx);
            this.worldProperties.setRainTime(_snowmanxxxx);
            this.worldProperties.setClearWeatherTime(_snowmanxx);
            this.worldProperties.setThundering(_snowmanxxxxx);
            this.worldProperties.setRaining(_snowmanxxxxxx);
         }

         this.thunderGradientPrev = this.thunderGradient;
         if (this.properties.isThundering()) {
            this.thunderGradient = (float)((double)this.thunderGradient + 0.01);
         } else {
            this.thunderGradient = (float)((double)this.thunderGradient - 0.01);
         }

         this.thunderGradient = MathHelper.clamp(this.thunderGradient, 0.0F, 1.0F);
         this.rainGradientPrev = this.rainGradient;
         if (this.properties.isRaining()) {
            this.rainGradient = (float)((double)this.rainGradient + 0.01);
         } else {
            this.rainGradient = (float)((double)this.rainGradient - 0.01);
         }

         this.rainGradient = MathHelper.clamp(this.rainGradient, 0.0F, 1.0F);
      }

      if (this.rainGradientPrev != this.rainGradient) {
         this.server
            .getPlayerManager()
            .sendToDimension(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.RAIN_GRADIENT_CHANGED, this.rainGradient), this.getRegistryKey());
      }

      if (this.thunderGradientPrev != this.thunderGradient) {
         this.server
            .getPlayerManager()
            .sendToDimension(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.THUNDER_GRADIENT_CHANGED, this.thunderGradient), this.getRegistryKey());
      }

      if (_snowmanx != this.isRaining()) {
         if (_snowmanx) {
            this.server.getPlayerManager().sendToAll(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.RAIN_STOPPED, 0.0F));
         } else {
            this.server.getPlayerManager().sendToAll(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.RAIN_STARTED, 0.0F));
         }

         this.server.getPlayerManager().sendToAll(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.RAIN_GRADIENT_CHANGED, this.rainGradient));
         this.server.getPlayerManager().sendToAll(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.THUNDER_GRADIENT_CHANGED, this.thunderGradient));
      }

      if (this.allPlayersSleeping && this.players.stream().noneMatch(player -> !player.isSpectator() && !player.isSleepingLongEnough())) {
         this.allPlayersSleeping = false;
         if (this.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
            long _snowmanxx = this.properties.getTimeOfDay() + 24000L;
            this.setTimeOfDay(_snowmanxx - _snowmanxx % 24000L);
         }

         this.wakeSleepingPlayers();
         if (this.getGameRules().getBoolean(GameRules.DO_WEATHER_CYCLE)) {
            this.resetWeather();
         }
      }

      this.calculateAmbientDarkness();
      this.tickTime();
      _snowman.swap("chunkSource");
      this.getChunkManager().tick(shouldKeepTicking);
      _snowman.swap("tickPending");
      if (!this.isDebugWorld()) {
         this.blockTickScheduler.tick();
         this.fluidTickScheduler.tick();
      }

      _snowman.swap("raid");
      this.raidManager.tick();
      _snowman.swap("blockEvents");
      this.processSyncedBlockEvents();
      this.inBlockTick = false;
      _snowman.swap("entities");
      boolean _snowmanxx = !this.players.isEmpty() || !this.getForcedChunks().isEmpty();
      if (_snowmanxx) {
         this.resetIdleTimeout();
      }

      if (_snowmanxx || this.idleTimeout++ < 300) {
         if (this.enderDragonFight != null) {
            this.enderDragonFight.tick();
         }

         this.inEntityTick = true;
         ObjectIterator<Entry<Entity>> _snowmanxxx = this.entitiesById.int2ObjectEntrySet().iterator();

         while (_snowmanxxx.hasNext()) {
            Entry<Entity> _snowmanxxxx = (Entry<Entity>)_snowmanxxx.next();
            Entity _snowmanxxxxx = (Entity)_snowmanxxxx.getValue();
            Entity _snowmanxxxxxx = _snowmanxxxxx.getVehicle();
            if (!this.server.shouldSpawnAnimals() && (_snowmanxxxxx instanceof AnimalEntity || _snowmanxxxxx instanceof WaterCreatureEntity)) {
               _snowmanxxxxx.remove();
            }

            if (!this.server.shouldSpawnNpcs() && _snowmanxxxxx instanceof Npc) {
               _snowmanxxxxx.remove();
            }

            _snowman.push("checkDespawn");
            if (!_snowmanxxxxx.removed) {
               _snowmanxxxxx.checkDespawn();
            }

            _snowman.pop();
            if (_snowmanxxxxxx != null) {
               if (!_snowmanxxxxxx.removed && _snowmanxxxxxx.hasPassenger(_snowmanxxxxx)) {
                  continue;
               }

               _snowmanxxxxx.stopRiding();
            }

            _snowman.push("tick");
            if (!_snowmanxxxxx.removed && !(_snowmanxxxxx instanceof EnderDragonPart)) {
               this.tickEntity(this::tickEntity, _snowmanxxxxx);
            }

            _snowman.pop();
            _snowman.push("remove");
            if (_snowmanxxxxx.removed) {
               this.removeEntityFromChunk(_snowmanxxxxx);
               _snowmanxxx.remove();
               this.unloadEntity(_snowmanxxxxx);
            }

            _snowman.pop();
         }

         this.inEntityTick = false;

         Entity _snowmanxxxxxxx;
         while ((_snowmanxxxxxxx = this.entitiesToLoad.poll()) != null) {
            this.loadEntityUnchecked(_snowmanxxxxxxx);
         }

         this.tickBlockEntities();
      }

      _snowman.pop();
   }

   protected void tickTime() {
      if (this.shouldTickTime) {
         long _snowman = this.properties.getTime() + 1L;
         this.worldProperties.setTime(_snowman);
         this.worldProperties.getScheduledEvents().processEvents(this.server, _snowman);
         if (this.properties.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
            this.setTimeOfDay(this.properties.getTimeOfDay() + 1L);
         }
      }
   }

   public void setTimeOfDay(long timeOfDay) {
      this.worldProperties.setTimeOfDay(timeOfDay);
   }

   public void tickSpawners(boolean spawnMonsters, boolean spawnAnimals) {
      for (Spawner _snowman : this.spawners) {
         _snowman.spawn(this, spawnMonsters, spawnAnimals);
      }
   }

   private void wakeSleepingPlayers() {
      this.players.stream().filter(LivingEntity::isSleeping).collect(Collectors.toList()).forEach(player -> player.wakeUp(false, false));
   }

   public void tickChunk(WorldChunk chunk, int randomTickSpeed) {
      ChunkPos _snowman = chunk.getPos();
      boolean _snowmanx = this.isRaining();
      int _snowmanxx = _snowman.getStartX();
      int _snowmanxxx = _snowman.getStartZ();
      Profiler _snowmanxxxx = this.getProfiler();
      _snowmanxxxx.push("thunder");
      if (_snowmanx && this.isThundering() && this.random.nextInt(100000) == 0) {
         BlockPos _snowmanxxxxx = this.getSurface(this.getRandomPosInChunk(_snowmanxx, 0, _snowmanxxx, 15));
         if (this.hasRain(_snowmanxxxxx)) {
            LocalDifficulty _snowmanxxxxxx = this.getLocalDifficulty(_snowmanxxxxx);
            boolean _snowmanxxxxxxx = this.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)
               && this.random.nextDouble() < (double)_snowmanxxxxxx.getLocalDifficulty() * 0.01;
            if (_snowmanxxxxxxx) {
               SkeletonHorseEntity _snowmanxxxxxxxx = EntityType.SKELETON_HORSE.create(this);
               _snowmanxxxxxxxx.setTrapped(true);
               _snowmanxxxxxxxx.setBreedingAge(0);
               _snowmanxxxxxxxx.updatePosition((double)_snowmanxxxxx.getX(), (double)_snowmanxxxxx.getY(), (double)_snowmanxxxxx.getZ());
               this.spawnEntity(_snowmanxxxxxxxx);
            }

            LightningEntity _snowmanxxxxxxxx = EntityType.LIGHTNING_BOLT.create(this);
            _snowmanxxxxxxxx.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(_snowmanxxxxx));
            _snowmanxxxxxxxx.setCosmetic(_snowmanxxxxxxx);
            this.spawnEntity(_snowmanxxxxxxxx);
         }
      }

      _snowmanxxxx.swap("iceandsnow");
      if (this.random.nextInt(16) == 0) {
         BlockPos _snowmanxxxxx = this.getTopPosition(Heightmap.Type.MOTION_BLOCKING, this.getRandomPosInChunk(_snowmanxx, 0, _snowmanxxx, 15));
         BlockPos _snowmanxxxxxx = _snowmanxxxxx.down();
         Biome _snowmanxxxxxxx = this.getBiome(_snowmanxxxxx);
         if (_snowmanxxxxxxx.canSetIce(this, _snowmanxxxxxx)) {
            this.setBlockState(_snowmanxxxxxx, Blocks.ICE.getDefaultState());
         }

         if (_snowmanx && _snowmanxxxxxxx.canSetSnow(this, _snowmanxxxxx)) {
            this.setBlockState(_snowmanxxxxx, Blocks.SNOW.getDefaultState());
         }

         if (_snowmanx && this.getBiome(_snowmanxxxxxx).getPrecipitation() == Biome.Precipitation.RAIN) {
            this.getBlockState(_snowmanxxxxxx).getBlock().rainTick(this, _snowmanxxxxxx);
         }
      }

      _snowmanxxxx.swap("tickBlocks");
      if (randomTickSpeed > 0) {
         for (ChunkSection _snowmanxxxxxxxx : chunk.getSectionArray()) {
            if (_snowmanxxxxxxxx != WorldChunk.EMPTY_SECTION && _snowmanxxxxxxxx.hasRandomTicks()) {
               int _snowmanxxxxxxxxx = _snowmanxxxxxxxx.getYOffset();

               for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < randomTickSpeed; _snowmanxxxxxxxxxx++) {
                  BlockPos _snowmanxxxxxxxxxxx = this.getRandomPosInChunk(_snowmanxx, _snowmanxxxxxxxxx, _snowmanxxx, 15);
                  _snowmanxxxx.push("randomTick");
                  BlockState _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxx.getBlockState(_snowmanxxxxxxxxxxx.getX() - _snowmanxx, _snowmanxxxxxxxxxxx.getY() - _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx.getZ() - _snowmanxxx);
                  if (_snowmanxxxxxxxxxxxx.hasRandomTicks()) {
                     _snowmanxxxxxxxxxxxx.randomTick(this, _snowmanxxxxxxxxxxx, this.random);
                  }

                  FluidState _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.getFluidState();
                  if (_snowmanxxxxxxxxxxxxx.hasRandomTicks()) {
                     _snowmanxxxxxxxxxxxxx.onRandomTick(this, _snowmanxxxxxxxxxxx, this.random);
                  }

                  _snowmanxxxx.pop();
               }
            }
         }
      }

      _snowmanxxxx.pop();
   }

   protected BlockPos getSurface(BlockPos pos) {
      BlockPos _snowman = this.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos);
      Box _snowmanx = new Box(_snowman, new BlockPos(_snowman.getX(), this.getHeight(), _snowman.getZ())).expand(3.0);
      List<LivingEntity> _snowmanxx = this.getEntitiesByClass(
         LivingEntity.class, _snowmanx, entity -> entity != null && entity.isAlive() && this.isSkyVisible(entity.getBlockPos())
      );
      if (!_snowmanxx.isEmpty()) {
         return _snowmanxx.get(this.random.nextInt(_snowmanxx.size())).getBlockPos();
      } else {
         if (_snowman.getY() == -1) {
            _snowman = _snowman.up(2);
         }

         return _snowman;
      }
   }

   public boolean isInBlockTick() {
      return this.inBlockTick;
   }

   public void updateSleepingPlayers() {
      this.allPlayersSleeping = false;
      if (!this.players.isEmpty()) {
         int _snowman = 0;
         int _snowmanx = 0;

         for (ServerPlayerEntity _snowmanxx : this.players) {
            if (_snowmanxx.isSpectator()) {
               _snowman++;
            } else if (_snowmanxx.isSleeping()) {
               _snowmanx++;
            }
         }

         this.allPlayersSleeping = _snowmanx > 0 && _snowmanx >= this.players.size() - _snowman;
      }
   }

   public ServerScoreboard getScoreboard() {
      return this.server.getScoreboard();
   }

   private void resetWeather() {
      this.worldProperties.setRainTime(0);
      this.worldProperties.setRaining(false);
      this.worldProperties.setThunderTime(0);
      this.worldProperties.setThundering(false);
   }

   public void resetIdleTimeout() {
      this.idleTimeout = 0;
   }

   private void tickFluid(ScheduledTick<Fluid> tick) {
      FluidState _snowman = this.getFluidState(tick.pos);
      if (_snowman.getFluid() == tick.getObject()) {
         _snowman.onScheduledTick(this, tick.pos);
      }
   }

   private void tickBlock(ScheduledTick<Block> tick) {
      BlockState _snowman = this.getBlockState(tick.pos);
      if (_snowman.isOf(tick.getObject())) {
         _snowman.scheduledTick(this, tick.pos, this.random);
      }
   }

   public void tickEntity(Entity entity) {
      if (!(entity instanceof PlayerEntity) && !this.getChunkManager().shouldTickEntity(entity)) {
         this.checkEntityChunkPos(entity);
      } else {
         entity.resetPosition(entity.getX(), entity.getY(), entity.getZ());
         entity.prevYaw = entity.yaw;
         entity.prevPitch = entity.pitch;
         if (entity.updateNeeded) {
            entity.age++;
            Profiler _snowman = this.getProfiler();
            _snowman.push(() -> Registry.ENTITY_TYPE.getId(entity.getType()).toString());
            _snowman.visit("tickNonPassenger");
            entity.tick();
            _snowman.pop();
         }

         this.checkEntityChunkPos(entity);
         if (entity.updateNeeded) {
            for (Entity _snowman : entity.getPassengerList()) {
               this.tickPassenger(entity, _snowman);
            }
         }
      }
   }

   public void tickPassenger(Entity vehicle, Entity passenger) {
      if (passenger.removed || passenger.getVehicle() != vehicle) {
         passenger.stopRiding();
      } else if (passenger instanceof PlayerEntity || this.getChunkManager().shouldTickEntity(passenger)) {
         passenger.resetPosition(passenger.getX(), passenger.getY(), passenger.getZ());
         passenger.prevYaw = passenger.yaw;
         passenger.prevPitch = passenger.pitch;
         if (passenger.updateNeeded) {
            passenger.age++;
            Profiler _snowman = this.getProfiler();
            _snowman.push(() -> Registry.ENTITY_TYPE.getId(passenger.getType()).toString());
            _snowman.visit("tickPassenger");
            passenger.tickRiding();
            _snowman.pop();
         }

         this.checkEntityChunkPos(passenger);
         if (passenger.updateNeeded) {
            for (Entity _snowman : passenger.getPassengerList()) {
               this.tickPassenger(passenger, _snowman);
            }
         }
      }
   }

   public void checkEntityChunkPos(Entity entity) {
      if (entity.isChunkPosUpdateRequested()) {
         this.getProfiler().push("chunkCheck");
         int _snowman = MathHelper.floor(entity.getX() / 16.0);
         int _snowmanx = MathHelper.floor(entity.getY() / 16.0);
         int _snowmanxx = MathHelper.floor(entity.getZ() / 16.0);
         if (!entity.updateNeeded || entity.chunkX != _snowman || entity.chunkY != _snowmanx || entity.chunkZ != _snowmanxx) {
            if (entity.updateNeeded && this.isChunkLoaded(entity.chunkX, entity.chunkZ)) {
               this.getChunk(entity.chunkX, entity.chunkZ).remove(entity, entity.chunkY);
            }

            if (!entity.teleportRequested() && !this.isChunkLoaded(_snowman, _snowmanxx)) {
               if (entity.updateNeeded) {
                  LOGGER.warn("Entity {} left loaded chunk area", entity);
               }

               entity.updateNeeded = false;
            } else {
               this.getChunk(_snowman, _snowmanxx).addEntity(entity);
            }
         }

         this.getProfiler().pop();
      }
   }

   @Override
   public boolean canPlayerModifyAt(PlayerEntity player, BlockPos pos) {
      return !this.server.isSpawnProtected(this, pos, player) && this.getWorldBorder().contains(pos);
   }

   public void save(@Nullable ProgressListener progressListener, boolean flush, boolean _snowman) {
      ServerChunkManager _snowmanx = this.getChunkManager();
      if (!_snowman) {
         if (progressListener != null) {
            progressListener.method_15412(new TranslatableText("menu.savingLevel"));
         }

         this.saveLevel();
         if (progressListener != null) {
            progressListener.method_15414(new TranslatableText("menu.savingChunks"));
         }

         _snowmanx.save(flush);
      }
   }

   private void saveLevel() {
      if (this.enderDragonFight != null) {
         this.server.getSaveProperties().setDragonFight(this.enderDragonFight.toTag());
      }

      this.getChunkManager().getPersistentStateManager().save();
   }

   public List<Entity> getEntitiesByType(@Nullable EntityType<?> type, Predicate<? super Entity> predicate) {
      List<Entity> _snowman = Lists.newArrayList();
      ServerChunkManager _snowmanx = this.getChunkManager();
      ObjectIterator var5 = this.entitiesById.values().iterator();

      while (var5.hasNext()) {
         Entity _snowmanxx = (Entity)var5.next();
         if ((type == null || _snowmanxx.getType() == type)
            && _snowmanx.isChunkLoaded(MathHelper.floor(_snowmanxx.getX()) >> 4, MathHelper.floor(_snowmanxx.getZ()) >> 4)
            && predicate.test(_snowmanxx)) {
            _snowman.add(_snowmanxx);
         }
      }

      return _snowman;
   }

   public List<EnderDragonEntity> getAliveEnderDragons() {
      List<EnderDragonEntity> _snowman = Lists.newArrayList();
      ObjectIterator var2 = this.entitiesById.values().iterator();

      while (var2.hasNext()) {
         Entity _snowmanx = (Entity)var2.next();
         if (_snowmanx instanceof EnderDragonEntity && _snowmanx.isAlive()) {
            _snowman.add((EnderDragonEntity)_snowmanx);
         }
      }

      return _snowman;
   }

   public List<ServerPlayerEntity> getPlayers(Predicate<? super ServerPlayerEntity> predicate) {
      List<ServerPlayerEntity> _snowman = Lists.newArrayList();

      for (ServerPlayerEntity _snowmanx : this.players) {
         if (predicate.test(_snowmanx)) {
            _snowman.add(_snowmanx);
         }
      }

      return _snowman;
   }

   @Nullable
   public ServerPlayerEntity getRandomAlivePlayer() {
      List<ServerPlayerEntity> _snowman = this.getPlayers(LivingEntity::isAlive);
      return _snowman.isEmpty() ? null : _snowman.get(this.random.nextInt(_snowman.size()));
   }

   @Override
   public boolean spawnEntity(Entity entity) {
      return this.addEntity(entity);
   }

   public boolean tryLoadEntity(Entity entity) {
      return this.addEntity(entity);
   }

   public void onDimensionChanged(Entity entity) {
      boolean _snowman = entity.teleporting;
      entity.teleporting = true;
      this.tryLoadEntity(entity);
      entity.teleporting = _snowman;
      this.checkEntityChunkPos(entity);
   }

   public void onPlayerTeleport(ServerPlayerEntity player) {
      this.addPlayer(player);
      this.checkEntityChunkPos(player);
   }

   public void onPlayerChangeDimension(ServerPlayerEntity player) {
      this.addPlayer(player);
      this.checkEntityChunkPos(player);
   }

   public void onPlayerConnected(ServerPlayerEntity player) {
      this.addPlayer(player);
   }

   public void onPlayerRespawned(ServerPlayerEntity player) {
      this.addPlayer(player);
   }

   private void addPlayer(ServerPlayerEntity player) {
      Entity _snowman = this.entitiesByUuid.get(player.getUuid());
      if (_snowman != null) {
         LOGGER.warn("Force-added player with duplicate UUID {}", player.getUuid().toString());
         _snowman.detach();
         this.removePlayer((ServerPlayerEntity)_snowman);
      }

      this.players.add(player);
      this.updateSleepingPlayers();
      Chunk _snowmanx = this.getChunk(MathHelper.floor(player.getX() / 16.0), MathHelper.floor(player.getZ() / 16.0), ChunkStatus.FULL, true);
      if (_snowmanx instanceof WorldChunk) {
         _snowmanx.addEntity(player);
      }

      this.loadEntityUnchecked(player);
   }

   private boolean addEntity(Entity entity) {
      if (entity.removed) {
         LOGGER.warn("Tried to add entity {} but it was marked as removed already", EntityType.getId(entity.getType()));
         return false;
      } else if (this.checkUuid(entity)) {
         return false;
      } else {
         Chunk _snowman = this.getChunk(MathHelper.floor(entity.getX() / 16.0), MathHelper.floor(entity.getZ() / 16.0), ChunkStatus.FULL, entity.teleporting);
         if (!(_snowman instanceof WorldChunk)) {
            return false;
         } else {
            _snowman.addEntity(entity);
            this.loadEntityUnchecked(entity);
            return true;
         }
      }
   }

   public boolean loadEntity(Entity entity) {
      if (this.checkUuid(entity)) {
         return false;
      } else {
         this.loadEntityUnchecked(entity);
         return true;
      }
   }

   private boolean checkUuid(Entity _snowman) {
      UUID _snowmanx = _snowman.getUuid();
      Entity _snowmanxx = this.checkIfUuidExists(_snowmanx);
      if (_snowmanxx == null) {
         return false;
      } else {
         LOGGER.warn(
            "Trying to add entity with duplicated UUID {}. Existing {}#{}, new: {}#{}",
            _snowmanx,
            EntityType.getId(_snowmanxx.getType()),
            _snowmanxx.getEntityId(),
            EntityType.getId(_snowman.getType()),
            _snowman.getEntityId()
         );
         return true;
      }
   }

   @Nullable
   private Entity checkIfUuidExists(UUID _snowman) {
      Entity _snowmanx = this.entitiesByUuid.get(_snowman);
      if (_snowmanx != null) {
         return _snowmanx;
      } else {
         if (this.inEntityTick) {
            for (Entity _snowmanxx : this.entitiesToLoad) {
               if (_snowmanxx.getUuid().equals(_snowman)) {
                  return _snowmanxx;
               }
            }
         }

         return null;
      }
   }

   public boolean shouldCreateNewEntityWithPassenger(Entity _snowman) {
      if (_snowman.streamPassengersRecursively().anyMatch(this::checkUuid)) {
         return false;
      } else {
         this.spawnEntityAndPassengers(_snowman);
         return true;
      }
   }

   public void unloadEntities(WorldChunk chunk) {
      this.unloadedBlockEntities.addAll(chunk.getBlockEntities().values());
      TypeFilterableList[] var2 = chunk.getEntitySectionArray();
      int var3 = var2.length;

      for (int var4 = 0; var4 < var3; var4++) {
         for (Entity _snowman : var2[var4]) {
            if (!(_snowman instanceof ServerPlayerEntity)) {
               if (this.inEntityTick) {
                  throw (IllegalStateException)Util.throwOrPause(new IllegalStateException("Removing entity while ticking!"));
               }

               this.entitiesById.remove(_snowman.getEntityId());
               this.unloadEntity(_snowman);
            }
         }
      }
   }

   public void unloadEntity(Entity entity) {
      if (entity instanceof EnderDragonEntity) {
         for (EnderDragonPart _snowman : ((EnderDragonEntity)entity).getBodyParts()) {
            _snowman.remove();
         }
      }

      this.entitiesByUuid.remove(entity.getUuid());
      this.getChunkManager().unloadEntity(entity);
      if (entity instanceof ServerPlayerEntity) {
         ServerPlayerEntity _snowman = (ServerPlayerEntity)entity;
         this.players.remove(_snowman);
      }

      this.getScoreboard().resetEntityScore(entity);
      if (entity instanceof MobEntity) {
         this.entityNavigations.remove(((MobEntity)entity).getNavigation());
      }
   }

   private void loadEntityUnchecked(Entity entity) {
      if (this.inEntityTick) {
         this.entitiesToLoad.add(entity);
      } else {
         this.entitiesById.put(entity.getEntityId(), entity);
         if (entity instanceof EnderDragonEntity) {
            for (EnderDragonPart _snowman : ((EnderDragonEntity)entity).getBodyParts()) {
               this.entitiesById.put(_snowman.getEntityId(), _snowman);
            }
         }

         this.entitiesByUuid.put(entity.getUuid(), entity);
         this.getChunkManager().loadEntity(entity);
         if (entity instanceof MobEntity) {
            this.entityNavigations.add(((MobEntity)entity).getNavigation());
         }
      }
   }

   public void removeEntity(Entity entity) {
      if (this.inEntityTick) {
         throw (IllegalStateException)Util.throwOrPause(new IllegalStateException("Removing entity while ticking!"));
      } else {
         this.removeEntityFromChunk(entity);
         this.entitiesById.remove(entity.getEntityId());
         this.unloadEntity(entity);
      }
   }

   private void removeEntityFromChunk(Entity entity) {
      Chunk _snowman = this.getChunk(entity.chunkX, entity.chunkZ, ChunkStatus.FULL, false);
      if (_snowman instanceof WorldChunk) {
         ((WorldChunk)_snowman).remove(entity);
      }
   }

   public void removePlayer(ServerPlayerEntity player) {
      player.remove();
      this.removeEntity(player);
      this.updateSleepingPlayers();
   }

   @Override
   public void setBlockBreakingInfo(int entityId, BlockPos pos, int progress) {
      for (ServerPlayerEntity _snowman : this.server.getPlayerManager().getPlayerList()) {
         if (_snowman != null && _snowman.world == this && _snowman.getEntityId() != entityId) {
            double _snowmanx = (double)pos.getX() - _snowman.getX();
            double _snowmanxx = (double)pos.getY() - _snowman.getY();
            double _snowmanxxx = (double)pos.getZ() - _snowman.getZ();
            if (_snowmanx * _snowmanx + _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx < 1024.0) {
               _snowman.networkHandler.sendPacket(new BlockBreakingProgressS2CPacket(entityId, pos, progress));
            }
         }
      }
   }

   @Override
   public void playSound(@Nullable PlayerEntity player, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch) {
      this.server
         .getPlayerManager()
         .sendToAround(
            player,
            x,
            y,
            z,
            volume > 1.0F ? (double)(16.0F * volume) : 16.0,
            this.getRegistryKey(),
            new PlaySoundS2CPacket(sound, category, x, y, z, volume, pitch)
         );
   }

   @Override
   public void playSoundFromEntity(@Nullable PlayerEntity player, Entity entity, SoundEvent sound, SoundCategory category, float volume, float pitch) {
      this.server
         .getPlayerManager()
         .sendToAround(
            player,
            entity.getX(),
            entity.getY(),
            entity.getZ(),
            volume > 1.0F ? (double)(16.0F * volume) : 16.0,
            this.getRegistryKey(),
            new PlaySoundFromEntityS2CPacket(sound, category, entity, volume, pitch)
         );
   }

   @Override
   public void syncGlobalEvent(int eventId, BlockPos pos, int data) {
      this.server.getPlayerManager().sendToAll(new WorldEventS2CPacket(eventId, pos, data, true));
   }

   @Override
   public void syncWorldEvent(@Nullable PlayerEntity player, int eventId, BlockPos pos, int data) {
      this.server
         .getPlayerManager()
         .sendToAround(
            player, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 64.0, this.getRegistryKey(), new WorldEventS2CPacket(eventId, pos, data, false)
         );
   }

   @Override
   public void updateListeners(BlockPos pos, BlockState oldState, BlockState newState, int flags) {
      this.getChunkManager().markForUpdate(pos);
      VoxelShape _snowman = oldState.getCollisionShape(this, pos);
      VoxelShape _snowmanx = newState.getCollisionShape(this, pos);
      if (VoxelShapes.matchesAnywhere(_snowman, _snowmanx, BooleanBiFunction.NOT_SAME)) {
         for (EntityNavigation _snowmanxx : this.entityNavigations) {
            if (!_snowmanxx.shouldRecalculatePath()) {
               _snowmanxx.onBlockChanged(pos);
            }
         }
      }
   }

   @Override
   public void sendEntityStatus(Entity entity, byte status) {
      this.getChunkManager().sendToNearbyPlayers(entity, new EntityStatusS2CPacket(entity, status));
   }

   public ServerChunkManager getChunkManager() {
      return this.serverChunkManager;
   }

   @Override
   public Explosion createExplosion(
      @Nullable Entity entity,
      @Nullable DamageSource damageSource,
      @Nullable ExplosionBehavior _snowman,
      double _snowman,
      double _snowman,
      double _snowman,
      float _snowman,
      boolean _snowman,
      Explosion.DestructionType _snowman
   ) {
      Explosion _snowmanxxxxxxx = new Explosion(this, entity, damageSource, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      _snowmanxxxxxxx.collectBlocksAndDamageEntities();
      _snowmanxxxxxxx.affectWorld(false);
      if (_snowman == Explosion.DestructionType.NONE) {
         _snowmanxxxxxxx.clearAffectedBlocks();
      }

      for (ServerPlayerEntity _snowmanxxxxxxxx : this.players) {
         if (_snowmanxxxxxxxx.squaredDistanceTo(_snowman, _snowman, _snowman) < 4096.0) {
            _snowmanxxxxxxxx.networkHandler.sendPacket(new ExplosionS2CPacket(_snowman, _snowman, _snowman, _snowman, _snowmanxxxxxxx.getAffectedBlocks(), _snowmanxxxxxxx.getAffectedPlayers().get(_snowmanxxxxxxxx)));
         }
      }

      return _snowmanxxxxxxx;
   }

   @Override
   public void addSyncedBlockEvent(BlockPos pos, Block block, int type, int data) {
      this.syncedBlockEventQueue.add(new BlockEvent(pos, block, type, data));
   }

   private void processSyncedBlockEvents() {
      while (!this.syncedBlockEventQueue.isEmpty()) {
         BlockEvent _snowman = (BlockEvent)this.syncedBlockEventQueue.removeFirst();
         if (this.processBlockEvent(_snowman)) {
            this.server
               .getPlayerManager()
               .sendToAround(
                  null,
                  (double)_snowman.getPos().getX(),
                  (double)_snowman.getPos().getY(),
                  (double)_snowman.getPos().getZ(),
                  64.0,
                  this.getRegistryKey(),
                  new BlockEventS2CPacket(_snowman.getPos(), _snowman.getBlock(), _snowman.getType(), _snowman.getData())
               );
         }
      }
   }

   private boolean processBlockEvent(BlockEvent event) {
      BlockState _snowman = this.getBlockState(event.getPos());
      return _snowman.isOf(event.getBlock()) ? _snowman.onSyncedBlockEvent(this, event.getPos(), event.getType(), event.getData()) : false;
   }

   public ServerTickScheduler<Block> getBlockTickScheduler() {
      return this.blockTickScheduler;
   }

   public ServerTickScheduler<Fluid> getFluidTickScheduler() {
      return this.fluidTickScheduler;
   }

   @Nonnull
   @Override
   public MinecraftServer getServer() {
      return this.server;
   }

   public PortalForcer getPortalForcer() {
      return this.portalForcer;
   }

   public StructureManager getStructureManager() {
      return this.server.getStructureManager();
   }

   public <T extends ParticleEffect> int spawnParticles(
      T particle, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed
   ) {
      ParticleS2CPacket _snowman = new ParticleS2CPacket(particle, false, x, y, z, (float)deltaX, (float)deltaY, (float)deltaZ, (float)speed, count);
      int _snowmanx = 0;

      for (int _snowmanxx = 0; _snowmanxx < this.players.size(); _snowmanxx++) {
         ServerPlayerEntity _snowmanxxx = this.players.get(_snowmanxx);
         if (this.sendToPlayerIfNearby(_snowmanxxx, false, x, y, z, _snowman)) {
            _snowmanx++;
         }
      }

      return _snowmanx;
   }

   public <T extends ParticleEffect> boolean spawnParticles(
      ServerPlayerEntity viewer, T particle, boolean force, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed
   ) {
      Packet<?> _snowman = new ParticleS2CPacket(particle, force, x, y, z, (float)deltaX, (float)deltaY, (float)deltaZ, (float)speed, count);
      return this.sendToPlayerIfNearby(viewer, force, x, y, z, _snowman);
   }

   private boolean sendToPlayerIfNearby(ServerPlayerEntity player, boolean force, double x, double y, double z, Packet<?> packet) {
      if (player.getServerWorld() != this) {
         return false;
      } else {
         BlockPos _snowman = player.getBlockPos();
         if (_snowman.isWithinDistance(new Vec3d(x, y, z), force ? 512.0 : 32.0)) {
            player.networkHandler.sendPacket(packet);
            return true;
         } else {
            return false;
         }
      }
   }

   @Nullable
   @Override
   public Entity getEntityById(int id) {
      return (Entity)this.entitiesById.get(id);
   }

   @Nullable
   public Entity getEntity(UUID _snowman) {
      return this.entitiesByUuid.get(_snowman);
   }

   @Nullable
   public BlockPos locateStructure(StructureFeature<?> feature, BlockPos pos, int radius, boolean skipExistingChunks) {
      return !this.server.getSaveProperties().getGeneratorOptions().shouldGenerateStructures()
         ? null
         : this.getChunkManager().getChunkGenerator().locateStructure(this, feature, pos, radius, skipExistingChunks);
   }

   @Nullable
   public BlockPos locateBiome(Biome biome, BlockPos pos, int radius, int _snowman) {
      return this.getChunkManager()
         .getChunkGenerator()
         .getBiomeSource()
         .locateBiome(pos.getX(), pos.getY(), pos.getZ(), radius, _snowman, _snowmanxx -> _snowmanxx == biome, this.random, true);
   }

   @Override
   public RecipeManager getRecipeManager() {
      return this.server.getRecipeManager();
   }

   @Override
   public TagManager getTagManager() {
      return this.server.getTagManager();
   }

   @Override
   public boolean isSavingDisabled() {
      return this.savingDisabled;
   }

   @Override
   public DynamicRegistryManager getRegistryManager() {
      return this.server.getRegistryManager();
   }

   public PersistentStateManager getPersistentStateManager() {
      return this.getChunkManager().getPersistentStateManager();
   }

   @Nullable
   @Override
   public MapState getMapState(String id) {
      return this.getServer().getOverworld().getPersistentStateManager().get(() -> new MapState(id), id);
   }

   @Override
   public void putMapState(MapState mapState) {
      this.getServer().getOverworld().getPersistentStateManager().set(mapState);
   }

   @Override
   public int getNextMapId() {
      return this.getServer().getOverworld().getPersistentStateManager().getOrCreate(IdCountsState::new, "idcounts").getNextMapId();
   }

   public void setSpawnPos(BlockPos pos, float angle) {
      ChunkPos _snowman = new ChunkPos(new BlockPos(this.properties.getSpawnX(), 0, this.properties.getSpawnZ()));
      this.properties.setSpawnPos(pos, angle);
      this.getChunkManager().removeTicket(ChunkTicketType.START, _snowman, 11, Unit.INSTANCE);
      this.getChunkManager().addTicket(ChunkTicketType.START, new ChunkPos(pos), 11, Unit.INSTANCE);
      this.getServer().getPlayerManager().sendToAll(new PlayerSpawnPositionS2CPacket(pos, angle));
   }

   public BlockPos getSpawnPos() {
      BlockPos _snowman = new BlockPos(this.properties.getSpawnX(), this.properties.getSpawnY(), this.properties.getSpawnZ());
      if (!this.getWorldBorder().contains(_snowman)) {
         _snowman = this.getTopPosition(Heightmap.Type.MOTION_BLOCKING, new BlockPos(this.getWorldBorder().getCenterX(), 0.0, this.getWorldBorder().getCenterZ()));
      }

      return _snowman;
   }

   public float getSpawnAngle() {
      return this.properties.getSpawnAngle();
   }

   public LongSet getForcedChunks() {
      ForcedChunkState _snowman = this.getPersistentStateManager().get(ForcedChunkState::new, "chunks");
      return (LongSet)(_snowman != null ? LongSets.unmodifiable(_snowman.getChunks()) : LongSets.EMPTY_SET);
   }

   public boolean setChunkForced(int x, int z, boolean forced) {
      ForcedChunkState _snowman = this.getPersistentStateManager().getOrCreate(ForcedChunkState::new, "chunks");
      ChunkPos _snowmanx = new ChunkPos(x, z);
      long _snowmanxx = _snowmanx.toLong();
      boolean _snowmanxxx;
      if (forced) {
         _snowmanxxx = _snowman.getChunks().add(_snowmanxx);
         if (_snowmanxxx) {
            this.getChunk(x, z);
         }
      } else {
         _snowmanxxx = _snowman.getChunks().remove(_snowmanxx);
      }

      _snowman.setDirty(_snowmanxxx);
      if (_snowmanxxx) {
         this.getChunkManager().setChunkForced(_snowmanx, forced);
      }

      return _snowmanxxx;
   }

   @Override
   public List<ServerPlayerEntity> getPlayers() {
      return this.players;
   }

   @Override
   public void onBlockChanged(BlockPos pos, BlockState oldBlock, BlockState newBlock) {
      Optional<PointOfInterestType> _snowman = PointOfInterestType.from(oldBlock);
      Optional<PointOfInterestType> _snowmanx = PointOfInterestType.from(newBlock);
      if (!Objects.equals(_snowman, _snowmanx)) {
         BlockPos _snowmanxx = pos.toImmutable();
         _snowman.ifPresent(_snowmanxxx -> this.getServer().execute(() -> {
               this.getPointOfInterestStorage().remove(_snowman);
               DebugInfoSender.sendPoiRemoval(this, _snowman);
            }));
         _snowmanx.ifPresent(_snowmanxxx -> this.getServer().execute(() -> {
               this.getPointOfInterestStorage().add(_snowman, _snowmanx);
               DebugInfoSender.sendPoiAddition(this, _snowman);
            }));
      }
   }

   public PointOfInterestStorage getPointOfInterestStorage() {
      return this.getChunkManager().getPointOfInterestStorage();
   }

   public boolean isNearOccupiedPointOfInterest(BlockPos pos) {
      return this.isNearOccupiedPointOfInterest(pos, 1);
   }

   public boolean isNearOccupiedPointOfInterest(ChunkSectionPos sectionPos) {
      return this.isNearOccupiedPointOfInterest(sectionPos.getCenterPos());
   }

   public boolean isNearOccupiedPointOfInterest(BlockPos pos, int maxDistance) {
      return maxDistance > 6 ? false : this.getOccupiedPointOfInterestDistance(ChunkSectionPos.from(pos)) <= maxDistance;
   }

   public int getOccupiedPointOfInterestDistance(ChunkSectionPos pos) {
      return this.getPointOfInterestStorage().getDistanceFromNearestOccupied(pos);
   }

   public RaidManager getRaidManager() {
      return this.raidManager;
   }

   @Nullable
   public Raid getRaidAt(BlockPos pos) {
      return this.raidManager.getRaidAt(pos, 9216);
   }

   public boolean hasRaidAt(BlockPos pos) {
      return this.getRaidAt(pos) != null;
   }

   public void handleInteraction(EntityInteraction interaction, Entity entity, InteractionObserver observer) {
      observer.onInteractionWith(interaction, entity);
   }

   public void dump(Path _snowman) throws IOException {
      ThreadedAnvilChunkStorage _snowmanx = this.getChunkManager().threadedAnvilChunkStorage;

      try (Writer _snowmanxx = Files.newBufferedWriter(_snowman.resolve("stats.txt"))) {
         _snowmanxx.write(String.format("spawning_chunks: %d\n", _snowmanx.getTicketManager().getSpawningChunkCount()));
         SpawnHelper.Info _snowmanxxx = this.getChunkManager().getSpawnInfo();
         if (_snowmanxxx != null) {
            ObjectIterator var6 = _snowmanxxx.getGroupToCount().object2IntEntrySet().iterator();

            while (var6.hasNext()) {
               it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<SpawnGroup> _snowmanxxxx = (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<SpawnGroup>)var6.next();
               _snowmanxx.write(String.format("spawn_count.%s: %d\n", ((SpawnGroup)_snowmanxxxx.getKey()).getName(), _snowmanxxxx.getIntValue()));
            }
         }

         _snowmanxx.write(String.format("entities: %d\n", this.entitiesById.size()));
         _snowmanxx.write(String.format("block_entities: %d\n", this.blockEntities.size()));
         _snowmanxx.write(String.format("block_ticks: %d\n", this.getBlockTickScheduler().getTicks()));
         _snowmanxx.write(String.format("fluid_ticks: %d\n", this.getFluidTickScheduler().getTicks()));
         _snowmanxx.write("distance_manager: " + _snowmanx.getTicketManager().toDumpString() + "\n");
         _snowmanxx.write(String.format("pending_tasks: %d\n", this.getChunkManager().getPendingTasks()));
      }

      CrashReport _snowmanxx = new CrashReport("Level dump", new Exception("dummy"));
      this.addDetailsToCrashReport(_snowmanxx);

      try (Writer _snowmanxxx = Files.newBufferedWriter(_snowman.resolve("example_crash.txt"))) {
         _snowmanxxx.write(_snowmanxx.asString());
      }

      Path _snowmanxxx = _snowman.resolve("chunks.csv");

      try (Writer _snowmanxxxx = Files.newBufferedWriter(_snowmanxxx)) {
         _snowmanx.dump(_snowmanxxxx);
      }

      Path _snowmanxxxx = _snowman.resolve("entities.csv");

      try (Writer _snowmanxxxxx = Files.newBufferedWriter(_snowmanxxxx)) {
         dumpEntities(_snowmanxxxxx, this.entitiesById.values());
      }

      Path _snowmanxxxxx = _snowman.resolve("block_entities.csv");

      try (Writer _snowmanxxxxxx = Files.newBufferedWriter(_snowmanxxxxx)) {
         this.dumpBlockEntities(_snowmanxxxxxx);
      }
   }

   private static void dumpEntities(Writer writer, Iterable<Entity> entities) throws IOException {
      CsvWriter _snowman = CsvWriter.makeHeader()
         .addColumn("x")
         .addColumn("y")
         .addColumn("z")
         .addColumn("uuid")
         .addColumn("type")
         .addColumn("alive")
         .addColumn("display_name")
         .addColumn("custom_name")
         .startBody(writer);

      for (Entity _snowmanx : entities) {
         Text _snowmanxx = _snowmanx.getCustomName();
         Text _snowmanxxx = _snowmanx.getDisplayName();
         _snowman.printRow(
            _snowmanx.getX(),
            _snowmanx.getY(),
            _snowmanx.getZ(),
            _snowmanx.getUuid(),
            Registry.ENTITY_TYPE.getId(_snowmanx.getType()),
            _snowmanx.isAlive(),
            _snowmanxxx.getString(),
            _snowmanxx != null ? _snowmanxx.getString() : null
         );
      }
   }

   private void dumpBlockEntities(Writer writer) throws IOException {
      CsvWriter _snowman = CsvWriter.makeHeader().addColumn("x").addColumn("y").addColumn("z").addColumn("type").startBody(writer);

      for (BlockEntity _snowmanx : this.blockEntities) {
         BlockPos _snowmanxx = _snowmanx.getPos();
         _snowman.printRow(_snowmanxx.getX(), _snowmanxx.getY(), _snowmanxx.getZ(), Registry.BLOCK_ENTITY_TYPE.getId(_snowmanx.getType()));
      }
   }

   @VisibleForTesting
   public void clearUpdatesInArea(BlockBox box) {
      this.syncedBlockEventQueue.removeIf(_snowmanx -> box.contains(_snowmanx.getPos()));
   }

   @Override
   public void updateNeighbors(BlockPos pos, Block block) {
      if (!this.isDebugWorld()) {
         this.updateNeighborsAlways(pos, block);
      }
   }

   @Override
   public float getBrightness(Direction direction, boolean shaded) {
      return 1.0F;
   }

   public Iterable<Entity> iterateEntities() {
      return Iterables.unmodifiableIterable(this.entitiesById.values());
   }

   @Override
   public String toString() {
      return "ServerLevel[" + this.worldProperties.getLevelName() + "]";
   }

   public boolean isFlat() {
      return this.server.getSaveProperties().getGeneratorOptions().isFlatWorld();
   }

   @Override
   public long getSeed() {
      return this.server.getSaveProperties().getGeneratorOptions().getSeed();
   }

   @Nullable
   public EnderDragonFight getEnderDragonFight() {
      return this.enderDragonFight;
   }

   @Override
   public Stream<? extends StructureStart<?>> getStructures(ChunkSectionPos pos, StructureFeature<?> feature) {
      return this.getStructureAccessor().getStructuresWithChildren(pos, feature);
   }

   @Override
   public ServerWorld toServerWorld() {
      return this;
   }

   @VisibleForTesting
   public String method_31268() {
      return String.format(
         "players: %s, entities: %d [%s], block_entities: %d [%s], block_ticks: %d, fluid_ticks: %d, chunk_source: %s",
         this.players.size(),
         this.entitiesById.size(),
         method_31270(this.entitiesById.values(), _snowman -> Registry.ENTITY_TYPE.getId(_snowman.getType())),
         this.tickingBlockEntities.size(),
         method_31270(this.tickingBlockEntities, _snowman -> Registry.BLOCK_ENTITY_TYPE.getId(_snowman.getType())),
         this.getBlockTickScheduler().getTicks(),
         this.getFluidTickScheduler().getTicks(),
         this.getDebugString()
      );
   }

   private static <T> String method_31270(Collection<T> _snowman, Function<T, Identifier> _snowman) {
      try {
         Object2IntOpenHashMap<Identifier> _snowmanxx = new Object2IntOpenHashMap();

         for (T _snowmanxxx : _snowman) {
            Identifier _snowmanxxxx = _snowman.apply(_snowmanxxx);
            _snowmanxx.addTo(_snowmanxxxx, 1);
         }

         return _snowmanxx.object2IntEntrySet()
            .stream()
            .sorted(Comparator.comparing(it.unimi.dsi.fastutil.objects.Object2IntMap.Entry::getIntValue).reversed())
            .limit(5L)
            .map(_snowmanxxx -> _snowmanxxx.getKey() + ":" + _snowmanxxx.getIntValue())
            .collect(Collectors.joining(","));
      } catch (Exception var6) {
         return "";
      }
   }

   public static void createEndSpawnPlatform(ServerWorld world) {
      BlockPos _snowman = END_SPAWN_POS;
      int _snowmanx = _snowman.getX();
      int _snowmanxx = _snowman.getY() - 2;
      int _snowmanxxx = _snowman.getZ();
      BlockPos.iterate(_snowmanx - 2, _snowmanxx + 1, _snowmanxxx - 2, _snowmanx + 2, _snowmanxx + 3, _snowmanxxx + 2).forEach(_snowmanxxxx -> world.setBlockState(_snowmanxxxx, Blocks.AIR.getDefaultState()));
      BlockPos.iterate(_snowmanx - 2, _snowmanxx, _snowmanxxx - 2, _snowmanx + 2, _snowmanxx, _snowmanxxx + 2).forEach(_snowmanxxxx -> world.setBlockState(_snowmanxxxx, Blocks.OBSIDIAN.getDefaultState()));
   }
}
