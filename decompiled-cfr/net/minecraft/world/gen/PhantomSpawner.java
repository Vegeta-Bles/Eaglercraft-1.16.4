/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.gen.Spawner;

public class PhantomSpawner
implements Spawner {
    private int ticksUntilNextSpawn;

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (!spawnMonsters) {
            return 0;
        }
        if (!world.getGameRules().getBoolean(GameRules.DO_INSOMNIA)) {
            return 0;
        }
        Random random = world.random;
        --this.ticksUntilNextSpawn;
        if (this.ticksUntilNextSpawn > 0) {
            return 0;
        }
        this.ticksUntilNextSpawn += (60 + random.nextInt(60)) * 20;
        if (world.getAmbientDarkness() < 5 && world.getDimension().hasSkyLight()) {
            return 0;
        }
        int _snowman2 = 0;
        for (PlayerEntity playerEntity : world.getPlayers()) {
            LocalDifficulty localDifficulty;
            if (playerEntity.isSpectator()) continue;
            BlockPos blockPos = playerEntity.getBlockPos();
            if (world.getDimension().hasSkyLight() && (blockPos.getY() < world.getSeaLevel() || !world.isSkyVisible(blockPos)) || !(localDifficulty = world.getLocalDifficulty(blockPos)).isHarderThan(random.nextFloat() * 3.0f)) continue;
            ServerStatHandler _snowman3 = ((ServerPlayerEntity)playerEntity).getStatHandler();
            int _snowman4 = MathHelper.clamp(_snowman3.getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
            int _snowman5 = 24000;
            if (random.nextInt(_snowman4) < 72000 || !SpawnHelper.isClearForSpawn(world, _snowman = blockPos.up(20 + random.nextInt(15)).east(-10 + random.nextInt(21)).south(-10 + random.nextInt(21)), _snowman = world.getBlockState(_snowman), _snowman = world.getFluidState(_snowman), EntityType.PHANTOM)) continue;
            EntityData _snowman6 = null;
            int _snowman7 = 1 + random.nextInt(localDifficulty.getGlobalDifficulty().getId() + 1);
            for (int i = 0; i < _snowman7; ++i) {
                PhantomEntity phantomEntity = EntityType.PHANTOM.create(world);
                phantomEntity.refreshPositionAndAngles(_snowman, 0.0f, 0.0f);
                _snowman6 = phantomEntity.initialize(world, localDifficulty, SpawnReason.NATURAL, _snowman6, null);
                world.spawnEntityAndPassengers(phantomEntity);
            }
            _snowman2 += _snowman7;
        }
        return _snowman2;
    }
}

