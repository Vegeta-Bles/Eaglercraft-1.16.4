import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallback;

public class dev {
   private final Long2ObjectMap<der> a = new Long2ObjectOpenHashMap();
   private final des b;

   public dev(des var1) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      this.b = _snowman;
      GLFW.glfwSetMonitorCallback(this::a);
      PointerBuffer _snowman = GLFW.glfwGetMonitors();
      if (_snowman != null) {
         for (int _snowmanx = 0; _snowmanx < _snowman.limit(); _snowmanx++) {
            long _snowmanxx = _snowman.get(_snowmanx);
            this.a.put(_snowmanxx, _snowman.createMonitor(_snowmanxx));
         }
      }
   }

   private void a(long var1, int var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (_snowman == 262145) {
         this.a.put(_snowman, this.b.createMonitor(_snowman));
      } else if (_snowman == 262146) {
         this.a.remove(_snowman);
      }
   }

   @Nullable
   public der a(long var1) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      return (der)this.a.get(_snowman);
   }

   @Nullable
   public der a(dez var1) {
      long _snowman = GLFW.glfwGetWindowMonitor(_snowman.i());
      if (_snowman != 0L) {
         return this.a(_snowman);
      } else {
         int _snowmanx = _snowman.q();
         int _snowmanxx = _snowmanx + _snowman.m();
         int _snowmanxxx = _snowman.r();
         int _snowmanxxxx = _snowmanxxx + _snowman.n();
         int _snowmanxxxxx = -1;
         der _snowmanxxxxxx = null;
         ObjectIterator var10 = this.a.values().iterator();

         while (var10.hasNext()) {
            der _snowmanxxxxxxx = (der)var10.next();
            int _snowmanxxxxxxxx = _snowmanxxxxxxx.c();
            int _snowmanxxxxxxxxx = _snowmanxxxxxxxx + _snowmanxxxxxxx.b().a();
            int _snowmanxxxxxxxxxx = _snowmanxxxxxxx.d();
            int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx + _snowmanxxxxxxx.b().b();
            int _snowmanxxxxxxxxxxxx = a(_snowmanx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxx = a(_snowmanxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxx = a(_snowmanxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxx = a(_snowmanxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxx = Math.max(0, _snowmanxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxx = Math.max(0, _snowmanxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxxxxxxxx > _snowmanxxxxx) {
               _snowmanxxxxxx = _snowmanxxxxxxx;
               _snowmanxxxxx = _snowmanxxxxxxxxxxxxxxxxxx;
            }
         }

         return _snowmanxxxxxx;
      }
   }

   public static int a(int var0, int var1, int var2) {
      if (_snowman < _snowman) {
         return _snowman;
      } else {
         return _snowman > _snowman ? _snowman : _snowman;
      }
   }

   public void a() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GLFWMonitorCallback _snowman = GLFW.glfwSetMonitorCallback(null);
      if (_snowman != null) {
         _snowman.free();
      }
   }
}
