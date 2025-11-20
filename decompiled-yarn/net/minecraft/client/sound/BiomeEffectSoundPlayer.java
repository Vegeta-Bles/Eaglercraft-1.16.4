package net.minecraft.client.sound;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.Optional;
import java.util.Random;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;

public class BiomeEffectSoundPlayer implements ClientPlayerTickable {
   private final ClientPlayerEntity player;
   private final SoundManager soundManager;
   private final BiomeAccess biomeAccess;
   private final Random random;
   private Object2ObjectArrayMap<Biome, BiomeEffectSoundPlayer.MusicLoop> soundLoops = new Object2ObjectArrayMap();
   private Optional<BiomeMoodSound> moodSound = Optional.empty();
   private Optional<BiomeAdditionsSound> additionsSound = Optional.empty();
   private float moodPercentage;
   private Biome activeBiome;

   public BiomeEffectSoundPlayer(ClientPlayerEntity player, SoundManager soundManager, BiomeAccess biomeAccess) {
      this.random = player.world.getRandom();
      this.player = player;
      this.soundManager = soundManager;
      this.biomeAccess = biomeAccess;
   }

   public float getMoodPercentage() {
      return this.moodPercentage;
   }

   @Override
   public void tick() {
      this.soundLoops.values().removeIf(MovingSoundInstance::isDone);
      Biome _snowman = this.biomeAccess.getBiome(this.player.getX(), this.player.getY(), this.player.getZ());
      if (_snowman != this.activeBiome) {
         this.activeBiome = _snowman;
         this.moodSound = _snowman.getMoodSound();
         this.additionsSound = _snowman.getAdditionsSound();
         this.soundLoops.values().forEach(BiomeEffectSoundPlayer.MusicLoop::fadeOut);
         _snowman.getLoopSound().ifPresent(_snowmanx -> {
            BiomeEffectSoundPlayer.MusicLoop var10000 = (BiomeEffectSoundPlayer.MusicLoop)this.soundLoops.compute(_snowman, (_snowmanxxx, _snowmanxx) -> {
               if (_snowmanxx == null) {
                  _snowmanxx = new BiomeEffectSoundPlayer.MusicLoop(_snowmanx);
                  this.soundManager.play(_snowmanxx);
               }

               _snowmanxx.fadeIn();
               return _snowmanxx;
            });
         });
      }

      this.additionsSound.ifPresent(_snowmanx -> {
         if (this.random.nextDouble() < _snowmanx.getChance()) {
            this.soundManager.play(PositionedSoundInstance.ambient(_snowmanx.getSound()));
         }
      });
      this.moodSound
         .ifPresent(
            _snowmanx -> {
               World _snowmanx = this.player.world;
               int _snowmanxx = _snowmanx.getSpawnRange() * 2 + 1;
               BlockPos _snowmanxxx = new BlockPos(
                  this.player.getX() + (double)this.random.nextInt(_snowmanxx) - (double)_snowmanx.getSpawnRange(),
                  this.player.getEyeY() + (double)this.random.nextInt(_snowmanxx) - (double)_snowmanx.getSpawnRange(),
                  this.player.getZ() + (double)this.random.nextInt(_snowmanxx) - (double)_snowmanx.getSpawnRange()
               );
               int _snowmanxxxx = _snowmanx.getLightLevel(LightType.SKY, _snowmanxxx);
               if (_snowmanxxxx > 0) {
                  this.moodPercentage = this.moodPercentage - (float)_snowmanxxxx / (float)_snowmanx.getMaxLightLevel() * 0.001F;
               } else {
                  this.moodPercentage = this.moodPercentage - (float)(_snowmanx.getLightLevel(LightType.BLOCK, _snowmanxxx) - 1) / (float)_snowmanx.getCultivationTicks();
               }

               if (this.moodPercentage >= 1.0F) {
                  double _snowmanxxxxx = (double)_snowmanxxx.getX() + 0.5;
                  double _snowmanxxxxxx = (double)_snowmanxxx.getY() + 0.5;
                  double _snowmanxxxxxxx = (double)_snowmanxxx.getZ() + 0.5;
                  double _snowmanxxxxxxxx = _snowmanxxxxx - this.player.getX();
                  double _snowmanxxxxxxxxx = _snowmanxxxxxx - this.player.getEyeY();
                  double _snowmanxxxxxxxxxx = _snowmanxxxxxxx - this.player.getZ();
                  double _snowmanxxxxxxxxxxx = (double)MathHelper.sqrt(_snowmanxxxxxxxx * _snowmanxxxxxxxx + _snowmanxxxxxxxxx * _snowmanxxxxxxxxx + _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx);
                  double _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx + _snowmanx.getExtraDistance();
                  PositionedSoundInstance _snowmanxxxxxxxxxxxxx = PositionedSoundInstance.ambient(
                     _snowmanx.getSound(),
                     this.player.getX() + _snowmanxxxxxxxx / _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxxx,
                     this.player.getEyeY() + _snowmanxxxxxxxxx / _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxxx,
                     this.player.getZ() + _snowmanxxxxxxxxxx / _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxxx
                  );
                  this.soundManager.play(_snowmanxxxxxxxxxxxxx);
                  this.moodPercentage = 0.0F;
               } else {
                  this.moodPercentage = Math.max(this.moodPercentage, 0.0F);
               }
            }
         );
   }

   public static class MusicLoop extends MovingSoundInstance {
      private int delta;
      private int strength;

      public MusicLoop(SoundEvent sound) {
         super(sound, SoundCategory.AMBIENT);
         this.repeat = true;
         this.repeatDelay = 0;
         this.volume = 1.0F;
         this.looping = true;
      }

      @Override
      public void tick() {
         if (this.strength < 0) {
            this.setDone();
         }

         this.strength = this.strength + this.delta;
         this.volume = MathHelper.clamp((float)this.strength / 40.0F, 0.0F, 1.0F);
      }

      public void fadeOut() {
         this.strength = Math.min(this.strength, 40);
         this.delta = -1;
      }

      public void fadeIn() {
         this.strength = Math.max(0, this.strength);
         this.delta = 1;
      }
   }
}
