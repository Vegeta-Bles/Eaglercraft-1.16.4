/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.entity.decoration;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;

public abstract class AbstractDecorationEntity
extends Entity {
    protected static final Predicate<Entity> PREDICATE = entity -> entity instanceof AbstractDecorationEntity;
    private int obstructionCheckCounter;
    protected BlockPos attachmentPos;
    protected Direction facing = Direction.SOUTH;

    protected AbstractDecorationEntity(EntityType<? extends AbstractDecorationEntity> entityType, World world) {
        super(entityType, world);
    }

    protected AbstractDecorationEntity(EntityType<? extends AbstractDecorationEntity> type, World world, BlockPos pos) {
        this(type, world);
        this.attachmentPos = pos;
    }

    @Override
    protected void initDataTracker() {
    }

    protected void setFacing(Direction facing) {
        Validate.notNull((Object)facing);
        Validate.isTrue((boolean)facing.getAxis().isHorizontal());
        this.facing = facing;
        this.prevYaw = this.yaw = (float)(this.facing.getHorizontal() * 90);
        this.updateAttachmentPosition();
    }

    protected void updateAttachmentPosition() {
        if (this.facing == null) {
            return;
        }
        double d = (double)this.attachmentPos.getX() + 0.5;
        _snowman = (double)this.attachmentPos.getY() + 0.5;
        _snowman = (double)this.attachmentPos.getZ() + 0.5;
        _snowman = 0.46875;
        _snowman = this.method_6893(this.getWidthPixels());
        _snowman = this.method_6893(this.getHeightPixels());
        d -= (double)this.facing.getOffsetX() * 0.46875;
        _snowman -= (double)this.facing.getOffsetZ() * 0.46875;
        Direction _snowman2 = this.facing.rotateYCounterclockwise();
        this.setPos(d += _snowman * (double)_snowman2.getOffsetX(), _snowman += _snowman, _snowman += _snowman * (double)_snowman2.getOffsetZ());
        _snowman = this.getWidthPixels();
        _snowman = this.getHeightPixels();
        _snowman = this.getWidthPixels();
        if (this.facing.getAxis() == Direction.Axis.Z) {
            _snowman = 1.0;
        } else {
            _snowman = 1.0;
        }
        this.setBoundingBox(new Box(d - (_snowman /= 32.0), _snowman - (_snowman /= 32.0), _snowman - (_snowman /= 32.0), d + _snowman, _snowman + _snowman, _snowman + _snowman));
    }

    private double method_6893(int n) {
        return n % 32 == 0 ? 0.5 : 0.0;
    }

    @Override
    public void tick() {
        if (!this.world.isClient) {
            if (this.getY() < -64.0) {
                this.destroy();
            }
            if (this.obstructionCheckCounter++ == 100) {
                this.obstructionCheckCounter = 0;
                if (!this.removed && !this.canStayAttached()) {
                    this.remove();
                    this.onBreak(null);
                }
            }
        }
    }

    public boolean canStayAttached() {
        if (!this.world.isSpaceEmpty(this)) {
            return false;
        }
        int n = Math.max(1, this.getWidthPixels() / 16);
        _snowman = Math.max(1, this.getHeightPixels() / 16);
        BlockPos _snowman2 = this.attachmentPos.offset(this.facing.getOpposite());
        Direction _snowman3 = this.facing.rotateYCounterclockwise();
        BlockPos.Mutable _snowman4 = new BlockPos.Mutable();
        for (_snowman = 0; _snowman < n; ++_snowman) {
            for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                _snowman = (n - 1) / -2;
                _snowman = (_snowman - 1) / -2;
                _snowman4.set(_snowman2).move(_snowman3, _snowman + _snowman).move(Direction.UP, _snowman + _snowman);
                BlockState blockState = this.world.getBlockState(_snowman4);
                if (blockState.getMaterial().isSolid() || AbstractRedstoneGateBlock.isRedstoneGate(blockState)) continue;
                return false;
            }
        }
        return this.world.getOtherEntities(this, this.getBoundingBox(), PREDICATE).isEmpty();
    }

    @Override
    public boolean collides() {
        return true;
    }

    @Override
    public boolean handleAttack(Entity attacker) {
        if (attacker instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)attacker;
            if (!this.world.canPlayerModifyAt(playerEntity, this.attachmentPos)) {
                return true;
            }
            return this.damage(DamageSource.player(playerEntity), 0.0f);
        }
        return false;
    }

    @Override
    public Direction getHorizontalFacing() {
        return this.facing;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (!this.removed && !this.world.isClient) {
            this.remove();
            this.scheduleVelocityUpdate();
            this.onBreak(source.getAttacker());
        }
        return true;
    }

    @Override
    public void move(MovementType type, Vec3d movement) {
        if (!this.world.isClient && !this.removed && movement.lengthSquared() > 0.0) {
            this.remove();
            this.onBreak(null);
        }
    }

    @Override
    public void addVelocity(double deltaX, double deltaY, double deltaZ) {
        if (!this.world.isClient && !this.removed && deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ > 0.0) {
            this.remove();
            this.onBreak(null);
        }
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        BlockPos blockPos = this.getDecorationBlockPos();
        tag.putInt("TileX", blockPos.getX());
        tag.putInt("TileY", blockPos.getY());
        tag.putInt("TileZ", blockPos.getZ());
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        this.attachmentPos = new BlockPos(tag.getInt("TileX"), tag.getInt("TileY"), tag.getInt("TileZ"));
    }

    public abstract int getWidthPixels();

    public abstract int getHeightPixels();

    public abstract void onBreak(@Nullable Entity var1);

    public abstract void onPlace();

    @Override
    public ItemEntity dropStack(ItemStack stack, float yOffset) {
        ItemEntity itemEntity = new ItemEntity(this.world, this.getX() + (double)((float)this.facing.getOffsetX() * 0.15f), this.getY() + (double)yOffset, this.getZ() + (double)((float)this.facing.getOffsetZ() * 0.15f), stack);
        itemEntity.setToDefaultPickupDelay();
        this.world.spawnEntity(itemEntity);
        return itemEntity;
    }

    @Override
    protected boolean shouldSetPositionOnLoad() {
        return false;
    }

    @Override
    public void updatePosition(double x, double y, double z) {
        this.attachmentPos = new BlockPos(x, y, z);
        this.updateAttachmentPosition();
        this.velocityDirty = true;
    }

    public BlockPos getDecorationBlockPos() {
        return this.attachmentPos;
    }

    @Override
    public float applyRotation(BlockRotation rotation) {
        if (this.facing.getAxis() != Direction.Axis.Y) {
            switch (rotation) {
                case CLOCKWISE_180: {
                    this.facing = this.facing.getOpposite();
                    break;
                }
                case COUNTERCLOCKWISE_90: {
                    this.facing = this.facing.rotateYCounterclockwise();
                    break;
                }
                case CLOCKWISE_90: {
                    this.facing = this.facing.rotateYClockwise();
                    break;
                }
            }
        }
        float f = MathHelper.wrapDegrees(this.yaw);
        switch (rotation) {
            case CLOCKWISE_180: {
                return f + 180.0f;
            }
            case COUNTERCLOCKWISE_90: {
                return f + 90.0f;
            }
            case CLOCKWISE_90: {
                return f + 270.0f;
            }
        }
        return f;
    }

    @Override
    public float applyMirror(BlockMirror mirror) {
        return this.applyRotation(mirror.getRotation(this.facing));
    }

    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
    }

    @Override
    public void calculateDimensions() {
    }
}

