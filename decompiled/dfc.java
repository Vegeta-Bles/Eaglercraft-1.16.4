import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Locale;

public class dfc {
   private static dfc a;
   private final int b;
   private final int c;
   private final int d;
   private final int e;
   private final int f;
   private final boolean g;
   private final boolean h;

   private dfc(boolean var1, boolean var2, int var3, int var4, int var5, int var6, int var7) {
      this.g = _snowman;
      this.b = _snowman;
      this.d = _snowman;
      this.c = _snowman;
      this.e = _snowman;
      this.h = _snowman;
      this.f = _snowman;
   }

   public dfc() {
      this(false, true, 1, 0, 1, 0, 32774);
   }

   public dfc(int var1, int var2, int var3) {
      this(false, false, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public dfc(int var1, int var2, int var3, int var4, int var5) {
      this(true, false, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public void a() {
      if (!this.equals(a)) {
         if (a == null || this.h != a.b()) {
            a = this;
            if (this.h) {
               RenderSystem.disableBlend();
               return;
            }

            RenderSystem.enableBlend();
         }

         RenderSystem.blendEquation(this.f);
         if (this.g) {
            RenderSystem.blendFuncSeparate(this.b, this.d, this.c, this.e);
         } else {
            RenderSystem.blendFunc(this.b, this.d);
         }
      }
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof dfc)) {
         return false;
      } else {
         dfc _snowman = (dfc)_snowman;
         if (this.f != _snowman.f) {
            return false;
         } else if (this.e != _snowman.e) {
            return false;
         } else if (this.d != _snowman.d) {
            return false;
         } else if (this.h != _snowman.h) {
            return false;
         } else if (this.g != _snowman.g) {
            return false;
         } else {
            return this.c != _snowman.c ? false : this.b == _snowman.b;
         }
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.b;
      _snowman = 31 * _snowman + this.c;
      _snowman = 31 * _snowman + this.d;
      _snowman = 31 * _snowman + this.e;
      _snowman = 31 * _snowman + this.f;
      _snowman = 31 * _snowman + (this.g ? 1 : 0);
      return 31 * _snowman + (this.h ? 1 : 0);
   }

   public boolean b() {
      return this.h;
   }

   public static int a(String var0) {
      String _snowman = _snowman.trim().toLowerCase(Locale.ROOT);
      if ("add".equals(_snowman)) {
         return 32774;
      } else if ("subtract".equals(_snowman)) {
         return 32778;
      } else if ("reversesubtract".equals(_snowman)) {
         return 32779;
      } else if ("reverse_subtract".equals(_snowman)) {
         return 32779;
      } else if ("min".equals(_snowman)) {
         return 32775;
      } else {
         return "max".equals(_snowman) ? 32776 : 32774;
      }
   }

   public static int b(String var0) {
      String _snowman = _snowman.trim().toLowerCase(Locale.ROOT);
      _snowman = _snowman.replaceAll("_", "");
      _snowman = _snowman.replaceAll("one", "1");
      _snowman = _snowman.replaceAll("zero", "0");
      _snowman = _snowman.replaceAll("minus", "-");
      if ("0".equals(_snowman)) {
         return 0;
      } else if ("1".equals(_snowman)) {
         return 1;
      } else if ("srccolor".equals(_snowman)) {
         return 768;
      } else if ("1-srccolor".equals(_snowman)) {
         return 769;
      } else if ("dstcolor".equals(_snowman)) {
         return 774;
      } else if ("1-dstcolor".equals(_snowman)) {
         return 775;
      } else if ("srcalpha".equals(_snowman)) {
         return 770;
      } else if ("1-srcalpha".equals(_snowman)) {
         return 771;
      } else if ("dstalpha".equals(_snowman)) {
         return 772;
      } else {
         return "1-dstalpha".equals(_snowman) ? 773 : -1;
      }
   }
}
