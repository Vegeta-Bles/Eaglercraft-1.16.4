/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.block.entity;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.MobSpawnerEntry;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;

public class MobSpawnerBlockEntity
extends BlockEntity
implements Tickable {
    private final MobSpawnerLogic logic = new MobSpawnerLogic(this){
        final /* synthetic */ MobSpawnerBlockEntity field_12115;
        {
            this.field_12115 = mobSpawnerBlockEntity;
        }

        public void sendStatus(int status) {
            this.field_12115.world.addSyncedBlockEvent(this.field_12115.pos, Blocks.SPAWNER, status, 0);
        }

        public World getWorld() {
            return this.field_12115.world;
        }

        public BlockPos getPos() {
            return this.field_12115.pos;
        }

        public void setSpawnEntry(MobSpawnerEntry spawnEntry) {
            super.setSpawnEntry(spawnEntry);
            if (this.getWorld() != null) {
                BlockState blockState = this.getWorld().getBlockState(this.getPos());
                this.getWorld().updateListeners(this.field_12115.pos, blockState, blockState, 4);
            }
        }
    };

    public MobSpawnerBlockEntity() {
        super(BlockEntityType.MOB_SPAWNER);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.logic.fromTag(tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        this.logic.toTag(tag);
        return tag;
    }

    @Override
    public void tick() {
        this.logic.update();
    }

    @Override
    @Nullable
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(this.pos, 1, this.toInitialChunkDataTag());
    }

    @Override
    public CompoundTag toInitialChunkDataTag() {
        CompoundTag compoundTag = this.toTag(new CompoundTag());
        compoundTag.remove("SpawnPotentials");
        return compoundTag;
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (this.logic.method_8275(type)) {
            return true;
        }
        return super.onSyncedBlockEvent(type, data);
    }

    @Override
    public boolean copyItemDataRequiresOperator() {
        return true;
    }

    public MobSpawnerLogic getLogic() {
        return this.logic;
    }
}

