import com.mojang.datafixers.util.Pair;
import java.util.List;

public class ebz extends ece<cca> {
   private final dwn a = a();
   private final dwn c = new dwn(64, 64, 44, 0);
   private final dwn d;

   public ebz(ecd var1) {
      super(_snowman);
      this.c.a(-1.0F, -30.0F, -1.0F, 2.0F, 42.0F, 2.0F, 0.0F);
      this.d = new dwn(64, 64, 0, 42);
      this.d.a(-10.0F, -32.0F, -1.0F, 20.0F, 2.0F, 2.0F, 0.0F);
   }

   public static dwn a() {
      dwn _snowman = new dwn(64, 64, 0, 0);
      _snowman.a(-10.0F, 0.0F, -2.0F, 20.0F, 40.0F, 1.0F, 0.0F);
      return _snowman;
   }

   public void a(cca var1, float var2, dfm var3, eag var4, int var5, int var6) {
      List<Pair<ccb, bkx>> _snowman = _snowman.c();
      if (_snowman != null) {
         float _snowmanx = 0.6666667F;
         boolean _snowmanxx = _snowman.v() == null;
         _snowman.a();
         long _snowmanxxx;
         if (_snowmanxx) {
            _snowmanxxx = 0L;
            _snowman.a(0.5, 0.5, 0.5);
            this.c.h = true;
         } else {
            _snowmanxxx = _snowman.v().T();
            ceh _snowmanxxxx = _snowman.p();
            if (_snowmanxxxx.b() instanceof btw) {
               _snowman.a(0.5, 0.5, 0.5);
               float _snowmanxxxxx = (float)(-_snowmanxxxx.c(btw.a) * 360) / 16.0F;
               _snowman.a(g.d.a(_snowmanxxxxx));
               this.c.h = true;
            } else {
               _snowman.a(0.5, -0.16666667F, 0.5);
               float _snowmanxxxxx = -_snowmanxxxx.c(cbj.a).o();
               _snowman.a(g.d.a(_snowmanxxxxx));
               _snowman.a(0.0, -0.3125, -0.4375);
               this.c.h = false;
            }
         }

         _snowman.a();
         _snowman.a(0.6666667F, -0.6666667F, -0.6666667F);
         dfq _snowmanxxxxx = els.f.a(_snowman, eao::b);
         this.c.a(_snowman, _snowmanxxxxx, _snowman, _snowman);
         this.d.a(_snowman, _snowmanxxxxx, _snowman, _snowman);
         fx _snowmanxxxxxx = _snowman.o();
         float _snowmanxxxxxxx = ((float)Math.floorMod((long)(_snowmanxxxxxx.u() * 7 + _snowmanxxxxxx.v() * 9 + _snowmanxxxxxx.w() * 13) + _snowmanxxx, 100L) + _snowman) / 100.0F;
         this.a.d = (-0.0125F + 0.01F * afm.b((float) (Math.PI * 2) * _snowmanxxxxxxx)) * (float) Math.PI;
         this.a.b = -32.0F;
         a(_snowman, _snowman, _snowman, _snowman, this.a, els.f, true, _snowman);
         _snowman.b();
         _snowman.b();
      }
   }

   public static void a(dfm var0, eag var1, int var2, int var3, dwn var4, elr var5, boolean var6, List<Pair<ccb, bkx>> var7) {
      a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, false);
   }

   public static void a(dfm var0, eag var1, int var2, int var3, dwn var4, elr var5, boolean var6, List<Pair<ccb, bkx>> var7, boolean var8) {
      _snowman.a(_snowman, _snowman.a(_snowman, eao::b, _snowman), _snowman, _snowman);

      for (int _snowman = 0; _snowman < 17 && _snowman < _snowman.size(); _snowman++) {
         Pair<ccb, bkx> _snowmanx = _snowman.get(_snowman);
         float[] _snowmanxx = ((bkx)_snowmanx.getSecond()).e();
         elr _snowmanxxx = new elr(_snowman ? ear.c : ear.d, ((ccb)_snowmanx.getFirst()).a(_snowman));
         _snowman.a(_snowman, _snowmanxxx.a(_snowman, eao::k), _snowman, _snowman, _snowmanxx[0], _snowmanxx[1], _snowmanxx[2], 1.0F);
      }
   }
}
