import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class dmy {
   private final Supplier<String> a;
   private final Consumer<String> b;
   private final Supplier<String> c;
   private final Consumer<String> d;
   private final Predicate<String> e;
   private int f;
   private int g;

   public dmy(Supplier<String> var1, Consumer<String> var2, Supplier<String> var3, Consumer<String> var4, Predicate<String> var5) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f();
   }

   public static Supplier<String> a(djz var0) {
      return () -> b(_snowman);
   }

   public static String b(djz var0) {
      return k.a(_snowman.m.a().replaceAll("\\r", ""));
   }

   public static Consumer<String> c(djz var0) {
      return var1 -> a(_snowman, var1);
   }

   public static void a(djz var0, String var1) {
      _snowman.m.a(_snowman);
   }

   public boolean a(char var1) {
      if (w.a(_snowman)) {
         this.a(this.a.get(), Character.toString(_snowman));
      }

      return true;
   }

   public boolean a(int var1) {
      if (dot.i(_snowman)) {
         this.d();
         return true;
      } else if (dot.h(_snowman)) {
         this.c();
         return true;
      } else if (dot.g(_snowman)) {
         this.b();
         return true;
      } else if (dot.f(_snowman)) {
         this.a();
         return true;
      } else if (_snowman == 259) {
         this.d(-1);
         return true;
      } else {
         if (_snowman == 261) {
            this.d(1);
         } else {
            if (_snowman == 263) {
               if (dot.x()) {
                  this.b(-1, dot.y());
               } else {
                  this.a(-1, dot.y());
               }

               return true;
            }

            if (_snowman == 262) {
               if (dot.x()) {
                  this.b(1, dot.y());
               } else {
                  this.a(1, dot.y());
               }

               return true;
            }

            if (_snowman == 268) {
               this.b(dot.y());
               return true;
            }

            if (_snowman == 269) {
               this.c(dot.y());
               return true;
            }
         }

         return false;
      }
   }

   private int g(int var1) {
      return afm.a(_snowman, 0, this.a.get().length());
   }

   private void a(String var1, String var2) {
      if (this.g != this.f) {
         _snowman = this.c(_snowman);
      }

      this.f = afm.a(this.f, 0, _snowman.length());
      String _snowman = new StringBuilder(_snowman).insert(this.f, _snowman).toString();
      if (this.e.test(_snowman)) {
         this.b.accept(_snowman);
         this.g = this.f = Math.min(_snowman.length(), this.f + _snowman.length());
      }
   }

   public void a(String var1) {
      this.a(this.a.get(), _snowman);
   }

   private void a(boolean var1) {
      if (!_snowman) {
         this.g = this.f;
      }
   }

   public void a(int var1, boolean var2) {
      this.f = x.a(this.a.get(), this.f, _snowman);
      this.a(_snowman);
   }

   public void b(int var1, boolean var2) {
      this.f = dkj.a(this.a.get(), _snowman, this.f, true);
      this.a(_snowman);
   }

   public void d(int var1) {
      String _snowman = this.a.get();
      if (!_snowman.isEmpty()) {
         String _snowmanx;
         if (this.g != this.f) {
            _snowmanx = this.c(_snowman);
         } else {
            int _snowmanxx = x.a(_snowman, this.f, _snowman);
            int _snowmanxxx = Math.min(_snowmanxx, this.f);
            int _snowmanxxxx = Math.max(_snowmanxx, this.f);
            _snowmanx = new StringBuilder(_snowman).delete(_snowmanxxx, _snowmanxxxx).toString();
            if (_snowman < 0) {
               this.g = this.f = _snowmanxxx;
            }
         }

         this.b.accept(_snowmanx);
      }
   }

   public void a() {
      String _snowman = this.a.get();
      this.d.accept(this.b(_snowman));
      this.b.accept(this.c(_snowman));
   }

   public void b() {
      this.a(this.a.get(), this.c.get());
      this.g = this.f;
   }

   public void c() {
      this.d.accept(this.b(this.a.get()));
   }

   public void d() {
      this.g = 0;
      this.f = this.a.get().length();
   }

   private String b(String var1) {
      int _snowman = Math.min(this.f, this.g);
      int _snowmanx = Math.max(this.f, this.g);
      return _snowman.substring(_snowman, _snowmanx);
   }

   private String c(String var1) {
      if (this.g == this.f) {
         return _snowman;
      } else {
         int _snowman = Math.min(this.f, this.g);
         int _snowmanx = Math.max(this.f, this.g);
         String _snowmanxx = _snowman.substring(0, _snowman) + _snowman.substring(_snowmanx);
         this.g = this.f = _snowman;
         return _snowmanxx;
      }
   }

   private void b(boolean var1) {
      this.f = 0;
      this.a(_snowman);
   }

   public void f() {
      this.c(false);
   }

   private void c(boolean var1) {
      this.f = this.a.get().length();
      this.a(_snowman);
   }

   public int g() {
      return this.f;
   }

   public void c(int var1, boolean var2) {
      this.f = this.g(_snowman);
      this.a(_snowman);
   }

   public int h() {
      return this.g;
   }

   public void a(int var1, int var2) {
      int _snowman = this.a.get().length();
      this.f = afm.a(_snowman, 0, _snowman);
      this.g = afm.a(_snowman, 0, _snowman);
   }

   public boolean i() {
      return this.f != this.g;
   }
}
