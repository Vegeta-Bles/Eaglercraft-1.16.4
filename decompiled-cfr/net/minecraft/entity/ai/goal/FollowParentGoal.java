/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.ai.goal;

import java.util.List;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;

public class FollowParentGoal
extends Goal {
    private final AnimalEntity animal;
    private AnimalEntity parent;
    private final double speed;
    private int delay;

    public FollowParentGoal(AnimalEntity animal, double speed) {
        this.animal = animal;
        this.speed = speed;
    }

    @Override
    public boolean canStart() {
        if (this.animal.getBreedingAge() >= 0) {
            return false;
        }
        List<?> list = this.animal.world.getNonSpectatingEntities(this.animal.getClass(), this.animal.getBoundingBox().expand(8.0, 4.0, 8.0));
        AnimalEntity _snowman2 = null;
        double _snowman3 = Double.MAX_VALUE;
        for (AnimalEntity animalEntity : list) {
            if (animalEntity.getBreedingAge() < 0 || (_snowman = this.animal.squaredDistanceTo(animalEntity)) > _snowman3) continue;
            _snowman3 = _snowman;
            _snowman2 = animalEntity;
        }
        if (_snowman2 == null) {
            return false;
        }
        if (_snowman3 < 9.0) {
            return false;
        }
        this.parent = _snowman2;
        return true;
    }

    @Override
    public boolean shouldContinue() {
        if (this.animal.getBreedingAge() >= 0) {
            return false;
        }
        if (!this.parent.isAlive()) {
            return false;
        }
        double d = this.animal.squaredDistanceTo(this.parent);
        return !(d < 9.0) && !(d > 256.0);
    }

    @Override
    public void start() {
        this.delay = 0;
    }

    @Override
    public void stop() {
        this.parent = null;
    }

    @Override
    public void tick() {
        if (--this.delay > 0) {
            return;
        }
        this.delay = 10;
        this.animal.getNavigation().startMovingTo(this.parent, this.speed);
    }
}

