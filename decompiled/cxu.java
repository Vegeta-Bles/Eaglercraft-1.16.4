import java.util.Objects;
import javax.annotation.Nullable;

public class cxu {
   private final cxu.a a;
   private byte b;
   private byte c;
   private byte d;
   private final nr e;

   public cxu(cxu.a var1, byte var2, byte var3, byte var4, @Nullable nr var5) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   public byte a() {
      return this.a.a();
   }

   public cxu.a b() {
      return this.a;
   }

   public byte c() {
      return this.b;
   }

   public byte d() {
      return this.c;
   }

   public byte e() {
      return this.d;
   }

   public boolean f() {
      return this.a.b();
   }

   @Nullable
   public nr g() {
      return this.e;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof cxu)) {
         return false;
      } else {
         cxu _snowman = (cxu)_snowman;
         if (this.a != _snowman.a) {
            return false;
         } else if (this.d != _snowman.d) {
            return false;
         } else if (this.b != _snowman.b) {
            return false;
         } else {
            return this.c != _snowman.c ? false : Objects.equals(this.e, _snowman.e);
         }
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.a.a();
      _snowman = 31 * _snowman + this.b;
      _snowman = 31 * _snowman + this.c;
      _snowman = 31 * _snowman + this.d;
      return 31 * _snowman + Objects.hashCode(this.e);
   }

   public static enum a {
      a(false),
      b(true),
      c(false),
      d(false),
      e(true),
      f(true),
      g(false),
      h(false),
      i(true, 5393476),
      j(true, 3830373),
      k(true),
      l(true),
      m(true),
      n(true),
      o(true),
      p(true),
      q(true),
      r(true),
      s(true),
      t(true),
      u(true),
      v(true),
      w(true),
      x(true),
      y(true),
      z(true),
      A(true);

      private final byte B = (byte)this.ordinal();
      private final boolean C;
      private final int D;

      private a(boolean var3) {
         this(_snowman, -1);
      }

      private a(boolean var3, int var4) {
         this.C = _snowman;
         this.D = _snowman;
      }

      public byte a() {
         return this.B;
      }

      public boolean b() {
         return this.C;
      }

      public boolean c() {
         return this.D >= 0;
      }

      public int d() {
         return this.D;
      }

      public static cxu.a a(byte var0) {
         return values()[afm.a(_snowman, 0, values().length - 1)];
      }
   }
}
