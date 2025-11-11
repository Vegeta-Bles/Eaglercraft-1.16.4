import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Optional;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;

public final class der {
   private final long a;
   private final List<dey> b;
   private dey c;
   private int d;
   private int e;

   public der(long var1) {
      this.a = _snowman;
      this.b = Lists.newArrayList();
      this.a();
   }

   public void a() {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      this.b.clear();
      Buffer _snowman = GLFW.glfwGetVideoModes(this.a);

      for (int _snowmanx = _snowman.limit() - 1; _snowmanx >= 0; _snowmanx--) {
         _snowman.position(_snowmanx);
         dey _snowmanxx = new dey(_snowman);
         if (_snowmanxx.c() >= 8 && _snowmanxx.d() >= 8 && _snowmanxx.e() >= 8) {
            this.b.add(_snowmanxx);
         }
      }

      int[] _snowmanxx = new int[1];
      int[] _snowmanxxx = new int[1];
      GLFW.glfwGetMonitorPos(this.a, _snowmanxx, _snowmanxxx);
      this.d = _snowmanxx[0];
      this.e = _snowmanxxx[0];
      GLFWVidMode _snowmanxxxx = GLFW.glfwGetVideoMode(this.a);
      this.c = new dey(_snowmanxxxx);
   }

   public dey a(Optional<dey> var1) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      if (_snowman.isPresent()) {
         dey _snowman = _snowman.get();

         for (dey _snowmanx : this.b) {
            if (_snowmanx.equals(_snowman)) {
               return _snowmanx;
            }
         }
      }

      return this.b();
   }

   public int a(dey var1) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      return this.b.indexOf(_snowman);
   }

   public dey b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }

   public int d() {
      return this.e;
   }

   public dey a(int var1) {
      return this.b.get(_snowman);
   }

   public int e() {
      return this.b.size();
   }

   public long f() {
      return this.a;
   }

   @Override
   public String toString() {
      return String.format("Monitor[%s %sx%s %s]", this.a, this.d, this.e, this.c);
   }
}
