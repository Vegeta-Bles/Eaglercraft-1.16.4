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

public class HopperBlockEntity extends LootableContainerBlockEntity implements Hopper, Tickable {
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
      if (this.world != null && !this.world.isClient) {
         this.transferCooldown--;
         this.lastTickTime = this.world.getTime();
         if (!this.needsCooldown()) {
            this.setCooldown(0);
            this.insertAndExtract(() -> extract(this));
         }
      }
   }

   private boolean insertAndExtract(Supplier<Boolean> extractMethod) {
      if (this.world != null && !this.world.isClient) {
         if (!this.needsCooldown() && this.getCachedState().get(HopperBlock.ENABLED)) {
            boolean _snowman = false;
            if (!this.isEmpty()) {
               _snowman = this.insert();
            }

            if (!this.isFull()) {
               _snowman |= extractMethod.get();
            }

            if (_snowman) {
               this.setCooldown(8);
               this.markDirty();
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean isFull() {
      for (ItemStack _snowman : this.inventory) {
         if (_snowman.isEmpty() || _snowman.getCount() != _snowman.getMaxCount()) {
            return false;
         }
      }

      return true;
   }

   private boolean insert() {
      Inventory _snowman = this.getOutputInventory();
      if (_snowman == null) {
         return false;
      } else {
         Direction _snowmanx = this.getCachedState().get(HopperBlock.FACING).getOpposite();
         if (this.isInventoryFull(_snowman, _snowmanx)) {
            return false;
         } else {
            for (int _snowmanxx = 0; _snowmanxx < this.size(); _snowmanxx++) {
               if (!this.getStack(_snowmanxx).isEmpty()) {
                  ItemStack _snowmanxxx = this.getStack(_snowmanxx).copy();
                  ItemStack _snowmanxxxx = transfer(this, _snowman, this.removeStack(_snowmanxx, 1), _snowmanx);
                  if (_snowmanxxxx.isEmpty()) {
                     _snowman.markDirty();
                     return true;
                  }

                  this.setStack(_snowmanxx, _snowmanxxx);
               }
            }

            return false;
         }
      }
   }

   private static IntStream getAvailableSlots(Inventory inventory, Direction side) {
      return inventory instanceof SidedInventory ? IntStream.of(((SidedInventory)inventory).getAvailableSlots(side)) : IntStream.range(0, inventory.size());
   }

   private boolean isInventoryFull(Inventory inv, Direction _snowman) {
      return getAvailableSlots(inv, _snowman).allMatch(_snowmanxxx -> {
         ItemStack _snowmanxx = inv.getStack(_snowmanxxx);
         return _snowmanxx.getCount() >= _snowmanxx.getMaxCount();
      });
   }

   private static boolean isInventoryEmpty(Inventory inv, Direction facing) {
      return getAvailableSlots(inv, facing).allMatch(_snowmanx -> inv.getStack(_snowmanx).isEmpty());
   }

   public static boolean extract(Hopper hopper) {
      Inventory _snowman = getInputInventory(hopper);
      if (_snowman != null) {
         Direction _snowmanx = Direction.DOWN;
         return isInventoryEmpty(_snowman, _snowmanx) ? false : getAvailableSlots(_snowman, _snowmanx).anyMatch(_snowmanxxx -> extract(hopper, _snowman, _snowmanxxx, _snowman));
      } else {
         for (ItemEntity _snowmanx : getInputItemEntities(hopper)) {
            if (extract(hopper, _snowmanx)) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean extract(Hopper hopper, Inventory inventory, int slot, Direction side) {
      ItemStack _snowman = inventory.getStack(slot);
      if (!_snowman.isEmpty() && canExtract(inventory, _snowman, slot, side)) {
         ItemStack _snowmanx = _snowman.copy();
         ItemStack _snowmanxx = transfer(inventory, hopper, inventory.removeStack(slot, 1), null);
         if (_snowmanxx.isEmpty()) {
            inventory.markDirty();
            return true;
         }

         inventory.setStack(slot, _snowmanx);
      }

      return false;
   }

   public static boolean extract(Inventory inventory, ItemEntity itemEntity) {
      boolean _snowman = false;
      ItemStack _snowmanx = itemEntity.getStack().copy();
      ItemStack _snowmanxx = transfer(null, inventory, _snowmanx, null);
      if (_snowmanxx.isEmpty()) {
         _snowman = true;
         itemEntity.remove();
      } else {
         itemEntity.setStack(_snowmanxx);
      }

      return _snowman;
   }

   public static ItemStack transfer(@Nullable Inventory from, Inventory to, ItemStack stack, @Nullable Direction side) {
      if (to instanceof SidedInventory && side != null) {
         SidedInventory _snowman = (SidedInventory)to;
         int[] _snowmanx = _snowman.getAvailableSlots(side);

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.length && !stack.isEmpty(); _snowmanxx++) {
            stack = transfer(from, to, stack, _snowmanx[_snowmanxx], side);
         }
      } else {
         int _snowman = to.size();

         for (int _snowmanx = 0; _snowmanx < _snowman && !stack.isEmpty(); _snowmanx++) {
            stack = transfer(from, to, stack, _snowmanx, side);
         }
      }

      return stack;
   }

   private static boolean canInsert(Inventory inventory, ItemStack stack, int slot, @Nullable Direction side) {
      return !inventory.isValid(slot, stack) ? false : !(inventory instanceof SidedInventory) || ((SidedInventory)inventory).canInsert(slot, stack, side);
   }

   private static boolean canExtract(Inventory inv, ItemStack stack, int slot, Direction facing) {
      return !(inv instanceof SidedInventory) || ((SidedInventory)inv).canExtract(slot, stack, facing);
   }

   private static ItemStack transfer(@Nullable Inventory from, Inventory to, ItemStack stack, int slot, @Nullable Direction _snowman) {
      ItemStack _snowmanx = to.getStack(slot);
      if (canInsert(to, stack, slot, _snowman)) {
         boolean _snowmanxx = false;
         boolean _snowmanxxx = to.isEmpty();
         if (_snowmanx.isEmpty()) {
            to.setStack(slot, stack);
            stack = ItemStack.EMPTY;
            _snowmanxx = true;
         } else if (canMergeItems(_snowmanx, stack)) {
            int _snowmanxxxx = stack.getMaxCount() - _snowmanx.getCount();
            int _snowmanxxxxx = Math.min(stack.getCount(), _snowmanxxxx);
            stack.decrement(_snowmanxxxxx);
            _snowmanx.increment(_snowmanxxxxx);
            _snowmanxx = _snowmanxxxxx > 0;
         }

         if (_snowmanxx) {
            if (_snowmanxxx && to instanceof HopperBlockEntity) {
               HopperBlockEntity _snowmanxxxx = (HopperBlockEntity)to;
               if (!_snowmanxxxx.isDisabled()) {
                  int _snowmanxxxxx = 0;
                  if (from instanceof HopperBlockEntity) {
                     HopperBlockEntity _snowmanxxxxxx = (HopperBlockEntity)from;
                     if (_snowmanxxxx.lastTickTime >= _snowmanxxxxxx.lastTickTime) {
                        _snowmanxxxxx = 1;
                     }
                  }

                  _snowmanxxxx.setCooldown(8 - _snowmanxxxxx);
               }
            }

            to.markDirty();
         }
      }

      return stack;
   }

   @Nullable
   private Inventory getOutputInventory() {
      Direction _snowman = this.getCachedState().get(HopperBlock.FACING);
      return getInventoryAt(this.getWorld(), this.pos.offset(_snowman));
   }

   @Nullable
   public static Inventory getInputInventory(Hopper hopper) {
      return getInventoryAt(hopper.getWorld(), hopper.getHopperX(), hopper.getHopperY() + 1.0, hopper.getHopperZ());
   }

   public static List<ItemEntity> getInputItemEntities(Hopper _snowman) {
      return _snowman.getInputAreaShape()
         .getBoundingBoxes()
         .stream()
         .flatMap(
            _snowmanxx -> _snowman.getWorld()
                  .getEntitiesByClass(
                     ItemEntity.class, _snowmanxx.offset(_snowman.getHopperX() - 0.5, _snowman.getHopperY() - 0.5, _snowman.getHopperZ() - 0.5), EntityPredicates.VALID_ENTITY
                  )
                  .stream()
         )
         .collect(Collectors.toList());
   }

   @Nullable
   public static Inventory getInventoryAt(World _snowman, BlockPos _snowman) {
      return getInventoryAt(_snowman, (double)_snowman.getX() + 0.5, (double)_snowman.getY() + 0.5, (double)_snowman.getZ() + 0.5);
   }

   @Nullable
   public static Inventory getInventoryAt(World world, double x, double y, double z) {
      Inventory _snowman = null;
      BlockPos _snowmanx = new BlockPos(x, y, z);
      BlockState _snowmanxx = world.getBlockState(_snowmanx);
      Block _snowmanxxx = _snowmanxx.getBlock();
      if (_snowmanxxx instanceof InventoryProvider) {
         _snowman = ((InventoryProvider)_snowmanxxx).getInventory(_snowmanxx, world, _snowmanx);
      } else if (_snowmanxxx.hasBlockEntity()) {
         BlockEntity _snowmanxxxx = world.getBlockEntity(_snowmanx);
         if (_snowmanxxxx instanceof Inventory) {
            _snowman = (Inventory)_snowmanxxxx;
            if (_snowman instanceof ChestBlockEntity && _snowmanxxx instanceof ChestBlock) {
               _snowman = ChestBlock.getInventory((ChestBlock)_snowmanxxx, _snowmanxx, world, _snowmanx, true);
            }
         }
      }

      if (_snowman == null) {
         List<Entity> _snowmanxxxx = world.getOtherEntities(
            (Entity)null, new Box(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5), EntityPredicates.VALID_INVENTORIES
         );
         if (!_snowmanxxxx.isEmpty()) {
            _snowman = (Inventory)_snowmanxxxx.get(world.random.nextInt(_snowmanxxxx.size()));
         }
      }

      return _snowman;
   }

   private static boolean canMergeItems(ItemStack first, ItemStack second) {
      if (first.getItem() != second.getItem()) {
         return false;
      } else if (first.getDamage() != second.getDamage()) {
         return false;
      } else {
         return first.getCount() > first.getMaxCount() ? false : ItemStack.areTagsEqual(first, second);
      }
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

   public void onEntityCollided(Entity _snowman) {
      if (_snowman instanceof ItemEntity) {
         BlockPos _snowmanx = this.getPos();
         if (VoxelShapes.matchesAnywhere(
            VoxelShapes.cuboid(_snowman.getBoundingBox().offset((double)(-_snowmanx.getX()), (double)(-_snowmanx.getY()), (double)(-_snowmanx.getZ()))),
            this.getInputAreaShape(),
            BooleanBiFunction.AND
         )) {
            this.insertAndExtract(() -> extract(this, (ItemEntity)_snowman));
         }
      }
   }

   @Override
   protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
      return new HopperScreenHandler(syncId, playerInventory, this);
   }
}
