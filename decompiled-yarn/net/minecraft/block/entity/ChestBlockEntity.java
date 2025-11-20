package net.minecraft.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
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

public class ChestBlockEntity extends LootableContainerBlockEntity implements ChestAnimationProgress, Tickable {
   private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
   protected float animationAngle;
   protected float lastAnimationAngle;
   protected int viewerCount;
   private int ticksOpen;

   protected ChestBlockEntity(BlockEntityType<?> _snowman) {
      super(_snowman);
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
      int _snowman = this.pos.getX();
      int _snowmanx = this.pos.getY();
      int _snowmanxx = this.pos.getZ();
      this.ticksOpen++;
      this.viewerCount = tickViewerCount(this.world, this, this.ticksOpen, _snowman, _snowmanx, _snowmanxx, this.viewerCount);
      this.lastAnimationAngle = this.animationAngle;
      float _snowmanxxx = 0.1F;
      if (this.viewerCount > 0 && this.animationAngle == 0.0F) {
         this.playSound(SoundEvents.BLOCK_CHEST_OPEN);
      }

      if (this.viewerCount == 0 && this.animationAngle > 0.0F || this.viewerCount > 0 && this.animationAngle < 1.0F) {
         float _snowmanxxxx = this.animationAngle;
         if (this.viewerCount > 0) {
            this.animationAngle += 0.1F;
         } else {
            this.animationAngle -= 0.1F;
         }

         if (this.animationAngle > 1.0F) {
            this.animationAngle = 1.0F;
         }

         float _snowmanxxxxx = 0.5F;
         if (this.animationAngle < 0.5F && _snowmanxxxx >= 0.5F) {
            this.playSound(SoundEvents.BLOCK_CHEST_CLOSE);
         }

         if (this.animationAngle < 0.0F) {
            this.animationAngle = 0.0F;
         }
      }
   }

   public static int tickViewerCount(World world, LockableContainerBlockEntity inventory, int ticksOpen, int x, int y, int z, int viewerCount) {
      if (!world.isClient && viewerCount != 0 && (ticksOpen + x + y + z) % 200 == 0) {
         viewerCount = countViewers(world, inventory, x, y, z);
      }

      return viewerCount;
   }

   public static int countViewers(World world, LockableContainerBlockEntity inventory, int x, int y, int z) {
      int _snowman = 0;
      float _snowmanx = 5.0F;

      for (PlayerEntity _snowmanxx : world.getNonSpectatingEntities(
         PlayerEntity.class,
         new Box(
            (double)((float)x - 5.0F),
            (double)((float)y - 5.0F),
            (double)((float)z - 5.0F),
            (double)((float)(x + 1) + 5.0F),
            (double)((float)(y + 1) + 5.0F),
            (double)((float)(z + 1) + 5.0F)
         )
      )) {
         if (_snowmanxx.currentScreenHandler instanceof GenericContainerScreenHandler) {
            Inventory _snowmanxxx = ((GenericContainerScreenHandler)_snowmanxx.currentScreenHandler).getInventory();
            if (_snowmanxxx == inventory || _snowmanxxx instanceof DoubleInventory && ((DoubleInventory)_snowmanxxx).isPart(inventory)) {
               _snowman++;
            }
         }
      }

      return _snowman;
   }

   private void playSound(SoundEvent _snowman) {
      ChestType _snowmanx = this.getCachedState().get(ChestBlock.CHEST_TYPE);
      if (_snowmanx != ChestType.LEFT) {
         double _snowmanxx = (double)this.pos.getX() + 0.5;
         double _snowmanxxx = (double)this.pos.getY() + 0.5;
         double _snowmanxxxx = (double)this.pos.getZ() + 0.5;
         if (_snowmanx == ChestType.RIGHT) {
            Direction _snowmanxxxxx = ChestBlock.getFacing(this.getCachedState());
            _snowmanxx += (double)_snowmanxxxxx.getOffsetX() * 0.5;
            _snowmanxxxx += (double)_snowmanxxxxx.getOffsetZ() * 0.5;
         }

         this.world.playSound(null, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowman, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
      }
   }

   @Override
   public boolean onSyncedBlockEvent(int type, int data) {
      if (type == 1) {
         this.viewerCount = data;
         return true;
      } else {
         return super.onSyncedBlockEvent(type, data);
      }
   }

   @Override
   public void onOpen(PlayerEntity player) {
      if (!player.isSpectator()) {
         if (this.viewerCount < 0) {
            this.viewerCount = 0;
         }

         this.viewerCount++;
         this.onInvOpenOrClose();
      }
   }

   @Override
   public void onClose(PlayerEntity player) {
      if (!player.isSpectator()) {
         this.viewerCount--;
         this.onInvOpenOrClose();
      }
   }

   protected void onInvOpenOrClose() {
      Block _snowman = this.getCachedState().getBlock();
      if (_snowman instanceof ChestBlock) {
         this.world.addSyncedBlockEvent(this.pos, _snowman, 1, this.viewerCount);
         this.world.updateNeighborsAlways(this.pos, _snowman);
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
      BlockState _snowman = world.getBlockState(pos);
      if (_snowman.getBlock().hasBlockEntity()) {
         BlockEntity _snowmanx = world.getBlockEntity(pos);
         if (_snowmanx instanceof ChestBlockEntity) {
            return ((ChestBlockEntity)_snowmanx).viewerCount;
         }
      }

      return 0;
   }

   public static void copyInventory(ChestBlockEntity from, ChestBlockEntity to) {
      DefaultedList<ItemStack> _snowman = from.getInvStackList();
      from.setInvStackList(to.getInvStackList());
      to.setInvStackList(_snowman);
   }

   @Override
   protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
      return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this);
   }
}
