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
   private static final SpawnGroup[] SPAWNABLE_GROUPS = Stream.of(SpawnGroup.values()).filter(_snowman -> _snowman != SpawnGroup.MISC).toArray(SpawnGroup[]::new);

   public static SpawnHelper.Info setupSpawn(int spawningChunkCount, Iterable<Entity> entities, SpawnHelper.ChunkSource chunkSource) {
      GravityField _snowman = new GravityField();
      Object2IntOpenHashMap<SpawnGroup> _snowmanx = new Object2IntOpenHashMap();

      for (Entity _snowmanxx : entities) {
         if (_snowmanxx instanceof MobEntity) {
            MobEntity _snowmanxxx = (MobEntity)_snowmanxx;
            if (_snowmanxxx.isPersistent() || _snowmanxxx.cannotDespawn()) {
               continue;
            }
         }

         SpawnGroup _snowmanxxx = _snowmanxx.getType().getSpawnGroup();
         if (_snowmanxxx != SpawnGroup.MISC) {
            BlockPos _snowmanxxxx = _snowmanxx.getBlockPos();
            long _snowmanxxxxx = ChunkPos.toLong(_snowmanxxxx.getX() >> 4, _snowmanxxxx.getZ() >> 4);
            chunkSource.query(_snowmanxxxxx, _snowmanxxxxxx -> {
               SpawnSettings.SpawnDensity _snowmanxxxxxxx = getBiomeDirectly(_snowman, _snowmanxxxxxx).getSpawnSettings().getSpawnDensity(_snowman.getType());
               if (_snowmanxxxxxxx != null) {
                  _snowman.addPoint(_snowman.getBlockPos(), _snowmanxxxxxxx.getMass());
               }

               _snowman.addTo(_snowman, 1);
            });
         }
      }

      return new SpawnHelper.Info(spawningChunkCount, _snowmanx, _snowman);
   }

   private static Biome getBiomeDirectly(BlockPos pos, Chunk chunk) {
      return DirectBiomeAccessType.INSTANCE.getBiome(0L, pos.getX(), pos.getY(), pos.getZ(), chunk.getBiomeArray());
   }

   public static void spawn(ServerWorld world, WorldChunk chunk, SpawnHelper.Info info, boolean spawnAnimals, boolean spawnMonsters, boolean shouldSpawnAnimals) {
      world.getProfiler().push("spawner");

      for (SpawnGroup _snowman : SPAWNABLE_GROUPS) {
         if ((spawnAnimals || !_snowman.isPeaceful()) && (spawnMonsters || _snowman.isPeaceful()) && (shouldSpawnAnimals || !_snowman.isAnimal()) && info.isBelowCap(_snowman)) {
            spawnEntitiesInChunk(_snowman, world, chunk, (_snowmanx, _snowmanxx, _snowmanxxx) -> info.test(_snowmanx, _snowmanxx, _snowmanxxx), (_snowmanx, _snowmanxx) -> info.run(_snowmanx, _snowmanxx));
         }
      }

      world.getProfiler().pop();
   }

   public static void spawnEntitiesInChunk(SpawnGroup group, ServerWorld world, WorldChunk chunk, SpawnHelper.Checker checker, SpawnHelper.Runner runner) {
      BlockPos _snowman = getSpawnPos(world, chunk);
      if (_snowman.getY() >= 1) {
         spawnEntitiesInChunk(group, world, chunk, _snowman, checker, runner);
      }
   }

   public static void spawnEntitiesInChunk(
      SpawnGroup group, ServerWorld world, Chunk chunk, BlockPos pos, SpawnHelper.Checker checker, SpawnHelper.Runner runner
   ) {
      StructureAccessor _snowman = world.getStructureAccessor();
      ChunkGenerator _snowmanx = world.getChunkManager().getChunkGenerator();
      int _snowmanxx = pos.getY();
      BlockState _snowmanxxx = chunk.getBlockState(pos);
      if (!_snowmanxxx.isSolidBlock(chunk, pos)) {
         BlockPos.Mutable _snowmanxxxx = new BlockPos.Mutable();
         int _snowmanxxxxx = 0;

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 3; _snowmanxxxxxx++) {
            int _snowmanxxxxxxx = pos.getX();
            int _snowmanxxxxxxxx = pos.getZ();
            int _snowmanxxxxxxxxx = 6;
            SpawnSettings.SpawnEntry _snowmanxxxxxxxxxx = null;
            EntityData _snowmanxxxxxxxxxxx = null;
            int _snowmanxxxxxxxxxxxx = MathHelper.ceil(world.random.nextFloat() * 4.0F);
            int _snowmanxxxxxxxxxxxxx = 0;

            for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxx++) {
               _snowmanxxxxxxx += world.random.nextInt(6) - world.random.nextInt(6);
               _snowmanxxxxxxxx += world.random.nextInt(6) - world.random.nextInt(6);
               _snowmanxxxx.set(_snowmanxxxxxxx, _snowmanxx, _snowmanxxxxxxxx);
               double _snowmanxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxx + 0.5;
               double _snowmanxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxx + 0.5;
               PlayerEntity _snowmanxxxxxxxxxxxxxxxxx = world.getClosestPlayer(_snowmanxxxxxxxxxxxxxxx, (double)_snowmanxx, _snowmanxxxxxxxxxxxxxxxx, -1.0, false);
               if (_snowmanxxxxxxxxxxxxxxxxx != null) {
                  double _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.squaredDistanceTo(_snowmanxxxxxxxxxxxxxxx, (double)_snowmanxx, _snowmanxxxxxxxxxxxxxxxx);
                  if (isAcceptableSpawnPosition(world, chunk, _snowmanxxxx, _snowmanxxxxxxxxxxxxxxxxxx)) {
                     if (_snowmanxxxxxxxxxx == null) {
                        _snowmanxxxxxxxxxx = pickRandomSpawnEntry(world, _snowman, _snowmanx, group, world.random, _snowmanxxxx);
                        if (_snowmanxxxxxxxxxx == null) {
                           break;
                        }

                        _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx.minGroupSize + world.random.nextInt(1 + _snowmanxxxxxxxxxx.maxGroupSize - _snowmanxxxxxxxxxx.minGroupSize);
                     }

                     if (canSpawn(world, group, _snowman, _snowmanx, _snowmanxxxxxxxxxx, _snowmanxxxx, _snowmanxxxxxxxxxxxxxxxxxx) && checker.test(_snowmanxxxxxxxxxx.type, _snowmanxxxx, chunk)) {
                        MobEntity _snowmanxxxxxxxxxxxxxxxxxxx = createMob(world, _snowmanxxxxxxxxxx.type);
                        if (_snowmanxxxxxxxxxxxxxxxxxxx == null) {
                           return;
                        }

                        _snowmanxxxxxxxxxxxxxxxxxxx.refreshPositionAndAngles(_snowmanxxxxxxxxxxxxxxx, (double)_snowmanxx, _snowmanxxxxxxxxxxxxxxxx, world.random.nextFloat() * 360.0F, 0.0F);
                        if (isValidSpawn(world, _snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx)) {
                           _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.initialize(
                              world, world.getLocalDifficulty(_snowmanxxxxxxxxxxxxxxxxxxx.getBlockPos()), SpawnReason.NATURAL, _snowmanxxxxxxxxxxx, null
                           );
                           _snowmanxxxxx++;
                           _snowmanxxxxxxxxxxxxx++;
                           world.spawnEntityAndPassengers(_snowmanxxxxxxxxxxxxxxxxxxx);
                           runner.run(_snowmanxxxxxxxxxxxxxxxxxxx, chunk);
                           if (_snowmanxxxxx >= _snowmanxxxxxxxxxxxxxxxxxxx.getLimitPerChunk()) {
                              return;
                           }

                           if (_snowmanxxxxxxxxxxxxxxxxxxx.spawnsTooManyForEachTry(_snowmanxxxxxxxxxxxxx)) {
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean isAcceptableSpawnPosition(ServerWorld world, Chunk chunk, BlockPos.Mutable pos, double squaredDistance) {
      if (squaredDistance <= 576.0) {
         return false;
      } else if (world.getSpawnPos().isWithinDistance(new Vec3d((double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5), 24.0)) {
         return false;
      } else {
         ChunkPos _snowman = new ChunkPos(pos);
         return Objects.equals(_snowman, chunk.getPos()) || world.getChunkManager().shouldTickChunk(_snowman);
      }
   }

   private static boolean canSpawn(
      ServerWorld world,
      SpawnGroup group,
      StructureAccessor structureAccessor,
      ChunkGenerator chunkGenerator,
      SpawnSettings.SpawnEntry spawnEntry,
      BlockPos.Mutable pos,
      double squaredDistance
   ) {
      EntityType<?> _snowman = spawnEntry.type;
      if (_snowman.getSpawnGroup() == SpawnGroup.MISC) {
         return false;
      } else if (!_snowman.isSpawnableFarFromPlayer()
         && squaredDistance > (double)(_snowman.getSpawnGroup().getImmediateDespawnRange() * _snowman.getSpawnGroup().getImmediateDespawnRange())) {
         return false;
      } else if (_snowman.isSummonable() && containsSpawnEntry(world, structureAccessor, chunkGenerator, group, spawnEntry, pos)) {
         SpawnRestriction.Location _snowmanx = SpawnRestriction.getLocation(_snowman);
         if (!canSpawn(_snowmanx, world, pos, _snowman)) {
            return false;
         } else {
            return !SpawnRestriction.canSpawn(_snowman, world, SpawnReason.NATURAL, pos, world.random)
               ? false
               : world.isSpaceEmpty(_snowman.createSimpleBoundingBox((double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5));
         }
      } else {
         return false;
      }
   }

   @Nullable
   private static MobEntity createMob(ServerWorld world, EntityType<?> type) {
      try {
         Entity _snowman = type.create(world);
         if (!(_snowman instanceof MobEntity)) {
            throw new IllegalStateException("Trying to spawn a non-mob: " + Registry.ENTITY_TYPE.getId(type));
         } else {
            return (MobEntity)_snowman;
         }
      } catch (Exception var4) {
         LOGGER.warn("Failed to create mob", var4);
         return null;
      }
   }

   private static boolean isValidSpawn(ServerWorld world, MobEntity entity, double squaredDistance) {
      return squaredDistance
               > (double)(entity.getType().getSpawnGroup().getImmediateDespawnRange() * entity.getType().getSpawnGroup().getImmediateDespawnRange())
            && entity.canImmediatelyDespawn(squaredDistance)
         ? false
         : entity.canSpawn(world, SpawnReason.NATURAL) && entity.canSpawn(world);
   }

   @Nullable
   private static SpawnSettings.SpawnEntry pickRandomSpawnEntry(ServerWorld _snowman, StructureAccessor _snowman, ChunkGenerator _snowman, SpawnGroup _snowman, Random _snowman, BlockPos _snowman) {
      Biome _snowmanxxxxxx = _snowman.getBiome(_snowman);
      if (_snowman == SpawnGroup.WATER_AMBIENT && _snowmanxxxxxx.getCategory() == Biome.Category.RIVER && _snowman.nextFloat() < 0.98F) {
         return null;
      } else {
         List<SpawnSettings.SpawnEntry> _snowmanxxxxxxx = method_29950(_snowman, _snowman, _snowman, _snowman, _snowman, _snowmanxxxxxx);
         return _snowmanxxxxxxx.isEmpty() ? null : WeightedPicker.getRandom(_snowman, _snowmanxxxxxxx);
      }
   }

   private static boolean containsSpawnEntry(ServerWorld _snowman, StructureAccessor _snowman, ChunkGenerator _snowman, SpawnGroup _snowman, SpawnSettings.SpawnEntry _snowman, BlockPos _snowman) {
      return method_29950(_snowman, _snowman, _snowman, _snowman, _snowman, null).contains(_snowman);
   }

   private static List<SpawnSettings.SpawnEntry> method_29950(ServerWorld _snowman, StructureAccessor _snowman, ChunkGenerator _snowman, SpawnGroup _snowman, BlockPos _snowman, @Nullable Biome _snowman) {
      return _snowman == SpawnGroup.MONSTER
            && _snowman.getBlockState(_snowman.down()).getBlock() == Blocks.NETHER_BRICKS
            && _snowman.getStructureAt(_snowman, false, StructureFeature.FORTRESS).hasChildren()
         ? StructureFeature.FORTRESS.getMonsterSpawns()
         : _snowman.getEntitySpawnList(_snowman != null ? _snowman : _snowman.getBiome(_snowman), _snowman, _snowman, _snowman);
   }

   private static BlockPos getSpawnPos(World world, WorldChunk chunk) {
      ChunkPos _snowman = chunk.getPos();
      int _snowmanx = _snowman.getStartX() + world.random.nextInt(16);
      int _snowmanxx = _snowman.getStartZ() + world.random.nextInt(16);
      int _snowmanxxx = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, _snowmanx, _snowmanxx) + 1;
      int _snowmanxxxx = world.random.nextInt(_snowmanxxx + 1);
      return new BlockPos(_snowmanx, _snowmanxxxx, _snowmanxx);
   }

   public static boolean isClearForSpawn(BlockView blockView, BlockPos pos, BlockState state, FluidState fluidState, EntityType<?> _snowman) {
      if (state.isFullCube(blockView, pos)) {
         return false;
      } else if (state.emitsRedstonePower()) {
         return false;
      } else if (!fluidState.isEmpty()) {
         return false;
      } else {
         return state.isIn(BlockTags.PREVENT_MOB_SPAWNING_INSIDE) ? false : !_snowman.isInvalidSpawn(state);
      }
   }

   public static boolean canSpawn(SpawnRestriction.Location location, WorldView world, BlockPos pos, @Nullable EntityType<?> entityType) {
      if (location == SpawnRestriction.Location.NO_RESTRICTIONS) {
         return true;
      } else if (entityType != null && world.getWorldBorder().contains(pos)) {
         BlockState _snowman = world.getBlockState(pos);
         FluidState _snowmanx = world.getFluidState(pos);
         BlockPos _snowmanxx = pos.up();
         BlockPos _snowmanxxx = pos.down();
         switch (location) {
            case IN_WATER:
               return _snowmanx.isIn(FluidTags.WATER) && world.getFluidState(_snowmanxxx).isIn(FluidTags.WATER) && !world.getBlockState(_snowmanxx).isSolidBlock(world, _snowmanxx);
            case IN_LAVA:
               return _snowmanx.isIn(FluidTags.LAVA);
            case ON_GROUND:
            default:
               BlockState _snowmanxxxx = world.getBlockState(_snowmanxxx);
               return !_snowmanxxxx.allowsSpawning(world, _snowmanxxx, entityType)
                  ? false
                  : isClearForSpawn(world, pos, _snowman, _snowmanx, entityType)
                     && isClearForSpawn(world, _snowmanxx, world.getBlockState(_snowmanxx), world.getFluidState(_snowmanxx), entityType);
         }
      } else {
         return false;
      }
   }

   public static void populateEntities(ServerWorldAccess _snowman, Biome biome, int chunkX, int chunkZ, Random _snowman) {
      SpawnSettings _snowmanxx = biome.getSpawnSettings();
      List<SpawnSettings.SpawnEntry> _snowmanxxx = _snowmanxx.getSpawnEntry(SpawnGroup.CREATURE);
      if (!_snowmanxxx.isEmpty()) {
         int _snowmanxxxx = chunkX << 4;
         int _snowmanxxxxx = chunkZ << 4;

         while (_snowman.nextFloat() < _snowmanxx.getCreatureSpawnProbability()) {
            SpawnSettings.SpawnEntry _snowmanxxxxxx = WeightedPicker.getRandom(_snowman, _snowmanxxx);
            int _snowmanxxxxxxx = _snowmanxxxxxx.minGroupSize + _snowman.nextInt(1 + _snowmanxxxxxx.maxGroupSize - _snowmanxxxxxx.minGroupSize);
            EntityData _snowmanxxxxxxxx = null;
            int _snowmanxxxxxxxxx = _snowmanxxxx + _snowman.nextInt(16);
            int _snowmanxxxxxxxxxx = _snowmanxxxxx + _snowman.nextInt(16);
            int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx;
            int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx;

            for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < _snowmanxxxxxxx; _snowmanxxxxxxxxxxxxx++) {
               boolean _snowmanxxxxxxxxxxxxxx = false;

               for (int _snowmanxxxxxxxxxxxxxxx = 0; !_snowmanxxxxxxxxxxxxxx && _snowmanxxxxxxxxxxxxxxx < 4; _snowmanxxxxxxxxxxxxxxx++) {
                  BlockPos _snowmanxxxxxxxxxxxxxxxx = getEntitySpawnPos(_snowman, _snowmanxxxxxx.type, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
                  if (_snowmanxxxxxx.type.isSummonable() && canSpawn(SpawnRestriction.getLocation(_snowmanxxxxxx.type), _snowman, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxx.type)) {
                     float _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxx.type.getWidth();
                     double _snowmanxxxxxxxxxxxxxxxxxx = MathHelper.clamp(
                        (double)_snowmanxxxxxxxxx, (double)_snowmanxxxx + (double)_snowmanxxxxxxxxxxxxxxxxx, (double)_snowmanxxxx + 16.0 - (double)_snowmanxxxxxxxxxxxxxxxxx
                     );
                     double _snowmanxxxxxxxxxxxxxxxxxxx = MathHelper.clamp(
                        (double)_snowmanxxxxxxxxxx, (double)_snowmanxxxxx + (double)_snowmanxxxxxxxxxxxxxxxxx, (double)_snowmanxxxxx + 16.0 - (double)_snowmanxxxxxxxxxxxxxxxxx
                     );
                     if (!_snowman.isSpaceEmpty(_snowmanxxxxxx.type.createSimpleBoundingBox(_snowmanxxxxxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxxxx.getY(), _snowmanxxxxxxxxxxxxxxxxxxx))
                        || !SpawnRestriction.canSpawn(
                           _snowmanxxxxxx.type,
                           _snowman,
                           SpawnReason.CHUNK_GENERATION,
                           new BlockPos(_snowmanxxxxxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxxxx.getY(), _snowmanxxxxxxxxxxxxxxxxxxx),
                           _snowman.getRandom()
                        )) {
                        continue;
                     }

                     Entity _snowmanxxxxxxxxxxxxxxxxxxxx;
                     try {
                        _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx.type.create(_snowman.toServerWorld());
                     } catch (Exception var27) {
                        LOGGER.warn("Failed to create mob", var27);
                        continue;
                     }

                     _snowmanxxxxxxxxxxxxxxxxxxxx.refreshPositionAndAngles(
                        _snowmanxxxxxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxxxx.getY(), _snowmanxxxxxxxxxxxxxxxxxxx, _snowman.nextFloat() * 360.0F, 0.0F
                     );
                     if (_snowmanxxxxxxxxxxxxxxxxxxxx instanceof MobEntity) {
                        MobEntity _snowmanxxxxxxxxxxxxxxxxxxxxx = (MobEntity)_snowmanxxxxxxxxxxxxxxxxxxxx;
                        if (_snowmanxxxxxxxxxxxxxxxxxxxxx.canSpawn(_snowman, SpawnReason.CHUNK_GENERATION) && _snowmanxxxxxxxxxxxxxxxxxxxxx.canSpawn(_snowman)) {
                           _snowmanxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx.initialize(
                              _snowman, _snowman.getLocalDifficulty(_snowmanxxxxxxxxxxxxxxxxxxxxx.getBlockPos()), SpawnReason.CHUNK_GENERATION, _snowmanxxxxxxxx, null
                           );
                           _snowman.spawnEntityAndPassengers(_snowmanxxxxxxxxxxxxxxxxxxxxx);
                           _snowmanxxxxxxxxxxxxxx = true;
                        }
                     }
                  }

                  _snowmanxxxxxxxxx += _snowman.nextInt(5) - _snowman.nextInt(5);

                  for (_snowmanxxxxxxxxxx += _snowman.nextInt(5) - _snowman.nextInt(5);
                     _snowmanxxxxxxxxx < _snowmanxxxx || _snowmanxxxxxxxxx >= _snowmanxxxx + 16 || _snowmanxxxxxxxxxx < _snowmanxxxxx || _snowmanxxxxxxxxxx >= _snowmanxxxxx + 16;
                     _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxx + _snowman.nextInt(5) - _snowman.nextInt(5)
                  ) {
                     _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxx + _snowman.nextInt(5) - _snowman.nextInt(5);
                  }
               }
            }
         }
      }
   }

   private static BlockPos getEntitySpawnPos(WorldView world, EntityType<?> entityType, int x, int z) {
      int _snowman = world.getTopY(SpawnRestriction.getHeightmapType(entityType), x, z);
      BlockPos.Mutable _snowmanx = new BlockPos.Mutable(x, _snowman, z);
      if (world.getDimension().hasCeiling()) {
         do {
            _snowmanx.move(Direction.DOWN);
         } while (!world.getBlockState(_snowmanx).isAir());

         do {
            _snowmanx.move(Direction.DOWN);
         } while (world.getBlockState(_snowmanx).isAir() && _snowmanx.getY() > 0);
      }

      if (SpawnRestriction.getLocation(entityType) == SpawnRestriction.Location.ON_GROUND) {
         BlockPos _snowmanxx = _snowmanx.down();
         if (world.getBlockState(_snowmanxx).canPathfindThrough(world, _snowmanxx, NavigationType.LAND)) {
            return _snowmanxx;
         }
      }

      return _snowmanx.toImmutable();
   }

   @FunctionalInterface
   public interface Checker {
      boolean test(EntityType<?> type, BlockPos pos, Chunk chunk);
   }

   @FunctionalInterface
   public interface ChunkSource {
      void query(long pos, Consumer<WorldChunk> chunkConsumer);
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
         SpawnSettings.SpawnDensity _snowman = SpawnHelper.getBiomeDirectly(pos, chunk).getSpawnSettings().getSpawnDensity(type);
         if (_snowman == null) {
            this.cachedDensityMass = 0.0;
            return true;
         } else {
            double _snowmanx = _snowman.getMass();
            this.cachedDensityMass = _snowmanx;
            double _snowmanxx = this.densityField.calculate(pos, _snowmanx);
            return _snowmanxx <= _snowman.getGravityLimit();
         }
      }

      private void run(MobEntity entity, Chunk chunk) {
         EntityType<?> _snowman = entity.getType();
         BlockPos _snowmanx = entity.getBlockPos();
         double _snowmanxx;
         if (_snowmanx.equals(this.cachedPos) && _snowman == this.cachedEntityType) {
            _snowmanxx = this.cachedDensityMass;
         } else {
            SpawnSettings.SpawnDensity _snowmanxxx = SpawnHelper.getBiomeDirectly(_snowmanx, chunk).getSpawnSettings().getSpawnDensity(_snowman);
            if (_snowmanxxx != null) {
               _snowmanxx = _snowmanxxx.getMass();
            } else {
               _snowmanxx = 0.0;
            }
         }

         this.densityField.addPoint(_snowmanx, _snowmanxx);
         this.groupToCount.addTo(_snowman.getSpawnGroup(), 1);
      }

      public int getSpawningChunkCount() {
         return this.spawningChunkCount;
      }

      public Object2IntMap<SpawnGroup> getGroupToCount() {
         return this.groupToCountView;
      }

      private boolean isBelowCap(SpawnGroup group) {
         int _snowman = group.getCapacity() * this.spawningChunkCount / SpawnHelper.CHUNK_AREA;
         return this.groupToCount.getInt(group) < _snowman;
      }
   }

   @FunctionalInterface
   public interface Runner {
      void run(MobEntity entity, Chunk chunk);
   }
}
