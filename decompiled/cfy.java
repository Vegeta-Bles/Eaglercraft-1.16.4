import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public abstract class cfy {
   public static final Codec<cfy> a = gm.bc.dispatchStable(cfy::a, Function.identity());
   protected final bsy b;
   protected final bsy c;
   private final chv d;
   private final long e;
   private final List<brd> f = Lists.newArrayList();

   public cfy(bsy var1, chv var2) {
      this(_snowman, _snowman, _snowman, 0L);
   }

   public cfy(bsy var1, bsy var2, chv var3, long var4) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   private void g() {
      if (this.f.isEmpty()) {
         cmx _snowman = this.d.b();
         if (_snowman != null && _snowman.c() != 0) {
            List<bsv> _snowmanx = Lists.newArrayList();

            for (bsv _snowmanxx : this.b.b()) {
               if (_snowmanxx.e().a(cla.k)) {
                  _snowmanx.add(_snowmanxx);
               }
            }

            int _snowmanxxx = _snowman.a();
            int _snowmanxxxx = _snowman.c();
            int _snowmanxxxxx = _snowman.b();
            Random _snowmanxxxxxx = new Random();
            _snowmanxxxxxx.setSeed(this.e);
            double _snowmanxxxxxxx = _snowmanxxxxxx.nextDouble() * Math.PI * 2.0;
            int _snowmanxxxxxxxx = 0;
            int _snowmanxxxxxxxxx = 0;

            for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < _snowmanxxxx; _snowmanxxxxxxxxxx++) {
               double _snowmanxxxxxxxxxxx = (double)(4 * _snowmanxxx + _snowmanxxx * _snowmanxxxxxxxxx * 6) + (_snowmanxxxxxx.nextDouble() - 0.5) * (double)_snowmanxxx * 2.5;
               int _snowmanxxxxxxxxxxxx = (int)Math.round(Math.cos(_snowmanxxxxxxx) * _snowmanxxxxxxxxxxx);
               int _snowmanxxxxxxxxxxxxx = (int)Math.round(Math.sin(_snowmanxxxxxxx) * _snowmanxxxxxxxxxxx);
               fx _snowmanxxxxxxxxxxxxxx = this.b.a((_snowmanxxxxxxxxxxxx << 4) + 8, 0, (_snowmanxxxxxxxxxxxxx << 4) + 8, 112, _snowmanx::contains, _snowmanxxxxxx);
               if (_snowmanxxxxxxxxxxxxxx != null) {
                  _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.u() >> 4;
                  _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.w() >> 4;
               }

               this.f.add(new brd(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx));
               _snowmanxxxxxxx += (Math.PI * 2) / (double)_snowmanxxxxx;
               if (++_snowmanxxxxxxxx == _snowmanxxxxx) {
                  _snowmanxxxxxxxxx++;
                  _snowmanxxxxxxxx = 0;
                  _snowmanxxxxx += 2 * _snowmanxxxxx / (_snowmanxxxxxxxxx + 1);
                  _snowmanxxxxx = Math.min(_snowmanxxxxx, _snowmanxxxx - _snowmanxxxxxxxxxx);
                  _snowmanxxxxxxx += _snowmanxxxxxx.nextDouble() * Math.PI * 2.0;
               }
            }
         }
      }
   }

   protected abstract Codec<? extends cfy> a();

   public abstract cfy a(long var1);

   public void a(gm<bsv> var1, cfw var2) {
      brd _snowman = _snowman.g();
      ((cgp)_snowman).a(new cfx(_snowman, _snowman, this.c));
   }

   public void a(long var1, bsx var3, cfw var4, chm.a var5) {
      bsx _snowman = _snowman.a(this.b);
      chx _snowmanx = new chx();
      int _snowmanxx = 8;
      brd _snowmanxxx = _snowman.g();
      int _snowmanxxxx = _snowmanxxx.b;
      int _snowmanxxxxx = _snowmanxxx.c;
      bsw _snowmanxxxxxx = this.b.b(_snowmanxxx.b << 2, 0, _snowmanxxx.c << 2).e();
      BitSet _snowmanxxxxxxx = ((cgp)_snowman).b(_snowman);

      for (int _snowmanxxxxxxxx = _snowmanxxxx - 8; _snowmanxxxxxxxx <= _snowmanxxxx + 8; _snowmanxxxxxxxx++) {
         for (int _snowmanxxxxxxxxx = _snowmanxxxxx - 8; _snowmanxxxxxxxxx <= _snowmanxxxxx + 8; _snowmanxxxxxxxxx++) {
            List<Supplier<cib<?>>> _snowmanxxxxxxxxxx = _snowmanxxxxxx.a(_snowman);
            ListIterator<Supplier<cib<?>>> _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.listIterator();

            while (_snowmanxxxxxxxxxxx.hasNext()) {
               int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.nextIndex();
               cib<?> _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.next().get();
               _snowmanx.c(_snowman + (long)_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
               if (_snowmanxxxxxxxxxxxxx.a(_snowmanx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx)) {
                  _snowmanxxxxxxxxxxxxx.a(_snowman, _snowman::a, _snowmanx, this.f(), _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxx);
               }
            }
         }
      }
   }

   @Nullable
   public fx a(aag var1, cla<?> var2, fx var3, int var4, boolean var5) {
      if (!this.b.a(_snowman)) {
         return null;
      } else if (_snowman == cla.k) {
         this.g();
         fx _snowman = null;
         double _snowmanx = Double.MAX_VALUE;
         fx.a _snowmanxx = new fx.a();

         for (brd _snowmanxxx : this.f) {
            _snowmanxx.d((_snowmanxxx.b << 4) + 8, 32, (_snowmanxxx.c << 4) + 8);
            double _snowmanxxxx = _snowmanxx.j(_snowman);
            if (_snowman == null) {
               _snowman = new fx(_snowmanxx);
               _snowmanx = _snowmanxxxx;
            } else if (_snowmanxxxx < _snowmanx) {
               _snowman = new fx(_snowmanxx);
               _snowmanx = _snowmanxxxx;
            }
         }

         return _snowman;
      } else {
         cmy _snowman = this.d.a(_snowman);
         return _snowman == null ? null : _snowman.a(_snowman, _snowman.a(), _snowman, _snowman, _snowman, _snowman.C(), _snowman);
      }
   }

   public void a(aam var1, bsn var2) {
      int _snowman = _snowman.a();
      int _snowmanx = _snowman.b();
      int _snowmanxx = _snowman * 16;
      int _snowmanxxx = _snowmanx * 16;
      fx _snowmanxxxx = new fx(_snowmanxx, 0, _snowmanxxx);
      bsv _snowmanxxxxx = this.b.b((_snowman << 2) + 2, 2, (_snowmanx << 2) + 2);
      chx _snowmanxxxxxx = new chx();
      long _snowmanxxxxxxx = _snowmanxxxxxx.a(_snowman.C(), _snowmanxx, _snowmanxxx);

      try {
         _snowmanxxxxx.a(_snowman, this, _snowman, _snowmanxxxxxxx, _snowmanxxxxxx, _snowmanxxxx);
      } catch (Exception var14) {
         l _snowmanxxxxxxxx = l.a(var14, "Biome decoration");
         _snowmanxxxxxxxx.a("Generation").a("CenterX", _snowman).a("CenterZ", _snowmanx).a("Seed", _snowmanxxxxxxx).a("Biome", _snowmanxxxxx);
         throw new u(_snowmanxxxxxxxx);
      }
   }

   public abstract void a(aam var1, cfw var2);

   public void a(aam var1) {
   }

   public chv b() {
      return this.d;
   }

   public int c() {
      return 64;
   }

   public bsy d() {
      return this.c;
   }

   public int e() {
      return 256;
   }

   public List<btg.c> a(bsv var1, bsn var2, aqo var3, fx var4) {
      return _snowman.b().a(_snowman);
   }

   public void a(gn var1, bsn var2, cfw var3, csw var4, long var5) {
      brd _snowman = _snowman.g();
      bsv _snowmanx = this.b.b((_snowman.b << 2) + 2, 0, (_snowman.c << 2) + 2);
      this.a(ko.k, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowmanx);

      for (Supplier<ciw<?, ?>> _snowmanxx : _snowmanx.e().a()) {
         this.a(_snowmanxx.get(), _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowmanx);
      }
   }

   private void a(ciw<?, ?> var1, gn var2, bsn var3, cfw var4, csw var5, long var6, brd var8, bsv var9) {
      crv<?> _snowman = _snowman.a(gp.a(_snowman.g(), 0), _snowman.d, _snowman);
      int _snowmanx = _snowman != null ? _snowman.j() : 0;
      cmy _snowmanxx = this.d.a(_snowman.d);
      if (_snowmanxx != null) {
         crv<?> _snowmanxxx = _snowman.a(_snowman, this, this.b, _snowman, _snowman, _snowman, _snowman, _snowmanx, _snowmanxx);
         _snowman.a(gp.a(_snowman.g(), 0), _snowman.d, _snowmanxxx, _snowman);
      }
   }

   public void a(bsr var1, bsn var2, cfw var3) {
      int _snowman = 8;
      int _snowmanx = _snowman.g().b;
      int _snowmanxx = _snowman.g().c;
      int _snowmanxxx = _snowmanx << 4;
      int _snowmanxxxx = _snowmanxx << 4;
      gp _snowmanxxxxx = gp.a(_snowman.g(), 0);

      for (int _snowmanxxxxxx = _snowmanx - 8; _snowmanxxxxxx <= _snowmanx + 8; _snowmanxxxxxx++) {
         for (int _snowmanxxxxxxx = _snowmanxx - 8; _snowmanxxxxxxx <= _snowmanxx + 8; _snowmanxxxxxxx++) {
            long _snowmanxxxxxxxx = brd.a(_snowmanxxxxxx, _snowmanxxxxxxx);

            for (crv<?> _snowmanxxxxxxxxx : _snowman.a(_snowmanxxxxxx, _snowmanxxxxxxx).h().values()) {
               try {
                  if (_snowmanxxxxxxxxx != crv.a && _snowmanxxxxxxxxx.c().a(_snowmanxxx, _snowmanxxxx, _snowmanxxx + 15, _snowmanxxxx + 15)) {
                     _snowman.a(_snowmanxxxxx, _snowmanxxxxxxxxx.l(), _snowmanxxxxxxxx, _snowman);
                     rz.a(_snowman, _snowmanxxxxxxxxx);
                  }
               } catch (Exception var19) {
                  l _snowmanxxxxxxxxxx = l.a(var19, "Generating structure reference");
                  m _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.a("Structure");
                  _snowmanxxxxxxxxxxx.a("Id", () -> gm.aG.b(_snowman.l()).toString());
                  _snowmanxxxxxxxxxxx.a("Name", () -> _snowman.l().i());
                  _snowmanxxxxxxxxxxx.a("Class", () -> _snowman.l().getClass().getCanonicalName());
                  throw new u(_snowmanxxxxxxxxxx);
               }
            }
         }
      }
   }

   public abstract void a(bry var1, bsn var2, cfw var3);

   public int f() {
      return 63;
   }

   public abstract int a(int var1, int var2, chn.a var3);

   public abstract brc a(int var1, int var2);

   public int b(int var1, int var2, chn.a var3) {
      return this.a(_snowman, _snowman, _snowman);
   }

   public int c(int var1, int var2, chn.a var3) {
      return this.a(_snowman, _snowman, _snowman) - 1;
   }

   public boolean a(brd var1) {
      this.g();
      return this.f.contains(_snowman);
   }

   static {
      gm.a(gm.bc, "noise", cho.d);
      gm.a(gm.bc, "flat", chl.d);
      gm.a(gm.bc, "debug", chj.d);
   }
}
