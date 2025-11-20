/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Spawner;

public class PillagerSpawner
implements Spawner {
    private int ticksUntilNextSpawn;

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (!spawnMonsters) {
            return 0;
        }
        if (!world.getGameRules().getBoolean(GameRules.DO_PATROL_SPAWNING)) {
            return 0;
        }
        Random random = world.random;
        --this.ticksUntilNextSpawn;
        if (this.ticksUntilNextSpawn > 0) {
            return 0;
        }
        this.ticksUntilNextSpawn += 12000 + random.nextInt(1200);
        long _snowman2 = world.getTimeOfDay() / 24000L;
        if (_snowman2 < 5L || !world.isDay()) {
            return 0;
        }
        if (random.nextInt(5) != 0) {
            return 0;
        }
        int _snowman3 = world.getPlayers().size();
        if (_snowman3 < 1) {
            return 0;
        }
        PlayerEntity _snowman4 = world.getPlayers().get(random.nextInt(_snowman3));
        if (_snowman4.isSpectator()) {
            return 0;
        }
        if (world.isNearOccupiedPointOfInterest(_snowman4.getBlockPos(), 2)) {
            return 0;
        }
        int _snowman5 = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
        int _snowman6 = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
        BlockPos.Mutable _snowman7 = _snowman4.getBlockPos().mutableCopy().move(_snowman5, 0, _snowman6);
        if (!world.isRegionLoaded(_snowman7.getX() - 10, _snowman7.getY() - 10, _snowman7.getZ() - 10, _snowman7.getX() + 10, _snowman7.getY() + 10, _snowman7.getZ() + 10)) {
            return 0;
        }
        Biome _snowman8 = world.getBiome(_snowman7);
        Biome.Category _snowman9 = _snowman8.getCategory();
        if (_snowman9 == Biome.Category.MUSHROOM) {
            return 0;
        }
        int _snowman10 = 0;
        int _snowman11 = (int)Math.ceil(world.getLocalDifficulty(_snowman7).getLocalDifficulty()) + 1;
        for (int i = 0; i < _snowman11; ++i) {
            ++_snowman10;
            _snowman7.setY(world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, _snowman7).getY());
            if (i == 0) {
                if (!this.spawnPillager(world, _snowman7, random, true)) {
                    break;
                }
            } else {
                this.spawnPillager(world, _snowman7, random, false);
            }
            _snowman7.setX(_snowman7.getX() + random.nextInt(5) - random.nextInt(5));
            _snowman7.setZ(_snowman7.getZ() + random.nextInt(5) - random.nextInt(5));
        }
        return _snowman10;
    }

    private boolean spawnPillager(ServerWorld world, BlockPos pos, Random random, boolean captain) {
        BlockState blockState = world.getBlockState(pos);
        if (!SpawnHelper.isClearForSpawn(world, pos, blockState, blockState.getFluidState(), EntityType.PILLAGER)) {
            return false;
        }
        if (!PatrolEntity.canSpawn(EntityType.PILLAGER, world, SpawnReason.PATROL, pos, random)) {
            return false;
        }
        PatrolEntity _snowman2 = EntityType.PILLAGER.create(world);
        if (_snowman2 != null) {
            if (captain) {
                _snowman2.setPatrolLeader(true);
                _snowman2.setRandomPatrolTarget();
            }
            _snowman2.updatePosition(pos.getX(), pos.getY(), pos.getZ());
            _snowman2.initialize(world, world.getLocalDifficulty(pos), SpawnReason.PATROL, null, null);
            world.spawnEntityAndPassengers(_snowman2);
            return true;
        }
        return false;
    }
}

