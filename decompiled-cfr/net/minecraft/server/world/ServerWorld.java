/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Queues
 *  com.google.common.collect.Sets
 *  it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.longs.LongSets
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  javax.annotation.Nonnull
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server.world;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
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
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.BlockEvent;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerTickScheduler;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
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
import net.minecraft.world.TickScheduler;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkManager;
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

public class ServerWorld
extends World
implements StructureWorldAccess {
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
    private final ServerTickScheduler<Block> blockTickScheduler = new ServerTickScheduler<Block>(this, block -> block == null || block.getDefaultState().isAir(), Registry.BLOCK::getId, this::tickBlock);
    private final ServerTickScheduler<Fluid> fluidTickScheduler = new ServerTickScheduler<Fluid>(this, fluid -> fluid == null || fluid == Fluids.EMPTY, Registry.FLUID::getId, this::tickFluid);
    private final Set<EntityNavigation> entityNavigations = Sets.newHashSet();
    protected final RaidManager raidManager;
    private final ObjectLinkedOpenHashSet<BlockEvent> syncedBlockEventQueue = new ObjectLinkedOpenHashSet();
    private boolean inBlockTick;
    private final List<Spawner> spawners;
    @Nullable
    private final EnderDragonFight enderDragonFight;
    private final StructureAccessor structureAccessor;
    private final boolean shouldTickTime;

    public ServerWorld(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey<World> registryKey, DimensionType dimensionType, WorldGenerationProgressListener worldGenerationProgressListener, ChunkGenerator chunkGenerator, boolean debugWorld, long l, List<Spawner> list, boolean bl) {
        super(properties, registryKey, dimensionType, server::getProfiler, false, debugWorld, l);
        this.shouldTickTime = bl;
        this.server = server;
        this.spawners = list;
        this.worldProperties = properties;
        this.serverChunkManager = new ServerChunkManager(this, session, server.getDataFixer(), server.getStructureManager(), workerExecutor, chunkGenerator, server.getPlayerManager().getViewDistance(), server.syncChunkWrites(), worldGenerationProgressListener, () -> server.getOverworld().getPersistentStateManager());
        this.portalForcer = new PortalForcer(this);
        this.calculateAmbientDarkness();
        this.initWeatherGradients();
        this.getWorldBorder().setMaxWorldBorderRadius(server.getMaxWorldBorderRadius());
        this.raidManager = this.getPersistentStateManager().getOrCreate(() -> new RaidManager(this), RaidManager.nameFor(this.getDimension()));
        if (!server.isSinglePlayer()) {
            properties.setGameMode(server.getDefaultGameMode());
        }
        this.structureAccessor = new StructureAccessor(this, server.getSaveProperties().getGeneratorOptions());
        this.enderDragonFight = this.getDimension().hasEnderDragonFight() ? new EnderDragonFight(this, server.getSaveProperties().getGeneratorOptions().getSeed(), server.getSaveProperties().getDragonFight()) : null;
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
        Profiler profiler = this.getProfiler();
        this.inBlockTick = true;
        profiler.push("world border");
        this.getWorldBorder().tick();
        profiler.swap("weather");
        boolean _snowman2 = this.isRaining();
        if (this.getDimension().hasSkyLight()) {
            if (this.getGameRules().getBoolean(GameRules.DO_WEATHER_CYCLE)) {
                int n = this.worldProperties.getClearWeatherTime();
                _snowman = this.worldProperties.getThunderTime();
                _snowman = this.worldProperties.getRainTime();
                boolean _snowman3 = this.properties.isThundering();
                boolean _snowman4 = this.properties.isRaining();
                if (n > 0) {
                    --n;
                    _snowman = _snowman3 ? 0 : 1;
                    _snowman = _snowman4 ? 0 : 1;
                    _snowman3 = false;
                    _snowman4 = false;
                } else {
                    if (_snowman > 0) {
                        if (--_snowman == 0) {
                            _snowman3 = !_snowman3;
                        }
                    } else {
                        _snowman = _snowman3 ? this.random.nextInt(12000) + 3600 : this.random.nextInt(168000) + 12000;
                    }
                    if (_snowman > 0) {
                        if (--_snowman == 0) {
                            _snowman4 = !_snowman4;
                        }
                    } else {
                        _snowman = _snowman4 ? this.random.nextInt(12000) + 12000 : this.random.nextInt(168000) + 12000;
                    }
                }
                this.worldProperties.setThunderTime(_snowman);
                this.worldProperties.setRainTime(_snowman);
                this.worldProperties.setClearWeatherTime(n);
                this.worldProperties.setThundering(_snowman3);
                this.worldProperties.setRaining(_snowman4);
            }
            this.thunderGradientPrev = this.thunderGradient;
            this.thunderGradient = this.properties.isThundering() ? (float)((double)this.thunderGradient + 0.01) : (float)((double)this.thunderGradient - 0.01);
            this.thunderGradient = MathHelper.clamp(this.thunderGradient, 0.0f, 1.0f);
            this.rainGradientPrev = this.rainGradient;
            this.rainGradient = this.properties.isRaining() ? (float)((double)this.rainGradient + 0.01) : (float)((double)this.rainGradient - 0.01);
            this.rainGradient = MathHelper.clamp(this.rainGradient, 0.0f, 1.0f);
        }
        if (this.rainGradientPrev != this.rainGradient) {
            this.server.getPlayerManager().sendToDimension(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.RAIN_GRADIENT_CHANGED, this.rainGradient), this.getRegistryKey());
        }
        if (this.thunderGradientPrev != this.thunderGradient) {
            this.server.getPlayerManager().sendToDimension(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.THUNDER_GRADIENT_CHANGED, this.thunderGradient), this.getRegistryKey());
        }
        if (_snowman2 != this.isRaining()) {
            if (_snowman2) {
                this.server.getPlayerManager().sendToAll(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.RAIN_STOPPED, 0.0f));
            } else {
                this.server.getPlayerManager().sendToAll(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.RAIN_STARTED, 0.0f));
            }
            this.server.getPlayerManager().sendToAll(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.RAIN_GRADIENT_CHANGED, this.rainGradient));
            this.server.getPlayerManager().sendToAll(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.THUNDER_GRADIENT_CHANGED, this.thunderGradient));
        }
        if (this.allPlayersSleeping && this.players.stream().noneMatch(player -> !player.isSpectator() && !player.isSleepingLongEnough())) {
            this.allPlayersSleeping = false;
            if (this.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
                long _snowman5 = this.properties.getTimeOfDay() + 24000L;
                this.setTimeOfDay(_snowman5 - _snowman5 % 24000L);
            }
            this.wakeSleepingPlayers();
            if (this.getGameRules().getBoolean(GameRules.DO_WEATHER_CYCLE)) {
                this.resetWeather();
            }
        }
        this.calculateAmbientDarkness();
        this.tickTime();
        profiler.swap("chunkSource");
        this.getChunkManager().tick(shouldKeepTicking);
        profiler.swap("tickPending");
        if (!this.isDebugWorld()) {
            this.blockTickScheduler.tick();
            this.fluidTickScheduler.tick();
        }
        profiler.swap("raid");
        this.raidManager.tick();
        profiler.swap("blockEvents");
        this.processSyncedBlockEvents();
        this.inBlockTick = false;
        profiler.swap("entities");
        boolean bl = _snowman = !this.players.isEmpty() || !this.getForcedChunks().isEmpty();
        if (_snowman) {
            this.resetIdleTimeout();
        }
        if (_snowman || this.idleTimeout++ < 300) {
            if (this.enderDragonFight != null) {
                this.enderDragonFight.tick();
            }
            this.inEntityTick = true;
            ObjectIterator objectIterator = this.entitiesById.int2ObjectEntrySet().iterator();
            while (objectIterator.hasNext()) {
                Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry)objectIterator.next();
                Entity _snowman6 = (Entity)entry.getValue();
                Entity _snowman7 = _snowman6.getVehicle();
                if (!this.server.shouldSpawnAnimals() && (_snowman6 instanceof AnimalEntity || _snowman6 instanceof WaterCreatureEntity)) {
                    _snowman6.remove();
                }
                if (!this.server.shouldSpawnNpcs() && _snowman6 instanceof Npc) {
                    _snowman6.remove();
                }
                profiler.push("checkDespawn");
                if (!_snowman6.removed) {
                    _snowman6.checkDespawn();
                }
                profiler.pop();
                if (_snowman7 != null) {
                    if (!_snowman7.removed && _snowman7.hasPassenger(_snowman6)) continue;
                    _snowman6.stopRiding();
                }
                profiler.push("tick");
                if (!_snowman6.removed && !(_snowman6 instanceof EnderDragonPart)) {
                    this.tickEntity(this::tickEntity, _snowman6);
                }
                profiler.pop();
                profiler.push("remove");
                if (_snowman6.removed) {
                    this.removeEntityFromChunk(_snowman6);
                    objectIterator.remove();
                    this.unloadEntity(_snowman6);
                }
                profiler.pop();
            }
            this.inEntityTick = false;
            while ((_snowman = this.entitiesToLoad.poll()) != null) {
                this.loadEntityUnchecked(_snowman);
            }
            this.tickBlockEntities();
        }
        profiler.pop();
    }

    protected void tickTime() {
        if (!this.shouldTickTime) {
            return;
        }
        long l = this.properties.getTime() + 1L;
        this.worldProperties.setTime(l);
        this.worldProperties.getScheduledEvents().processEvents(this.server, l);
        if (this.properties.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
            this.setTimeOfDay(this.properties.getTimeOfDay() + 1L);
        }
    }

    public void setTimeOfDay(long timeOfDay) {
        this.worldProperties.setTimeOfDay(timeOfDay);
    }

    public void tickSpawners(boolean spawnMonsters, boolean spawnAnimals) {
        for (Spawner spawner : this.spawners) {
            spawner.spawn(this, spawnMonsters, spawnAnimals);
        }
    }

    private void wakeSleepingPlayers() {
        this.players.stream().filter(LivingEntity::isSleeping).collect(Collectors.toList()).forEach(player -> player.wakeUp(false, false));
    }

    public void tickChunk(WorldChunk chunk, int randomTickSpeed) {
        BlockPos blockPos;
        ChunkPos chunkPos = chunk.getPos();
        boolean _snowman2 = this.isRaining();
        int _snowman3 = chunkPos.getStartX();
        int _snowman4 = chunkPos.getStartZ();
        Profiler _snowman5 = this.getProfiler();
        _snowman5.push("thunder");
        if (_snowman2 && this.isThundering() && this.random.nextInt(100000) == 0 && this.hasRain(blockPos = this.getSurface(this.getRandomPosInChunk(_snowman3, 0, _snowman4, 15)))) {
            LocalDifficulty localDifficulty = this.getLocalDifficulty(blockPos);
            boolean bl = _snowman = this.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING) && this.random.nextDouble() < (double)localDifficulty.getLocalDifficulty() * 0.01;
            if (_snowman) {
                SkeletonHorseEntity skeletonHorseEntity = EntityType.SKELETON_HORSE.create(this);
                skeletonHorseEntity.setTrapped(true);
                skeletonHorseEntity.setBreedingAge(0);
                skeletonHorseEntity.updatePosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                this.spawnEntity(skeletonHorseEntity);
            }
            LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(this);
            lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
            lightningEntity.setCosmetic(_snowman);
            this.spawnEntity(lightningEntity);
        }
        _snowman5.swap("iceandsnow");
        if (this.random.nextInt(16) == 0) {
            BlockPos blockPos2 = this.getTopPosition(Heightmap.Type.MOTION_BLOCKING, this.getRandomPosInChunk(_snowman3, 0, _snowman4, 15));
            BlockPos _snowman6 = blockPos2.down();
            Biome _snowman7 = this.getBiome(blockPos2);
            if (_snowman7.canSetIce(this, _snowman6)) {
                this.setBlockState(_snowman6, Blocks.ICE.getDefaultState());
            }
            if (_snowman2 && _snowman7.canSetSnow(this, blockPos2)) {
                this.setBlockState(blockPos2, Blocks.SNOW.getDefaultState());
            }
            if (_snowman2 && this.getBiome(_snowman6).getPrecipitation() == Biome.Precipitation.RAIN) {
                this.getBlockState(_snowman6).getBlock().rainTick(this, _snowman6);
            }
        }
        _snowman5.swap("tickBlocks");
        if (randomTickSpeed > 0) {
            for (ChunkSection chunkSection : chunk.getSectionArray()) {
                if (chunkSection == WorldChunk.EMPTY_SECTION || !chunkSection.hasRandomTicks()) continue;
                int n = chunkSection.getYOffset();
                for (int i = 0; i < randomTickSpeed; ++i) {
                    blockPos = this.getRandomPosInChunk(_snowman3, n, _snowman4, 15);
                    _snowman5.push("randomTick");
                    BlockState _snowman8 = chunkSection.getBlockState(blockPos.getX() - _snowman3, blockPos.getY() - n, blockPos.getZ() - _snowman4);
                    if (_snowman8.hasRandomTicks()) {
                        _snowman8.randomTick(this, blockPos, this.random);
                    }
                    if ((_snowman = _snowman8.getFluidState()).hasRandomTicks()) {
                        _snowman.onRandomTick(this, blockPos, this.random);
                    }
                    _snowman5.pop();
                }
            }
        }
        _snowman5.pop();
    }

    protected BlockPos getSurface(BlockPos pos) {
        BlockPos blockPos = this.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos);
        Box _snowman2 = new Box(blockPos, new BlockPos(blockPos.getX(), this.getHeight(), blockPos.getZ())).expand(3.0);
        List<LivingEntity> _snowman3 = this.getEntitiesByClass(LivingEntity.class, _snowman2, entity -> entity != null && entity.isAlive() && this.isSkyVisible(entity.getBlockPos()));
        if (!_snowman3.isEmpty()) {
            return _snowman3.get(this.random.nextInt(_snowman3.size())).getBlockPos();
        }
        if (blockPos.getY() == -1) {
            blockPos = blockPos.up(2);
        }
        return blockPos;
    }

    public boolean isInBlockTick() {
        return this.inBlockTick;
    }

    public void updateSleepingPlayers() {
        this.allPlayersSleeping = false;
        if (!this.players.isEmpty()) {
            int n;
            int n2 = 0;
            n = 0;
            for (ServerPlayerEntity serverPlayerEntity : this.players) {
                if (serverPlayerEntity.isSpectator()) {
                    ++n2;
                    continue;
                }
                if (!serverPlayerEntity.isSleeping()) continue;
                ++n;
            }
            this.allPlayersSleeping = n > 0 && n >= this.players.size() - n2;
        }
    }

    @Override
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
        FluidState fluidState = this.getFluidState(tick.pos);
        if (fluidState.getFluid() == tick.getObject()) {
            fluidState.onScheduledTick(this, tick.pos);
        }
    }

    private void tickBlock(ScheduledTick<Block> tick) {
        BlockState blockState = this.getBlockState(tick.pos);
        if (blockState.isOf(tick.getObject())) {
            blockState.scheduledTick(this, tick.pos, this.random);
        }
    }

    public void tickEntity(Entity entity) {
        if (!(entity instanceof PlayerEntity) && !this.getChunkManager().shouldTickEntity(entity)) {
            this.checkEntityChunkPos(entity);
            return;
        }
        entity.resetPosition(entity.getX(), entity.getY(), entity.getZ());
        entity.prevYaw = entity.yaw;
        entity.prevPitch = entity.pitch;
        if (entity.updateNeeded) {
            ++entity.age;
            Profiler profiler = this.getProfiler();
            profiler.push(() -> Registry.ENTITY_TYPE.getId(entity.getType()).toString());
            profiler.visit("tickNonPassenger");
            entity.tick();
            profiler.pop();
        }
        this.checkEntityChunkPos(entity);
        if (entity.updateNeeded) {
            for (Entity entity2 : entity.getPassengerList()) {
                this.tickPassenger(entity, entity2);
            }
        }
    }

    public void tickPassenger(Entity vehicle, Entity passenger) {
        if (passenger.removed || passenger.getVehicle() != vehicle) {
            passenger.stopRiding();
            return;
        }
        if (!(passenger instanceof PlayerEntity) && !this.getChunkManager().shouldTickEntity(passenger)) {
            return;
        }
        passenger.resetPosition(passenger.getX(), passenger.getY(), passenger.getZ());
        passenger.prevYaw = passenger.yaw;
        passenger.prevPitch = passenger.pitch;
        if (passenger.updateNeeded) {
            ++passenger.age;
            Profiler profiler = this.getProfiler();
            profiler.push(() -> Registry.ENTITY_TYPE.getId(passenger.getType()).toString());
            profiler.visit("tickPassenger");
            passenger.tickRiding();
            profiler.pop();
        }
        this.checkEntityChunkPos(passenger);
        if (passenger.updateNeeded) {
            for (Entity entity : passenger.getPassengerList()) {
                this.tickPassenger(passenger, entity);
            }
        }
    }

    public void checkEntityChunkPos(Entity entity) {
        if (!entity.isChunkPosUpdateRequested()) {
            return;
        }
        this.getProfiler().push("chunkCheck");
        int n = MathHelper.floor(entity.getX() / 16.0);
        _snowman = MathHelper.floor(entity.getY() / 16.0);
        _snowman = MathHelper.floor(entity.getZ() / 16.0);
        if (!entity.updateNeeded || entity.chunkX != n || entity.chunkY != _snowman || entity.chunkZ != _snowman) {
            if (entity.updateNeeded && this.isChunkLoaded(entity.chunkX, entity.chunkZ)) {
                this.getChunk(entity.chunkX, entity.chunkZ).remove(entity, entity.chunkY);
            }
            if (entity.teleportRequested() || this.isChunkLoaded(n, _snowman)) {
                this.getChunk(n, _snowman).addEntity(entity);
            } else {
                if (entity.updateNeeded) {
                    LOGGER.warn("Entity {} left loaded chunk area", (Object)entity);
                }
                entity.updateNeeded = false;
            }
        }
        this.getProfiler().pop();
    }

    @Override
    public boolean canPlayerModifyAt(PlayerEntity player, BlockPos pos) {
        return !this.server.isSpawnProtected(this, pos, player) && this.getWorldBorder().contains(pos);
    }

    public void save(@Nullable ProgressListener progressListener, boolean flush, boolean bl) {
        ServerChunkManager serverChunkManager = this.getChunkManager();
        if (bl) {
            return;
        }
        if (progressListener != null) {
            progressListener.method_15412(new TranslatableText("menu.savingLevel"));
        }
        this.saveLevel();
        if (progressListener != null) {
            progressListener.method_15414(new TranslatableText("menu.savingChunks"));
        }
        serverChunkManager.save(flush);
    }

    private void saveLevel() {
        if (this.enderDragonFight != null) {
            this.server.getSaveProperties().setDragonFight(this.enderDragonFight.toTag());
        }
        this.getChunkManager().getPersistentStateManager().save();
    }

    public List<Entity> getEntitiesByType(@Nullable EntityType<?> type, Predicate<? super Entity> predicate) {
        ArrayList arrayList = Lists.newArrayList();
        ServerChunkManager _snowman2 = this.getChunkManager();
        for (Entity entity : this.entitiesById.values()) {
            if (type != null && entity.getType() != type || !_snowman2.isChunkLoaded(MathHelper.floor(entity.getX()) >> 4, MathHelper.floor(entity.getZ()) >> 4) || !predicate.test(entity)) continue;
            arrayList.add(entity);
        }
        return arrayList;
    }

    public List<EnderDragonEntity> getAliveEnderDragons() {
        ArrayList arrayList = Lists.newArrayList();
        for (Entity entity : this.entitiesById.values()) {
            if (!(entity instanceof EnderDragonEntity) || !entity.isAlive()) continue;
            arrayList.add((EnderDragonEntity)entity);
        }
        return arrayList;
    }

    public List<ServerPlayerEntity> getPlayers(Predicate<? super ServerPlayerEntity> predicate) {
        ArrayList arrayList = Lists.newArrayList();
        for (ServerPlayerEntity serverPlayerEntity : this.players) {
            if (!predicate.test(serverPlayerEntity)) continue;
            arrayList.add(serverPlayerEntity);
        }
        return arrayList;
    }

    @Nullable
    public ServerPlayerEntity getRandomAlivePlayer() {
        List<ServerPlayerEntity> list = this.getPlayers(LivingEntity::isAlive);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(this.random.nextInt(list.size()));
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
        Entity entity = this.entitiesByUuid.get(player.getUuid());
        if (entity != null) {
            LOGGER.warn("Force-added player with duplicate UUID {}", (Object)player.getUuid().toString());
            entity.detach();
            this.removePlayer((ServerPlayerEntity)entity);
        }
        this.players.add(player);
        this.updateSleepingPlayers();
        Chunk _snowman2 = this.getChunk(MathHelper.floor(player.getX() / 16.0), MathHelper.floor(player.getZ() / 16.0), ChunkStatus.FULL, true);
        if (_snowman2 instanceof WorldChunk) {
            _snowman2.addEntity(player);
        }
        this.loadEntityUnchecked(player);
    }

    private boolean addEntity(Entity entity) {
        if (entity.removed) {
            LOGGER.warn("Tried to add entity {} but it was marked as removed already", (Object)EntityType.getId(entity.getType()));
            return false;
        }
        if (this.checkUuid(entity)) {
            return false;
        }
        Chunk chunk = this.getChunk(MathHelper.floor(entity.getX() / 16.0), MathHelper.floor(entity.getZ() / 16.0), ChunkStatus.FULL, entity.teleporting);
        if (!(chunk instanceof WorldChunk)) {
            return false;
        }
        chunk.addEntity(entity);
        this.loadEntityUnchecked(entity);
        return true;
    }

    public boolean loadEntity(Entity entity) {
        if (this.checkUuid(entity)) {
            return false;
        }
        this.loadEntityUnchecked(entity);
        return true;
    }

    private boolean checkUuid(Entity entity) {
        UUID uUID = entity.getUuid();
        Entity _snowman2 = this.checkIfUuidExists(uUID);
        if (_snowman2 == null) {
            return false;
        }
        LOGGER.warn("Trying to add entity with duplicated UUID {}. Existing {}#{}, new: {}#{}", (Object)uUID, (Object)EntityType.getId(_snowman2.getType()), (Object)_snowman2.getEntityId(), (Object)EntityType.getId(entity.getType()), (Object)entity.getEntityId());
        return true;
    }

    @Nullable
    private Entity checkIfUuidExists(UUID uUID) {
        Entity entity = this.entitiesByUuid.get(uUID);
        if (entity != null) {
            return entity;
        }
        if (this.inEntityTick) {
            for (Entity entity2 : this.entitiesToLoad) {
                if (!entity2.getUuid().equals(uUID)) continue;
                return entity2;
            }
        }
        return null;
    }

    public boolean shouldCreateNewEntityWithPassenger(Entity entity) {
        if (entity.streamPassengersRecursively().anyMatch(this::checkUuid)) {
            return false;
        }
        this.spawnEntityAndPassengers(entity);
        return true;
    }

    public void unloadEntities(WorldChunk chunk) {
        this.unloadedBlockEntities.addAll(chunk.getBlockEntities().values());
        for (TypeFilterableList<Entity> typeFilterableList : chunk.getEntitySectionArray()) {
            for (Entity entity : typeFilterableList) {
                if (entity instanceof ServerPlayerEntity) continue;
                if (this.inEntityTick) {
                    throw Util.throwOrPause(new IllegalStateException("Removing entity while ticking!"));
                }
                this.entitiesById.remove(entity.getEntityId());
                this.unloadEntity(entity);
            }
        }
    }

    public void unloadEntity(Entity entity) {
        if (entity instanceof EnderDragonEntity) {
            for (EnderDragonPart enderDragonPart : ((EnderDragonEntity)entity).getBodyParts()) {
                enderDragonPart.remove();
            }
        }
        this.entitiesByUuid.remove(entity.getUuid());
        this.getChunkManager().unloadEntity(entity);
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
            this.players.remove(serverPlayerEntity);
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
            this.entitiesById.put(entity.getEntityId(), (Object)entity);
            if (entity instanceof EnderDragonEntity) {
                for (EnderDragonPart enderDragonPart : ((EnderDragonEntity)entity).getBodyParts()) {
                    this.entitiesById.put(enderDragonPart.getEntityId(), (Object)enderDragonPart);
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
            throw Util.throwOrPause(new IllegalStateException("Removing entity while ticking!"));
        }
        this.removeEntityFromChunk(entity);
        this.entitiesById.remove(entity.getEntityId());
        this.unloadEntity(entity);
    }

    private void removeEntityFromChunk(Entity entity) {
        Chunk chunk = this.getChunk(entity.chunkX, entity.chunkZ, ChunkStatus.FULL, false);
        if (chunk instanceof WorldChunk) {
            ((WorldChunk)chunk).remove(entity);
        }
    }

    public void removePlayer(ServerPlayerEntity player) {
        player.remove();
        this.removeEntity(player);
        this.updateSleepingPlayers();
    }

    @Override
    public void setBlockBreakingInfo(int entityId, BlockPos pos, int progress) {
        for (ServerPlayerEntity serverPlayerEntity : this.server.getPlayerManager().getPlayerList()) {
            if (serverPlayerEntity == null || serverPlayerEntity.world != this || serverPlayerEntity.getEntityId() == entityId || !((_snowman = (double)pos.getX() - serverPlayerEntity.getX()) * _snowman + (_snowman = (double)pos.getY() - serverPlayerEntity.getY()) * _snowman + (_snowman = (double)pos.getZ() - serverPlayerEntity.getZ()) * _snowman < 1024.0)) continue;
            serverPlayerEntity.networkHandler.sendPacket(new BlockBreakingProgressS2CPacket(entityId, pos, progress));
        }
    }

    @Override
    public void playSound(@Nullable PlayerEntity player, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch) {
        this.server.getPlayerManager().sendToAround(player, x, y, z, volume > 1.0f ? (double)(16.0f * volume) : 16.0, this.getRegistryKey(), new PlaySoundS2CPacket(sound, category, x, y, z, volume, pitch));
    }

    @Override
    public void playSoundFromEntity(@Nullable PlayerEntity player, Entity entity, SoundEvent sound, SoundCategory category, float volume, float pitch) {
        this.server.getPlayerManager().sendToAround(player, entity.getX(), entity.getY(), entity.getZ(), volume > 1.0f ? (double)(16.0f * volume) : 16.0, this.getRegistryKey(), new PlaySoundFromEntityS2CPacket(sound, category, entity, volume, pitch));
    }

    @Override
    public void syncGlobalEvent(int eventId, BlockPos pos, int data) {
        this.server.getPlayerManager().sendToAll(new WorldEventS2CPacket(eventId, pos, data, true));
    }

    @Override
    public void syncWorldEvent(@Nullable PlayerEntity player, int eventId, BlockPos pos, int data) {
        this.server.getPlayerManager().sendToAround(player, pos.getX(), pos.getY(), pos.getZ(), 64.0, this.getRegistryKey(), new WorldEventS2CPacket(eventId, pos, data, false));
    }

    @Override
    public void updateListeners(BlockPos pos, BlockState oldState, BlockState newState, int flags) {
        this.getChunkManager().markForUpdate(pos);
        VoxelShape voxelShape = oldState.getCollisionShape(this, pos);
        _snowman = newState.getCollisionShape(this, pos);
        if (!VoxelShapes.matchesAnywhere(voxelShape, _snowman, BooleanBiFunction.NOT_SAME)) {
            return;
        }
        for (EntityNavigation entityNavigation : this.entityNavigations) {
            if (entityNavigation.shouldRecalculatePath()) continue;
            entityNavigation.onBlockChanged(pos);
        }
    }

    @Override
    public void sendEntityStatus(Entity entity, byte status) {
        this.getChunkManager().sendToNearbyPlayers(entity, new EntityStatusS2CPacket(entity, status));
    }

    @Override
    public ServerChunkManager getChunkManager() {
        return this.serverChunkManager;
    }

    @Override
    public Explosion createExplosion(@Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionBehavior explosionBehavior, double d, double d2, double d3, float f, boolean bl, Explosion.DestructionType destructionType) {
        Explosion explosion = new Explosion(this, entity, damageSource, explosionBehavior, d, d2, d3, f, bl, destructionType);
        explosion.collectBlocksAndDamageEntities();
        explosion.affectWorld(false);
        if (destructionType == Explosion.DestructionType.NONE) {
            explosion.clearAffectedBlocks();
        }
        for (ServerPlayerEntity serverPlayerEntity : this.players) {
            if (!(serverPlayerEntity.squaredDistanceTo(d, d2, d3) < 4096.0)) continue;
            serverPlayerEntity.networkHandler.sendPacket(new ExplosionS2CPacket(d, d2, d3, f, explosion.getAffectedBlocks(), explosion.getAffectedPlayers().get(serverPlayerEntity)));
        }
        return explosion;
    }

    @Override
    public void addSyncedBlockEvent(BlockPos pos, Block block, int type, int data) {
        this.syncedBlockEventQueue.add((Object)new BlockEvent(pos, block, type, data));
    }

    private void processSyncedBlockEvents() {
        while (!this.syncedBlockEventQueue.isEmpty()) {
            BlockEvent blockEvent = (BlockEvent)this.syncedBlockEventQueue.removeFirst();
            if (!this.processBlockEvent(blockEvent)) continue;
            this.server.getPlayerManager().sendToAround(null, blockEvent.getPos().getX(), blockEvent.getPos().getY(), blockEvent.getPos().getZ(), 64.0, this.getRegistryKey(), new BlockEventS2CPacket(blockEvent.getPos(), blockEvent.getBlock(), blockEvent.getType(), blockEvent.getData()));
        }
    }

    private boolean processBlockEvent(BlockEvent event) {
        BlockState blockState = this.getBlockState(event.getPos());
        if (blockState.isOf(event.getBlock())) {
            return blockState.onSyncedBlockEvent(this, event.getPos(), event.getType(), event.getData());
        }
        return false;
    }

    public ServerTickScheduler<Block> getBlockTickScheduler() {
        return this.blockTickScheduler;
    }

    public ServerTickScheduler<Fluid> getFluidTickScheduler() {
        return this.fluidTickScheduler;
    }

    @Override
    @Nonnull
    public MinecraftServer getServer() {
        return this.server;
    }

    public PortalForcer getPortalForcer() {
        return this.portalForcer;
    }

    public StructureManager getStructureManager() {
        return this.server.getStructureManager();
    }

    public <T extends ParticleEffect> int spawnParticles(T particle, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed) {
        ParticleS2CPacket particleS2CPacket = new ParticleS2CPacket(particle, false, x, y, z, (float)deltaX, (float)deltaY, (float)deltaZ, (float)speed, count);
        int _snowman2 = 0;
        for (int i = 0; i < this.players.size(); ++i) {
            ServerPlayerEntity serverPlayerEntity = this.players.get(i);
            if (!this.sendToPlayerIfNearby(serverPlayerEntity, false, x, y, z, particleS2CPacket)) continue;
            ++_snowman2;
        }
        return _snowman2;
    }

    public <T extends ParticleEffect> boolean spawnParticles(ServerPlayerEntity viewer, T particle, boolean force, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed) {
        ParticleS2CPacket particleS2CPacket = new ParticleS2CPacket(particle, force, x, y, z, (float)deltaX, (float)deltaY, (float)deltaZ, (float)speed, count);
        return this.sendToPlayerIfNearby(viewer, force, x, y, z, particleS2CPacket);
    }

    private boolean sendToPlayerIfNearby(ServerPlayerEntity player, boolean force, double x, double y, double z, Packet<?> packet) {
        if (player.getServerWorld() != this) {
            return false;
        }
        BlockPos blockPos = player.getBlockPos();
        if (blockPos.isWithinDistance(new Vec3d(x, y, z), force ? 512.0 : 32.0)) {
            player.networkHandler.sendPacket(packet);
            return true;
        }
        return false;
    }

    @Override
    @Nullable
    public Entity getEntityById(int id) {
        return (Entity)this.entitiesById.get(id);
    }

    @Nullable
    public Entity getEntity(UUID uUID) {
        return this.entitiesByUuid.get(uUID);
    }

    @Nullable
    public BlockPos locateStructure(StructureFeature<?> feature, BlockPos pos, int radius, boolean skipExistingChunks) {
        if (!this.server.getSaveProperties().getGeneratorOptions().shouldGenerateStructures()) {
            return null;
        }
        return this.getChunkManager().getChunkGenerator().locateStructure(this, feature, pos, radius, skipExistingChunks);
    }

    @Nullable
    public BlockPos locateBiome(Biome biome, BlockPos pos, int radius, int n) {
        return this.getChunkManager().getChunkGenerator().getBiomeSource().locateBiome(pos.getX(), pos.getY(), pos.getZ(), radius, n, biome2 -> biome2 == biome, this.random, true);
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

    @Override
    @Nullable
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
        ChunkPos chunkPos = new ChunkPos(new BlockPos(this.properties.getSpawnX(), 0, this.properties.getSpawnZ()));
        this.properties.setSpawnPos(pos, angle);
        this.getChunkManager().removeTicket(ChunkTicketType.START, chunkPos, 11, Unit.INSTANCE);
        this.getChunkManager().addTicket(ChunkTicketType.START, new ChunkPos(pos), 11, Unit.INSTANCE);
        this.getServer().getPlayerManager().sendToAll(new PlayerSpawnPositionS2CPacket(pos, angle));
    }

    public BlockPos getSpawnPos() {
        BlockPos blockPos = new BlockPos(this.properties.getSpawnX(), this.properties.getSpawnY(), this.properties.getSpawnZ());
        if (!this.getWorldBorder().contains(blockPos)) {
            blockPos = this.getTopPosition(Heightmap.Type.MOTION_BLOCKING, new BlockPos(this.getWorldBorder().getCenterX(), 0.0, this.getWorldBorder().getCenterZ()));
        }
        return blockPos;
    }

    public float getSpawnAngle() {
        return this.properties.getSpawnAngle();
    }

    public LongSet getForcedChunks() {
        ForcedChunkState forcedChunkState = this.getPersistentStateManager().get(ForcedChunkState::new, "chunks");
        return forcedChunkState != null ? LongSets.unmodifiable((LongSet)forcedChunkState.getChunks()) : LongSets.EMPTY_SET;
    }

    public boolean setChunkForced(int x, int z, boolean forced) {
        boolean _snowman4;
        ForcedChunkState forcedChunkState = this.getPersistentStateManager().getOrCreate(ForcedChunkState::new, "chunks");
        ChunkPos _snowman2 = new ChunkPos(x, z);
        long _snowman3 = _snowman2.toLong();
        if (forced) {
            _snowman4 = forcedChunkState.getChunks().add(_snowman3);
            if (_snowman4) {
                this.getChunk(x, z);
            }
        } else {
            _snowman4 = forcedChunkState.getChunks().remove(_snowman3);
        }
        forcedChunkState.setDirty(_snowman4);
        if (_snowman4) {
            this.getChunkManager().setChunkForced(_snowman2, forced);
        }
        return _snowman4;
    }

    public List<ServerPlayerEntity> getPlayers() {
        return this.players;
    }

    @Override
    public void onBlockChanged(BlockPos pos, BlockState oldBlock, BlockState newBlock) {
        Optional<PointOfInterestType> optional = PointOfInterestType.from(oldBlock);
        if (Objects.equals(optional, _snowman = PointOfInterestType.from(newBlock))) {
            return;
        }
        BlockPos _snowman2 = pos.toImmutable();
        optional.ifPresent(pointOfInterestType -> this.getServer().execute(() -> {
            this.getPointOfInterestStorage().remove(_snowman2);
            DebugInfoSender.sendPoiRemoval(this, _snowman2);
        }));
        _snowman.ifPresent(pointOfInterestType -> this.getServer().execute(() -> {
            this.getPointOfInterestStorage().add(_snowman2, (PointOfInterestType)pointOfInterestType);
            DebugInfoSender.sendPoiAddition(this, _snowman2);
        }));
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
        if (maxDistance > 6) {
            return false;
        }
        return this.getOccupiedPointOfInterestDistance(ChunkSectionPos.from(pos)) <= maxDistance;
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
        ThreadedAnvilChunkStorage threadedAnvilChunkStorage = this.getChunkManager().threadedAnvilChunkStorage;
        try (Object _snowman2 = Files.newBufferedWriter(path.resolve("stats.txt"), new OpenOption[0]);){
            ((Writer)_snowman2).write(String.format("spawning_chunks: %d\n", threadedAnvilChunkStorage.getTicketManager().getSpawningChunkCount()));
            Object object = this.getChunkManager().getSpawnInfo();
            if (object != null) {
                for (Object _snowman3 : ((SpawnHelper.Info)object).getGroupToCount().object2IntEntrySet()) {
                    ((Writer)_snowman2).write(String.format("spawn_count.%s: %d\n", ((SpawnGroup)_snowman3.getKey()).getName(), _snowman3.getIntValue()));
                }
            }
            ((Writer)_snowman2).write(String.format("entities: %d\n", this.entitiesById.size()));
            ((Writer)_snowman2).write(String.format("block_entities: %d\n", this.blockEntities.size()));
            ((Writer)_snowman2).write(String.format("block_ticks: %d\n", ((ServerTickScheduler)this.getBlockTickScheduler()).getTicks()));
            ((Writer)_snowman2).write(String.format("fluid_ticks: %d\n", ((ServerTickScheduler)this.getFluidTickScheduler()).getTicks()));
            ((Writer)_snowman2).write("distance_manager: " + threadedAnvilChunkStorage.getTicketManager().toDumpString() + "\n");
            ((Writer)_snowman2).write(String.format("pending_tasks: %d\n", this.getChunkManager().getPendingTasks()));
        }
        _snowman2 = new CrashReport("Level dump", new Exception("dummy"));
        this.addDetailsToCrashReport((CrashReport)_snowman2);
        Object object = Files.newBufferedWriter(path.resolve("example_crash.txt"), new OpenOption[0]);
        object = null;
        try {
            ((Writer)object).write(((CrashReport)_snowman2).asString());
        }
        catch (Throwable throwable) {
            object = throwable;
            throw throwable;
        }
        finally {
            if (object != null) {
                if (object != null) {
                    try {
                        ((Writer)object).close();
                    }
                    catch (Throwable throwable) {
                        ((Throwable)object).addSuppressed(throwable);
                    }
                } else {
                    ((Writer)object).close();
                }
            }
        }
        object = path.resolve("chunks.csv");
        object = Files.newBufferedWriter((Path)object, new OpenOption[0]);
        Object object2 = null;
        try {
            threadedAnvilChunkStorage.dump((Writer)object);
        }
        catch (Throwable _snowman3) {
            object2 = _snowman3;
            throw _snowman3;
        }
        finally {
            if (object != null) {
                if (object2 != null) {
                    try {
                        ((Writer)object).close();
                    }
                    catch (Throwable _snowman3) {
                        ((Throwable)object2).addSuppressed(_snowman3);
                    }
                } else {
                    ((Writer)object).close();
                }
            }
        }
        object = path.resolve("entities.csv");
        _snowman = Files.newBufferedWriter((Path)object, new OpenOption[0]);
        Object _snowman3 = null;
        try {
            ServerWorld.dumpEntities((Writer)_snowman, (Iterable<Entity>)this.entitiesById.values());
        }
        catch (Throwable throwable) {
            _snowman3 = throwable;
            throw throwable;
        }
        finally {
            if (_snowman != null) {
                if (_snowman3 != null) {
                    try {
                        ((Writer)_snowman).close();
                    }
                    catch (Throwable throwable) {
                        ((Throwable)_snowman3).addSuppressed(throwable);
                    }
                } else {
                    ((Writer)_snowman).close();
                }
            }
        }
        _snowman = path.resolve("block_entities.csv");
        _snowman3 = Files.newBufferedWriter((Path)_snowman, new OpenOption[0]);
        Throwable throwable = null;
        try {
            this.dumpBlockEntities((Writer)_snowman3);
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            if (_snowman3 != null) {
                if (throwable != null) {
                    try {
                        ((Writer)_snowman3).close();
                    }
                    catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                } else {
                    ((Writer)_snowman3).close();
                }
            }
        }
    }

    private static void dumpEntities(Writer writer, Iterable<Entity> entities) throws IOException {
        CsvWriter csvWriter = CsvWriter.makeHeader().addColumn("x").addColumn("y").addColumn("z").addColumn("uuid").addColumn("type").addColumn("alive").addColumn("display_name").addColumn("custom_name").startBody(writer);
        for (Entity entity : entities) {
            Text text = entity.getCustomName();
            _snowman = entity.getDisplayName();
            csvWriter.printRow(entity.getX(), entity.getY(), entity.getZ(), entity.getUuid(), Registry.ENTITY_TYPE.getId(entity.getType()), entity.isAlive(), _snowman.getString(), text != null ? text.getString() : null);
        }
    }

    private void dumpBlockEntities(Writer writer) throws IOException {
        CsvWriter csvWriter = CsvWriter.makeHeader().addColumn("x").addColumn("y").addColumn("z").addColumn("type").startBody(writer);
        for (BlockEntity blockEntity : this.blockEntities) {
            BlockPos blockPos = blockEntity.getPos();
            csvWriter.printRow(blockPos.getX(), blockPos.getY(), blockPos.getZ(), Registry.BLOCK_ENTITY_TYPE.getId(blockEntity.getType()));
        }
    }

    @VisibleForTesting
    public void clearUpdatesInArea(BlockBox box) {
        this.syncedBlockEventQueue.removeIf(blockEvent -> box.contains(blockEvent.getPos()));
    }

    @Override
    public void updateNeighbors(BlockPos pos, Block block) {
        if (!this.isDebugWorld()) {
            this.updateNeighborsAlways(pos, block);
        }
    }

    @Override
    public float getBrightness(Direction direction, boolean shaded) {
        return 1.0f;
    }

    public Iterable<Entity> iterateEntities() {
        return Iterables.unmodifiableIterable((Iterable)this.entitiesById.values());
    }

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
        return String.format("players: %s, entities: %d [%s], block_entities: %d [%s], block_ticks: %d, fluid_ticks: %d, chunk_source: %s", this.players.size(), this.entitiesById.size(), ServerWorld.method_31270(this.entitiesById.values(), entity -> Registry.ENTITY_TYPE.getId(entity.getType())), this.tickingBlockEntities.size(), ServerWorld.method_31270(this.tickingBlockEntities, blockEntity -> Registry.BLOCK_ENTITY_TYPE.getId(blockEntity.getType())), ((ServerTickScheduler)this.getBlockTickScheduler()).getTicks(), ((ServerTickScheduler)this.getFluidTickScheduler()).getTicks(), this.getDebugString());
    }

    private static <T> String method_31270(Collection<T> collection, Function<T, Identifier> function) {
        try {
            Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
            for (T t : collection) {
                Identifier identifier = function.apply(t);
                object2IntOpenHashMap.addTo((Object)identifier, 1);
            }
            return object2IntOpenHashMap.object2IntEntrySet().stream().sorted(Comparator.comparing(Object2IntMap.Entry::getIntValue).reversed()).limit(5L).map(entry -> entry.getKey() + ":" + entry.getIntValue()).collect(Collectors.joining(","));
        }
        catch (Exception exception) {
            return "";
        }
    }

    public static void createEndSpawnPlatform(ServerWorld world) {
        BlockPos blockPos2 = END_SPAWN_POS;
        int _snowman2 = blockPos2.getX();
        int _snowman3 = blockPos2.getY() - 2;
        int _snowman4 = blockPos2.getZ();
        BlockPos.iterate(_snowman2 - 2, _snowman3 + 1, _snowman4 - 2, _snowman2 + 2, _snowman3 + 3, _snowman4 + 2).forEach(blockPos -> world.setBlockState((BlockPos)blockPos, Blocks.AIR.getDefaultState()));
        BlockPos.iterate(_snowman2 - 2, _snowman3, _snowman4 - 2, _snowman2 + 2, _snowman3, _snowman4 + 2).forEach(blockPos -> world.setBlockState((BlockPos)blockPos, Blocks.OBSIDIAN.getDefaultState()));
    }

    @Override
    public /* synthetic */ Scoreboard getScoreboard() {
        return this.getScoreboard();
    }

    @Override
    public /* synthetic */ ChunkManager getChunkManager() {
        return this.getChunkManager();
    }

    public /* synthetic */ TickScheduler getFluidTickScheduler() {
        return this.getFluidTickScheduler();
    }

    public /* synthetic */ TickScheduler getBlockTickScheduler() {
        return this.getBlockTickScheduler();
    }
}

