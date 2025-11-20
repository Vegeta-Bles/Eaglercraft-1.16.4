/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.player;

import net.minecraft.nbt.CompoundTag;

public class PlayerAbilities {
    public boolean invulnerable;
    public boolean flying;
    public boolean allowFlying;
    public boolean creativeMode;
    public boolean allowModifyWorld = true;
    private float flySpeed = 0.05f;
    private float walkSpeed = 0.1f;

    public void serialize(CompoundTag compoundTag) {
        _snowman = new CompoundTag();
        _snowman.putBoolean("invulnerable", this.invulnerable);
        _snowman.putBoolean("flying", this.flying);
        _snowman.putBoolean("mayfly", this.allowFlying);
        _snowman.putBoolean("instabuild", this.creativeMode);
        _snowman.putBoolean("mayBuild", this.allowModifyWorld);
        _snowman.putFloat("flySpeed", this.flySpeed);
        _snowman.putFloat("walkSpeed", this.walkSpeed);
        compoundTag.put("abilities", _snowman);
    }

    public void deserialize(CompoundTag compoundTag) {
        if (compoundTag.contains("abilities", 10)) {
            _snowman = compoundTag.getCompound("abilities");
            this.invulnerable = _snowman.getBoolean("invulnerable");
            this.flying = _snowman.getBoolean("flying");
            this.allowFlying = _snowman.getBoolean("mayfly");
            this.creativeMode = _snowman.getBoolean("instabuild");
            if (_snowman.contains("flySpeed", 99)) {
                this.flySpeed = _snowman.getFloat("flySpeed");
                this.walkSpeed = _snowman.getFloat("walkSpeed");
            }
            if (_snowman.contains("mayBuild", 1)) {
                this.allowModifyWorld = _snowman.getBoolean("mayBuild");
            }
        }
    }

    public float getFlySpeed() {
        return this.flySpeed;
    }

    public void setFlySpeed(float flySpeed) {
        this.flySpeed = flySpeed;
    }

    public float getWalkSpeed() {
        return this.walkSpeed;
    }

    public void setWalkSpeed(float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }
}

