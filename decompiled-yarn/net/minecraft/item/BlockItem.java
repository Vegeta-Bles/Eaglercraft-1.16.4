package net.minecraft.item;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockItem extends Item {
   @Deprecated
   private final Block block;

   public BlockItem(Block block, Item.Settings settings) {
      super(settings);
      this.block = block;
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      ActionResult _snowman = this.place(new ItemPlacementContext(context));
      return !_snowman.isAccepted() && this.isFood() ? this.use(context.getWorld(), context.getPlayer(), context.getHand()).getResult() : _snowman;
   }

   public ActionResult place(ItemPlacementContext context) {
      if (!context.canPlace()) {
         return ActionResult.FAIL;
      } else {
         ItemPlacementContext _snowman = this.getPlacementContext(context);
         if (_snowman == null) {
            return ActionResult.FAIL;
         } else {
            BlockState _snowmanx = this.getPlacementState(_snowman);
            if (_snowmanx == null) {
               return ActionResult.FAIL;
            } else if (!this.place(_snowman, _snowmanx)) {
               return ActionResult.FAIL;
            } else {
               BlockPos _snowmanxx = _snowman.getBlockPos();
               World _snowmanxxx = _snowman.getWorld();
               PlayerEntity _snowmanxxxx = _snowman.getPlayer();
               ItemStack _snowmanxxxxx = _snowman.getStack();
               BlockState _snowmanxxxxxx = _snowmanxxx.getBlockState(_snowmanxx);
               Block _snowmanxxxxxxx = _snowmanxxxxxx.getBlock();
               if (_snowmanxxxxxxx == _snowmanx.getBlock()) {
                  _snowmanxxxxxx = this.placeFromTag(_snowmanxx, _snowmanxxx, _snowmanxxxxx, _snowmanxxxxxx);
                  this.postPlacement(_snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
                  _snowmanxxxxxxx.onPlaced(_snowmanxxx, _snowmanxx, _snowmanxxxxxx, _snowmanxxxx, _snowmanxxxxx);
                  if (_snowmanxxxx instanceof ServerPlayerEntity) {
                     Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)_snowmanxxxx, _snowmanxx, _snowmanxxxxx);
                  }
               }

               BlockSoundGroup _snowmanxxxxxxxx = _snowmanxxxxxx.getSoundGroup();
               _snowmanxxx.playSound(_snowmanxxxx, _snowmanxx, this.getPlaceSound(_snowmanxxxxxx), SoundCategory.BLOCKS, (_snowmanxxxxxxxx.getVolume() + 1.0F) / 2.0F, _snowmanxxxxxxxx.getPitch() * 0.8F);
               if (_snowmanxxxx == null || !_snowmanxxxx.abilities.creativeMode) {
                  _snowmanxxxxx.decrement(1);
               }

               return ActionResult.success(_snowmanxxx.isClient);
            }
         }
      }
   }

   protected SoundEvent getPlaceSound(BlockState state) {
      return state.getSoundGroup().getPlaceSound();
   }

   @Nullable
   public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
      return context;
   }

   protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
      return writeTagToBlockEntity(world, player, pos, stack);
   }

   @Nullable
   protected BlockState getPlacementState(ItemPlacementContext context) {
      BlockState _snowman = this.getBlock().getPlacementState(context);
      return _snowman != null && this.canPlace(context, _snowman) ? _snowman : null;
   }

   private BlockState placeFromTag(BlockPos pos, World world, ItemStack stack, BlockState state) {
      BlockState _snowman = state;
      CompoundTag _snowmanx = stack.getTag();
      if (_snowmanx != null) {
         CompoundTag _snowmanxx = _snowmanx.getCompound("BlockStateTag");
         StateManager<Block, BlockState> _snowmanxxx = state.getBlock().getStateManager();

         for (String _snowmanxxxx : _snowmanxx.getKeys()) {
            Property<?> _snowmanxxxxx = _snowmanxxx.getProperty(_snowmanxxxx);
            if (_snowmanxxxxx != null) {
               String _snowmanxxxxxx = _snowmanxx.get(_snowmanxxxx).asString();
               _snowman = with(_snowman, _snowmanxxxxx, _snowmanxxxxxx);
            }
         }
      }

      if (_snowman != state) {
         world.setBlockState(pos, _snowman, 2);
      }

      return _snowman;
   }

   private static <T extends Comparable<T>> BlockState with(BlockState state, Property<T> property, String name) {
      return property.parse(name).map(value -> state.with(property, value)).orElse(state);
   }

   protected boolean canPlace(ItemPlacementContext context, BlockState state) {
      PlayerEntity _snowman = context.getPlayer();
      ShapeContext _snowmanx = _snowman == null ? ShapeContext.absent() : ShapeContext.of(_snowman);
      return (!this.checkStatePlacement() || state.canPlaceAt(context.getWorld(), context.getBlockPos()))
         && context.getWorld().canPlace(state, context.getBlockPos(), _snowmanx);
   }

   protected boolean checkStatePlacement() {
      return true;
   }

   protected boolean place(ItemPlacementContext context, BlockState state) {
      return context.getWorld().setBlockState(context.getBlockPos(), state, 11);
   }

   public static boolean writeTagToBlockEntity(World world, @Nullable PlayerEntity player, BlockPos pos, ItemStack stack) {
      MinecraftServer _snowman = world.getServer();
      if (_snowman == null) {
         return false;
      } else {
         CompoundTag _snowmanx = stack.getSubTag("BlockEntityTag");
         if (_snowmanx != null) {
            BlockEntity _snowmanxx = world.getBlockEntity(pos);
            if (_snowmanxx != null) {
               if (!world.isClient && _snowmanxx.copyItemDataRequiresOperator() && (player == null || !player.isCreativeLevelTwoOp())) {
                  return false;
               }

               CompoundTag _snowmanxxx = _snowmanxx.toTag(new CompoundTag());
               CompoundTag _snowmanxxxx = _snowmanxxx.copy();
               _snowmanxxx.copyFrom(_snowmanx);
               _snowmanxxx.putInt("x", pos.getX());
               _snowmanxxx.putInt("y", pos.getY());
               _snowmanxxx.putInt("z", pos.getZ());
               if (!_snowmanxxx.equals(_snowmanxxxx)) {
                  _snowmanxx.fromTag(world.getBlockState(pos), _snowmanxxx);
                  _snowmanxx.markDirty();
                  return true;
               }
            }
         }

         return false;
      }
   }

   @Override
   public String getTranslationKey() {
      return this.getBlock().getTranslationKey();
   }

   @Override
   public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
      if (this.isIn(group)) {
         this.getBlock().addStacksForDisplay(group, stacks);
      }
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
      super.appendTooltip(stack, world, tooltip, context);
      this.getBlock().appendTooltip(stack, world, tooltip, context);
   }

   public Block getBlock() {
      return this.block;
   }

   public void appendBlocks(Map<Block, Item> map, Item item) {
      map.put(this.getBlock(), item);
   }
}
