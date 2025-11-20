package net.minecraft.client.sound;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class WeightedSoundSet implements SoundContainer<Sound> {
   private final List<SoundContainer<Sound>> sounds = Lists.newArrayList();
   private final Random random = new Random();
   private final Identifier id;
   @Nullable
   private final Text subtitle;

   public WeightedSoundSet(Identifier id, @Nullable String subtitle) {
      this.id = id;
      this.subtitle = subtitle == null ? null : new TranslatableText(subtitle);
   }

   @Override
   public int getWeight() {
      int _snowman = 0;

      for (SoundContainer<Sound> _snowmanx : this.sounds) {
         _snowman += _snowmanx.getWeight();
      }

      return _snowman;
   }

   public Sound getSound() {
      int _snowman = this.getWeight();
      if (!this.sounds.isEmpty() && _snowman != 0) {
         int _snowmanx = this.random.nextInt(_snowman);

         for (SoundContainer<Sound> _snowmanxx : this.sounds) {
            _snowmanx -= _snowmanxx.getWeight();
            if (_snowmanx < 0) {
               return _snowmanxx.getSound();
            }
         }

         return SoundManager.MISSING_SOUND;
      } else {
         return SoundManager.MISSING_SOUND;
      }
   }

   public void add(SoundContainer<Sound> _snowman) {
      this.sounds.add(_snowman);
   }

   @Nullable
   public Text getSubtitle() {
      return this.subtitle;
   }

   @Override
   public void preload(SoundSystem soundSystem) {
      for (SoundContainer<Sound> _snowman : this.sounds) {
         _snowman.preload(soundSystem);
      }
   }
}
