import java.util.function.Predicate;
import javax.annotation.Nullable;

public class cgi {
   private static final cgm<ceh> a = new cge<>(buo.m, bup.a.n());
   private final int b;
   private short c;
   private short d;
   private short e;
   private final cgo<ceh> f;

   public cgi(int var1) {
      this(_snowman, (short)0, (short)0, (short)0);
   }

   public cgi(int var1, short var2, short var3, short var4) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = new cgo<>(a, buo.m, mp::c, mp::a, bup.a.n());
   }

   public ceh a(int var1, int var2, int var3) {
      return this.f.a(_snowman, _snowman, _snowman);
   }

   public cux b(int var1, int var2, int var3) {
      return this.f.a(_snowman, _snowman, _snowman).m();
   }

   public void a() {
      this.f.a();
   }

   public void b() {
      this.f.b();
   }

   public ceh a(int var1, int var2, int var3, ceh var4) {
      return this.a(_snowman, _snowman, _snowman, _snowman, true);
   }

   public ceh a(int var1, int var2, int var3, ceh var4, boolean var5) {
      ceh _snowman;
      if (_snowman) {
         _snowman = this.f.a(_snowman, _snowman, _snowman, _snowman);
      } else {
         _snowman = this.f.b(_snowman, _snowman, _snowman, _snowman);
      }

      cux _snowmanx = _snowman.m();
      cux _snowmanxx = _snowman.m();
      if (!_snowman.g()) {
         this.c--;
         if (_snowman.n()) {
            this.d--;
         }
      }

      if (!_snowmanx.c()) {
         this.e--;
      }

      if (!_snowman.g()) {
         this.c++;
         if (_snowman.n()) {
            this.d++;
         }
      }

      if (!_snowmanxx.c()) {
         this.e++;
      }

      return _snowman;
   }

   public boolean c() {
      return this.c == 0;
   }

   public static boolean a(@Nullable cgi var0) {
      return _snowman == cgh.a || _snowman.c();
   }

   public boolean d() {
      return this.e() || this.f();
   }

   public boolean e() {
      return this.d > 0;
   }

   public boolean f() {
      return this.e > 0;
   }

   public int g() {
      return this.b;
   }

   public void h() {
      this.c = 0;
      this.d = 0;
      this.e = 0;
      this.f.a((var1, var2) -> {
         cux _snowman = var1.m();
         if (!var1.g()) {
            this.c = (short)(this.c + var2);
            if (var1.n()) {
               this.d = (short)(this.d + var2);
            }
         }

         if (!_snowman.c()) {
            this.c = (short)(this.c + var2);
            if (_snowman.f()) {
               this.e = (short)(this.e + var2);
            }
         }
      });
   }

   public cgo<ceh> i() {
      return this.f;
   }

   public void a(nf var1) {
      this.c = _snowman.readShort();
      this.f.a(_snowman);
   }

   public void b(nf var1) {
      _snowman.writeShort(this.c);
      this.f.b(_snowman);
   }

   public int j() {
      return 2 + this.f.c();
   }

   public boolean a(Predicate<ceh> var1) {
      return this.f.a(_snowman);
   }
}
