package net.minecraft.client.sound;

import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public abstract class MovingSoundInstance extends AbstractSoundInstance implements TickableSoundInstance {
   private boolean done;

   protected MovingSoundInstance(SoundEvent _snowman, SoundCategory _snowman) {
      super(_snowman, _snowman);
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
