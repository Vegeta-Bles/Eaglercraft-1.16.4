/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class FlyOntoTreeGoal
extends WanderAroundFarGoal {
    public FlyOntoTreeGoal(PathAwareEntity pathAwareEntity, double d) {
        super(pathAwareEntity, d);
    }

    @Override
    @Nullable
    protected Vec3d getWanderTarget() {
        Vec3d vec3d = null;
        if (this.mob.isTouchingWater()) {
            vec3d = TargetFinder.findGroundTarget(this.mob, 15, 15);
        }
        if (this.mob.getRandom().nextFloat() >= this.probability) {
            vec3d = this.getTreeTarget();
        }
        return vec3d == null ? super.getWanderTarget() : vec3d;
    }

    @Nullable
    private Vec3d getTreeTarget() {
        BlockPos blockPos = this.mob.getBlockPos();
        BlockPos.Mutable _snowman2 = new BlockPos.Mutable();
        BlockPos.Mutable _snowman3 = new BlockPos.Mutable();
        Iterable<BlockPos> _snowman4 = BlockPos.iterate(MathHelper.floor(this.mob.getX() - 3.0), MathHelper.floor(this.mob.getY() - 6.0), MathHelper.floor(this.mob.getZ() - 3.0), MathHelper.floor(this.mob.getX() + 3.0), MathHelper.floor(this.mob.getY() + 6.0), MathHelper.floor(this.mob.getZ() + 3.0));
        for (BlockPos blockPos2 : _snowman4) {
            if (blockPos.equals(blockPos2) || !(_snowman = (_snowman = this.mob.world.getBlockState(_snowman3.set(blockPos2, Direction.DOWN)).getBlock()) instanceof LeavesBlock || _snowman.isIn(BlockTags.LOGS)) || !this.mob.world.isAir(blockPos2) || !this.mob.world.isAir(_snowman2.set(blockPos2, Direction.UP))) continue;
            return Vec3d.ofBottomCenter(blockPos2);
        }
        return null;
    }
}

