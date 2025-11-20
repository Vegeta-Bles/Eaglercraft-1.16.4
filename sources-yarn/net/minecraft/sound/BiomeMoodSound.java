package net.minecraft.sound;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class BiomeMoodSound {
   public static final Codec<BiomeMoodSound> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
               SoundEvent.CODEC.fieldOf("sound").forGetter(arg -> arg.sound),
               Codec.INT.fieldOf("tick_delay").forGetter(arg -> arg.cultivationTicks),
               Codec.INT.fieldOf("block_search_extent").forGetter(arg -> arg.spawnRange),
               Codec.DOUBLE.fieldOf("offset").forGetter(arg -> arg.extraDistance)
            )
            .apply(instance, BiomeMoodSound::new)
   );
   public static final BiomeMoodSound CAVE = new BiomeMoodSound(SoundEvents.AMBIENT_CAVE, 6000, 8, 2.0);
   private SoundEvent sound;
   private int cultivationTicks;
   private int spawnRange;
   private double extraDistance;

   public BiomeMoodSound(SoundEvent sound, int cultivationTicks, int spawnRange, double extraDistance) {
      this.sound = sound;
      this.cultivationTicks = cultivationTicks;
      this.spawnRange = spawnRange;
      this.extraDistance = extraDistance;
   }

   @Environment(EnvType.CLIENT)
   public SoundEvent getSound() {
      return this.sound;
   }

   @Environment(EnvType.CLIENT)
   public int getCultivationTicks() {
      return this.cultivationTicks;
   }

   @Environment(EnvType.CLIENT)
   public int getSpawnRange() {
      return this.spawnRange;
   }

   @Environment(EnvType.CLIENT)
   public double getExtraDistance() {
      return this.extraDistance;
   }
}
