/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.item;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
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

public class BlockItem
extends Item {
    @Deprecated
    private final Block block;

    public BlockItem(Block block, Item.Settings settings) {
        super(settings);
        this.block = block;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ActionResult actionResult = this.place(new ItemPlacementContext(context));
        if (!actionResult.isAccepted() && this.isFood()) {
            return this.use(context.getWorld(), context.getPlayer(), context.getHand()).getResult();
        }
        return actionResult;
    }

    public ActionResult place(ItemPlacementContext context) {
        if (!context.canPlace()) {
            return ActionResult.FAIL;
        }
        ItemPlacementContext itemPlacementContext = this.getPlacementContext(context);
        if (itemPlacementContext == null) {
            return ActionResult.FAIL;
        }
        BlockState _snowman2 = this.getPlacementState(itemPlacementContext);
        if (_snowman2 == null) {
            return ActionResult.FAIL;
        }
        if (!this.place(itemPlacementContext, _snowman2)) {
            return ActionResult.FAIL;
        }
        BlockPos _snowman3 = itemPlacementContext.getBlockPos();
        World _snowman4 = itemPlacementContext.getWorld();
        PlayerEntity _snowman5 = itemPlacementContext.getPlayer();
        ItemStack _snowman6 = itemPlacementContext.getStack();
        BlockState _snowman7 = _snowman4.getBlockState(_snowman3);
        Block _snowman8 = _snowman7.getBlock();
        if (_snowman8 == _snowman2.getBlock()) {
            _snowman7 = this.placeFromTag(_snowman3, _snowman4, _snowman6, _snowman7);
            this.postPlacement(_snowman3, _snowman4, _snowman5, _snowman6, _snowman7);
            _snowman8.onPlaced(_snowman4, _snowman3, _snowman7, _snowman5, _snowman6);
            if (_snowman5 instanceof ServerPlayerEntity) {
                Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)_snowman5, _snowman3, _snowman6);
            }
        }
        BlockSoundGroup _snowman9 = _snowman7.getSoundGroup();
        _snowman4.playSound(_snowman5, _snowman3, this.getPlaceSound(_snowman7), SoundCategory.BLOCKS, (_snowman9.getVolume() + 1.0f) / 2.0f, _snowman9.getPitch() * 0.8f);
        if (_snowman5 == null || !_snowman5.abilities.creativeMode) {
            _snowman6.decrement(1);
        }
        return ActionResult.success(_snowman4.isClient);
    }

    protected SoundEvent getPlaceSound(BlockState state) {
        return state.getSoundGroup().getPlaceSound();
    }

    @Nullable
    public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        return context;
    }

    protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        return BlockItem.writeTagToBlockEntity(world, player, pos, stack);
    }

    @Nullable
    protected BlockState getPlacementState(ItemPlacementContext context) {
        BlockState blockState = this.getBlock().getPlacementState(context);
        return blockState != null && this.canPlace(context, blockState) ? blockState : null;
    }

    private BlockState placeFromTag(BlockPos pos, World world, ItemStack stack, BlockState state) {
        BlockState _snowman5 = state;
        CompoundTag _snowman2 = stack.getTag();
        if (_snowman2 != null) {
            CompoundTag compoundTag = _snowman2.getCompound("BlockStateTag");
            StateManager<Block, BlockState> _snowman3 = _snowman5.getBlock().getStateManager();
            for (String string : compoundTag.getKeys()) {
                Property<?> property = _snowman3.getProperty(string);
                if (property == null) continue;
                String _snowman4 = compoundTag.get(string).asString();
                _snowman5 = BlockItem.with(_snowman5, property, _snowman4);
            }
        }
        if (_snowman5 != state) {
            world.setBlockState(pos, _snowman5, 2);
        }
        return _snowman5;
    }

    private static <T extends Comparable<T>> BlockState with(BlockState state, Property<T> property, String name) {
        return property.parse(name).map(value -> (BlockState)state.with(property, value)).orElse(state);
    }

    protected boolean canPlace(ItemPlacementContext context, BlockState state) {
        PlayerEntity playerEntity = context.getPlayer();
        ShapeContext _snowman2 = playerEntity == null ? ShapeContext.absent() : ShapeContext.of(playerEntity);
        return (!this.checkStatePlacement() || state.canPlaceAt(context.getWorld(), context.getBlockPos())) && context.getWorld().canPlace(state, context.getBlockPos(), _snowman2);
    }

    protected boolean checkStatePlacement() {
        return true;
    }

    protected boolean place(ItemPlacementContext context, BlockState state) {
        return context.getWorld().setBlockState(context.getBlockPos(), state, 11);
    }

    public static boolean writeTagToBlockEntity(World world, @Nullable PlayerEntity player, BlockPos pos, ItemStack stack) {
        MinecraftServer minecraftServer = world.getServer();
        if (minecraftServer == null) {
            return false;
        }
        CompoundTag _snowman2 = stack.getSubTag("BlockEntityTag");
        if (_snowman2 != null && (_snowman = world.getBlockEntity(pos)) != null) {
            if (!(world.isClient || !_snowman.copyItemDataRequiresOperator() || player != null && player.isCreativeLevelTwoOp())) {
                return false;
            }
            CompoundTag compoundTag = _snowman.toTag(new CompoundTag());
            _snowman = compoundTag.copy();
            compoundTag.copyFrom(_snowman2);
            compoundTag.putInt("x", pos.getX());
            compoundTag.putInt("y", pos.getY());
            compoundTag.putInt("z", pos.getZ());
            if (!compoundTag.equals(_snowman)) {
                _snowman.fromTag(world.getBlockState(pos), compoundTag);
                _snowman.markDirty();
                return true;
            }
        }
        return false;
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

