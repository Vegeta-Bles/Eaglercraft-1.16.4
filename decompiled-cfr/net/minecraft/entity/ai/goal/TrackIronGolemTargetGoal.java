/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

public class TrackIronGolemTargetGoal
extends TrackTargetGoal {
    private final IronGolemEntity golem;
    private LivingEntity target;
    private final TargetPredicate targetPredicate = new TargetPredicate().setBaseMaxDistance(64.0);

    public TrackIronGolemTargetGoal(IronGolemEntity golem) {
        super(golem, false, true);
        this.golem = golem;
        this.setControls(EnumSet.of(Goal.Control.TARGET));
    }

    @Override
    public boolean canStart() {
        Box box = this.golem.getBoundingBox().expand(10.0, 8.0, 10.0);
        List<VillagerEntity> _snowman2 = this.golem.world.getTargets(VillagerEntity.class, this.targetPredicate, this.golem, box);
        List<PlayerEntity> _snowman3 = this.golem.world.getPlayers(this.targetPredicate, this.golem, box);
        for (LivingEntity livingEntity : _snowman2) {
            VillagerEntity villagerEntity = (VillagerEntity)livingEntity;
            for (PlayerEntity playerEntity : _snowman3) {
                int n = villagerEntity.getReputation(playerEntity);
                if (n > -100) continue;
                this.target = playerEntity;
            }
        }
        if (this.target == null) {
            return false;
        }
        return !(this.target instanceof PlayerEntity) || !this.target.isSpectator() && !((PlayerEntity)this.target).isCreative();
    }

    @Override
    public void start() {
        this.golem.setTarget(this.target);
        super.start();
    }
}

