/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.item;

import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class EndCrystalItem
extends Item {
    public EndCrystalItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockState _snowman2 = world.getBlockState(_snowman = context.getBlockPos());
        if (!_snowman2.isOf(Blocks.OBSIDIAN) && !_snowman2.isOf(Blocks.BEDROCK)) {
            return ActionResult.FAIL;
        }
        BlockPos _snowman3 = _snowman.up();
        if (!world.isAir(_snowman3)) {
            return ActionResult.FAIL;
        }
        double _snowman4 = _snowman3.getX();
        List<Entity> _snowman5 = world.getOtherEntities(null, new Box(_snowman4, _snowman = (double)_snowman3.getY(), _snowman = (double)_snowman3.getZ(), _snowman4 + 1.0, _snowman + 2.0, _snowman + 1.0));
        if (!_snowman5.isEmpty()) {
            return ActionResult.FAIL;
        }
        if (world instanceof ServerWorld) {
            EndCrystalEntity endCrystalEntity = new EndCrystalEntity(world, _snowman4 + 0.5, _snowman, _snowman + 0.5);
            endCrystalEntity.setShowBottom(false);
            world.spawnEntity(endCrystalEntity);
            EnderDragonFight _snowman6 = ((ServerWorld)world).getEnderDragonFight();
            if (_snowman6 != null) {
                _snowman6.respawnDragon();
            }
        }
        context.getStack().decrement(1);
        return ActionResult.success(world.isClient);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}

