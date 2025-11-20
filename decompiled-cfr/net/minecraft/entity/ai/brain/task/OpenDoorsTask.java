/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;

public class OpenDoorsTask
extends Task<LivingEntity> {
    @Nullable
    private PathNode field_26387;
    private int field_26388;

    public OpenDoorsTask() {
        super((Map<MemoryModuleType<?>, MemoryModuleState>)ImmutableMap.of(MemoryModuleType.PATH, (Object)((Object)MemoryModuleState.VALUE_PRESENT), MemoryModuleType.DOORS_TO_CLOSE, (Object)((Object)MemoryModuleState.REGISTERED)));
    }

    @Override
    protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
        Path path = entity.getBrain().getOptionalMemory(MemoryModuleType.PATH).get();
        if (path.method_30849() || path.isFinished()) {
            return false;
        }
        if (!Objects.equals(this.field_26387, path.method_29301())) {
            this.field_26388 = 20;
            return true;
        }
        if (this.field_26388 > 0) {
            --this.field_26388;
        }
        return this.field_26388 == 0;
    }

    @Override
    protected void run(ServerWorld world, LivingEntity entity, long time) {
        BlockState blockState;
        Path path = entity.getBrain().getOptionalMemory(MemoryModuleType.PATH).get();
        this.field_26387 = path.method_29301();
        PathNode _snowman2 = path.method_30850();
        PathNode _snowman3 = path.method_29301();
        BlockPos _snowman4 = _snowman2.getPos();
        BlockState _snowman5 = world.getBlockState(_snowman4);
        if (_snowman5.isIn(BlockTags.WOODEN_DOORS)) {
            Object object = (DoorBlock)_snowman5.getBlock();
            if (!((DoorBlock)object).method_30841(_snowman5)) {
                ((DoorBlock)object).setOpen(world, _snowman5, _snowman4, true);
            }
            this.method_30767(world, entity, _snowman4);
        }
        if ((blockState = world.getBlockState((BlockPos)(object = _snowman3.getPos()))).isIn(BlockTags.WOODEN_DOORS) && !(_snowman = (DoorBlock)blockState.getBlock()).method_30841(blockState)) {
            _snowman.setOpen(world, blockState, (BlockPos)object, true);
            this.method_30767(world, entity, (BlockPos)object);
        }
        OpenDoorsTask.method_30760(world, entity, _snowman2, _snowman3);
    }

    public static void method_30760(ServerWorld serverWorld, LivingEntity livingEntity, @Nullable PathNode pathNode, @Nullable PathNode pathNode2) {
        Brain<Set<GlobalPos>> brain = livingEntity.getBrain();
        if (brain.hasMemoryModule(MemoryModuleType.DOORS_TO_CLOSE)) {
            Iterator<GlobalPos> iterator = brain.getOptionalMemory(MemoryModuleType.DOORS_TO_CLOSE).get().iterator();
            while (iterator.hasNext()) {
                GlobalPos globalPos = iterator.next();
                BlockPos _snowman2 = globalPos.getPos();
                if (pathNode != null && pathNode.getPos().equals(_snowman2) || pathNode2 != null && pathNode2.getPos().equals(_snowman2)) continue;
                if (OpenDoorsTask.method_30762(serverWorld, livingEntity, globalPos)) {
                    iterator.remove();
                    continue;
                }
                BlockState _snowman3 = serverWorld.getBlockState(_snowman2);
                if (!_snowman3.isIn(BlockTags.WOODEN_DOORS)) {
                    iterator.remove();
                    continue;
                }
                DoorBlock _snowman4 = (DoorBlock)_snowman3.getBlock();
                if (!_snowman4.method_30841(_snowman3)) {
                    iterator.remove();
                    continue;
                }
                if (OpenDoorsTask.method_30761(serverWorld, livingEntity, _snowman2)) {
                    iterator.remove();
                    continue;
                }
                _snowman4.setOpen(serverWorld, _snowman3, _snowman2, false);
                iterator.remove();
            }
        }
    }

    private static boolean method_30761(ServerWorld serverWorld, LivingEntity livingEntity3, BlockPos blockPos) {
        Brain<List<LivingEntity>> brain = livingEntity3.getBrain();
        if (!brain.hasMemoryModule(MemoryModuleType.MOBS)) {
            return false;
        }
        return brain.getOptionalMemory(MemoryModuleType.MOBS).get().stream().filter(livingEntity2 -> livingEntity2.getType() == livingEntity3.getType()).filter(livingEntity -> blockPos.isWithinDistance(livingEntity.getPos(), 2.0)).anyMatch(livingEntity -> OpenDoorsTask.method_30766(serverWorld, livingEntity, blockPos));
    }

    private static boolean method_30766(ServerWorld serverWorld, LivingEntity livingEntity, BlockPos blockPos) {
        if (!livingEntity.getBrain().hasMemoryModule(MemoryModuleType.PATH)) {
            return false;
        }
        Path path = livingEntity.getBrain().getOptionalMemory(MemoryModuleType.PATH).get();
        if (path.isFinished()) {
            return false;
        }
        PathNode _snowman2 = path.method_30850();
        if (_snowman2 == null) {
            return false;
        }
        PathNode _snowman3 = path.method_29301();
        return blockPos.equals(_snowman2.getPos()) || blockPos.equals(_snowman3.getPos());
    }

    private static boolean method_30762(ServerWorld serverWorld, LivingEntity livingEntity, GlobalPos globalPos) {
        return globalPos.getDimension() != serverWorld.getRegistryKey() || !globalPos.getPos().isWithinDistance(livingEntity.getPos(), 2.0);
    }

    private void method_30767(ServerWorld serverWorld, LivingEntity livingEntity, BlockPos blockPos) {
        Brain<?> brain = livingEntity.getBrain();
        GlobalPos _snowman2 = GlobalPos.create(serverWorld.getRegistryKey(), blockPos);
        if (brain.getOptionalMemory(MemoryModuleType.DOORS_TO_CLOSE).isPresent()) {
            brain.getOptionalMemory(MemoryModuleType.DOORS_TO_CLOSE).get().add(_snowman2);
        } else {
            brain.remember(MemoryModuleType.DOORS_TO_CLOSE, Sets.newHashSet((Object[])new GlobalPos[]{_snowman2}));
        }
    }
}

