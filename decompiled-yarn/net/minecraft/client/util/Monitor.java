package net.minecraft.client.util;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Optional;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;

public final class Monitor {
   private final long handle;
   private final List<VideoMode> videoModes;
   private VideoMode currentVideoMode;
   private int x;
   private int y;

   public Monitor(long _snowman) {
      this.handle = _snowman;
      this.videoModes = Lists.newArrayList();
      this.populateVideoModes();
   }

   public void populateVideoModes() {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      this.videoModes.clear();
      Buffer _snowman = GLFW.glfwGetVideoModes(this.handle);

      for (int _snowmanx = _snowman.limit() - 1; _snowmanx >= 0; _snowmanx--) {
         _snowman.position(_snowmanx);
         VideoMode _snowmanxx = new VideoMode(_snowman);
         if (_snowmanxx.getRedBits() >= 8 && _snowmanxx.getGreenBits() >= 8 && _snowmanxx.getBlueBits() >= 8) {
            this.videoModes.add(_snowmanxx);
         }
      }

      int[] _snowmanxx = new int[1];
      int[] _snowmanxxx = new int[1];
      GLFW.glfwGetMonitorPos(this.handle, _snowmanxx, _snowmanxxx);
      this.x = _snowmanxx[0];
      this.y = _snowmanxxx[0];
      GLFWVidMode _snowmanxxxx = GLFW.glfwGetVideoMode(this.handle);
      this.currentVideoMode = new VideoMode(_snowmanxxxx);
   }

   public VideoMode findClosestVideoMode(Optional<VideoMode> _snowman) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      if (_snowman.isPresent()) {
         VideoMode _snowmanx = _snowman.get();

         for (VideoMode _snowmanxx : this.videoModes) {
            if (_snowmanxx.equals(_snowmanx)) {
               return _snowmanxx;
            }
         }
      }

      return this.getCurrentVideoMode();
   }

   public int findClosestVideoModeIndex(VideoMode _snowman) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      return this.videoModes.indexOf(_snowman);
   }

   public VideoMode getCurrentVideoMode() {
      return this.currentVideoMode;
   }

   public int getViewportX() {
      return this.x;
   }

   public int getViewportY() {
      return this.y;
   }

   public VideoMode getVideoMode(int _snowman) {
      return this.videoModes.get(_snowman);
   }

   public int getVideoModeCount() {
      return this.videoModes.size();
   }

   public long getHandle() {
      return this.handle;
   }

   @Override
   public String toString() {
      return String.format("Monitor[%s %sx%s %s]", this.handle, this.x, this.y, this.currentVideoMode);
   }
}
