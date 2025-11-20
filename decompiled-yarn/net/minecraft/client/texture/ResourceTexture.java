package net.minecraft.client.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.Closeable;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.resource.metadata.TextureResourceMetadata;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceTexture extends AbstractTexture {
   private static final Logger LOGGER = LogManager.getLogger();
   protected final Identifier location;

   public ResourceTexture(Identifier location) {
      this.location = location;
   }

   @Override
   public void load(ResourceManager manager) throws IOException {
      ResourceTexture.TextureData _snowman = this.loadTextureData(manager);
      _snowman.checkException();
      TextureResourceMetadata _snowmanx = _snowman.getMetadata();
      boolean _snowmanxx;
      boolean _snowmanxxx;
      if (_snowmanx != null) {
         _snowmanxx = _snowmanx.shouldBlur();
         _snowmanxxx = _snowmanx.shouldClamp();
      } else {
         _snowmanxx = false;
         _snowmanxxx = false;
      }

      NativeImage _snowmanxxxx = _snowman.getImage();
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> this.upload(_snowman, _snowman, _snowman));
      } else {
         this.upload(_snowmanxxxx, _snowmanxx, _snowmanxxx);
      }
   }

   private void upload(NativeImage _snowman, boolean blur, boolean clamp) {
      TextureUtil.allocate(this.getGlId(), 0, _snowman.getWidth(), _snowman.getHeight());
      _snowman.upload(0, 0, 0, 0, 0, _snowman.getWidth(), _snowman.getHeight(), blur, clamp, false, true);
   }

   protected ResourceTexture.TextureData loadTextureData(ResourceManager resourceManager) {
      return ResourceTexture.TextureData.load(resourceManager, this.location);
   }

   public static class TextureData implements Closeable {
      @Nullable
      private final TextureResourceMetadata metadata;
      @Nullable
      private final NativeImage image;
      @Nullable
      private final IOException exception;

      public TextureData(IOException exception) {
         this.exception = exception;
         this.metadata = null;
         this.image = null;
      }

      public TextureData(@Nullable TextureResourceMetadata metadata, NativeImage image) {
         this.exception = null;
         this.metadata = metadata;
         this.image = image;
      }

      public static ResourceTexture.TextureData load(ResourceManager _snowman, Identifier _snowman) {
         try (Resource _snowmanxx = _snowman.getResource(_snowman)) {
            NativeImage _snowmanxxx = NativeImage.read(_snowmanxx.getInputStream());
            TextureResourceMetadata _snowmanxxxx = null;

            try {
               _snowmanxxxx = _snowmanxx.getMetadata(TextureResourceMetadata.READER);
            } catch (RuntimeException var17) {
               ResourceTexture.LOGGER.warn("Failed reading metadata of: {}", _snowman, var17);
            }

            return new ResourceTexture.TextureData(_snowmanxxxx, _snowmanxxx);
         } catch (IOException var20) {
            return new ResourceTexture.TextureData(var20);
         }
      }

      @Nullable
      public TextureResourceMetadata getMetadata() {
         return this.metadata;
      }

      public NativeImage getImage() throws IOException {
         if (this.exception != null) {
            throw this.exception;
         } else {
            return this.image;
         }
      }

      @Override
      public void close() {
         if (this.image != null) {
            this.image.close();
         }
      }

      public void checkException() throws IOException {
         if (this.exception != null) {
            throw this.exception;
         }
      }
   }
}
