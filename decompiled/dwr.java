import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dwr extends cfz {
   private static final Logger a = LogManager.getLogger();
   private final cgh b;
   private final cuo c;
   private volatile dwr.a d;
   private final dwt e;

   public dwr(dwt var1, int var2) {
      this.e = _snowman;
      this.b = new cgc(_snowman, new brd(0, 0));
      this.c = new cuo(this, true, _snowman.k().b());
      this.d = new dwr.a(b(_snowman));
   }

   @Override
   public cuo l() {
      return this.c;
   }

   private static boolean a(@Nullable cgh var0, int var1, int var2) {
      if (_snowman == null) {
         return false;
      } else {
         brd _snowman = _snowman.g();
         return _snowman.b == _snowman && _snowman.c == _snowman;
      }
   }

   public void d(int var1, int var2) {
      if (this.d.b(_snowman, _snowman)) {
         int _snowman = this.d.a(_snowman, _snowman);
         cgh _snowmanx = this.d.a(_snowman);
         if (a(_snowmanx, _snowman, _snowman)) {
            this.d.a(_snowman, _snowmanx, null);
         }
      }
   }

   @Nullable
   public cgh b(int var1, int var2, cga var3, boolean var4) {
      if (this.d.b(_snowman, _snowman)) {
         cgh _snowman = this.d.a(this.d.a(_snowman, _snowman));
         if (a(_snowman, _snowman, _snowman)) {
            return _snowman;
         }
      }

      return _snowman ? this.b : null;
   }

   @Override
   public brc m() {
      return this.e;
   }

   @Nullable
   public cgh a(int var1, int var2, @Nullable cfx var3, nf var4, md var5, int var6, boolean var7) {
      if (!this.d.b(_snowman, _snowman)) {
         a.warn("Ignoring chunk since it's not in the view range: {}, {}", _snowman, _snowman);
         return null;
      } else {
         int _snowman = this.d.a(_snowman, _snowman);
         cgh _snowmanx = this.d.b.get(_snowman);
         if (!_snowman && a(_snowmanx, _snowman, _snowman)) {
            _snowmanx.a(_snowman, _snowman, _snowman, _snowman);
         } else {
            if (_snowman == null) {
               a.warn("Ignoring chunk since we don't have complete data: {}, {}", _snowman, _snowman);
               return null;
            }

            _snowmanx = new cgh(this.e, new brd(_snowman, _snowman), _snowman);
            _snowmanx.a(_snowman, _snowman, _snowman, _snowman);
            this.d.a(_snowman, _snowmanx);
         }

         cgi[] _snowmanxx = _snowmanx.d();
         cuo _snowmanxxx = this.l();
         _snowmanxxx.a(new brd(_snowman, _snowman), true);

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxx.length; _snowmanxxxx++) {
            cgi _snowmanxxxxx = _snowmanxx[_snowmanxxxx];
            _snowmanxxx.a(gp.a(_snowman, _snowmanxxxx, _snowman), cgi.a(_snowmanxxxxx));
         }

         this.e.e(_snowman, _snowman);
         return _snowmanx;
      }
   }

   public void a(BooleanSupplier var1) {
   }

   public void e(int var1, int var2) {
      this.d.e = _snowman;
      this.d.f = _snowman;
   }

   public void a(int var1) {
      int _snowman = this.d.c;
      int _snowmanx = b(_snowman);
      if (_snowman != _snowmanx) {
         dwr.a _snowmanxx = new dwr.a(_snowmanx);
         _snowmanxx.e = this.d.e;
         _snowmanxx.f = this.d.f;

         for (int _snowmanxxx = 0; _snowmanxxx < this.d.b.length(); _snowmanxxx++) {
            cgh _snowmanxxxx = this.d.b.get(_snowmanxxx);
            if (_snowmanxxxx != null) {
               brd _snowmanxxxxx = _snowmanxxxx.g();
               if (_snowmanxx.b(_snowmanxxxxx.b, _snowmanxxxxx.c)) {
                  _snowmanxx.a(_snowmanxx.a(_snowmanxxxxx.b, _snowmanxxxxx.c), _snowmanxxxx);
               }
            }
         }

         this.d = _snowmanxx;
      }
   }

   private static int b(int var0) {
      return Math.max(2, _snowman) + 3;
   }

   @Override
   public String e() {
      return "Client Chunk Cache: " + this.d.b.length() + ", " + this.h();
   }

   public int h() {
      return this.d.g;
   }

   @Override
   public void a(bsf var1, gp var2) {
      djz.C().e.b(_snowman.a(), _snowman.b(), _snowman.c());
   }

   @Override
   public boolean a(fx var1) {
      return this.b(_snowman.u() >> 4, _snowman.w() >> 4);
   }

   @Override
   public boolean a(brd var1) {
      return this.b(_snowman.b, _snowman.c);
   }

   @Override
   public boolean a(aqa var1) {
      return this.b(afm.c(_snowman.cD()) >> 4, afm.c(_snowman.cH()) >> 4);
   }

   final class a {
      private final AtomicReferenceArray<cgh> b;
      private final int c;
      private final int d;
      private volatile int e;
      private volatile int f;
      private int g;

      private a(int var2) {
         this.c = _snowman;
         this.d = _snowman * 2 + 1;
         this.b = new AtomicReferenceArray<>(this.d * this.d);
      }

      private int a(int var1, int var2) {
         return Math.floorMod(_snowman, this.d) * this.d + Math.floorMod(_snowman, this.d);
      }

      protected void a(int var1, @Nullable cgh var2) {
         cgh _snowman = this.b.getAndSet(_snowman, _snowman);
         if (_snowman != null) {
            this.g--;
            dwr.this.e.a(_snowman);
         }

         if (_snowman != null) {
            this.g++;
         }
      }

      protected cgh a(int var1, cgh var2, @Nullable cgh var3) {
         if (this.b.compareAndSet(_snowman, _snowman, _snowman) && _snowman == null) {
            this.g--;
         }

         dwr.this.e.a(_snowman);
         return _snowman;
      }

      private boolean b(int var1, int var2) {
         return Math.abs(_snowman - this.e) <= this.c && Math.abs(_snowman - this.f) <= this.c;
      }

      @Nullable
      protected cgh a(int var1) {
         return this.b.get(_snowman);
      }
   }
}
