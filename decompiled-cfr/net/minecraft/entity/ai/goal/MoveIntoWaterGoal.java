/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.ai.goal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;

public class MoveIntoWaterGoal
extends Goal {
    private final PathAwareEntity mob;

    public MoveIntoWaterGoal(PathAwareEntity mob) {
        this.mob = mob;
    }

    @Override
    public boolean canStart() {
        return this.mob.isOnGround() && !this.mob.world.getFluidState(this.mob.getBlockPos()).isIn(FluidTags.WATER);
    }

    @Override
    public void start() {
        Vec3i vec3i = null;
        Iterable<BlockPos> _snowman2 = BlockPos.iterate(MathHelper.floor(this.mob.getX() - 2.0), MathHelper.floor(this.mob.getY() - 2.0), MathHelper.floor(this.mob.getZ() - 2.0), MathHelper.floor(this.mob.getX() + 2.0), MathHelper.floor(this.mob.getY()), MathHelper.floor(this.mob.getZ() + 2.0));
        for (BlockPos blockPos : _snowman2) {
            if (!this.mob.world.getFluidState(blockPos).isIn(FluidTags.WATER)) continue;
            vec3i = blockPos;
            break;
        }
        if (vec3i != null) {
            this.mob.getMoveControl().moveTo(vec3i.getX(), vec3i.getY(), vec3i.getZ(), 1.0);
        }
    }
}

