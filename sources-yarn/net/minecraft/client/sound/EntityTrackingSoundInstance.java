package net.minecraft.client.sound;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

@Environment(EnvType.CLIENT)
public class EntityTrackingSoundInstance extends MovingSoundInstance {
   private final Entity entity;

   public EntityTrackingSoundInstance(SoundEvent sound, SoundCategory arg2, Entity arg3) {
      this(sound, arg2, 1.0F, 1.0F, arg3);
   }

   public EntityTrackingSoundInstance(SoundEvent sound, SoundCategory arg2, float volume, float pitch, Entity arg3) {
      super(sound, arg2);
      this.volume = volume;
      this.pitch = pitch;
      this.entity = arg3;
      this.x = (double)((float)this.entity.getX());
      this.y = (double)((float)this.entity.getY());
      this.z = (double)((float)this.entity.getZ());
   }

   @Override
   public boolean canPlay() {
      return !this.entity.isSilent();
   }

   @Override
   public void tick() {
      if (this.entity.removed) {
         this.setDone();
      } else {
         this.x = (double)((float)this.entity.getX());
         this.y = (double)((float)this.entity.getY());
         this.z = (double)((float)this.entity.getZ());
      }
   }
}
