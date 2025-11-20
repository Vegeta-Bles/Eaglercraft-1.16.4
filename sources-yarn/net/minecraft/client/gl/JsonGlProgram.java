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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
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
      Identifier lv = new Identifier("shaders/program/" + name + ".json");
      this.name = name;
      Resource lv2 = null;

      try {
         lv2 = resource.getResource(lv);
         JsonObject jsonObject = JsonHelper.deserialize(new InputStreamReader(lv2.getInputStream(), StandardCharsets.UTF_8));
         String string2 = JsonHelper.getString(jsonObject, "vertex");
         String string3 = JsonHelper.getString(jsonObject, "fragment");
         JsonArray jsonArray = JsonHelper.getArray(jsonObject, "samplers", null);
         if (jsonArray != null) {
            int i = 0;

            for (JsonElement jsonElement : jsonArray) {
               try {
                  this.addSampler(jsonElement);
               } catch (Exception var24) {
                  ShaderParseException lv3 = ShaderParseException.wrap(var24);
                  lv3.addFaultyElement("samplers[" + i + "]");
                  throw lv3;
               }

               i++;
            }
         }

         JsonArray jsonArray2 = JsonHelper.getArray(jsonObject, "attributes", null);
         if (jsonArray2 != null) {
            int j = 0;
            this.attribLocs = Lists.newArrayListWithCapacity(jsonArray2.size());
            this.attribNames = Lists.newArrayListWithCapacity(jsonArray2.size());

            for (JsonElement jsonElement2 : jsonArray2) {
               try {
                  this.attribNames.add(JsonHelper.asString(jsonElement2, "attribute"));
               } catch (Exception var23) {
                  ShaderParseException lv4 = ShaderParseException.wrap(var23);
                  lv4.addFaultyElement("attributes[" + j + "]");
                  throw lv4;
               }

               j++;
            }
         } else {
            this.attribLocs = null;
            this.attribNames = null;
         }

         JsonArray jsonArray3 = JsonHelper.getArray(jsonObject, "uniforms", null);
         if (jsonArray3 != null) {
            int k = 0;

            for (JsonElement jsonElement3 : jsonArray3) {
               try {
                  this.addUniform(jsonElement3);
               } catch (Exception var22) {
                  ShaderParseException lv5 = ShaderParseException.wrap(var22);
                  lv5.addFaultyElement("uniforms[" + k + "]");
                  throw lv5;
               }

               k++;
            }
         }

         this.blendState = deserializeBlendState(JsonHelper.getObject(jsonObject, "blend", null));
         this.vertexShader = getShader(resource, GlShader.Type.VERTEX, string2);
         this.fragmentShader = getShader(resource, GlShader.Type.FRAGMENT, string3);
         this.programRef = GlProgramManager.createProgram();
         GlProgramManager.linkProgram(this);
         this.finalizeUniformsAndSamplers();
         if (this.attribNames != null) {
            for (String string4 : this.attribNames) {
               int l = GlUniform.getAttribLocation(this.programRef, string4);
               this.attribLocs.add(l);
            }
         }
      } catch (Exception var25) {
         String string5;
         if (lv2 != null) {
            string5 = " (" + lv2.getResourcePackName() + ")";
         } else {
            string5 = "";
         }

         ShaderParseException lv6 = ShaderParseException.wrap(var25);
         lv6.addFaultyFile(lv.getPath() + string5);
         throw lv6;
      } finally {
         IOUtils.closeQuietly(lv2);
      }

      this.markUniformsDirty();
   }

   public static GlShader getShader(ResourceManager resourceManager, GlShader.Type type, String name) throws IOException {
      GlShader lv = type.getLoadedShaders().get(name);
      if (lv == null) {
         Identifier lv2 = new Identifier("shaders/program/" + name + type.getFileExtension());
         Resource lv3 = resourceManager.getResource(lv2);

         try {
            lv = GlShader.createFromResource(type, name, lv3.getInputStream(), lv3.getResourcePackName());
         } finally {
            IOUtils.closeQuietly(lv3);
         }
      }

      return lv;
   }

   public static GlBlendState deserializeBlendState(JsonObject json) {
      if (json == null) {
         return new GlBlendState();
      } else {
         int i = 32774;
         int j = 1;
         int k = 0;
         int l = 1;
         int m = 0;
         boolean bl = true;
         boolean bl2 = false;
         if (JsonHelper.hasString(json, "func")) {
            i = GlBlendState.getFuncFromString(json.get("func").getAsString());
            if (i != 32774) {
               bl = false;
            }
         }

         if (JsonHelper.hasString(json, "srcrgb")) {
            j = GlBlendState.getComponentFromString(json.get("srcrgb").getAsString());
            if (j != 1) {
               bl = false;
            }
         }

         if (JsonHelper.hasString(json, "dstrgb")) {
            k = GlBlendState.getComponentFromString(json.get("dstrgb").getAsString());
            if (k != 0) {
               bl = false;
            }
         }

         if (JsonHelper.hasString(json, "srcalpha")) {
            l = GlBlendState.getComponentFromString(json.get("srcalpha").getAsString());
            if (l != 1) {
               bl = false;
            }

            bl2 = true;
         }

         if (JsonHelper.hasString(json, "dstalpha")) {
            m = GlBlendState.getComponentFromString(json.get("dstalpha").getAsString());
            if (m != 0) {
               bl = false;
            }

            bl2 = true;
         }

         if (bl) {
            return new GlBlendState();
         } else {
            return bl2 ? new GlBlendState(j, k, l, m, i) : new GlBlendState(j, k, i);
         }
      }
   }

   @Override
   public void close() {
      for (GlUniform lv : this.uniformData) {
         lv.close();
      }

      GlProgramManager.deleteProgram(this);
   }

   public void disable() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GlProgramManager.useProgram(0);
      activeProgramRef = -1;
      activeProgram = null;

      for (int i = 0; i < this.samplerShaderLocs.size(); i++) {
         if (this.samplerBinds.get(this.samplerNames.get(i)) != null) {
            GlStateManager.activeTexture(33984 + i);
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

      for (int i = 0; i < this.samplerShaderLocs.size(); i++) {
         String string = this.samplerNames.get(i);
         IntSupplier intSupplier = this.samplerBinds.get(string);
         if (intSupplier != null) {
            RenderSystem.activeTexture(33984 + i);
            RenderSystem.enableTexture();
            int j = intSupplier.getAsInt();
            if (j != -1) {
               RenderSystem.bindTexture(j);
               GlUniform.uniform1(this.samplerShaderLocs.get(i), i);
            }
         }
      }

      for (GlUniform lv : this.uniformData) {
         lv.upload();
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
      GlUniform lv = this.getUniformByName(name);
      return (Uniform)(lv == null ? dummyUniform : lv);
   }

   private void finalizeUniformsAndSamplers() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      IntList intList = new IntArrayList();

      for (int i = 0; i < this.samplerNames.size(); i++) {
         String string = this.samplerNames.get(i);
         int j = GlUniform.getUniformLocation(this.programRef, string);
         if (j == -1) {
            LOGGER.warn("Shader {} could not find sampler named {} in the specified shader program.", this.name, string);
            this.samplerBinds.remove(string);
            intList.add(i);
         } else {
            this.samplerShaderLocs.add(j);
         }
      }

      for (int k = intList.size() - 1; k >= 0; k--) {
         this.samplerNames.remove(intList.getInt(k));
      }

      for (GlUniform lv : this.uniformData) {
         String string2 = lv.getName();
         int l = GlUniform.getUniformLocation(this.programRef, string2);
         if (l == -1) {
            LOGGER.warn("Could not find uniform named {} in the specified shader program.", string2);
         } else {
            this.uniformLocs.add(l);
            lv.setLoc(l);
            this.uniformByName.put(string2, lv);
         }
      }
   }

   private void addSampler(JsonElement jsonElement) {
      JsonObject jsonObject = JsonHelper.asObject(jsonElement, "sampler");
      String string = JsonHelper.getString(jsonObject, "name");
      if (!JsonHelper.hasString(jsonObject, "file")) {
         this.samplerBinds.put(string, null);
         this.samplerNames.add(string);
      } else {
         this.samplerNames.add(string);
      }
   }

   public void bindSampler(String samplerName, IntSupplier intSupplier) {
      if (this.samplerBinds.containsKey(samplerName)) {
         this.samplerBinds.remove(samplerName);
      }

      this.samplerBinds.put(samplerName, intSupplier);
      this.markUniformsDirty();
   }

   private void addUniform(JsonElement jsonElement) throws ShaderParseException {
      JsonObject jsonObject = JsonHelper.asObject(jsonElement, "uniform");
      String string = JsonHelper.getString(jsonObject, "name");
      int i = GlUniform.getTypeIndex(JsonHelper.getString(jsonObject, "type"));
      int j = JsonHelper.getInt(jsonObject, "count");
      float[] fs = new float[Math.max(j, 16)];
      JsonArray jsonArray = JsonHelper.getArray(jsonObject, "values");
      if (jsonArray.size() != j && jsonArray.size() > 1) {
         throw new ShaderParseException("Invalid amount of values specified (expected " + j + ", found " + jsonArray.size() + ")");
      } else {
         int k = 0;

         for (JsonElement jsonElement2 : jsonArray) {
            try {
               fs[k] = JsonHelper.asFloat(jsonElement2, "value");
            } catch (Exception var13) {
               ShaderParseException lv = ShaderParseException.wrap(var13);
               lv.addFaultyElement("values[" + k + "]");
               throw lv;
            }

            k++;
         }

         if (j > 1 && jsonArray.size() == 1) {
            while (k < j) {
               fs[k] = fs[0];
               k++;
            }
         }

         int l = j > 1 && j <= 4 && i < 8 ? j - 1 : 0;
         GlUniform lv2 = new GlUniform(string, i + l, j, this);
         if (i <= 3) {
            lv2.set((int)fs[0], (int)fs[1], (int)fs[2], (int)fs[3]);
         } else if (i <= 7) {
            lv2.setForDataType(fs[0], fs[1], fs[2], fs[3]);
         } else {
            lv2.set(fs);
         }

         this.uniformData.add(lv2);
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
