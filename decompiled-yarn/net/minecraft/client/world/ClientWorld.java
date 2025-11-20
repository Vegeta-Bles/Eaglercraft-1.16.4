package net.minecraft.client.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.sound.EntityTrackingSoundInstance;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagManager;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.CuboidBlockIterator;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.TickScheduler;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.ColorResolver;

public class ClientWorld extends World {
   private final Int2ObjectMap<Entity> regularEntities = new Int2ObjectOpenHashMap();
   private final ClientPlayNetworkHandler netHandler;
   private final WorldRenderer worldRenderer;
   private final ClientWorld.Properties clientWorldProperties;
   private final SkyProperties skyProperties;
   private final MinecraftClient client = MinecraftClient.getInstance();
   private final List<AbstractClientPlayerEntity> players = Lists.newArrayList();
   private Scoreboard scoreboard = new Scoreboard();
   private final Map<String, MapState> mapStates = Maps.newHashMap();
   private int lightningTicksLeft;
   private final Object2ObjectArrayMap<ColorResolver, BiomeColorCache> colorCache = Util.make(new Object2ObjectArrayMap(3), cache -> {
      cache.put(BiomeColors.GRASS_COLOR, new BiomeColorCache());
      cache.put(BiomeColors.FOLIAGE_COLOR, new BiomeColorCache());
      cache.put(BiomeColors.WATER_COLOR, new BiomeColorCache());
   });
   private final ClientChunkManager chunkManager;

   public ClientWorld(
      ClientPlayNetworkHandler networkHandler,
      ClientWorld.Properties properties,
      RegistryKey<World> registryRef,
      DimensionType dimensionType,
      int loadDistance,
      Supplier<Profiler> profiler,
      WorldRenderer worldRenderer,
      boolean debugWorld,
      long seed
   ) {
      super(properties, registryRef, dimensionType, profiler, true, debugWorld, seed);
      this.netHandler = networkHandler;
      this.chunkManager = new ClientChunkManager(this, loadDistance);
      this.clientWorldProperties = properties;
      this.worldRenderer = worldRenderer;
      this.skyProperties = SkyProperties.byDimensionType(dimensionType);
      this.setSpawnPos(new BlockPos(8, 64, 8), 0.0F);
      this.calculateAmbientDarkness();
      this.initWeatherGradients();
   }

   public SkyProperties getSkyProperties() {
      return this.skyProperties;
   }

   public void tick(BooleanSupplier shouldKeepTicking) {
      this.getWorldBorder().tick();
      this.tickTime();
      this.getProfiler().push("blocks");
      this.chunkManager.tick(shouldKeepTicking);
      this.getProfiler().pop();
   }

   private void tickTime() {
      this.method_29089(this.properties.getTime() + 1L);
      if (this.properties.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
         this.setTimeOfDay(this.properties.getTimeOfDay() + 1L);
      }
   }

   public void method_29089(long _snowman) {
      this.clientWorldProperties.setTime(_snowman);
   }

   public void setTimeOfDay(long _snowman) {
      if (_snowman < 0L) {
         _snowman = -_snowman;
         this.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(false, null);
      } else {
         this.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(true, null);
      }

      this.clientWorldProperties.setTimeOfDay(_snowman);
   }

   public Iterable<Entity> getEntities() {
      return this.regularEntities.values();
   }

   public void tickEntities() {
      Profiler _snowman = this.getProfiler();
      _snowman.push("entities");
      ObjectIterator<Entry<Entity>> _snowmanx = this.regularEntities.int2ObjectEntrySet().iterator();

      while (_snowmanx.hasNext()) {
         Entry<Entity> _snowmanxx = (Entry<Entity>)_snowmanx.next();
         Entity _snowmanxxx = (Entity)_snowmanxx.getValue();
         if (!_snowmanxxx.hasVehicle()) {
            _snowman.push("tick");
            if (!_snowmanxxx.removed) {
               this.tickEntity(this::tickEntity, _snowmanxxx);
            }

            _snowman.pop();
            _snowman.push("remove");
            if (_snowmanxxx.removed) {
               _snowmanx.remove();
               this.finishRemovingEntity(_snowmanxxx);
            }

            _snowman.pop();
         }
      }

      this.tickBlockEntities();
      _snowman.pop();
   }

