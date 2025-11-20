package net.minecraft.client.texture;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.render.SpriteTexturedVertexConsumer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.resource.metadata.AnimationFrameResourceMetadata;
import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;

public class Sprite implements AutoCloseable {
   private final SpriteAtlasTexture atlas;
   private final Sprite.Info info;
   private final AnimationResourceMetadata animationMetadata;
   protected final NativeImage[] images;
   private final int[] frameXs;
   private final int[] frameYs;
   @Nullable
   private final Sprite.Interpolation interpolation;
   private final int x;
   private final int y;
   private final float uMin;
   private final float uMax;
   private final float vMin;
   private final float vMax;
   private int frameIndex;
   private int frameTicks;

   protected Sprite(SpriteAtlasTexture _snowman, Sprite.Info _snowman, int maxLevel, int atlasWidth, int atlasHeight, int x, int y, NativeImage _snowman) {
      this.atlas = _snowman;
      AnimationResourceMetadata _snowmanxxx = _snowman.animationData;
      int _snowmanxxxx = _snowman.width;
      int _snowmanxxxxx = _snowman.height;
      this.x = x;
      this.y = y;
      this.uMin = (float)x / (float)atlasWidth;
      this.uMax = (float)(x + _snowmanxxxx) / (float)atlasWidth;
      this.vMin = (float)y / (float)atlasHeight;
      this.vMax = (float)(y + _snowmanxxxxx) / (float)atlasHeight;
      int _snowmanxxxxxx = _snowman.getWidth() / _snowmanxxx.getWidth(_snowmanxxxx);
      int _snowmanxxxxxxx = _snowman.getHeight() / _snowmanxxx.getHeight(_snowmanxxxxx);
      if (_snowmanxxx.getFrameCount() > 0) {
         int _snowmanxxxxxxxx = _snowmanxxx.getFrameIndexSet().stream().max(Integer::compareTo).get() + 1;
         this.frameXs = new int[_snowmanxxxxxxxx];
         this.frameYs = new int[_snowmanxxxxxxxx];
         Arrays.fill(this.frameXs, -1);
         Arrays.fill(this.frameYs, -1);

         for (int _snowmanxxxxxxxxx : _snowmanxxx.getFrameIndexSet()) {
            if (_snowmanxxxxxxxxx >= _snowmanxxxxxx * _snowmanxxxxxxx) {
               throw new RuntimeException("invalid frameindex " + _snowmanxxxxxxxxx);
            }

            int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx / _snowmanxxxxxx;
            int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx % _snowmanxxxxxx;
            this.frameXs[_snowmanxxxxxxxxx] = _snowmanxxxxxxxxxxx;
            this.frameYs[_snowmanxxxxxxxxx] = _snowmanxxxxxxxxxx;
         }
      } else {
         List<AnimationFrameResourceMetadata> _snowmanxxxxxxxx = Lists.newArrayList();
         int _snowmanxxxxxxxxx = _snowmanxxxxxx * _snowmanxxxxxxx;
         this.frameXs = new int[_snowmanxxxxxxxxx];
         this.frameYs = new int[_snowmanxxxxxxxxx];

         for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < _snowmanxxxxxxx; _snowmanxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxxxxxx++) {
               int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx * _snowmanxxxxxx + _snowmanxxxxxxxxxxx;
               this.frameXs[_snowmanxxxxxxxxxxxx] = _snowmanxxxxxxxxxxx;
               this.frameYs[_snowmanxxxxxxxxxxxx] = _snowmanxxxxxxxxxx;
               _snowmanxxxxxxxx.add(new AnimationFrameResourceMetadata(_snowmanxxxxxxxxxxxx, -1));
            }
         }

