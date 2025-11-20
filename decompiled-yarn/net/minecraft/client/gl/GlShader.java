package net.minecraft.client.gl;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import net.minecraft.client.texture.TextureUtil;
import org.apache.commons.lang3.StringUtils;

public class GlShader {
   private final GlShader.Type shaderType;
   private final String name;
   private final int shaderRef;
   private int refCount;

   private GlShader(GlShader.Type shaderType, int shaderRef, String name) {
      this.shaderType = shaderType;
      this.shaderRef = shaderRef;
      this.name = name;
   }

   public void attachTo(GlProgram _snowman) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      this.refCount++;
      GlStateManager.attachShader(_snowman.getProgramRef(), this.shaderRef);
   }

   public void release() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      this.refCount--;
      if (this.refCount <= 0) {
         GlStateManager.deleteShader(this.shaderRef);
         this.shaderType.getLoadedShaders().remove(this.name);
      }
   }

   public String getName() {
      return this.name;
   }

   public static GlShader createFromResource(GlShader.Type type, String name, InputStream sourceCode, String _snowman) throws IOException {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      String _snowmanx = TextureUtil.readAllToString(sourceCode);
      if (_snowmanx == null) {
         throw new IOException("Could not load program " + type.getName());
      } else {
         int _snowmanxx = GlStateManager.createShader(type.getGlType());
         GlStateManager.shaderSource(_snowmanxx, _snowmanx);
         GlStateManager.compileShader(_snowmanxx);
         if (GlStateManager.getShader(_snowmanxx, 35713) == 0) {
            String _snowmanxxx = StringUtils.trim(GlStateManager.getShaderInfoLog(_snowmanxx, 32768));
            throw new IOException("Couldn't compile " + type.getName() + " program (" + _snowman + ", " + name + ") : " + _snowmanxxx);
         } else {
            GlShader _snowmanxxx = new GlShader(type, _snowmanxx, name);
            type.getLoadedShaders().put(name, _snowmanxxx);
            return _snowmanxxx;
         }
      }
   }

   public static enum Type {
      VERTEX("vertex", ".vsh", 35633),
      FRAGMENT("fragment", ".fsh", 35632);

      private final String name;
      private final String fileExtension;
      private final int glType;
      private final Map<String, GlShader> loadedShaders = Maps.newHashMap();

      private Type(String var3, String var4, int var5) {
         this.name = _snowman;
         this.fileExtension = _snowman;
         this.glType = _snowman;
      }

      public String getName() {
         return this.name;
      }

      public String getFileExtension() {
         return this.fileExtension;
      }

      private int getGlType() {
         return this.glType;
      }

      public Map<String, GlShader> getLoadedShaders() {
         return this.loadedShaders;
      }
   }
}
