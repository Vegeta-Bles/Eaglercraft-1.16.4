/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.TraderLlamaEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.Spawner;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public class WanderingTraderManager
implements Spawner {
    private final Random random = new Random();
    private final ServerWorldProperties properties;
    private int spawnTimer;
    private int spawnDelay;
    private int spawnChance;

    public WanderingTraderManager(ServerWorldProperties properties) {
        this.properties = properties;
        this.spawnTimer = 1200;
        this.spawnDelay = properties.getWanderingTraderSpawnDelay();
        this.spawnChance = properties.getWanderingTraderSpawnChance();
        if (this.spawnDelay == 0 && this.spawnChance == 0) {
            this.spawnDelay = 24000;
            properties.setWanderingTraderSpawnDelay(this.spawnDelay);
            this.spawnChance = 25;
            properties.setWanderingTraderSpawnChance(this.spawnChance);
        }
    }

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (!world.getGameRules().getBoolean(GameRules.DO_TRADER_SPAWNING)) {
            return 0;
        }
        if (--this.spawnTimer > 0) {
            return 0;
        }
        this.spawnTimer = 1200;
        this.spawnDelay -= 1200;
        this.properties.setWanderingTraderSpawnDelay(this.spawnDelay);
        if (this.spawnDelay > 0) {
            return 0;
        }
        this.spawnDelay = 24000;
        if (!world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
            return 0;
        }
        int n = this.spawnChance;
        this.spawnChance = MathHelper.clamp(this.spawnChance + 25, 25, 75);
        this.properties.setWanderingTraderSpawnChance(this.spawnChance);
        if (this.random.nextInt(100) > n) {
            return 0;
        }
        if (this.method_18018(world)) {
            this.spawnChance = 25;
            return 1;
        }
        return 0;
    }

    private boolean method_18018(ServerWorld serverWorld) {
        ServerPlayerEntity serverPlayerEntity = serverWorld.getRandomAlivePlayer();
        if (serverPlayerEntity == null) {
            return true;
        }
        if (this.random.nextInt(10) != 0) {
            return false;
        }
        BlockPos _snowman2 = serverPlayerEntity.getBlockPos();
        int _snowman3 = 48;
        PointOfInterestStorage _snowman4 = serverWorld.getPointOfInterestStorage();
        Optional<BlockPos> _snowman5 = _snowman4.getPosition(PointOfInterestType.MEETING.getCompletionCondition(), blockPos -> true, _snowman2, 48, PointOfInterestStorage.OccupationStatus.ANY);
        BlockPos _snowman6 = _snowman5.orElse(_snowman2);
        BlockPos _snowman7 = this.getNearbySpawnPos(serverWorld, _snowman6, 48);
        if (_snowman7 != null && this.doesNotSuffocateAt(serverWorld, _snowman7)) {
            if (serverWorld.method_31081(_snowman7).equals(Optional.of(BiomeKeys.THE_VOID))) {
                return false;
            }
            WanderingTraderEntity wanderingTraderEntity = EntityType.WANDERING_TRADER.spawn(serverWorld, null, null, null, _snowman7, SpawnReason.EVENT, false, false);
            if (wanderingTraderEntity != null) {
                for (int i = 0; i < 2; ++i) {
                    this.spawnLlama(serverWorld, wanderingTraderEntity, 4);
                }
                this.properties.setWanderingTraderId(wanderingTraderEntity.getUuid());
                wanderingTraderEntity.setDespawnDelay(48000);
                wanderingTraderEntity.setWanderTarget(_snowman6);
                wanderingTraderEntity.setPositionTarget(_snowman6, 16);
                return true;
            }
        }
        return false;
    }

    private void spawnLlama(ServerWorld serverWorld, WanderingTraderEntity wanderingTraderEntity, int n) {
        BlockPos blockPos = this.getNearbySpawnPos(serverWorld, wanderingTraderEntity.getBlockPos(), n);
        if (blockPos == null) {
            return;
        }
        TraderLlamaEntity _snowman2 = EntityType.TRADER_LLAMA.spawn(serverWorld, null, null, null, blockPos, SpawnReason.EVENT, false, false);
        if (_snowman2 == null) {
            return;
        }
        _snowman2.attachLeash(wanderingTraderEntity, true);
    }

    @Nullable
    private BlockPos getNearbySpawnPos(WorldView worldView, BlockPos blockPos, int n) {
        BlockPos blockPos2 = null;
        for (int i = 0; i < 10; ++i) {
            _snowman = blockPos.getX() + this.random.nextInt(n * 2) - n;
            BlockPos blockPos3 = new BlockPos(_snowman, _snowman = worldView.getTopY(Heightmap.Type.WORLD_SURFACE, _snowman, _snowman = blockPos.getZ() + this.random.nextInt(n * 2) - n), _snowman);
            if (!SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, worldView, blockPos3, EntityType.WANDERING_TRADER)) continue;
            blockPos2 = blockPos3;
            break;
        }
        return blockPos2;
    }

    private boolean doesNotSuffocateAt(BlockView blockView, BlockPos blockPos) {
        for (BlockPos blockPos2 : BlockPos.iterate(blockPos, blockPos.add(1, 2, 1))) {
            if (blockView.getBlockState(blockPos2).getCollisionShape(blockView, blockPos2).isEmpty()) continue;
            return false;
        }
        return true;
    }
}

