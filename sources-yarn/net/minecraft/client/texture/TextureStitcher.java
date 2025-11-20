package net.minecraft.client.texture;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class TextureStitcher {
   private static final Comparator<TextureStitcher.Holder> COMPARATOR = Comparator.<TextureStitcher.Holder, Integer>comparing(arg -> -arg.height)
      .thenComparing(arg -> -arg.width)
      .thenComparing(arg -> arg.sprite.getId());
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
      TextureStitcher.Holder lv = new TextureStitcher.Holder(info, this.mipLevel);
      this.holders.add(lv);
   }

   public void stitch() {
      List<TextureStitcher.Holder> list = Lists.newArrayList(this.holders);
      list.sort(COMPARATOR);

      for (TextureStitcher.Holder lv : list) {
         if (!this.fit(lv)) {
            throw new TextureStitcherCannotFitException(lv.sprite, list.stream().map(arg -> arg.sprite).collect(ImmutableList.toImmutableList()));
         }
      }

      this.width = MathHelper.smallestEncompassingPowerOfTwo(this.width);
      this.height = MathHelper.smallestEncompassingPowerOfTwo(this.height);
   }

   public void getStitchedSprites(TextureStitcher.SpriteConsumer arg) {
      for (TextureStitcher.Slot lv : this.slots) {
         lv.addAllFilledSlots(arg2 -> {
            TextureStitcher.Holder lvx = arg2.getTexture();
            Sprite.Info lv2 = lvx.sprite;
            arg.load(lv2, this.width, this.height, arg2.getX(), arg2.getY());
         });
      }
   }

   private static int applyMipLevel(int size, int mipLevel) {
      return (size >> mipLevel) + ((size & (1 << mipLevel) - 1) == 0 ? 0 : 1) << mipLevel;
   }

   private boolean fit(TextureStitcher.Holder arg) {
      for (TextureStitcher.Slot lv : this.slots) {
         if (lv.fit(arg)) {
            return true;
         }
      }

      return this.growAndFit(arg);
   }

   private boolean growAndFit(TextureStitcher.Holder arg) {
      int i = MathHelper.smallestEncompassingPowerOfTwo(this.width);
      int j = MathHelper.smallestEncompassingPowerOfTwo(this.height);
      int k = MathHelper.smallestEncompassingPowerOfTwo(this.width + arg.width);
      int l = MathHelper.smallestEncompassingPowerOfTwo(this.height + arg.height);
      boolean bl = k <= this.maxWidth;
      boolean bl2 = l <= this.maxHeight;
      if (!bl && !bl2) {
         return false;
      } else {
         boolean bl3 = bl && i != k;
         boolean bl4 = bl2 && j != l;
         boolean bl5;
         if (bl3 ^ bl4) {
            bl5 = bl3;
         } else {
            bl5 = bl && i <= j;
         }

         TextureStitcher.Slot lv;
         if (bl5) {
            if (this.height == 0) {
               this.height = arg.height;
            }

            lv = new TextureStitcher.Slot(this.width, 0, arg.width, this.height);
            this.width = this.width + arg.width;
         } else {
            lv = new TextureStitcher.Slot(0, this.height, this.width, arg.height);
            this.height = this.height + arg.height;
         }

         lv.fit(arg);
         this.slots.add(lv);
         return true;
      }
   }

   @Environment(EnvType.CLIENT)
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

   @Environment(EnvType.CLIENT)
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

      public boolean fit(TextureStitcher.Holder arg) {
         if (this.texture != null) {
            return false;
         } else {
            int i = arg.width;
            int j = arg.height;
            if (i <= this.width && j <= this.height) {
               if (i == this.width && j == this.height) {
                  this.texture = arg;
                  return true;
               } else {
                  if (this.subSlots == null) {
                     this.subSlots = Lists.newArrayListWithCapacity(1);
                     this.subSlots.add(new TextureStitcher.Slot(this.x, this.y, i, j));
                     int k = this.width - i;
                     int l = this.height - j;
                     if (l > 0 && k > 0) {
                        int m = Math.max(this.height, k);
                        int n = Math.max(this.width, l);
                        if (m >= n) {
                           this.subSlots.add(new TextureStitcher.Slot(this.x, this.y + j, i, l));
                           this.subSlots.add(new TextureStitcher.Slot(this.x + i, this.y, k, this.height));
                        } else {
                           this.subSlots.add(new TextureStitcher.Slot(this.x + i, this.y, k, j));
                           this.subSlots.add(new TextureStitcher.Slot(this.x, this.y + j, this.width, l));
                        }
                     } else if (k == 0) {
                        this.subSlots.add(new TextureStitcher.Slot(this.x, this.y + j, i, l));
                     } else if (l == 0) {
                        this.subSlots.add(new TextureStitcher.Slot(this.x + i, this.y, k, j));
                     }
                  }

                  for (TextureStitcher.Slot lv : this.subSlots) {
                     if (lv.fit(arg)) {
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

      public void addAllFilledSlots(Consumer<TextureStitcher.Slot> consumer) {
         if (this.texture != null) {
            consumer.accept(this);
         } else if (this.subSlots != null) {
            for (TextureStitcher.Slot lv : this.subSlots) {
               lv.addAllFilledSlots(consumer);
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

   @Environment(EnvType.CLIENT)
   public interface SpriteConsumer {
      void load(Sprite.Info arg, int width, int height, int x, int y);
   }
}
