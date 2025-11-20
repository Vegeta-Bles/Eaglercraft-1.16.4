package net.minecraft.client.util;

import java.io.IOException;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class RawTextureDataLoader {
   @Deprecated
   public static int[] loadRawTextureData(ResourceManager _snowman, Identifier _snowman) throws IOException {
      int[] var6;
      try (
         Resource _snowmanxx = _snowman.getResource(_snowman);
         NativeImage _snowmanxxx = NativeImage.read(_snowmanxx.getInputStream());
      ) {
         var6 = _snowmanxxx.makePixelArray();
      }

      return var6;
   }
}
