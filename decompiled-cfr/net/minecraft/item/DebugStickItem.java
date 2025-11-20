/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.item;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.MessageType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class DebugStickItem
extends Item {
    public DebugStickItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        if (!world.isClient) {
            this.use(miner, state, world, pos, false, miner.getStackInHand(Hand.MAIN_HAND));
        }
        return false;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity playerEntity = context.getPlayer();
        World _snowman2 = context.getWorld();
        if (!_snowman2.isClient && playerEntity != null) {
            BlockPos blockPos = context.getBlockPos();
            this.use(playerEntity, _snowman2.getBlockState(blockPos), _snowman2, blockPos, true, context.getStack());
        }
        return ActionResult.success(_snowman2.isClient);
    }

    private void use(PlayerEntity player, BlockState state, WorldAccess world, BlockPos pos, boolean update, ItemStack stack) {
        if (!player.isCreativeLevelTwoOp()) {
            return;
        }
        Block block = state.getBlock();
        StateManager<Block, BlockState> _snowman2 = block.getStateManager();
        Collection<Property<?>> _snowman3 = _snowman2.getProperties();
        String _snowman4 = Registry.BLOCK.getId(block).toString();
        if (_snowman3.isEmpty()) {
            DebugStickItem.sendMessage(player, new TranslatableText(this.getTranslationKey() + ".empty", _snowman4));
            return;
        }
        CompoundTag _snowman5 = stack.getOrCreateSubTag("DebugProperty");
        String _snowman6 = _snowman5.getString(_snowman4);
        Property<?> _snowman7 = _snowman2.getProperty(_snowman6);
        if (update) {
            if (_snowman7 == null) {
                _snowman7 = _snowman3.iterator().next();
            }
            BlockState blockState = DebugStickItem.cycle(state, _snowman7, player.shouldCancelInteraction());
            world.setBlockState(pos, blockState, 18);
            DebugStickItem.sendMessage(player, new TranslatableText(this.getTranslationKey() + ".update", _snowman7.getName(), DebugStickItem.getValueString(blockState, _snowman7)));
        } else {
            _snowman7 = DebugStickItem.cycle(_snowman3, _snowman7, player.shouldCancelInteraction());
            String string = _snowman7.getName();
            _snowman5.putString(_snowman4, string);
            DebugStickItem.sendMessage(player, new TranslatableText(this.getTranslationKey() + ".select", string, DebugStickItem.getValueString(state, _snowman7)));
        }
    }

    private static <T extends Comparable<T>> BlockState cycle(BlockState state, Property<T> property, boolean inverse) {
        return (BlockState)state.with(property, (Comparable)DebugStickItem.cycle(property.getValues(), state.get(property), inverse));
    }

    private static <T> T cycle(Iterable<T> elements, @Nullable T current, boolean inverse) {
        return inverse ? Util.previous(elements, current) : Util.next(elements, current);
    }

    private static void sendMessage(PlayerEntity player, Text message) {
        ((ServerPlayerEntity)player).sendMessage(message, MessageType.GAME_INFO, Util.NIL_UUID);
    }

    private static <T extends Comparable<T>> String getValueString(BlockState state, Property<T> property) {
        return property.name(state.get(property));
    }
}

