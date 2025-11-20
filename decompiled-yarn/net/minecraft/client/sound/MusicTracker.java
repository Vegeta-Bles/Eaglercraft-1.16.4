package net.minecraft.client.sound;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.math.MathHelper;

public class MusicTracker {
   private final Random random = new Random();
   private final MinecraftClient client;
   @Nullable
   private SoundInstance current;
   private int timeUntilNextSong = 100;

   public MusicTracker(MinecraftClient client) {
      this.client = client;
   }

   public void tick() {
      MusicSound _snowman = this.client.getMusicType();
      if (this.current != null) {
         if (!_snowman.getSound().getId().equals(this.current.getId()) && _snowman.shouldReplaceCurrentMusic()) {
            this.client.getSoundManager().stop(this.current);
            this.timeUntilNextSong = MathHelper.nextInt(this.random, 0, _snowman.getMinDelay() / 2);
         }

         if (!this.client.getSoundManager().isPlaying(this.current)) {
            this.current = null;
            this.timeUntilNextSong = Math.min(this.timeUntilNextSong, MathHelper.nextInt(this.random, _snowman.getMinDelay(), _snowman.getMaxDelay()));
         }
      }

      this.timeUntilNextSong = Math.min(this.timeUntilNextSong, _snowman.getMaxDelay());
      if (this.current == null && this.timeUntilNextSong-- <= 0) {
         this.play(_snowman);
      }
   }

   public void play(MusicSound type) {
      this.current = PositionedSoundInstance.music(type.getSound());
      if (this.current.getSound() != SoundManager.MISSING_SOUND) {
         this.client.getSoundManager().play(this.current);
      }

      this.timeUntilNextSong = Integer.MAX_VALUE;
   }

   public void stop() {
      if (this.current != null) {
         this.client.getSoundManager().stop(this.current);
         this.current = null;
      }

      this.timeUntilNextSong += 100;
   }

   public boolean isPlayingType(MusicSound type) {
      return this.current == null ? false : type.getSound().getId().equals(this.current.getId());
   }
}
