/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block.entity;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ChestBlockEntity
extends LootableContainerBlockEntity
implements ChestAnimationProgress,
Tickable {
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
    protected float animationAngle;
    protected float lastAnimationAngle;
    protected int viewerCount;
    private int ticksOpen;

    protected ChestBlockEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    public ChestBlockEntity() {
        this(BlockEntityType.CHEST);
    }

    @Override
    public int size() {
        return 27;
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.chest");
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.deserializeLootTable(tag)) {
            Inventories.fromTag(tag, this.inventory);
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        if (!this.serializeLootTable(tag)) {
            Inventories.toTag(tag, this.inventory);
        }
        return tag;
    }

    @Override
    public void tick() {
        int n = this.pos.getX();
        _snowman = this.pos.getY();
        _snowman = this.pos.getZ();
        ++this.ticksOpen;
        this.viewerCount = ChestBlockEntity.tickViewerCount(this.world, this, this.ticksOpen, n, _snowman, _snowman, this.viewerCount);
        this.lastAnimationAngle = this.animationAngle;
        float _snowman2 = 0.1f;
        if (this.viewerCount > 0 && this.animationAngle == 0.0f) {
            this.playSound(SoundEvents.BLOCK_CHEST_OPEN);
        }
        if (this.viewerCount == 0 && this.animationAngle > 0.0f || this.viewerCount > 0 && this.animationAngle < 1.0f) {
            float f = this.animationAngle;
            this.animationAngle = this.viewerCount > 0 ? (this.animationAngle += 0.1f) : (this.animationAngle -= 0.1f);
            if (this.animationAngle > 1.0f) {
                this.animationAngle = 1.0f;
            }
            _snowman = 0.5f;
            if (this.animationAngle < 0.5f && f >= 0.5f) {
                this.playSound(SoundEvents.BLOCK_CHEST_CLOSE);
            }
            if (this.animationAngle < 0.0f) {
                this.animationAngle = 0.0f;
            }
        }
    }

    public static int tickViewerCount(World world, LockableContainerBlockEntity inventory, int ticksOpen, int x, int y, int z, int viewerCount) {
        if (!world.isClient && viewerCount != 0 && (ticksOpen + x + y + z) % 200 == 0) {
            viewerCount = ChestBlockEntity.countViewers(world, inventory, x, y, z);
        }
        return viewerCount;
    }

    public static int countViewers(World world, LockableContainerBlockEntity inventory, int x, int y, int z) {
        int n = 0;
        float _snowman2 = 5.0f;
        List<PlayerEntity> _snowman3 = world.getNonSpectatingEntities(PlayerEntity.class, new Box((float)x - 5.0f, (float)y - 5.0f, (float)z - 5.0f, (float)(x + 1) + 5.0f, (float)(y + 1) + 5.0f, (float)(z + 1) + 5.0f));
        for (PlayerEntity playerEntity : _snowman3) {
            if (!(playerEntity.currentScreenHandler instanceof GenericContainerScreenHandler) || (_snowman = ((GenericContainerScreenHandler)playerEntity.currentScreenHandler).getInventory()) != inventory && (!(_snowman instanceof DoubleInventory) || !((DoubleInventory)_snowman).isPart(inventory))) continue;
            ++n;
        }
        return n;
    }

    private void playSound(SoundEvent soundEvent2) {
        SoundEvent soundEvent2;
        ChestType chestType = this.getCachedState().get(ChestBlock.CHEST_TYPE);
        if (chestType == ChestType.LEFT) {
            return;
        }
        double _snowman2 = (double)this.pos.getX() + 0.5;
        double _snowman3 = (double)this.pos.getY() + 0.5;
        double _snowman4 = (double)this.pos.getZ() + 0.5;
        if (chestType == ChestType.RIGHT) {
            Direction direction = ChestBlock.getFacing(this.getCachedState());
            _snowman2 += (double)direction.getOffsetX() * 0.5;
            _snowman4 += (double)direction.getOffsetZ() * 0.5;
        }
        this.world.playSound(null, _snowman2, _snowman3, _snowman4, soundEvent2, SoundCategory.BLOCKS, 0.5f, this.world.random.nextFloat() * 0.1f + 0.9f);
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            this.viewerCount = data;
            return true;
        }
        return super.onSyncedBlockEvent(type, data);
    }

    @Override
    public void onOpen(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (this.viewerCount < 0) {
                this.viewerCount = 0;
            }
            ++this.viewerCount;
            this.onInvOpenOrClose();
        }
    }

    @Override
    public void onClose(PlayerEntity player) {
        if (!player.isSpectator()) {
            --this.viewerCount;
            this.onInvOpenOrClose();
        }
    }

    protected void onInvOpenOrClose() {
        Block block = this.getCachedState().getBlock();
        if (block instanceof ChestBlock) {
            this.world.addSyncedBlockEvent(this.pos, block, 1, this.viewerCount);
            this.world.updateNeighborsAlways(this.pos, block);
        }
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
    public float getAnimationProgress(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.lastAnimationAngle, this.animationAngle);
    }

    public static int getPlayersLookingInChestCount(BlockView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.getBlock().hasBlockEntity() && (_snowman = world.getBlockEntity(pos)) instanceof ChestBlockEntity) {
            return ((ChestBlockEntity)_snowman).viewerCount;
        }
        return 0;
    }

    public static void copyInventory(ChestBlockEntity from, ChestBlockEntity to) {
        DefaultedList<ItemStack> defaultedList = from.getInvStackList();
        from.setInvStackList(to.getInvStackList());
        to.setInvStackList(defaultedList);
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this);
    }
}

