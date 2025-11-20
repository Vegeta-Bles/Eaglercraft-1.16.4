package net.minecraft.block.entity;

import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
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

public class ShulkerBoxBlockEntity extends LootableContainerBlockEntity implements SidedInventory, Tickable {
   private static final int[] AVAILABLE_SLOTS = IntStream.range(0, 27).toArray();
   private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
   private int viewerCount;
   private ShulkerBoxBlockEntity.AnimationStage animationStage = ShulkerBoxBlockEntity.AnimationStage.CLOSED;
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
      this(null);
      this.cachedColorUpdateNeeded = true;
   }

   @Override
   public void tick() {
      this.updateAnimation();
      if (this.animationStage == ShulkerBoxBlockEntity.AnimationStage.OPENING || this.animationStage == ShulkerBoxBlockEntity.AnimationStage.CLOSING) {
         this.pushEntities();
      }
   }

   protected void updateAnimation() {
      this.prevAnimationProgress = this.animationProgress;
      switch (this.animationStage) {
         case CLOSED:
            this.animationProgress = 0.0F;
            break;
         case OPENING:
            this.animationProgress += 0.1F;
            if (this.animationProgress >= 1.0F) {
               this.pushEntities();
               this.animationStage = ShulkerBoxBlockEntity.AnimationStage.OPENED;
               this.animationProgress = 1.0F;
               this.updateNeighborStates();
            }
            break;
         case CLOSING:
            this.animationProgress -= 0.1F;
            if (this.animationProgress <= 0.0F) {
               this.animationStage = ShulkerBoxBlockEntity.AnimationStage.CLOSED;
               this.animationProgress = 0.0F;
               this.updateNeighborStates();
            }
            break;
         case OPENED:
            this.animationProgress = 1.0F;
      }
   }

   public ShulkerBoxBlockEntity.AnimationStage getAnimationStage() {
      return this.animationStage;
   }

   public Box getBoundingBox(BlockState state) {
      return this.getBoundingBox(state.get(ShulkerBoxBlock.FACING));
   }

   public Box getBoundingBox(Direction openDirection) {
      float _snowman = this.getAnimationProgress(1.0F);
      return VoxelShapes.fullCube()
         .getBoundingBox()
         .stretch(
            (double)(0.5F * _snowman * (float)openDirection.getOffsetX()),
            (double)(0.5F * _snowman * (float)openDirection.getOffsetY()),
            (double)(0.5F * _snowman * (float)openDirection.getOffsetZ())
         );
   }

   private Box getCollisionBox(Direction facing) {
      Direction _snowman = facing.getOpposite();
      return this.getBoundingBox(facing).shrink((double)_snowman.getOffsetX(), (double)_snowman.getOffsetY(), (double)_snowman.getOffsetZ());
   }

   private void pushEntities() {
      BlockState _snowman = this.world.getBlockState(this.getPos());
      if (_snowman.getBlock() instanceof ShulkerBoxBlock) {
         Direction _snowmanx = _snowman.get(ShulkerBoxBlock.FACING);
         Box _snowmanxx = this.getCollisionBox(_snowmanx).offset(this.pos);
         List<Entity> _snowmanxxx = this.world.getOtherEntities(null, _snowmanxx);
         if (!_snowmanxxx.isEmpty()) {
            for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx.size(); _snowmanxxxx++) {
               Entity _snowmanxxxxx = _snowmanxxx.get(_snowmanxxxx);
               if (_snowmanxxxxx.getPistonBehavior() != PistonBehavior.IGNORE) {
                  double _snowmanxxxxxx = 0.0;
                  double _snowmanxxxxxxx = 0.0;
                  double _snowmanxxxxxxxx = 0.0;
                  Box _snowmanxxxxxxxxx = _snowmanxxxxx.getBoundingBox();
                  switch (_snowmanx.getAxis()) {
                     case X:
                        if (_snowmanx.getDirection() == Direction.AxisDirection.POSITIVE) {
                           _snowmanxxxxxx = _snowmanxx.maxX - _snowmanxxxxxxxxx.minX;
                        } else {
                           _snowmanxxxxxx = _snowmanxxxxxxxxx.maxX - _snowmanxx.minX;
                        }

                        _snowmanxxxxxx += 0.01;
                        break;
                     case Y:
                        if (_snowmanx.getDirection() == Direction.AxisDirection.POSITIVE) {
                           _snowmanxxxxxxx = _snowmanxx.maxY - _snowmanxxxxxxxxx.minY;
                        } else {
                           _snowmanxxxxxxx = _snowmanxxxxxxxxx.maxY - _snowmanxx.minY;
                        }

                        _snowmanxxxxxxx += 0.01;
                        break;
                     case Z:
                        if (_snowmanx.getDirection() == Direction.AxisDirection.POSITIVE) {
                           _snowmanxxxxxxxx = _snowmanxx.maxZ - _snowmanxxxxxxxxx.minZ;
                        } else {
                           _snowmanxxxxxxxx = _snowmanxxxxxxxxx.maxZ - _snowmanxx.minZ;
                        }

                        _snowmanxxxxxxxx += 0.01;
                  }

                  _snowmanxxxxx.move(
                     MovementType.SHULKER_BOX,
                     new Vec3d(_snowmanxxxxxx * (double)_snowmanx.getOffsetX(), _snowmanxxxxxxx * (double)_snowmanx.getOffsetY(), _snowmanxxxxxxxx * (double)_snowmanx.getOffsetZ())
                  );
               }
            }
         }
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
            this.animationStage = ShulkerBoxBlockEntity.AnimationStage.CLOSING;
            this.updateNeighborStates();
         }

         if (data == 1) {
            this.animationStage = ShulkerBoxBlockEntity.AnimationStage.OPENING;
            this.updateNeighborStates();
         }

         return true;
      } else {
         return super.onSyncedBlockEvent(type, data);
      }
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

         this.viewerCount++;
         this.world.addSyncedBlockEvent(this.pos, this.getCachedState().getBlock(), 1, this.viewerCount);
         if (this.viewerCount == 1) {
            this.world.playSound(null, this.pos, SoundEvents.BLOCK_SHULKER_BOX_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
         }
      }
   }

   @Override
   public void onClose(PlayerEntity player) {
      if (!player.isSpectator()) {
         this.viewerCount--;
         this.world.addSyncedBlockEvent(this.pos, this.getCachedState().getBlock(), 1, this.viewerCount);
         if (this.viewerCount <= 0) {
            this.world.playSound(null, this.pos, SoundEvents.BLOCK_SHULKER_BOX_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
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

   public float getAnimationProgress(float _snowman) {
      return MathHelper.lerp(_snowman, this.prevAnimationProgress, this.animationProgress);
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
      return this.animationStage == ShulkerBoxBlockEntity.AnimationStage.CLOSED;
   }

   public static enum AnimationStage {
      CLOSED,
      OPENING,
      OPENED,
      CLOSING;

      private AnimationStage() {
      }
   }
}
