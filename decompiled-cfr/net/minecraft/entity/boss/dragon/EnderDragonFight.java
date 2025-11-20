/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ContiguousSet
 *  com.google.common.collect.DiscreteDomain
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Range
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.entity.boss.dragon;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
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
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonSpawnState;
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
    private final ServerBossBar bossBar = (ServerBossBar)new ServerBossBar(new TranslatableText("entity.minecraft.ender_dragon"), BossBar.Color.PINK, BossBar.Style.PROGRESS).setDragonMusic(true).setThickenFog(true);
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

    public EnderDragonFight(ServerWorld world, long l2, CompoundTag compoundTag) {
        this.world = world;
        if (compoundTag.contains("DragonKilled", 99)) {
            if (compoundTag.containsUuid("Dragon")) {
                this.dragonUuid = compoundTag.getUuid("Dragon");
            }
            this.dragonKilled = compoundTag.getBoolean("DragonKilled");
            this.previouslyKilled = compoundTag.getBoolean("PreviouslyKilled");
            if (compoundTag.getBoolean("IsRespawning")) {
                this.dragonSpawnState = EnderDragonSpawnState.START;
            }
            if (compoundTag.contains("ExitPortalLocation", 10)) {
                this.exitPortalLocation = NbtHelper.toBlockPos(compoundTag.getCompound("ExitPortalLocation"));
            }
        } else {
            this.dragonKilled = true;
            this.previouslyKilled = true;
        }
        if (compoundTag.contains("Gateways", 9)) {
            ListTag listTag = compoundTag.getList("Gateways", 3);
            for (int i = 0; i < listTag.size(); ++i) {
                this.gateways.add(listTag.getInt(i));
            }
        } else {
            long l2;
            this.gateways.addAll((Collection<Integer>)ContiguousSet.create((Range)Range.closedOpen((Comparable)Integer.valueOf(0), (Comparable)Integer.valueOf(20)), (DiscreteDomain)DiscreteDomain.integers()));
            Collections.shuffle(this.gateways, new Random(l2));
        }
        this.endPortalPattern = BlockPatternBuilder.start().aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").aisle("  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  ").aisle("       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       ").where('#', CachedBlockPosition.matchesBlockState(BlockPredicate.make(Blocks.BEDROCK))).build();
    }

    public CompoundTag toTag() {
        CompoundTag compoundTag = new CompoundTag();
        if (this.dragonUuid != null) {
            compoundTag.putUuid("Dragon", this.dragonUuid);
        }
        compoundTag.putBoolean("DragonKilled", this.dragonKilled);
        compoundTag.putBoolean("PreviouslyKilled", this.previouslyKilled);
        if (this.exitPortalLocation != null) {
            compoundTag.put("ExitPortalLocation", NbtHelper.fromBlockPos(this.exitPortalLocation));
        }
        ListTag _snowman2 = new ListTag();
        for (int n : this.gateways) {
            _snowman2.add(IntTag.of(n));
        }
        compoundTag.put("Gateways", _snowman2);
        return compoundTag;
    }

    public void tick() {
        this.bossBar.setVisible(!this.dragonKilled);
        if (++this.playerUpdateTimer >= 20) {
            this.updatePlayers();
            this.playerUpdateTimer = 0;
        }
        if (!this.bossBar.getPlayers().isEmpty()) {
            this.world.getChunkManager().addTicket(ChunkTicketType.DRAGON, new ChunkPos(0, 0), 9, Unit.INSTANCE);
            boolean bl = this.loadChunks();
            if (this.doLegacyCheck && bl) {
                this.convertFromLegacy();
                this.doLegacyCheck = false;
            }
            if (this.dragonSpawnState != null) {
                if (this.crystals == null && bl) {
                    this.dragonSpawnState = null;
                    this.respawnDragon();
                }
                this.dragonSpawnState.run(this.world, this, this.crystals, this.spawnStateTimer++, this.exitPortalLocation);
            }
            if (!this.dragonKilled) {
                if ((this.dragonUuid == null || ++this.dragonSeenTimer >= 1200) && bl) {
                    this.checkDragonSeen();
                    this.dragonSeenTimer = 0;
                }
                if (++this.crystalCountTimer >= 100 && bl) {
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
        boolean bl = this.worldContainsEndPortal();
        if (bl) {
            LOGGER.info("Found that the dragon has been killed in this world already.");
            this.previouslyKilled = true;
        } else {
            LOGGER.info("Found that the dragon has not yet been killed in this world.");
            this.previouslyKilled = false;
            if (this.findEndPortal() == null) {
                this.generateEndPortal(false);
            }
        }
        List<EnderDragonEntity> _snowman2 = this.world.getAliveEnderDragons();
        if (_snowman2.isEmpty()) {
            this.dragonKilled = true;
        } else {
            EnderDragonEntity enderDragonEntity = _snowman2.get(0);
            this.dragonUuid = enderDragonEntity.getUuid();
            LOGGER.info("Found that there's a dragon still alive ({})", (Object)enderDragonEntity);
            this.dragonKilled = false;
            if (!bl) {
                LOGGER.info("But we didn't have a portal, let's remove it.");
                enderDragonEntity.remove();
                this.dragonUuid = null;
            }
        }
        if (!this.previouslyKilled && this.dragonKilled) {
            this.dragonKilled = false;
        }
    }

    private void checkDragonSeen() {
        List<EnderDragonEntity> list = this.world.getAliveEnderDragons();
        if (list.isEmpty()) {
            LOGGER.debug("Haven't seen the dragon, respawning it");
            this.createDragon();
        } else {
            LOGGER.debug("Haven't seen our dragon, but found another one to use.");
            this.dragonUuid = list.get(0).getUuid();
        }
    }

    protected void setSpawnState(EnderDragonSpawnState enderDragonSpawnState2) {
        if (this.dragonSpawnState == null) {
            throw new IllegalStateException("Dragon respawn isn't in progress, can't skip ahead in the animation.");
        }
        this.spawnStateTimer = 0;
        if (enderDragonSpawnState2 == EnderDragonSpawnState.END) {
            this.dragonSpawnState = null;
            this.dragonKilled = false;
            EnderDragonEntity enderDragonEntity = this.createDragon();
            for (ServerPlayerEntity serverPlayerEntity : this.bossBar.getPlayers()) {
                Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, enderDragonEntity);
            }
        } else {
            EnderDragonSpawnState enderDragonSpawnState2;
            this.dragonSpawnState = enderDragonSpawnState2;
        }
    }

    private boolean worldContainsEndPortal() {
        for (int i = -8; i <= 8; ++i) {
            for (_snowman = -8; _snowman <= 8; ++_snowman) {
                WorldChunk worldChunk = this.world.getChunk(i, _snowman);
                for (BlockEntity blockEntity : worldChunk.getBlockEntities().values()) {
                    if (!(blockEntity instanceof EndPortalBlockEntity)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    @Nullable
    private BlockPattern.Result findEndPortal() {
        Object object;
        for (int i = -8; i <= 8; ++i) {
            for (i = -8; i <= 8; ++i) {
                object = this.world.getChunk(i, i);
                for (BlockEntity blockEntity : ((WorldChunk)object).getBlockEntities().values()) {
                    if (!(blockEntity instanceof EndPortalBlockEntity) || (_snowman = this.endPortalPattern.searchAround(this.world, blockEntity.getPos())) == null) continue;
                    BlockPos blockPos = _snowman.translate(3, 3, 3).getBlockPos();
                    if (this.exitPortalLocation == null && blockPos.getX() == 0 && blockPos.getZ() == 0) {
                        this.exitPortalLocation = blockPos;
                    }
                    return _snowman;
                }
            }
        }
        for (int i = i = this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, EndPortalFeature.ORIGIN).getY(); i >= 0; --i) {
            object = this.endPortalPattern.searchAround(this.world, new BlockPos(EndPortalFeature.ORIGIN.getX(), i, EndPortalFeature.ORIGIN.getZ()));
            if (object == null) continue;
            if (this.exitPortalLocation == null) {
                this.exitPortalLocation = ((BlockPattern.Result)object).translate(3, 3, 3).getBlockPos();
            }
            return object;
        }
        return null;
    }

    private boolean loadChunks() {
        for (int i = -8; i <= 8; ++i) {
            for (_snowman = 8; _snowman <= 8; ++_snowman) {
                Chunk chunk = this.world.getChunk(i, _snowman, ChunkStatus.FULL, false);
                if (!(chunk instanceof WorldChunk)) {
                    return false;
                }
                ChunkHolder.LevelType _snowman2 = ((WorldChunk)chunk).getLevelType();
                if (_snowman2.isAfter(ChunkHolder.LevelType.TICKING)) continue;
                return false;
            }
        }
        return true;
    }

    private void updatePlayers() {
        HashSet hashSet = Sets.newHashSet();
        for (ServerPlayerEntity serverPlayerEntity : this.world.getPlayers(VALID_ENTITY)) {
            this.bossBar.addPlayer(serverPlayerEntity);
            hashSet.add(serverPlayerEntity);
        }
        HashSet hashSet2 = Sets.newHashSet(this.bossBar.getPlayers());
        hashSet2.removeAll(hashSet);
        for (ServerPlayerEntity serverPlayerEntity : hashSet2) {
            this.bossBar.removePlayer(serverPlayerEntity);
        }
    }

    private void countAliveCrystals() {
        this.crystalCountTimer = 0;
        this.endCrystalsAlive = 0;
        for (EndSpikeFeature.Spike spike : EndSpikeFeature.getSpikes(this.world)) {
            this.endCrystalsAlive += this.world.getNonSpectatingEntities(EndCrystalEntity.class, spike.getBoundingBox()).size();
        }
        LOGGER.debug("Found {} end crystals still alive", (Object)this.endCrystalsAlive);
    }

    public void dragonKilled(EnderDragonEntity dragon) {
        if (dragon.getUuid().equals(this.dragonUuid)) {
            this.bossBar.setPercent(0.0f);
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
        if (this.gateways.isEmpty()) {
            return;
        }
        int n = this.gateways.remove(this.gateways.size() - 1);
        _snowman = MathHelper.floor(96.0 * Math.cos(2.0 * (-Math.PI + 0.15707963267948966 * (double)n)));
        _snowman = MathHelper.floor(96.0 * Math.sin(2.0 * (-Math.PI + 0.15707963267948966 * (double)n)));
        this.generateEndGateway(new BlockPos(_snowman, 75, _snowman));
    }

    private void generateEndGateway(BlockPos blockPos) {
        this.world.syncWorldEvent(3000, blockPos, 0);
        ConfiguredFeatures.END_GATEWAY_DELAYED.generate(this.world, this.world.getChunkManager().getChunkGenerator(), new Random(), blockPos);
    }

    private void generateEndPortal(boolean previouslyKilled) {
        EndPortalFeature endPortalFeature = new EndPortalFeature(previouslyKilled);
        if (this.exitPortalLocation == null) {
            this.exitPortalLocation = this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPortalFeature.ORIGIN).down();
            while (this.world.getBlockState(this.exitPortalLocation).isOf(Blocks.BEDROCK) && this.exitPortalLocation.getY() > this.world.getSeaLevel()) {
                this.exitPortalLocation = this.exitPortalLocation.down();
            }
        }
        endPortalFeature.configure(FeatureConfig.DEFAULT).generate(this.world, this.world.getChunkManager().getChunkGenerator(), new Random(), this.exitPortalLocation);
    }

    private EnderDragonEntity createDragon() {
        this.world.getWorldChunk(new BlockPos(0, 128, 0));
        EnderDragonEntity enderDragonEntity = EntityType.ENDER_DRAGON.create(this.world);
        enderDragonEntity.getPhaseManager().setPhase(PhaseType.HOLDING_PATTERN);
        enderDragonEntity.refreshPositionAndAngles(0.0, 128.0, 0.0, this.world.random.nextFloat() * 360.0f, 0.0f);
        this.world.spawnEntity(enderDragonEntity);
        this.dragonUuid = enderDragonEntity.getUuid();
        return enderDragonEntity;
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
            Entity entity = this.world.getEntity(this.dragonUuid);
            if (entity instanceof EnderDragonEntity) {
                ((EnderDragonEntity)entity).crystalDestroyed(enderCrystal, enderCrystal.getBlockPos(), source);
            }
        }
    }

    public boolean hasPreviouslyKilled() {
        return this.previouslyKilled;
    }

    public void respawnDragon() {
        if (this.dragonKilled && this.dragonSpawnState == null) {
            Object object;
            BlockPos _snowman2 = this.exitPortalLocation;
            if (_snowman2 == null) {
                LOGGER.debug("Tried to respawn, but need to find the portal first.");
                object = this.findEndPortal();
                if (object == null) {
                    LOGGER.debug("Couldn't find a portal, so we made one.");
                    this.generateEndPortal(true);
                } else {
                    LOGGER.debug("Found the exit portal & temporarily using it.");
                }
                _snowman2 = this.exitPortalLocation;
            }
            object = Lists.newArrayList();
            BlockPos _snowman3 = _snowman2.up(1);
            for (Direction direction : Direction.Type.HORIZONTAL) {
                List<EndCrystalEntity> list = this.world.getNonSpectatingEntities(EndCrystalEntity.class, new Box(_snowman3.offset(direction, 2)));
                if (list.isEmpty()) {
                    return;
                }
                object.addAll(list);
            }
            LOGGER.debug("Found all crystals, respawning dragon.");
            this.respawnDragon((List<EndCrystalEntity>)object);
        }
    }

    private void respawnDragon(List<EndCrystalEntity> crystals) {
        if (this.dragonKilled && this.dragonSpawnState == null) {
            BlockPattern.Result result = this.findEndPortal();
            while (result != null) {
                for (int i = 0; i < this.endPortalPattern.getWidth(); ++i) {
                    for (_snowman = 0; _snowman < this.endPortalPattern.getHeight(); ++_snowman) {
                        for (_snowman = 0; _snowman < this.endPortalPattern.getDepth(); ++_snowman) {
                            CachedBlockPosition cachedBlockPosition = result.translate(i, _snowman, _snowman);
                            if (!cachedBlockPosition.getBlockState().isOf(Blocks.BEDROCK) && !cachedBlockPosition.getBlockState().isOf(Blocks.END_PORTAL)) continue;
                            this.world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.END_STONE.getDefaultState());
                        }
                    }
                }
                result = this.findEndPortal();
            }
            this.dragonSpawnState = EnderDragonSpawnState.START;
            this.spawnStateTimer = 0;
            this.generateEndPortal(false);
            this.crystals = crystals;
        }
    }

    public void resetEndCrystals() {
        for (EndSpikeFeature.Spike spike : EndSpikeFeature.getSpikes(this.world)) {
            List<EndCrystalEntity> list = this.world.getNonSpectatingEntities(EndCrystalEntity.class, spike.getBoundingBox());
            for (EndCrystalEntity endCrystalEntity : list) {
                endCrystalEntity.setInvulnerable(false);
                endCrystalEntity.setBeamTarget(null);
            }
        }
    }
}

