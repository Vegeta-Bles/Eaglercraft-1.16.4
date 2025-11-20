/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.command.argument;

import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;

public class BlockStateArgument
implements Predicate<CachedBlockPosition> {
    private final BlockState state;
    private final Set<Property<?>> properties;
    @Nullable
    private final CompoundTag data;

    public BlockStateArgument(BlockState state, Set<Property<?>> properties, @Nullable CompoundTag data) {
        this.state = state;
        this.properties = properties;
        this.data = data;
    }

    public BlockState getBlockState() {
        return this.state;
    }

    @Override
    public boolean test(CachedBlockPosition cachedBlockPosition2) {
        BlockState blockState = cachedBlockPosition2.getBlockState();
        if (!blockState.isOf(this.state.getBlock())) {
            return false;
        }
        for (Property<?> property : this.properties) {
            if (blockState.get(property) == this.state.get(property)) continue;
            return false;
        }
        if (this.data != null) {
            CachedBlockPosition cachedBlockPosition2;
            BlockEntity _snowman2 = cachedBlockPosition2.getBlockEntity();
            return _snowman2 != null && NbtHelper.matches(this.data, _snowman2.toTag(new CompoundTag()), true);
        }
        return true;
    }

    public boolean setBlockState(ServerWorld serverWorld, BlockPos blockPos, int n) {
        BlockState blockState = Block.postProcessState(this.state, serverWorld, blockPos);
        if (blockState.isAir()) {
            blockState = this.state;
        }
        if (!serverWorld.setBlockState(blockPos, blockState, n)) {
            return false;
        }
        if (this.data != null && (_snowman = serverWorld.getBlockEntity(blockPos)) != null) {
            CompoundTag compoundTag = this.data.copy();
            compoundTag.putInt("x", blockPos.getX());
            compoundTag.putInt("y", blockPos.getY());
            compoundTag.putInt("z", blockPos.getZ());
            _snowman.fromTag(blockState, compoundTag);
        }
        return true;
    }

    @Override
    public /* synthetic */ boolean test(Object context) {
        return this.test((CachedBlockPosition)context);
    }
}