   public void tickEntity(Entity entity) {
      if (!(entity instanceof PlayerEntity) && !this.getChunkManager().shouldTickEntity(entity)) {
         this.checkEntityChunkPos(entity);
      } else {
         entity.resetPosition(entity.getX(), entity.getY(), entity.getZ());
         entity.prevYaw = entity.yaw;
         entity.prevPitch = entity.pitch;
         if (entity.updateNeeded || entity.isSpectator()) {
            entity.age++;
            this.getProfiler().push(() -> Registry.ENTITY_TYPE.getId(entity.getType()).toString());
            entity.tick();
            this.getProfiler().pop();
         }

         this.checkEntityChunkPos(entity);
         if (entity.updateNeeded) {
            for (Entity _snowman : entity.getPassengerList()) {
               this.tickPassenger(entity, _snowman);
            }
         }
      }
   }

   public void tickPassenger(Entity entity, Entity passenger) {
      if (passenger.removed || passenger.getVehicle() != entity) {
         passenger.stopRiding();
      } else if (passenger instanceof PlayerEntity || this.getChunkManager().shouldTickEntity(passenger)) {
         passenger.resetPosition(passenger.getX(), passenger.getY(), passenger.getZ());
         passenger.prevYaw = passenger.yaw;
         passenger.prevPitch = passenger.pitch;
         if (passenger.updateNeeded) {
            passenger.age++;
            passenger.tickRiding();
         }

         this.checkEntityChunkPos(passenger);
         if (passenger.updateNeeded) {
            for (Entity _snowman : passenger.getPassengerList()) {
               this.tickPassenger(passenger, _snowman);
            }
         }
      }
   }

   private void checkEntityChunkPos(Entity entity) {
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

   public void unloadBlockEntities(WorldChunk chunk) {
      this.unloadedBlockEntities.addAll(chunk.getBlockEntities().values());
      this.chunkManager.getLightingProvider().setColumnEnabled(chunk.getPos(), false);
   }

   public void resetChunkColor(int _snowman, int _snowman) {
      this.colorCache.forEach((_snowmanxxxx, _snowmanxxx) -> _snowmanxxx.reset(_snowman, _snowman));
   }

   public void reloadColor() {
      this.colorCache.forEach((_snowman, _snowmanx) -> _snowmanx.reset());
   }

   @Override
   public boolean isChunkLoaded(int chunkX, int chunkZ) {
      return true;
   }

   public int getRegularEntityCount() {
      return this.regularEntities.size();
   }

   public void addPlayer(int id, AbstractClientPlayerEntity player) {
      this.addEntityPrivate(id, player);
      this.players.add(player);
   }

   public void addEntity(int id, Entity entity) {
      this.addEntityPrivate(id, entity);
   }

   private void addEntityPrivate(int id, Entity entity) {
      this.removeEntity(id);
      this.regularEntities.put(id, entity);
      this.getChunkManager().getChunk(MathHelper.floor(entity.getX() / 16.0), MathHelper.floor(entity.getZ() / 16.0), ChunkStatus.FULL, true).addEntity(entity);
   }

   public void removeEntity(int entityId) {
      Entity _snowman = (Entity)this.regularEntities.remove(entityId);
      if (_snowman != null) {
         _snowman.remove();
         this.finishRemovingEntity(_snowman);
      }
   }

   private void finishRemovingEntity(Entity entity) {
      entity.detach();
      if (entity.updateNeeded) {
         this.getChunk(entity.chunkX, entity.chunkZ).remove(entity);
      }

      this.players.remove(entity);
   }

   public void addEntitiesToChunk(WorldChunk chunk) {
      ObjectIterator var2 = this.regularEntities.int2ObjectEntrySet().iterator();

      while (var2.hasNext()) {
         Entry<Entity> _snowman = (Entry<Entity>)var2.next();
         Entity _snowmanx = (Entity)_snowman.getValue();
         int _snowmanxx = MathHelper.floor(_snowmanx.getX() / 16.0);
         int _snowmanxxx = MathHelper.floor(_snowmanx.getZ() / 16.0);
         if (_snowmanxx == chunk.getPos().x && _snowmanxxx == chunk.getPos().z) {
            chunk.addEntity(_snowmanx);
         }
      }
   }

   @Nullable
   @Override
   public Entity getEntityById(int id) {
      return (Entity)this.regularEntities.get(id);
   }

   public void setBlockStateWithoutNeighborUpdates(BlockPos pos, BlockState state) {
      this.setBlockState(pos, state, 19);
   }

   @Override
   public void disconnect() {
      this.netHandler.getConnection().disconnect(new TranslatableText("multiplayer.status.quitting"));
   }

   public void doRandomBlockDisplayTicks(int xCenter, int yCenter, int zCenter) {
      int _snowman = 32;
      Random _snowmanx = new Random();
      boolean _snowmanxx = false;
      if (this.client.interactionManager.getCurrentGameMode() == GameMode.CREATIVE) {
         for (ItemStack _snowmanxxx : this.client.player.getItemsHand()) {
            if (_snowmanxxx.getItem() == Blocks.BARRIER.asItem()) {
               _snowmanxx = true;
               break;
            }
         }
      }

      BlockPos.Mutable _snowmanxxxx = new BlockPos.Mutable();

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < 667; _snowmanxxxxx++) {
         this.randomBlockDisplayTick(xCenter, yCenter, zCenter, 16, _snowmanx, _snowmanxx, _snowmanxxxx);
         this.randomBlockDisplayTick(xCenter, yCenter, zCenter, 32, _snowmanx, _snowmanxx, _snowmanxxxx);
      }
   }

