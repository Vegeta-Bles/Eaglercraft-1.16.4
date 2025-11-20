/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class LeadItem
extends Item {
    public LeadItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        Block _snowman2 = world.getBlockState(_snowman = context.getBlockPos()).getBlock();
        if (_snowman2.isIn(BlockTags.FENCES)) {
            PlayerEntity playerEntity = context.getPlayer();
            if (!world.isClient && playerEntity != null) {
                LeadItem.attachHeldMobsToBlock(playerEntity, world, _snowman);
            }
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    public static ActionResult attachHeldMobsToBlock(PlayerEntity playerEntity, World world, BlockPos blockPos) {
        LeashKnotEntity leashKnotEntity = null;
        boolean _snowman2 = false;
        double _snowman3 = 7.0;
        int _snowman4 = blockPos.getX();
        int _snowman5 = blockPos.getY();
        int _snowman6 = blockPos.getZ();
        List<MobEntity> _snowman7 = world.getNonSpectatingEntities(MobEntity.class, new Box((double)_snowman4 - 7.0, (double)_snowman5 - 7.0, (double)_snowman6 - 7.0, (double)_snowman4 + 7.0, (double)_snowman5 + 7.0, (double)_snowman6 + 7.0));
        for (MobEntity mobEntity2 : _snowman7) {
            MobEntity mobEntity2;
            if (mobEntity2.getHoldingEntity() != playerEntity) continue;
            if (leashKnotEntity == null) {
                leashKnotEntity = LeashKnotEntity.getOrCreate(world, blockPos);
            }
            mobEntity2.attachLeash(leashKnotEntity, true);
            _snowman2 = true;
        }
        return _snowman2 ? ActionResult.SUCCESS : ActionResult.PASS;
    }
}

