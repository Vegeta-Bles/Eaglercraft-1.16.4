/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.gen;

import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.GameRules;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.gen.Spawner;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public class CatSpawner
implements Spawner {
    private int ticksUntilNextSpawn;

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (!spawnAnimals || !world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
            return 0;
        }
        --this.ticksUntilNextSpawn;
        if (this.ticksUntilNextSpawn > 0) {
            return 0;
        }
        this.ticksUntilNextSpawn = 1200;
        ServerPlayerEntity serverPlayerEntity = world.getRandomAlivePlayer();
        if (serverPlayerEntity == null) {
            return 0;
        }
        Random _snowman2 = world.random;
        int _snowman3 = (8 + _snowman2.nextInt(24)) * (_snowman2.nextBoolean() ? -1 : 1);
        int _snowman4 = (8 + _snowman2.nextInt(24)) * (_snowman2.nextBoolean() ? -1 : 1);
        BlockPos _snowman5 = serverPlayerEntity.getBlockPos().add(_snowman3, 0, _snowman4);
        if (!world.isRegionLoaded(_snowman5.getX() - 10, _snowman5.getY() - 10, _snowman5.getZ() - 10, _snowman5.getX() + 10, _snowman5.getY() + 10, _snowman5.getZ() + 10)) {
            return 0;
        }
        if (SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, world, _snowman5, EntityType.CAT)) {
            if (world.isNearOccupiedPointOfInterest(_snowman5, 2)) {
                return this.spawnInHouse(world, _snowman5);
            }
            if (world.getStructureAccessor().getStructureAt(_snowman5, true, StructureFeature.SWAMP_HUT).hasChildren()) {
                return this.spawnInSwampHut(world, _snowman5);
            }
        }
        return 0;
    }

    private int spawnInHouse(ServerWorld world, BlockPos pos) {
        int n = 48;
        if (world.getPointOfInterestStorage().count(PointOfInterestType.HOME.getCompletionCondition(), pos, 48, PointOfInterestStorage.OccupationStatus.IS_OCCUPIED) > 4L && (_snowman = world.getNonSpectatingEntities(CatEntity.class, new Box(pos).expand(48.0, 8.0, 48.0))).size() < 5) {
            return this.spawn(pos, world);
        }
        return 0;
    }

    private int spawnInSwampHut(ServerWorld world, BlockPos pos) {
        int n = 16;
        List<CatEntity> _snowman2 = world.getNonSpectatingEntities(CatEntity.class, new Box(pos).expand(16.0, 8.0, 16.0));
        if (_snowman2.size() < 1) {
            return this.spawn(pos, world);
        }
        return 0;
    }

    private int spawn(BlockPos pos, ServerWorld world) {
        CatEntity catEntity = EntityType.CAT.create(world);
        if (catEntity == null) {
            return 0;
        }
        catEntity.initialize(world, world.getLocalDifficulty(pos), SpawnReason.NATURAL, null, null);
        catEntity.refreshPositionAndAngles(pos, 0.0f, 0.0f);
        world.spawnEntityAndPassengers(catEntity);
        return 1;
    }
}

