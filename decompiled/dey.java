import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;

public final class dey {
   private final int a;
   private final int b;
   private final int c;
   private final int d;
   private final int e;
   private final int f;
   private static final Pattern g = Pattern.compile("(\\d+)x(\\d+)(?:@(\\d+)(?::(\\d+))?)?");

   public dey(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   public dey(Buffer var1) {
      this.a = _snowman.width();
      this.b = _snowman.height();
      this.c = _snowman.redBits();
      this.d = _snowman.greenBits();
      this.e = _snowman.blueBits();
      this.f = _snowman.refreshRate();
   }

   public dey(GLFWVidMode var1) {
      this.a = _snowman.width();
      this.b = _snowman.height();
      this.c = _snowman.redBits();
      this.d = _snowman.greenBits();
      this.e = _snowman.blueBits();
      this.f = _snowman.refreshRate();
   }

   public int a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }

   public int c() {
      return this.c;
   }

   public int d() {
      return this.d;
   }

   public int e() {
      return this.e;
   }

   public int f() {
      return this.f;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         dey _snowman = (dey)_snowman;
         return this.a == _snowman.a && this.b == _snowman.b && this.c == _snowman.c && this.d == _snowman.d && this.e == _snowman.e && this.f == _snowman.f;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.a, this.b, this.c, this.d, this.e, this.f);
   }

   @Override
   public String toString() {
      return String.format("%sx%s@%s (%sbit)", this.a, this.b, this.f, this.c + this.d + this.e);
   }

   public static Optional<dey> a(@Nullable String var0) {
      if (_snowman == null) {
         return Optional.empty();
      } else {
         try {
            Matcher _snowman = g.matcher(_snowman);
            if (_snowman.matches()) {
               int _snowmanx = Integer.parseInt(_snowman.group(1));
               int _snowmanxx = Integer.parseInt(_snowman.group(2));
               String _snowmanxxx = _snowman.group(3);
               int _snowmanxxxx;
               if (_snowmanxxx == null) {
                  _snowmanxxxx = 60;
               } else {
                  _snowmanxxxx = Integer.parseInt(_snowmanxxx);
               }

               String _snowmanxxxxx = _snowman.group(4);
               int _snowmanxxxxxx;
               if (_snowmanxxxxx == null) {
                  _snowmanxxxxxx = 24;
               } else {
                  _snowmanxxxxxx = Integer.parseInt(_snowmanxxxxx);
               }

               int _snowmanxxxxxxx = _snowmanxxxxxx / 3;
               return Optional.of(new dey(_snowmanx, _snowmanxx, _snowmanxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxx, _snowmanxxxx));
            }
         } catch (Exception var9) {
         }

         return Optional.empty();
      }
   }

   public String g() {
      return String.format("%sx%s@%s:%s", this.a, this.b, this.f, this.c + this.d + this.e);
   }
}
