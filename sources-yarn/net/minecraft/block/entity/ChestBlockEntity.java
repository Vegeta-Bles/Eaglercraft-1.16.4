package net.minecraft.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.fabricmc.api.EnvironmentInterfaces;
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

@EnvironmentInterfaces({@EnvironmentInterface(
      value = EnvType.CLIENT,
      itf = ChestAnimationProgress.class
   )})
public class ChestBlockEntity extends LootableContainerBlockEntity implements ChestAnimationProgress, Tickable {
   private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
   protected float animationAngle;
   protected float lastAnimationAngle;
   protected int viewerCount;
   private int ticksOpen;

   protected ChestBlockEntity(BlockEntityType<?> arg) {
      super(arg);
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
      int i = this.pos.getX();
      int j = this.pos.getY();
      int k = this.pos.getZ();
      this.ticksOpen++;
      this.viewerCount = tickViewerCount(this.world, this, this.ticksOpen, i, j, k, this.viewerCount);
      this.lastAnimationAngle = this.animationAngle;
      float f = 0.1F;
      if (this.viewerCount > 0 && this.animationAngle == 0.0F) {
         this.playSound(SoundEvents.BLOCK_CHEST_OPEN);
      }

      if (this.viewerCount == 0 && this.animationAngle > 0.0F || this.viewerCount > 0 && this.animationAngle < 1.0F) {
         float g = this.animationAngle;
         if (this.viewerCount > 0) {
            this.animationAngle += 0.1F;
         } else {
            this.animationAngle -= 0.1F;
         }

         if (this.animationAngle > 1.0F) {
            this.animationAngle = 1.0F;
         }

         float h = 0.5F;
         if (this.animationAngle < 0.5F && g >= 0.5F) {
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
      int l = 0;
      float f = 5.0F;

      for (PlayerEntity lv : world.getNonSpectatingEntities(
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
         if (lv.currentScreenHandler instanceof GenericContainerScreenHandler) {
            Inventory lv2 = ((GenericContainerScreenHandler)lv.currentScreenHandler).getInventory();
            if (lv2 == inventory || lv2 instanceof DoubleInventory && ((DoubleInventory)lv2).isPart(inventory)) {
               l++;
            }
         }
      }

      return l;
   }

   private void playSound(SoundEvent arg) {
      ChestType lv = this.getCachedState().get(ChestBlock.CHEST_TYPE);
      if (lv != ChestType.LEFT) {
         double d = (double)this.pos.getX() + 0.5;
         double e = (double)this.pos.getY() + 0.5;
         double f = (double)this.pos.getZ() + 0.5;
         if (lv == ChestType.RIGHT) {
            Direction lv2 = ChestBlock.getFacing(this.getCachedState());
            d += (double)lv2.getOffsetX() * 0.5;
            f += (double)lv2.getOffsetZ() * 0.5;
         }

         this.world.playSound(null, d, e, f, arg, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
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
      Block lv = this.getCachedState().getBlock();
      if (lv instanceof ChestBlock) {
         this.world.addSyncedBlockEvent(this.pos, lv, 1, this.viewerCount);
         this.world.updateNeighborsAlways(this.pos, lv);
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

   @Environment(EnvType.CLIENT)
   @Override
   public float getAnimationProgress(float tickDelta) {
      return MathHelper.lerp(tickDelta, this.lastAnimationAngle, this.animationAngle);
   }

   public static int getPlayersLookingInChestCount(BlockView world, BlockPos pos) {
      BlockState lv = world.getBlockState(pos);
      if (lv.getBlock().hasBlockEntity()) {
         BlockEntity lv2 = world.getBlockEntity(pos);
         if (lv2 instanceof ChestBlockEntity) {
            return ((ChestBlockEntity)lv2).viewerCount;
         }
      }

      return 0;
   }

   public static void copyInventory(ChestBlockEntity from, ChestBlockEntity to) {
      DefaultedList<ItemStack> lv = from.getInvStackList();
      from.setInvStackList(to.getInvStackList());
      to.setInvStackList(lv);
   }

   @Override
   protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
      return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this);
   }
}
