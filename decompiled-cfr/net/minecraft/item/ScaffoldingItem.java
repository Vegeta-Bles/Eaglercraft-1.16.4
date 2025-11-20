/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ScaffoldingItem
extends BlockItem {
    public ScaffoldingItem(Block block, Item.Settings settings) {
        super(block, settings);
    }

    @Override
    @Nullable
    public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        BlockPos blockPos = context.getBlockPos();
        World _snowman2 = context.getWorld();
        BlockState _snowman3 = _snowman2.getBlockState(blockPos);
        if (_snowman3.isOf(_snowman = this.getBlock())) {
            Direction direction = context.shouldCancelInteraction() ? (context.hitsInsideBlock() ? context.getSide().getOpposite() : context.getSide()) : (context.getSide() == Direction.UP ? context.getPlayerFacing() : Direction.UP);
            int _snowman4 = 0;
            BlockPos.Mutable _snowman5 = blockPos.mutableCopy().move(direction);
            while (_snowman4 < 7) {
                if (!_snowman2.isClient && !World.isInBuildLimit(_snowman5)) {
                    PlayerEntity playerEntity = context.getPlayer();
                    int _snowman6 = _snowman2.getHeight();
                    if (!(playerEntity instanceof ServerPlayerEntity) || _snowman5.getY() < _snowman6) break;
                    GameMessageS2CPacket _snowman7 = new GameMessageS2CPacket(new TranslatableText("build.tooHigh", _snowman6).formatted(Formatting.RED), MessageType.GAME_INFO, Util.NIL_UUID);
                    ((ServerPlayerEntity)playerEntity).networkHandler.sendPacket(_snowman7);
                    break;
                }
                _snowman3 = _snowman2.getBlockState(_snowman5);
                if (!_snowman3.isOf(this.getBlock())) {
                    if (!_snowman3.canReplace(context)) break;
                    return ItemPlacementContext.offset(context, _snowman5, direction);
                }
                _snowman5.move(direction);
                if (!direction.getAxis().isHorizontal()) continue;
                ++_snowman4;
            }
            return null;
        }
        if (ScaffoldingBlock.calculateDistance(_snowman2, blockPos) == 7) {
            return null;
        }
        return context;
    }

    @Override
    protected boolean checkStatePlacement() {
        return false;
    }
}

