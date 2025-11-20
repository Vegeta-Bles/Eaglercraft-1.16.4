package net.minecraft.client.sound;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

@Environment(EnvType.CLIENT)
public abstract class MovingSoundInstance extends AbstractSoundInstance implements TickableSoundInstance {
   private boolean done;

   protected MovingSoundInstance(SoundEvent arg, SoundCategory arg2) {
      super(arg, arg2);
   }

   @Override
   public boolean isDone() {
      return this.done;
   }

   protected final void setDone() {
      this.done = true;
      this.repeat = false;
   }
}
