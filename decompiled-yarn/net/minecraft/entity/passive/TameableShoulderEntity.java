package net.minecraft.entity.passive;

import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public abstract class TameableShoulderEntity extends TameableEntity {
   private int ticks;

   protected TameableShoulderEntity(EntityType<? extends TameableShoulderEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public boolean mountOnto(ServerPlayerEntity player) {
      CompoundTag _snowman = new CompoundTag();
      _snowman.putString("id", this.getSavedEntityId());
      this.toTag(_snowman);
      if (player.addShoulderEntity(_snowman)) {
         this.remove();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void tick() {
      this.ticks++;
      super.tick();
   }

   public boolean isReadyToSitOnPlayer() {
      return this.ticks > 100;
   }
}
