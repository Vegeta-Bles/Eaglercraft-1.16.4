import java.util.Arrays;
import javax.annotation.Nullable;
import org.apache.commons.lang3.mutable.MutableInt;

public abstract class cul<M extends cui<M>, S extends cun<M>> extends cuj implements cum {
   private static final gc[] e = gc.values();
   protected final cgj a;
   protected final bsf b;
   protected final S c;
   private boolean f;
   protected final fx.a d = new fx.a();
   private final long[] g = new long[2];
   private final brc[] h = new brc[2];

   public cul(cgj var1, bsf var2, S var3) {
      super(16, 256, 8192);
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d();
   }

   @Override
   protected void f(long var1) {
      this.c.d();
      if (this.c.g(gp.e(_snowman))) {
         super.f(_snowman);
      }
   }

   @Nullable
   private brc a(int var1, int var2) {
      long _snowman = brd.a(_snowman, _snowman);

      for (int _snowmanx = 0; _snowmanx < 2; _snowmanx++) {
         if (_snowman == this.g[_snowmanx]) {
            return this.h[_snowmanx];
         }
      }

      brc _snowmanxx = this.a.c(_snowman, _snowman);

      for (int _snowmanxxx = 1; _snowmanxxx > 0; _snowmanxxx--) {
         this.g[_snowmanxxx] = this.g[_snowmanxxx - 1];
         this.h[_snowmanxxx] = this.h[_snowmanxxx - 1];
      }

      this.g[0] = _snowman;
      this.h[0] = _snowmanxx;
      return _snowmanxx;
   }

   private void d() {
      Arrays.fill(this.g, brd.a);
      Arrays.fill(this.h, null);
   }

   protected ceh a(long var1, @Nullable MutableInt var3) {
      if (_snowman == Long.MAX_VALUE) {
         if (_snowman != null) {
            _snowman.setValue(0);
         }

         return bup.a.n();
      } else {
         int _snowman = gp.a(fx.b(_snowman));
         int _snowmanx = gp.a(fx.d(_snowman));
         brc _snowmanxx = this.a(_snowman, _snowmanx);
         if (_snowmanxx == null) {
            if (_snowman != null) {
               _snowman.setValue(16);
            }

            return bup.z.n();
         } else {
            this.d.g(_snowman);
            ceh _snowmanxxx = _snowmanxx.d_(this.d);
            boolean _snowmanxxxx = _snowmanxxx.l() && _snowmanxxx.e();
            if (_snowman != null) {
               _snowman.setValue(_snowmanxxx.b(this.a.m(), this.d));
            }

            return _snowmanxxxx ? _snowmanxxx : bup.a.n();
         }
      }
   }

   protected ddh a(ceh var1, long var2, gc var4) {
      return _snowman.l() ? _snowman.a(this.a.m(), this.d.g(_snowman), _snowman) : dde.a();
   }

   public static int a(brc var0, ceh var1, fx var2, ceh var3, fx var4, gc var5, int var6) {
      boolean _snowman = _snowman.l() && _snowman.e();
      boolean _snowmanx = _snowman.l() && _snowman.e();
      if (!_snowman && !_snowmanx) {
         return _snowman;
      } else {
         ddh _snowmanxx = _snowman ? _snowman.c(_snowman, _snowman) : dde.a();
         ddh _snowmanxxx = _snowmanx ? _snowman.c(_snowman, _snowman) : dde.a();
         return dde.b(_snowmanxx, _snowmanxxx, _snowman) ? 16 : _snowman;
      }
   }

   @Override
   protected boolean a(long var1) {
      return _snowman == Long.MAX_VALUE;
   }

   @Override
   protected int a(long var1, long var3, int var5) {
      return 0;
   }

   @Override
   protected int c(long var1) {
      return _snowman == Long.MAX_VALUE ? 0 : 15 - this.c.i(_snowman);
   }

   protected int a(cgb var1, long var2) {
      return 15 - _snowman.a(gp.b(fx.b(_snowman)), gp.b(fx.c(_snowman)), gp.b(fx.d(_snowman)));
   }

   @Override
   protected void a(long var1, int var3) {
      this.c.b(_snowman, Math.min(15, 15 - _snowman));
   }

   @Override
   protected int b(long var1, long var3, int var5) {
      return 0;
   }

   public boolean a() {
      return this.b() || this.c.b() || this.c.a();
   }

   public int a(int var1, boolean var2, boolean var3) {
      if (!this.f) {
         if (this.c.b()) {
            _snowman = this.c.b(_snowman);
            if (_snowman == 0) {
               return _snowman;
            }
         }

         this.c.a(this, _snowman, _snowman);
      }

      this.f = true;
      if (this.b()) {
         _snowman = this.b(_snowman);
         this.d();
         if (_snowman == 0) {
            return _snowman;
         }
      }

      this.f = false;
      this.c.e();
      return _snowman;
   }

   protected void a(long var1, @Nullable cgb var3, boolean var4) {
      this.c.a(_snowman, _snowman, _snowman);
   }

   @Nullable
   @Override
   public cgb a(gp var1) {
      return this.c.h(_snowman.s());
   }

   @Override
   public int b(fx var1) {
      return this.c.d(_snowman.a());
   }

   public String b(long var1) {
      return "" + this.c.c(_snowman);
   }

   public void a(fx var1) {
      long _snowman = _snowman.a();
      this.f(_snowman);

      for (gc _snowmanx : e) {
         this.f(fx.a(_snowman, _snowmanx));
      }
   }

   public void a(fx var1, int var2) {
   }

   @Override
   public void a(gp var1, boolean var2) {
      this.c.d(_snowman.s(), _snowman);
   }

   public void a(brd var1, boolean var2) {
      long _snowman = gp.f(gp.b(_snowman.b, 0, _snowman.c));
      this.c.b(_snowman, _snowman);
   }

   public void b(brd var1, boolean var2) {
      long _snowman = gp.f(gp.b(_snowman.b, 0, _snowman.c));
      this.c.c(_snowman, _snowman);
   }
}
