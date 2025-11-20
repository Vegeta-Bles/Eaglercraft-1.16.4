/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.mob.MobEntity;

public class StopAndLookAtEntityGoal
extends LookAtEntityGoal {
    public StopAndLookAtEntityGoal(MobEntity mobEntity, Class<? extends LivingEntity> clazz, float f, float f2) {
        super(mobEntity, clazz, f, f2);
        this.setControls(EnumSet.of(Goal.Control.LOOK, Goal.Control.MOVE));
    }
}

