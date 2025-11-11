import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharModsCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWDropCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;

public class deo {
   @Nullable
   private static final MethodHandle b;
   private static final int c;
   public static final deo.a a;

   public static deo.a a(int var0, int var1) {
      return _snowman == -1 ? deo.b.b.a(_snowman) : deo.b.a.a(_snowman);
   }

   public static deo.a a(String var0) {
      if (deo.a.e.containsKey(_snowman)) {
         return deo.a.e.get(_snowman);
      } else {
         for (deo.b _snowman : deo.b.values()) {
            if (_snowman.startsWith(_snowman.e)) {
               String _snowmanx = _snowman.substring(_snowman.e.length() + 1);
               return _snowman.a(Integer.parseInt(_snowmanx));
            }
         }

         throw new IllegalArgumentException("Unknown key name: " + _snowman);
      }
   }

   public static boolean a(long var0, int var2) {
      return GLFW.glfwGetKey(_snowman, _snowman) == 1;
   }

   public static void a(long var0, GLFWKeyCallbackI var2, GLFWCharModsCallbackI var3) {
      GLFW.glfwSetKeyCallback(_snowman, _snowman);
      GLFW.glfwSetCharModsCallback(_snowman, _snowman);
   }

   public static void a(long var0, GLFWCursorPosCallbackI var2, GLFWMouseButtonCallbackI var3, GLFWScrollCallbackI var4, GLFWDropCallbackI var5) {
      GLFW.glfwSetCursorPosCallback(_snowman, _snowman);
      GLFW.glfwSetMouseButtonCallback(_snowman, _snowman);
      GLFW.glfwSetScrollCallback(_snowman, _snowman);
      GLFW.glfwSetDropCallback(_snowman, _snowman);
   }

   public static void a(long var0, int var2, double var3, double var5) {
      GLFW.glfwSetCursorPos(_snowman, _snowman, _snowman);
      GLFW.glfwSetInputMode(_snowman, 208897, _snowman);
   }

   public static boolean a() {
      try {
         return b != null && (boolean)b.invokeExact();
      } catch (Throwable var1) {
         throw new RuntimeException(var1);
      }
   }

   public static void a(long var0, boolean var2) {
      if (a()) {
         GLFW.glfwSetInputMode(_snowman, c, _snowman ? 1 : 0);
      }
   }

   static {
      Lookup _snowman = MethodHandles.lookup();
      MethodType _snowmanx = MethodType.methodType(boolean.class);
      MethodHandle _snowmanxx = null;
      int _snowmanxxx = 0;

      try {
         _snowmanxx = _snowman.findStatic(GLFW.class, "glfwRawMouseMotionSupported", _snowmanx);
         MethodHandle _snowmanxxxx = _snowman.findStaticGetter(GLFW.class, "GLFW_RAW_MOUSE_MOTION", int.class);
         _snowmanxxx = (int)_snowmanxxxx.invokeExact();
      } catch (NoSuchFieldException | NoSuchMethodException var5) {
      } catch (Throwable var6) {
         throw new RuntimeException(var6);
      }

      b = _snowmanxx;
      c = _snowmanxxx;
      a = deo.b.a.a(-1);
   }

   public static final class a {
      private final String a;
      private final deo.b b;
      private final int c;
      private final afi<nr> d;
      private static final Map<String, deo.a> e = Maps.newHashMap();

      private a(String var1, deo.b var2, int var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = new afi<>(() -> _snowman.f.apply(_snowman, _snowman));
         e.put(_snowman, this);
      }

      public deo.b a() {
         return this.b;
      }

      public int b() {
         return this.c;
      }

      public String c() {
         return this.a;
      }

      public nr d() {
         return this.d.a();
      }

      public OptionalInt e() {
         if (this.c >= 48 && this.c <= 57) {
            return OptionalInt.of(this.c - 48);
         } else {
            return this.c >= 320 && this.c <= 329 ? OptionalInt.of(this.c - 320) : OptionalInt.empty();
         }
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
            deo.a _snowman = (deo.a)_snowman;
            return this.c == _snowman.c && this.b == _snowman.b;
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.b, this.c);
      }

