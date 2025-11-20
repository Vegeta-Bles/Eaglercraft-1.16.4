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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
   private static final SpawnGroup[] SPAWNABLE_GROUPS = Stream.of(SpawnGroup.values()).filter(arg -> arg != SpawnGroup.MISC).toArray(SpawnGroup[]::new);

   public static SpawnHelper.Info setupSpawn(int spawningChunkCount, Iterable<Entity> entities, SpawnHelper.ChunkSource chunkSource) {
      GravityField lv = new GravityField();
      Object2IntOpenHashMap<SpawnGroup> object2IntOpenHashMap = new Object2IntOpenHashMap();

      for (Entity lv2 : entities) {
         if (lv2 instanceof MobEntity) {
            MobEntity lv3 = (MobEntity)lv2;
            if (lv3.isPersistent() || lv3.cannotDespawn()) {
               continue;
            }
         }

         SpawnGroup lv4 = lv2.getType().getSpawnGroup();
         if (lv4 != SpawnGroup.MISC) {
            BlockPos lv5 = lv2.getBlockPos();
            long l = ChunkPos.toLong(lv5.getX() >> 4, lv5.getZ() >> 4);
            chunkSource.query(l, arg5 -> {
               SpawnSettings.SpawnDensity lvx = getBiomeDirectly(lv5, arg5).getSpawnSettings().getSpawnDensity(lv2.getType());
               if (lvx != null) {
                  lv.addPoint(lv2.getBlockPos(), lvx.getMass());
               }

               object2IntOpenHashMap.addTo(lv4, 1);
            });
         }
      }

      return new SpawnHelper.Info(spawningChunkCount, object2IntOpenHashMap, lv);
   }

   private static Biome getBiomeDirectly(BlockPos pos, Chunk chunk) {
      return DirectBiomeAccessType.INSTANCE.getBiome(0L, pos.getX(), pos.getY(), pos.getZ(), chunk.getBiomeArray());
   }

   public static void spawn(ServerWorld world, WorldChunk chunk, SpawnHelper.Info info, boolean spawnAnimals, boolean spawnMonsters, boolean shouldSpawnAnimals) {
      world.getProfiler().push("spawner");

      for (SpawnGroup lv : SPAWNABLE_GROUPS) {
         if ((spawnAnimals || !lv.isPeaceful()) && (spawnMonsters || lv.isPeaceful()) && (shouldSpawnAnimals || !lv.isAnimal()) && info.isBelowCap(lv)) {
            spawnEntitiesInChunk(lv, world, chunk, (arg2, arg3, arg4) -> info.test(arg2, arg3, arg4), (arg2, arg3) -> info.run(arg2, arg3));
         }
      }

      world.getProfiler().pop();
   }

   public static void spawnEntitiesInChunk(SpawnGroup group, ServerWorld world, WorldChunk chunk, SpawnHelper.Checker checker, SpawnHelper.Runner runner) {
      BlockPos lv = getSpawnPos(world, chunk);
      if (lv.getY() >= 1) {
         spawnEntitiesInChunk(group, world, chunk, lv, checker, runner);
      }
   }

   public static void spawnEntitiesInChunk(
      SpawnGroup group, ServerWorld world, Chunk chunk, BlockPos pos, SpawnHelper.Checker checker, SpawnHelper.Runner runner
   ) {
      StructureAccessor lv = world.getStructureAccessor();
      ChunkGenerator lv2 = world.getChunkManager().getChunkGenerator();
      int i = pos.getY();
      BlockState lv3 = chunk.getBlockState(pos);
      if (!lv3.isSolidBlock(chunk, pos)) {
         BlockPos.Mutable lv4 = new BlockPos.Mutable();
         int j = 0;

         for (int k = 0; k < 3; k++) {
            int l = pos.getX();
            int m = pos.getZ();
            int n = 6;
            SpawnSettings.SpawnEntry lv5 = null;
            EntityData lv6 = null;
            int o = MathHelper.ceil(world.random.nextFloat() * 4.0F);
            int p = 0;

            for (int q = 0; q < o; q++) {
               l += world.random.nextInt(6) - world.random.nextInt(6);
               m += world.random.nextInt(6) - world.random.nextInt(6);
               lv4.set(l, i, m);
               double d = (double)l + 0.5;
               double e = (double)m + 0.5;
               PlayerEntity lv7 = world.getClosestPlayer(d, (double)i, e, -1.0, false);
               if (lv7 != null) {
                  double f = lv7.squaredDistanceTo(d, (double)i, e);
                  if (isAcceptableSpawnPosition(world, chunk, lv4, f)) {
                     if (lv5 == null) {
                        lv5 = pickRandomSpawnEntry(world, lv, lv2, group, world.random, lv4);
                        if (lv5 == null) {
                           break;
                        }

                        o = lv5.minGroupSize + world.random.nextInt(1 + lv5.maxGroupSize - lv5.minGroupSize);
                     }

                     if (canSpawn(world, group, lv, lv2, lv5, lv4, f) && checker.test(lv5.type, lv4, chunk)) {
                        MobEntity lv8 = createMob(world, lv5.type);
                        if (lv8 == null) {
                           return;
                        }

                        lv8.refreshPositionAndAngles(d, (double)i, e, world.random.nextFloat() * 360.0F, 0.0F);
                        if (isValidSpawn(world, lv8, f)) {
                           lv6 = lv8.initialize(world, world.getLocalDifficulty(lv8.getBlockPos()), SpawnReason.NATURAL, lv6, null);
                           j++;
                           p++;
                           world.spawnEntityAndPassengers(lv8);
                           runner.run(lv8, chunk);
                           if (j >= lv8.getLimitPerChunk()) {
                              return;
                           }

                           if (lv8.spawnsTooManyForEachTry(p)) {
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
         ChunkPos lv = new ChunkPos(pos);
         return Objects.equals(lv, chunk.getPos()) || world.getChunkManager().shouldTickChunk(lv);
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
      EntityType<?> lv = spawnEntry.type;
      if (lv.getSpawnGroup() == SpawnGroup.MISC) {
         return false;
      } else if (!lv.isSpawnableFarFromPlayer()
         && squaredDistance > (double)(lv.getSpawnGroup().getImmediateDespawnRange() * lv.getSpawnGroup().getImmediateDespawnRange())) {
         return false;
      } else if (lv.isSummonable() && containsSpawnEntry(world, structureAccessor, chunkGenerator, group, spawnEntry, pos)) {
         SpawnRestriction.Location lv2 = SpawnRestriction.getLocation(lv);
         if (!canSpawn(lv2, world, pos, lv)) {
            return false;
         } else {
            return !SpawnRestriction.canSpawn(lv, world, SpawnReason.NATURAL, pos, world.random)
               ? false
               : world.isSpaceEmpty(lv.createSimpleBoundingBox((double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5));
         }
      } else {
         return false;
      }
   }

   @Nullable
   private static MobEntity createMob(ServerWorld world, EntityType<?> type) {
      try {
         Entity lv = type.create(world);
         if (!(lv instanceof MobEntity)) {
            throw new IllegalStateException("Trying to spawn a non-mob: " + Registry.ENTITY_TYPE.getId(type));
         } else {
            return (MobEntity)lv;
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
   private static SpawnSettings.SpawnEntry pickRandomSpawnEntry(
      ServerWorld arg, StructureAccessor arg2, ChunkGenerator arg3, SpawnGroup arg4, Random random, BlockPos arg5
   ) {
      Biome lv = arg.getBiome(arg5);
      if (arg4 == SpawnGroup.WATER_AMBIENT && lv.getCategory() == Biome.Category.RIVER && random.nextFloat() < 0.98F) {
         return null;
      } else {
         List<SpawnSettings.SpawnEntry> list = method_29950(arg, arg2, arg3, arg4, arg5, lv);
         return list.isEmpty() ? null : WeightedPicker.getRandom(random, list);
      }
   }

   private static boolean containsSpawnEntry(
      ServerWorld arg, StructureAccessor arg2, ChunkGenerator arg3, SpawnGroup arg4, SpawnSettings.SpawnEntry arg5, BlockPos arg6
   ) {
      return method_29950(arg, arg2, arg3, arg4, arg6, null).contains(arg5);
   }

   private static List<SpawnSettings.SpawnEntry> method_29950(
      ServerWorld arg, StructureAccessor arg2, ChunkGenerator arg3, SpawnGroup arg4, BlockPos arg5, @Nullable Biome arg6
   ) {
      return arg4 == SpawnGroup.MONSTER
            && arg.getBlockState(arg5.down()).getBlock() == Blocks.NETHER_BRICKS
            && arg2.getStructureAt(arg5, false, StructureFeature.FORTRESS).hasChildren()
         ? StructureFeature.FORTRESS.getMonsterSpawns()
         : arg3.getEntitySpawnList(arg6 != null ? arg6 : arg.getBiome(arg5), arg2, arg4, arg5);
   }

   private static BlockPos getSpawnPos(World world, WorldChunk chunk) {
      ChunkPos lv = chunk.getPos();
      int i = lv.getStartX() + world.random.nextInt(16);
      int j = lv.getStartZ() + world.random.nextInt(16);
      int k = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, i, j) + 1;
      int l = world.random.nextInt(k + 1);
      return new BlockPos(i, l, j);
   }

   public static boolean isClearForSpawn(BlockView blockView, BlockPos pos, BlockState state, FluidState fluidState, EntityType<?> arg5) {
      if (state.isFullCube(blockView, pos)) {
         return false;
      } else if (state.emitsRedstonePower()) {
         return false;
      } else if (!fluidState.isEmpty()) {
         return false;
      } else {
         return state.isIn(BlockTags.PREVENT_MOB_SPAWNING_INSIDE) ? false : !arg5.isInvalidSpawn(state);
      }
   }

   public static boolean canSpawn(SpawnRestriction.Location location, WorldView world, BlockPos pos, @Nullable EntityType<?> entityType) {
      if (location == SpawnRestriction.Location.NO_RESTRICTIONS) {
         return true;
      } else if (entityType != null && world.getWorldBorder().contains(pos)) {
         BlockState lv = world.getBlockState(pos);
         FluidState lv2 = world.getFluidState(pos);
         BlockPos lv3 = pos.up();
         BlockPos lv4 = pos.down();
         switch (location) {
            case IN_WATER:
               return lv2.isIn(FluidTags.WATER) && world.getFluidState(lv4).isIn(FluidTags.WATER) && !world.getBlockState(lv3).isSolidBlock(world, lv3);
            case IN_LAVA:
               return lv2.isIn(FluidTags.LAVA);
            case ON_GROUND:
            default:
               BlockState lv5 = world.getBlockState(lv4);
               return !lv5.allowsSpawning(world, lv4, entityType)
                  ? false
                  : isClearForSpawn(world, pos, lv, lv2, entityType)
                     && isClearForSpawn(world, lv3, world.getBlockState(lv3), world.getFluidState(lv3), entityType);
         }
      } else {
         return false;
      }
   }

   public static void populateEntities(ServerWorldAccess arg, Biome biome, int chunkX, int chunkZ, Random random) {
      SpawnSettings lv = biome.getSpawnSettings();
      List<SpawnSettings.SpawnEntry> list = lv.getSpawnEntry(SpawnGroup.CREATURE);
      if (!list.isEmpty()) {
         int k = chunkX << 4;
         int l = chunkZ << 4;

         while (random.nextFloat() < lv.getCreatureSpawnProbability()) {
            SpawnSettings.SpawnEntry lv2 = WeightedPicker.getRandom(random, list);
            int m = lv2.minGroupSize + random.nextInt(1 + lv2.maxGroupSize - lv2.minGroupSize);
            EntityData lv3 = null;
            int n = k + random.nextInt(16);
            int o = l + random.nextInt(16);
            int p = n;
            int q = o;

            for (int r = 0; r < m; r++) {
               boolean bl = false;

               for (int s = 0; !bl && s < 4; s++) {
                  BlockPos lv4 = getEntitySpawnPos(arg, lv2.type, n, o);
                  if (lv2.type.isSummonable() && canSpawn(SpawnRestriction.getLocation(lv2.type), arg, lv4, lv2.type)) {
                     float f = lv2.type.getWidth();
                     double d = MathHelper.clamp((double)n, (double)k + (double)f, (double)k + 16.0 - (double)f);
                     double e = MathHelper.clamp((double)o, (double)l + (double)f, (double)l + 16.0 - (double)f);
                     if (!arg.isSpaceEmpty(lv2.type.createSimpleBoundingBox(d, (double)lv4.getY(), e))
                        || !SpawnRestriction.canSpawn(lv2.type, arg, SpawnReason.CHUNK_GENERATION, new BlockPos(d, (double)lv4.getY(), e), arg.getRandom())) {
                        continue;
                     }

                     Entity lv5;
                     try {
                        lv5 = lv2.type.create(arg.toServerWorld());
                     } catch (Exception var27) {
                        LOGGER.warn("Failed to create mob", var27);
                        continue;
                     }

                     lv5.refreshPositionAndAngles(d, (double)lv4.getY(), e, random.nextFloat() * 360.0F, 0.0F);
                     if (lv5 instanceof MobEntity) {
                        MobEntity lv7 = (MobEntity)lv5;
                        if (lv7.canSpawn(arg, SpawnReason.CHUNK_GENERATION) && lv7.canSpawn(arg)) {
                           lv3 = lv7.initialize(arg, arg.getLocalDifficulty(lv7.getBlockPos()), SpawnReason.CHUNK_GENERATION, lv3, null);
                           arg.spawnEntityAndPassengers(lv7);
                           bl = true;
                        }
                     }
                  }

                  n += random.nextInt(5) - random.nextInt(5);

                  for (o += random.nextInt(5) - random.nextInt(5); n < k || n >= k + 16 || o < l || o >= l + 16; o = q + random.nextInt(5) - random.nextInt(5)) {
                     n = p + random.nextInt(5) - random.nextInt(5);
                  }
               }
            }
         }
      }
   }

   private static BlockPos getEntitySpawnPos(WorldView world, EntityType<?> entityType, int x, int z) {
      int k = world.getTopY(SpawnRestriction.getHeightmapType(entityType), x, z);
      BlockPos.Mutable lv = new BlockPos.Mutable(x, k, z);
      if (world.getDimension().hasCeiling()) {
         do {
            lv.move(Direction.DOWN);
         } while (!world.getBlockState(lv).isAir());

         do {
            lv.move(Direction.DOWN);
         } while (world.getBlockState(lv).isAir() && lv.getY() > 0);
      }

      if (SpawnRestriction.getLocation(entityType) == SpawnRestriction.Location.ON_GROUND) {
         BlockPos lv2 = lv.down();
         if (world.getBlockState(lv2).canPathfindThrough(world, lv2, NavigationType.LAND)) {
            return lv2;
         }
      }

      return lv.toImmutable();
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
         SpawnSettings.SpawnDensity lv = SpawnHelper.getBiomeDirectly(pos, chunk).getSpawnSettings().getSpawnDensity(type);
         if (lv == null) {
            this.cachedDensityMass = 0.0;
            return true;
         } else {
            double d = lv.getMass();
            this.cachedDensityMass = d;
            double e = this.densityField.calculate(pos, d);
            return e <= lv.getGravityLimit();
         }
      }

      private void run(MobEntity entity, Chunk chunk) {
         EntityType<?> lv = entity.getType();
         BlockPos lv2 = entity.getBlockPos();
         double d;
         if (lv2.equals(this.cachedPos) && lv == this.cachedEntityType) {
            d = this.cachedDensityMass;
         } else {
            SpawnSettings.SpawnDensity lv3 = SpawnHelper.getBiomeDirectly(lv2, chunk).getSpawnSettings().getSpawnDensity(lv);
            if (lv3 != null) {
               d = lv3.getMass();
            } else {
               d = 0.0;
            }
         }

         this.densityField.addPoint(lv2, d);
         this.groupToCount.addTo(lv.getSpawnGroup(), 1);
      }

      @Environment(EnvType.CLIENT)
      public int getSpawningChunkCount() {
         return this.spawningChunkCount;
      }

      public Object2IntMap<SpawnGroup> getGroupToCount() {
         return this.groupToCountView;
      }

      private boolean isBelowCap(SpawnGroup group) {
         int i = group.getCapacity() * this.spawningChunkCount / SpawnHelper.CHUNK_AREA;
         return this.groupToCount.getInt(group) < i;
      }
   }

   @FunctionalInterface
   public interface Runner {
      void run(MobEntity entity, Chunk chunk);
   }
}
