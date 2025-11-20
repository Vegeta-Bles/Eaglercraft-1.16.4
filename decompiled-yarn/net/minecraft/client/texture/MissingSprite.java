package net.minecraft.client.texture;

import com.google.common.collect.Lists;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.metadata.AnimationFrameResourceMetadata;
import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.util.Identifier;
import net.minecraft.util.Lazy;

public final class MissingSprite extends Sprite {
   private static final Identifier MISSINGNO = new Identifier("missingno");
   @Nullable
   private static NativeImageBackedTexture texture;
   private static final Lazy<NativeImage> IMAGE = new Lazy<>(() -> {
      NativeImage _snowman = new NativeImage(16, 16, false);
      int _snowmanx = -16777216;
      int _snowmanxx = -524040;

      for (int _snowmanxxx = 0; _snowmanxxx < 16; _snowmanxxx++) {
         for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
            if (_snowmanxxx < 8 ^ _snowmanxxxx < 8) {
               _snowman.setPixelColor(_snowmanxxxx, _snowmanxxx, -524040);
            } else {
               _snowman.setPixelColor(_snowmanxxxx, _snowmanxxx, -16777216);
            }
         }
      }

      _snowman.untrack();
      return _snowman;
   });
   private static final Sprite.Info INFO = new Sprite.Info(
      MISSINGNO,
      16,
      16,
      new AnimationResourceMetadata(Lists.newArrayList(new AnimationFrameResourceMetadata[]{new AnimationFrameResourceMetadata(0, -1)}), 16, 16, 1, false)
   );

   private MissingSprite(SpriteAtlasTexture _snowman, int maxLevel, int atlasWidth, int atlasHeight, int x, int y) {
      super(_snowman, INFO, maxLevel, atlasWidth, atlasHeight, x, y, IMAGE.get());
   }

   public static MissingSprite getMissingSprite(SpriteAtlasTexture _snowman, int maxLevel, int atlasWidth, int atlasHeight, int x, int y) {
      return new MissingSprite(_snowman, maxLevel, atlasWidth, atlasHeight, x, y);
   }

   public static Identifier getMissingSpriteId() {
      return MISSINGNO;
   }

   public static Sprite.Info getMissingInfo() {
      return INFO;
   }

   @Override
   public void close() {
      for (int _snowman = 1; _snowman < this.images.length; _snowman++) {
         this.images[_snowman].close();
      }
   }

   public static NativeImageBackedTexture getMissingSpriteTexture() {
      if (texture == null) {
         texture = new NativeImageBackedTexture(IMAGE.get());
         MinecraftClient.getInstance().getTextureManager().registerTexture(MISSINGNO, texture);
      }

      return texture;
   }
}
