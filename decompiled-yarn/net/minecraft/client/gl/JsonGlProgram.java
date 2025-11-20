package net.minecraft.client.gl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonGlProgram implements GlProgram, AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Uniform dummyUniform = new Uniform();
   private static JsonGlProgram activeProgram;
   private static int activeProgramRef = -1;
   private final Map<String, IntSupplier> samplerBinds = Maps.newHashMap();
   private final List<String> samplerNames = Lists.newArrayList();
   private final List<Integer> samplerShaderLocs = Lists.newArrayList();
   private final List<GlUniform> uniformData = Lists.newArrayList();
   private final List<Integer> uniformLocs = Lists.newArrayList();
   private final Map<String, GlUniform> uniformByName = Maps.newHashMap();
   private final int programRef;
   private final String name;
   private boolean uniformStateDirty;
   private final GlBlendState blendState;
   private final List<Integer> attribLocs;
   private final List<String> attribNames;
   private final GlShader vertexShader;
   private final GlShader fragmentShader;

   public JsonGlProgram(ResourceManager resource, String name) throws IOException {
      Identifier _snowman = new Identifier("shaders/program/" + name + ".json");
      this.name = name;
      Resource _snowmanx = null;

      try {
         _snowmanx = resource.getResource(_snowman);
         JsonObject _snowmanxx = JsonHelper.deserialize(new InputStreamReader(_snowmanx.getInputStream(), StandardCharsets.UTF_8));
         String _snowmanxxx = JsonHelper.getString(_snowmanxx, "vertex");
         String _snowmanxxxx = JsonHelper.getString(_snowmanxx, "fragment");
         JsonArray _snowmanxxxxx = JsonHelper.getArray(_snowmanxx, "samplers", null);
         if (_snowmanxxxxx != null) {
            int _snowmanxxxxxx = 0;

            for (JsonElement _snowmanxxxxxxx : _snowmanxxxxx) {
               try {
                  this.addSampler(_snowmanxxxxxxx);
               } catch (Exception var24) {
                  ShaderParseException _snowmanxxxxxxxx = ShaderParseException.wrap(var24);
                  _snowmanxxxxxxxx.addFaultyElement("samplers[" + _snowmanxxxxxx + "]");
                  throw _snowmanxxxxxxxx;
               }

               _snowmanxxxxxx++;
            }
         }

         JsonArray _snowmanxxxxxx = JsonHelper.getArray(_snowmanxx, "attributes", null);
         if (_snowmanxxxxxx != null) {
            int _snowmanxxxxxxx = 0;
            this.attribLocs = Lists.newArrayListWithCapacity(_snowmanxxxxxx.size());
            this.attribNames = Lists.newArrayListWithCapacity(_snowmanxxxxxx.size());

            for (JsonElement _snowmanxxxxxxxx : _snowmanxxxxxx) {
               try {
                  this.attribNames.add(JsonHelper.asString(_snowmanxxxxxxxx, "attribute"));
               } catch (Exception var23) {
                  ShaderParseException _snowmanxxxxxxxxx = ShaderParseException.wrap(var23);
                  _snowmanxxxxxxxxx.addFaultyElement("attributes[" + _snowmanxxxxxxx + "]");
                  throw _snowmanxxxxxxxxx;
               }

               _snowmanxxxxxxx++;
            }
         } else {
            this.attribLocs = null;
            this.attribNames = null;
         }

         JsonArray _snowmanxxxxxxx = JsonHelper.getArray(_snowmanxx, "uniforms", null);
         if (_snowmanxxxxxxx != null) {
            int _snowmanxxxxxxxx = 0;

            for (JsonElement _snowmanxxxxxxxxx : _snowmanxxxxxxx) {
               try {
                  this.addUniform(_snowmanxxxxxxxxx);
               } catch (Exception var22) {
                  ShaderParseException _snowmanxxxxxxxxxx = ShaderParseException.wrap(var22);
                  _snowmanxxxxxxxxxx.addFaultyElement("uniforms[" + _snowmanxxxxxxxx + "]");
                  throw _snowmanxxxxxxxxxx;
               }

               _snowmanxxxxxxxx++;
            }
         }

         this.blendState = deserializeBlendState(JsonHelper.getObject(_snowmanxx, "blend", null));
         this.vertexShader = getShader(resource, GlShader.Type.VERTEX, _snowmanxxx);
         this.fragmentShader = getShader(resource, GlShader.Type.FRAGMENT, _snowmanxxxx);
         this.programRef = GlProgramManager.createProgram();
         GlProgramManager.linkProgram(this);
         this.finalizeUniformsAndSamplers();
         if (this.attribNames != null) {
            for (String _snowmanxxxxxxxx : this.attribNames) {
               int _snowmanxxxxxxxxx = GlUniform.getAttribLocation(this.programRef, _snowmanxxxxxxxx);
               this.attribLocs.add(_snowmanxxxxxxxxx);
            }
         }
      } catch (Exception var25) {
         String _snowmanxxxxxxxx;
         if (_snowmanx != null) {
            _snowmanxxxxxxxx = " (" + _snowmanx.getResourcePackName() + ")";
         } else {
            _snowmanxxxxxxxx = "";
         }

         ShaderParseException _snowmanxxxxxxxxx = ShaderParseException.wrap(var25);
         _snowmanxxxxxxxxx.addFaultyFile(_snowman.getPath() + _snowmanxxxxxxxx);
         throw _snowmanxxxxxxxxx;
      } finally {
         IOUtils.closeQuietly(_snowmanx);
      }

      this.markUniformsDirty();
   }

   public static GlShader getShader(ResourceManager resourceManager, GlShader.Type type, String name) throws IOException {
      GlShader _snowman = type.getLoadedShaders().get(name);
      if (_snowman == null) {
         Identifier _snowmanx = new Identifier("shaders/program/" + name + type.getFileExtension());
         Resource _snowmanxx = resourceManager.getResource(_snowmanx);

         try {
            _snowman = GlShader.createFromResource(type, name, _snowmanxx.getInputStream(), _snowmanxx.getResourcePackName());
         } finally {
            IOUtils.closeQuietly(_snowmanxx);
         }
      }

      return _snowman;
   }

   public static GlBlendState deserializeBlendState(JsonObject json) {
      if (json == null) {
         return new GlBlendState();
      } else {
         int _snowman = 32774;
         int _snowmanx = 1;
         int _snowmanxx = 0;
         int _snowmanxxx = 1;
         int _snowmanxxxx = 0;
         boolean _snowmanxxxxx = true;
         boolean _snowmanxxxxxx = false;
         if (JsonHelper.hasString(json, "func")) {
            _snowman = GlBlendState.getFuncFromString(json.get("func").getAsString());
            if (_snowman != 32774) {
               _snowmanxxxxx = false;
            }
         }

         if (JsonHelper.hasString(json, "srcrgb")) {
            _snowmanx = GlBlendState.getComponentFromString(json.get("srcrgb").getAsString());
            if (_snowmanx != 1) {
               _snowmanxxxxx = false;
            }
         }

         if (JsonHelper.hasString(json, "dstrgb")) {
            _snowmanxx = GlBlendState.getComponentFromString(json.get("dstrgb").getAsString());
            if (_snowmanxx != 0) {
               _snowmanxxxxx = false;
            }
         }

         if (JsonHelper.hasString(json, "srcalpha")) {
            _snowmanxxx = GlBlendState.getComponentFromString(json.get("srcalpha").getAsString());
            if (_snowmanxxx != 1) {
               _snowmanxxxxx = false;
            }

            _snowmanxxxxxx = true;
         }

         if (JsonHelper.hasString(json, "dstalpha")) {
            _snowmanxxxx = GlBlendState.getComponentFromString(json.get("dstalpha").getAsString());
            if (_snowmanxxxx != 0) {
               _snowmanxxxxx = false;
            }

            _snowmanxxxxxx = true;
         }

         if (_snowmanxxxxx) {
            return new GlBlendState();
         } else {
            return _snowmanxxxxxx ? new GlBlendState(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowman) : new GlBlendState(_snowmanx, _snowmanxx, _snowman);
         }
      }
   }

   @Override
   public void close() {
      for (GlUniform _snowman : this.uniformData) {
         _snowman.close();
      }

      GlProgramManager.deleteProgram(this);
   }

   public void disable() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GlProgramManager.useProgram(0);
      activeProgramRef = -1;
      activeProgram = null;

      for (int _snowman = 0; _snowman < this.samplerShaderLocs.size(); _snowman++) {
         if (this.samplerBinds.get(this.samplerNames.get(_snowman)) != null) {
            GlStateManager.activeTexture(33984 + _snowman);
            GlStateManager.disableTexture();
            GlStateManager.bindTexture(0);
         }
      }
   }

   public void enable() {
      RenderSystem.assertThread(RenderSystem::isOnGameThread);
      this.uniformStateDirty = false;
      activeProgram = this;
      this.blendState.enable();
      if (this.programRef != activeProgramRef) {
         GlProgramManager.useProgram(this.programRef);
         activeProgramRef = this.programRef;
      }

      for (int _snowman = 0; _snowman < this.samplerShaderLocs.size(); _snowman++) {
         String _snowmanx = this.samplerNames.get(_snowman);
         IntSupplier _snowmanxx = this.samplerBinds.get(_snowmanx);
         if (_snowmanxx != null) {
            RenderSystem.activeTexture(33984 + _snowman);
            RenderSystem.enableTexture();
            int _snowmanxxx = _snowmanxx.getAsInt();
            if (_snowmanxxx != -1) {
               RenderSystem.bindTexture(_snowmanxxx);
               GlUniform.uniform1(this.samplerShaderLocs.get(_snowman), _snowman);
            }
         }
      }

      for (GlUniform _snowmanx : this.uniformData) {
         _snowmanx.upload();
      }
   }

   @Override
   public void markUniformsDirty() {
      this.uniformStateDirty = true;
   }

   @Nullable
   public GlUniform getUniformByName(String name) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return this.uniformByName.get(name);
   }

   public Uniform getUniformByNameOrDummy(String name) {
      RenderSystem.assertThread(RenderSystem::isOnGameThread);
      GlUniform _snowman = this.getUniformByName(name);
      return (Uniform)(_snowman == null ? dummyUniform : _snowman);
   }

   private void finalizeUniformsAndSamplers() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      IntList _snowman = new IntArrayList();

      for (int _snowmanx = 0; _snowmanx < this.samplerNames.size(); _snowmanx++) {
         String _snowmanxx = this.samplerNames.get(_snowmanx);
         int _snowmanxxx = GlUniform.getUniformLocation(this.programRef, _snowmanxx);
         if (_snowmanxxx == -1) {
            LOGGER.warn("Shader {} could not find sampler named {} in the specified shader program.", this.name, _snowmanxx);
            this.samplerBinds.remove(_snowmanxx);
            _snowman.add(_snowmanx);
         } else {
            this.samplerShaderLocs.add(_snowmanxxx);
         }
      }

      for (int _snowmanxx = _snowman.size() - 1; _snowmanxx >= 0; _snowmanxx--) {
         this.samplerNames.remove(_snowman.getInt(_snowmanxx));
      }

      for (GlUniform _snowmanxx : this.uniformData) {
         String _snowmanxxx = _snowmanxx.getName();
         int _snowmanxxxx = GlUniform.getUniformLocation(this.programRef, _snowmanxxx);
         if (_snowmanxxxx == -1) {
            LOGGER.warn("Could not find uniform named {} in the specified shader program.", _snowmanxxx);
         } else {
            this.uniformLocs.add(_snowmanxxxx);
            _snowmanxx.setLoc(_snowmanxxxx);
            this.uniformByName.put(_snowmanxxx, _snowmanxx);
         }
      }
   }

   private void addSampler(JsonElement _snowman) {
      JsonObject _snowmanx = JsonHelper.asObject(_snowman, "sampler");
      String _snowmanxx = JsonHelper.getString(_snowmanx, "name");
      if (!JsonHelper.hasString(_snowmanx, "file")) {
         this.samplerBinds.put(_snowmanxx, null);
         this.samplerNames.add(_snowmanxx);
      } else {
         this.samplerNames.add(_snowmanxx);
      }
   }

   public void bindSampler(String samplerName, IntSupplier _snowman) {
      if (this.samplerBinds.containsKey(samplerName)) {
         this.samplerBinds.remove(samplerName);
      }

      this.samplerBinds.put(samplerName, _snowman);
      this.markUniformsDirty();
   }

   private void addUniform(JsonElement _snowman) throws ShaderParseException {
      JsonObject _snowmanx = JsonHelper.asObject(_snowman, "uniform");
      String _snowmanxx = JsonHelper.getString(_snowmanx, "name");
      int _snowmanxxx = GlUniform.getTypeIndex(JsonHelper.getString(_snowmanx, "type"));
      int _snowmanxxxx = JsonHelper.getInt(_snowmanx, "count");
      float[] _snowmanxxxxx = new float[Math.max(_snowmanxxxx, 16)];
      JsonArray _snowmanxxxxxx = JsonHelper.getArray(_snowmanx, "values");
      if (_snowmanxxxxxx.size() != _snowmanxxxx && _snowmanxxxxxx.size() > 1) {
         throw new ShaderParseException("Invalid amount of values specified (expected " + _snowmanxxxx + ", found " + _snowmanxxxxxx.size() + ")");
      } else {
         int _snowmanxxxxxxx = 0;

         for (JsonElement _snowmanxxxxxxxx : _snowmanxxxxxx) {
            try {
               _snowmanxxxxx[_snowmanxxxxxxx] = JsonHelper.asFloat(_snowmanxxxxxxxx, "value");
            } catch (Exception var13) {
               ShaderParseException _snowmanxxxxxxxxx = ShaderParseException.wrap(var13);
               _snowmanxxxxxxxxx.addFaultyElement("values[" + _snowmanxxxxxxx + "]");
               throw _snowmanxxxxxxxxx;
            }

            _snowmanxxxxxxx++;
         }

         if (_snowmanxxxx > 1 && _snowmanxxxxxx.size() == 1) {
            while (_snowmanxxxxxxx < _snowmanxxxx) {
               _snowmanxxxxx[_snowmanxxxxxxx] = _snowmanxxxxx[0];
               _snowmanxxxxxxx++;
            }
         }

         int _snowmanxxxxxxxx = _snowmanxxxx > 1 && _snowmanxxxx <= 4 && _snowmanxxx < 8 ? _snowmanxxxx - 1 : 0;
         GlUniform _snowmanxxxxxxxxx = new GlUniform(_snowmanxx, _snowmanxxx + _snowmanxxxxxxxx, _snowmanxxxx, this);
         if (_snowmanxxx <= 3) {
            _snowmanxxxxxxxxx.set((int)_snowmanxxxxx[0], (int)_snowmanxxxxx[1], (int)_snowmanxxxxx[2], (int)_snowmanxxxxx[3]);
         } else if (_snowmanxxx <= 7) {
            _snowmanxxxxxxxxx.setForDataType(_snowmanxxxxx[0], _snowmanxxxxx[1], _snowmanxxxxx[2], _snowmanxxxxx[3]);
         } else {
            _snowmanxxxxxxxxx.set(_snowmanxxxxx);
         }

         this.uniformData.add(_snowmanxxxxxxxxx);
      }
   }

   @Override
   public GlShader getVertexShader() {
      return this.vertexShader;
   }

   @Override
   public GlShader getFragmentShader() {
      return this.fragmentShader;
   }

   @Override
   public int getProgramRef() {
      return this.programRef;
   }
}
