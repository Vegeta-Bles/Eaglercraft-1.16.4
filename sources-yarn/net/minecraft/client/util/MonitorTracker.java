package net.minecraft.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallback;

@Environment(EnvType.CLIENT)
public class MonitorTracker {
   private final Long2ObjectMap<Monitor> pointerToMonitorMap = new Long2ObjectOpenHashMap();
   private final MonitorFactory monitorFactory;

   public MonitorTracker(MonitorFactory monitorFactory) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      this.monitorFactory = monitorFactory;
      GLFW.glfwSetMonitorCallback(this::handleMonitorEvent);
      PointerBuffer pointerBuffer = GLFW.glfwGetMonitors();
      if (pointerBuffer != null) {
         for (int i = 0; i < pointerBuffer.limit(); i++) {
            long l = pointerBuffer.get(i);
            this.pointerToMonitorMap.put(l, monitorFactory.createMonitor(l));
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
   public Monitor getMonitor(long l) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      return (Monitor)this.pointerToMonitorMap.get(l);
   }

   @Nullable
   public Monitor getMonitor(Window arg) {
      long l = GLFW.glfwGetWindowMonitor(arg.getHandle());
      if (l != 0L) {
         return this.getMonitor(l);
      } else {
         int i = arg.getX();
         int j = i + arg.getWidth();
         int k = arg.getY();
         int m = k + arg.getHeight();
         int n = -1;
         Monitor lv = null;
         ObjectIterator var10 = this.pointerToMonitorMap.values().iterator();

         while (var10.hasNext()) {
            Monitor lv2 = (Monitor)var10.next();
            int o = lv2.getViewportX();
            int p = o + lv2.getCurrentVideoMode().getWidth();
            int q = lv2.getViewportY();
            int r = q + lv2.getCurrentVideoMode().getHeight();
            int s = clamp(i, o, p);
            int t = clamp(j, o, p);
            int u = clamp(k, q, r);
            int v = clamp(m, q, r);
            int w = Math.max(0, t - s);
            int x = Math.max(0, v - u);
            int y = w * x;
            if (y > n) {
               lv = lv2;
               n = y;
            }
         }

         return lv;
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
      GLFWMonitorCallback gLFWMonitorCallback = GLFW.glfwSetMonitorCallback(null);
      if (gLFWMonitorCallback != null) {
         gLFWMonitorCallback.free();
      }
   }
}
