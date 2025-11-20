package net.minecraft.client.sound;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.sound.SoundEvents;

public class AmbientSoundPlayer implements ClientPlayerTickable {
   private final ClientPlayerEntity player;
   private final SoundManager soundManager;
   private int ticksUntilPlay = 0;

   public AmbientSoundPlayer(ClientPlayerEntity player, SoundManager _snowman) {
      this.player = player;
      this.soundManager = _snowman;
   }

   @Override
   public void tick() {
      this.ticksUntilPlay--;
      if (this.ticksUntilPlay <= 0 && this.player.isSubmergedInWater()) {
         float _snowman = this.player.world.random.nextFloat();
         if (_snowman < 1.0E-4F) {
            this.ticksUntilPlay = 0;
            this.soundManager.play(new AmbientSoundLoops.MusicLoop(this.player, SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE));
         } else if (_snowman < 0.001F) {
            this.ticksUntilPlay = 0;
            this.soundManager.play(new AmbientSoundLoops.MusicLoop(this.player, SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE));
         } else if (_snowman < 0.01F) {
            this.ticksUntilPlay = 0;
            this.soundManager.play(new AmbientSoundLoops.MusicLoop(this.player, SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS));
         }
      }
   }
}