      @Override
      public String toString() {
         return this.a;
      }
   }

   public static enum b {
      a("key.keyboard", (var0, var1) -> {
         String _snowman = GLFW.glfwGetKeyName(var0, -1);
         return (nr)(_snowman != null ? new oe(_snowman) : new of(var1));
      }),
      b("scancode", (var0, var1) -> {
         String _snowman = GLFW.glfwGetKeyName(-1, var0);
         return (nr)(_snowman != null ? new oe(_snowman) : new of(var1));
      }),
      c("key.mouse", (var0, var1) -> ly.a().b(var1) ? new of(var1) : new of("key.mouse", var0 + 1));

      private final Int2ObjectMap<deo.a> d = new Int2ObjectOpenHashMap();
      private final String e;
      private final BiFunction<Integer, String, nr> f;

      private static void a(deo.b var0, String var1, int var2) {
         deo.a _snowman = new deo.a(_snowman, _snowman, _snowman);
         _snowman.d.put(_snowman, _snowman);
      }

      private b(String var3, BiFunction<Integer, String, nr> var4) {
         this.e = _snowman;
         this.f = _snowman;
      }

      public deo.a a(int var1) {
         return (deo.a)this.d.computeIfAbsent(_snowman, var1x -> {
            int _snowman = var1x;
            if (this == c) {
               _snowman = var1x + 1;
            }

            String _snowmanx = this.e + "." + _snowman;
            return new deo.a(_snowmanx, this, var1x);
         });
      }

      static {
         a(a, "key.keyboard.unknown", -1);
         a(c, "key.mouse.left", 0);
         a(c, "key.mouse.right", 1);
         a(c, "key.mouse.middle", 2);
         a(c, "key.mouse.4", 3);
         a(c, "key.mouse.5", 4);
         a(c, "key.mouse.6", 5);
         a(c, "key.mouse.7", 6);
         a(c, "key.mouse.8", 7);
         a(a, "key.keyboard.0", 48);
         a(a, "key.keyboard.1", 49);
         a(a, "key.keyboard.2", 50);
         a(a, "key.keyboard.3", 51);
         a(a, "key.keyboard.4", 52);
         a(a, "key.keyboard.5", 53);
         a(a, "key.keyboard.6", 54);
         a(a, "key.keyboard.7", 55);
         a(a, "key.keyboard.8", 56);
         a(a, "key.keyboard.9", 57);
         a(a, "key.keyboard.a", 65);
         a(a, "key.keyboard.b", 66);
         a(a, "key.keyboard.c", 67);
         a(a, "key.keyboard.d", 68);
         a(a, "key.keyboard.e", 69);
         a(a, "key.keyboard.f", 70);
         a(a, "key.keyboard.g", 71);
         a(a, "key.keyboard.h", 72);
         a(a, "key.keyboard.i", 73);
         a(a, "key.keyboard.j", 74);
         a(a, "key.keyboard.k", 75);
         a(a, "key.keyboard.l", 76);
         a(a, "key.keyboard.m", 77);
         a(a, "key.keyboard.n", 78);
         a(a, "key.keyboard.o", 79);
         a(a, "key.keyboard.p", 80);
         a(a, "key.keyboard.q", 81);
         a(a, "key.keyboard.r", 82);
         a(a, "key.keyboard.s", 83);
         a(a, "key.keyboard.t", 84);
         a(a, "key.keyboard.u", 85);
         a(a, "key.keyboard.v", 86);
         a(a, "key.keyboard.w", 87);
         a(a, "key.keyboard.x", 88);
         a(a, "key.keyboard.y", 89);
         a(a, "key.keyboard.z", 90);
         a(a, "key.keyboard.f1", 290);
         a(a, "key.keyboard.f2", 291);
         a(a, "key.keyboard.f3", 292);
         a(a, "key.keyboard.f4", 293);
         a(a, "key.keyboard.f5", 294);
         a(a, "key.keyboard.f6", 295);
         a(a, "key.keyboard.f7", 296);
         a(a, "key.keyboard.f8", 297);
         a(a, "key.keyboard.f9", 298);
         a(a, "key.keyboard.f10", 299);
         a(a, "key.keyboard.f11", 300);
         a(a, "key.keyboard.f12", 301);
         a(a, "key.keyboard.f13", 302);
         a(a, "key.keyboard.f14", 303);
         a(a, "key.keyboard.f15", 304);
         a(a, "key.keyboard.f16", 305);
         a(a, "key.keyboard.f17", 306);
         a(a, "key.keyboard.f18", 307);
         a(a, "key.keyboard.f19", 308);
         a(a, "key.keyboard.f20", 309);
         a(a, "key.keyboard.f21", 310);
         a(a, "key.keyboard.f22", 311);
         a(a, "key.keyboard.f23", 312);
         a(a, "key.keyboard.f24", 313);
         a(a, "key.keyboard.f25", 314);
         a(a, "key.keyboard.num.lock", 282);
         a(a, "key.keyboard.keypad.0", 320);
         a(a, "key.keyboard.keypad.1", 321);
         a(a, "key.keyboard.keypad.2", 322);
         a(a, "key.keyboard.keypad.3", 323);
         a(a, "key.keyboard.keypad.4", 324);
         a(a, "key.keyboard.keypad.5", 325);
         a(a, "key.keyboard.keypad.6", 326);
         a(a, "key.keyboard.keypad.7", 327);
         a(a, "key.keyboard.keypad.8", 328);
         a(a, "key.keyboard.keypad.9", 329);
         a(a, "key.keyboard.keypad.add", 334);
         a(a, "key.keyboard.keypad.decimal", 330);
         a(a, "key.keyboard.keypad.enter", 335);
         a(a, "key.keyboard.keypad.equal", 336);
         a(a, "key.keyboard.keypad.multiply", 332);
         a(a, "key.keyboard.keypad.divide", 331);
         a(a, "key.keyboard.keypad.subtract", 333);
         a(a, "key.keyboard.down", 264);
         a(a, "key.keyboard.left", 263);
         a(a, "key.keyboard.right", 262);
         a(a, "key.keyboard.up", 265);
         a(a, "key.keyboard.apostrophe", 39);
         a(a, "key.keyboard.backslash", 92);
         a(a, "key.keyboard.comma", 44);
         a(a, "key.keyboard.equal", 61);
         a(a, "key.keyboard.grave.accent", 96);
         a(a, "key.keyboard.left.bracket", 91);
         a(a, "key.keyboard.minus", 45);
         a(a, "key.keyboard.period", 46);
         a(a, "key.keyboard.right.bracket", 93);
         a(a, "key.keyboard.semicolon", 59);
         a(a, "key.keyboard.slash", 47);
         a(a, "key.keyboard.space", 32);
         a(a, "key.keyboard.tab", 258);
         a(a, "key.keyboard.left.alt", 342);
         a(a, "key.keyboard.left.control", 341);
         a(a, "key.keyboard.left.shift", 340);
         a(a, "key.keyboard.left.win", 343);
         a(a, "key.keyboard.right.alt", 346);
         a(a, "key.keyboard.right.control", 345);
         a(a, "key.keyboard.right.shift", 344);
         a(a, "key.keyboard.right.win", 347);
         a(a, "key.keyboard.enter", 257);
         a(a, "key.keyboard.escape", 256);
         a(a, "key.keyboard.backspace", 259);
         a(a, "key.keyboard.delete", 261);
         a(a, "key.keyboard.end", 269);
         a(a, "key.keyboard.home", 268);
         a(a, "key.keyboard.insert", 260);
         a(a, "key.keyboard.page.down", 267);
         a(a, "key.keyboard.page.up", 266);
         a(a, "key.keyboard.caps.lock", 280);
         a(a, "key.keyboard.pause", 284);
         a(a, "key.keyboard.scroll.lock", 281);
         a(a, "key.keyboard.menu", 348);
         a(a, "key.keyboard.print.screen", 283);
         a(a, "key.keyboard.world.1", 161);
         a(a, "key.keyboard.world.2", 162);
      }
   }
}
