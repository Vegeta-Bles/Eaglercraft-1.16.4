package net.minecraft.block.entity;

import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
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
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3i;

public class BarrelBlockEntity extends LootableContainerBlockEntity {
   private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
   private int viewerCount;

   private BarrelBlockEntity(BlockEntityType<?> type) {
      super(type);
   }

   public BarrelBlockEntity() {
      this(BlockEntityType.BARREL);
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
   public void fromTag(BlockState state, CompoundTag tag) {
      super.fromTag(state, tag);
      this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
      if (!this.deserializeLootTable(tag)) {
         Inventories.fromTag(tag, this.inventory);
      }
   }

   @Override
   public int size() {
      return 27;
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
   protected Text getContainerName() {
      return new TranslatableText("container.barrel");
   }

   @Override
   protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
      return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this);
   }

   @Override
   public void onOpen(PlayerEntity player) {
      if (!player.isSpectator()) {
         if (this.viewerCount < 0) {
            this.viewerCount = 0;
         }

         this.viewerCount++;
         BlockState _snowman = this.getCachedState();
         boolean _snowmanx = _snowman.get(BarrelBlock.OPEN);
         if (!_snowmanx) {
            this.playSound(_snowman, SoundEvents.BLOCK_BARREL_OPEN);
            this.setOpen(_snowman, true);
         }

         this.scheduleUpdate();
      }
   }

   private void scheduleUpdate() {
      this.world.getBlockTickScheduler().schedule(this.getPos(), this.getCachedState().getBlock(), 5);
   }

   public void tick() {
      int _snowman = this.pos.getX();
      int _snowmanx = this.pos.getY();
      int _snowmanxx = this.pos.getZ();
      this.viewerCount = ChestBlockEntity.countViewers(this.world, this, _snowman, _snowmanx, _snowmanxx);
      if (this.viewerCount > 0) {
         this.scheduleUpdate();
      } else {
         BlockState _snowmanxxx = this.getCachedState();
         if (!_snowmanxxx.isOf(Blocks.BARREL)) {
            this.markRemoved();
            return;
         }

         boolean _snowmanxxxx = _snowmanxxx.get(BarrelBlock.OPEN);
         if (_snowmanxxxx) {
            this.playSound(_snowmanxxx, SoundEvents.BLOCK_BARREL_CLOSE);
            this.setOpen(_snowmanxxx, false);
         }
      }
   }

   @Override
   public void onClose(PlayerEntity player) {
      if (!player.isSpectator()) {
         this.viewerCount--;
      }
   }

   private void setOpen(BlockState state, boolean open) {
      this.world.setBlockState(this.getPos(), state.with(BarrelBlock.OPEN, Boolean.valueOf(open)), 3);
   }

   private void playSound(BlockState _snowman, SoundEvent _snowman) {
      Vec3i _snowmanxx = _snowman.get(BarrelBlock.FACING).getVector();
      double _snowmanxxx = (double)this.pos.getX() + 0.5 + (double)_snowmanxx.getX() / 2.0;
      double _snowmanxxxx = (double)this.pos.getY() + 0.5 + (double)_snowmanxx.getY() / 2.0;
      double _snowmanxxxxx = (double)this.pos.getZ() + 0.5 + (double)_snowmanxx.getZ() / 2.0;
      this.world.playSound(null, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowman, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
   }
}
