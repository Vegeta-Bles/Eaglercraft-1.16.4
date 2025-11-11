import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import org.apache.commons.lang3.mutable.MutableBoolean;

public abstract class cig<C extends chz> {
   public static final cig<cmk> a = a("cave", new cia(cmk.b, 256));
   public static final cig<cmk> b = a("nether_cave", new cic(cmk.b));
   public static final cig<cmk> c = a("canyon", new chy(cmk.b));
   public static final cig<cmk> d = a("underwater_canyon", new cie(cmk.b));
   public static final cig<cmk> e = a("underwater_cave", new cif(cmk.b));
   protected static final ceh f = bup.a.n();
   protected static final ceh g = bup.lb.n();
   protected static final cux h = cuy.c.h();
   protected static final cux i = cuy.e.h();
   protected Set<buo> j = ImmutableSet.of(
      bup.b,
      bup.c,
      bup.e,
      bup.g,
      bup.j,
      bup.k,
      new buo[]{
         bup.l,
         bup.i,
         bup.gR,
         bup.fF,
         bup.fG,
         bup.fH,
         bup.fI,
         bup.fJ,
         bup.fK,
         bup.fL,
         bup.fM,
         bup.fN,
         bup.fO,
         bup.fP,
         bup.fQ,
         bup.fR,
         bup.fS,
         bup.fT,
         bup.fU,
         bup.at,
         bup.hG,
         bup.dT,
         bup.cC,
         bup.gT
      }
   );
   protected Set<cuw> k = ImmutableSet.of(cuy.c);
   private final Codec<cib<C>> m;
   protected final int l;

   private static <C extends chz, F extends cig<C>> F a(String var0, F var1) {
      return gm.a(gm.aC, _snowman, _snowman);
   }

   public cig(Codec<C> var1, int var2) {
      this.l = _snowman;
      this.m = _snowman.fieldOf("config").xmap(this::a, cib::a).codec();
   }

   public cib<C> a(C var1) {
      return new cib<>(this, _snowman);
   }

   public Codec<cib<C>> c() {
      return this.m;
   }

   public int d() {
      return 4;
   }

   protected boolean a(
      cfw var1,
      Function<fx, bsv> var2,
      long var3,
      int var5,
      int var6,
      int var7,
      double var8,
      double var10,
      double var12,
      double var14,
      double var16,
      BitSet var18
   ) {
      Random _snowman = new Random(_snowman + (long)_snowman + (long)_snowman);
      double _snowmanx = (double)(_snowman * 16 + 8);
      double _snowmanxx = (double)(_snowman * 16 + 8);
      if (!(_snowman < _snowmanx - 16.0 - _snowman * 2.0) && !(_snowman < _snowmanxx - 16.0 - _snowman * 2.0) && !(_snowman > _snowmanx + 16.0 + _snowman * 2.0) && !(_snowman > _snowmanxx + 16.0 + _snowman * 2.0)) {
         int _snowmanxxx = Math.max(afm.c(_snowman - _snowman) - _snowman * 16 - 1, 0);
         int _snowmanxxxx = Math.min(afm.c(_snowman + _snowman) - _snowman * 16 + 1, 16);
         int _snowmanxxxxx = Math.max(afm.c(_snowman - _snowman) - 1, 1);
         int _snowmanxxxxxx = Math.min(afm.c(_snowman + _snowman) + 1, this.l - 8);
         int _snowmanxxxxxxx = Math.max(afm.c(_snowman - _snowman) - _snowman * 16 - 1, 0);
         int _snowmanxxxxxxxx = Math.min(afm.c(_snowman + _snowman) - _snowman * 16 + 1, 16);
         if (this.a(_snowman, _snowman, _snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx)) {
            return false;
         } else {
            boolean _snowmanxxxxxxxxx = false;
            fx.a _snowmanxxxxxxxxxx = new fx.a();
            fx.a _snowmanxxxxxxxxxxx = new fx.a();
            fx.a _snowmanxxxxxxxxxxxx = new fx.a();

            for (int _snowmanxxxxxxxxxxxxx = _snowmanxxx; _snowmanxxxxxxxxxxxxx < _snowmanxxxx; _snowmanxxxxxxxxxxxxx++) {
               int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx + _snowman * 16;
               double _snowmanxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxxx + 0.5 - _snowman) / _snowman;

               for (int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxx; _snowmanxxxxxxxxxxxxxxxx < _snowmanxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx++) {
                  int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx + _snowman * 16;
                  double _snowmanxxxxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxxxxxx + 0.5 - _snowman) / _snowman;
                  if (!(_snowmanxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxx >= 1.0)) {
                     MutableBoolean _snowmanxxxxxxxxxxxxxxxxxxx = new MutableBoolean(false);

                     for (int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxx > _snowmanxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxx--) {
                        double _snowmanxxxxxxxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxxxxxxxxx - 0.5 - _snowman) / _snowman;
                        if (!this.a(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx)) {
                           _snowmanxxxxxxxxx |= this.a(
                              _snowman,
                              _snowman,
                              _snowman,
                              _snowman,
                              _snowmanxxxxxxxxxx,
                              _snowmanxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxx,
                              _snowman,
                              _snowman,
                              _snowman,
                              _snowmanxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxx
                           );
                        }
                     }
                  }
               }
            }