   public void randomBlockDisplayTick(int xCenter, int yCenter, int zCenter, int radius, Random random, boolean spawnBarrierParticles, BlockPos.Mutable pos) {
      int _snowman = xCenter + this.random.nextInt(radius) - this.random.nextInt(radius);
      int _snowmanx = yCenter + this.random.nextInt(radius) - this.random.nextInt(radius);
      int _snowmanxx = zCenter + this.random.nextInt(radius) - this.random.nextInt(radius);
      pos.set(_snowman, _snowmanx, _snowmanxx);
      BlockState _snowmanxxx = this.getBlockState(pos);
      _snowmanxxx.getBlock().randomDisplayTick(_snowmanxxx, this, pos, random);
      FluidState _snowmanxxxx = this.getFluidState(pos);
      if (!_snowmanxxxx.isEmpty()) {
         _snowmanxxxx.randomDisplayTick(this, pos, random);
         ParticleEffect _snowmanxxxxx = _snowmanxxxx.getParticle();
         if (_snowmanxxxxx != null && this.random.nextInt(10) == 0) {
            boolean _snowmanxxxxxx = _snowmanxxx.isSideSolidFullSquare(this, pos, Direction.DOWN);
            BlockPos _snowmanxxxxxxx = pos.down();
            this.addParticle(_snowmanxxxxxxx, this.getBlockState(_snowmanxxxxxxx), _snowmanxxxxx, _snowmanxxxxxx);
         }
      }

      if (spawnBarrierParticles && _snowmanxxx.isOf(Blocks.BARRIER)) {
         this.addParticle(ParticleTypes.BARRIER, (double)_snowman + 0.5, (double)_snowmanx + 0.5, (double)_snowmanxx + 0.5, 0.0, 0.0, 0.0);
      }

      if (!_snowmanxxx.isFullCube(this, pos)) {
         this.getBiome(pos)
            .getParticleConfig()
            .ifPresent(
               _snowmanxxxxx -> {
                  if (_snowmanxxxxx.shouldAddParticle(this.random)) {
                     this.addParticle(
                        _snowmanxxxxx.getParticle(),
                        (double)pos.getX() + this.random.nextDouble(),
                        (double)pos.getY() + this.random.nextDouble(),
                        (double)pos.getZ() + this.random.nextDouble(),
                        0.0,
                        0.0,
                        0.0
                     );
                  }
               }
            );
      }
   }

   private void addParticle(BlockPos pos, BlockState state, ParticleEffect parameters, boolean _snowman) {
      if (state.getFluidState().isEmpty()) {
         VoxelShape _snowmanx = state.getCollisionShape(this, pos);
         double _snowmanxx = _snowmanx.getMax(Direction.Axis.Y);
         if (_snowmanxx < 1.0) {
            if (_snowman) {
               this.addParticle(
                  (double)pos.getX(), (double)(pos.getX() + 1), (double)pos.getZ(), (double)(pos.getZ() + 1), (double)(pos.getY() + 1) - 0.05, parameters
               );
            }
         } else if (!state.isIn(BlockTags.IMPERMEABLE)) {
            double _snowmanxxx = _snowmanx.getMin(Direction.Axis.Y);
            if (_snowmanxxx > 0.0) {
               this.addParticle(pos, parameters, _snowmanx, (double)pos.getY() + _snowmanxxx - 0.05);
            } else {
               BlockPos _snowmanxxxx = pos.down();
               BlockState _snowmanxxxxx = this.getBlockState(_snowmanxxxx);
               VoxelShape _snowmanxxxxxx = _snowmanxxxxx.getCollisionShape(this, _snowmanxxxx);
               double _snowmanxxxxxxx = _snowmanxxxxxx.getMax(Direction.Axis.Y);
               if (_snowmanxxxxxxx < 1.0 && _snowmanxxxxx.getFluidState().isEmpty()) {
                  this.addParticle(pos, parameters, _snowmanx, (double)pos.getY() - 0.05);
               }
            }
         }
      }
   }

