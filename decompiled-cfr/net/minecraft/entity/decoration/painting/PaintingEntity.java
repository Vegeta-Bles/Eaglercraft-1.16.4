/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.decoration.painting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.painting.PaintingMotive;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.PaintingSpawnS2CPacket;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class PaintingEntity
extends AbstractDecorationEntity {
    public PaintingMotive motive;

    public PaintingEntity(EntityType<? extends PaintingEntity> entityType, World world) {
        super((EntityType<? extends AbstractDecorationEntity>)entityType, world);
    }

    public PaintingEntity(World world, BlockPos pos, Direction direction) {
        super(EntityType.PAINTING, world, pos);
        ArrayList arrayList = Lists.newArrayList();
        int _snowman2 = 0;
        Iterator iterator = Registry.PAINTING_MOTIVE.iterator();
        while (iterator.hasNext()) {
            this.motive = paintingMotive = (PaintingMotive)iterator.next();
            this.setFacing(direction);
            if (!this.canStayAttached()) continue;
            arrayList.add(paintingMotive);
            int n = paintingMotive.getWidth() * paintingMotive.getHeight();
            if (n <= _snowman2) continue;
            _snowman2 = n;
        }
        if (!arrayList.isEmpty()) {
            Iterator iterator2 = arrayList.iterator();
            while (iterator2.hasNext()) {
                PaintingMotive paintingMotive = (PaintingMotive)iterator2.next();
                if (paintingMotive.getWidth() * paintingMotive.getHeight() >= _snowman2) continue;
                iterator2.remove();
            }
            this.motive = (PaintingMotive)arrayList.get(this.random.nextInt(arrayList.size()));
        }
        this.setFacing(direction);
    }

    public PaintingEntity(World world, BlockPos pos, Direction direction, PaintingMotive motive) {
        this(world, pos, direction);
        this.motive = motive;
        this.setFacing(direction);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        tag.putString("Motive", Registry.PAINTING_MOTIVE.getId(this.motive).toString());
        tag.putByte("Facing", (byte)this.facing.getHorizontal());
        super.writeCustomDataToTag(tag);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        this.motive = Registry.PAINTING_MOTIVE.get(Identifier.tryParse(tag.getString("Motive")));
        this.facing = Direction.fromHorizontal(tag.getByte("Facing"));
        super.readCustomDataFromTag(tag);
        this.setFacing(this.facing);
    }

    @Override
    public int getWidthPixels() {
        if (this.motive == null) {
            return 1;
        }
        return this.motive.getWidth();
    }

    @Override
    public int getHeightPixels() {
        if (this.motive == null) {
            return 1;
        }
        return this.motive.getHeight();
    }

    @Override
    public void onBreak(@Nullable Entity entity) {
        if (!this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            return;
        }
        this.playSound(SoundEvents.ENTITY_PAINTING_BREAK, 1.0f, 1.0f);
        if (entity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)entity;
            if (playerEntity.abilities.creativeMode) {
                return;
            }
        }
        this.dropItem(Items.PAINTING);
    }

    @Override
    public void onPlace() {
        this.playSound(SoundEvents.ENTITY_PAINTING_PLACE, 1.0f, 1.0f);
    }

    @Override
    public void refreshPositionAndAngles(double x, double y, double z, float yaw, float pitch) {
        this.updatePosition(x, y, z);
    }

    @Override
    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
        BlockPos blockPos = this.attachmentPos.add(x - this.getX(), y - this.getY(), z - this.getZ());
        this.updatePosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new PaintingSpawnS2CPacket(this);
    }
}

