/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block.entity;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.PistonType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Boxes;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class PistonBlockEntity
extends BlockEntity
implements Tickable {
    private BlockState pushedBlock;
    private Direction facing;
    private boolean extending;
    private boolean source;
    private static final ThreadLocal<Direction> field_12205 = ThreadLocal.withInitial(() -> null);
    private float progress;
    private float lastProgress;
    private long savedWorldTime;
    private int field_26705;

    public PistonBlockEntity() {
        super(BlockEntityType.PISTON);
    }

    public PistonBlockEntity(BlockState pushedBlock, Direction facing, boolean extending, boolean source) {
        this();
        this.pushedBlock = pushedBlock;
        this.facing = facing;
        this.extending = extending;
        this.source = source;
    }

    @Override
    public CompoundTag toInitialChunkDataTag() {
        return this.toTag(new CompoundTag());
    }

    public boolean isExtending() {
        return this.extending;
    }

    public Direction getFacing() {
        return this.facing;
    }

    public boolean isSource() {
        return this.source;
    }

    public float getProgress(float tickDelta) {
        if (tickDelta > 1.0f) {
            tickDelta = 1.0f;
        }
        return MathHelper.lerp(tickDelta, this.lastProgress, this.progress);
    }

    public float getRenderOffsetX(float tickDelta) {
        return (float)this.facing.getOffsetX() * this.getAmountExtended(this.getProgress(tickDelta));
    }

    public float getRenderOffsetY(float tickDelta) {
        return (float)this.facing.getOffsetY() * this.getAmountExtended(this.getProgress(tickDelta));
    }

    public float getRenderOffsetZ(float tickDelta) {
        return (float)this.facing.getOffsetZ() * this.getAmountExtended(this.getProgress(tickDelta));
    }

    private float getAmountExtended(float progress) {
        return this.extending ? progress - 1.0f : 1.0f - progress;
    }

    private BlockState getHeadBlockState() {
        if (!this.isExtending() && this.isSource() && this.pushedBlock.getBlock() instanceof PistonBlock) {
            return (BlockState)((BlockState)((BlockState)Blocks.PISTON_HEAD.getDefaultState().with(PistonHeadBlock.SHORT, this.progress > 0.25f)).with(PistonHeadBlock.TYPE, this.pushedBlock.isOf(Blocks.STICKY_PISTON) ? PistonType.STICKY : PistonType.DEFAULT)).with(PistonHeadBlock.FACING, this.pushedBlock.get(PistonBlock.FACING));
        }
        return this.pushedBlock;
    }

    private void pushEntities(float nextProgress) {
        Direction direction = this.getMovementDirection();
        double _snowman2 = nextProgress - this.progress;
        VoxelShape _snowman3 = this.getHeadBlockState().getCollisionShape(this.world, this.getPos());
        if (_snowman3.isEmpty()) {
            return;
        }
        Box _snowman4 = this.offsetHeadBox(_snowman3.getBoundingBox());
        List<Entity> _snowman5 = this.world.getOtherEntities(null, Boxes.stretch(_snowman4, direction, _snowman2).union(_snowman4));
        if (_snowman5.isEmpty()) {
            return;
        }
        List<Box> _snowman6 = _snowman3.getBoundingBoxes();
        boolean _snowman7 = this.pushedBlock.isOf(Blocks.SLIME_BLOCK);
        for (Entity entity : _snowman5) {
            if (entity.getPistonBehavior() == PistonBehavior.IGNORE) continue;
            if (_snowman7) {
                if (entity instanceof ServerPlayerEntity) continue;
                Vec3d vec3d = entity.getVelocity();
                double _snowman8 = vec3d.x;
                double _snowman9 = vec3d.y;
                double _snowman10 = vec3d.z;
                switch (direction.getAxis()) {
                    case X: {
                        _snowman8 = direction.getOffsetX();
                        break;
                    }
                    case Y: {
                        _snowman9 = direction.getOffsetY();
                        break;
                    }
                    case Z: {
                        _snowman10 = direction.getOffsetZ();
                    }
                }
                entity.setVelocity(_snowman8, _snowman9, _snowman10);
            }
            double d = 0.0;
            Iterator<Box> iterator = _snowman6.iterator();
            while (!(!iterator.hasNext() || (_snowman = Boxes.stretch(this.offsetHeadBox(_snowman = iterator.next()), direction, _snowman2)).intersects(_snowman = entity.getBoundingBox()) && (d = Math.max(d, PistonBlockEntity.getIntersectionSize(_snowman, direction, _snowman))) >= _snowman2)) {
            }
            if (d <= 0.0) continue;
            d = Math.min(d, _snowman2) + 0.01;
            PistonBlockEntity.method_23672(direction, entity, d, direction);
            if (this.extending || !this.source) continue;
            this.push(entity, direction, _snowman2);
        }
    }

    private static void method_23672(Direction direction, Entity entity, double d, Direction direction2) {
        field_12205.set(direction);
        entity.move(MovementType.PISTON, new Vec3d(d * (double)direction2.getOffsetX(), d * (double)direction2.getOffsetY(), d * (double)direction2.getOffsetZ()));
        field_12205.set(null);
    }

    private void method_23674(float f) {
        if (!this.isPushingHoneyBlock()) {
            return;
        }
        Direction direction = this.getMovementDirection();
        if (!direction.getAxis().isHorizontal()) {
            return;
        }
        double _snowman2 = this.pushedBlock.getCollisionShape(this.world, this.pos).getMax(Direction.Axis.Y);
        Box _snowman3 = this.offsetHeadBox(new Box(0.0, _snowman2, 0.0, 1.0, 1.5000000999999998, 1.0));
        double _snowman4 = f - this.progress;
        List<Entity> _snowman5 = this.world.getOtherEntities(null, _snowman3, entity -> PistonBlockEntity.method_23671(_snowman3, entity));
        for (Entity entity2 : _snowman5) {
            PistonBlockEntity.method_23672(direction, entity2, _snowman4, direction);
        }
    }

    private static boolean method_23671(Box box, Entity entity) {
        return entity.getPistonBehavior() == PistonBehavior.NORMAL && entity.isOnGround() && entity.getX() >= box.minX && entity.getX() <= box.maxX && entity.getZ() >= box.minZ && entity.getZ() <= box.maxZ;
    }

    private boolean isPushingHoneyBlock() {
        return this.pushedBlock.isOf(Blocks.HONEY_BLOCK);
    }

    public Direction getMovementDirection() {
        return this.extending ? this.facing : this.facing.getOpposite();
    }

    private static double getIntersectionSize(Box box, Direction direction, Box box2) {
        switch (direction) {
            case EAST: {
                return box.maxX - box2.minX;
            }
            case WEST: {
                return box2.maxX - box.minX;
            }
            default: {
                return box.maxY - box2.minY;
            }
            case DOWN: {
                return box2.maxY - box.minY;
            }
            case SOUTH: {
                return box.maxZ - box2.minZ;
            }
            case NORTH: 
        }
        return box2.maxZ - box.minZ;
    }

    private Box offsetHeadBox(Box box) {
        double d = this.getAmountExtended(this.progress);
        return box.offset((double)this.pos.getX() + d * (double)this.facing.getOffsetX(), (double)this.pos.getY() + d * (double)this.facing.getOffsetY(), (double)this.pos.getZ() + d * (double)this.facing.getOffsetZ());
    }

    private void push(Entity entity, Direction direction, double amount) {
        Box box = entity.getBoundingBox();
        if (box.intersects(_snowman = VoxelShapes.fullCube().getBoundingBox().offset(this.pos)) && Math.abs((d = PistonBlockEntity.getIntersectionSize(_snowman, _snowman = direction.getOpposite(), box) + 0.01) - (_snowman = PistonBlockEntity.getIntersectionSize(_snowman, _snowman, box.intersection(_snowman)) + 0.01)) < 0.01) {
            double d = Math.min(d, amount) + 0.01;
            PistonBlockEntity.method_23672(direction, entity, d, _snowman);
        }
    }

    public BlockState getPushedBlock() {
        return this.pushedBlock;
    }

    public void finish() {
        if (this.world != null && (this.lastProgress < 1.0f || this.world.isClient)) {
            this.lastProgress = this.progress = 1.0f;
            this.world.removeBlockEntity(this.pos);
            this.markRemoved();
            if (this.world.getBlockState(this.pos).isOf(Blocks.MOVING_PISTON)) {
                BlockState blockState = this.source ? Blocks.AIR.getDefaultState() : Block.postProcessState(this.pushedBlock, this.world, this.pos);
                this.world.setBlockState(this.pos, blockState, 3);
                this.world.updateNeighbor(this.pos, blockState.getBlock(), this.pos);
            }
        }
    }

    @Override
    public void tick() {
        this.savedWorldTime = this.world.getTime();
        this.lastProgress = this.progress;
        if (this.lastProgress >= 1.0f) {
            if (this.world.isClient && this.field_26705 < 5) {
                ++this.field_26705;
                return;
            }
            this.world.removeBlockEntity(this.pos);
            this.markRemoved();
            if (this.pushedBlock != null && this.world.getBlockState(this.pos).isOf(Blocks.MOVING_PISTON)) {
                BlockState blockState = Block.postProcessState(this.pushedBlock, this.world, this.pos);
                if (blockState.isAir()) {
                    this.world.setBlockState(this.pos, this.pushedBlock, 84);
                    Block.replace(this.pushedBlock, blockState, this.world, this.pos, 3);
                } else {
                    if (blockState.contains(Properties.WATERLOGGED) && blockState.get(Properties.WATERLOGGED).booleanValue()) {
                        blockState = (BlockState)blockState.with(Properties.WATERLOGGED, false);
                    }
                    this.world.setBlockState(this.pos, blockState, 67);
                    this.world.updateNeighbor(this.pos, blockState.getBlock(), this.pos);
                }
            }
            return;
        }
        float f = this.progress + 0.5f;
        this.pushEntities(f);
        this.method_23674(f);
        this.progress = f;
        if (this.progress >= 1.0f) {
            this.progress = 1.0f;
        }
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.pushedBlock = NbtHelper.toBlockState(tag.getCompound("blockState"));
        this.facing = Direction.byId(tag.getInt("facing"));
        this.lastProgress = this.progress = tag.getFloat("progress");
        this.extending = tag.getBoolean("extending");
        this.source = tag.getBoolean("source");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.put("blockState", NbtHelper.fromBlockState(this.pushedBlock));
        tag.putInt("facing", this.facing.getId());
        tag.putFloat("progress", this.lastProgress);
        tag.putBoolean("extending", this.extending);
        tag.putBoolean("source", this.source);
        return tag;
    }

    public VoxelShape getCollisionShape(BlockView world, BlockPos pos) {
        VoxelShape voxelShape = !this.extending && this.source ? ((BlockState)this.pushedBlock.with(PistonBlock.EXTENDED, true)).getCollisionShape(world, pos) : VoxelShapes.empty();
        Direction _snowman2 = field_12205.get();
        if ((double)this.progress < 1.0 && _snowman2 == this.getMovementDirection()) {
            return voxelShape;
        }
        BlockState _snowman3 = this.isSource() ? (BlockState)((BlockState)Blocks.PISTON_HEAD.getDefaultState().with(PistonHeadBlock.FACING, this.facing)).with(PistonHeadBlock.SHORT, this.extending != 1.0f - this.progress < 0.25f) : this.pushedBlock;
        float _snowman4 = this.getAmountExtended(this.progress);
        double _snowman5 = (float)this.facing.getOffsetX() * _snowman4;
        double _snowman6 = (float)this.facing.getOffsetY() * _snowman4;
        double _snowman7 = (float)this.facing.getOffsetZ() * _snowman4;
        return VoxelShapes.union(voxelShape, _snowman3.getCollisionShape(world, pos).offset(_snowman5, _snowman6, _snowman7));
    }

    public long getSavedWorldTime() {
        return this.savedWorldTime;
    }

    @Override
    public double getSquaredRenderDistance() {
        return 68.0;
    }
}

