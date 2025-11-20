package net.minecraft.client.texture;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.util.math.MathHelper;

public class TextureStitcher {
   private static final Comparator<TextureStitcher.Holder> COMPARATOR = Comparator.<TextureStitcher.Holder, Integer>comparing(_snowman -> -_snowman.height)
      .thenComparing(_snowman -> -_snowman.width)
      .thenComparing(_snowman -> _snowman.sprite.getId());
   private final int mipLevel;
   private final Set<TextureStitcher.Holder> holders = Sets.newHashSetWithExpectedSize(256);
   private final List<TextureStitcher.Slot> slots = Lists.newArrayListWithCapacity(256);
   private int width;
   private int height;
   private final int maxWidth;
   private final int maxHeight;

   public TextureStitcher(int maxWidth, int maxHeight, int mipLevel) {
      this.mipLevel = mipLevel;
      this.maxWidth = maxWidth;
      this.maxHeight = maxHeight;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public void add(Sprite.Info info) {
      TextureStitcher.Holder _snowman = new TextureStitcher.Holder(info, this.mipLevel);
      this.holders.add(_snowman);
   }

   public void stitch() {
      List<TextureStitcher.Holder> _snowman = Lists.newArrayList(this.holders);
      _snowman.sort(COMPARATOR);

      for (TextureStitcher.Holder _snowmanx : _snowman) {
         if (!this.fit(_snowmanx)) {
            throw new TextureStitcherCannotFitException(_snowmanx.sprite, _snowman.stream().map(_snowmanxx -> _snowmanxx.sprite).collect(ImmutableList.toImmutableList()));
         }
      }

      this.width = MathHelper.smallestEncompassingPowerOfTwo(this.width);
      this.height = MathHelper.smallestEncompassingPowerOfTwo(this.height);
   }

   public void getStitchedSprites(TextureStitcher.SpriteConsumer _snowman) {
      for (TextureStitcher.Slot _snowmanx : this.slots) {
         _snowmanx.addAllFilledSlots(_snowmanxxx -> {
            TextureStitcher.Holder _snowmanxx = _snowmanxxx.getTexture();
            Sprite.Info _snowmanxxx = _snowmanxx.sprite;
            _snowman.load(_snowmanxxx, this.width, this.height, _snowmanxxx.getX(), _snowmanxxx.getY());
         });
      }
   }

   private static int applyMipLevel(int size, int mipLevel) {
      return (size >> mipLevel) + ((size & (1 << mipLevel) - 1) == 0 ? 0 : 1) << mipLevel;
   }

   private boolean fit(TextureStitcher.Holder _snowman) {
      for (TextureStitcher.Slot _snowmanx : this.slots) {
         if (_snowmanx.fit(_snowman)) {
            return true;
         }
      }

      return this.growAndFit(_snowman);
   }

   private boolean growAndFit(TextureStitcher.Holder _snowman) {
      int _snowmanx = MathHelper.smallestEncompassingPowerOfTwo(this.width);
      int _snowmanxx = MathHelper.smallestEncompassingPowerOfTwo(this.height);
      int _snowmanxxx = MathHelper.smallestEncompassingPowerOfTwo(this.width + _snowman.width);
      int _snowmanxxxx = MathHelper.smallestEncompassingPowerOfTwo(this.height + _snowman.height);
      boolean _snowmanxxxxx = _snowmanxxx <= this.maxWidth;
      boolean _snowmanxxxxxx = _snowmanxxxx <= this.maxHeight;
      if (!_snowmanxxxxx && !_snowmanxxxxxx) {
         return false;
      } else {
         boolean _snowmanxxxxxxx = _snowmanxxxxx && _snowmanx != _snowmanxxx;
         boolean _snowmanxxxxxxxx = _snowmanxxxxxx && _snowmanxx != _snowmanxxxx;
         boolean _snowmanxxxxxxxxx;
         if (_snowmanxxxxxxx ^ _snowmanxxxxxxxx) {
            _snowmanxxxxxxxxx = _snowmanxxxxxxx;
         } else {
            _snowmanxxxxxxxxx = _snowmanxxxxx && _snowmanx <= _snowmanxx;
         }

         TextureStitcher.Slot _snowmanxxxxxxxxxx;
         if (_snowmanxxxxxxxxx) {
            if (this.height == 0) {
               this.height = _snowman.height;
            }

            _snowmanxxxxxxxxxx = new TextureStitcher.Slot(this.width, 0, _snowman.width, this.height);
            this.width = this.width + _snowman.width;
         } else {
            _snowmanxxxxxxxxxx = new TextureStitcher.Slot(0, this.height, this.width, _snowman.height);
            this.height = this.height + _snowman.height;
         }

         _snowmanxxxxxxxxxx.fit(_snowman);
         this.slots.add(_snowmanxxxxxxxxxx);
         return true;
      }
   }

   static class Holder {
      public final Sprite.Info sprite;
      public final int width;
      public final int height;

      public Holder(Sprite.Info sprite, int mipLevel) {
         this.sprite = sprite;
         this.width = TextureStitcher.applyMipLevel(sprite.getWidth(), mipLevel);
         this.height = TextureStitcher.applyMipLevel(sprite.getHeight(), mipLevel);
      }

      @Override
      public String toString() {
         return "Holder{width=" + this.width + ", height=" + this.height + '}';
      }
   }

   public static class Slot {
      private final int x;
      private final int y;
      private final int width;
      private final int height;
      private List<TextureStitcher.Slot> subSlots;
      private TextureStitcher.Holder texture;

      public Slot(int x, int y, int width, int height) {
         this.x = x;
         this.y = y;
         this.width = width;
         this.height = height;
      }

      public TextureStitcher.Holder getTexture() {
         return this.texture;
      }

      public int getX() {
         return this.x;
      }

      public int getY() {
         return this.y;
      }

      public boolean fit(TextureStitcher.Holder _snowman) {
         if (this.texture != null) {
            return false;
         } else {
            int _snowmanx = _snowman.width;
            int _snowmanxx = _snowman.height;
            if (_snowmanx <= this.width && _snowmanxx <= this.height) {
               if (_snowmanx == this.width && _snowmanxx == this.height) {
                  this.texture = _snowman;
                  return true;
               } else {
                  if (this.subSlots == null) {
                     this.subSlots = Lists.newArrayListWithCapacity(1);
                     this.subSlots.add(new TextureStitcher.Slot(this.x, this.y, _snowmanx, _snowmanxx));
                     int _snowmanxxx = this.width - _snowmanx;
                     int _snowmanxxxx = this.height - _snowmanxx;
                     if (_snowmanxxxx > 0 && _snowmanxxx > 0) {
                        int _snowmanxxxxx = Math.max(this.height, _snowmanxxx);
                        int _snowmanxxxxxx = Math.max(this.width, _snowmanxxxx);
                        if (_snowmanxxxxx >= _snowmanxxxxxx) {
                           this.subSlots.add(new TextureStitcher.Slot(this.x, this.y + _snowmanxx, _snowmanx, _snowmanxxxx));
                           this.subSlots.add(new TextureStitcher.Slot(this.x + _snowmanx, this.y, _snowmanxxx, this.height));
                        } else {
                           this.subSlots.add(new TextureStitcher.Slot(this.x + _snowmanx, this.y, _snowmanxxx, _snowmanxx));
                           this.subSlots.add(new TextureStitcher.Slot(this.x, this.y + _snowmanxx, this.width, _snowmanxxxx));
                        }
                     } else if (_snowmanxxx == 0) {
                        this.subSlots.add(new TextureStitcher.Slot(this.x, this.y + _snowmanxx, _snowmanx, _snowmanxxxx));
                     } else if (_snowmanxxxx == 0) {
                        this.subSlots.add(new TextureStitcher.Slot(this.x + _snowmanx, this.y, _snowmanxxx, _snowmanxx));
                     }
                  }

                  for (TextureStitcher.Slot _snowmanxxx : this.subSlots) {
                     if (_snowmanxxx.fit(_snowman)) {
                        return true;
                     }
                  }

                  return false;
               }
            } else {
               return false;
            }
         }
      }

      public void addAllFilledSlots(Consumer<TextureStitcher.Slot> _snowman) {
         if (this.texture != null) {
            _snowman.accept(this);
         } else if (this.subSlots != null) {
            for (TextureStitcher.Slot _snowmanx : this.subSlots) {
               _snowmanx.addAllFilledSlots(_snowman);
            }
         }
      }

      @Override
      public String toString() {
         return "Slot{originX="
            + this.x
            + ", originY="
            + this.y
            + ", width="
            + this.width
            + ", height="
            + this.height
            + ", texture="
            + this.texture
            + ", subSlots="
            + this.subSlots
            + '}';
      }
   }

   public interface SpriteConsumer {
      void load(Sprite.Info var1, int width, int height, int x, int y);
   }
}