            return _snowmanxxxxxxxxx;
         }
      } else {
         return false;
      }
   }

   protected boolean a(
      cfw var1,
      Function<fx, bsv> var2,
      BitSet var3,
      Random var4,
      fx.a var5,
      fx.a var6,
      fx.a var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      int var13,
      int var14,
      int var15,
      MutableBoolean var16
   ) {
      int _snowman = _snowman | _snowman << 4 | _snowman << 8;
      if (_snowman.get(_snowman)) {
         return false;
      } else {
         _snowman.set(_snowman);
         _snowman.d(_snowman, _snowman, _snowman);
         ceh _snowmanx = _snowman.d_(_snowman);
         ceh _snowmanxx = _snowman.d_(_snowman.a(_snowman, gc.b));
         if (_snowmanx.a(bup.i) || _snowmanx.a(bup.dT)) {
            _snowman.setTrue();
         }

         if (!this.a(_snowmanx, _snowmanxx)) {
            return false;
         } else {
            if (_snowman < 11) {
               _snowman.a(_snowman, i.g(), false);
            } else {
               _snowman.a(_snowman, g, false);
               if (_snowman.isTrue()) {
                  _snowman.a(_snowman, gc.a);
                  if (_snowman.d_(_snowman).a(bup.j)) {
                     _snowman.a(_snowman, _snowman.apply(_snowman).e().e().a(), false);
                  }
               }
            }

            return true;
         }
      }
   }

   public abstract boolean a(cfw var1, Function<fx, bsv> var2, Random var3, int var4, int var5, int var6, int var7, int var8, BitSet var9, C var10);

   public abstract boolean a(Random var1, int var2, int var3, C var4);

   protected boolean a(ceh var1) {
      return this.j.contains(_snowman.b());
   }

   protected boolean a(ceh var1, ceh var2) {
      return this.a(_snowman) || (_snowman.a(bup.C) || _snowman.a(bup.E)) && !_snowman.m().a(aef.b);
   }

   protected boolean a(cfw var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      fx.a _snowman = new fx.a();

      for (int _snowmanx = _snowman; _snowmanx < _snowman; _snowmanx++) {
         for (int _snowmanxx = _snowman; _snowmanxx < _snowman; _snowmanxx++) {
            for (int _snowmanxxx = _snowman - 1; _snowmanxxx <= _snowman + 1; _snowmanxxx++) {
               if (this.k.contains(_snowman.b(_snowman.d(_snowmanx + _snowman * 16, _snowmanxxx, _snowmanxx + _snowman * 16)).a())) {
                  return true;
               }

               if (_snowmanxxx != _snowman + 1 && !this.a(_snowman, _snowman, _snowman, _snowman, _snowmanx, _snowmanxx)) {
                  _snowmanxxx = _snowman;
               }
            }
         }
      }

      return false;
   }

   private boolean a(int var1, int var2, int var3, int var4, int var5, int var6) {
      return _snowman == _snowman || _snowman == _snowman - 1 || _snowman == _snowman || _snowman == _snowman - 1;
   }

   protected boolean a(int var1, int var2, double var3, double var5, int var7, int var8, float var9) {
      double _snowman = (double)(_snowman * 16 + 8);
      double _snowmanx = (double)(_snowman * 16 + 8);
      double _snowmanxx = _snowman - _snowman;
      double _snowmanxxx = _snowman - _snowmanx;
      double _snowmanxxxx = (double)(_snowman - _snowman);
      double _snowmanxxxxx = (double)(_snowman + 2.0F + 16.0F);
      return _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx - _snowmanxxxx * _snowmanxxxx <= _snowmanxxxxx * _snowmanxxxxx;
   }

   protected abstract boolean a(double var1, double var3, double var5, int var7);
}
