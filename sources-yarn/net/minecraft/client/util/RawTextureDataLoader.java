package net.minecraft.client.util;

import java.io.IOException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RawTextureDataLoader {
   @Deprecated
   public static int[] loadRawTextureData(ResourceManager arg, Identifier arg2) throws IOException {
      int[] var6;
      try (
         Resource lv = arg.getResource(arg2);
         NativeImage lv2 = NativeImage.read(lv.getInputStream());
      ) {
         var6 = lv2.makePixelArray();
      }

      return var6;
   }
}
