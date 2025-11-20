package net.minecraft.client.gl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.Matrix4f;
import org.apache.commons.io.IOUtils;

public class ShaderEffect implements AutoCloseable {
   private final Framebuffer mainTarget;
   private final ResourceManager resourceManager;
   private final String name;
   private final List<PostProcessShader> passes = Lists.newArrayList();
   private final Map<String, Framebuffer> targetsByName = Maps.newHashMap();
   private final List<Framebuffer> defaultSizedTargets = Lists.newArrayList();
   private Matrix4f projectionMatrix;
   private int width;
   private int height;
   private float time;
   private float lastTickDelta;

   public ShaderEffect(TextureManager _snowman, ResourceManager _snowman, Framebuffer framebuffer, Identifier location) throws IOException, JsonSyntaxException {
      this.resourceManager = _snowman;
      this.mainTarget = framebuffer;
      this.time = 0.0F;
      this.lastTickDelta = 0.0F;
      this.width = framebuffer.viewportWidth;
      this.height = framebuffer.viewportHeight;
      this.name = location.toString();
      this.setupProjectionMatrix();
      this.parseEffect(_snowman, location);
   }

   private void parseEffect(TextureManager _snowman, Identifier location) throws IOException, JsonSyntaxException {
      Resource _snowmanx = null;

      try {
         _snowmanx = this.resourceManager.getResource(location);
         JsonObject _snowmanxx = JsonHelper.deserialize(new InputStreamReader(_snowmanx.getInputStream(), StandardCharsets.UTF_8));
         if (JsonHelper.hasArray(_snowmanxx, "targets")) {
            JsonArray _snowmanxxx = _snowmanxx.getAsJsonArray("targets");
            int _snowmanxxxx = 0;

            for (JsonElement _snowmanxxxxx : _snowmanxxx) {
               try {
                  this.parseTarget(_snowmanxxxxx);
               } catch (Exception var17) {
                  ShaderParseException _snowmanxxxxxx = ShaderParseException.wrap(var17);
                  _snowmanxxxxxx.addFaultyElement("targets[" + _snowmanxxxx + "]");
                  throw _snowmanxxxxxx;
               }

               _snowmanxxxx++;
            }
         }

         if (JsonHelper.hasArray(_snowmanxx, "passes")) {
            JsonArray _snowmanxxx = _snowmanxx.getAsJsonArray("passes");
            int _snowmanxxxx = 0;

            for (JsonElement _snowmanxxxxx : _snowmanxxx) {
               try {
                  this.parsePass(_snowman, _snowmanxxxxx);
               } catch (Exception var16) {
                  ShaderParseException _snowmanxxxxxx = ShaderParseException.wrap(var16);
                  _snowmanxxxxxx.addFaultyElement("passes[" + _snowmanxxxx + "]");
                  throw _snowmanxxxxxx;
               }

               _snowmanxxxx++;
            }
         }
      } catch (Exception var18) {
         String _snowmanxxx;
         if (_snowmanx != null) {
            _snowmanxxx = " (" + _snowmanx.getResourcePackName() + ")";
         } else {
            _snowmanxxx = "";
         }

         ShaderParseException _snowmanxxxx = ShaderParseException.wrap(var18);
         _snowmanxxxx.addFaultyFile(location.getPath() + _snowmanxxx);
         throw _snowmanxxxx;
      } finally {
         IOUtils.closeQuietly(_snowmanx);
      }
   }

   private void parseTarget(JsonElement jsonTarget) throws ShaderParseException {
      if (JsonHelper.isString(jsonTarget)) {
         this.addTarget(jsonTarget.getAsString(), this.width, this.height);
      } else {
         JsonObject _snowman = JsonHelper.asObject(jsonTarget, "target");
         String _snowmanx = JsonHelper.getString(_snowman, "name");
         int _snowmanxx = JsonHelper.getInt(_snowman, "width", this.width);
         int _snowmanxxx = JsonHelper.getInt(_snowman, "height", this.height);
         if (this.targetsByName.containsKey(_snowmanx)) {
            throw new ShaderParseException(_snowmanx + " is already defined");
         }

         this.addTarget(_snowmanx, _snowmanxx, _snowmanxxx);
      }
   }

