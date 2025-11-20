package net.minecraft.client.gl;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.List;
import java.util.function.IntSupplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.math.Matrix4f;

@Environment(EnvType.CLIENT)
public class PostProcessShader implements AutoCloseable {
   private final JsonGlProgram program;
   public final Framebuffer input;
   public final Framebuffer output;
   private final List<IntSupplier> samplerValues = Lists.newArrayList();
   private final List<String> samplerNames = Lists.newArrayList();
   private final List<Integer> samplerWidths = Lists.newArrayList();
   private final List<Integer> samplerHeights = Lists.newArrayList();
   private Matrix4f projectionMatrix;

   public PostProcessShader(ResourceManager resourceManager, String programName, Framebuffer input, Framebuffer output) throws IOException {
      this.program = new JsonGlProgram(resourceManager, programName);
      this.input = input;
      this.output = output;
   }

   @Override
   public void close() {
      this.program.close();
   }

   public void addAuxTarget(String name, IntSupplier intSupplier, int width, int height) {
      this.samplerNames.add(this.samplerNames.size(), name);
      this.samplerValues.add(this.samplerValues.size(), intSupplier);
      this.samplerWidths.add(this.samplerWidths.size(), width);
      this.samplerHeights.add(this.samplerHeights.size(), height);
   }

   public void setProjectionMatrix(Matrix4f projectionMatrix) {
      this.projectionMatrix = projectionMatrix;
   }

   public void render(float time) {
      this.input.endWrite();
      float g = (float)this.output.textureWidth;
      float h = (float)this.output.textureHeight;
      RenderSystem.viewport(0, 0, (int)g, (int)h);
      this.program.bindSampler("DiffuseSampler", this.input::getColorAttachment);

      for (int i = 0; i < this.samplerValues.size(); i++) {
         this.program.bindSampler(this.samplerNames.get(i), this.samplerValues.get(i));
         this.program.getUniformByNameOrDummy("AuxSize" + i).set((float)this.samplerWidths.get(i).intValue(), (float)this.samplerHeights.get(i).intValue());
      }

      this.program.getUniformByNameOrDummy("ProjMat").set(this.projectionMatrix);
      this.program.getUniformByNameOrDummy("InSize").set((float)this.input.textureWidth, (float)this.input.textureHeight);
      this.program.getUniformByNameOrDummy("OutSize").set(g, h);
      this.program.getUniformByNameOrDummy("Time").set(time);
      MinecraftClient lv = MinecraftClient.getInstance();
      this.program.getUniformByNameOrDummy("ScreenSize").set((float)lv.getWindow().getFramebufferWidth(), (float)lv.getWindow().getFramebufferHeight());
      this.program.enable();
      this.output.clear(MinecraftClient.IS_SYSTEM_MAC);
      this.output.beginWrite(false);
      RenderSystem.depthFunc(519);
      BufferBuilder lv2 = Tessellator.getInstance().getBuffer();
      lv2.begin(7, VertexFormats.POSITION_COLOR);
      lv2.vertex(0.0, 0.0, 500.0).color(255, 255, 255, 255).next();
      lv2.vertex((double)g, 0.0, 500.0).color(255, 255, 255, 255).next();
      lv2.vertex((double)g, (double)h, 500.0).color(255, 255, 255, 255).next();
      lv2.vertex(0.0, (double)h, 500.0).color(255, 255, 255, 255).next();
      lv2.end();
      BufferRenderer.draw(lv2);
      RenderSystem.depthFunc(515);
      this.program.disable();
      this.output.endWrite();
      this.input.endRead();

      for (Object object : this.samplerValues) {
         if (object instanceof Framebuffer) {
            ((Framebuffer)object).endRead();
         }
      }
   }

   public JsonGlProgram getProgram() {
      return this.program;
   }
}
