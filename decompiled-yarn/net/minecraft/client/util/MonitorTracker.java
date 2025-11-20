package net.minecraft.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallback;

public class MonitorTracker {
   private final Long2ObjectMap<Monitor> pointerToMonitorMap = new Long2ObjectOpenHashMap();
   private final MonitorFactory monitorFactory;

   public MonitorTracker(MonitorFactory monitorFactory) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      this.monitorFactory = monitorFactory;
      GLFW.glfwSetMonitorCallback(this::handleMonitorEvent);
      PointerBuffer _snowman = GLFW.glfwGetMonitors();
      if (_snowman != null) {
         for (int _snowmanx = 0; _snowmanx < _snowman.limit(); _snowmanx++) {
            long _snowmanxx = _snowman.get(_snowmanx);
            this.pointerToMonitorMap.put(_snowmanxx, monitorFactory.createMonitor(_snowmanxx));
         }
      }
   }

   private void handleMonitorEvent(long monitor, int event) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (event == 262145) {
         this.pointerToMonitorMap.put(monitor, this.monitorFactory.createMonitor(monitor));
      } else if (event == 262146) {
         this.pointerToMonitorMap.remove(monitor);
      }
   }

   @Nullable
   public Monitor getMonitor(long _snowman) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      return (Monitor)this.pointerToMonitorMap.get(_snowman);
   }

   @Nullable
   public Monitor getMonitor(Window _snowman) {
      long _snowmanx = GLFW.glfwGetWindowMonitor(_snowman.getHandle());
      if (_snowmanx != 0L) {
         return this.getMonitor(_snowmanx);
      } else {
         int _snowmanxx = _snowman.getX();
         int _snowmanxxx = _snowmanxx + _snowman.getWidth();
         int _snowmanxxxx = _snowman.getY();
         int _snowmanxxxxx = _snowmanxxxx + _snowman.getHeight();
         int _snowmanxxxxxx = -1;
         Monitor _snowmanxxxxxxx = null;
         ObjectIterator var10 = this.pointerToMonitorMap.values().iterator();

         while (var10.hasNext()) {
            Monitor _snowmanxxxxxxxx = (Monitor)var10.next();
            int _snowmanxxxxxxxxx = _snowmanxxxxxxxx.getViewportX();
            int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx + _snowmanxxxxxxxx.getCurrentVideoMode().getWidth();
            int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx.getViewportY();
            int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx + _snowmanxxxxxxxx.getCurrentVideoMode().getHeight();
            int _snowmanxxxxxxxxxxxxx = clamp(_snowmanxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxx = clamp(_snowmanxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxx = clamp(_snowmanxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxx = clamp(_snowmanxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxx = Math.max(0, _snowmanxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxxx = Math.max(0, _snowmanxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxxxxxxxxx > _snowmanxxxxxx) {
               _snowmanxxxxxxx = _snowmanxxxxxxxx;
               _snowmanxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx;
            }
         }

         return _snowmanxxxxxxx;
      }
   }

   public static int clamp(int value, int min, int max) {
      if (value < min) {
         return min;
      } else {
         return value > max ? max : value;
      }
   }

   public void stop() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GLFWMonitorCallback _snowman = GLFW.glfwSetMonitorCallback(null);
      if (_snowman != null) {
         _snowman.free();
      }
   }
}