   private void parsePass(TextureManager _snowman, JsonElement jsonPass) throws IOException {
      JsonObject _snowmanx = JsonHelper.asObject(jsonPass, "pass");
      String _snowmanxx = JsonHelper.getString(_snowmanx, "name");
      String _snowmanxxx = JsonHelper.getString(_snowmanx, "intarget");
      String _snowmanxxxx = JsonHelper.getString(_snowmanx, "outtarget");
      Framebuffer _snowmanxxxxx = this.getTarget(_snowmanxxx);
      Framebuffer _snowmanxxxxxx = this.getTarget(_snowmanxxxx);
      if (_snowmanxxxxx == null) {
         throw new ShaderParseException("Input target '" + _snowmanxxx + "' does not exist");
      } else if (_snowmanxxxxxx == null) {
         throw new ShaderParseException("Output target '" + _snowmanxxxx + "' does not exist");
      } else {
         PostProcessShader _snowmanxxxxxxx = this.addPass(_snowmanxx, _snowmanxxxxx, _snowmanxxxxxx);
         JsonArray _snowmanxxxxxxxx = JsonHelper.getArray(_snowmanx, "auxtargets", null);
         if (_snowmanxxxxxxxx != null) {
            int _snowmanxxxxxxxxx = 0;

            for (JsonElement _snowmanxxxxxxxxxx : _snowmanxxxxxxxx) {
               try {
                  JsonObject _snowmanxxxxxxxxxxx = JsonHelper.asObject(_snowmanxxxxxxxxxx, "auxtarget");
                  String _snowmanxxxxxxxxxxxx = JsonHelper.getString(_snowmanxxxxxxxxxxx, "name");
                  String _snowmanxxxxxxxxxxxxx = JsonHelper.getString(_snowmanxxxxxxxxxxx, "id");
                  boolean _snowmanxxxxxxxxxxxxxx;
                  String _snowmanxxxxxxxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxxxx.endsWith(":depth")) {
                     _snowmanxxxxxxxxxxxxxx = true;
                     _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.substring(0, _snowmanxxxxxxxxxxxxx.lastIndexOf(58));
                  } else {
                     _snowmanxxxxxxxxxxxxxx = false;
                     _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx;
                  }

                  Framebuffer _snowmanxxxxxxxxxxxxxxxx = this.getTarget(_snowmanxxxxxxxxxxxxxxx);
                  if (_snowmanxxxxxxxxxxxxxxxx == null) {
                     if (_snowmanxxxxxxxxxxxxxx) {
                        throw new ShaderParseException("Render target '" + _snowmanxxxxxxxxxxxxxxx + "' can't be used as depth buffer");
                     }

                     Identifier _snowmanxxxxxxxxxxxxxxxxx = new Identifier("textures/effect/" + _snowmanxxxxxxxxxxxxxxx + ".png");
                     Resource _snowmanxxxxxxxxxxxxxxxxxx = null;

                     try {
                        _snowmanxxxxxxxxxxxxxxxxxx = this.resourceManager.getResource(_snowmanxxxxxxxxxxxxxxxxx);
                     } catch (FileNotFoundException var31) {
                        throw new ShaderParseException("Render target or texture '" + _snowmanxxxxxxxxxxxxxxx + "' does not exist");
                     } finally {
                        IOUtils.closeQuietly(_snowmanxxxxxxxxxxxxxxxxxx);
                     }

                     _snowman.bindTexture(_snowmanxxxxxxxxxxxxxxxxx);
                     AbstractTexture var22 = _snowman.getTexture(_snowmanxxxxxxxxxxxxxxxxx);
                     int var23 = JsonHelper.getInt(_snowmanxxxxxxxxxxx, "width");
                     int var24 = JsonHelper.getInt(_snowmanxxxxxxxxxxx, "height");
                     boolean _snowmanxxxxxxxxxxxxxxxxxxx = JsonHelper.getBoolean(_snowmanxxxxxxxxxxx, "bilinear");
                     if (_snowmanxxxxxxxxxxxxxxxxxxx) {
                        RenderSystem.texParameter(3553, 10241, 9729);
                        RenderSystem.texParameter(3553, 10240, 9729);
                     } else {
                        RenderSystem.texParameter(3553, 10241, 9728);
                        RenderSystem.texParameter(3553, 10240, 9728);
                     }

                     _snowmanxxxxxxx.addAuxTarget(_snowmanxxxxxxxxxxxx, var22::getGlId, var23, var24);
                  } else if (_snowmanxxxxxxxxxxxxxx) {
                     _snowmanxxxxxxx.addAuxTarget(
                        _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx::getDepthAttachment, _snowmanxxxxxxxxxxxxxxxx.textureWidth, _snowmanxxxxxxxxxxxxxxxx.textureHeight
                     );
                  } else {
                     _snowmanxxxxxxx.addAuxTarget(
                        _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx::getColorAttachment, _snowmanxxxxxxxxxxxxxxxx.textureWidth, _snowmanxxxxxxxxxxxxxxxx.textureHeight
                     );
                  }
               } catch (Exception var33) {
                  ShaderParseException _snowmanxxxxxxxxxxxxxxxx = ShaderParseException.wrap(var33);
                  _snowmanxxxxxxxxxxxxxxxx.addFaultyElement("auxtargets[" + _snowmanxxxxxxxxx + "]");
                  throw _snowmanxxxxxxxxxxxxxxxx;
               }

               _snowmanxxxxxxxxx++;
            }
         }

         JsonArray _snowmanxxxxxxxxx = JsonHelper.getArray(_snowmanx, "uniforms", null);
         if (_snowmanxxxxxxxxx != null) {
            int _snowmanxxxxxxxxxx = 0;

            for (JsonElement _snowmanxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxx) {
               try {
                  this.parseUniform(_snowmanxxxxxxxxxxxxxxxx);
               } catch (Exception var30) {
                  ShaderParseException _snowmanxxxxxxxxxxxxxxxxx = ShaderParseException.wrap(var30);
                  _snowmanxxxxxxxxxxxxxxxxx.addFaultyElement("uniforms[" + _snowmanxxxxxxxxxx + "]");
                  throw _snowmanxxxxxxxxxxxxxxxxx;
               }

               _snowmanxxxxxxxxxx++;
            }
         }
      }
   }

   private void parseUniform(JsonElement jsonUniform) throws ShaderParseException {
      JsonObject _snowman = JsonHelper.asObject(jsonUniform, "uniform");
      String _snowmanx = JsonHelper.getString(_snowman, "name");
      GlUniform _snowmanxx = this.passes.get(this.passes.size() - 1).getProgram().getUniformByName(_snowmanx);
      if (_snowmanxx == null) {
         throw new ShaderParseException("Uniform '" + _snowmanx + "' does not exist");
      } else {
         float[] _snowmanxxx = new float[4];
         int _snowmanxxxx = 0;

         for (JsonElement _snowmanxxxxx : JsonHelper.getArray(_snowman, "values")) {
            try {
               _snowmanxxx[_snowmanxxxx] = JsonHelper.asFloat(_snowmanxxxxx, "value");
            } catch (Exception var12) {
               ShaderParseException _snowmanxxxxxx = ShaderParseException.wrap(var12);
               _snowmanxxxxxx.addFaultyElement("values[" + _snowmanxxxx + "]");
               throw _snowmanxxxxxx;
            }

            _snowmanxxxx++;
         }

         switch (_snowmanxxxx) {
            case 0:
            default:
               break;
            case 1:
               _snowmanxx.set(_snowmanxxx[0]);
               break;
            case 2:
               _snowmanxx.set(_snowmanxxx[0], _snowmanxxx[1]);
               break;
            case 3:
               _snowmanxx.set(_snowmanxxx[0], _snowmanxxx[1], _snowmanxxx[2]);
               break;
            case 4:
               _snowmanxx.set(_snowmanxxx[0], _snowmanxxx[1], _snowmanxxx[2], _snowmanxxx[3]);
         }
      }
   }

   public Framebuffer getSecondaryTarget(String name) {
      return this.targetsByName.get(name);
   }

   public void addTarget(String name, int width, int height) {
      Framebuffer _snowman = new Framebuffer(width, height, true, MinecraftClient.IS_SYSTEM_MAC);
      _snowman.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
      this.targetsByName.put(name, _snowman);
      if (width == this.width && height == this.height) {
         this.defaultSizedTargets.add(_snowman);
      }
   }

   @Override
   public void close() {
      for (Framebuffer _snowman : this.targetsByName.values()) {
         _snowman.delete();
      }

      for (PostProcessShader _snowman : this.passes) {
         _snowman.close();
      }

      this.passes.clear();
   }

   public PostProcessShader addPass(String programName, Framebuffer source, Framebuffer dest) throws IOException {
      PostProcessShader _snowman = new PostProcessShader(this.resourceManager, programName, source, dest);
      this.passes.add(this.passes.size(), _snowman);
      return _snowman;
   }

   private void setupProjectionMatrix() {
      this.projectionMatrix = Matrix4f.projectionMatrix((float)this.mainTarget.textureWidth, (float)this.mainTarget.textureHeight, 0.1F, 1000.0F);
   }

   public void setupDimensions(int targetsWidth, int targetsHeight) {
      this.width = this.mainTarget.textureWidth;
      this.height = this.mainTarget.textureHeight;
      this.setupProjectionMatrix();

      for (PostProcessShader _snowman : this.passes) {
         _snowman.setProjectionMatrix(this.projectionMatrix);
      }

      for (Framebuffer _snowman : this.defaultSizedTargets) {
         _snowman.resize(targetsWidth, targetsHeight, MinecraftClient.IS_SYSTEM_MAC);
      }
   }

   public void render(float tickDelta) {
      if (tickDelta < this.lastTickDelta) {
         this.time = this.time + (1.0F - this.lastTickDelta);
         this.time += tickDelta;
      } else {
         this.time = this.time + (tickDelta - this.lastTickDelta);
      }

      this.lastTickDelta = tickDelta;

      while (this.time > 20.0F) {
         this.time -= 20.0F;
      }

      for (PostProcessShader _snowman : this.passes) {
         _snowman.render(this.time / 20.0F);
      }
   }

   public final String getName() {
      return this.name;
   }

   private Framebuffer getTarget(String name) {
      if (name == null) {
         return null;
      } else {
         return name.equals("minecraft:main") ? this.mainTarget : this.targetsByName.get(name);
      }
   }
}
