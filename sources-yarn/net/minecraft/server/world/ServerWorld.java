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
import it.unimi.dsi.fastutil.objects.Object2IntMap;
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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
      this, arg -> arg == null || arg.getDefaultState().isAir(), Registry.BLOCK::getId, this::tickBlock
   );
   private final ServerTickScheduler<Fluid> fluidTickScheduler = new ServerTickScheduler<>(
      this, arg -> arg == null || arg == Fluids.EMPTY, Registry.FLUID::getId, this::tickFluid
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
      RegistryKey<World> arg3,
      DimensionType arg4,
      WorldGenerationProgressListener arg5,
      ChunkGenerator arg6,
      boolean debugWorld,
      long l,
      List<Spawner> list,
      boolean bl2
   ) {
      super(properties, arg3, arg4, server::getProfiler, false, debugWorld, l);
      this.shouldTickTime = bl2;
      this.server = server;
      this.spawners = list;
      this.worldProperties = properties;
      this.serverChunkManager = new ServerChunkManager(
         this,
         session,
         server.getDataFixer(),
         server.getStructureManager(),
         workerExecutor,
         arg6,
         server.getPlayerManager().getViewDistance(),
         server.syncChunkWrites(),
         arg5,
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
      Profiler lv = this.getProfiler();
      this.inBlockTick = true;
      lv.push("world border");
      this.getWorldBorder().tick();
      lv.swap("weather");
      boolean bl = this.isRaining();
      if (this.getDimension().hasSkyLight()) {
         if (this.getGameRules().getBoolean(GameRules.DO_WEATHER_CYCLE)) {
            int i = this.worldProperties.getClearWeatherTime();
            int j = this.worldProperties.getThunderTime();
            int k = this.worldProperties.getRainTime();
            boolean bl2 = this.properties.isThundering();
            boolean bl3 = this.properties.isRaining();
            if (i > 0) {
               i--;
               j = bl2 ? 0 : 1;
               k = bl3 ? 0 : 1;
               bl2 = false;
               bl3 = false;
            } else {
               if (j > 0) {
                  if (--j == 0) {
                     bl2 = !bl2;
                  }
               } else if (bl2) {
                  j = this.random.nextInt(12000) + 3600;
               } else {
                  j = this.random.nextInt(168000) + 12000;
               }

               if (k > 0) {
                  if (--k == 0) {
                     bl3 = !bl3;
                  }
               } else if (bl3) {
                  k = this.random.nextInt(12000) + 12000;
               } else {
                  k = this.random.nextInt(168000) + 12000;
               }
            }

            this.worldProperties.setThunderTime(j);
            this.worldProperties.setRainTime(k);
            this.worldProperties.setClearWeatherTime(i);
            this.worldProperties.setThundering(bl2);
            this.worldProperties.setRaining(bl3);
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

      if (bl != this.isRaining()) {
         if (bl) {
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
            long l = this.properties.getTimeOfDay() + 24000L;
            this.setTimeOfDay(l - l % 24000L);
         }

         this.wakeSleepingPlayers();
         if (this.getGameRules().getBoolean(GameRules.DO_WEATHER_CYCLE)) {
            this.resetWeather();
         }
      }

      this.calculateAmbientDarkness();
      this.tickTime();
      lv.swap("chunkSource");
      this.getChunkManager().tick(shouldKeepTicking);
      lv.swap("tickPending");
      if (!this.isDebugWorld()) {
         this.blockTickScheduler.tick();
         this.fluidTickScheduler.tick();
      }

      lv.swap("raid");
      this.raidManager.tick();
      lv.swap("blockEvents");
      this.processSyncedBlockEvents();
      this.inBlockTick = false;
      lv.swap("entities");
      boolean bl4 = !this.players.isEmpty() || !this.getForcedChunks().isEmpty();
      if (bl4) {
         this.resetIdleTimeout();
      }

      if (bl4 || this.idleTimeout++ < 300) {
         if (this.enderDragonFight != null) {
            this.enderDragonFight.tick();
         }

         this.inEntityTick = true;
         ObjectIterator<Entry<Entity>> objectIterator = this.entitiesById.int2ObjectEntrySet().iterator();

         while (objectIterator.hasNext()) {
            Entry<Entity> entry = (Entry<Entity>)objectIterator.next();
            Entity lv2 = (Entity)entry.getValue();
            Entity lv3 = lv2.getVehicle();
            if (!this.server.shouldSpawnAnimals() && (lv2 instanceof AnimalEntity || lv2 instanceof WaterCreatureEntity)) {
               lv2.remove();
            }

            if (!this.server.shouldSpawnNpcs() && lv2 instanceof Npc) {
               lv2.remove();
            }

            lv.push("checkDespawn");
            if (!lv2.removed) {
               lv2.checkDespawn();
            }

            lv.pop();
            if (lv3 != null) {
               if (!lv3.removed && lv3.hasPassenger(lv2)) {
                  continue;
               }

               lv2.stopRiding();
            }

            lv.push("tick");
            if (!lv2.removed && !(lv2 instanceof EnderDragonPart)) {
               this.tickEntity(this::tickEntity, lv2);
            }

            lv.pop();
            lv.push("remove");
            if (lv2.removed) {
               this.removeEntityFromChunk(lv2);
               objectIterator.remove();
               this.unloadEntity(lv2);
            }

            lv.pop();
         }

         this.inEntityTick = false;

         Entity lv4;
         while ((lv4 = this.entitiesToLoad.poll()) != null) {
            this.loadEntityUnchecked(lv4);
         }

         this.tickBlockEntities();
      }

      lv.pop();
   }

   protected void tickTime() {
      if (this.shouldTickTime) {
         long l = this.properties.getTime() + 1L;
         this.worldProperties.setTime(l);
         this.worldProperties.getScheduledEvents().processEvents(this.server, l);
         if (this.properties.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
            this.setTimeOfDay(this.properties.getTimeOfDay() + 1L);
         }
      }
   }

   public void setTimeOfDay(long timeOfDay) {
      this.worldProperties.setTimeOfDay(timeOfDay);
   }

   public void tickSpawners(boolean spawnMonsters, boolean spawnAnimals) {
      for (Spawner lv : this.spawners) {
         lv.spawn(this, spawnMonsters, spawnAnimals);
      }
   }

   private void wakeSleepingPlayers() {
      this.players.stream().filter(LivingEntity::isSleeping).collect(Collectors.toList()).forEach(player -> player.wakeUp(false, false));
   }

   public void tickChunk(WorldChunk chunk, int randomTickSpeed) {
      ChunkPos lv = chunk.getPos();
      boolean bl = this.isRaining();
      int j = lv.getStartX();
      int k = lv.getStartZ();
      Profiler lv2 = this.getProfiler();
      lv2.push("thunder");
      if (bl && this.isThundering() && this.random.nextInt(100000) == 0) {
         BlockPos lv3 = this.getSurface(this.getRandomPosInChunk(j, 0, k, 15));
         if (this.hasRain(lv3)) {
            LocalDifficulty lv4 = this.getLocalDifficulty(lv3);
            boolean bl2 = this.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING) && this.random.nextDouble() < (double)lv4.getLocalDifficulty() * 0.01;
            if (bl2) {
               SkeletonHorseEntity lv5 = EntityType.SKELETON_HORSE.create(this);
               lv5.setTrapped(true);
               lv5.setBreedingAge(0);
               lv5.updatePosition((double)lv3.getX(), (double)lv3.getY(), (double)lv3.getZ());
               this.spawnEntity(lv5);
            }

            LightningEntity lv6 = EntityType.LIGHTNING_BOLT.create(this);
            lv6.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(lv3));
            lv6.setCosmetic(bl2);
            this.spawnEntity(lv6);
         }
      }

      lv2.swap("iceandsnow");
      if (this.random.nextInt(16) == 0) {
         BlockPos lv7 = this.getTopPosition(Heightmap.Type.MOTION_BLOCKING, this.getRandomPosInChunk(j, 0, k, 15));
         BlockPos lv8 = lv7.down();
         Biome lv9 = this.getBiome(lv7);
         if (lv9.canSetIce(this, lv8)) {
            this.setBlockState(lv8, Blocks.ICE.getDefaultState());
         }

         if (bl && lv9.canSetSnow(this, lv7)) {
            this.setBlockState(lv7, Blocks.SNOW.getDefaultState());
         }

         if (bl && this.getBiome(lv8).getPrecipitation() == Biome.Precipitation.RAIN) {
            this.getBlockState(lv8).getBlock().rainTick(this, lv8);
         }
      }

      lv2.swap("tickBlocks");
      if (randomTickSpeed > 0) {
         for (ChunkSection lv10 : chunk.getSectionArray()) {
            if (lv10 != WorldChunk.EMPTY_SECTION && lv10.hasRandomTicks()) {
               int l = lv10.getYOffset();

               for (int m = 0; m < randomTickSpeed; m++) {
                  BlockPos lv11 = this.getRandomPosInChunk(j, l, k, 15);
                  lv2.push("randomTick");
                  BlockState lv12 = lv10.getBlockState(lv11.getX() - j, lv11.getY() - l, lv11.getZ() - k);
                  if (lv12.hasRandomTicks()) {
                     lv12.randomTick(this, lv11, this.random);
                  }

                  FluidState lv13 = lv12.getFluidState();
                  if (lv13.hasRandomTicks()) {
                     lv13.onRandomTick(this, lv11, this.random);
                  }

                  lv2.pop();
               }
            }
         }
      }

      lv2.pop();
   }

   protected BlockPos getSurface(BlockPos pos) {
      BlockPos lv = this.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos);
      Box lv2 = new Box(lv, new BlockPos(lv.getX(), this.getHeight(), lv.getZ())).expand(3.0);
      List<LivingEntity> list = this.getEntitiesByClass(
         LivingEntity.class, lv2, entity -> entity != null && entity.isAlive() && this.isSkyVisible(entity.getBlockPos())
      );
      if (!list.isEmpty()) {
         return list.get(this.random.nextInt(list.size())).getBlockPos();
      } else {
         if (lv.getY() == -1) {
            lv = lv.up(2);
         }

         return lv;
      }
   }

   public boolean isInBlockTick() {
      return this.inBlockTick;
   }

   public void updateSleepingPlayers() {
      this.allPlayersSleeping = false;
      if (!this.players.isEmpty()) {
         int i = 0;
         int j = 0;

         for (ServerPlayerEntity lv : this.players) {
            if (lv.isSpectator()) {
               i++;
            } else if (lv.isSleeping()) {
               j++;
            }
         }

         this.allPlayersSleeping = j > 0 && j >= this.players.size() - i;
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
      FluidState lv = this.getFluidState(tick.pos);
      if (lv.getFluid() == tick.getObject()) {
         lv.onScheduledTick(this, tick.pos);
      }
   }

   private void tickBlock(ScheduledTick<Block> tick) {
      BlockState lv = this.getBlockState(tick.pos);
      if (lv.isOf(tick.getObject())) {
         lv.scheduledTick(this, tick.pos, this.random);
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
            Profiler lv = this.getProfiler();
            lv.push(() -> Registry.ENTITY_TYPE.getId(entity.getType()).toString());
            lv.visit("tickNonPassenger");
            entity.tick();
            lv.pop();
         }

         this.checkEntityChunkPos(entity);
         if (entity.updateNeeded) {
            for (Entity lv2 : entity.getPassengerList()) {
               this.tickPassenger(entity, lv2);
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
            Profiler lv = this.getProfiler();
            lv.push(() -> Registry.ENTITY_TYPE.getId(passenger.getType()).toString());
            lv.visit("tickPassenger");
            passenger.tickRiding();
            lv.pop();
         }

         this.checkEntityChunkPos(passenger);
         if (passenger.updateNeeded) {
            for (Entity lv2 : passenger.getPassengerList()) {
               this.tickPassenger(passenger, lv2);
            }
         }
      }
   }

   public void checkEntityChunkPos(Entity entity) {
      if (entity.isChunkPosUpdateRequested()) {
         this.getProfiler().push("chunkCheck");
         int i = MathHelper.floor(entity.getX() / 16.0);
         int j = MathHelper.floor(entity.getY() / 16.0);
         int k = MathHelper.floor(entity.getZ() / 16.0);
         if (!entity.updateNeeded || entity.chunkX != i || entity.chunkY != j || entity.chunkZ != k) {
            if (entity.updateNeeded && this.isChunkLoaded(entity.chunkX, entity.chunkZ)) {
               this.getChunk(entity.chunkX, entity.chunkZ).remove(entity, entity.chunkY);
            }

            if (!entity.teleportRequested() && !this.isChunkLoaded(i, k)) {
               if (entity.updateNeeded) {
                  LOGGER.warn("Entity {} left loaded chunk area", entity);
               }

               entity.updateNeeded = false;
            } else {
               this.getChunk(i, k).addEntity(entity);
            }
         }

         this.getProfiler().pop();
      }
   }

   @Override
   public boolean canPlayerModifyAt(PlayerEntity player, BlockPos pos) {
      return !this.server.isSpawnProtected(this, pos, player) && this.getWorldBorder().contains(pos);
   }

   public void save(@Nullable ProgressListener progressListener, boolean flush, boolean bl2) {
      ServerChunkManager lv = this.getChunkManager();
      if (!bl2) {
         if (progressListener != null) {
            progressListener.method_15412(new TranslatableText("menu.savingLevel"));
         }

         this.saveLevel();
         if (progressListener != null) {
            progressListener.method_15414(new TranslatableText("menu.savingChunks"));
         }

         lv.save(flush);
      }
   }

   private void saveLevel() {
      if (this.enderDragonFight != null) {
         this.server.getSaveProperties().setDragonFight(this.enderDragonFight.toTag());
      }

      this.getChunkManager().getPersistentStateManager().save();
   }

   public List<Entity> getEntitiesByType(@Nullable EntityType<?> type, Predicate<? super Entity> predicate) {
      List<Entity> list = Lists.newArrayList();
      ServerChunkManager lv = this.getChunkManager();
      ObjectIterator var5 = this.entitiesById.values().iterator();

      while (var5.hasNext()) {
         Entity lv2 = (Entity)var5.next();
         if ((type == null || lv2.getType() == type)
            && lv.isChunkLoaded(MathHelper.floor(lv2.getX()) >> 4, MathHelper.floor(lv2.getZ()) >> 4)
            && predicate.test(lv2)) {
            list.add(lv2);
         }
      }

      return list;
   }

   public List<EnderDragonEntity> getAliveEnderDragons() {
      List<EnderDragonEntity> list = Lists.newArrayList();
      ObjectIterator var2 = this.entitiesById.values().iterator();

      while (var2.hasNext()) {
         Entity lv = (Entity)var2.next();
         if (lv instanceof EnderDragonEntity && lv.isAlive()) {
            list.add((EnderDragonEntity)lv);
         }
      }

      return list;
   }

   public List<ServerPlayerEntity> getPlayers(Predicate<? super ServerPlayerEntity> predicate) {
      List<ServerPlayerEntity> list = Lists.newArrayList();

      for (ServerPlayerEntity lv : this.players) {
         if (predicate.test(lv)) {
            list.add(lv);
         }
      }

      return list;
   }

   @Nullable
   public ServerPlayerEntity getRandomAlivePlayer() {
      List<ServerPlayerEntity> list = this.getPlayers(LivingEntity::isAlive);
      return list.isEmpty() ? null : list.get(this.random.nextInt(list.size()));
   }

   @Override
   public boolean spawnEntity(Entity entity) {
      return this.addEntity(entity);
   }

   public boolean tryLoadEntity(Entity entity) {
      return this.addEntity(entity);
   }

   public void onDimensionChanged(Entity entity) {
      boolean bl = entity.teleporting;
      entity.teleporting = true;
      this.tryLoadEntity(entity);
      entity.teleporting = bl;
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
      Entity lv = this.entitiesByUuid.get(player.getUuid());
      if (lv != null) {
         LOGGER.warn("Force-added player with duplicate UUID {}", player.getUuid().toString());
         lv.detach();
         this.removePlayer((ServerPlayerEntity)lv);
      }

      this.players.add(player);
      this.updateSleepingPlayers();
      Chunk lv2 = this.getChunk(MathHelper.floor(player.getX() / 16.0), MathHelper.floor(player.getZ() / 16.0), ChunkStatus.FULL, true);
      if (lv2 instanceof WorldChunk) {
         lv2.addEntity(player);
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
         Chunk lv = this.getChunk(MathHelper.floor(entity.getX() / 16.0), MathHelper.floor(entity.getZ() / 16.0), ChunkStatus.FULL, entity.teleporting);
         if (!(lv instanceof WorldChunk)) {
            return false;
         } else {
            lv.addEntity(entity);
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

   private boolean checkUuid(Entity arg) {
      UUID uUID = arg.getUuid();
      Entity lv = this.checkIfUuidExists(uUID);
      if (lv == null) {
         return false;
      } else {
         LOGGER.warn(
            "Trying to add entity with duplicated UUID {}. Existing {}#{}, new: {}#{}",
            uUID,
            EntityType.getId(lv.getType()),
            lv.getEntityId(),
            EntityType.getId(arg.getType()),
            arg.getEntityId()
         );
         return true;
      }
   }

   @Nullable
   private Entity checkIfUuidExists(UUID uUID) {
      Entity lv = this.entitiesByUuid.get(uUID);
      if (lv != null) {
         return lv;
      } else {
         if (this.inEntityTick) {
            for (Entity lv2 : this.entitiesToLoad) {
               if (lv2.getUuid().equals(uUID)) {
                  return lv2;
               }
            }
         }

         return null;
      }
   }

   public boolean shouldCreateNewEntityWithPassenger(Entity arg) {
      if (arg.streamPassengersRecursively().anyMatch(this::checkUuid)) {
         return false;
      } else {
         this.spawnEntityAndPassengers(arg);
         return true;
      }
   }

   public void unloadEntities(WorldChunk chunk) {
      this.unloadedBlockEntities.addAll(chunk.getBlockEntities().values());
      TypeFilterableList<Entity>[] sections = chunk.getEntitySectionArray();

      for (TypeFilterableList<Entity> section : sections) {
         for (Entity lv2 : section) {
            if (!(lv2 instanceof ServerPlayerEntity)) {
               if (this.inEntityTick) {
                  throw (IllegalStateException)Util.throwOrPause(new IllegalStateException("Removing entity while ticking!"));
               }

               this.entitiesById.remove(lv2.getEntityId());
               this.unloadEntity(lv2);
            }
         }
      }
   }

   public void unloadEntity(Entity entity) {
      if (entity instanceof EnderDragonEntity) {
         for (EnderDragonPart lv : ((EnderDragonEntity)entity).getBodyParts()) {
            lv.remove();
         }
      }

      this.entitiesByUuid.remove(entity.getUuid());
      this.getChunkManager().unloadEntity(entity);
      if (entity instanceof ServerPlayerEntity) {
         ServerPlayerEntity lv2 = (ServerPlayerEntity)entity;
         this.players.remove(lv2);
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
            for (EnderDragonPart lv : ((EnderDragonEntity)entity).getBodyParts()) {
               this.entitiesById.put(lv.getEntityId(), lv);
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
      Chunk lv = this.getChunk(entity.chunkX, entity.chunkZ, ChunkStatus.FULL, false);
      if (lv instanceof WorldChunk) {
         ((WorldChunk)lv).remove(entity);
      }
   }

   public void removePlayer(ServerPlayerEntity player) {
      player.remove();
      this.removeEntity(player);
      this.updateSleepingPlayers();
   }

   @Override
   public void setBlockBreakingInfo(int entityId, BlockPos pos, int progress) {
      for (ServerPlayerEntity lv : this.server.getPlayerManager().getPlayerList()) {
         if (lv != null && lv.world == this && lv.getEntityId() != entityId) {
            double d = (double)pos.getX() - lv.getX();
            double e = (double)pos.getY() - lv.getY();
            double f = (double)pos.getZ() - lv.getZ();
            if (d * d + e * e + f * f < 1024.0) {
               lv.networkHandler.sendPacket(new BlockBreakingProgressS2CPacket(entityId, pos, progress));
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
      VoxelShape lv = oldState.getCollisionShape(this, pos);
      VoxelShape lv2 = newState.getCollisionShape(this, pos);
      if (VoxelShapes.matchesAnywhere(lv, lv2, BooleanBiFunction.NOT_SAME)) {
         for (EntityNavigation lv3 : this.entityNavigations) {
            if (!lv3.shouldRecalculatePath()) {
               lv3.onBlockChanged(pos);
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
      @Nullable ExplosionBehavior arg3,
      double d,
      double e,
      double f,
      float g,
      boolean bl,
      Explosion.DestructionType arg4
   ) {
      Explosion lv = new Explosion(this, entity, damageSource, arg3, d, e, f, g, bl, arg4);
      lv.collectBlocksAndDamageEntities();
      lv.affectWorld(false);
      if (arg4 == Explosion.DestructionType.NONE) {
         lv.clearAffectedBlocks();
      }

      for (ServerPlayerEntity lv2 : this.players) {
         if (lv2.squaredDistanceTo(d, e, f) < 4096.0) {
            lv2.networkHandler.sendPacket(new ExplosionS2CPacket(d, e, f, g, lv.getAffectedBlocks(), lv.getAffectedPlayers().get(lv2)));
         }
      }

      return lv;
   }

   @Override
   public void addSyncedBlockEvent(BlockPos pos, Block block, int type, int data) {
      this.syncedBlockEventQueue.add(new BlockEvent(pos, block, type, data));
   }

   private void processSyncedBlockEvents() {
      while (!this.syncedBlockEventQueue.isEmpty()) {
         BlockEvent lv = (BlockEvent)this.syncedBlockEventQueue.removeFirst();
         if (this.processBlockEvent(lv)) {
            this.server
               .getPlayerManager()
               .sendToAround(
                  null,
                  (double)lv.getPos().getX(),
                  (double)lv.getPos().getY(),
                  (double)lv.getPos().getZ(),
                  64.0,
                  this.getRegistryKey(),
                  new BlockEventS2CPacket(lv.getPos(), lv.getBlock(), lv.getType(), lv.getData())
               );
         }
      }
   }

   private boolean processBlockEvent(BlockEvent event) {
      BlockState lv = this.getBlockState(event.getPos());
      return lv.isOf(event.getBlock()) ? lv.onSyncedBlockEvent(this, event.getPos(), event.getType(), event.getData()) : false;
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
      ParticleS2CPacket lv = new ParticleS2CPacket(particle, false, x, y, z, (float)deltaX, (float)deltaY, (float)deltaZ, (float)speed, count);
      int l = 0;

      for (int m = 0; m < this.players.size(); m++) {
         ServerPlayerEntity lv2 = this.players.get(m);
         if (this.sendToPlayerIfNearby(lv2, false, x, y, z, lv)) {
            l++;
         }
      }

      return l;
   }

   public <T extends ParticleEffect> boolean spawnParticles(
      ServerPlayerEntity viewer, T particle, boolean force, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed
   ) {
      Packet<?> lv = new ParticleS2CPacket(particle, force, x, y, z, (float)deltaX, (float)deltaY, (float)deltaZ, (float)speed, count);
      return this.sendToPlayerIfNearby(viewer, force, x, y, z, lv);
   }

   private boolean sendToPlayerIfNearby(ServerPlayerEntity player, boolean force, double x, double y, double z, Packet<?> packet) {
      if (player.getServerWorld() != this) {
         return false;
      } else {
         BlockPos lv = player.getBlockPos();
         if (lv.isWithinDistance(new Vec3d(x, y, z), force ? 512.0 : 32.0)) {
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
   public Entity getEntity(UUID uUID) {
      return this.entitiesByUuid.get(uUID);
   }

   @Nullable
   public BlockPos locateStructure(StructureFeature<?> feature, BlockPos pos, int radius, boolean skipExistingChunks) {
      return !this.server.getSaveProperties().getGeneratorOptions().shouldGenerateStructures()
         ? null
         : this.getChunkManager().getChunkGenerator().locateStructure(this, feature, pos, radius, skipExistingChunks);
   }

   @Nullable
   public BlockPos locateBiome(Biome biome, BlockPos pos, int radius, int j) {
      return this.getChunkManager()
         .getChunkGenerator()
         .getBiomeSource()
         .locateBiome(pos.getX(), pos.getY(), pos.getZ(), radius, j, arg2 -> arg2 == biome, this.random, true);
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
      ChunkPos lv = new ChunkPos(new BlockPos(this.properties.getSpawnX(), 0, this.properties.getSpawnZ()));
      this.properties.setSpawnPos(pos, angle);
      this.getChunkManager().removeTicket(ChunkTicketType.START, lv, 11, Unit.INSTANCE);
      this.getChunkManager().addTicket(ChunkTicketType.START, new ChunkPos(pos), 11, Unit.INSTANCE);
      this.getServer().getPlayerManager().sendToAll(new PlayerSpawnPositionS2CPacket(pos, angle));
   }

   public BlockPos getSpawnPos() {
      BlockPos lv = new BlockPos(this.properties.getSpawnX(), this.properties.getSpawnY(), this.properties.getSpawnZ());
      if (!this.getWorldBorder().contains(lv)) {
         lv = this.getTopPosition(Heightmap.Type.MOTION_BLOCKING, new BlockPos(this.getWorldBorder().getCenterX(), 0.0, this.getWorldBorder().getCenterZ()));
      }

      return lv;
   }

   public float getSpawnAngle() {
      return this.properties.getSpawnAngle();
   }

   public LongSet getForcedChunks() {
      ForcedChunkState lv = this.getPersistentStateManager().get(ForcedChunkState::new, "chunks");
      return (LongSet)(lv != null ? LongSets.unmodifiable(lv.getChunks()) : LongSets.EMPTY_SET);
   }

   public boolean setChunkForced(int x, int z, boolean forced) {
      ForcedChunkState lv = this.getPersistentStateManager().getOrCreate(ForcedChunkState::new, "chunks");
      ChunkPos lv2 = new ChunkPos(x, z);
      long l = lv2.toLong();
      boolean bl2;
      if (forced) {
         bl2 = lv.getChunks().add(l);
         if (bl2) {
            this.getChunk(x, z);
         }
      } else {
         bl2 = lv.getChunks().remove(l);
      }

      lv.setDirty(bl2);
      if (bl2) {
         this.getChunkManager().setChunkForced(lv2, forced);
      }

      return bl2;
   }

   @Override
   public List<ServerPlayerEntity> getPlayers() {
      return this.players;
   }

   @Override
   public void onBlockChanged(BlockPos pos, BlockState oldBlock, BlockState newBlock) {
      Optional<PointOfInterestType> optional = PointOfInterestType.from(oldBlock);
      Optional<PointOfInterestType> optional2 = PointOfInterestType.from(newBlock);
      if (!Objects.equals(optional, optional2)) {
         BlockPos lv = pos.toImmutable();
         optional.ifPresent(arg2 -> this.getServer().execute(() -> {
               this.getPointOfInterestStorage().remove(lv);
               DebugInfoSender.sendPoiRemoval(this, lv);
            }));
         optional2.ifPresent(arg2 -> this.getServer().execute(() -> {
               this.getPointOfInterestStorage().add(lv, arg2);
               DebugInfoSender.sendPoiAddition(this, lv);
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

   public void dump(Path path) throws IOException {
      ThreadedAnvilChunkStorage lv = this.getChunkManager().threadedAnvilChunkStorage;

      try (Writer writer = Files.newBufferedWriter(path.resolve("stats.txt"))) {
         writer.write(String.format("spawning_chunks: %d\n", lv.getTicketManager().getSpawningChunkCount()));
         SpawnHelper.Info lv2 = this.getChunkManager().getSpawnInfo();
         if (lv2 != null) {
            ObjectIterator path4 = lv2.getGroupToCount().object2IntEntrySet().iterator();

            while (path4.hasNext()) {
               it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<SpawnGroup> entry = (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<SpawnGroup>)path4.next();
               writer.write(String.format("spawn_count.%s: %d\n", ((SpawnGroup)entry.getKey()).getName(), entry.getIntValue()));
            }
         }

         writer.write(String.format("entities: %d\n", this.entitiesById.size()));
         writer.write(String.format("block_entities: %d\n", this.blockEntities.size()));
         writer.write(String.format("block_ticks: %d\n", this.getBlockTickScheduler().getTicks()));
         writer.write(String.format("fluid_ticks: %d\n", this.getFluidTickScheduler().getTicks()));
         writer.write("distance_manager: " + lv.getTicketManager().toDumpString() + "\n");
         writer.write(String.format("pending_tasks: %d\n", this.getChunkManager().getPendingTasks()));
      }

      CrashReport lv3 = new CrashReport("Level dump", new Exception("dummy"));
      this.addDetailsToCrashReport(lv3);

      try (Writer writer2 = Files.newBufferedWriter(path.resolve("example_crash.txt"))) {
         writer2.write(lv3.asString());
      }

      Path path2 = path.resolve("chunks.csv");

      try (Writer writer3 = Files.newBufferedWriter(path2)) {
         lv.dump(writer3);
      }

      Path path3 = path.resolve("entities.csv");

      try (Writer writer4 = Files.newBufferedWriter(path3)) {
         dumpEntities(writer4, this.entitiesById.values());
      }

      Path path4 = path.resolve("block_entities.csv");

      try (Writer writer5 = Files.newBufferedWriter(path4)) {
         this.dumpBlockEntities(writer5);
      }
   }

   private static void dumpEntities(Writer writer, Iterable<Entity> entities) throws IOException {
      CsvWriter lv = CsvWriter.makeHeader()
         .addColumn("x")
         .addColumn("y")
         .addColumn("z")
         .addColumn("uuid")
         .addColumn("type")
         .addColumn("alive")
         .addColumn("display_name")
         .addColumn("custom_name")
         .startBody(writer);

      for (Entity lv2 : entities) {
         Text lv3 = lv2.getCustomName();
         Text lv4 = lv2.getDisplayName();
         lv.printRow(
            lv2.getX(),
            lv2.getY(),
            lv2.getZ(),
            lv2.getUuid(),
            Registry.ENTITY_TYPE.getId(lv2.getType()),
            lv2.isAlive(),
            lv4.getString(),
            lv3 != null ? lv3.getString() : null
         );
      }
   }

   private void dumpBlockEntities(Writer writer) throws IOException {
      CsvWriter lv = CsvWriter.makeHeader().addColumn("x").addColumn("y").addColumn("z").addColumn("type").startBody(writer);

      for (BlockEntity lv2 : this.blockEntities) {
         BlockPos lv3 = lv2.getPos();
         lv.printRow(lv3.getX(), lv3.getY(), lv3.getZ(), Registry.BLOCK_ENTITY_TYPE.getId(lv2.getType()));
      }
   }

   @VisibleForTesting
   public void clearUpdatesInArea(BlockBox box) {
      this.syncedBlockEventQueue.removeIf(arg2 -> box.contains(arg2.getPos()));
   }

   @Override
   public void updateNeighbors(BlockPos pos, Block block) {
      if (!this.isDebugWorld()) {
         this.updateNeighborsAlways(pos, block);
      }
   }

   @Environment(EnvType.CLIENT)
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
         method_31270(this.entitiesById.values(), arg -> Registry.ENTITY_TYPE.getId(arg.getType())),
         this.tickingBlockEntities.size(),
         method_31270(this.tickingBlockEntities, arg -> Registry.BLOCK_ENTITY_TYPE.getId(arg.getType())),
         this.getBlockTickScheduler().getTicks(),
         this.getFluidTickScheduler().getTicks(),
         this.getDebugString()
      );
   }

   private static <T> String method_31270(Collection<T> collection, Function<T, Identifier> function) {
      try {
         Object2IntOpenHashMap<Identifier> object2IntOpenHashMap = new Object2IntOpenHashMap();

         for (T object : collection) {
            Identifier lv = function.apply(object);
            object2IntOpenHashMap.addTo(lv, 1);
         }

         return object2IntOpenHashMap.object2IntEntrySet()
            .stream()
            .sorted(Comparator.comparingInt((Object2IntMap.Entry<Identifier> entry) -> entry.getIntValue()).reversed())
            .limit(5L)
            .map(entry -> entry.getKey() + ":" + entry.getIntValue())
            .collect(Collectors.joining(","));
      } catch (Exception var6) {
         return "";
      }
   }

   public static void createEndSpawnPlatform(ServerWorld world) {
      BlockPos lv = END_SPAWN_POS;
      int i = lv.getX();
      int j = lv.getY() - 2;
      int k = lv.getZ();
      BlockPos.iterate(i - 2, j + 1, k - 2, i + 2, j + 3, k + 2).forEach(arg2 -> world.setBlockState(arg2, Blocks.AIR.getDefaultState()));
      BlockPos.iterate(i - 2, j, k - 2, i + 2, j, k + 2).forEach(arg2 -> world.setBlockState(arg2, Blocks.OBSIDIAN.getDefaultState()));
   }
}
