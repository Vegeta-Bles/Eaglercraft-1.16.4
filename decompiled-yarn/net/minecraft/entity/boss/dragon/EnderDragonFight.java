package net.minecraft.entity.boss.dragon;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndPortalBlockEntity;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.predicate.block.BlockPredicate;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.EndPortalFeature;
import net.minecraft.world.gen.feature.EndSpikeFeature;
import net.minecraft.world.gen.feature.FeatureConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnderDragonFight {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Predicate<Entity> VALID_ENTITY = EntityPredicates.VALID_ENTITY.and(EntityPredicates.maxDistance(0.0, 128.0, 0.0, 192.0));
   private final ServerBossBar bossBar = (ServerBossBar)new ServerBossBar(
         new TranslatableText("entity.minecraft.ender_dragon"), BossBar.Color.PINK, BossBar.Style.PROGRESS
      )
      .setDragonMusic(true)
      .setThickenFog(true);
   private final ServerWorld world;
   private final List<Integer> gateways = Lists.newArrayList();
   private final BlockPattern endPortalPattern;
   private int dragonSeenTimer;
   private int endCrystalsAlive;
   private int crystalCountTimer;
   private int playerUpdateTimer;
   private boolean dragonKilled;
   private boolean previouslyKilled;
   private UUID dragonUuid;
   private boolean doLegacyCheck = true;
   private BlockPos exitPortalLocation;
   private EnderDragonSpawnState dragonSpawnState;
   private int spawnStateTimer;
   private List<EndCrystalEntity> crystals;

   public EnderDragonFight(ServerWorld world, long _snowman, CompoundTag _snowman) {
      this.world = world;
      if (_snowman.contains("DragonKilled", 99)) {
         if (_snowman.containsUuid("Dragon")) {
            this.dragonUuid = _snowman.getUuid("Dragon");
         }

         this.dragonKilled = _snowman.getBoolean("DragonKilled");
         this.previouslyKilled = _snowman.getBoolean("PreviouslyKilled");
         if (_snowman.getBoolean("IsRespawning")) {
            this.dragonSpawnState = EnderDragonSpawnState.START;
         }

         if (_snowman.contains("ExitPortalLocation", 10)) {
            this.exitPortalLocation = NbtHelper.toBlockPos(_snowman.getCompound("ExitPortalLocation"));
         }
      } else {
         this.dragonKilled = true;
         this.previouslyKilled = true;
      }

      if (_snowman.contains("Gateways", 9)) {
         ListTag _snowmanxx = _snowman.getList("Gateways", 3);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
            this.gateways.add(_snowmanxx.getInt(_snowmanxxx));
         }
      } else {
         this.gateways.addAll(ContiguousSet.create(Range.closedOpen(0, 20), DiscreteDomain.integers()));
         Collections.shuffle(this.gateways, new Random(_snowman));
      }

      this.endPortalPattern = BlockPatternBuilder.start()
         .aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ")
         .aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ")
         .aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ")
         .aisle("  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  ")
         .aisle("       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       ")
         .where('#', CachedBlockPosition.matchesBlockState(BlockPredicate.make(Blocks.BEDROCK)))
         .build();
   }

   public CompoundTag toTag() {
      CompoundTag _snowman = new CompoundTag();
      if (this.dragonUuid != null) {
         _snowman.putUuid("Dragon", this.dragonUuid);
      }

      _snowman.putBoolean("DragonKilled", this.dragonKilled);
      _snowman.putBoolean("PreviouslyKilled", this.previouslyKilled);
      if (this.exitPortalLocation != null) {
         _snowman.put("ExitPortalLocation", NbtHelper.fromBlockPos(this.exitPortalLocation));
      }

      ListTag _snowmanx = new ListTag();

      for (int _snowmanxx : this.gateways) {
         _snowmanx.add(IntTag.of(_snowmanxx));
      }

      _snowman.put("Gateways", _snowmanx);
      return _snowman;
   }

   public void tick() {
      this.bossBar.setVisible(!this.dragonKilled);
      if (++this.playerUpdateTimer >= 20) {
         this.updatePlayers();
         this.playerUpdateTimer = 0;
      }

      if (!this.bossBar.getPlayers().isEmpty()) {
         this.world.getChunkManager().addTicket(ChunkTicketType.DRAGON, new ChunkPos(0, 0), 9, Unit.INSTANCE);
         boolean _snowman = this.loadChunks();
         if (this.doLegacyCheck && _snowman) {
            this.convertFromLegacy();
            this.doLegacyCheck = false;
         }

         if (this.dragonSpawnState != null) {
            if (this.crystals == null && _snowman) {
               this.dragonSpawnState = null;
               this.respawnDragon();
            }

            this.dragonSpawnState.run(this.world, this, this.crystals, this.spawnStateTimer++, this.exitPortalLocation);
         }

         if (!this.dragonKilled) {
            if ((this.dragonUuid == null || ++this.dragonSeenTimer >= 1200) && _snowman) {
               this.checkDragonSeen();
               this.dragonSeenTimer = 0;
            }

            if (++this.crystalCountTimer >= 100 && _snowman) {
               this.countAliveCrystals();
               this.crystalCountTimer = 0;
            }
         }
      } else {
         this.world.getChunkManager().removeTicket(ChunkTicketType.DRAGON, new ChunkPos(0, 0), 9, Unit.INSTANCE);
      }
   }

   private void convertFromLegacy() {
      LOGGER.info("Scanning for legacy world dragon fight...");
      boolean _snowman = this.worldContainsEndPortal();
      if (_snowman) {
         LOGGER.info("Found that the dragon has been killed in this world already.");
         this.previouslyKilled = true;
      } else {
         LOGGER.info("Found that the dragon has not yet been killed in this world.");
         this.previouslyKilled = false;
         if (this.findEndPortal() == null) {
            this.generateEndPortal(false);
         }
      }

      List<EnderDragonEntity> _snowmanx = this.world.getAliveEnderDragons();
      if (_snowmanx.isEmpty()) {
         this.dragonKilled = true;
      } else {
         EnderDragonEntity _snowmanxx = _snowmanx.get(0);
         this.dragonUuid = _snowmanxx.getUuid();
         LOGGER.info("Found that there's a dragon still alive ({})", _snowmanxx);
         this.dragonKilled = false;
         if (!_snowman) {
            LOGGER.info("But we didn't have a portal, let's remove it.");
            _snowmanxx.remove();
            this.dragonUuid = null;
         }
      }

      if (!this.previouslyKilled && this.dragonKilled) {
         this.dragonKilled = false;
      }
   }

   private void checkDragonSeen() {
      List<EnderDragonEntity> _snowman = this.world.getAliveEnderDragons();
      if (_snowman.isEmpty()) {
         LOGGER.debug("Haven't seen the dragon, respawning it");
         this.createDragon();
      } else {
         LOGGER.debug("Haven't seen our dragon, but found another one to use.");
         this.dragonUuid = _snowman.get(0).getUuid();
      }
   }

   protected void setSpawnState(EnderDragonSpawnState _snowman) {
      if (this.dragonSpawnState == null) {
         throw new IllegalStateException("Dragon respawn isn't in progress, can't skip ahead in the animation.");
      } else {
         this.spawnStateTimer = 0;
         if (_snowman == EnderDragonSpawnState.END) {
            this.dragonSpawnState = null;
            this.dragonKilled = false;
            EnderDragonEntity _snowmanx = this.createDragon();

            for (ServerPlayerEntity _snowmanxx : this.bossBar.getPlayers()) {
               Criteria.SUMMONED_ENTITY.trigger(_snowmanxx, _snowmanx);
            }
         } else {
            this.dragonSpawnState = _snowman;
         }
      }
   }

   private boolean worldContainsEndPortal() {
      for (int _snowman = -8; _snowman <= 8; _snowman++) {
         for (int _snowmanx = -8; _snowmanx <= 8; _snowmanx++) {
            WorldChunk _snowmanxx = this.world.getChunk(_snowman, _snowmanx);

            for (BlockEntity _snowmanxxx : _snowmanxx.getBlockEntities().values()) {
               if (_snowmanxxx instanceof EndPortalBlockEntity) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @Nullable
   private BlockPattern.Result findEndPortal() {
      for (int _snowman = -8; _snowman <= 8; _snowman++) {
         for (int _snowmanx = -8; _snowmanx <= 8; _snowmanx++) {
            WorldChunk _snowmanxx = this.world.getChunk(_snowman, _snowmanx);

            for (BlockEntity _snowmanxxx : _snowmanxx.getBlockEntities().values()) {
               if (_snowmanxxx instanceof EndPortalBlockEntity) {
                  BlockPattern.Result _snowmanxxxx = this.endPortalPattern.searchAround(this.world, _snowmanxxx.getPos());
                  if (_snowmanxxxx != null) {
                     BlockPos _snowmanxxxxx = _snowmanxxxx.translate(3, 3, 3).getBlockPos();
                     if (this.exitPortalLocation == null && _snowmanxxxxx.getX() == 0 && _snowmanxxxxx.getZ() == 0) {
                        this.exitPortalLocation = _snowmanxxxxx;
                     }

                     return _snowmanxxxx;
                  }
               }
            }
         }
      }

      int _snowman = this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, EndPortalFeature.ORIGIN).getY();

      for (int _snowmanx = _snowman; _snowmanx >= 0; _snowmanx--) {
         BlockPattern.Result _snowmanxx = this.endPortalPattern
            .searchAround(this.world, new BlockPos(EndPortalFeature.ORIGIN.getX(), _snowmanx, EndPortalFeature.ORIGIN.getZ()));
         if (_snowmanxx != null) {
            if (this.exitPortalLocation == null) {
               this.exitPortalLocation = _snowmanxx.translate(3, 3, 3).getBlockPos();
            }

            return _snowmanxx;
         }
      }

      return null;
   }

   private boolean loadChunks() {
      for (int _snowman = -8; _snowman <= 8; _snowman++) {
         for (int _snowmanx = 8; _snowmanx <= 8; _snowmanx++) {
            Chunk _snowmanxx = this.world.getChunk(_snowman, _snowmanx, ChunkStatus.FULL, false);
            if (!(_snowmanxx instanceof WorldChunk)) {
               return false;
            }

            ChunkHolder.LevelType _snowmanxxx = ((WorldChunk)_snowmanxx).getLevelType();
            if (!_snowmanxxx.isAfter(ChunkHolder.LevelType.TICKING)) {
               return false;
            }
         }
      }

      return true;
   }

   private void updatePlayers() {
      Set<ServerPlayerEntity> _snowman = Sets.newHashSet();

      for (ServerPlayerEntity _snowmanx : this.world.getPlayers(VALID_ENTITY)) {
         this.bossBar.addPlayer(_snowmanx);
         _snowman.add(_snowmanx);
      }

      Set<ServerPlayerEntity> _snowmanx = Sets.newHashSet(this.bossBar.getPlayers());
      _snowmanx.removeAll(_snowman);

      for (ServerPlayerEntity _snowmanxx : _snowmanx) {
         this.bossBar.removePlayer(_snowmanxx);
      }
   }

   private void countAliveCrystals() {
      this.crystalCountTimer = 0;
      this.endCrystalsAlive = 0;

      for (EndSpikeFeature.Spike _snowman : EndSpikeFeature.getSpikes(this.world)) {
         this.endCrystalsAlive = this.endCrystalsAlive + this.world.getNonSpectatingEntities(EndCrystalEntity.class, _snowman.getBoundingBox()).size();
      }

      LOGGER.debug("Found {} end crystals still alive", this.endCrystalsAlive);
   }

   public void dragonKilled(EnderDragonEntity dragon) {
      if (dragon.getUuid().equals(this.dragonUuid)) {
         this.bossBar.setPercent(0.0F);
         this.bossBar.setVisible(false);
         this.generateEndPortal(true);
         this.generateNewEndGateway();
         if (!this.previouslyKilled) {
            this.world.setBlockState(this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, EndPortalFeature.ORIGIN), Blocks.DRAGON_EGG.getDefaultState());
         }

         this.previouslyKilled = true;
         this.dragonKilled = true;
      }
   }

   private void generateNewEndGateway() {
      if (!this.gateways.isEmpty()) {
         int _snowman = this.gateways.remove(this.gateways.size() - 1);
         int _snowmanx = MathHelper.floor(96.0 * Math.cos(2.0 * (-Math.PI + (Math.PI / 20) * (double)_snowman)));
         int _snowmanxx = MathHelper.floor(96.0 * Math.sin(2.0 * (-Math.PI + (Math.PI / 20) * (double)_snowman)));
         this.generateEndGateway(new BlockPos(_snowmanx, 75, _snowmanxx));
      }
   }

   private void generateEndGateway(BlockPos _snowman) {
      this.world.syncWorldEvent(3000, _snowman, 0);
      ConfiguredFeatures.END_GATEWAY_DELAYED.generate(this.world, this.world.getChunkManager().getChunkGenerator(), new Random(), _snowman);
   }

   private void generateEndPortal(boolean previouslyKilled) {
      EndPortalFeature _snowman = new EndPortalFeature(previouslyKilled);
      if (this.exitPortalLocation == null) {
         this.exitPortalLocation = this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPortalFeature.ORIGIN).down();

         while (this.world.getBlockState(this.exitPortalLocation).isOf(Blocks.BEDROCK) && this.exitPortalLocation.getY() > this.world.getSeaLevel()) {
            this.exitPortalLocation = this.exitPortalLocation.down();
         }
      }

      _snowman.configure(FeatureConfig.DEFAULT).generate(this.world, this.world.getChunkManager().getChunkGenerator(), new Random(), this.exitPortalLocation);
   }

   private EnderDragonEntity createDragon() {
      this.world.getWorldChunk(new BlockPos(0, 128, 0));
      EnderDragonEntity _snowman = EntityType.ENDER_DRAGON.create(this.world);
      _snowman.getPhaseManager().setPhase(PhaseType.HOLDING_PATTERN);
      _snowman.refreshPositionAndAngles(0.0, 128.0, 0.0, this.world.random.nextFloat() * 360.0F, 0.0F);
      this.world.spawnEntity(_snowman);
      this.dragonUuid = _snowman.getUuid();
      return _snowman;
   }

   public void updateFight(EnderDragonEntity dragon) {
      if (dragon.getUuid().equals(this.dragonUuid)) {
         this.bossBar.setPercent(dragon.getHealth() / dragon.getMaxHealth());
         this.dragonSeenTimer = 0;
         if (dragon.hasCustomName()) {
            this.bossBar.setName(dragon.getDisplayName());
         }
      }
   }

   public int getAliveEndCrystals() {
      return this.endCrystalsAlive;
   }

   public void crystalDestroyed(EndCrystalEntity enderCrystal, DamageSource source) {
      if (this.dragonSpawnState != null && this.crystals.contains(enderCrystal)) {
         LOGGER.debug("Aborting respawn sequence");
         this.dragonSpawnState = null;
         this.spawnStateTimer = 0;
         this.resetEndCrystals();
         this.generateEndPortal(true);
      } else {
         this.countAliveCrystals();
         Entity _snowman = this.world.getEntity(this.dragonUuid);
         if (_snowman instanceof EnderDragonEntity) {
            ((EnderDragonEntity)_snowman).crystalDestroyed(enderCrystal, enderCrystal.getBlockPos(), source);
         }
      }
   }

   public boolean hasPreviouslyKilled() {
      return this.previouslyKilled;
   }

   public void respawnDragon() {
      if (this.dragonKilled && this.dragonSpawnState == null) {
         BlockPos _snowman = this.exitPortalLocation;
         if (_snowman == null) {
            LOGGER.debug("Tried to respawn, but need to find the portal first.");
            BlockPattern.Result _snowmanx = this.findEndPortal();
            if (_snowmanx == null) {
               LOGGER.debug("Couldn't find a portal, so we made one.");
               this.generateEndPortal(true);
            } else {
               LOGGER.debug("Found the exit portal & temporarily using it.");
            }

            _snowman = this.exitPortalLocation;
         }

         List<EndCrystalEntity> _snowmanx = Lists.newArrayList();
         BlockPos _snowmanxx = _snowman.up(1);

         for (Direction _snowmanxxx : Direction.Type.HORIZONTAL) {
            List<EndCrystalEntity> _snowmanxxxx = this.world.getNonSpectatingEntities(EndCrystalEntity.class, new Box(_snowmanxx.offset(_snowmanxxx, 2)));
            if (_snowmanxxxx.isEmpty()) {
               return;
            }

            _snowmanx.addAll(_snowmanxxxx);
         }

         LOGGER.debug("Found all crystals, respawning dragon.");
         this.respawnDragon(_snowmanx);
      }
   }

   private void respawnDragon(List<EndCrystalEntity> crystals) {
      if (this.dragonKilled && this.dragonSpawnState == null) {
         for (BlockPattern.Result _snowman = this.findEndPortal(); _snowman != null; _snowman = this.findEndPortal()) {
            for (int _snowmanx = 0; _snowmanx < this.endPortalPattern.getWidth(); _snowmanx++) {
               for (int _snowmanxx = 0; _snowmanxx < this.endPortalPattern.getHeight(); _snowmanxx++) {
                  for (int _snowmanxxx = 0; _snowmanxxx < this.endPortalPattern.getDepth(); _snowmanxxx++) {
                     CachedBlockPosition _snowmanxxxx = _snowman.translate(_snowmanx, _snowmanxx, _snowmanxxx);
                     if (_snowmanxxxx.getBlockState().isOf(Blocks.BEDROCK) || _snowmanxxxx.getBlockState().isOf(Blocks.END_PORTAL)) {
                        this.world.setBlockState(_snowmanxxxx.getBlockPos(), Blocks.END_STONE.getDefaultState());
                     }
                  }
               }
            }
         }

         this.dragonSpawnState = EnderDragonSpawnState.START;
         this.spawnStateTimer = 0;
         this.generateEndPortal(false);
         this.crystals = crystals;
      }
   }

   public void resetEndCrystals() {
      for (EndSpikeFeature.Spike _snowman : EndSpikeFeature.getSpikes(this.world)) {
         for (EndCrystalEntity _snowmanx : this.world.getNonSpectatingEntities(EndCrystalEntity.class, _snowman.getBoundingBox())) {
            _snowmanx.setInvulnerable(false);
            _snowmanx.setBeamTarget(null);
         }
      }
   }
}