         _snowmanxxx = new AnimationResourceMetadata(_snowmanxxxxxxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxx.getDefaultFrameTime(), _snowmanxxx.shouldInterpolate());
      }

      this.info = new Sprite.Info(_snowman.id, _snowmanxxxx, _snowmanxxxxx, _snowmanxxx);
      this.animationMetadata = _snowmanxxx;

      try {
         try {
            this.images = MipmapHelper.getMipmapLevelsImages(_snowman, maxLevel);
         } catch (Throwable var19) {
            CrashReport _snowmanxxxxxxxx = CrashReport.create(var19, "Generating mipmaps for frame");
            CrashReportSection _snowmanxxxxxxxxx = _snowmanxxxxxxxx.addElement("Frame being iterated");
            _snowmanxxxxxxxxx.add("First frame", () -> {
               StringBuilder _snowmanxxxxxxxxxx = new StringBuilder();
               if (_snowmanxxxxxxxxxx.length() > 0) {
                  _snowmanxxxxxxxxxx.append(", ");
               }

               _snowmanxxxxxxxxxx.append(_snowman.getWidth()).append("x").append(_snowman.getHeight());
               return _snowmanxxxxxxxxxx.toString();
            });
            throw new CrashException(_snowmanxxxxxxxx);
         }
      } catch (Throwable var20) {
         CrashReport _snowmanxxxxxxxx = CrashReport.create(var20, "Applying mipmap");
         CrashReportSection _snowmanxxxxxxxxx = _snowmanxxxxxxxx.addElement("Sprite being mipmapped");
         _snowmanxxxxxxxxx.add("Sprite name", () -> this.getId().toString());
         _snowmanxxxxxxxxx.add("Sprite size", () -> this.getWidth() + " x " + this.getHeight());
         _snowmanxxxxxxxxx.add("Sprite frames", () -> this.getFrameCount() + " frames");
         _snowmanxxxxxxxxx.add("Mipmap levels", maxLevel);
         throw new CrashException(_snowmanxxxxxxxx);
      }

      if (_snowmanxxx.shouldInterpolate()) {
         this.interpolation = new Sprite.Interpolation(_snowman, maxLevel);
      } else {
         this.interpolation = null;
      }
   }

   private void upload(int frame) {
      int _snowman = this.frameXs[frame] * this.info.width;
      int _snowmanx = this.frameYs[frame] * this.info.height;
      this.upload(_snowman, _snowmanx, this.images);
   }

   private void upload(int frameX, int frameY, NativeImage[] output) {
      for (int _snowman = 0; _snowman < this.images.length; _snowman++) {
         output[_snowman].upload(_snowman, this.x >> _snowman, this.y >> _snowman, frameX >> _snowman, frameY >> _snowman, this.info.width >> _snowman, this.info.height >> _snowman, this.images.length > 1, false);
      }
   }

   public int getWidth() {
      return this.info.width;
   }

   public int getHeight() {
      return this.info.height;
   }

   public float getMinU() {
      return this.uMin;
   }

   public float getMaxU() {
      return this.uMax;
   }

   public float getFrameU(double frame) {
      float _snowman = this.uMax - this.uMin;
      return this.uMin + _snowman * (float)frame / 16.0F;
   }

   public float getMinV() {
      return this.vMin;
   }

   public float getMaxV() {
      return this.vMax;
   }

   public float getFrameV(double frame) {
      float _snowman = this.vMax - this.vMin;
      return this.vMin + _snowman * (float)frame / 16.0F;
   }

   public Identifier getId() {
      return this.info.id;
   }

   public SpriteAtlasTexture getAtlas() {
      return this.atlas;
   }

   public int getFrameCount() {
      return this.frameXs.length;
   }

   @Override
   public void close() {
      for (NativeImage _snowman : this.images) {
         if (_snowman != null) {
            _snowman.close();
         }
      }

      if (this.interpolation != null) {
         this.interpolation.close();
      }
   }

   @Override
   public String toString() {
      int _snowman = this.frameXs.length;
      return "TextureAtlasSprite{name='"
         + this.info.id
         + '\''
         + ", frameCount="
         + _snowman
         + ", x="
         + this.x
         + ", y="
         + this.y
         + ", height="
         + this.info.height
         + ", width="
         + this.info.width
         + ", u0="
         + this.uMin
         + ", u1="
         + this.uMax
         + ", v0="
         + this.vMin
         + ", v1="
         + this.vMax
         + '}';
   }

   public boolean isPixelTransparent(int frame, int x, int y) {
      return (this.images[0].getPixelColor(x + this.frameXs[frame] * this.info.width, y + this.frameYs[frame] * this.info.height) >> 24 & 0xFF) == 0;
   }

   public void upload() {
      this.upload(0);
   }

   private float getFrameDeltaFactor() {
      float _snowman = (float)this.info.width / (this.uMax - this.uMin);
      float _snowmanx = (float)this.info.height / (this.vMax - this.vMin);
      return Math.max(_snowmanx, _snowman);
   }

   public float getAnimationFrameDelta() {
      return 4.0F / this.getFrameDeltaFactor();
   }

   public void tickAnimation() {
      this.frameTicks++;
      if (this.frameTicks >= this.animationMetadata.getFrameTime(this.frameIndex)) {
         int _snowman = this.animationMetadata.getFrameIndex(this.frameIndex);
         int _snowmanx = this.animationMetadata.getFrameCount() == 0 ? this.getFrameCount() : this.animationMetadata.getFrameCount();
         this.frameIndex = (this.frameIndex + 1) % _snowmanx;
         this.frameTicks = 0;
         int _snowmanxx = this.animationMetadata.getFrameIndex(this.frameIndex);
         if (_snowman != _snowmanxx && _snowmanxx >= 0 && _snowmanxx < this.getFrameCount()) {
            this.upload(_snowmanxx);
         }
      } else if (this.interpolation != null) {
         if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> _snowman.apply());
         } else {
            this.interpolation.apply();
         }
      }
   }

   public boolean isAnimated() {
      return this.animationMetadata.getFrameCount() > 1;
   }

   public VertexConsumer getTextureSpecificVertexConsumer(VertexConsumer _snowman) {
      return new SpriteTexturedVertexConsumer(_snowman, this);
   }

   public static final class Info {
      private final Identifier id;
      private final int width;
      private final int height;
      private final AnimationResourceMetadata animationData;

      public Info(Identifier id, int width, int height, AnimationResourceMetadata animationData) {
         this.id = id;
         this.width = width;
         this.height = height;
         this.animationData = animationData;
      }

      public Identifier getId() {
         return this.id;
      }

      public int getWidth() {
         return this.width;
      }

      public int getHeight() {
         return this.height;
      }
   }

   final class Interpolation implements AutoCloseable {
      private final NativeImage[] images;

      private Interpolation(Sprite.Info var2, int mipmap) {
         this.images = new NativeImage[mipmap + 1];

         for (int _snowman = 0; _snowman < this.images.length; _snowman++) {
            int _snowmanx = _snowman.width >> _snowman;
            int _snowmanxx = _snowman.height >> _snowman;
            if (this.images[_snowman] == null) {
               this.images[_snowman] = new NativeImage(_snowmanx, _snowmanxx, false);
            }
         }
      }

      private void apply() {
         double _snowman = 1.0 - (double)Sprite.this.frameTicks / (double)Sprite.this.animationMetadata.getFrameTime(Sprite.this.frameIndex);
         int _snowmanx = Sprite.this.animationMetadata.getFrameIndex(Sprite.this.frameIndex);
         int _snowmanxx = Sprite.this.animationMetadata.getFrameCount() == 0 ? Sprite.this.getFrameCount() : Sprite.this.animationMetadata.getFrameCount();
         int _snowmanxxx = Sprite.this.animationMetadata.getFrameIndex((Sprite.this.frameIndex + 1) % _snowmanxx);
         if (_snowmanx != _snowmanxxx && _snowmanxxx >= 0 && _snowmanxxx < Sprite.this.getFrameCount()) {
            for (int _snowmanxxxx = 0; _snowmanxxxx < this.images.length; _snowmanxxxx++) {
               int _snowmanxxxxx = Sprite.this.info.width >> _snowmanxxxx;
               int _snowmanxxxxxx = Sprite.this.info.height >> _snowmanxxxx;

               for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxx++) {
                  for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxx++) {
                     int _snowmanxxxxxxxxx = this.getPixelColor(_snowmanx, _snowmanxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxx);
                     int _snowmanxxxxxxxxxx = this.getPixelColor(_snowmanxxx, _snowmanxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxx);
                     int _snowmanxxxxxxxxxxx = this.lerp(_snowman, _snowmanxxxxxxxxx >> 16 & 0xFF, _snowmanxxxxxxxxxx >> 16 & 0xFF);
                     int _snowmanxxxxxxxxxxxx = this.lerp(_snowman, _snowmanxxxxxxxxx >> 8 & 0xFF, _snowmanxxxxxxxxxx >> 8 & 0xFF);
                     int _snowmanxxxxxxxxxxxxx = this.lerp(_snowman, _snowmanxxxxxxxxx & 0xFF, _snowmanxxxxxxxxxx & 0xFF);
                     this.images[_snowmanxxxx].setPixelColor(_snowmanxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx & 0xFF000000 | _snowmanxxxxxxxxxxx << 16 | _snowmanxxxxxxxxxxxx << 8 | _snowmanxxxxxxxxxxxxx);
                  }
               }
            }

            Sprite.this.upload(0, 0, this.images);
         }
      }

      private int getPixelColor(int frameIndex, int layer, int x, int y) {
         return Sprite.this.images[layer]
            .getPixelColor(
               x + (Sprite.this.frameXs[frameIndex] * Sprite.this.info.width >> layer),
               y + (Sprite.this.frameYs[frameIndex] * Sprite.this.info.height >> layer)
            );
      }

      private int lerp(double delta, int to, int from) {
         return (int)(delta * (double)to + (1.0 - delta) * (double)from);
      }

      @Override
      public void close() {
         for (NativeImage _snowman : this.images) {
            if (_snowman != null) {
               _snowman.close();
            }
         }
      }
   }
}
