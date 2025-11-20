package net.minecraft.entity.player;

import net.minecraft.nbt.CompoundTag;

public class PlayerAbilities {
   public boolean invulnerable;
   public boolean flying;
   public boolean allowFlying;
   public boolean creativeMode;
   public boolean allowModifyWorld = true;
   private float flySpeed = 0.05F;
   private float walkSpeed = 0.1F;

   public PlayerAbilities() {
   }

   public void serialize(CompoundTag _snowman) {
      CompoundTag _snowmanx = new CompoundTag();
      _snowmanx.putBoolean("invulnerable", this.invulnerable);
      _snowmanx.putBoolean("flying", this.flying);
      _snowmanx.putBoolean("mayfly", this.allowFlying);
      _snowmanx.putBoolean("instabuild", this.creativeMode);
      _snowmanx.putBoolean("mayBuild", this.allowModifyWorld);
      _snowmanx.putFloat("flySpeed", this.flySpeed);
      _snowmanx.putFloat("walkSpeed", this.walkSpeed);
      _snowman.put("abilities", _snowmanx);
   }

   public void deserialize(CompoundTag _snowman) {
      if (_snowman.contains("abilities", 10)) {
         CompoundTag _snowmanx = _snowman.getCompound("abilities");
         this.invulnerable = _snowmanx.getBoolean("invulnerable");
         this.flying = _snowmanx.getBoolean("flying");
         this.allowFlying = _snowmanx.getBoolean("mayfly");
         this.creativeMode = _snowmanx.getBoolean("instabuild");
         if (_snowmanx.contains("flySpeed", 99)) {
            this.flySpeed = _snowmanx.getFloat("flySpeed");
            this.walkSpeed = _snowmanx.getFloat("walkSpeed");
         }

         if (_snowmanx.contains("mayBuild", 1)) {
            this.allowModifyWorld = _snowmanx.getBoolean("mayBuild");
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
