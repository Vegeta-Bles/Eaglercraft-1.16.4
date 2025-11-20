package net.minecraft.sound;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class BiomeAdditionsSound {
   public static final Codec<BiomeAdditionsSound> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(SoundEvent.CODEC.fieldOf("sound").forGetter(_snowmanx -> _snowmanx.sound), Codec.DOUBLE.fieldOf("tick_chance").forGetter(_snowmanx -> _snowmanx.chance))
            .apply(_snowman, BiomeAdditionsSound::new)
   );
   private SoundEvent sound;
   private double chance;

   public BiomeAdditionsSound(SoundEvent sound, double chance) {
      this.sound = sound;
      this.chance = chance;
   }

   public SoundEvent getSound() {
      return this.sound;
   }

   public double getChance() {
      return this.chance;
   }
}
