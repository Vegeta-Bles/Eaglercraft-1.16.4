import java.util.function.IntConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dfs {
   private static final Logger a = LogManager.getLogger();
   private final dfs.a b;
   private final dfs.b c;
   private final int d;
   private final int e;
   private final int f;

   public dfs(int var1, dfs.a var2, dfs.b var3, int var4) {
      if (this.a(_snowman, _snowman)) {
         this.c = _snowman;
      } else {
         a.warn("Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.");
         this.c = dfs.b.d;
      }

      this.b = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman.a() * this.e;
   }

   private boolean a(int var1, dfs.b var2) {
      return _snowman == 0 || _snowman == dfs.b.d;
   }

   public final dfs.a a() {
      return this.b;
   }

   public final dfs.b b() {
      return this.c;
   }

   public final int c() {
      return this.d;
   }

   @Override
   public String toString() {
      return this.e + "," + this.c.a() + "," + this.b.b();
   }

   public final int d() {
      return this.f;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         dfs _snowman = (dfs)_snowman;
         if (this.e != _snowman.e) {
            return false;
         } else if (this.d != _snowman.d) {
            return false;
         } else {
            return this.b != _snowman.b ? false : this.c == _snowman.c;
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.b.hashCode();
      _snowman = 31 * _snowman + this.c.hashCode();
      _snowman = 31 * _snowman + this.d;
      return 31 * _snowman + this.e;
   }

   public void a(long var1, int var3) {
      this.c.a(this.e, this.b.c(), _snowman, _snowman, this.d);
   }

   public void e() {
      this.c.a(this.d);
   }

   public static enum a {
      a(4, "Float", 5126),
      b(1, "Unsigned Byte", 5121),
      c(1, "Byte", 5120),
      d(2, "Unsigned Short", 5123),
      e(2, "Short", 5122),
      f(4, "Unsigned Int", 5125),
      g(4, "Int", 5124);

      private final int h;
      private final String i;
      private final int j;

      private a(int var3, String var4, int var5) {
         this.h = _snowman;
         this.i = _snowman;
         this.j = _snowman;
      }

      public int a() {
         return this.h;
      }

      public String b() {
         return this.i;
      }

      public int c() {
         return this.j;
      }
   }

   public static enum b {
      a("Position", (var0, var1, var2, var3, var5) -> {
         dem.b(var0, var1, var2, var3);
         dem.x(32884);
      }, var0 -> dem.y(32884)),
      b("Normal", (var0, var1, var2, var3, var5) -> {
         dem.a(var1, var2, var3);
         dem.x(32885);
      }, var0 -> dem.y(32885)),
      c("Vertex Color", (var0, var1, var2, var3, var5) -> {
         dem.c(var0, var1, var2, var3);
         dem.x(32886);
      }, var0 -> {
         dem.y(32886);
         dem.S();
      }),
      d("UV", (var0, var1, var2, var3, var5) -> {
         dem.n(33984 + var5);
         dem.a(var0, var1, var2, var3);
         dem.x(32888);
         dem.n(33984);
      }, var0 -> {
         dem.n(33984 + var0);
         dem.y(32888);
         dem.n(33984);
      }),
      e("Padding", (var0, var1, var2, var3, var5) -> {
      }, var0 -> {
      }),
      f("Generic", (var0, var1, var2, var3, var5) -> {
         dem.z(var5);
         dem.a(var5, var0, var1, false, var2, var3);
      }, dem::A);

      private final String g;
      private final dfs.b.a h;
      private final IntConsumer i;

      private b(String var3, dfs.b.a var4, IntConsumer var5) {
         this.g = _snowman;
         this.h = _snowman;
         this.i = _snowman;
      }

      private void a(int var1, int var2, int var3, long var4, int var6) {
         this.h.setupBufferState(_snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void a(int var1) {
         this.i.accept(_snowman);
      }

      public String a() {
         return this.g;
      }

      interface a {
         void setupBufferState(int var1, int var2, int var3, long var4, int var6);
      }
   }
}
