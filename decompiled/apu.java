import com.google.common.collect.ComparisonChain;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class apu implements Comparable<apu> {
   private static final Logger a = LogManager.getLogger();
   private final aps b;
   private int c;
   private int d;
   private boolean e;
   private boolean f;
   private boolean g;
   private boolean h;
   private boolean i;
   @Nullable
   private apu j;

   public apu(aps var1) {
      this(_snowman, 0, 0);
   }

   public apu(aps var1, int var2) {
      this(_snowman, _snowman, 0);
   }

   public apu(aps var1, int var2, int var3) {
      this(_snowman, _snowman, _snowman, false, true);
   }

   public apu(aps var1, int var2, int var3, boolean var4, boolean var5) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public apu(aps var1, int var2, int var3, boolean var4, boolean var5, boolean var6) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, null);
   }

   public apu(aps var1, int var2, int var3, boolean var4, boolean var5, boolean var6, @Nullable apu var7) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.f = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
   }

   public apu(apu var1) {
      this.b = _snowman.b;
      this.a(_snowman);
   }

   void a(apu var1) {
      this.c = _snowman.c;
      this.d = _snowman.d;
      this.f = _snowman.f;
      this.h = _snowman.h;
      this.i = _snowman.i;
   }

   public boolean b(apu var1) {
      if (this.b != _snowman.b) {
         a.warn("This method should only be called for matching effects!");
      }

      boolean _snowman = false;
      if (_snowman.d > this.d) {
         if (_snowman.c < this.c) {
            apu _snowmanx = this.j;
            this.j = new apu(this);
            this.j.j = _snowmanx;
         }

         this.d = _snowman.d;
         this.c = _snowman.c;
         _snowman = true;
      } else if (_snowman.c > this.c) {
         if (_snowman.d == this.d) {
            this.c = _snowman.c;
            _snowman = true;
         } else if (this.j == null) {
            this.j = new apu(_snowman);
         } else {
            this.j.b(_snowman);
         }
      }

      if (!_snowman.f && this.f || _snowman) {
         this.f = _snowman.f;
         _snowman = true;
      }

      if (_snowman.h != this.h) {
         this.h = _snowman.h;
         _snowman = true;
      }

      if (_snowman.i != this.i) {
         this.i = _snowman.i;
         _snowman = true;
      }

      return _snowman;
   }

   public aps a() {
      return this.b;
   }

   public int b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }

   public boolean d() {
      return this.f;
   }

   public boolean e() {
      return this.h;
   }

   public boolean f() {
      return this.i;
   }

   public boolean a(aqm var1, Runnable var2) {
      if (this.c > 0) {
         if (this.b.a(this.c, this.d)) {
            this.a(_snowman);
         }

         this.i();
         if (this.c == 0 && this.j != null) {
            this.a(this.j);
            this.j = this.j.j;
            _snowman.run();
         }
      }

      return this.c > 0;
   }

   private int i() {
      if (this.j != null) {
         this.j.i();
      }

      return --this.c;
   }

   public void a(aqm var1) {
      if (this.c > 0) {
         this.b.a(_snowman, this.d);
      }
   }

   public String g() {
      return this.b.c();
   }

   @Override
   public String toString() {
      String _snowman;
      if (this.d > 0) {
         _snowman = this.g() + " x " + (this.d + 1) + ", Duration: " + this.c;
      } else {
         _snowman = this.g() + ", Duration: " + this.c;
      }

      if (this.e) {
         _snowman = _snowman + ", Splash: true";
      }

      if (!this.h) {
         _snowman = _snowman + ", Particles: false";
      }

      if (!this.i) {
         _snowman = _snowman + ", Show Icon: false";
      }

      return _snowman;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof apu)) {
         return false;
      } else {
         apu _snowman = (apu)_snowman;
         return this.c == _snowman.c && this.d == _snowman.d && this.e == _snowman.e && this.f == _snowman.f && this.b.equals(_snowman.b);
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.b.hashCode();
      _snowman = 31 * _snowman + this.c;
      _snowman = 31 * _snowman + this.d;
      _snowman = 31 * _snowman + (this.e ? 1 : 0);
      return 31 * _snowman + (this.f ? 1 : 0);
   }

   public md a(md var1) {
      _snowman.a("Id", (byte)aps.a(this.a()));
      this.c(_snowman);
      return _snowman;
   }

   private void c(md var1) {
      _snowman.a("Amplifier", (byte)this.c());
      _snowman.b("Duration", this.b());
      _snowman.a("Ambient", this.d());
      _snowman.a("ShowParticles", this.e());
      _snowman.a("ShowIcon", this.f());
      if (this.j != null) {
         md _snowman = new md();
         this.j.a(_snowman);
         _snowman.a("HiddenEffect", _snowman);
      }
   }

   public static apu b(md var0) {
      int _snowman = _snowman.f("Id");
      aps _snowmanx = aps.a(_snowman);
      return _snowmanx == null ? null : a(_snowmanx, _snowman);
   }

   private static apu a(aps var0, md var1) {
      int _snowman = _snowman.f("Amplifier");
      int _snowmanx = _snowman.h("Duration");
      boolean _snowmanxx = _snowman.q("Ambient");
      boolean _snowmanxxx = true;
      if (_snowman.c("ShowParticles", 1)) {
         _snowmanxxx = _snowman.q("ShowParticles");
      }

      boolean _snowmanxxxx = _snowmanxxx;
      if (_snowman.c("ShowIcon", 1)) {
         _snowmanxxxx = _snowman.q("ShowIcon");
      }

      apu _snowmanxxxxx = null;
      if (_snowman.c("HiddenEffect", 10)) {
         _snowmanxxxxx = a(_snowman, _snowman.p("HiddenEffect"));
      }

      return new apu(_snowman, _snowmanx, _snowman < 0 ? 0 : _snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   public void b(boolean var1) {
      this.g = _snowman;
   }

   public boolean h() {
      return this.g;
   }

   public int c(apu var1) {
      int _snowman = 32147;
      return (this.b() <= 32147 || _snowman.b() <= 32147) && (!this.d() || !_snowman.d())
         ? ComparisonChain.start().compare(this.d(), _snowman.d()).compare(this.b(), _snowman.b()).compare(this.a().f(), _snowman.a().f()).result()
         : ComparisonChain.start().compare(this.d(), _snowman.d()).compare(this.a().f(), _snowman.a().f()).result();
   }
}
