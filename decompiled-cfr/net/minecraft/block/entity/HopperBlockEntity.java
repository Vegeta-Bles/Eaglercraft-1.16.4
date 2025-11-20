/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.block.entity;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.Hopper;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.screen.HopperScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public class HopperBlockEntity
extends LootableContainerBlockEntity
implements Hopper,
Tickable {
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
    private int transferCooldown = -1;
    private long lastTickTime;

    public HopperBlockEntity() {
        super(BlockEntityType.HOPPER);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.deserializeLootTable(tag)) {
            Inventories.fromTag(tag, this.inventory);
        }
        this.transferCooldown = tag.getInt("TransferCooldown");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        if (!this.serializeLootTable(tag)) {
            Inventories.toTag(tag, this.inventory);
        }
        tag.putInt("TransferCooldown", this.transferCooldown);
        return tag;
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        this.checkLootInteraction(null);
        return Inventories.splitStack(this.getInvStackList(), slot, amount);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.checkLootInteraction(null);
        this.getInvStackList().set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.hopper");
    }

    @Override
    public void tick() {
        if (this.world == null || this.world.isClient) {
            return;
        }
        --this.transferCooldown;
        this.lastTickTime = this.world.getTime();
        if (!this.needsCooldown()) {
            this.setCooldown(0);
            this.insertAndExtract(() -> HopperBlockEntity.extract(this));
        }
    }

    private boolean insertAndExtract(Supplier<Boolean> extractMethod) {
        if (this.world == null || this.world.isClient) {
            return false;
        }
        if (!this.needsCooldown() && this.getCachedState().get(HopperBlock.ENABLED).booleanValue()) {
            boolean bl = false;
            if (!this.isEmpty()) {
                bl = this.insert();
            }
            if (!this.isFull()) {
                bl |= extractMethod.get().booleanValue();
            }
            if (bl) {
                this.setCooldown(8);
                this.markDirty();
                return true;
            }
        }
        return false;
    }

    private boolean isFull() {
        for (ItemStack itemStack : this.inventory) {
            if (!itemStack.isEmpty() && itemStack.getCount() == itemStack.getMaxCount()) continue;
            return false;
        }
        return true;
    }

    private boolean insert() {
        Inventory inventory = this.getOutputInventory();
        if (inventory == null) {
            return false;
        }
        Direction _snowman2 = this.getCachedState().get(HopperBlock.FACING).getOpposite();
        if (this.isInventoryFull(inventory, _snowman2)) {
            return false;
        }
        for (int i = 0; i < this.size(); ++i) {
            if (this.getStack(i).isEmpty()) continue;
            ItemStack itemStack = this.getStack(i).copy();
            _snowman = HopperBlockEntity.transfer(this, inventory, this.removeStack(i, 1), _snowman2);
            if (_snowman.isEmpty()) {
                inventory.markDirty();
                return true;
            }
            this.setStack(i, itemStack);
        }
        return false;
    }

    private static IntStream getAvailableSlots(Inventory inventory, Direction side) {
        if (inventory instanceof SidedInventory) {
            return IntStream.of(((SidedInventory)inventory).getAvailableSlots(side));
        }
        return IntStream.range(0, inventory.size());
    }

    private boolean isInventoryFull(Inventory inv, Direction direction) {
        return HopperBlockEntity.getAvailableSlots(inv, direction).allMatch(n -> {
            ItemStack itemStack = inv.getStack(n);
            return itemStack.getCount() >= itemStack.getMaxCount();
        });
    }

    private static boolean isInventoryEmpty(Inventory inv, Direction facing) {
        return HopperBlockEntity.getAvailableSlots(inv, facing).allMatch(n -> inv.getStack(n).isEmpty());
    }

    public static boolean extract(Hopper hopper) {
        Inventory inventory = HopperBlockEntity.getInputInventory(hopper);
        if (inventory != null) {
            Direction direction = Direction.DOWN;
            if (HopperBlockEntity.isInventoryEmpty(inventory, direction)) {
                return false;
            }
            return HopperBlockEntity.getAvailableSlots(inventory, direction).anyMatch(n -> HopperBlockEntity.extract(hopper, inventory, n, direction));
        }
        for (ItemEntity itemEntity : HopperBlockEntity.getInputItemEntities(hopper)) {
            if (!HopperBlockEntity.extract(hopper, itemEntity)) continue;
            return true;
        }
        return false;
    }

    private static boolean extract(Hopper hopper, Inventory inventory, int slot, Direction side) {
        ItemStack itemStack = inventory.getStack(slot);
        if (!itemStack.isEmpty() && HopperBlockEntity.canExtract(inventory, itemStack, slot, side)) {
            _snowman = itemStack.copy();
            _snowman = HopperBlockEntity.transfer(inventory, hopper, inventory.removeStack(slot, 1), null);
            if (_snowman.isEmpty()) {
                inventory.markDirty();
                return true;
            }
            inventory.setStack(slot, _snowman);
        }
        return false;
    }

    public static boolean extract(Inventory inventory, ItemEntity itemEntity) {
        boolean bl = false;
        ItemStack _snowman2 = itemEntity.getStack().copy();
        ItemStack _snowman3 = HopperBlockEntity.transfer(null, inventory, _snowman2, null);
        if (_snowman3.isEmpty()) {
            bl = true;
            itemEntity.remove();
        } else {
            itemEntity.setStack(_snowman3);
        }
        return bl;
    }

    public static ItemStack transfer(@Nullable Inventory from, Inventory to, ItemStack stack, @Nullable Direction side) {
        if (to instanceof SidedInventory && side != null) {
            SidedInventory sidedInventory = (SidedInventory)to;
            int[] _snowman2 = sidedInventory.getAvailableSlots(side);
            for (int i = 0; i < _snowman2.length && !stack.isEmpty(); ++i) {
                stack = HopperBlockEntity.transfer(from, to, stack, _snowman2[i], side);
            }
        } else {
            int n = to.size();
            for (_snowman = 0; _snowman < n && !stack.isEmpty(); ++_snowman) {
                stack = HopperBlockEntity.transfer(from, to, stack, _snowman, side);
            }
        }
        return stack;
    }

    private static boolean canInsert(Inventory inventory, ItemStack stack, int slot, @Nullable Direction side) {
        if (!inventory.isValid(slot, stack)) {
            return false;
        }
        return !(inventory instanceof SidedInventory) || ((SidedInventory)inventory).canInsert(slot, stack, side);
    }

    private static boolean canExtract(Inventory inv, ItemStack stack, int slot, Direction facing) {
        return !(inv instanceof SidedInventory) || ((SidedInventory)inv).canExtract(slot, stack, facing);
    }

    private static ItemStack transfer(@Nullable Inventory from, Inventory to, ItemStack stack, int slot, @Nullable Direction direction) {
        ItemStack itemStack = to.getStack(slot);
        if (HopperBlockEntity.canInsert(to, stack, slot, direction)) {
            boolean bl = false;
            _snowman = to.isEmpty();
            if (itemStack.isEmpty()) {
                to.setStack(slot, stack);
                stack = ItemStack.EMPTY;
                bl = true;
            } else if (HopperBlockEntity.canMergeItems(itemStack, stack)) {
                int n = stack.getMaxCount() - itemStack.getCount();
                n = Math.min(stack.getCount(), n);
                stack.decrement(n);
                itemStack.increment(n);
                boolean bl2 = bl = n > 0;
            }
            if (bl) {
                if (_snowman && to instanceof HopperBlockEntity && !(hopperBlockEntity = (HopperBlockEntity)to).isDisabled()) {
                    HopperBlockEntity hopperBlockEntity;
                    int n = 0;
                    if (from instanceof HopperBlockEntity) {
                        HopperBlockEntity hopperBlockEntity2 = (HopperBlockEntity)from;
                        if (hopperBlockEntity.lastTickTime >= hopperBlockEntity2.lastTickTime) {
                            n = 1;
                        }
                    }
                    hopperBlockEntity.setCooldown(8 - n);
                }
                to.markDirty();
            }
        }
        return stack;
    }

    @Nullable
    private Inventory getOutputInventory() {
        Direction direction = this.getCachedState().get(HopperBlock.FACING);
        return HopperBlockEntity.getInventoryAt(this.getWorld(), this.pos.offset(direction));
    }

    @Nullable
    public static Inventory getInputInventory(Hopper hopper) {
        return HopperBlockEntity.getInventoryAt(hopper.getWorld(), hopper.getHopperX(), hopper.getHopperY() + 1.0, hopper.getHopperZ());
    }

    public static List<ItemEntity> getInputItemEntities(Hopper hopper) {
        return hopper.getInputAreaShape().getBoundingBoxes().stream().flatMap(box -> hopper.getWorld().getEntitiesByClass(ItemEntity.class, box.offset(hopper.getHopperX() - 0.5, hopper.getHopperY() - 0.5, hopper.getHopperZ() - 0.5), EntityPredicates.VALID_ENTITY).stream()).collect(Collectors.toList());
    }

    @Nullable
    public static Inventory getInventoryAt(World world, BlockPos blockPos) {
        return HopperBlockEntity.getInventoryAt(world, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5);
    }

    @Nullable
    public static Inventory getInventoryAt(World world, double x, double y, double z) {
        Inventory inventory = null;
        BlockPos _snowman2 = new BlockPos(x, y, z);
        BlockState _snowman3 = world.getBlockState(_snowman2);
        Block _snowman4 = _snowman3.getBlock();
        if (_snowman4 instanceof InventoryProvider) {
            inventory = ((InventoryProvider)((Object)_snowman4)).getInventory(_snowman3, world, _snowman2);
        } else if (_snowman4.hasBlockEntity() && (_snowman = world.getBlockEntity(_snowman2)) instanceof Inventory && (inventory = (Inventory)_snowman) instanceof ChestBlockEntity && _snowman4 instanceof ChestBlock) {
            inventory = ChestBlock.getInventory((ChestBlock)_snowman4, _snowman3, world, _snowman2, true);
        }
        if (inventory == null && !(_snowman = world.getOtherEntities(null, new Box(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5), EntityPredicates.VALID_INVENTORIES)).isEmpty()) {
            inventory = (Inventory)_snowman.get(world.random.nextInt(_snowman.size()));
        }
        return inventory;
    }

    private static boolean canMergeItems(ItemStack first, ItemStack second) {
        if (first.getItem() != second.getItem()) {
            return false;
        }
        if (first.getDamage() != second.getDamage()) {
            return false;
        }
        if (first.getCount() > first.getMaxCount()) {
            return false;
        }
        return ItemStack.areTagsEqual(first, second);
    }

    @Override
    public double getHopperX() {
        return (double)this.pos.getX() + 0.5;
    }

    @Override
    public double getHopperY() {
        return (double)this.pos.getY() + 0.5;
    }

    @Override
    public double getHopperZ() {
        return (double)this.pos.getZ() + 0.5;
    }

    private void setCooldown(int cooldown) {
        this.transferCooldown = cooldown;
    }

    private boolean needsCooldown() {
        return this.transferCooldown > 0;
    }

    private boolean isDisabled() {
        return this.transferCooldown > 8;
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    public void onEntityCollided(Entity entity) {
        if (entity instanceof ItemEntity) {
            BlockPos blockPos = this.getPos();
            if (VoxelShapes.matchesAnywhere(VoxelShapes.cuboid(entity.getBoundingBox().offset(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ())), this.getInputAreaShape(), BooleanBiFunction.AND)) {
                this.insertAndExtract(() -> HopperBlockEntity.extract(this, (ItemEntity)entity));
            }
        }
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new HopperScreenHandler(syncId, playerInventory, this);
    }
}

