/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.vehicle;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;

public class SpawnerMinecartEntity
extends AbstractMinecartEntity {
    private final MobSpawnerLogic logic = new MobSpawnerLogic(this){
        final /* synthetic */ SpawnerMinecartEntity field_7747;
        {
            this.field_7747 = spawnerMinecartEntity;
        }

        public void sendStatus(int status) {
            this.field_7747.world.sendEntityStatus(this.field_7747, (byte)status);
        }

        public World getWorld() {
            return this.field_7747.world;
        }

        public BlockPos getPos() {
            return this.field_7747.getBlockPos();
        }
    };

    public SpawnerMinecartEntity(EntityType<? extends SpawnerMinecartEntity> entityType, World world) {
        super(entityType, world);
    }

    public SpawnerMinecartEntity(World world, double x, double y, double z) {
        super(EntityType.SPAWNER_MINECART, world, x, y, z);
    }

    @Override
    public AbstractMinecartEntity.Type getMinecartType() {
        return AbstractMinecartEntity.Type.SPAWNER;
    }

    @Override
    public BlockState getDefaultContainedBlock() {
        return Blocks.SPAWNER.getDefaultState();
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.logic.fromTag(tag);
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        this.logic.toTag(tag);
    }

    @Override
    public void handleStatus(byte status) {
        this.logic.method_8275(status);
    }

    @Override
    public void tick() {
        super.tick();
        this.logic.update();
    }

    @Override
    public boolean entityDataRequiresOperator() {
        return true;
    }
}

