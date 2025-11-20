/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.util.math.Vec3d;

public class FormCaravanGoal
extends Goal {
    public final LlamaEntity llama;
    private double speed;
    private int counter;

    public FormCaravanGoal(LlamaEntity llama, double speed) {
        this.llama = llama;
        this.speed = speed;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        LlamaEntity llamaEntity;
        if (this.llama.isLeashed() || this.llama.isFollowing()) {
            return false;
        }
        List<Entity> list = this.llama.world.getOtherEntities(this.llama, this.llama.getBoundingBox().expand(9.0, 4.0, 9.0), entity -> {
            EntityType<?> entityType = entity.getType();
            return entityType == EntityType.LLAMA || entityType == EntityType.TRADER_LLAMA;
        });
        MobEntity _snowman2 = null;
        double _snowman3 = Double.MAX_VALUE;
        for (Entity _snowman4 : list) {
            llamaEntity = (LlamaEntity)_snowman4;
            if (!llamaEntity.isFollowing() || llamaEntity.hasFollower() || (_snowman = this.llama.squaredDistanceTo(llamaEntity)) > _snowman3) continue;
            _snowman3 = _snowman;
            _snowman2 = llamaEntity;
        }
        if (_snowman2 == null) {
            for (Entity _snowman4 : list) {
                llamaEntity = (LlamaEntity)_snowman4;
                if (!llamaEntity.isLeashed() || llamaEntity.hasFollower() || (_snowman = this.llama.squaredDistanceTo(llamaEntity)) > _snowman3) continue;
                _snowman3 = _snowman;
                _snowman2 = llamaEntity;
            }
        }
        if (_snowman2 == null) {
            return false;
        }
        if (_snowman3 < 4.0) {
            return false;
        }
        if (!_snowman2.isLeashed() && !this.canFollow((LlamaEntity)_snowman2, 1)) {
            return false;
        }
        this.llama.follow((LlamaEntity)_snowman2);
        return true;
    }

    @Override
    public boolean shouldContinue() {
        if (!(this.llama.isFollowing() && this.llama.getFollowing().isAlive() && this.canFollow(this.llama, 0))) {
            return false;
        }
        double d = this.llama.squaredDistanceTo(this.llama.getFollowing());
        if (d > 676.0) {
            if (this.speed <= 3.0) {
                this.speed *= 1.2;
                this.counter = 40;
                return true;
            }
            if (this.counter == 0) {
                return false;
            }
        }
        if (this.counter > 0) {
            --this.counter;
        }
        return true;
    }

    @Override
    public void stop() {
        this.llama.stopFollowing();
        this.speed = 2.1;
    }

    @Override
    public void tick() {
        if (!this.llama.isFollowing()) {
            return;
        }
        if (this.llama.getHoldingEntity() instanceof LeashKnotEntity) {
            return;
        }
        LlamaEntity llamaEntity = this.llama.getFollowing();
        double _snowman2 = this.llama.distanceTo(llamaEntity);
        float _snowman3 = 2.0f;
        Vec3d _snowman4 = new Vec3d(llamaEntity.getX() - this.llama.getX(), llamaEntity.getY() - this.llama.getY(), llamaEntity.getZ() - this.llama.getZ()).normalize().multiply(Math.max(_snowman2 - 2.0, 0.0));
        this.llama.getNavigation().startMovingTo(this.llama.getX() + _snowman4.x, this.llama.getY() + _snowman4.y, this.llama.getZ() + _snowman4.z, this.speed);
    }

    private boolean canFollow(LlamaEntity llama, int length) {
        if (length > 8) {
            return false;
        }
        if (llama.isFollowing()) {
            if (llama.getFollowing().isLeashed()) {
                return true;
            }
            return this.canFollow(llama.getFollowing(), ++length);
        }
        return false;
    }
}

