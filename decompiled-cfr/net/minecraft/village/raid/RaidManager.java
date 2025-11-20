/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.village.raid;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.GameRules;
import net.minecraft.world.PersistentState;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public class RaidManager
extends PersistentState {
    private final Map<Integer, Raid> raids = Maps.newHashMap();
    private final ServerWorld world;
    private int nextAvailableId;
    private int currentTime;

    public RaidManager(ServerWorld world) {
        super(RaidManager.nameFor(world.getDimension()));
        this.world = world;
        this.nextAvailableId = 1;
        this.markDirty();
    }

    public Raid getRaid(int id) {
        return this.raids.get(id);
    }

    public void tick() {
        ++this.currentTime;
        Iterator<Raid> iterator = this.raids.values().iterator();
        while (iterator.hasNext()) {
            Raid raid = iterator.next();
            if (this.world.getGameRules().getBoolean(GameRules.DISABLE_RAIDS)) {
                raid.invalidate();
            }
            if (raid.hasStopped()) {
                iterator.remove();
                this.markDirty();
                continue;
            }
            raid.tick();
        }
        if (this.currentTime % 200 == 0) {
            this.markDirty();
        }
        DebugInfoSender.sendRaids(this.world, this.raids.values());
    }

    public static boolean isValidRaiderFor(RaiderEntity raider, Raid raid) {
        if (raider != null && raid != null && raid.getWorld() != null) {
            return raider.isAlive() && raider.canJoinRaid() && raider.getDespawnCounter() <= 2400 && raider.world.getDimension() == raid.getWorld().getDimension();
        }
        return false;
    }

    @Nullable
    public Raid startRaid(ServerPlayerEntity player) {
        BlockPos blockPos;
        if (player.isSpectator()) {
            return null;
        }
        if (this.world.getGameRules().getBoolean(GameRules.DISABLE_RAIDS)) {
            return null;
        }
        DimensionType dimensionType = player.world.getDimension();
        if (!dimensionType.hasRaids()) {
            return null;
        }
        BlockPos _snowman2 = player.getBlockPos();
        List _snowman3 = this.world.getPointOfInterestStorage().getInCircle(PointOfInterestType.ALWAYS_TRUE, _snowman2, 64, PointOfInterestStorage.OccupationStatus.IS_OCCUPIED).collect(Collectors.toList());
        int _snowman4 = 0;
        Vec3d _snowman5 = Vec3d.ZERO;
        for (PointOfInterest pointOfInterest : _snowman3) {
            BlockPos blockPos2 = pointOfInterest.getPos();
            _snowman5 = _snowman5.add(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
            ++_snowman4;
        }
        if (_snowman4 > 0) {
            _snowman5 = _snowman5.multiply(1.0 / (double)_snowman4);
            blockPos = new BlockPos(_snowman5);
        } else {
            blockPos = _snowman2;
        }
        Raid _snowman6 = this.getOrCreateRaid(player.getServerWorld(), blockPos);
        boolean _snowman7 = false;
        if (!_snowman6.hasStarted()) {
            if (!this.raids.containsKey(_snowman6.getRaidId())) {
                this.raids.put(_snowman6.getRaidId(), _snowman6);
            }
            _snowman7 = true;
        } else if (_snowman6.getBadOmenLevel() < _snowman6.getMaxAcceptableBadOmenLevel()) {
            _snowman7 = true;
        } else {
            player.removeStatusEffect(StatusEffects.BAD_OMEN);
            player.networkHandler.sendPacket(new EntityStatusS2CPacket(player, 43));
        }
        if (_snowman7) {
            _snowman6.start(player);
            player.networkHandler.sendPacket(new EntityStatusS2CPacket(player, 43));
            if (!_snowman6.hasSpawned()) {
                player.incrementStat(Stats.RAID_TRIGGER);
                Criteria.VOLUNTARY_EXILE.trigger(player);
            }
        }
        this.markDirty();
        return _snowman6;
    }

    private Raid getOrCreateRaid(ServerWorld world, BlockPos pos) {
        Raid raid = world.getRaidAt(pos);
        return raid != null ? raid : new Raid(this.nextId(), world, pos);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        this.nextAvailableId = tag.getInt("NextAvailableID");
        this.currentTime = tag.getInt("Tick");
        ListTag listTag = tag.getList("Raids", 10);
        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag = listTag.getCompound(i);
            Raid _snowman2 = new Raid(this.world, compoundTag);
            this.raids.put(_snowman2.getRaidId(), _snowman2);
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putInt("NextAvailableID", this.nextAvailableId);
        tag.putInt("Tick", this.currentTime);
        ListTag listTag = new ListTag();
        for (Raid raid : this.raids.values()) {
            CompoundTag compoundTag = new CompoundTag();
            raid.toTag(compoundTag);
            listTag.add(compoundTag);
        }
        tag.put("Raids", listTag);
        return tag;
    }

    public static String nameFor(DimensionType dimensionType) {
        return "raids" + dimensionType.getSuffix();
    }

    private int nextId() {
        return ++this.nextAvailableId;
    }

    @Nullable
    public Raid getRaidAt(BlockPos pos, int n) {
        Raid _snowman3 = null;
        double _snowman2 = n;
        for (Raid raid : this.raids.values()) {
            double d = raid.getCenter().getSquaredDistance(pos);
            if (!raid.isActive() || !(d < _snowman2)) continue;
            _snowman3 = raid;
            _snowman2 = d;
        }
        return _snowman3;
    }
}

