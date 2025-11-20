/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.block.entity;

import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;

public class ShulkerBoxBlockEntity
extends LootableContainerBlockEntity
implements SidedInventory,
Tickable {
    private static final int[] AVAILABLE_SLOTS = IntStream.range(0, 27).toArray();
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
    private int viewerCount;
    private AnimationStage animationStage = AnimationStage.CLOSED;
    private float animationProgress;
    private float prevAnimationProgress;
    @Nullable
    private DyeColor cachedColor;
    private boolean cachedColorUpdateNeeded;

    public ShulkerBoxBlockEntity(@Nullable DyeColor color) {
        super(BlockEntityType.SHULKER_BOX);
        this.cachedColor = color;
    }

    public ShulkerBoxBlockEntity() {
        this((DyeColor)null);
        this.cachedColorUpdateNeeded = true;
    }

    @Override
    public void tick() {
        this.updateAnimation();
        if (this.animationStage == AnimationStage.OPENING || this.animationStage == AnimationStage.CLOSING) {
            this.pushEntities();
        }
    }

    protected void updateAnimation() {
        this.prevAnimationProgress = this.animationProgress;
        switch (this.animationStage) {
            case CLOSED: {
                this.animationProgress = 0.0f;
                break;
            }
            case OPENING: {
                this.animationProgress += 0.1f;
                if (!(this.animationProgress >= 1.0f)) break;
                this.pushEntities();
                this.animationStage = AnimationStage.OPENED;
                this.animationProgress = 1.0f;
                this.updateNeighborStates();
                break;
            }
            case CLOSING: {
                this.animationProgress -= 0.1f;
                if (!(this.animationProgress <= 0.0f)) break;
                this.animationStage = AnimationStage.CLOSED;
                this.animationProgress = 0.0f;
                this.updateNeighborStates();
                break;
            }
            case OPENED: {
                this.animationProgress = 1.0f;
            }
        }
    }

    public AnimationStage getAnimationStage() {
        return this.animationStage;
    }

    public Box getBoundingBox(BlockState state) {
        return this.getBoundingBox(state.get(ShulkerBoxBlock.FACING));
    }

    public Box getBoundingBox(Direction openDirection) {
        float f = this.getAnimationProgress(1.0f);
        return VoxelShapes.fullCube().getBoundingBox().stretch(0.5f * f * (float)openDirection.getOffsetX(), 0.5f * f * (float)openDirection.getOffsetY(), 0.5f * f * (float)openDirection.getOffsetZ());
    }

    private Box getCollisionBox(Direction facing) {
        Direction direction = facing.getOpposite();
        return this.getBoundingBox(facing).shrink(direction.getOffsetX(), direction.getOffsetY(), direction.getOffsetZ());
    }

    private void pushEntities() {
        BlockState blockState = this.world.getBlockState(this.getPos());
        if (!(blockState.getBlock() instanceof ShulkerBoxBlock)) {
            return;
        }
        Direction _snowman2 = blockState.get(ShulkerBoxBlock.FACING);
        Box _snowman3 = this.getCollisionBox(_snowman2).offset(this.pos);
        List<Entity> _snowman4 = this.world.getOtherEntities(null, _snowman3);
        if (_snowman4.isEmpty()) {
            return;
        }
        for (int i = 0; i < _snowman4.size(); ++i) {
            Entity entity = _snowman4.get(i);
            if (entity.getPistonBehavior() == PistonBehavior.IGNORE) continue;
            double _snowman5 = 0.0;
            double _snowman6 = 0.0;
            double _snowman7 = 0.0;
            Box _snowman8 = entity.getBoundingBox();
            switch (_snowman2.getAxis()) {
                case X: {
                    _snowman5 = _snowman2.getDirection() == Direction.AxisDirection.POSITIVE ? _snowman3.maxX - _snowman8.minX : _snowman8.maxX - _snowman3.minX;
                    _snowman5 += 0.01;
                    break;
                }
                case Y: {
                    _snowman6 = _snowman2.getDirection() == Direction.AxisDirection.POSITIVE ? _snowman3.maxY - _snowman8.minY : _snowman8.maxY - _snowman3.minY;
                    _snowman6 += 0.01;
                    break;
                }
                case Z: {
                    _snowman7 = _snowman2.getDirection() == Direction.AxisDirection.POSITIVE ? _snowman3.maxZ - _snowman8.minZ : _snowman8.maxZ - _snowman3.minZ;
                    _snowman7 += 0.01;
                }
            }
            entity.move(MovementType.SHULKER_BOX, new Vec3d(_snowman5 * (double)_snowman2.getOffsetX(), _snowman6 * (double)_snowman2.getOffsetY(), _snowman7 * (double)_snowman2.getOffsetZ()));
        }
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            this.viewerCount = data;
            if (data == 0) {
                this.animationStage = AnimationStage.CLOSING;
                this.updateNeighborStates();
            }
            if (data == 1) {
                this.animationStage = AnimationStage.OPENING;
                this.updateNeighborStates();
            }
            return true;
        }
        return super.onSyncedBlockEvent(type, data);
    }

    private void updateNeighborStates() {
        this.getCachedState().updateNeighbors(this.getWorld(), this.getPos(), 3);
    }

    @Override
    public void onOpen(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (this.viewerCount < 0) {
                this.viewerCount = 0;
            }
            ++this.viewerCount;
            this.world.addSyncedBlockEvent(this.pos, this.getCachedState().getBlock(), 1, this.viewerCount);
            if (this.viewerCount == 1) {
                this.world.playSound(null, this.pos, SoundEvents.BLOCK_SHULKER_BOX_OPEN, SoundCategory.BLOCKS, 0.5f, this.world.random.nextFloat() * 0.1f + 0.9f);
            }
        }
    }

    @Override
    public void onClose(PlayerEntity player) {
        if (!player.isSpectator()) {
            --this.viewerCount;
            this.world.addSyncedBlockEvent(this.pos, this.getCachedState().getBlock(), 1, this.viewerCount);
            if (this.viewerCount <= 0) {
                this.world.playSound(null, this.pos, SoundEvents.BLOCK_SHULKER_BOX_CLOSE, SoundCategory.BLOCKS, 0.5f, this.world.random.nextFloat() * 0.1f + 0.9f);
            }
        }
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.shulkerBox");
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.deserializeInventory(tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        return this.serializeInventory(tag);
    }

    public void deserializeInventory(CompoundTag tag) {
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.deserializeLootTable(tag) && tag.contains("Items", 9)) {
            Inventories.fromTag(tag, this.inventory);
        }
    }

    public CompoundTag serializeInventory(CompoundTag tag) {
        if (!this.serializeLootTable(tag)) {
            Inventories.toTag(tag, this.inventory, false);
        }
        return tag;
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return AVAILABLE_SLOTS;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return !(Block.getBlockFromItem(stack.getItem()) instanceof ShulkerBoxBlock);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    public float getAnimationProgress(float f) {
        return MathHelper.lerp(f, this.prevAnimationProgress, this.animationProgress);
    }

    @Nullable
    public DyeColor getColor() {
        if (this.cachedColorUpdateNeeded) {
            this.cachedColor = ShulkerBoxBlock.getColor(this.getCachedState().getBlock());
            this.cachedColorUpdateNeeded = false;
        }
        return this.cachedColor;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new ShulkerBoxScreenHandler(syncId, playerInventory, this);
    }

    public boolean suffocates() {
        return this.animationStage == AnimationStage.CLOSED;
    }

    public static enum AnimationStage {
        CLOSED,
        OPENING,
        OPENED,
        CLOSING;

    }
}

