/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.village.raid;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
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
    private Status status;
    private int finishCooldown;
    private Optional<BlockPos> preCalculatedRavagerSpawnLocation = Optional.empty();

    public Raid(int id, ServerWorld world, BlockPos pos) {
        this.id = id;
        this.world = world;
        this.active = true;
        this.preRaidTicks = 300;
        this.bar.setPercent(0.0f);
        this.center = pos;
        this.waveCount = this.getMaxWaves(world.getDifficulty());
        this.status = Status.ONGOING;
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
        this.status = Status.fromName(tag.getString("Status"));
        this.heroesOfTheVillage.clear();
        if (tag.contains("HeroesOfTheVillage", 9)) {
            ListTag listTag = tag.getList("HeroesOfTheVillage", 11);
            for (int i = 0; i < listTag.size(); ++i) {
                this.heroesOfTheVillage.add(NbtHelper.toUuid(listTag.get(i)));
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
        return this.status == Status.STOPPED;
    }

    public boolean hasWon() {
        return this.status == Status.VICTORY;
    }

    public boolean hasLost() {
        return this.status == Status.LOSS;
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
            BlockPos blockPos = player.getBlockPos();
            return player.isAlive() && this.world.getRaidAt(blockPos) == this;
        };
    }

    private void updateBarToPlayers() {
        HashSet hashSet = Sets.newHashSet(this.bar.getPlayers());
        List<ServerPlayerEntity> _snowman2 = this.world.getPlayers(this.isInRaidDistance());
        for (ServerPlayerEntity _snowman3 : _snowman2) {
            if (hashSet.contains(_snowman3)) continue;
            this.bar.addPlayer(_snowman3);
        }
        for (ServerPlayerEntity _snowman3 : hashSet) {
            if (_snowman2.contains(_snowman3)) continue;
            this.bar.removePlayer(_snowman3);
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
            this.badOmenLevel += player.getStatusEffect(StatusEffects.BAD_OMEN).getAmplifier() + 1;
            this.badOmenLevel = MathHelper.clamp(this.badOmenLevel, 0, this.getMaxAcceptableBadOmenLevel());
        }
        player.removeStatusEffect(StatusEffects.BAD_OMEN);
    }

    public void invalidate() {
        this.active = false;
        this.bar.clearPlayers();
        this.status = Status.STOPPED;
    }

    public void tick() {
        if (this.hasStopped()) {
            return;
        }
        if (this.status == Status.ONGOING) {
            int _snowman3;
            boolean bl = this.active;
            this.active = this.world.isChunkLoaded(this.center);
            if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
                this.invalidate();
                return;
            }
            if (bl != this.active) {
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
                    this.status = Status.LOSS;
                } else {
                    this.invalidate();
                }
            }
            ++this.ticksActive;
            if (this.ticksActive >= 48000L) {
                this.invalidate();
                return;
            }
            int _snowman2 = this.getRaiderCount();
            if (_snowman2 == 0 && this.shouldSpawnMoreGroups()) {
                if (this.preRaidTicks > 0) {
                    bl2 = this.preCalculatedRavagerSpawnLocation.isPresent();
                    int n = _snowman3 = !bl2 && this.preRaidTicks % 5 == 0 ? 1 : 0;
                    if (bl2 && !this.world.getChunkManager().shouldTickChunk(new ChunkPos(this.preCalculatedRavagerSpawnLocation.get()))) {
                        _snowman3 = 1;
                    }
                    if (_snowman3 != 0) {
                        _snowman = 0;
                        if (this.preRaidTicks < 100) {
                            _snowman = 1;
                        } else if (this.preRaidTicks < 40) {
                            _snowman = 2;
                        }
                        this.preCalculatedRavagerSpawnLocation = this.preCalculateRavagerSpawnLocation(_snowman);
                    }
                    if (this.preRaidTicks == 300 || this.preRaidTicks % 20 == 0) {
                        this.updateBarToPlayers();
                    }
                    --this.preRaidTicks;
                    this.bar.setPercent(MathHelper.clamp((float)(300 - this.preRaidTicks) / 300.0f, 0.0f, 1.0f));
                } else if (this.preRaidTicks == 0 && this.wavesSpawned > 0) {
                    this.preRaidTicks = 300;
                    this.bar.setName(EVENT_TEXT);
                    return;
                }
            }
            if (this.ticksActive % 20L == 0L) {
                this.updateBarToPlayers();
                this.removeObsoleteRaiders();
                if (_snowman2 > 0) {
                    if (_snowman2 <= 2) {
                        this.bar.setName(EVENT_TEXT.shallowCopy().append(" - ").append(new TranslatableText("event.minecraft.raid.raiders_remaining", _snowman2)));
                    } else {
                        this.bar.setName(EVENT_TEXT);
                    }
                } else {
                    this.bar.setName(EVENT_TEXT);
                }
            }
            boolean bl2 = false;
            _snowman3 = 0;
            while (this.canSpawnRaiders()) {
                BlockPos blockPos = _snowman = this.preCalculatedRavagerSpawnLocation.isPresent() ? this.preCalculatedRavagerSpawnLocation.get() : this.getRavagerSpawnLocation(_snowman3, 20);
                if (_snowman != null) {
                    this.started = true;
                    this.spawnNextWave(_snowman);
                    if (!bl2) {
                        this.playRaidHorn(_snowman);
                        bl2 = true;
                    }
                } else {
                    ++_snowman3;
                }
                if (_snowman3 <= 3) continue;
                this.invalidate();
                break;
            }
            if (this.hasStarted() && !this.shouldSpawnMoreGroups() && _snowman2 == 0) {
                if (this.postRaidTicks < 40) {
                    ++this.postRaidTicks;
                } else {
                    this.status = Status.VICTORY;
                    for (UUID uUID : this.heroesOfTheVillage) {
                        Entity entity = this.world.getEntity(uUID);
                        if (!(entity instanceof LivingEntity) || entity.isSpectator()) continue;
                        LivingEntity _snowman4 = (LivingEntity)entity;
                        _snowman4.addStatusEffect(new StatusEffectInstance(StatusEffects.HERO_OF_THE_VILLAGE, 48000, this.badOmenLevel - 1, false, false, true));
                        if (!(_snowman4 instanceof ServerPlayerEntity)) continue;
                        ServerPlayerEntity _snowman5 = (ServerPlayerEntity)_snowman4;
                        _snowman5.incrementStat(Stats.RAID_WIN);
                        Criteria.HERO_OF_THE_VILLAGE.trigger(_snowman5);
                    }
                }
            }
            this.markDirty();
        } else if (this.isFinished()) {
            ++this.finishCooldown;
            if (this.finishCooldown >= 600) {
                this.invalidate();
                return;
            }
            if (this.finishCooldown % 20 == 0) {
                this.updateBarToPlayers();
                this.bar.setVisible(true);
                if (this.hasWon()) {
                    this.bar.setPercent(0.0f);
                    this.bar.setName(VICTORY_TITLE);
                } else {
                    this.bar.setName(DEFEAT_TITLE);
                }
            }
        }
    }

    private void moveRaidCenter() {
        Stream<ChunkSectionPos> stream = ChunkSectionPos.stream(ChunkSectionPos.from(this.center), 2);
        stream.filter(this.world::isNearOccupiedPointOfInterest).map(ChunkSectionPos::getCenterPos).min(Comparator.comparingDouble(blockPos -> blockPos.getSquaredDistance(this.center))).ifPresent(this::setCenter);
    }

    private Optional<BlockPos> preCalculateRavagerSpawnLocation(int proximity) {
        for (int i = 0; i < 3; ++i) {
            BlockPos blockPos = this.getRavagerSpawnLocation(proximity, 1);
            if (blockPos == null) continue;
            return Optional.of(blockPos);
        }
        return Optional.empty();
    }

    private boolean shouldSpawnMoreGroups() {
        if (this.hasExtraWave()) {
            return !this.hasSpawnedExtraWave();
        }
        return !this.hasSpawnedFinalWave();
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
        Iterator<Set<RaiderEntity>> iterator = this.waveToRaiders.values().iterator();
        HashSet _snowman2 = Sets.newHashSet();
        while (iterator.hasNext()) {
            Set<RaiderEntity> set = iterator.next();
            for (RaiderEntity raiderEntity : set) {
                BlockPos blockPos = raiderEntity.getBlockPos();
                if (raiderEntity.removed || raiderEntity.world.getRegistryKey() != this.world.getRegistryKey() || this.center.getSquaredDistance(blockPos) >= 12544.0) {
                    _snowman2.add(raiderEntity);
                    continue;
                }
                if (raiderEntity.age <= 600) continue;
                if (this.world.getEntity(raiderEntity.getUuid()) == null) {
                    _snowman2.add(raiderEntity);
                }
                if (!this.world.isNearOccupiedPointOfInterest(blockPos) && raiderEntity.getDespawnCounter() > 2400) {
                    raiderEntity.setOutOfRaidCounter(raiderEntity.getOutOfRaidCounter() + 1);
                }
                if (raiderEntity.getOutOfRaidCounter() < 30) continue;
                _snowman2.add(raiderEntity);
            }
        }
        for (RaiderEntity raiderEntity : _snowman2) {
            this.removeFromWave(raiderEntity, true);
        }
    }

    private void playRaidHorn(BlockPos pos) {
        float f = 13.0f;
        int _snowman2 = 64;
        Collection<ServerPlayerEntity> _snowman3 = this.bar.getPlayers();
        for (ServerPlayerEntity serverPlayerEntity : this.world.getPlayers()) {
            Vec3d vec3d = serverPlayerEntity.getPos();
            _snowman = Vec3d.ofCenter(pos);
            float _snowman4 = MathHelper.sqrt((_snowman.x - vec3d.x) * (_snowman.x - vec3d.x) + (_snowman.z - vec3d.z) * (_snowman.z - vec3d.z));
            double _snowman5 = vec3d.x + (double)(13.0f / _snowman4) * (_snowman.x - vec3d.x);
            double _snowman6 = vec3d.z + (double)(13.0f / _snowman4) * (_snowman.z - vec3d.z);
            if (!(_snowman4 <= 64.0f) && !_snowman3.contains(serverPlayerEntity)) continue;
            serverPlayerEntity.networkHandler.sendPacket(new PlaySoundS2CPacket(SoundEvents.EVENT_RAID_HORN, SoundCategory.NEUTRAL, _snowman5, serverPlayerEntity.getY(), _snowman6, 64.0f, 1.0f));
        }
    }

    private void spawnNextWave(BlockPos pos) {
        boolean bl = false;
        int _snowman2 = this.wavesSpawned + 1;
        this.totalHealth = 0.0f;
        LocalDifficulty _snowman3 = this.world.getLocalDifficulty(pos);
        _snowman = this.isSpawningExtraWave();
        for (Member member : Member.VALUES) {
            int n = this.getCount(member, _snowman2, _snowman) + this.getBonusCount(member, this.random, _snowman2, _snowman3, _snowman);
            _snowman = 0;
            for (_snowman = 0; _snowman < n; ++_snowman) {
                RaiderEntity raiderEntity = (RaiderEntity)member.type.create(this.world);
                if (!bl && raiderEntity.canLead()) {
                    raiderEntity.setPatrolLeader(true);
                    this.setWaveCaptain(_snowman2, raiderEntity);
                    bl = true;
                }
                this.addRaider(_snowman2, raiderEntity, pos, false);
                if (member.type != EntityType.RAVAGER) continue;
                _snowman = null;
                if (_snowman2 == this.getMaxWaves(Difficulty.NORMAL)) {
                    _snowman = EntityType.PILLAGER.create(this.world);
                } else if (_snowman2 >= this.getMaxWaves(Difficulty.HARD)) {
                    _snowman = _snowman == 0 ? (RaiderEntity)EntityType.EVOKER.create(this.world) : (RaiderEntity)EntityType.VINDICATOR.create(this.world);
                }
                ++_snowman;
                if (_snowman == null) continue;
                this.addRaider(_snowman2, _snowman, pos, false);
                _snowman.refreshPositionAndAngles(pos, 0.0f, 0.0f);
                _snowman.startRiding(raiderEntity);
            }
        }
        this.preCalculatedRavagerSpawnLocation = Optional.empty();
        ++this.wavesSpawned;
        this.updateBar();
        this.markDirty();
    }

    public void addRaider(int wave, RaiderEntity raider, @Nullable BlockPos pos, boolean existing) {
        boolean bl = this.addToWave(wave, raider);
        if (bl) {
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
        this.bar.setPercent(MathHelper.clamp(this.getCurrentRaiderHealth() / this.totalHealth, 0.0f, 1.0f));
    }

    public float getCurrentRaiderHealth() {
        float f = 0.0f;
        for (Set<RaiderEntity> set : this.waveToRaiders.values()) {
            for (RaiderEntity raiderEntity : set) {
                f += raiderEntity.getHealth();
            }
        }
        return f;
    }

    private boolean canSpawnRaiders() {
        return this.preRaidTicks == 0 && (this.wavesSpawned < this.waveCount || this.isSpawningExtraWave()) && this.getRaiderCount() == 0;
    }

    public int getRaiderCount() {
        return this.waveToRaiders.values().stream().mapToInt(Set::size).sum();
    }

    public void removeFromWave(RaiderEntity entity, boolean countHealth) {
        Set<RaiderEntity> set = this.waveToRaiders.get(entity.getWave());
        if (set != null && (_snowman = set.remove(entity))) {
            if (countHealth) {
                this.totalHealth -= entity.getHealth();
            }
            entity.setRaid(null);
            this.updateBar();
            this.markDirty();
        }
    }

    private void markDirty() {
        this.world.getRaidManager().markDirty();
    }

    public static ItemStack getOminousBanner() {
        ItemStack itemStack = new ItemStack(Items.WHITE_BANNER);
        CompoundTag _snowman2 = itemStack.getOrCreateSubTag("BlockEntityTag");
        ListTag _snowman3 = new BannerPattern.Patterns().add(BannerPattern.RHOMBUS_MIDDLE, DyeColor.CYAN).add(BannerPattern.STRIPE_BOTTOM, DyeColor.LIGHT_GRAY).add(BannerPattern.STRIPE_CENTER, DyeColor.GRAY).add(BannerPattern.BORDER, DyeColor.LIGHT_GRAY).add(BannerPattern.STRIPE_MIDDLE, DyeColor.BLACK).add(BannerPattern.HALF_HORIZONTAL, DyeColor.LIGHT_GRAY).add(BannerPattern.CIRCLE_MIDDLE, DyeColor.LIGHT_GRAY).add(BannerPattern.BORDER, DyeColor.BLACK).toTag();
        _snowman2.put("Patterns", _snowman3);
        itemStack.addHideFlag(ItemStack.TooltipSection.ADDITIONAL);
        itemStack.setCustomName(new TranslatableText("block.minecraft.ominous_banner").formatted(Formatting.GOLD));
        return itemStack;
    }

    @Nullable
    public RaiderEntity getCaptain(int wave) {
        return this.waveToCaptain.get(wave);
    }

    @Nullable
    private BlockPos getRavagerSpawnLocation(int proximity, int tries) {
        int n = proximity == 0 ? 2 : 2 - proximity;
        BlockPos.Mutable _snowman2 = new BlockPos.Mutable();
        for (_snowman = 0; _snowman < tries; ++_snowman) {
            float f = this.world.random.nextFloat() * ((float)Math.PI * 2);
            int _snowman3 = this.center.getX() + MathHelper.floor(MathHelper.cos(f) * 32.0f * (float)n) + this.world.random.nextInt(5);
            int _snowman4 = this.center.getZ() + MathHelper.floor(MathHelper.sin(f) * 32.0f * (float)n) + this.world.random.nextInt(5);
            int _snowman5 = this.world.getTopY(Heightmap.Type.WORLD_SURFACE, _snowman3, _snowman4);
            _snowman2.set(_snowman3, _snowman5, _snowman4);
            if (this.world.isNearOccupiedPointOfInterest(_snowman2) && proximity < 2 || !this.world.isRegionLoaded(_snowman2.getX() - 10, _snowman2.getY() - 10, _snowman2.getZ() - 10, _snowman2.getX() + 10, _snowman2.getY() + 10, _snowman2.getZ() + 10) || !this.world.getChunkManager().shouldTickChunk(new ChunkPos(_snowman2)) || !SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, this.world, _snowman2, EntityType.RAVAGER) && (!this.world.getBlockState((BlockPos)_snowman2.down()).isOf(Blocks.SNOW) || !this.world.getBlockState(_snowman2).isAir())) continue;
            return _snowman2;
        }
        return null;
    }

    private boolean addToWave(int wave, RaiderEntity entity) {
        return this.addToWave(wave, entity, true);
    }

    public boolean addToWave(int wave, RaiderEntity entity, boolean countHealth) {
        this.waveToRaiders.computeIfAbsent(wave, n -> Sets.newHashSet());
        Set<RaiderEntity> set = this.waveToRaiders.get(wave);
        RaiderEntity _snowman2 = null;
        for (RaiderEntity raiderEntity : set) {
            if (!raiderEntity.getUuid().equals(entity.getUuid())) continue;
            _snowman2 = raiderEntity;
            break;
        }
        if (_snowman2 != null) {
            set.remove(_snowman2);
            set.add(entity);
        }
        set.add(entity);
        if (countHealth) {
            this.totalHealth += entity.getHealth();
        }
        this.updateBar();
        this.markDirty();
        return true;
    }

    public void setWaveCaptain(int wave, RaiderEntity entity) {
        this.waveToCaptain.put(wave, entity);
        entity.equipStack(EquipmentSlot.HEAD, Raid.getOminousBanner());
        entity.setEquipmentDropChance(EquipmentSlot.HEAD, 2.0f);
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

    private int getCount(Member member, int wave, boolean extra) {
        return extra ? member.countInWave[this.waveCount] : member.countInWave[wave];
    }

    private int getBonusCount(Member member, Random random, int wave, LocalDifficulty localDifficulty, boolean extra) {
        int n;
        Difficulty difficulty = localDifficulty.getGlobalDifficulty();
        boolean _snowman2 = difficulty == Difficulty.EASY;
        boolean _snowman3 = difficulty == Difficulty.NORMAL;
        switch (member) {
            case WITCH: {
                if (!_snowman2 && wave > 2 && wave != 4) {
                    n = 1;
                    break;
                }
                return 0;
            }
            case PILLAGER: 
            case VINDICATOR: {
                if (_snowman2) {
                    n = random.nextInt(2);
                    break;
                }
                if (_snowman3) {
                    n = 1;
                    break;
                }
                n = 2;
                break;
            }
            case RAVAGER: {
                n = !_snowman2 && extra ? 1 : 0;
                break;
            }
            default: {
                return 0;
            }
        }
        return n > 0 ? random.nextInt(n + 1) : 0;
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
        ListTag listTag = new ListTag();
        for (UUID uUID : this.heroesOfTheVillage) {
            listTag.add(NbtHelper.fromUuid(uUID));
        }
        tag.put("HeroesOfTheVillage", listTag);
        return tag;
    }

    public int getMaxWaves(Difficulty difficulty) {
        switch (difficulty) {
            case EASY: {
                return 3;
            }
            case NORMAL: {
                return 5;
            }
            case HARD: {
                return 7;
            }
        }
        return 0;
    }

    public float getEnchantmentChance() {
        int n = this.getBadOmenLevel();
        if (n == 2) {
            return 0.1f;
        }
        if (n == 3) {
            return 0.25f;
        }
        if (n == 4) {
            return 0.5f;
        }
        if (n == 5) {
            return 0.75f;
        }
        return 0.0f;
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

        private static final Member[] VALUES;
        private final EntityType<? extends RaiderEntity> type;
        private final int[] countInWave;

        private Member(EntityType<? extends RaiderEntity> type, int[] countInWave) {
            this.type = type;
            this.countInWave = countInWave;
        }

        static {
            VALUES = Member.values();
        }
    }

    static enum Status {
        ONGOING,
        VICTORY,
        LOSS,
        STOPPED;

        private static final Status[] VALUES;

        private static Status fromName(String string) {
            for (Status status : VALUES) {
                if (!string.equalsIgnoreCase(status.name())) continue;
                return status;
            }
            return ONGOING;
        }

        public String getName() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        static {
            VALUES = Status.values();
        }
    }
}

