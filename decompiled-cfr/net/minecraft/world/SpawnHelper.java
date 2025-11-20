/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.collection.WeightedPicker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.GravityField;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.DirectBiomeAccessType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class SpawnHelper {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int CHUNK_AREA = (int)Math.pow(17.0, 2.0);
    private static final SpawnGroup[] SPAWNABLE_GROUPS = (SpawnGroup[])Stream.of(SpawnGroup.values()).filter(spawnGroup -> spawnGroup != SpawnGroup.MISC).toArray(SpawnGroup[]::new);

    public static Info setupSpawn(int spawningChunkCount, Iterable<Entity> entities, ChunkSource chunkSource) {
        GravityField gravityField = new GravityField();
        Object2IntOpenHashMap _snowman2 = new Object2IntOpenHashMap();
        for (Entity entity : entities) {
            if (entity instanceof MobEntity && (((MobEntity)(_snowman = (MobEntity)entity)).isPersistent() || ((MobEntity)_snowman).cannotDespawn()) || (_snowman = entity.getType().getSpawnGroup()) == SpawnGroup.MISC) continue;
            BlockPos blockPos = entity.getBlockPos();
            long _snowman3 = ChunkPos.toLong(blockPos.getX() >> 4, blockPos.getZ() >> 4);
            chunkSource.query(_snowman3, arg_0 -> SpawnHelper.method_27819(blockPos, entity, gravityField, _snowman2, (SpawnGroup)_snowman, arg_0));
        }
        return new Info(spawningChunkCount, _snowman2, gravityField);
    }

    private static Biome getBiomeDirectly(BlockPos pos, Chunk chunk) {
        return DirectBiomeAccessType.INSTANCE.getBiome(0L, pos.getX(), pos.getY(), pos.getZ(), chunk.getBiomeArray());
    }

    public static void spawn(ServerWorld world, WorldChunk chunk2, Info info, boolean spawnAnimals, boolean spawnMonsters, boolean shouldSpawnAnimals) {
        world.getProfiler().push("spawner");
        for (SpawnGroup spawnGroup : SPAWNABLE_GROUPS) {
            if (!spawnAnimals && spawnGroup.isPeaceful() || !spawnMonsters && !spawnGroup.isPeaceful() || !shouldSpawnAnimals && spawnGroup.isAnimal() || !info.isBelowCap(spawnGroup)) continue;
            SpawnHelper.spawnEntitiesInChunk(spawnGroup, world, chunk2, (entityType, blockPos, chunk) -> info.test(entityType, blockPos, chunk), (mobEntity, chunk) -> info.run(mobEntity, chunk));
        }
        world.getProfiler().pop();
    }

    public static void spawnEntitiesInChunk(SpawnGroup group, ServerWorld world, WorldChunk chunk, Checker checker, Runner runner) {
        BlockPos blockPos = SpawnHelper.getSpawnPos(world, chunk);
        if (blockPos.getY() < 1) {
            return;
        }
        SpawnHelper.spawnEntitiesInChunk(group, world, chunk, blockPos, checker, runner);
    }

    public static void spawnEntitiesInChunk(SpawnGroup group, ServerWorld world, Chunk chunk, BlockPos pos, Checker checker, Runner runner) {
        StructureAccessor structureAccessor = world.getStructureAccessor();
        ChunkGenerator _snowman2 = world.getChunkManager().getChunkGenerator();
        int _snowman3 = pos.getY();
        BlockState _snowman4 = chunk.getBlockState(pos);
        if (_snowman4.isSolidBlock(chunk, pos)) {
            return;
        }
        BlockPos.Mutable _snowman5 = new BlockPos.Mutable();
        int _snowman6 = 0;
        block0: for (int i = 0; i < 3; ++i) {
            _snowman = pos.getX();
            _snowman = pos.getZ();
            _snowman = 6;
            SpawnSettings.SpawnEntry spawnEntry = null;
            EntityData _snowman7 = null;
            int _snowman8 = MathHelper.ceil(world.random.nextFloat() * 4.0f);
            int _snowman9 = 0;
            for (int j = 0; j < _snowman8; ++j) {
                _snowman5.set(_snowman += world.random.nextInt(6) - world.random.nextInt(6), _snowman3, _snowman += world.random.nextInt(6) - world.random.nextInt(6));
                double d = (double)_snowman + 0.5;
                _snowman = (double)_snowman + 0.5;
                PlayerEntity _snowman10 = world.getClosestPlayer(d, (double)_snowman3, _snowman, -1.0, false);
                if (_snowman10 == null || !SpawnHelper.isAcceptableSpawnPosition(world, chunk, _snowman5, _snowman = _snowman10.squaredDistanceTo(d, _snowman3, _snowman))) continue;
                if (spawnEntry == null) {
                    spawnEntry = SpawnHelper.pickRandomSpawnEntry(world, structureAccessor, _snowman2, group, world.random, _snowman5);
                    if (spawnEntry == null) continue block0;
                    _snowman8 = spawnEntry.minGroupSize + world.random.nextInt(1 + spawnEntry.maxGroupSize - spawnEntry.minGroupSize);
                }
                if (!SpawnHelper.canSpawn(world, group, structureAccessor, _snowman2, spawnEntry, _snowman5, _snowman) || !checker.test(spawnEntry.type, _snowman5, chunk)) continue;
                MobEntity _snowman11 = SpawnHelper.createMob(world, spawnEntry.type);
                if (_snowman11 == null) {
                    return;
                }
                _snowman11.refreshPositionAndAngles(d, _snowman3, _snowman, world.random.nextFloat() * 360.0f, 0.0f);
                if (!SpawnHelper.isValidSpawn(world, _snowman11, _snowman)) continue;
                _snowman7 = _snowman11.initialize(world, world.getLocalDifficulty(_snowman11.getBlockPos()), SpawnReason.NATURAL, _snowman7, null);
                ++_snowman9;
                world.spawnEntityAndPassengers(_snowman11);
                runner.run(_snowman11, chunk);
                if (++_snowman6 >= _snowman11.getLimitPerChunk()) {
                    return;
                }
                if (_snowman11.spawnsTooManyForEachTry(_snowman9)) continue block0;
            }
        }
    }

    private static boolean isAcceptableSpawnPosition(ServerWorld world, Chunk chunk, BlockPos.Mutable pos, double squaredDistance) {
        if (squaredDistance <= 576.0) {
            return false;
        }
        if (world.getSpawnPos().isWithinDistance(new Vec3d((double)pos.getX() + 0.5, pos.getY(), (double)pos.getZ() + 0.5), 24.0)) {
            return false;
        }
        ChunkPos chunkPos = new ChunkPos(pos);
        return Objects.equals(chunkPos, chunk.getPos()) || world.getChunkManager().shouldTickChunk(chunkPos);
    }

    private static boolean canSpawn(ServerWorld world, SpawnGroup group, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, SpawnSettings.SpawnEntry spawnEntry, BlockPos.Mutable pos, double squaredDistance) {
        EntityType<?> entityType = spawnEntry.type;
        if (entityType.getSpawnGroup() == SpawnGroup.MISC) {
            return false;
        }
        if (!entityType.isSpawnableFarFromPlayer() && squaredDistance > (double)(entityType.getSpawnGroup().getImmediateDespawnRange() * entityType.getSpawnGroup().getImmediateDespawnRange())) {
            return false;
        }
        if (!entityType.isSummonable() || !SpawnHelper.containsSpawnEntry(world, structureAccessor, chunkGenerator, group, spawnEntry, pos)) {
            return false;
        }
        SpawnRestriction.Location _snowman2 = SpawnRestriction.getLocation(entityType);
        if (!SpawnHelper.canSpawn(_snowman2, world, pos, entityType)) {
            return false;
        }
        if (!SpawnRestriction.canSpawn(entityType, world, SpawnReason.NATURAL, pos, world.random)) {
            return false;
        }
        return world.isSpaceEmpty(entityType.createSimpleBoundingBox((double)pos.getX() + 0.5, pos.getY(), (double)pos.getZ() + 0.5));
    }

    @Nullable
    private static MobEntity createMob(ServerWorld world, EntityType<?> type) {
        MobEntity _snowman2;
        try {
            Object obj = type.create(world);
            if (!(obj instanceof MobEntity)) {
                throw new IllegalStateException("Trying to spawn a non-mob: " + Registry.ENTITY_TYPE.getId(type));
            }
            _snowman2 = (MobEntity)obj;
        }
        catch (Exception exception) {
            LOGGER.warn("Failed to create mob", (Throwable)exception);
            return null;
        }
        return _snowman2;
    }

    private static boolean isValidSpawn(ServerWorld world, MobEntity entity, double squaredDistance) {
        if (squaredDistance > (double)(entity.getType().getSpawnGroup().getImmediateDespawnRange() * entity.getType().getSpawnGroup().getImmediateDespawnRange()) && entity.canImmediatelyDespawn(squaredDistance)) {
            return false;
        }
        return entity.canSpawn(world, SpawnReason.NATURAL) && entity.canSpawn(world);
    }

    @Nullable
    private static SpawnSettings.SpawnEntry pickRandomSpawnEntry(ServerWorld serverWorld, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, SpawnGroup spawnGroup, Random random, BlockPos blockPos) {
        Biome biome = serverWorld.getBiome(blockPos);
        if (spawnGroup == SpawnGroup.WATER_AMBIENT && biome.getCategory() == Biome.Category.RIVER && random.nextFloat() < 0.98f) {
            return null;
        }
        List<SpawnSettings.SpawnEntry> _snowman2 = SpawnHelper.method_29950(serverWorld, structureAccessor, chunkGenerator, spawnGroup, blockPos, biome);
        if (_snowman2.isEmpty()) {
            return null;
        }
        return WeightedPicker.getRandom(random, _snowman2);
    }

    private static boolean containsSpawnEntry(ServerWorld serverWorld, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, SpawnGroup spawnGroup, SpawnSettings.SpawnEntry spawnEntry, BlockPos blockPos) {
        return SpawnHelper.method_29950(serverWorld, structureAccessor, chunkGenerator, spawnGroup, blockPos, null).contains(spawnEntry);
    }

    private static List<SpawnSettings.SpawnEntry> method_29950(ServerWorld serverWorld, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, SpawnGroup spawnGroup, BlockPos blockPos, @Nullable Biome biome) {
        if (spawnGroup == SpawnGroup.MONSTER && serverWorld.getBlockState(blockPos.down()).getBlock() == Blocks.NETHER_BRICKS && structureAccessor.getStructureAt(blockPos, false, StructureFeature.FORTRESS).hasChildren()) {
            return StructureFeature.FORTRESS.getMonsterSpawns();
        }
        return chunkGenerator.getEntitySpawnList(biome != null ? biome : serverWorld.getBiome(blockPos), structureAccessor, spawnGroup, blockPos);
    }

    private static BlockPos getSpawnPos(World world, WorldChunk chunk) {
        ChunkPos chunkPos = chunk.getPos();
        int _snowman2 = chunkPos.getStartX() + world.random.nextInt(16);
        int _snowman3 = chunkPos.getStartZ() + world.random.nextInt(16);
        int _snowman4 = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, _snowman2, _snowman3) + 1;
        int _snowman5 = world.random.nextInt(_snowman4 + 1);
        return new BlockPos(_snowman2, _snowman5, _snowman3);
    }

    public static boolean isClearForSpawn(BlockView blockView, BlockPos pos, BlockState state, FluidState fluidState, EntityType<?> entityType) {
        if (state.isFullCube(blockView, pos)) {
            return false;
        }
        if (state.emitsRedstonePower()) {
            return false;
        }
        if (!fluidState.isEmpty()) {
            return false;
        }
        if (state.isIn(BlockTags.PREVENT_MOB_SPAWNING_INSIDE)) {
            return false;
        }
        return !entityType.isInvalidSpawn(state);
    }

    public static boolean canSpawn(SpawnRestriction.Location location, WorldView world, BlockPos pos, @Nullable EntityType<?> entityType) {
        if (location == SpawnRestriction.Location.NO_RESTRICTIONS) {
            return true;
        }
        if (entityType == null || !world.getWorldBorder().contains(pos)) {
            return false;
        }
        BlockState blockState = world.getBlockState(pos);
        FluidState _snowman2 = world.getFluidState(pos);
        BlockPos _snowman3 = pos.up();
        BlockPos _snowman4 = pos.down();
        switch (location) {
            case IN_WATER: {
                return _snowman2.isIn(FluidTags.WATER) && world.getFluidState(_snowman4).isIn(FluidTags.WATER) && !world.getBlockState(_snowman3).isSolidBlock(world, _snowman3);
            }
            case IN_LAVA: {
                return _snowman2.isIn(FluidTags.LAVA);
            }
        }
        _snowman = world.getBlockState(_snowman4);
        if (!_snowman.allowsSpawning(world, _snowman4, entityType)) {
            return false;
        }
        return SpawnHelper.isClearForSpawn(world, pos, blockState, _snowman2, entityType) && SpawnHelper.isClearForSpawn(world, _snowman3, world.getBlockState(_snowman3), world.getFluidState(_snowman3), entityType);
    }

    public static void populateEntities(ServerWorldAccess serverWorldAccess, Biome biome, int chunkX, int chunkZ, Random random2) {
        SpawnSettings spawnSettings = biome.getSpawnSettings();
        List<SpawnSettings.SpawnEntry> _snowman2 = spawnSettings.getSpawnEntry(SpawnGroup.CREATURE);
        if (_snowman2.isEmpty()) {
            return;
        }
        int _snowman3 = chunkX << 4;
        int _snowman4 = chunkZ << 4;
        while (random2.nextFloat() < spawnSettings.getCreatureSpawnProbability()) {
            SpawnSettings.SpawnEntry spawnEntry = WeightedPicker.getRandom(random2, _snowman2);
            int _snowman5 = spawnEntry.minGroupSize + random2.nextInt(1 + spawnEntry.maxGroupSize - spawnEntry.minGroupSize);
            EntityData _snowman6 = null;
            int _snowman7 = _snowman3 + random2.nextInt(16);
            int _snowman8 = _snowman4 + random2.nextInt(16);
            int _snowman9 = _snowman7;
            int _snowman10 = _snowman8;
            for (int i = 0; i < _snowman5; ++i) {
                boolean bl = false;
                for (int j = 0; !bl && j < 4; ++j) {
                    Random random2;
                    BlockPos blockPos = SpawnHelper.getEntitySpawnPos(serverWorldAccess, spawnEntry.type, _snowman7, _snowman8);
                    if (spawnEntry.type.isSummonable() && SpawnHelper.canSpawn(SpawnRestriction.getLocation(spawnEntry.type), serverWorldAccess, blockPos, spawnEntry.type)) {
                        float f = spawnEntry.type.getWidth();
                        double _snowman11 = MathHelper.clamp((double)_snowman7, (double)_snowman3 + (double)f, (double)_snowman3 + 16.0 - (double)f);
                        double _snowman12 = MathHelper.clamp((double)_snowman8, (double)_snowman4 + (double)f, (double)_snowman4 + 16.0 - (double)f);
                        if (!serverWorldAccess.isSpaceEmpty(spawnEntry.type.createSimpleBoundingBox(_snowman11, blockPos.getY(), _snowman12)) || !SpawnRestriction.canSpawn(spawnEntry.type, serverWorldAccess, SpawnReason.CHUNK_GENERATION, new BlockPos(_snowman11, (double)blockPos.getY(), _snowman12), serverWorldAccess.getRandom())) continue;
                        try {
                            Object obj = spawnEntry.type.create(serverWorldAccess.toServerWorld());
                        }
                        catch (Exception exception) {
                            LOGGER.warn("Failed to create mob", (Throwable)exception);
                            continue;
                        }
                        ((Entity)obj).refreshPositionAndAngles(_snowman11, blockPos.getY(), _snowman12, random2.nextFloat() * 360.0f, 0.0f);
                        if (obj instanceof MobEntity && (_snowman = (MobEntity)obj).canSpawn(serverWorldAccess, SpawnReason.CHUNK_GENERATION) && _snowman.canSpawn(serverWorldAccess)) {
                            _snowman6 = _snowman.initialize(serverWorldAccess, serverWorldAccess.getLocalDifficulty(_snowman.getBlockPos()), SpawnReason.CHUNK_GENERATION, _snowman6, null);
                            serverWorldAccess.spawnEntityAndPassengers(_snowman);
                            bl = true;
                        }
                    }
                    _snowman7 += random2.nextInt(5) - random2.nextInt(5);
                    _snowman8 += random2.nextInt(5) - random2.nextInt(5);
                    while (_snowman7 < _snowman3 || _snowman7 >= _snowman3 + 16 || _snowman8 < _snowman4 || _snowman8 >= _snowman4 + 16) {
                        _snowman7 = _snowman9 + random2.nextInt(5) - random2.nextInt(5);
                        _snowman8 = _snowman10 + random2.nextInt(5) - random2.nextInt(5);
                    }
                }
            }
        }
    }

    private static BlockPos getEntitySpawnPos(WorldView world, EntityType<?> entityType, int x, int z) {
        int n = world.getTopY(SpawnRestriction.getHeightmapType(entityType), x, z);
        BlockPos.Mutable _snowman2 = new BlockPos.Mutable(x, n, z);
        if (world.getDimension().hasCeiling()) {
            do {
                _snowman2.move(Direction.DOWN);
            } while (!world.getBlockState(_snowman2).isAir());
            do {
                _snowman2.move(Direction.DOWN);
            } while (world.getBlockState(_snowman2).isAir() && _snowman2.getY() > 0);
        }
        if (SpawnRestriction.getLocation(entityType) == SpawnRestriction.Location.ON_GROUND && world.getBlockState((BlockPos)(_snowman = _snowman2.down())).canPathfindThrough(world, (BlockPos)_snowman, NavigationType.LAND)) {
            return _snowman;
        }
        return _snowman2.toImmutable();
    }

    private static /* synthetic */ void method_27819(BlockPos blockPos, Entity entity, GravityField gravityField, Object2IntOpenHashMap object2IntOpenHashMap, SpawnGroup spawnGroup, WorldChunk worldChunk) {
        SpawnSettings.SpawnDensity spawnDensity = SpawnHelper.getBiomeDirectly(blockPos, worldChunk).getSpawnSettings().getSpawnDensity(entity.getType());
        if (spawnDensity != null) {
            gravityField.addPoint(entity.getBlockPos(), spawnDensity.getMass());
        }
        object2IntOpenHashMap.addTo((Object)spawnGroup, 1);
    }

    @FunctionalInterface
    public static interface ChunkSource {
        public void query(long var1, Consumer<WorldChunk> var3);
    }

    @FunctionalInterface
    public static interface Runner {
        public void run(MobEntity var1, Chunk var2);
    }

    @FunctionalInterface
    public static interface Checker {
        public boolean test(EntityType<?> var1, BlockPos var2, Chunk var3);
    }

    public static class Info {
        private final int spawningChunkCount;
        private final Object2IntOpenHashMap<SpawnGroup> groupToCount;
        private final GravityField densityField;
        private final Object2IntMap<SpawnGroup> groupToCountView;
        @Nullable
        private BlockPos cachedPos;
        @Nullable
        private EntityType<?> cachedEntityType;
        private double cachedDensityMass;

        private Info(int spawningChunkCount, Object2IntOpenHashMap<SpawnGroup> groupToCount, GravityField densityField) {
            this.spawningChunkCount = spawningChunkCount;
            this.groupToCount = groupToCount;
            this.densityField = densityField;
            this.groupToCountView = Object2IntMaps.unmodifiable(groupToCount);
        }

        private boolean test(EntityType<?> type, BlockPos pos, Chunk chunk) {
            this.cachedPos = pos;
            this.cachedEntityType = type;
            SpawnSettings.SpawnDensity spawnDensity = SpawnHelper.getBiomeDirectly(pos, chunk).getSpawnSettings().getSpawnDensity(type);
            if (spawnDensity == null) {
                this.cachedDensityMass = 0.0;
                return true;
            }
            this.cachedDensityMass = _snowman = spawnDensity.getMass();
            double _snowman2 = this.densityField.calculate(pos, _snowman);
            return _snowman2 <= spawnDensity.getGravityLimit();
        }

        private void run(MobEntity entity, Chunk chunk) {
            EntityType<?> entityType = entity.getType();
            BlockPos _snowman2 = entity.getBlockPos();
            double _snowman3 = _snowman2.equals(this.cachedPos) && entityType == this.cachedEntityType ? this.cachedDensityMass : ((_snowman = SpawnHelper.getBiomeDirectly(_snowman2, chunk).getSpawnSettings().getSpawnDensity(entityType)) != null ? _snowman.getMass() : 0.0);
            this.densityField.addPoint(_snowman2, _snowman3);
            this.groupToCount.addTo((Object)entityType.getSpawnGroup(), 1);
        }

        public int getSpawningChunkCount() {
            return this.spawningChunkCount;
        }

        public Object2IntMap<SpawnGroup> getGroupToCount() {
            return this.groupToCountView;
        }

        private boolean isBelowCap(SpawnGroup group) {
            int n = group.getCapacity() * this.spawningChunkCount / CHUNK_AREA;
            return this.groupToCount.getInt((Object)group) < n;
        }
    }
}

