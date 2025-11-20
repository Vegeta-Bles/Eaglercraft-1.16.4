package net.minecraft.client.gl;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.List;
import java.util.function.IntSupplier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.math.Matrix4f;

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

   public void addAuxTarget(String name, IntSupplier _snowman, int width, int height) {
      this.samplerNames.add(this.samplerNames.size(), name);
      this.samplerValues.add(this.samplerValues.size(), _snowman);
      this.samplerWidths.add(this.samplerWidths.size(), width);
      this.samplerHeights.add(this.samplerHeights.size(), height);
   }

   public void setProjectionMatrix(Matrix4f projectionMatrix) {
      this.projectionMatrix = projectionMatrix;
   }

   public void render(float time) {
      this.input.endWrite();
      float _snowman = (float)this.output.textureWidth;
      float _snowmanx = (float)this.output.textureHeight;
      RenderSystem.viewport(0, 0, (int)_snowman, (int)_snowmanx);
      this.program.bindSampler("DiffuseSampler", this.input::getColorAttachment);

      for (int _snowmanxx = 0; _snowmanxx < this.samplerValues.size(); _snowmanxx++) {
         this.program.bindSampler(this.samplerNames.get(_snowmanxx), this.samplerValues.get(_snowmanxx));
         this.program
            .getUniformByNameOrDummy("AuxSize" + _snowmanxx)
            .set((float)this.samplerWidths.get(_snowmanxx).intValue(), (float)this.samplerHeights.get(_snowmanxx).intValue());
      }

      this.program.getUniformByNameOrDummy("ProjMat").set(this.projectionMatrix);
      this.program.getUniformByNameOrDummy("InSize").set((float)this.input.textureWidth, (float)this.input.textureHeight);
      this.program.getUniformByNameOrDummy("OutSize").set(_snowman, _snowmanx);
      this.program.getUniformByNameOrDummy("Time").set(time);
      MinecraftClient _snowmanxx = MinecraftClient.getInstance();
      this.program.getUniformByNameOrDummy("ScreenSize").set((float)_snowmanxx.getWindow().getFramebufferWidth(), (float)_snowmanxx.getWindow().getFramebufferHeight());
      this.program.enable();
      this.output.clear(MinecraftClient.IS_SYSTEM_MAC);
      this.output.beginWrite(false);
      RenderSystem.depthFunc(519);
      BufferBuilder _snowmanxxx = Tessellator.getInstance().getBuffer();
      _snowmanxxx.begin(7, VertexFormats.POSITION_COLOR);
      _snowmanxxx.vertex(0.0, 0.0, 500.0).color(255, 255, 255, 255).next();
      _snowmanxxx.vertex((double)_snowman, 0.0, 500.0).color(255, 255, 255, 255).next();
      _snowmanxxx.vertex((double)_snowman, (double)_snowmanx, 500.0).color(255, 255, 255, 255).next();
      _snowmanxxx.vertex(0.0, (double)_snowmanx, 500.0).color(255, 255, 255, 255).next();
      _snowmanxxx.end();
      BufferRenderer.draw(_snowmanxxx);
      RenderSystem.depthFunc(515);
      this.program.disable();
      this.output.endWrite();
      this.input.endRead();

      for (Object _snowmanxxxx : this.samplerValues) {
         if (_snowmanxxxx instanceof Framebuffer) {
            ((Framebuffer)_snowmanxxxx).endRead();
         }
      }
   }

   public JsonGlProgram getProgram() {
      return this.program;
   }
}