   private void addParticle(BlockPos pos, ParticleEffect parameters, VoxelShape shape, double y) {
      this.addParticle(
         (double)pos.getX() + shape.getMin(Direction.Axis.X),
         (double)pos.getX() + shape.getMax(Direction.Axis.X),
         (double)pos.getZ() + shape.getMin(Direction.Axis.Z),
         (double)pos.getZ() + shape.getMax(Direction.Axis.Z),
         y,
         parameters
      );
   }

   private void addParticle(double minX, double maxX, double minZ, double maxZ, double y, ParticleEffect parameters) {
      this.addParticle(
         parameters, MathHelper.lerp(this.random.nextDouble(), minX, maxX), y, MathHelper.lerp(this.random.nextDouble(), minZ, maxZ), 0.0, 0.0, 0.0
      );
   }

   public void finishRemovingEntities() {
      ObjectIterator<Entry<Entity>> _snowman = this.regularEntities.int2ObjectEntrySet().iterator();

      while (_snowman.hasNext()) {
         Entry<Entity> _snowmanx = (Entry<Entity>)_snowman.next();
         Entity _snowmanxx = (Entity)_snowmanx.getValue();
         if (_snowmanxx.removed) {
            _snowman.remove();
            this.finishRemovingEntity(_snowmanxx);
         }
      }
   }

   @Override
   public CrashReportSection addDetailsToCrashReport(CrashReport report) {
      CrashReportSection _snowman = super.addDetailsToCrashReport(report);
      _snowman.add("Server brand", () -> this.client.player.getServerBrand());
      _snowman.add("Server type", () -> this.client.getServer() == null ? "Non-integrated multiplayer server" : "Integrated singleplayer server");
      return _snowman;
   }

   @Override
   public void playSound(@Nullable PlayerEntity player, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch) {
      if (player == this.client.player) {
         this.playSound(x, y, z, sound, category, volume, pitch, false);
      }
   }

   @Override
   public void playSoundFromEntity(@Nullable PlayerEntity player, Entity entity, SoundEvent sound, SoundCategory category, float volume, float pitch) {
      if (player == this.client.player) {
         this.client.getSoundManager().play(new EntityTrackingSoundInstance(sound, category, entity));
      }
   }

