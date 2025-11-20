/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.GameRules;

public class RevengeGoal
extends TrackTargetGoal {
    private static final TargetPredicate VALID_AVOIDABLES_PREDICATE = new TargetPredicate().includeHidden().ignoreDistanceScalingFactor();
    private boolean groupRevenge;
    private int lastAttackedTime;
    private final Class<?>[] noRevengeTypes;
    private Class<?>[] noHelpTypes;

    public RevengeGoal(PathAwareEntity mob, Class<?> ... noRevengeTypes) {
        super(mob, true);
        this.noRevengeTypes = noRevengeTypes;
        this.setControls(EnumSet.of(Goal.Control.TARGET));
    }

    @Override
    public boolean canStart() {
        int n = this.mob.getLastAttackedTime();
        LivingEntity _snowman2 = this.mob.getAttacker();
        if (n == this.lastAttackedTime || _snowman2 == null) {
            return false;
        }
        if (_snowman2.getType() == EntityType.PLAYER && this.mob.world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER)) {
            return false;
        }
        for (Class<?> clazz : this.noRevengeTypes) {
            if (!clazz.isAssignableFrom(_snowman2.getClass())) continue;
            return false;
        }
        return this.canTrack(_snowman2, VALID_AVOIDABLES_PREDICATE);
    }

    public RevengeGoal setGroupRevenge(Class<?> ... noHelpTypes) {
        this.groupRevenge = true;
        this.noHelpTypes = noHelpTypes;
        return this;
    }

    @Override
    public void start() {
        this.mob.setTarget(this.mob.getAttacker());
        this.target = this.mob.getTarget();
        this.lastAttackedTime = this.mob.getLastAttackedTime();
        this.maxTimeWithoutVisibility = 300;
        if (this.groupRevenge) {
            this.callSameTypeForRevenge();
        }
        super.start();
    }

    protected void callSameTypeForRevenge() {
        double d = this.getFollowRange();
        Box _snowman2 = Box.method_29968(this.mob.getPos()).expand(d, 10.0, d);
        List<?> _snowman3 = this.mob.world.getEntitiesIncludingUngeneratedChunks(this.mob.getClass(), _snowman2);
        for (MobEntity mobEntity2 : _snowman3) {
            MobEntity mobEntity2;
            if (this.mob == mobEntity2 || mobEntity2.getTarget() != null || this.mob instanceof TameableEntity && ((TameableEntity)this.mob).getOwner() != ((TameableEntity)mobEntity2).getOwner() || mobEntity2.isTeammate(this.mob.getAttacker())) continue;
            if (this.noHelpTypes != null) {
                boolean bl = false;
                for (Class<?> clazz : this.noHelpTypes) {
                    if (mobEntity2.getClass() != clazz) continue;
                    bl = true;
                    break;
                }
                if (bl) continue;
            }
            this.setMobEntityTarget(mobEntity2, this.mob.getAttacker());
        }
    }

    protected void setMobEntityTarget(MobEntity mob, LivingEntity target) {
        mob.setTarget(target);
    }
}

