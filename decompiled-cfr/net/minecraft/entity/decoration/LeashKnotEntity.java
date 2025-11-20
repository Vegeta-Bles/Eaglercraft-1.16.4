/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.decoration;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LeashKnotEntity
extends AbstractDecorationEntity {
    public LeashKnotEntity(EntityType<? extends LeashKnotEntity> entityType, World world) {
        super((EntityType<? extends AbstractDecorationEntity>)entityType, world);
    }

    public LeashKnotEntity(World world, BlockPos pos) {
        super(EntityType.LEASH_KNOT, world, pos);
        this.updatePosition((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5);
        float f = 0.125f;
        _snowman = 0.1875f;
        _snowman = 0.25f;
        this.setBoundingBox(new Box(this.getX() - 0.1875, this.getY() - 0.25 + 0.125, this.getZ() - 0.1875, this.getX() + 0.1875, this.getY() + 0.25 + 0.125, this.getZ() + 0.1875));
        this.teleporting = true;
    }

    @Override
    public void updatePosition(double x, double y, double z) {
        super.updatePosition((double)MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y) + 0.5, (double)MathHelper.floor(z) + 0.5);
    }

    @Override
    protected void updateAttachmentPosition() {
        this.setPos((double)this.attachmentPos.getX() + 0.5, (double)this.attachmentPos.getY() + 0.5, (double)this.attachmentPos.getZ() + 0.5);
    }

    @Override
    public void setFacing(Direction facing) {
    }

    @Override
    public int getWidthPixels() {
        return 9;
    }

    @Override
    public int getHeightPixels() {
        return 9;
    }

    @Override
    protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return -0.0625f;
    }

    @Override
    public boolean shouldRender(double distance) {
        return distance < 1024.0;
    }

    @Override
    public void onBreak(@Nullable Entity entity) {
        this.playSound(SoundEvents.ENTITY_LEASH_KNOT_BREAK, 1.0f, 1.0f);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (this.world.isClient) {
            return ActionResult.SUCCESS;
        }
        boolean bl = false;
        double _snowman2 = 7.0;
        List<MobEntity> _snowman3 = this.world.getNonSpectatingEntities(MobEntity.class, new Box(this.getX() - 7.0, this.getY() - 7.0, this.getZ() - 7.0, this.getX() + 7.0, this.getY() + 7.0, this.getZ() + 7.0));
        for (MobEntity mobEntity : _snowman3) {
            if (mobEntity.getHoldingEntity() != player) continue;
            mobEntity.attachLeash(this, true);
            bl = true;
        }
        if (!bl) {
            this.remove();
            if (player.abilities.creativeMode) {
                for (MobEntity mobEntity : _snowman3) {
                    if (!mobEntity.isLeashed() || mobEntity.getHoldingEntity() != this) continue;
                    mobEntity.detachLeash(true, false);
                }
            }
        }
        return ActionResult.CONSUME;
    }

    @Override
    public boolean canStayAttached() {
        return this.world.getBlockState(this.attachmentPos).getBlock().isIn(BlockTags.FENCES);
    }

    public static LeashKnotEntity getOrCreate(World world, BlockPos pos) {
        int n = pos.getX();
        _snowman = pos.getY();
        _snowman = pos.getZ();
        List<LeashKnotEntity> _snowman2 = world.getNonSpectatingEntities(LeashKnotEntity.class, new Box((double)n - 1.0, (double)_snowman - 1.0, (double)_snowman - 1.0, (double)n + 1.0, (double)_snowman + 1.0, (double)_snowman + 1.0));
        for (LeashKnotEntity leashKnotEntity : _snowman2) {
            if (!leashKnotEntity.getDecorationBlockPos().equals(pos)) continue;
            return leashKnotEntity;
        }
        LeashKnotEntity leashKnotEntity = new LeashKnotEntity(world, pos);
        world.spawnEntity(leashKnotEntity);
        leashKnotEntity.onPlace();
        return leashKnotEntity;
    }

    @Override
    public void onPlace() {
        this.playSound(SoundEvents.ENTITY_LEASH_KNOT_PLACE, 1.0f, 1.0f);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this, this.getType(), 0, this.getDecorationBlockPos());
    }

    @Override
    public Vec3d method_30951(float f) {
        return this.method_30950(f).add(0.0, 0.2, 0.0);
    }
}