   public void playSound(BlockPos pos, SoundEvent sound, SoundCategory category, float volume, float pitch, boolean useDistance) {
      this.playSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, sound, category, volume, pitch, useDistance);
   }

   @Override
   public void playSound(double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch, boolean _snowman) {
      double _snowmanx = this.client.gameRenderer.getCamera().getPos().squaredDistanceTo(x, y, z);
      PositionedSoundInstance _snowmanxx = new PositionedSoundInstance(sound, category, volume, pitch, x, y, z);
      if (_snowman && _snowmanx > 100.0) {
         double _snowmanxxx = Math.sqrt(_snowmanx) / 40.0;
         this.client.getSoundManager().play(_snowmanxx, (int)(_snowmanxxx * 20.0));
      } else {
         this.client.getSoundManager().play(_snowmanxx);
      }
   }

   @Override
   public void addFireworkParticle(double x, double y, double z, double velocityX, double velocityY, double velocityZ, @Nullable CompoundTag tag) {
      this.client
         .particleManager
         .addParticle(new FireworksSparkParticle.FireworkParticle(this, x, y, z, velocityX, velocityY, velocityZ, this.client.particleManager, tag));
   }

   @Override
   public void sendPacket(Packet<?> packet) {
      this.netHandler.sendPacket(packet);
   }

   @Override
   public RecipeManager getRecipeManager() {
      return this.netHandler.getRecipeManager();
   }

   public void setScoreboard(Scoreboard scoreboard) {
      this.scoreboard = scoreboard;
   }

   @Override
   public TickScheduler<Block> getBlockTickScheduler() {
      return DummyClientTickScheduler.get();
   }

   @Override
   public TickScheduler<Fluid> getFluidTickScheduler() {
      return DummyClientTickScheduler.get();
   }

   public ClientChunkManager getChunkManager() {
      return this.chunkManager;
   }

   @Nullable
   @Override
   public MapState getMapState(String id) {
      return this.mapStates.get(id);
   }

   @Override
   public void putMapState(MapState mapState) {
      this.mapStates.put(mapState.getId(), mapState);
   }

   @Override
   public int getNextMapId() {
      return 0;
   }

   @Override
   public Scoreboard getScoreboard() {
      return this.scoreboard;
   }

   @Override
   public TagManager getTagManager() {
      return this.netHandler.getTagManager();
   }

   @Override
   public DynamicRegistryManager getRegistryManager() {
      return this.netHandler.getRegistryManager();
   }

   @Override
   public void updateListeners(BlockPos pos, BlockState oldState, BlockState newState, int flags) {
      this.worldRenderer.updateBlock(this, pos, oldState, newState, flags);
   }

   @Override
   public void scheduleBlockRerenderIfNeeded(BlockPos pos, BlockState old, BlockState updated) {
      this.worldRenderer.scheduleBlockRerenderIfNeeded(pos, old, updated);
   }

   public void scheduleBlockRenders(int x, int y, int z) {
      this.worldRenderer.scheduleBlockRenders(x, y, z);
   }

   @Override
   public void setBlockBreakingInfo(int entityId, BlockPos pos, int progress) {
      this.worldRenderer.setBlockBreakingInfo(entityId, pos, progress);
   }

   @Override
   public void syncGlobalEvent(int eventId, BlockPos pos, int data) {
      this.worldRenderer.processGlobalEvent(eventId, pos, data);
   }

   @Override
   public void syncWorldEvent(@Nullable PlayerEntity player, int eventId, BlockPos pos, int data) {
      try {
         this.worldRenderer.processWorldEvent(player, eventId, pos, data);
      } catch (Throwable var8) {
         CrashReport _snowman = CrashReport.create(var8, "Playing level event");
         CrashReportSection _snowmanx = _snowman.addElement("Level event being played");
         _snowmanx.add("Block coordinates", CrashReportSection.createPositionString(pos));
         _snowmanx.add("Event source", player);
         _snowmanx.add("Event type", eventId);
         _snowmanx.add("Event data", data);
         throw new CrashException(_snowman);
      }
   }

   @Override
   public void addParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      this.worldRenderer.addParticle(parameters, parameters.getType().shouldAlwaysSpawn(), x, y, z, velocityX, velocityY, velocityZ);
   }

   @Override
   public void addParticle(ParticleEffect parameters, boolean alwaysSpawn, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      this.worldRenderer.addParticle(parameters, parameters.getType().shouldAlwaysSpawn() || alwaysSpawn, x, y, z, velocityX, velocityY, velocityZ);
   }

   @Override
   public void addImportantParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      this.worldRenderer.addParticle(parameters, false, true, x, y, z, velocityX, velocityY, velocityZ);
   }

   @Override
   public void addImportantParticle(
      ParticleEffect parameters, boolean alwaysSpawn, double x, double y, double z, double velocityX, double velocityY, double velocityZ
   ) {
      this.worldRenderer.addParticle(parameters, parameters.getType().shouldAlwaysSpawn() || alwaysSpawn, true, x, y, z, velocityX, velocityY, velocityZ);
   }

   @Override
   public List<AbstractClientPlayerEntity> getPlayers() {
      return this.players;
   }

   @Override
   public Biome getGeneratorStoredBiome(int biomeX, int biomeY, int biomeZ) {
      return this.getRegistryManager().get(Registry.BIOME_KEY).getOrThrow(BiomeKeys.PLAINS);
   }

   public float method_23783(float _snowman) {
      float _snowmanx = this.getSkyAngle(_snowman);
      float _snowmanxx = 1.0F - (MathHelper.cos(_snowmanx * (float) (Math.PI * 2)) * 2.0F + 0.2F);
      _snowmanxx = MathHelper.clamp(_snowmanxx, 0.0F, 1.0F);
      _snowmanxx = 1.0F - _snowmanxx;
      _snowmanxx = (float)((double)_snowmanxx * (1.0 - (double)(this.getRainGradient(_snowman) * 5.0F) / 16.0));
      _snowmanxx = (float)((double)_snowmanxx * (1.0 - (double)(this.getThunderGradient(_snowman) * 5.0F) / 16.0));
      return _snowmanxx * 0.8F + 0.2F;
   }

   public Vec3d method_23777(BlockPos _snowman, float _snowman) {
      float _snowmanxx = this.getSkyAngle(_snowman);
      float _snowmanxxx = MathHelper.cos(_snowmanxx * (float) (Math.PI * 2)) * 2.0F + 0.5F;
      _snowmanxxx = MathHelper.clamp(_snowmanxxx, 0.0F, 1.0F);
      Biome _snowmanxxxx = this.getBiome(_snowman);
      int _snowmanxxxxx = _snowmanxxxx.getSkyColor();
      float _snowmanxxxxxx = (float)(_snowmanxxxxx >> 16 & 0xFF) / 255.0F;
      float _snowmanxxxxxxx = (float)(_snowmanxxxxx >> 8 & 0xFF) / 255.0F;
      float _snowmanxxxxxxxx = (float)(_snowmanxxxxx & 0xFF) / 255.0F;
      _snowmanxxxxxx *= _snowmanxxx;
      _snowmanxxxxxxx *= _snowmanxxx;
      _snowmanxxxxxxxx *= _snowmanxxx;
      float _snowmanxxxxxxxxx = this.getRainGradient(_snowman);
      if (_snowmanxxxxxxxxx > 0.0F) {
         float _snowmanxxxxxxxxxx = (_snowmanxxxxxx * 0.3F + _snowmanxxxxxxx * 0.59F + _snowmanxxxxxxxx * 0.11F) * 0.6F;
         float _snowmanxxxxxxxxxxx = 1.0F - _snowmanxxxxxxxxx * 0.75F;
         _snowmanxxxxxx = _snowmanxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxx * (1.0F - _snowmanxxxxxxxxxxx);
         _snowmanxxxxxxx = _snowmanxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxx * (1.0F - _snowmanxxxxxxxxxxx);
         _snowmanxxxxxxxx = _snowmanxxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxx * (1.0F - _snowmanxxxxxxxxxxx);
      }

      float _snowmanxxxxxxxxxx = this.getThunderGradient(_snowman);
      if (_snowmanxxxxxxxxxx > 0.0F) {
         float _snowmanxxxxxxxxxxx = (_snowmanxxxxxx * 0.3F + _snowmanxxxxxxx * 0.59F + _snowmanxxxxxxxx * 0.11F) * 0.2F;
         float _snowmanxxxxxxxxxxxx = 1.0F - _snowmanxxxxxxxxxx * 0.75F;
         _snowmanxxxxxx = _snowmanxxxxxx * _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxx * (1.0F - _snowmanxxxxxxxxxxxx);
         _snowmanxxxxxxx = _snowmanxxxxxxx * _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxx * (1.0F - _snowmanxxxxxxxxxxxx);
         _snowmanxxxxxxxx = _snowmanxxxxxxxx * _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxx * (1.0F - _snowmanxxxxxxxxxxxx);
      }

      if (this.lightningTicksLeft > 0) {
         float _snowmanxxxxxxxxxxx = (float)this.lightningTicksLeft - _snowman;
         if (_snowmanxxxxxxxxxxx > 1.0F) {
            _snowmanxxxxxxxxxxx = 1.0F;
         }

         _snowmanxxxxxxxxxxx *= 0.45F;
         _snowmanxxxxxx = _snowmanxxxxxx * (1.0F - _snowmanxxxxxxxxxxx) + 0.8F * _snowmanxxxxxxxxxxx;
         _snowmanxxxxxxx = _snowmanxxxxxxx * (1.0F - _snowmanxxxxxxxxxxx) + 0.8F * _snowmanxxxxxxxxxxx;
         _snowmanxxxxxxxx = _snowmanxxxxxxxx * (1.0F - _snowmanxxxxxxxxxxx) + 1.0F * _snowmanxxxxxxxxxxx;
      }

      return new Vec3d((double)_snowmanxxxxxx, (double)_snowmanxxxxxxx, (double)_snowmanxxxxxxxx);
   }

   public Vec3d getCloudsColor(float tickDelta) {
      float _snowman = this.getSkyAngle(tickDelta);
      float _snowmanx = MathHelper.cos(_snowman * (float) (Math.PI * 2)) * 2.0F + 0.5F;
      _snowmanx = MathHelper.clamp(_snowmanx, 0.0F, 1.0F);
      float _snowmanxx = 1.0F;
      float _snowmanxxx = 1.0F;
      float _snowmanxxxx = 1.0F;
      float _snowmanxxxxx = this.getRainGradient(tickDelta);
      if (_snowmanxxxxx > 0.0F) {
         float _snowmanxxxxxx = (_snowmanxx * 0.3F + _snowmanxxx * 0.59F + _snowmanxxxx * 0.11F) * 0.6F;
         float _snowmanxxxxxxx = 1.0F - _snowmanxxxxx * 0.95F;
         _snowmanxx = _snowmanxx * _snowmanxxxxxxx + _snowmanxxxxxx * (1.0F - _snowmanxxxxxxx);
         _snowmanxxx = _snowmanxxx * _snowmanxxxxxxx + _snowmanxxxxxx * (1.0F - _snowmanxxxxxxx);
         _snowmanxxxx = _snowmanxxxx * _snowmanxxxxxxx + _snowmanxxxxxx * (1.0F - _snowmanxxxxxxx);
      }

      _snowmanxx *= _snowmanx * 0.9F + 0.1F;
      _snowmanxxx *= _snowmanx * 0.9F + 0.1F;
      _snowmanxxxx *= _snowmanx * 0.85F + 0.15F;
      float _snowmanxxxxxx = this.getThunderGradient(tickDelta);
      if (_snowmanxxxxxx > 0.0F) {
         float _snowmanxxxxxxx = (_snowmanxx * 0.3F + _snowmanxxx * 0.59F + _snowmanxxxx * 0.11F) * 0.2F;
         float _snowmanxxxxxxxx = 1.0F - _snowmanxxxxxx * 0.95F;
         _snowmanxx = _snowmanxx * _snowmanxxxxxxxx + _snowmanxxxxxxx * (1.0F - _snowmanxxxxxxxx);
         _snowmanxxx = _snowmanxxx * _snowmanxxxxxxxx + _snowmanxxxxxxx * (1.0F - _snowmanxxxxxxxx);
         _snowmanxxxx = _snowmanxxxx * _snowmanxxxxxxxx + _snowmanxxxxxxx * (1.0F - _snowmanxxxxxxxx);
      }

      return new Vec3d((double)_snowmanxx, (double)_snowmanxxx, (double)_snowmanxxxx);
   }

   public float method_23787(float _snowman) {
      float _snowmanx = this.getSkyAngle(_snowman);
      float _snowmanxx = 1.0F - (MathHelper.cos(_snowmanx * (float) (Math.PI * 2)) * 2.0F + 0.25F);
      _snowmanxx = MathHelper.clamp(_snowmanxx, 0.0F, 1.0F);
      return _snowmanxx * _snowmanxx * 0.5F;
   }

   public int getLightningTicksLeft() {
      return this.lightningTicksLeft;
   }

   @Override
   public void setLightningTicksLeft(int lightningTicksLeft) {
      this.lightningTicksLeft = lightningTicksLeft;
   }

   @Override
   public float getBrightness(Direction direction, boolean shaded) {
      boolean _snowman = this.getSkyProperties().isDarkened();
      if (!shaded) {
         return _snowman ? 0.9F : 1.0F;
      } else {
         switch (direction) {
            case DOWN:
               return _snowman ? 0.9F : 0.5F;
            case UP:
               return _snowman ? 0.9F : 1.0F;
            case NORTH:
            case SOUTH:
               return 0.8F;
            case WEST:
            case EAST:
               return 0.6F;
            default:
               return 1.0F;
         }
      }
   }

   @Override
   public int getColor(BlockPos pos, ColorResolver colorResolver) {
      BiomeColorCache _snowman = (BiomeColorCache)this.colorCache.get(colorResolver);
      return _snowman.getBiomeColor(pos, () -> this.calculateColor(pos, colorResolver));
   }

   public int calculateColor(BlockPos pos, ColorResolver colorResolver) {
      int _snowman = MinecraftClient.getInstance().options.biomeBlendRadius;
      if (_snowman == 0) {
         return colorResolver.getColor(this.getBiome(pos), (double)pos.getX(), (double)pos.getZ());
      } else {
         int _snowmanx = (_snowman * 2 + 1) * (_snowman * 2 + 1);
         int _snowmanxx = 0;
         int _snowmanxxx = 0;
         int _snowmanxxxx = 0;
         CuboidBlockIterator _snowmanxxxxx = new CuboidBlockIterator(pos.getX() - _snowman, pos.getY(), pos.getZ() - _snowman, pos.getX() + _snowman, pos.getY(), pos.getZ() + _snowman);
         BlockPos.Mutable _snowmanxxxxxx = new BlockPos.Mutable();

         while (_snowmanxxxxx.step()) {
            _snowmanxxxxxx.set(_snowmanxxxxx.getX(), _snowmanxxxxx.getY(), _snowmanxxxxx.getZ());
            int _snowmanxxxxxxx = colorResolver.getColor(this.getBiome(_snowmanxxxxxx), (double)_snowmanxxxxxx.getX(), (double)_snowmanxxxxxx.getZ());
            _snowmanxx += (_snowmanxxxxxxx & 0xFF0000) >> 16;
            _snowmanxxx += (_snowmanxxxxxxx & 0xFF00) >> 8;
            _snowmanxxxx += _snowmanxxxxxxx & 0xFF;
         }

         return (_snowmanxx / _snowmanx & 0xFF) << 16 | (_snowmanxxx / _snowmanx & 0xFF) << 8 | _snowmanxxxx / _snowmanx & 0xFF;
      }
   }

   public BlockPos getSpawnPos() {
      BlockPos _snowman = new BlockPos(this.properties.getSpawnX(), this.properties.getSpawnY(), this.properties.getSpawnZ());
      if (!this.getWorldBorder().contains(_snowman)) {
         _snowman = this.getTopPosition(Heightmap.Type.MOTION_BLOCKING, new BlockPos(this.getWorldBorder().getCenterX(), 0.0, this.getWorldBorder().getCenterZ()));
      }

      return _snowman;
   }

   public float method_30671() {
      return this.properties.getSpawnAngle();
   }

   public void setSpawnPos(BlockPos pos, float angle) {
      this.properties.setSpawnPos(pos, angle);
   }

   @Override
   public String toString() {
      return "ClientLevel";
   }

   public ClientWorld.Properties getLevelProperties() {
      return this.clientWorldProperties;
   }

   public static class Properties implements MutableWorldProperties {
      private final boolean hardcore;
      private final GameRules gameRules;
      private final boolean flatWorld;
      private int spawnX;
      private int spawnY;
      private int spawnZ;
      private float spawnAngle;
      private long time;
      private long timeOfDay;
      private boolean raining;
      private Difficulty difficulty;
      private boolean difficultyLocked;

      public Properties(Difficulty difficulty, boolean hardcore, boolean flatWorld) {
         this.difficulty = difficulty;
         this.hardcore = hardcore;
         this.flatWorld = flatWorld;
         this.gameRules = new GameRules();
      }

      @Override
      public int getSpawnX() {
         return this.spawnX;
      }

      @Override
      public int getSpawnY() {
         return this.spawnY;
      }

      @Override
      public int getSpawnZ() {
         return this.spawnZ;
      }

      @Override
      public float getSpawnAngle() {
         return this.spawnAngle;
      }

      @Override
      public long getTime() {
         return this.time;
      }

      @Override
      public long getTimeOfDay() {
         return this.timeOfDay;
      }

      @Override
      public void setSpawnX(int spawnX) {
         this.spawnX = spawnX;
      }

      @Override
      public void setSpawnY(int spawnY) {
         this.spawnY = spawnY;
      }

      @Override
      public void setSpawnZ(int spawnZ) {
         this.spawnZ = spawnZ;
      }

      @Override
      public void setSpawnAngle(float angle) {
         this.spawnAngle = angle;
      }

      public void setTime(long difficulty) {
         this.time = difficulty;
      }

      public void setTimeOfDay(long time) {
         this.timeOfDay = time;
      }

      @Override
      public void setSpawnPos(BlockPos pos, float angle) {
         this.spawnX = pos.getX();
         this.spawnY = pos.getY();
         this.spawnZ = pos.getZ();
         this.spawnAngle = angle;
      }

      @Override
      public boolean isThundering() {
         return false;
      }

      @Override
      public boolean isRaining() {
         return this.raining;
      }

      @Override
      public void setRaining(boolean raining) {
         this.raining = raining;
      }

      @Override
      public boolean isHardcore() {
         return this.hardcore;
      }

      @Override
      public GameRules getGameRules() {
         return this.gameRules;
      }

      @Override
      public Difficulty getDifficulty() {
         return this.difficulty;
      }

      @Override
      public boolean isDifficultyLocked() {
         return this.difficultyLocked;
      }

      @Override
      public void populateCrashReport(CrashReportSection reportSection) {
         MutableWorldProperties.super.populateCrashReport(reportSection);
      }

      public void setDifficulty(Difficulty difficulty) {
         this.difficulty = difficulty;
      }

      public void setDifficultyLocked(boolean difficultyLocked) {
         this.difficultyLocked = difficultyLocked;
      }

      public double getSkyDarknessHeight() {
         return this.flatWorld ? 0.0 : 63.0;
      }

      public double getHorizonShadingRatio() {
         return this.flatWorld ? 1.0 : 0.03125;
      }
   }
}
